package prr.core;

import java.io.Serializable;
import java.util.ArrayList;
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

    public Client(String key, String name, int taxNumber, TariffPlan tariffPlan){
        this._terminals = new ArrayList<Terminal>();
        this._key = key;
        this._name = name;
        this._taxNumber = taxNumber;
        this._level = ClientLevel.NORMAL;
        this._receiveNotifications = true;
        this._tariffPlan = tariffPlan;
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
        // FIXME
        int res = 0;
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
        //FIXME
        int res = 0;
        for (Communication paidCommunication : _inDebtCommunications) {
            res += paidCommunication.getCost();
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
        String notifications = _receiveNotifications ? "YES" : "NO";
        return "CLIENT|" + _key + "|" + _name + "|" + _taxNumber + "|" + _level + "|" + notifications + "|" + getTerminalsSize() + "|" + calculatePayments() + "|" + calculateDebts();
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
}
