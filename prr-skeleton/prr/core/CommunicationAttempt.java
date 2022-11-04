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

    
    /** 
     * returns the communication mode
     * 
     * @return TerminalMode
     */
    public TerminalMode getMode(){
        return this._mode;
    }

    
    /** 
     * returns the terminal to which the communication is directed
     * 
     * @return Terminal
     */
    public Terminal getTo(){
        return this._to;
    }

    
    /** 
     * returns the terminal from which the communication is originated
     * 
     * @return Terminal
     */
    public Terminal getFrom(){
        return this._from;
    }

    
    /** 
     * returns the communication object
     * 
     * @return Communication
     */
    public Communication getCommunication(){
        return this._communication;
    }
}
