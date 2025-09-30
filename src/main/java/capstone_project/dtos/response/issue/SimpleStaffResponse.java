package capstone_project.dtos.response.issue;

import java.util.UUID;

/**
 * A simplified DTO representing essential staff information
 * containing only id, name, and phone.
 */
public record SimpleStaffResponse(
    UUID id,
    String name,
    String phone
) {
}
