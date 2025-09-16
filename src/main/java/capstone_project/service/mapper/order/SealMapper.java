package capstone_project.service.mapper.order;

import capstone_project.dtos.response.order.seal.GetOrderDetailSealResponse;
import capstone_project.dtos.response.order.seal.GetSealResponse;
import capstone_project.entity.order.order.SealEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SealMapper {
    GetSealResponse toGetSealResponse(SealEntity sealEntity);

    SealEntity toSealEntity(GetSealResponse getSealResponse);
}
