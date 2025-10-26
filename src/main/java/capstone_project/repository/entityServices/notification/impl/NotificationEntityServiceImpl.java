package capstone_project.repository.entityServices.notification.impl;

import capstone_project.entity.notification.NotificationEntity;
import capstone_project.repository.entityServices.notification.NotificationEntityService;
import capstone_project.repository.repositories.notification.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NotificationEntityServiceImpl implements NotificationEntityService {

    private final NotificationRepository notificationRepository;

    @Override
    public NotificationEntity save(NotificationEntity entity) {
        return notificationRepository.save(entity);
    }

    @Override
    public Optional<NotificationEntity> findEntityById(UUID uuid) {
        return notificationRepository.findById(uuid);
    }

    @Override
    public List<NotificationEntity> findAll() {
        return notificationRepository.findAll();
    }
}
