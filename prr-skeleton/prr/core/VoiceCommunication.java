package prr.core;

public class VoiceCommunication extends InteractiveCommunication{
    private double _duration;
    // Constructor
    public VoiceCommunication(Terminal to, Terminal from){
        super(to, from);    /* Call the InteractiveCommunication constructor */
    }

    public double getDuration(){
        return this._duration;
    }
}
