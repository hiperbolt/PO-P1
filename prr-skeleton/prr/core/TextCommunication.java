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
    
    
    /**
     * gets the text communication cost
     *  
     * @param plan - the plan to be used to send the message
     * @return double - the cost of sending the message
     */
    @Override
    protected double computeCost(TariffPlan plan){
        double cost = plan.computeCost(this.getFrom().getClient(), this);
        this.setCost(cost);
        return cost;
    }

    
    /** 
     * gets the communication type
     * @return String - the communication type
     */
    @Override
    public String getType(){
        return "TEXT";
    }

    
    /** 
     * @return int - the communication size
     */
    @Override
    public int getUnits(){
        return this.getSize();
    }

    
    /** 
     * returns 0 because text communications don't have a duration
     * 
     * @param duration - the duration of the communication
     * @param tariffplan - the tariff plan
     * @return double - the cost of the communication
     */
    @Override
    public double end(int duration, TariffPlan tariffplan) {
        return 0;
    }
}
