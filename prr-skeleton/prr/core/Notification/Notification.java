package prr.core.Notification;

public class Notification {
    NotificationType _type;

    public Notification(NotificationType type) {
        _type = type;
    }

    public String toString(){
        return _type.toString();
    }
}
