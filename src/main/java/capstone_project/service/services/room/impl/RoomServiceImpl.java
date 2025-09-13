package capstone_project.service.services.room.impl;

import capstone_project.common.enums.ErrorEnum;
import capstone_project.common.enums.OrderStatusEnum;
import capstone_project.common.enums.RoomEnum;
import capstone_project.common.exceptions.dto.BadRequestException;
import capstone_project.dtos.request.chat.CreateRoomRequest;
import capstone_project.dtos.response.chat.CreateRoomResponse;
import capstone_project.entity.auth.UserEntity;
import capstone_project.entity.chat.ParticipantInfo;
import capstone_project.entity.chat.RoomEntity;
import capstone_project.entity.order.order.OrderEntity;
import capstone_project.repository.entityServices.auth.UserEntityService;
import capstone_project.repository.entityServices.order.order.OrderEntityService;
import capstone_project.service.entityServices.auth.UserEntityService;
import capstone_project.service.entityServices.order.order.OrderEntityService;
import capstone_project.service.mapper.room.RoomMapper;
import capstone_project.service.services.redis.RedisService;
import capstone_project.service.services.room.RoomService;
import capstone_project.service.services.service.RedisService;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {
    private final Firestore firestore;
    private final RoomMapper roomMapper;
    private final UserEntityService userEntityService;
    private final OrderEntityService orderEntityService;
    private final RedisService redisService;
    private static final String ROOM_CHAT_ALL_CACHE_KEY = "rooms:active:user:";

    @Override
    @Transactional
    public CreateRoomResponse createRoom(CreateRoomRequest request) {
        if(request == null){
            log.warn("Input not found");
            throw new BadRequestException(
                    ErrorEnum.NOT_FOUND.getMessage(),
                    ErrorEnum.NOT_FOUND.getErrorCode()
            );
        }
        Optional<OrderEntity> getOrder = orderEntityService.findById(UUID.fromString(request.orderId()));
        if(getOrder.isEmpty() || !getOrder.get().getStatus().equals(OrderStatusEnum.SEALED_COMPLETED.name())){
            log.warn("Order not found or Order's status is not SEALED_COMPLETED");
            throw new BadRequestException(
                    ErrorEnum.NOT_FOUND.getMessage(),
                    ErrorEnum.NOT_FOUND.getErrorCode()
            );
        }


        List<UUID> uuidList = request.userIds().stream()
                .map(UUID::fromString).toList();
        Map<UUID, UserEntity> userMap = userEntityService.getAllUsersByIds(uuidList)
                .stream().collect(Collectors.toMap(UserEntity::getId, Function.identity()));
        List<ParticipantInfo> participantInfoList = new ArrayList<>();
        for(String userId : request.userIds()) {
            ParticipantInfo participantInfo = new ParticipantInfo();
            participantInfo.setUserId(userId);
            UserEntity user = userMap.get(UUID.fromString(userId));
            if (user != null) {
                participantInfo.setRoleName(user.getRole().getRoleName());
            }
            redisService.delete(ROOM_CHAT_ALL_CACHE_KEY + userId);
            participantInfoList.add(participantInfo);
        }

        CollectionReference rooms = firestore.collection("Rooms");
        DocumentReference roomDoc = rooms.document();
        RoomEntity room = RoomEntity.builder()
                .roomId(roomDoc.getId())
                .orderId(request.orderId())
                .status(RoomEnum.ACTIVE.name())
                .type(RoomEnum.PRIVATE.name())
                .participants(participantInfoList)
                .build();
        try {
            roomDoc.set(room);
            log.info("Room created with id: {}", room.getRoomId());
        } catch (Exception e) {
            throw new RuntimeException("Failed to create room in Firestore", e);
        }
        return roomMapper.toCreateRoomResponse(room);
    }

    @Override
    public boolean cancelRoomByOrderId(UUID orderId) {
        if(orderId == null){
            log.warn("orderId not found");
            throw new BadRequestException(
                    ErrorEnum.NOT_FOUND.getMessage(),
                    ErrorEnum.NOT_FOUND.getErrorCode()
            );
        }
        try {
            CollectionReference rooms = firestore.collection("Rooms");
            Query query = rooms.whereEqualTo("orderId", orderId.toString());
            ApiFuture<QuerySnapshot> querySnapshot = query.get();
            List<QueryDocumentSnapshot> documents = querySnapshot.get().getDocuments();
            if (documents.isEmpty()) {
                throw new BadRequestException(
                        ErrorEnum.NOT_FOUND.getMessage(),
                        ErrorEnum.NOT_FOUND.getErrorCode()
                );
            }
            for (QueryDocumentSnapshot doc : documents) {
                doc.getReference().update("status", RoomEnum.UN_ACTIVE.name());
                log.info("Room [{}] status set to UN_ACTIVE", doc.getId());
            }
            return true;

        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch room by orderId from Firestore", e);
        }
    }

    @Override
    public List<CreateRoomResponse> listRoomActiveByUserId(UUID userId) {

        if(userId == null){
            log.warn("userId not found");
            throw new BadRequestException(
                    ErrorEnum.NOT_FOUND.getMessage(),
                    ErrorEnum.NOT_FOUND.getErrorCode()
            );
        }
        List<RoomEntity> cached = redisService.getList(ROOM_CHAT_ALL_CACHE_KEY + userId, RoomEntity.class);
        List<RoomEntity> result = new ArrayList<>();
        if (cached != null) {
            log.info("Returned rooms from Redis cache for userId: {}", userId);
            result = cached;
            return roomMapper.toCreateRoomResponseList(result);
        }
        try {
            CollectionReference rooms = firestore.collection("Rooms");
            Query query = rooms.whereEqualTo("status", RoomEnum.ACTIVE.name());
            ApiFuture<QuerySnapshot> querySnapshot = query.get();
            List<QueryDocumentSnapshot> documents = querySnapshot.get().getDocuments();

            for (QueryDocumentSnapshot doc : documents) {
                RoomEntity room = doc.toObject(RoomEntity.class);
                boolean hasUser = room.getParticipants().stream()
                        .anyMatch(p -> p.getUserId().equals(userId.toString()));
                if (hasUser) {
                    result.add(room);
                }
            }
            log.info("Room list to be cached for {}: {}", userId, result.size());
            redisService.save(ROOM_CHAT_ALL_CACHE_KEY + userId, result);
            return roomMapper.toCreateRoomResponseList(result);
        }catch (Exception e){
            throw new RuntimeException("Failed to fetch room by orderId from Firestore", e);
        }
    }

    @Override
    public boolean activeRoomByOrderId(UUID orderId) {
        if(orderId == null){
            log.warn("orderId not found");
            throw new BadRequestException(
                    ErrorEnum.NOT_FOUND.getMessage(),
                    ErrorEnum.NOT_FOUND.getErrorCode()
            );
        }
        try {
            CollectionReference rooms = firestore.collection("Rooms");
            Query query = rooms.whereEqualTo("orderId", orderId.toString());
            ApiFuture<QuerySnapshot> querySnapshot = query.get();
            List<QueryDocumentSnapshot> documents = querySnapshot.get().getDocuments();
            if (documents.isEmpty()) {
                throw new BadRequestException(
                        ErrorEnum.NOT_FOUND.getMessage(),
                        ErrorEnum.NOT_FOUND.getErrorCode()
                );
            }
            for (QueryDocumentSnapshot doc : documents) {
                doc.getReference().update("status", RoomEnum.ACTIVE.name());
                log.info("Room [{}] status set to ACTIVE", doc.getId());
            }
            return true;

        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch room by orderId from Firestore", e);
        }
    }

    @Override
    public boolean deleteRoomByOrderId(UUID orderId) {
        if (orderId == null) {
            log.warn("orderId not found");
            throw new BadRequestException(
                    ErrorEnum.NOT_FOUND.getMessage(),
                    ErrorEnum.NOT_FOUND.getErrorCode()
            );
        }

        Optional<OrderEntity> getOrder = orderEntityService.findById(orderId);
        if (getOrder.isEmpty() ||
                !(getOrder.get().getStatus().equals(OrderStatusEnum.SUCCESSFUL.name()) ||
                        getOrder.get().getStatus().equals(OrderStatusEnum.RETURNED.name()))) {
            log.warn("Order not found or Order's status is not SUCCESSFUL or RETURNED");
            throw new BadRequestException(
                    ErrorEnum.NOT_FOUND.getMessage(),
                    ErrorEnum.NOT_FOUND.getErrorCode()
            );
        }

        try {
            Query query = firestore.collection("Rooms")
                    .whereEqualTo("orderId", orderId.toString());

            ApiFuture<QuerySnapshot> querySnapshotFuture = query.get();
            QuerySnapshot snapshot = querySnapshotFuture.get();

            if (snapshot.isEmpty()) {
                log.info("No rooms found with orderId: {}", orderId);
                return false;
            }

            int deletedCount = 0;

            for (DocumentSnapshot document : snapshot.getDocuments()) {
                RoomEntity room = document.toObject(RoomEntity.class);
                if (room == null) continue;

                if (room.getCreatedAt() != null &&
                        Duration.between(room.getCreatedAt(), Instant.now()).toDays() > 30) {

                    firestore.collection("Rooms")
                            .document(document.getId())
                            .delete();

                    deletedCount++;
                }
            }

            log.info("Deleted {} room(s) with orderId: {}", deletedCount, orderId);
            return deletedCount > 0;

        } catch (Exception e) {
            log.error("Error while deleting rooms by orderId: {}", orderId, e);
            throw new RuntimeException("Error deleting room(s): " + e.getMessage());
        }
    }



}
