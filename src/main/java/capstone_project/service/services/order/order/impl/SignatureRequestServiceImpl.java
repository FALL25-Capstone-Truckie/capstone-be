package capstone_project.service.services.order.order.impl;

import capstone_project.common.enums.ContractStatusEnum;
import capstone_project.common.enums.ErrorEnum;
import capstone_project.common.enums.OrderStatusEnum;
import capstone_project.common.exceptions.dto.BadRequestException;
import capstone_project.common.exceptions.dto.InternalServerException;
import capstone_project.common.exceptions.dto.NotFoundException;
import capstone_project.dtos.request.order.SignatureRequestDto;
import capstone_project.dtos.response.order.SignatureResponseDto;
import capstone_project.entity.auth.UserEntity;
import capstone_project.entity.order.conformation.SignatureRequestEntity;
import capstone_project.entity.order.contract.ContractEntity;
import capstone_project.entity.order.order.OrderEntity;
import capstone_project.entity.user.customer.CustomerEntity;
import capstone_project.repository.entityServices.auth.UserEntityService;
import capstone_project.repository.entityServices.order.conformation.SignatureRequestEntityService;
import capstone_project.repository.entityServices.order.contract.ContractEntityService;
import capstone_project.repository.entityServices.order.order.OrderEntityService;
import capstone_project.repository.entityServices.user.CustomerEntityService;
import capstone_project.service.mapper.order.SignatureRequestMapper;
import capstone_project.service.services.cloudinary.CloudinaryService;
import capstone_project.service.services.order.order.ContractService;
import capstone_project.service.services.order.order.OrderService;
import capstone_project.service.services.order.order.SignatureRequestService;
import capstone_project.service.services.pdf.PdfGenerationService;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.StampingProperties;
import com.itextpdf.signatures.*;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.cert.jcajce.JcaX509v3CertificateBuilder;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class SignatureRequestServiceImpl implements SignatureRequestService {
    private final SignatureRequestEntityService signatureRequestEntityService;
    private final SignatureRequestMapper signatureRequestMapper;
    private final OrderEntityService orderEntityService;
    private final ContractEntityService contractEntityService;
    private final CloudinaryService cloudinaryService;
    private final OrderService orderService;
    private final ContractService contractService;
    private final UserEntityService userEntityService;
    private final CustomerEntityService customerEntityService;
    private final PdfGenerationService pdfSigningService;



    @Override
    @Transactional
    public boolean verifySignedPdf(byte[] signedPdf, SignatureRequestDto requestDto) {
        try {
            Security.addProvider(new BouncyCastleProvider());

            PdfDocument pdfDoc = new PdfDocument(new PdfReader(new ByteArrayInputStream(signedPdf)));
            SignatureUtil signUtil = new SignatureUtil(pdfDoc);

            List<String> names = signUtil.getSignatureNames();
            if (names.isEmpty()) return false;

            UserEntity user = userEntityService.findEntityById(requestDto.userId())
                    .orElseThrow(() -> new BadRequestException(
                            ErrorEnum.NOT_FOUND.getMessage() + " Không tìm thấy user với ID này: " + requestDto.userId(),
                            ErrorEnum.NOT_FOUND.getErrorCode()
                    ));

            CustomerEntity customer = customerEntityService.findByUserId(user.getId())
                    .orElseThrow(() -> new BadRequestException(
                            ErrorEnum.NOT_FOUND.getMessage() + " Không tìm thấy khách hàng với user ID này: " + requestDto.userId(),
                            ErrorEnum.NOT_FOUND.getErrorCode()
                    ));

            if (customer.getPublicCertificate() == null)
                throw new NotFoundException(
                        ErrorEnum.NOT_FOUND.getMessage() + " Khóa public không tìm thấy với khách hàng ID này: " + customer.getId(),
                        ErrorEnum.NOT_FOUND.getErrorCode()
                );

            X509Certificate userCert = (X509Certificate) CertificateFactory
                    .getInstance("X.509")
                    .generateCertificate(new ByteArrayInputStream(customer.getPublicCertificate()));

            for (String name : names) {
                PdfPKCS7 pkcs7 = signUtil.readSignatureData(name);

                boolean integrity = pkcs7.verifySignatureIntegrityAndAuthenticity();
                if (!integrity) return false;

                X509Certificate signerCert = (X509Certificate) pkcs7.getSigningCertificate();
                if (!signerCert.equals(userCert)) return false;
            }

            // ✅ Nếu hợp lệ thì upload
            String uploadedUrl = cloudinaryService.uploadFile(
                    signedPdf,
                    "signed_contract_" + requestDto.orderId(),
                    "signed_contracts"
            ).get("url").toString();

            OrderEntity order = orderEntityService.findEntityById(requestDto.orderId())
                    .orElseThrow(() -> new InternalServerException(ErrorEnum.NOT_FOUND));

            ContractEntity contract = contractEntityService.findEntityById(order.getId())
                    .orElseThrow(() -> new InternalServerException(ErrorEnum.NOT_FOUND));

            SignatureRequestEntity signatureRequest = SignatureRequestEntity.builder()
                    .orderEntity(order)
                    .signatureImageUrl(uploadedUrl)
                    .notes(requestDto.notes())
                    .status(ContractStatusEnum.CONTRACT_SIGNED.name())
                    .user(user)
                    .build();

            signatureRequestEntityService.save(signatureRequest);

            orderService.changeStatusOrderWithAllOrderDetail(requestDto.orderId(), OrderStatusEnum.CONTRACT_SIGNED);
            contract.setStatus(ContractStatusEnum.CONTRACT_SIGNED.name());
            contractEntityService.save(contract);

            return true;
        } catch (Exception e) {
            throw new RuntimeException("Xác thực file PDF có chữ ký thất bại", e);
        }
    }

    @Override
    public List<SignatureResponseDto> getSignatureResponseDtoByUserId(UUID userId) {
        UserEntity user = userEntityService.findEntityById(userId)
                .orElseThrow(() -> new BadRequestException(
                        ErrorEnum.NOT_FOUND.getMessage() + " Không tìm thấy user với ID này:  " + userId,
                        ErrorEnum.NOT_FOUND.getErrorCode()
                ));

        List<SignatureRequestEntity> signatureRequest = signatureRequestEntityService.findByUser(user);
        if(signatureRequest == null){
            throw new NotFoundException(ErrorEnum.NOT_FOUND.getMessage() + "Không tìm thấy hợp đồng có chữ ký nào với user ID này: " + userId,ErrorEnum.NOT_FOUND.getErrorCode());
        }
        return signatureRequestMapper.toSignatureRequestDtoList(signatureRequest);
    }

    @Override
    public SignatureResponseDto getSignatureResponseDtoByOrderId(UUID orderId) {
        OrderEntity order = orderEntityService.findEntityById(orderId)
                .orElseThrow(() -> new BadRequestException(
                        ErrorEnum.NOT_FOUND.getMessage() + " Không tìm thấy đơn hàng với ID này:  " + orderId,
                        ErrorEnum.NOT_FOUND.getErrorCode()
                ));

        SignatureRequestEntity signatureRequest = signatureRequestEntityService.findByOrderEntity(order);

        if (signatureRequest == null) {
            throw new BadRequestException(
                    ErrorEnum.NOT_FOUND.getMessage() + " Không tìm thấy hợp đồng có chữ ký với đơn hàng ID này:  " + orderId,
                    ErrorEnum.NOT_FOUND.getErrorCode()
            );
        }

        return signatureRequestMapper.toSignatureResponseDto(signatureRequest);
    }

    @Override
    public SignatureResponseDto getSignatureResponseById(UUID signatureRequestId) {
        SignatureRequestEntity signatureRequest = signatureRequestEntityService.findEntityById(signatureRequestId)
                .orElseThrow(() -> new BadRequestException(
                        ErrorEnum.NOT_FOUND.getMessage() + " Không tìm thấy hợp đồng có chữ ký với ID này:  " + signatureRequestId,
                        ErrorEnum.NOT_FOUND.getErrorCode()
                ));

        return signatureRequestMapper.toSignatureResponseDto(signatureRequest);
    }

    @Override
    public byte[] generateKeyPairForCustomer(UUID customerId, String customerName) {
        try {
            // 1️⃣ Tạo cặp khóa RSA
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            keyGen.initialize(2048);
            KeyPair keyPair = keyGen.generateKeyPair();

            PrivateKey privateKey = keyPair.getPrivate();
            PublicKey publicKey = keyPair.getPublic();

            // 2️⃣ Tạo self-signed certificate từ public key
            X500Name issuer = new X500Name("CN=" + customerName);
            BigInteger serial = BigInteger.valueOf(System.currentTimeMillis());
            Date notBefore = new Date();
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.YEAR, 5);
            Date notAfter = cal.getTime();

            ContentSigner signer = new JcaContentSignerBuilder("SHA256withRSA")
                    .build(privateKey);

            X509v3CertificateBuilder certBuilder = new JcaX509v3CertificateBuilder(
                    issuer, serial, notBefore, notAfter, issuer, publicKey);

            X509Certificate cert = new JcaX509CertificateConverter()
                    .setProvider(new BouncyCastleProvider())
                    .getCertificate(certBuilder.build(signer));

            // 3️⃣ Lưu public cert vào CustomerEntity
            CustomerEntity customer = customerEntityService.findEntityById(customerId)
                    .orElseThrow(() -> new RuntimeException("Customer not found"));
            customer.setPublicCertificate(cert.getEncoded());
            customerEntityService.save(customer);

            // 4️⃣ Tạo file .p12 (PKCS12) chứa cả private key + cert để khách hàng tải về
            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            keyStore.load(null, null);
            keyStore.setKeyEntry("customer-key", privateKey, "password".toCharArray(),
                    new Certificate[]{cert});

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            keyStore.store(baos, "password".toCharArray());

            return baos.toByteArray(); // byte[] file .p12 để trả về cho FE tải
        } catch (Exception e) {
            throw new RuntimeException("Generate key pair failed", e);
        }
    }

}
