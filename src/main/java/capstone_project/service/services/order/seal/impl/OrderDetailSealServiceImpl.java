package capstone_project.service.services.order.seal.impl;

import capstone_project.common.enums.CommonStatusEnum;
import capstone_project.common.enums.ErrorEnum;
import capstone_project.common.exceptions.dto.NotFoundException;
import capstone_project.dtos.request.order.seal.OrderDetailSealRequest;
import capstone_project.dtos.response.order.seal.GetOrderDetailSealResponse;
import capstone_project.dtos.response.order.seal.GetSealFullResponse;
import capstone_project.dtos.response.order.seal.GetSealResponse;
import capstone_project.entity.order.order.OrderDetailEntity;
import capstone_project.entity.order.order.OrderDetailSealEntity;
import capstone_project.repository.entityServices.order.order.OrderDetailEntityService;
import capstone_project.repository.entityServices.order.order.OrderDetailSealEntityService;
import capstone_project.service.mapper.order.OrderDetailSealMapper;
import capstone_project.service.mapper.order.SealMapper;
import capstone_project.service.services.order.seal.OrderDetailSealService;
import capstone_project.service.services.order.seal.SealService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderDetailSealServiceImpl implements OrderDetailSealService {
    private final OrderDetailSealEntityService  orderDetailSealEntityService;
    private final OrderDetailEntityService orderDetailEntityService;
    private final SealService sealService;
    private final OrderDetailSealMapper orderDetailSealMapper;
    private final SealMapper sealMapper;

    @Override
    public GetSealFullResponse assignSealForOrderDetail(OrderDetailSealRequest orderDetailSealRequest) {
        List<OrderDetailSealEntity> orderDetailSealEntities = new ArrayList<>();

        GetSealResponse getSealResponse = sealService.createSeal(orderDetailSealRequest.description());
        for(UUID orderDetailId : orderDetailSealRequest.orderDetailSealIds()){
            Optional<OrderDetailEntity> detail = orderDetailEntityService.findEntityById(orderDetailId);
            if(detail.isEmpty()){
                throw new NotFoundException("Không tìm thấy detail với ID: "+orderDetailId, ErrorEnum.NOT_FOUND.getErrorCode());
            }
            OrderDetailSealEntity orderDetailSealEntity = OrderDetailSealEntity.builder()
                    .description(orderDetailSealRequest.description())
                    .sealDate(orderDetailSealRequest.sealDate())
                    .status(CommonStatusEnum.ACTIVE.name())
                    .seal(sealMapper.toSealEntity(getSealResponse))
                    .orderDetail(detail.get())
                    .build();
            orderDetailSealEntities.add(orderDetailSealEntity);
        }
        orderDetailSealEntityService.saveAll(orderDetailSealEntities);
        return new GetSealFullResponse(
                getSealResponse,
                orderDetailSealMapper.toGetOrderDetailSealResponses(orderDetailSealEntities)
        );

    }
}
