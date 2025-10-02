package capstone_project.dtos.response.route;

import java.util.List;

public record SuggestRouteResponse(
        List<RouteSegmentResponse> segments,
        long totalTollAmount,
        int totalTollCount,
        double totalDistance  // in kilometers
) {}
