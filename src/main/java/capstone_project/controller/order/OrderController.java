//package capstone_project.controller.order;
//
//import capstone_project.dtos.response.common.ApiResponse;
//import capstone_project.dtos.response.pricing.PricingRuleResponse;
//import capstone_project.service.services.order.order.OrderService;
//import capstone_project.service.services.pricing.PricingRuleService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("${order.api.base-path}")
//@RequiredArgsConstructor
//@PreAuthorize("isAuthenticated()")
//public class OrderController {
//
//    private final OrderService orderService;
//
//    @GetMapping()
//    public ResponseEntity<ApiResponse<List<PricingRuleResponse>>> getAllPricingRules() {
//        final var result = orderService.getAllPricingRules();
//        return ResponseEntity.ok(ApiResponse.ok(result));
//    }
//}
