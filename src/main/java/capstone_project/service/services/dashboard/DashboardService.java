package capstone_project.service.services.dashboard;

import capstone_project.dtos.response.dashboard.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface DashboardService {
    int countAllOrder();

    int countOrderEntitiesBySenderId(UUID senderId);

    int countOrderEntitiesBySenderCompanyNameContainingIgnoreCase(String senderCompanyName);

    int countOrderEntitiesByReceiverNameContainingIgnoreCase(String receiverName);

    List<MonthlyOrderCount> countTotalOrderByMonthOverYear(int year);

    Map<String, Long> countAllByOrderStatus();

    Map<String, Long> countByOrderStatus(String status);

    Map<String, Long> countOrderByWeek(int amount);

    Map<String, Long> countOrderByYear(int amount);

    Map<String, Long> countAllByUserStatus();

    Map<String, Long> countUsersByRole();

    Integer countAllUsers();

    List<MonthlyNewCustomerCountResponse> newCustomerByMonthOverYear(int year);

    List<CustomerGrowthRateByYearResponse> getCustomerGrowthRateByYear(int year);

    List<TopSenderResponse> topSenderByMonthAndYear(Integer month, Integer year, int amount);

    List<TopDriverResponse> topDriverByMonthAndYear(Integer month, Integer year, int amount);

    OnTImeVSLateDeliveriesResponse getOnTimeVsLateDeliveriesWithPercentage(Integer month, Integer year);

    List<OnTimeDeliveriesDriverResponse> topOnTimeDeliveriesByDriversWithPercentage(Integer month, Integer year, int amount);

    List<LateDeliveriesDriverResponse> topLateDeliveriesByDriversWithPercentage(Integer month, Integer year, int amount);

    BigDecimal getTotalRevenueInYear();

    Map<Integer, Long> getTotalRevenueCompareYear();

    Map<Integer, Long> getTotalRevenueByMonth();

    Map<Integer, Long> getTotalRevenueByLast4Weeks();

    List<TopPayCustomerResponse> getTopCustomersByRevenue(int amount);

//    List<MonthlyRevenue> totalRevenueByMonthOverYear(int year);


}
