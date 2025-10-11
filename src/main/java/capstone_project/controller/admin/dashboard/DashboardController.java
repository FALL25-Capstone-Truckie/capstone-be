package capstone_project.controller.admin.dashboard;

import capstone_project.dtos.response.common.ApiResponse;
import capstone_project.dtos.response.dashboard.*;
import capstone_project.service.services.dashboard.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("${dashboard.api.base-path}")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ADMIN')")
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/count-all-orders")
    public ResponseEntity<ApiResponse<Integer>> countAllOrder() {
        final var result = dashboardService.countAllOrder();
        return ResponseEntity.ok(ApiResponse.ok(result));
    }

    @GetMapping("/count-order-by/{senderId}")
    public ResponseEntity<ApiResponse<Integer>> countOrderEntitiesBySenderId(@PathVariable UUID senderId) {
        final var result = dashboardService.countOrderEntitiesBySenderId(senderId);
        return ResponseEntity.ok(ApiResponse.ok(result));
    }

    @GetMapping("/count-order-by-sender")
    public ResponseEntity<ApiResponse<Integer>> countOrderEntitiesBySenderCompanyNameContainingIgnoreCase(@RequestParam String senderCompanyName) {
        final var result = dashboardService.countOrderEntitiesBySenderCompanyNameContainingIgnoreCase(senderCompanyName);
        return ResponseEntity.ok(ApiResponse.ok(result));
    }

    @GetMapping("/count-order-by-receiver")
    public ResponseEntity<ApiResponse<Integer>> countOrderEntitiesByReceiverNameContainingIgnoreCase(@RequestParam String receiverName) {
        final var result = dashboardService.countOrderEntitiesByReceiverNameContainingIgnoreCase(receiverName);
        return ResponseEntity.ok(ApiResponse.ok(result));
    }

    @GetMapping("/count-order-by-month-over-year")
    public ResponseEntity<ApiResponse<List<MonthlyOrderCount>>> countTotalOrderByMonthOverYear(@RequestParam int year) {
        final var result = dashboardService.countTotalOrderByMonthOverYear(year);
        return ResponseEntity.ok(ApiResponse.ok(result));
    }

    @GetMapping("/count-order-all-by-status")
    public ResponseEntity<ApiResponse<Map<String, Long>>> countAllByOrderStatus() {
        final var result = dashboardService.countAllByOrderStatus();
        return ResponseEntity.ok(ApiResponse.ok(result));
    }

    @GetMapping("/count-by-week")
    public ResponseEntity<ApiResponse<Map<String, Long>>> countOrderByWeek(@RequestParam int amount) {
        final var result = dashboardService.countOrderByWeek(amount);
        return ResponseEntity.ok(ApiResponse.ok(result));
    }

    @GetMapping("/count-by-year")
    public ResponseEntity<ApiResponse<Map<String, Long>>> countAllByUserStatus(@RequestParam int amount) {
        final var result = dashboardService.countOrderByYear(amount);
        return ResponseEntity.ok(ApiResponse.ok(result));
    }

    @GetMapping("/count-all-users-over-status")
    public ResponseEntity<ApiResponse<Map<String, Long>>> countAllByUserStatus() {
        final var result = dashboardService.countAllByUserStatus();
        return ResponseEntity.ok(ApiResponse.ok(result));
    }

    @GetMapping("/count-users-by-role")
    public ResponseEntity<ApiResponse<Map<String, Long>>> countUsersByRole() {
        final var result = dashboardService.countUsersByRole();
        return ResponseEntity.ok(ApiResponse.ok(result));
    }

    @GetMapping("/new-customer-by-month-over-year")
    public ResponseEntity<ApiResponse<List<MonthlyNewCustomerCountResponse>>> newCustomerByMonthOverYear(@RequestParam int year) {
        final var result = dashboardService.newCustomerByMonthOverYear(year);
        return ResponseEntity.ok(ApiResponse.ok(result));
    }

    @GetMapping("/customer-growth-rate-over-year")
    public ResponseEntity<ApiResponse<List<CustomerGrowthRateByYearResponse>>> getCustomerGrowthRateByYear(@RequestParam int year) {
        final var result = dashboardService.getCustomerGrowthRateByYear(year);
        return ResponseEntity.ok(ApiResponse.ok(result));
    }

    // xử lý cả đồng hạng nữa nên là có thể trả về nhiều hơn amount
    @GetMapping("/top-sender-by-month-and-year")
    public ResponseEntity<ApiResponse<List<TopSenderResponse>>> topSenderByMonthAndYear(@RequestParam(required = false) Integer month,
                                                                                        @RequestParam(required = false) Integer year,
                                                                                        @RequestParam int amount) {
        final var result = dashboardService.topSenderByMonthAndYear(month, year, amount);
        return ResponseEntity.ok(ApiResponse.ok(result));
    }

    @GetMapping("/top-driver-by-month-and-year")
    public ResponseEntity<ApiResponse<List<TopDriverResponse>>> topDriverByMonthAndYear(@RequestParam(required = false) Integer month,
                                                                                        @RequestParam(required = false) Integer year,
                                                                                        @RequestParam int amount) {
        final var result = dashboardService.topDriverByMonthAndYear(month, year, amount);
        return ResponseEntity.ok(ApiResponse.ok(result));
    }

    @GetMapping("/on-time-vs-late-deliveries-with-percentage")
    public ResponseEntity<ApiResponse<OnTImeVSLateDeliveriesResponse>> getOnTimeVsLateDeliveriesWithPercentage(@RequestParam(required = false) Integer month,
                                                                                                               @RequestParam(required = false) Integer year) {
        final var result = dashboardService.getOnTimeVsLateDeliveriesWithPercentage(month, year);
        return ResponseEntity.ok(ApiResponse.ok(result));
    }

    @GetMapping("/top-driver-on-time-deliveries-with-percentage")
    public ResponseEntity<ApiResponse<List<OnTimeDeliveriesDriverResponse>>> topOnTimeDeliveriesByDriversWithPercentage(@RequestParam(required = false) Integer month,
                                                                                                                        @RequestParam(required = false) Integer year,
                                                                                                                        @RequestParam int amount) {
        final var result = dashboardService.topOnTimeDeliveriesByDriversWithPercentage(month, year, amount);
        return ResponseEntity.ok(ApiResponse.ok(result));
    }

    @GetMapping("/top-driver-late-deliveries-with-percentage")
    public ResponseEntity<ApiResponse<List<LateDeliveriesDriverResponse>>> topLateDeliveriesByDriversWithPercentage(@RequestParam(required = false) Integer month,
                                                                                                                    @RequestParam(required = false) Integer year,
                                                                                                                    @RequestParam int amount) {
        final var result = dashboardService.topLateDeliveriesByDriversWithPercentage(month, year, amount);
        return ResponseEntity.ok(ApiResponse.ok(result));
    }

    @GetMapping("/total-revenue-in-year")
    public ResponseEntity<ApiResponse<BigDecimal>> getTotalRevenueInYear() {
        final var result = dashboardService.getTotalRevenueInYear();
        return ResponseEntity.ok(ApiResponse.ok(result));
    }

    @GetMapping("/total-revenue-compare-year")
    public ResponseEntity<ApiResponse<Map<Integer, Long>>> getTotalRevenueCompareYear() {
        final var result = dashboardService.getTotalRevenueCompareYear();
        return ResponseEntity.ok(ApiResponse.ok(result));
    }

    @GetMapping("/total-revenue-by-month")
    public ResponseEntity<ApiResponse<Map<Integer, Long>>> getTotalRevenueByMonth() {
        final var result = dashboardService.getTotalRevenueByMonth();
        return ResponseEntity.ok(ApiResponse.ok(result));
    }

    @GetMapping("/total-revenue-by-last-4-weeks")
    public ResponseEntity<ApiResponse<Map<Integer, Long>>> getTotalRevenueByLast4Weeks() {
        final var result = dashboardService.getTotalRevenueByLast4Weeks();
        return ResponseEntity.ok(ApiResponse.ok(result));
    }

    @GetMapping("/top-customers-by-revenue")
    public ResponseEntity<ApiResponse<List<TopPayCustomerResponse>>> getTopCustomersByRevenue(@RequestParam int amount) {
        final var result = dashboardService.getTopCustomersByRevenue(amount);
        return ResponseEntity.ok(ApiResponse.ok(result));
    }

}
