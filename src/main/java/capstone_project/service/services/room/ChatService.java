package capstone_project.service.services.room;

import capstone_project.dtos.request.room.ChatImageRequest;
import capstone_project.dtos.request.room.MessageRequest;
import capstone_project.dtos.response.room.ChatPageResponse;
import capstone_project.dtos.response.room.ChatResponseDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public interface ChatService {
    CompletableFuture<ChatResponseDTO> saveMessage(MessageRequest messageRequest);

    ChatPageResponse getMessagesByRoomId(String roomId, int pageSize, String lastMessageId)
            throws ExecutionException, InterruptedException;

    ChatPageResponse getMessagesForRoomSupportForCusByUserId(UUID userId, int pageSize, String lastMessageId) throws ExecutionException, InterruptedException;

    String uploadChatImage(ChatImageRequest chatImageRequest) throws IOException;
}
