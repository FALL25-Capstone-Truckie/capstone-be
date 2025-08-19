package capstone_project.entity.order.contract;

import capstone_project.entity.common.BaseEntity;
import capstone_project.entity.pricing.VehicleRuleEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "contract_rules", schema = "public", catalog = "capstone-project")
@Data
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class ContractRuleEntity extends BaseEntity {

    @Column(name = "num_of_vehicles", length = Integer.MAX_VALUE)
    private Integer numOfVehicles;

    @Column(name = "note")
    @Size(max = 100)
    private String note;

    @Column(name = "status", length = 100)
    private String status;

    @Column(name = "total_value")
    private String info1;

    @Column(name = "info2")
    private String info2;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_rule_id")
    private VehicleRuleEntity vehicleRuleEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contract_id")
    private ContractEntity contractEntity;
}
