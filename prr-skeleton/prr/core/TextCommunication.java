package prr.core;

public class TextCommunication extends Communication{
    private String _message;

    // Constructor
    public TextCommunication(Terminal to, Terminal from, boolean friends, String message){
        super(to, from, friends, false);    /* Call the Communication constructor */
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
    
    @Override
    protected double computeCost(TariffPlan plan){
        double cost = plan.computeCost(this.getFrom().getClient(), this);
        this.setCost(cost);
        return cost;
    }

    @Override
    public String getType(){
        return "TEXT";
    }

    @Override
    public int getUnits(){
        return this.getSize();
    }
}
