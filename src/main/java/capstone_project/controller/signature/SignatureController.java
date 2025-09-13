package capstone_project.controller.signature;

import capstone_project.dtos.request.order.SignatureRequestDto;
import capstone_project.dtos.response.common.ApiResponse;
import capstone_project.dtos.response.order.SignatureResponseDto;
import capstone_project.service.services.order.order.SignatureRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("${signature.api.base-path}")
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
public class SignatureController {
    private final SignatureRequestService signatureRequestService;


    @PostMapping("/verify")
    public ResponseEntity<ApiResponse<Boolean>> verifySignedPdf(
            @RequestPart("file") MultipartFile signedPdfFile,
            @RequestPart("request") SignatureRequestDto requestDto
    ) {
        try {
            byte[] signedPdf = signedPdfFile.getBytes();
            boolean verified = signatureRequestService.verifySignedPdf(signedPdf, requestDto);

            return ResponseEntity.ok(ApiResponse.ok(verified));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.fail("Xác minh chữ ký thất bại + " + e.getMessage(),500));
        }
    }

    @GetMapping("/get-by-user-id/{userId}")
    public ResponseEntity<ApiResponse<List<SignatureResponseDto>>> getByUserId(@PathVariable("userId") UUID id) {
        List<SignatureResponseDto> result = signatureRequestService.getSignatureResponseDtoByUserId(id);
        return ResponseEntity.ok(ApiResponse.ok(result));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<SignatureResponseDto>> getById(@PathVariable("id") UUID id) {
        SignatureResponseDto result = signatureRequestService.getSignatureResponseById(id);
        return ResponseEntity.ok(ApiResponse.ok(result));
    }

    @GetMapping("/get-by-order-id/{orderId}")
    public ResponseEntity<ApiResponse<SignatureResponseDto>> getByOrderId(@PathVariable("orderId") UUID id) {
        SignatureResponseDto result = signatureRequestService.getSignatureResponseDtoByOrderId(id);
        return ResponseEntity.ok(ApiResponse.ok(result));
    }

    @PostMapping("/{customerId}/generate-keys")
    public ResponseEntity<byte[]> generateKeys(@PathVariable UUID customerId,
                                               @RequestParam String name) {
        byte[] p12File = signatureRequestService.generateKeyPairForCustomer(customerId, name);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=customer_keys.p12")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(p12File);
    }


}
