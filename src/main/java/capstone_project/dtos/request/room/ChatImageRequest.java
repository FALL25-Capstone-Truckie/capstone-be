package capstone_project.dtos.request.room;

import org.springframework.web.multipart.MultipartFile;

public record ChatImageRequest(
        MultipartFile file, String senderId, String roomId
) {
}
