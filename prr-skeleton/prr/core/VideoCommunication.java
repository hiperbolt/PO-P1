package prr.core;

public class VideoCommunication extends InteractiveCommunication{
    private double _duration;
    // Constructor
    public VideoCommunication(Terminal to, Terminal from){
        super(to, from);    /* Call the InteractiveCommunication constructor */
    }

    /**
     * Creates a new Voice type communication.
     *
     * @param to terminal.
     **/
    public void makeVoiceCall(Terminal to){
        this.setTo(to);
    }

    public double getDuration(){
        return this._duration;
    }
}
