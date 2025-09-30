package capstone_project.entity.order.order;

import capstone_project.entity.common.BaseEntity;
import capstone_project.entity.vehicle.VehicleAssignmentEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "journey_history", schema = "public", catalog = "capstone-project")
@Data
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class JourneyHistoryEntity extends BaseEntity {
    @Column(name = "start_location", precision = 11, scale = 8)
    private BigDecimal startLocation;

    @Column(name = "end_location", precision = 11, scale = 8)
    private BigDecimal endLocation;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @Size(max = 20)
    @Column(name = "status", length = 20)
    private String status;

    @Column(name = "total_distance")
    private BigDecimal totalDistance;

    @Column(name = "is_reported_incident")
    private Boolean isReportedIncident;
    
    @Column(name = "is_route_changed")
    private Boolean isRouteChanged;
    
    @Column(name = "route_change_reason", length = 500)
    private String routeChangeReason;
    
    @Column(name = "original_route_json", length = Integer.MAX_VALUE)
    private String originalRouteJson;
    
    @Column(name = "current_route_json", length = Integer.MAX_VALUE)
    private String currentRouteJson;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "previous_journey_id")
    private JourneyHistoryEntity previousJourney;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_assignment_id")
    private VehicleAssignmentEntity vehicleAssignmentEntity;
}