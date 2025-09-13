package capstone_project.service.services.order.order;

import capstone_project.dtos.request.order.SignatureRequestDto;
import capstone_project.dtos.response.order.SignatureResponseDto;

import java.io.IOException;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.util.List;
import java.util.UUID;

public interface SignatureRequestService {
    boolean verifySignedPdf(byte[] signedPdf, SignatureRequestDto requestDto);

    List<SignatureResponseDto> getSignatureResponseDtoByUserId(UUID userId);

    SignatureResponseDto getSignatureResponseDtoByOrderId(UUID orderId);

    SignatureResponseDto getSignatureResponseById(UUID signatureRequestId);

    byte[] generateKeyPairForCustomer(UUID customerId, String customerName);
}
