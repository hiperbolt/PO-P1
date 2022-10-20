package prr.core;

import java.io.Serializable;
abstract public class Communication implements Serializable{
    private static final long serialVersionUID = 202208091753L;

    static int _counter;     /* Counts the number of communication objects instantiated.*/
    int _id;
    boolean _isPaid;
    double _cost;
    boolean _isOngoing;
    Terminal _to;
    Terminal _from;

    public Communication(Terminal to, Terminal from){
        this._id = _counter + 1; /* The id is incremental according to the number of communication objects that already exist */
        this._isOngoing = true; // FIXME should we do this?
        this._to = to;
        this._from = from;
    }

}
