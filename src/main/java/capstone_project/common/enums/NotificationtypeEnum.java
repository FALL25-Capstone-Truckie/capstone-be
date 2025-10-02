package capstone_project.common.enums;

public enum NotificationtypeEnum {
    // Vehicle related notifications
    VEHICLE_CREATED,
    VEHICLE_UPDATED,
    VEHICLE_DELETED,
    MAINTENANCE_CREATED,
    MAINTENANCE_UPDATED,
    MAINTENANCE_DELETED,
    VEHICLE_ASSIGNMENT_CREATED,

    // Order related notifications
    ORDER_CREATED,
    ORDER_UPDATED,
    ORDER_COMPLETED,
    ORDER_CANCELLED,

    // User related notifications
    USER_REGISTERED,
    USER_UPDATED,
    PASSWORD_RESET,

    // Chat related notifications
    MESSAGE_RECEIVED,

    // Contract related notifications
    CONTRACT_CREATED,
    CONTRACT_UPDATED,
    CONTRACT_EXPIRED,

    // System notifications
    SYSTEM_ALERT,
    SYSTEM_MAINTENANCE

}
