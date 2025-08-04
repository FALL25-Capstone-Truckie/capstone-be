package capstone_project.entity.chat;

import com.google.cloud.firestore.annotation.DocumentId;
import com.google.cloud.firestore.annotation.ServerTimestamp;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Room {
    @DocumentId
    private String roomId;
    private String orderId;
    private List<String> participants;
    private String type;
    private String status;
    @ServerTimestamp
    private Timestamp createdAt;

}
