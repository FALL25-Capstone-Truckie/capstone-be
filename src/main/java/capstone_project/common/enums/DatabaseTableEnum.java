package capstone_project.common.enums;

public enum DatabaseTableEnum {
    ADDRESSES,
    BASING_PRICES,
    CAMERA_TRACKINGS,
    CATEGORIES,
    CATEGORY_PRICING_DETAIL,
    CONTRACT_RULE_ORDER_DETAIL,
    CONTRACTS,
    CONTRACT_RULES,
    CONTRACT_SETTINGS,
    
    // Order related tables
    ORDERS,
    ORDER_DETAILS,
    ORDER_SIZES,
    ORDER_SEALS,
    ORDER_DETAIL_SEALS,
    BILL_OF_LADINGS,
    PHOTO_COMPLETIONS,
    
    // Vehicle related tables
    VEHICLES,
    VEHICLE_ASSIGNMENTS,
    VEHICLE_MAINTENANCES,
    VEHICLE_TYPES,
    VEHICLE_RULES,
    MAINTENANCE_TYPES,
    
    // Chat related tables
    CHAT,
    CHAT_MESSAGE,
    CHAT_ROOM,
    CHAT_ROOM_USER,
    
    // User related tables
    USERS,
    CUSTOMERS,
    DRIVERS,
    MANAGERS,
    ROLE,
    
    // Device related tables
    DEVICES,
    DEVICE_TYPES,
    
    // Issue related tables
    ISSUES,
    ISSUE_TYPES,
    ISSUE_IMAGES,
    
    // Distance related tables
    DISTANCES,
    DISTANCE_RULES,
    
    // Penalty related tables
    PENALTIES,
    
    // Seal related tables
    SEALS,
    
    // Journey related tables
    JOURNEY_HISTORIES,
    
    // Settings related tables
    SYSTEM_SETTINGS,
    WEIGHT_UNIT_SETTINGS,
    
    // Transaction related tables
    PAYOS_TRANSACTIONS,
    STRIPE_TRANSACTIONS,
    
    // Notification related tables
    NOTIFICATIONS
}
