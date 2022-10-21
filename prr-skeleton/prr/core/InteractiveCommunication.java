package prr.core;

abstract public class InteractiveCommunication extends Communication{
    private int _duration;

    // Constructor
    public InteractiveCommunication(Terminal to, Terminal from){
        super(to, from);    /* Call the Communication constructor */
    }

    /**
     * Gets the Interactive Communication duration.
     *
     * @return size of _message
     **/
    protected int getSize(){
        return _duration;
    }

}
