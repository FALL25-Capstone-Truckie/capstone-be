package capstone_project.dtos.response.route;

import java.util.List;

public record RoutePointsResponse(
        List<RoutePointResponse> points
) {}
