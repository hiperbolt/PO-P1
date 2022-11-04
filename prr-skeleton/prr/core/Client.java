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
     * Disables notifications.
     */
    public void disableReceiveNotifications(){
        this._receiveNotifications = false;
    }
    public String getKey(){
        return this._key;
    }

    /**
     * Enables the client to receive notifications.
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
    public int getTerminalsSize(){
        if (_terminals == null){
            return 0;
        }
        return _terminals.size();
    }

    public ClientLevel getLevel(){
        return this._level;
    }

    public String toString() {
        // CLIENT|key|name|taxId|type|receiveNotifications|tariffPlan|terminals|payments|debts
        // notifications by received order
        String notifications = _receiveNotifications ? "YES" : "NO";

        return "CLIENT|" + _key + "|" + _name + "|" + _taxNumber + "|" + _level + "|" + notifications + "|" + getTerminalsSize() + "|" + calculatePayments() + "|" + calculateDebts();
    }

    public List<Notification> getNotifs() {
        // Make a deep copy of the notifications
        if(!_notifications.isEmpty()) {
            List<Notification> res = new ArrayList<Notification>(_notifications);
            _notifications.clear();
            return res;
        }
        return null;
    }

    public void addTerminal(Terminal t) {
        if (t != null){
            _terminals.add(t);
        }
    }

    public List<Terminal> getTerminals() {
        return _terminals;
    }

    public double calculateDebt(){
        double res = 0;
        for (Terminal terminal : _terminals) {
            res += terminal.getDebt();
        }
        return res;
    }

    public TariffPlan getTariffPlan(){
        return this._tariffPlan;
    }

    public List<String> getMadeCommunications(){
        List<String> res = new ArrayList<String>();
        for (Terminal terminal : _terminals) {
            for (Communication communication : terminal.getMadeCommunications()) {
                res.add(communication.toString());
            }
        }
        return res;
    }

    public List<String> getReceivedCommunications() {
        List<String> res = new ArrayList<String>();
        for (Terminal terminal : _terminals) {
            for (Communication communication : terminal.getReceivedCommunications()) {
                res.add(communication.toString());
            }
        }
        return res;
    }

    public boolean getNotificationReception() {
        return this._receiveNotifications;
    }

    public void addNotification(Notification n) {
    if (this._notifications == null){
        this._notifications = new ArrayList<Notification>();
    }
    this._notifications.add(n);
    }
}
