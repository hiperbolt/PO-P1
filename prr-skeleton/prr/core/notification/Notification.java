package prr.core.notification;

import prr.core.Terminal;

import java.io.Serializable;

public class Notification implements Serializable{
    private static final long serialVersionUID = 202208091753L;
    private NotificationType _type;
    private Terminal _from;
    private Terminal _to;


    public Notification(NotificationType type, Terminal from, Terminal to){
        _type = type;
        _from = from;
        _to = to;
    }

    public String toString(){
        return _type + "|" + _to.getId();
    }
}
