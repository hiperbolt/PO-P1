package prr.core;

public class VoiceCommunication extends InteractiveCommunication{
    private double _duration;
    // Constructor
    public VoiceCommunication(Terminal to, Terminal from, boolean friends){
        super(to, from, friends);    /* Call the InteractiveCommunication constructor */
    }

    
    /** 
     * calculates the cost of the communication
     * 
     * @param plan - the plan to be used make the communication
     * @return double - the cost of the communication
     */
    @Override
    protected double computeCost(TariffPlan plan){
        double cost = plan.computeCost(this.getFrom().getClient(), this);
        this.setCost(cost);
        return cost;
    }

    
    /**
     * Gets the Voice Communication type.
     *  
     * @return String - the communication type
     */
    @Override
    public String getType(){
        return "VOICE";
    }


}
