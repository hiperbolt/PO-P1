package prr.core;

import java.io.Serializable;

public class CommunicationAttempt implements Serializable {
    private static final long serialVersionUID = 202208091753L;

    private TerminalMode _mode;
    private Terminal _to;
    private Terminal _from;
    private Communication _communication;

    public CommunicationAttempt(TerminalMode mode, Terminal to, Terminal from, Communication communication){
        _mode = mode;
        this._to = to;
        this._from = from;
        this._communication = communication;
    }

    public Terminal getTo(){
        return this._to;
    }

    public Terminal getFrom(){
        return this._from;
    }

    public Communication getCommunication(){
        return this._communication;
    }
}
