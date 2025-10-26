package capstone_project.common.enums;

public enum NotificationStatusEnum {
    READ("Read"),
    UNREAD("Unread");

    private final String type;

    NotificationStatusEnum(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }


}
