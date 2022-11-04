package prr.core;

import prr.core.notification.Notification;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Client.
 */
public class Client implements Serializable{
    private static final long serialVersionUID = 202208091753L;

    private String _key;
    private String _name;
    private int _taxNumber;
    private ClientLevel _level;
    private boolean _receiveNotifications;
    private List<Terminal> _terminals;
    private TariffPlan _tariffPlan;
    private List<Notification> _notifications;

    public Client(String key, String name, int taxNumber, TariffPlan tariffPlan){
        this._terminals = new ArrayList<Terminal>();
        this._key = key;
        this._name = name;
        this._taxNumber = taxNumber;
        this._level = ClientLevel.NORMAL;
        this._receiveNotifications = true;
        this._tariffPlan = tariffPlan;
        this._notifications = new ArrayList<Notification>();
    }


    /**
     * Disables client notifications.
     */
    public void disableReceiveNotifications(){
        this._receiveNotifications = false;
    }
    
    /** 
     * @return String client key
     */
    public String getKey(){
        return this._key;
    }

    /**
     * Enables the client to receive notifications.
     * 
     * @return boolean
     */
    public void enableReceiveNotifications(){
        this._receiveNotifications = true;
    }

    /**
     * Calculates all payments the client has done.
     *
     * @return the sum of Payed communications.
     */
    public int calculatePayments() {
        int res = 0;
        for (Terminal t : this._terminals) {
            res += t.getPayments();
        }
        return res;
    }

    /**
     * Calculates all payments the client owes.
     *
     * @return the sum of in debt communications.
     */
    public int calculateDebts() {
        int res = 0;
        for (Terminal t : this._terminals) {
            res += t.getDebt();
        }
        return res;
    }
    
    /** 
     * @return number of terminals
     */
    public int getTerminalsSize(){
        if (_terminals == null){
            return 0;
        }
        return _terminals.size();
    }

    
    /** 
     * @return ClientLevel
     */
    public ClientLevel getLevel(){
        return this._level;
    }

    
    /** 
     * @return a client to string
     */
    public String toString() {
        // CLIENT|key|name|taxId|type|receiveNotifications|tariffPlan|terminals|payments|debts
        // notifications by received order
        String notifications = _receiveNotifications ? "YES" : "NO";

        return "CLIENT|" + _key + "|" + _name + "|" + _taxNumber + "|" + _level + "|" + notifications + "|" + getTerminalsSize() + "|" + calculatePayments() + "|" + calculateDebts();
    }

    
    /** 
     * @return List<Notification> of client notifications
     */
    public List<Notification> getNotifs() {
        // Make a deep copy of the notifications
        if(!_notifications.isEmpty()) {
            List<Notification> res = new ArrayList<Notification>(_notifications);
            _notifications.clear();
            return res;
        }
        return null;
    }

    
    /** 
     * adds a new terminal to the client
     * 
     * @param terminal terminal to add
     */
    public void addTerminal(Terminal t) {
        //check if terminal t not null and adds it to the client
        if (t != null){
            _terminals.add(t);
        }
    }

    
    /** 
     * @return List<Terminal> of client terminals
     */
    public List<Terminal> getTerminals() {
        return _terminals;
    }

    
    /** 
     * @return client debt
     */
    public double calculateDebt(){
        double res = 0;
        for (Terminal terminal : _terminals) {
            res += terminal.getDebt();
        }
        return res;
    }

    
    /** 
     * @return client TariffPlan
     */
    public TariffPlan getTariffPlan(){
        return this._tariffPlan;
    }

    
    /** 
     * @return List<String> of made communications
     */
    public List<String> getMadeCommunications(){
        List<String> res = new ArrayList<String>();
        for (Terminal terminal : _terminals) {
            for (Communication communication : terminal.getMadeCommunications()) {
                res.add(communication.toString());
            }
        }
        return res;
    }

    
    /** 
     * @return List<String> of received communications
     */
    public List<String> getReceivedCommunications() {
        List<String> res = new ArrayList<String>();
        for (Terminal terminal : _terminals) {
            for (Communication communication : terminal.getReceivedCommunications()) {
                res.add(communication.toString());
            }
        }
        return res;
    }

    
    /** 
     * @return boolean of receive notifications
     */
    public boolean getNotificationReception() {
        return this._receiveNotifications;
    }

    
    /** 
     * adds a new notification to the client
     * 
     * @param n notification to add
     */
    public void addNotification(Notification n) {
        if (this._notifications == null){
            this._notifications = new ArrayList<Notification>();
        }
        // Check if the notification is already in the list
        for (Notification notification : _notifications) {
            if (notification.getType() == n.getType() && notification.getToId().equals(n.getToId())){
                return;
            }
        }
        this._notifications.add(n);
    }
}
