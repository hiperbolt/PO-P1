package prr.core;

import java.io.Serializable;
abstract public class Communication implements Serializable{
    private static final long serialVersionUID = 202208091753L;

    private static int _counter;     /* Counts the number of communication objects instantiated.*/
    private int _id;                 /* Communication id */
    private boolean _paid;
    private double _cost;
    private boolean _isOngoing;
    private Terminal _to;
    private Terminal _from;
    private boolean _betweenFriends; // Is this communication between friends?

    public Communication(Terminal to, Terminal from, boolean friends, boolean onGoing){
        this._id = _counter + 1; /* The id is incremental according to the number of communication objects that already exist */
        this._isOngoing = true;
        this._to = to;
        this._from = from;
        this._paid = false;
        this._betweenFriends = friends;
    }


    public boolean isBetweenFriends(){
        return this._betweenFriends;
    }

    public double getCost(){
        return this._cost;
    }

    public void setCost(double cost){
        this._cost = cost;
    }


    public boolean isOngoingCommunication(){
        return this._isOngoing;
    }

    public Terminal getTo(){
        return this._to;
    }

    public Terminal getFrom(){
        return this._from;
    }

    public boolean setTo(Terminal to){
        this._to = to;
        return true;
    }

    public boolean setFrom(Terminal from){
        this._from = from;
        return true;
    }

    public void setPaid(boolean paid) {
        this._paid = paid;
    }

    /**
     * Calculates and sets the cost of the communication according to the client level and the tariff plan.
     * Should be called everytime a communication is terminated.
     *
     * @param plan - The tariff plan to use.
     *
     * @return cost of the communication
     */
    protected abstract double computeCost(TariffPlan plan);

    public abstract String getType();

    public abstract int getUnits();

    public String toString(){
        // type|id|idSender|idReceiver|units|price|status
        return getType() + "|" + _id + "|" + _from.getId() + "|" + _to.getId() + "|" + getUnits() + "|" + getCost() + "|" + (_isOngoing ? "ONGOING" : "FINISHED");

    }


    public int getId() {
        return _id;
    }

    public void setOngoing(boolean ongoing) {
        this._isOngoing = ongoing;
    }

    public abstract double end(int duration, TariffPlan tariffplan);

    public boolean isPaid() {
        return _paid;
    }
}
