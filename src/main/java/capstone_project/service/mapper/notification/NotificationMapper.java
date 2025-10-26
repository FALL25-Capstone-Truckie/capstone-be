package capstone_project.service.mapper.notification;



import capstone_project.dtos.request.notification.NotificationRequest;
import capstone_project.entity.notification.NotificationEntity;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface NotificationMapper {

    @Mapping(target = "id", ignore = true)
    NotificationEntity toEntity(NotificationRequest req);


}

