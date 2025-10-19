package capstone_project.dtos.response.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

/**
 * DTO for WebSocket order status change notifications
 * Sent to frontend when order status changes
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderStatusChangeMessage {
    /**
     * Order ID
     */
    private UUID orderId;
    
    /**
     * Order code (e.g., "ORD-001")
     */
    private String orderCode;
    
    /**
     * Previous order status
     */
    private String previousStatus;
    
    /**
     * New order status
     */
    private String newStatus;
    
    /**
     * Timestamp when status changed
     */
    private Instant timestamp;
    
    /**
     * Human-readable message for the status change
     */
    private String message;
}
