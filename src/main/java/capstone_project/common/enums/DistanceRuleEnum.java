package capstone_project.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum DistanceRuleEnum {
    TIER_1(0, 4),
    TIER_2(5, 15),
    TIER_3(16, 100),
    TIER_4(101, 99999999);

    private final Integer fromKm;
    private final Integer toKm;

    public static DistanceRuleEnum fromDistance(int distance) {
        for (DistanceRuleEnum tier : values()) {
            if (distance >= tier.getFromKm() && distance <= tier.getToKm()) {
                return tier;
            }
        }
        throw new IllegalArgumentException("No pricing tier found for distance: " + distance);
    }
}

