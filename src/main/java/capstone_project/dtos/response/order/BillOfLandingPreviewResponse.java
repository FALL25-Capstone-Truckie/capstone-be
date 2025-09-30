// Java
package capstone_project.dtos.response.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BillOfLandingPreviewResponse {
    private String fileName;
    private String base64Content;
    private String mimeType;
}