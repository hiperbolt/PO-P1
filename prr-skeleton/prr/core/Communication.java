package prr.core;

import java.io.Serializable;
abstract public class Communication implements Serializable{
    private static final long serialVersionUID = 202208091753L;

    private static int counter;     /* Counts the number of communication objects instantiated.*/
    private int _id;                 /* Communication id */
    private boolean _paid;
    private double _cost;
    private boolean _isOngoing;
    private Terminal _to;
    private Terminal _from;
    private boolean _betweenFriends; // Is this communication between friends?

    public Communication(Terminal to, Terminal from, boolean friends, boolean onGoing){
        counter++;
        this._id = counter; /* The id is incremental according to the number of communication objects that already exist */
        this._isOngoing = onGoing;
        this._to = to;
        this._from = from;
        this._paid = false;
        this._betweenFriends = friends;
    }


    
    /** 
     * returns true if the communication is between friends
     * 
     * @return boolean 
     */
    public boolean isBetweenFriends(){
        return this._betweenFriends;
    }

    
    /** 
     * returns de communication cost
     * 
     * @return double
     */
    public double getCost(){
        return this._cost;
    }

    
    /** 
     * sets the communication cost
     * 
     * @param cost
     */
    public void setCost(double cost){
        this._cost = cost;
    }


    
    /** 
     * returns true if the communication is ongoing
     * 
     * @return boolean
     */
    public boolean isOngoingCommunication(){
        return this._isOngoing;
    }

    
    /** 
     * returns the terminal that the communication is going to
     * 
     * @return Terminal
     */
    public Terminal getTo(){
        return this._to;
    }

    
    /** 
     * returns the terminal that the communication is coming from
     * 
     * @return Terminal
     */
    public Terminal getFrom(){
        return this._from;
    }

    
    /** 
     * sets the terminal that the communication is going to
     * 
     * @param to
     * @return boolean
     */
    public boolean setTo(Terminal to){
        this._to = to;
        return true;
    }

    
    /** 
     * sets the terminal that the communication is coming from
     * 
     * @param from
     * @return boolean
     */
    public boolean setFrom(Terminal from){
        this._from = from;
        return true;
    }

    
    /** 
     * sets the communication as paid
     * 
     * @param paid
     */
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

    
    /** 
     * returns a communication on a string format
     * 
     * @return String
     */
    public String toString(){
        // type|id|idSender|idReceiver|units|price|status
        return getType() + "|" + _id + "|" + _from.getId() + "|" + _to.getId() + "|" + getUnits() + "|" + Math.round(getCost()) + "|" + (_isOngoing ? "ONGOING" : "FINISHED");

    }


    
    /** 
     * returns communication id
     * 
     * @return int
     */
    public int getId() {
        return _id;
    }

    
    /** 
     * sets ongoing communication
     * 
     * @param ongoing
     */
    public void setOngoing(boolean ongoing) {
        this._isOngoing = ongoing;
    }

    public abstract double end(int duration, TariffPlan tariffplan);

    
    /** 
     * returns true if the communication is paid
     * 
     * @return boolean
     */
    public boolean isPaid() {
        return _paid;
    }
}
