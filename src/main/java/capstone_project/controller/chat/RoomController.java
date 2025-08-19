package capstone_project.controller.chat;

import capstone_project.dtos.request.chat.CreateRoomRequest;
import capstone_project.dtos.response.chat.CreateRoomResponse;
import capstone_project.dtos.response.common.ApiResponse;
import capstone_project.service.services.room.RoomService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("${room.api.base-path}")
@RequiredArgsConstructor
public class RoomController {
    private final RoomService roomService;

    @PostMapping("")
    public ResponseEntity<ApiResponse<CreateRoomResponse>> createRoom(@RequestBody @Valid CreateRoomRequest createRoomRequest) {
        final var createRoom = roomService.createRoom(createRoomRequest);
        return ResponseEntity.ok(ApiResponse.ok(createRoom));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<List<CreateRoomResponse>>> getAllRoomsByUserId(@PathVariable @Valid UUID userId) {
        final var getAllRoomsByUserId = roomService.listRoomActiveByUserId(userId);
        return ResponseEntity.ok(ApiResponse.ok(getAllRoomsByUserId));
    }

    @PutMapping("active/{orderId}")
    public ResponseEntity<ApiResponse<Boolean>> activeRoomsByOrderId(@PathVariable @Valid UUID orderId) {
        final var activeRoomsByOrderId = roomService.activeRoomByOrderId(orderId);
        return ResponseEntity.ok(ApiResponse.ok(activeRoomsByOrderId));
    }

    @PutMapping("in-active/{orderId}")
    public ResponseEntity<ApiResponse<Boolean>> inactiveRoomsByOrderId(@PathVariable @Valid UUID orderId) {
        final var inactiveRoomsByOrderId = roomService.cancelRoomByOrderId(orderId);
        return ResponseEntity.ok(ApiResponse.ok(inactiveRoomsByOrderId));
    }

    @DeleteMapping("{orderId}")
    public ResponseEntity<ApiResponse<Boolean>> deleteRoomsByOrderId(@PathVariable @Valid UUID orderId) {
        final var deleteRooms = roomService.deleteRoomByOrderId(orderId);
        return ResponseEntity.ok(ApiResponse.ok(deleteRooms));
    }
}
