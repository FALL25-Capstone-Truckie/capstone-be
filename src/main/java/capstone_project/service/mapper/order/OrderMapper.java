package capstone_project.service.mapper.order;

import capstone_project.dtos.request.order.OrderRequest;
import capstone_project.dtos.response.order.GetOrderResponse;
import capstone_project.entity.order.order.OrderEntity;
import capstone_project.entity.user.address.AddressEntity;
import capstone_project.entity.user.customer.CustomerEntity;
import org.mapstruct.*;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(source = "deliveryAddress.id", target = "deliveryAddressId")
    @Mapping(source = "pickupAddress.id", target = "pickupAddressId")
    @Mapping(source = "sender.id", target = "senderId")
    GetOrderResponse toOrderResponse(final OrderEntity orderEntity);

    @Mapping(source = "deliveryAddressId", target = "deliveryAddress", qualifiedByName = "deliveryAddressFromId")
    @Mapping(source = "pickupAddressId", target = "pickupAddress", qualifiedByName = "pickupAddressFromId")
    @Mapping(source = "senderId", target = "sender", qualifiedByName = "senderFromId")
    OrderEntity mapRequestToEntity(final OrderRequest orderRequest);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "deliveryAddress", source = "deliveryAddressId", qualifiedByName = "deliveryAddressFromId")
    @Mapping(target = "pickupAddress", source = "pickupAddressId", qualifiedByName = "pickupAddressFromId")
    @Mapping(target = "sender", source = "senderId", qualifiedByName = "senderFromId")
    void toOrderEntity(OrderRequest request, @MappingTarget OrderEntity entity);

    @Named("deliveryAddressFromId")
    default AddressEntity mapDeliveryAddressFromId(String addressId) {
        AddressEntity entity = new AddressEntity();
        entity.setId(UUID.fromString(addressId));
        return entity;
    }

    @Named("pickupAddressFromId")
    default AddressEntity mapPickupAddressFromId(String addressId) {
        AddressEntity entity = new AddressEntity();
        entity.setId(UUID.fromString(addressId));
        return entity;
    }

    @Named("senderFromId")
    default CustomerEntity mapSenderFromId(String senderId) {
        CustomerEntity entity = new CustomerEntity();
        entity.setId(UUID.fromString(senderId));
        return entity;
    }

}
