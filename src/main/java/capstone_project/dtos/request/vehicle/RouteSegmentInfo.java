package capstone_project.dtos.request.vehicle;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.math.BigDecimal;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record RouteSegmentInfo(
        Integer segmentOrder,
        String startPointName,
        String endPointName,
        BigDecimal startLatitude,
        BigDecimal startLongitude,
        BigDecimal endLatitude,
        BigDecimal endLongitude,
        BigDecimal distanceMeters,
        List<List<BigDecimal>> pathCoordinates,
        BigDecimal estimatedTollFee,
        List<TollDetail> tollDetails
) {}