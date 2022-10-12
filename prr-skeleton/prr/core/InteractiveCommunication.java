package prr.core;

abstract public class InteractiveCommunication extends Communication{
    int _duration;

    /**
     * Gets the Interactive Communication duration.
     *
     * @return size of _message
     **/
    @Override
    protected int getSize(){
        return _duration;
    }

}
