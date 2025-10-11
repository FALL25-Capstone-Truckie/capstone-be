package capstone_project.dtos.response.dashboard;

public record CustomerGrowthRateByYearResponse(
        int year,
        int month,
        int newUsers,
        int cumulativeUsers,
        double growthRate
) {
}
