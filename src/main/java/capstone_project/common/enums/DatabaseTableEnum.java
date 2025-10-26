package capstone_project.common.enums;

public enum DatabaseTableEnum {
    // Table enums
    ADDRESSES("addresses"),
    BASING_PRICES("basing_prices"),
    CAMERA_TRACKINGS("camera_trackings"),
    CATEGORIES("categories"),
    CATEGORY_PRICING_DETAIL("category_pricing_detail"),
    CONTRACT_RULE_ORDER_DETAIL("contract_rule_order_detail"),
    CONTRACTS("contracts"),
    CONTRACT_RULES("contract_rules"),
    CONTRACT_SETTINGS("contract_settings"),

    // Order related tables
    ORDERS("orders"),
    ORDER_DETAILS("order_details"),
    ORDER_SIZES("order_sizes"),
    ORDER_SEALS("order_seals"),
    ORDER_DETAIL_SEALS("order_detail_seals"),
    BILL_OF_LADINGS("bill_of_ladings"),
    PHOTO_COMPLETIONS("photo_completions"),

    // Vehicle related tables
    VEHICLES("vehicles"),
    VEHICLE_ASSIGNMENTS("vehicle_assignments"),
    VEHICLE_MAINTENANCES("vehicle_maintenances"),
    VEHICLE_TYPES("vehicle_types"),
    VEHICLE_RULES("vehicle_rules"),
    MAINTENANCE_TYPES("maintenance_types"),

    // Chat related tables
    CHAT("chat"),
    CHAT_MESSAGE("chat_message"),
    CHAT_ROOM("chat_room"),
    CHAT_ROOM_USER("chat_room_user"),

    // User related tables
    USERS("users"),
    CUSTOMERS("customers"),
    DRIVERS("drivers"),
    MANAGERS("managers"),
    ROLE("role"),

    // Device related tables
    DEVICES("devices"),
    DEVICE_TYPES("device_types"),

    // Issue related tables
    ISSUES("issues"),
    ISSUE_TYPES("issue_types"),
    ISSUE_IMAGES("issue_images"),

    // Distance related tables
    DISTANCES("distances"),
    DISTANCE_RULES("distance_rules"),

    // Penalty related tables
    PENALTIES("penalties"),

    // Seal related tables
    SEALS("seals"),

    // Journey related tables
    JOURNEY_HISTORIES("journey_histories"),

    // Settings related tables
    SYSTEM_SETTINGS("system_settings"),
    WEIGHT_UNIT_SETTINGS("weight_unit_settings"),

    // Transaction related tables
    PAYOS_TRANSACTIONS("payos_transactions"),
    STRIPE_TRANSACTIONS("stripe_transactions"),

    // Notification related tables
    NOTIFICATIONS("notifications");

    private final String type;

    DatabaseTableEnum(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
