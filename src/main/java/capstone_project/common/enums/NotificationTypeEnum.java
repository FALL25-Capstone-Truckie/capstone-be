package capstone_project.common.enums;

public enum NotificationTypeEnum {
    // Vehicle related notifications
    VEHICLE_CREATED("VEHICLE_CREATED", "New Vehicle Created", "A new vehicle has been added to the system."),
    VEHICLE_UPDATED("VEHICLE_UPDATED", "Vehicle Updated", "Vehicle details have been updated."),
    VEHICLE_DELETED("VEHICLE_DELETED", "Vehicle Deleted", "A vehicle has been removed from the system."),
    MAINTENANCE_CREATED("MAINTENANCE_CREATED", "New Maintenance Created", "A new maintenance task has been scheduled."),
    MAINTENANCE_UPDATED("MAINTENANCE_UPDATED", "Maintenance Updated", "Maintenance details have been updated."),
    MAINTENANCE_DELETED("MAINTENANCE_DELETED", "Maintenance Deleted", "A maintenance task has been deleted."),
    VEHICLE_ASSIGNMENT_CREATED("VEHICLE_ASSIGNMENT_CREATED", "Vehicle Assignment Created", "A vehicle has been assigned to a driver."),

    // Order related notifications
    ORDER_CREATED("ORDER_CREATED", "New Order Created", "A new order has been created."),
    ORDER_UPDATED("ORDER_UPDATED", "Order Updated", "Order details have been updated."),
    ORDER_COMPLETED("ORDER_COMPLETED", "Order Completed", "An order has been successfully completed."),
    ORDER_CANCELLED("ORDER_CANCELLED", "Order Cancelled", "An order has been cancelled."),

    // User related notifications
    USER_REGISTERED("USER_REGISTERED", "User Registered", "A new user has registered."),
    USER_UPDATED("USER_UPDATED", "User Updated", "User details have been updated."),
    PASSWORD_RESET("PASSWORD_RESET", "Password Reset", "A password reset was requested."),

    // Chat related notifications
    MESSAGE_RECEIVED("MESSAGE_RECEIVED", "New Message", "You have received a new message."),

    // Contract related notifications
    CONTRACT_CREATED("CONTRACT_CREATED", "Contract Created", "A new contract has been created."),
    CONTRACT_UPDATED("CONTRACT_UPDATED", "Contract Updated", "Contract details have been updated."),
    CONTRACT_EXPIRED("CONTRACT_EXPIRED", "Contract Expired", "A contract has expired."),

    // System notifications
    SYSTEM_ALERT("SYSTEM_ALERT", "System Alert", "There is an important system alert."),
    SYSTEM_MAINTENANCE("SYSTEM_MAINTENANCE", "System Maintenance", "The system will undergo maintenance.");

    private final String type;
    private final String title;
    private final String message;

    NotificationTypeEnum(String type, String title, String message) {
        this.type = type;
        this.title = title;
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }

    // Optional: reverse lookup by type
    public static NotificationTypeEnum fromType(String type) {
        for (NotificationTypeEnum n : values()) {
            if (n.type.equals(type)) {
                return n;
            }
        }
        throw new IllegalArgumentException("Unknown type: " + type);
    }
}
