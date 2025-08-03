package capstone_project.controller.user;

import capstone_project.dtos.request.auth.LoginWithoutEmailRequest;
import capstone_project.dtos.request.user.AddressRequest;
import capstone_project.dtos.response.auth.LoginResponse;
import capstone_project.dtos.response.common.ApiResponse;
import capstone_project.dtos.response.user.AddressResponse;
import capstone_project.service.services.user.AddressService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("${address.api.base-path}")
@RequiredArgsConstructor
public class AddressController {
    private final AddressService addressService;

    @PostMapping("")
    public ResponseEntity<ApiResponse<AddressResponse>> createAddress(@RequestBody @Valid AddressRequest addressRequest) {
        final var login = addressService.createAddress(addressRequest);
        return ResponseEntity.ok(ApiResponse.ok(login));
    }

    @GetMapping("")
    public ResponseEntity<ApiResponse<List<AddressResponse>>> getAllAddresses() {
        final var addresses = addressService.getAllAddresses();
        return ResponseEntity.ok(ApiResponse.ok(addresses));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AddressResponse>> getAddressById(@PathVariable UUID id) {
        final var address = addressService.getAddressById(id);
        return ResponseEntity.ok(ApiResponse.ok(address));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<AddressResponse>> updateAddress(
            @PathVariable UUID id,
            @RequestBody @Valid AddressRequest addressRequest) {
        final var updatedAddress = addressService.updateAddress(id, addressRequest);
        return ResponseEntity.ok(ApiResponse.ok(updatedAddress));
    }
}
