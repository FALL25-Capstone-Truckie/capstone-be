package capstone_project.common.utils;

import capstone_project.dtos.response.order.contract.ContractRuleAssignResponse;
import capstone_project.dtos.response.order.contract.OrderDetailForPackingResponse;
import capstone_project.dtos.response.order.contract.PackedDetailResponse;
import capstone_project.entity.order.order.OrderDetailEntity;
import capstone_project.entity.order.order.OrderSizeEntity;
import capstone_project.entity.pricing.VehicleRuleEntity;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class BinPacker {

    private static final boolean ALLOW_ROTATION = true; // cho phép xoay kiện
    // Choose unit multiplier: if your sizes are in meters use 1000; in cm use 10 to get mm-like ints
    private static final int UNIT_MULTIPLIER = 10;

    public static class BoxItem {

        public final UUID id;
        public final int lx, ly, lz; // length x,y,z as ints (units * UNIT_MULTIPLIER)
        public final long weight; // in grams or unit you choose (convert)
        public final long volume;

        public BoxItem(UUID id, int lx, int ly, int lz, long weight) {
            this.id = id;
            this.lx = lx; this.ly = ly; this.lz = lz; this.weight = weight;
            this.volume = (long) lx * ly * lz;
        }
    }

    public static class Placement {

        int x,y,z;
        int lx,ly,lz;
        public BoxItem box;

        Placement(BoxItem box, int x, int y, int z,
                  int lx, int ly, int lz) {
            this.box = box;
            this.x = x; this.y = y; this.z = z;
            this.lx = lx; this.ly = ly; this.lz = lz;
        }
    }

    // 1 xe
    public static class ContainerState {

        public final VehicleRuleEntity rule;
        final int maxX, maxY, maxZ;
        long currentWeight;
        public List<Placement> placements = new ArrayList<>();
        List<int[]> extremePoints = new ArrayList<>(); // each point [x,y,z]

        public ContainerState(VehicleRuleEntity rule, int maxX, int maxY, int maxZ) {
            this.rule = rule;
            this.maxX = maxX;
            this.maxY = maxY;
            this.maxZ = maxZ;
            this.currentWeight = 0;
            // initial extreme point = origin (0,0,0)
            this.extremePoints.add(new int[]{0,0,0});
        }

        boolean checkWeightAfterAdd(long addWeight) {
            BigDecimal maxW = rule.getMaxWeight() == null ? BigDecimal.valueOf(Long.MAX_VALUE) : rule.getMaxWeight();
            long maxWeightGram = Math.round(maxW.doubleValue() * 1000.0);
            return (currentWeight + addWeight) <= maxWeightGram;
        }

        public void addPlacement(Placement p) {
            placements.add(p);
            currentWeight += p.box.weight;
            // add new extreme points: right, front, top of placed block
            int nx = p.x + p.lx;
            int ny = p.y + p.ly;
            int nz = p.z + p.lz;
            extremePoints.add(new int[]{nx, p.y, p.z});
            extremePoints.add(new int[]{p.x, ny, p.z});
            extremePoints.add(new int[]{p.x, p.y, nz});
            // remove dominated extreme points (simple filter)
            pruneExtremePoints();
        }

        private void pruneExtremePoints() {
            // remove points outside container or duplicates
            Set<String> seen = new HashSet<>();
            List<int[]> out = new ArrayList<>();
            for (int[] p : extremePoints) {
                if (p[0] < 0 || p[1] < 0 || p[2] < 0) continue;
                if (p[0] > maxX || p[1] > maxY || p[2] > maxZ) continue;
                String k = p[0] + ":" + p[1] + ":" + p[2];
                if (!seen.contains(k)) { seen.add(k); out.add(p); }
            }
            extremePoints = out;
        }
    }

    // Generate 6 rotation permutations
    public static List<int[]> rotations(int lx, int ly, int lz) {
        List<int[]> r = new ArrayList<>();
        r.add(new int[]{lx, ly, lz});
        r.add(new int[]{lx, lz, ly});
        r.add(new int[]{ly, lx, lz});
        r.add(new int[]{ly, lz, lx});
        r.add(new int[]{lz, lx, ly});
        r.add(new int[]{lz, ly, lx});
        // remove dupes
        Set<String> seen = new HashSet<>();
        List<int[]> uniq = new ArrayList<>();
        for (int[] a : r) {
            String k = a[0] + ":" + a[1] + ":" + a[2];
            if (!seen.contains(k)) { seen.add(k); uniq.add(a); }
        }
        return uniq;
    }

    // Try place box into container; returns Placement if success
    public static Placement tryPlaceBoxInContainer(BoxItem box, ContainerState container) {
        // iterate extreme points
        for (int[] p : container.extremePoints) {
            int px = p[0], py = p[1], pz = p[2];
            List<int[]> rots = ALLOW_ROTATION ? rotations(box.lx, box.ly, box.lz) : Collections.singletonList(new int[]{box.lx, box.ly, box.lz});
            for (int[] dim : rots) {
                int lx = dim[0], ly = dim[1], lz = dim[2];
                // bounds check
                if (px + lx > container.maxX || py + ly > container.maxY || pz + lz > container.maxZ) continue;
                // collision check with existing placements (AABB)
                boolean collide = false;
                for (Placement ex : container.placements) {
                    if (intersect(px, py, pz, lx, ly, lz, ex.x, ex.y, ex.z, ex.lx, ex.ly, ex.lz)) {
                        collide = true; break;
                    }
                }
                if (collide) continue;
                // weight check
                if (!container.checkWeightAfterAdd(box.weight)) continue;
                return new Placement(box, px, py, pz, lx, ly, lz);
            }
        }
        return null;
    }

    public static boolean intersect(int ax, int ay, int az, int alx, int aly, int alz,
                                     int bx, int by, int bz, int blx, int bly, int blz) {
        boolean xOverlap = ax < bx + blx && bx < ax + alx;
        boolean yOverlap = ay < by + bly && by < ay + aly;
        boolean zOverlap = az < bz + blz && bz < az + alz;
        return xOverlap && yOverlap && zOverlap;
    }

    /**
     * Main packing function.
     * @param details order details list (entities)
     * @param vehicleRules sorted vehicle rules (from small->large or as you prefer)
     * @return List<ContainerState> each corresponds to one used container (vehicle)
     */
    public static List<ContainerState> pack(List<OrderDetailEntity> details, List<VehicleRuleEntity> vehicleRules) {
        // convert OrderDetailEntity -> BoxItem
        List<BoxItem> boxes = new ArrayList<>();
        for (OrderDetailEntity d : details) {
            OrderSizeEntity s = d.getOrderSizeEntity();
            if (s == null) throw new IllegalArgumentException("missing size for detail " + d.getId());
            int lx = convertToInt(s.getMaxLength()); // implement convertToInt
            int ly = convertToInt(s.getMaxWidth());
            int lz = convertToInt(s.getMaxHeight());
            long w = convertWeightToLong(s.getMaxHeight()); // implement
            boxes.add(new BoxItem(d.getId(), lx, ly, lz, w));
        }

        // sort by volume desc
        boxes.sort((a,b) -> {
            int cmp = Long.compare(b.weight, a.weight);
            if (cmp == 0) cmp = Long.compare(b.volume, a.volume);
            return cmp;
        });

        List<ContainerState> used = new ArrayList<>();

        // for each box, try to place to existing containers (try smaller ones first or try by fit)
        for (BoxItem box : boxes) {
            boolean placed = false;
            // prefer containers where it fits and minimal leftover volume increase: simple try order
            for (ContainerState c : used) {
                Placement p = tryPlaceBoxInContainer(box, c);
                if (p != null) {
                    c.addPlacement(p);
                    placed = true;
                    break;
                }
            }
            if (placed) continue;

            // not placed: try open new container of each vehicle rule (try smallest that fits box dims and weight)
            boolean opened = false;
            for (VehicleRuleEntity rule : vehicleRules) {
                int maxX = convertToInt(rule.getMaxLength());
                int maxY = convertToInt(rule.getMaxWidth());
                int maxZ = convertToInt(rule.getMaxHeight());
                // check single-box fits dimensionally
                if (box.lx <= maxX && box.ly <= maxY && box.lz <= maxZ) {
                    // create new container
                    ContainerState c = new ContainerState(rule, maxX, maxY, maxZ);
                    // weight check
                    if (!c.checkWeightAfterAdd(box.weight)) {
                        continue; // this container cannot handle this single box weight
                    }
                    Placement p = tryPlaceBoxInContainer(box, c);
                    if (p != null) {
                        c.addPlacement(p);
                        used.add(c);
                        opened = true; break;
                    }
                }
            }
            if (!opened) {
                // cannot fit into any vehicle type
                throw new RuntimeException("Không có loại xe nào chứa được kiện: " + box.id);
            }
        }

        return used;
    }

    // ----- helper converters -----
    public static int convertToInt(BigDecimal bd) {
        if (bd == null) return 0;
        return (int) Math.ceil(bd.doubleValue() * UNIT_MULTIPLIER);
    }

    public static long convertWeightToLong(BigDecimal w) {
        if (w == null) return 0L;
        // assume weight in kg -> convert to grams
        return Math.round(w.doubleValue() * 1000.0);
    }

    public static ContainerState upgradeContainer(ContainerState current, VehicleRuleEntity upgradedRule) {
        int maxX = convertToInt(upgradedRule.getMaxLength());
        int maxY = convertToInt(upgradedRule.getMaxWidth());
        int maxZ = convertToInt(upgradedRule.getMaxHeight());

        ContainerState upgraded = new ContainerState(upgradedRule, maxX, maxY, maxZ);

        // repack lại toàn bộ các box cũ
        for (BoxItem box : current.placements.stream().map(p -> p.box).toList()) {
            Placement p = tryPlaceBoxInContainer(box, upgraded);
            if (p == null) {
                return null; // upgrade thất bại
            }
            upgraded.addPlacement(p);
        }
        return upgraded;
    }


    // Convert ContainerState -> ContractRuleAssignResponse
//    public static List<ContractRuleAssignResponse> toContractResponses(List<ContainerState> used) {
//        List<ContractRuleAssignResponse> out = new ArrayList<>();
//
//        int vehicleIndex = 0;
//        for (ContainerState c : used) {
//            // Thông tin đơn hàng gán cho xe (basic info)
//            List<OrderDetailForPackingResponse> assigned = c.placements.stream()
//                    .map(pl -> new OrderDetailForPackingResponse(
//                            pl.box.id.toString(),
//                            BigDecimal.valueOf(pl.box.weight).divide(BigDecimal.valueOf(1000)), // grams -> kg
//                            BigDecimal.ZERO, // bạn có thể thay bằng kích thước chuẩn lưu trong DB
//                            "kg",
//                            null
//                    ))
//                    .collect(Collectors.toList());
//
//            // Thông tin packing chi tiết (tọa độ + xoay)
//            List<PackedDetailResponse> packedDetails = c.placements.stream()
//                    .map(pl -> new PackedDetailResponse(
//                            pl.box.id.toString(),
//                            BigDecimal.valueOf(pl.x).divide(BigDecimal.valueOf(UNIT_MULTIPLIER)), // mm->cm/m
//                            BigDecimal.valueOf(pl.y).divide(BigDecimal.valueOf(UNIT_MULTIPLIER)),
//                            BigDecimal.valueOf(pl.z).divide(BigDecimal.valueOf(UNIT_MULTIPLIER)),
//                            BigDecimal.valueOf(pl.lx).divide(BigDecimal.valueOf(UNIT_MULTIPLIER)),
//                            BigDecimal.valueOf(pl.ly).divide(BigDecimal.valueOf(UNIT_MULTIPLIER)),
//                            BigDecimal.valueOf(pl.lz).divide(BigDecimal.valueOf(UNIT_MULTIPLIER)),
//                            pl.lx + "x" + pl.ly + "x" + pl.lz
//                    ))
//                    .collect(Collectors.toList());
//
//            // map vehicleRuleId từ container
//            ContractRuleAssignResponse resp = ContractRuleAssignResponse.builder()
//                    .vehicleIndex(vehicleIndex++)
//                    .vehicleRuleId(c.rule.getId())
//                    .vehicleRuleName(c.rule.getVehicleRuleName())
//                    .currentLoad(BigDecimal.valueOf(c.currentWeight).divide(BigDecimal.valueOf(1000))) // grams->kg
//                    .assignedDetails(assigned)
//                    .packedDetailDetails(packedDetails)
//                    .build();
//
//            out.add(resp);
//        }
//
//        return out;
//    }

    // Convert ContainerState -> ContractRuleAssignResponse
    public static List<ContractRuleAssignResponse> toContractResponses(
            List<ContainerState> used,
            List<OrderDetailEntity> details
    ) {
        // Map để lấy lại dữ liệu chuẩn từ DB
        Map<UUID, OrderDetailEntity> detailMap = details.stream()
                .collect(Collectors.toMap(OrderDetailEntity::getId, Function.identity()));

        List<ContractRuleAssignResponse> out = new ArrayList<>();
        int vehicleIndex = 0;

        for (ContainerState c : used) {
            // 1. assignedDetails = data chuẩn từ DB
            List<OrderDetailForPackingResponse> assigned = c.placements.stream()
                    .map(pl -> {
                        OrderDetailEntity detail = detailMap.get(pl.box.id);
                        return new OrderDetailForPackingResponse(
                                detail.getId().toString(),
                                detail.getWeight(),            // lấy weight chuẩn từ DB
                                detail.getWeightBaseUnit(),    // dữ liệu gốc DB
                                detail.getUnit(),              // ví dụ "kg"
                                detail.getTrackingCode()       // trackingCode gốc
                        );
                    })
                    .collect(Collectors.toList());

            // 2. packedDetailDetails = thông tin packing (tọa độ, orientation)
            List<PackedDetailResponse> packedDetails = c.placements.stream()
                    .map(pl -> new PackedDetailResponse(
                            pl.box.id.toString(),
                            BigDecimal.valueOf(pl.x).divide(BigDecimal.valueOf(UNIT_MULTIPLIER)),
                            BigDecimal.valueOf(pl.y).divide(BigDecimal.valueOf(UNIT_MULTIPLIER)),
                            BigDecimal.valueOf(pl.z).divide(BigDecimal.valueOf(UNIT_MULTIPLIER)),
                            BigDecimal.valueOf(pl.lx).divide(BigDecimal.valueOf(UNIT_MULTIPLIER)),
                            BigDecimal.valueOf(pl.ly).divide(BigDecimal.valueOf(UNIT_MULTIPLIER)),
                            BigDecimal.valueOf(pl.lz).divide(BigDecimal.valueOf(UNIT_MULTIPLIER)),
                            pl.lx + "x" + pl.ly + "x" + pl.lz // orientation text
                    ))
                    .collect(Collectors.toList());

//            BigDecimal currentLoadTon = BigDecimal.valueOf(c.currentWeight)
//                    .divide(BigDecimal.valueOf(1_000_000));

            // 3. Ghép thành ContractRuleAssignResponse
            ContractRuleAssignResponse resp = ContractRuleAssignResponse.builder()
                    .vehicleIndex(vehicleIndex++)
                    .vehicleRuleId(c.rule.getId())
                    .vehicleRuleName(c.rule.getVehicleRuleName())
                    .currentLoad(BigDecimal.valueOf(c.currentWeight).divide(BigDecimal.valueOf(1000))) // grams -> kg
                    .assignedDetails(assigned)
                    .packedDetailDetails(packedDetails)
                    .build();

            out.add(resp);
        }

        return out;
    }


}