package capstone_project.service.mapper.order;

import capstone_project.dtos.request.order.SignatureRequestDto;
import capstone_project.dtos.response.order.SignatureResponseDto;
import capstone_project.entity.order.conformation.SignatureRequestEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SignatureRequestMapper {
    SignatureResponseDto toSignatureResponseDto (final SignatureRequestEntity signatureRequestDto);

    List<SignatureResponseDto> toSignatureRequestDtoList (final List<SignatureRequestEntity> signatureRequestEntityList);
}
