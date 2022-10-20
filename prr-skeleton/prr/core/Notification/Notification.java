package prr.core.Notification;

import java.io.Serializable;

public class Notification implements Serializable{
    private static final long serialVersionUID = 202208091753L;
    NotificationType _type;

    public Notification(NotificationType type) {
        _type = type;
    }

    public String toString(){
        return _type.toString();
    }
}
