package capstone_project.dtos.request.route;

import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

/**
 * Request for suggesting a route.
 * The route will be calculated respecting the exact order of points provided.
 *
 * @param points List of points as [longitude, latitude] pairs
 * @param pointTypes Optional list of point types (e.g., "carrier", "pickup", "delivery") matching points list
 * @param vehicleTypeId Optional vehicle type ID for route calculations
 */
public record SuggestRouteRequest(
        @Size(min = 2, message = "At least two points required")
        List<List<BigDecimal>> points,
        List<String> pointTypes,
        UUID vehicleTypeId
) {
    /**
     * Constructor with required parameters only
     */
    public SuggestRouteRequest(List<List<BigDecimal>> points, UUID vehicleTypeId) {
        this(points, null, vehicleTypeId);
    }
}
