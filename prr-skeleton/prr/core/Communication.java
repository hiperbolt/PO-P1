package prr.core;

import java.io.Serializable;
abstract public class Communication implements Serializable{
    private static final long serialVersionUID = 202208091753L;

    private static int _counter;     /* Counts the number of communication objects instantiated.*/
    private int _id;
    private boolean _isPaid;
    private double _cost;
    private boolean _isOngoing;
    private Terminal _to;
    private Terminal _from;

    public Communication(Terminal to, Terminal from){
        this._id = _counter + 1; /* The id is incremental according to the number of communication objects that already exist */
        this._isOngoing = true; // FIXME should we do this?
        this._to = to;
        this._from = from;
    }

    public double getCost(){
        return this._cost;
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

}
