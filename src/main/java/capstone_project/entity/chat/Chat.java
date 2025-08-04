package capstone_project.entity.chat;

import com.google.cloud.firestore.annotation.DocumentId;
import com.google.cloud.firestore.annotation.ServerTimestamp;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Chat {
    @DocumentId
    private String chatId;
    private String roomId;
    private String senderId;
    private String content;
    private String type;
    private String status;
    @ServerTimestamp
    private Timestamp createdAt;
}
