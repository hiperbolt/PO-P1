package prr.core;

public class VideoCommunication extends InteractiveCommunication{

    /**
     * Creates a new Voice type communication.
     *
     * @param to terminal.
     * @return void
     **/
    public void makeVoiceCall(Terminal to){
        this._to = to;
    }
}
