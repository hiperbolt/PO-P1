package prr.core.notification;

import java.io.Serializable;

public class Notification implements Serializable{
    private static final long serialVersionUID = 202208091753L;
    private NotificationType _type;

    public Notification(NotificationType type) {
        _type = type;
    }

    public String toString(){
        return _type.toString();
    }
}
