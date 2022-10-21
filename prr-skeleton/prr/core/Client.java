package prr.core;

import java.io.Serializable;
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
    private TariffPlan _tariffPlan;
    private List<Terminal> _terminals;
    private List<Communication> _paidCommunications;
    private List<Communication> _inDebtCommunications;

    public Client(String key, String name, int taxNumber) {
        this._key = key;
        this._name = name;
        this._taxNumber = taxNumber;
        this._level = ClientLevel.NORMAL;
        this._receiveNotifications = true;
        this._tariffPlan = new BasicPlan(); // Temporary since there is only one tariff plan.
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
        if (_paidCommunications.isEmpty()){
            return 0;
        }
        for (Communication paidCommunication : _paidCommunications) {
            res += paidCommunication.getCost();
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
        if (_inDebtCommunications.isEmpty()){
            return 0;
        }
        for (Communication paidCommunication : _inDebtCommunications) {
            res += paidCommunication.getCost();
        }
        return res;
    }


    public String toString() {
        // CLIENT|key|name|taxId|type|receiveNotifications|tariffPlan|terminals|payments|debts
        String notifications = _receiveNotifications ? "YES" : "NO";
        String res = "CLIENT|" + _key + "|" + _name + "|" + _taxNumber + "|" + _level + "|" + notifications + "|" + _tariffPlan + "|" + _terminals.size() + "|" + calculatePayments() + "|" + calculateDebts();
        return res;
    }
}
