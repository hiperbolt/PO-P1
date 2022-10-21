package prr.core;

public class TextCommunication extends Communication{
    private String _message;

    // Constructor
    public TextCommunication(Terminal to, Terminal from, String message){
        super(to, from);    /* Call the Communication constructor */
        this._message = message;
    }

    /**
     * Gets the Text Communication message size.
     *
     * @return size of _message
     **/

    protected int getSize(){
        return _message.length();
    }

}
