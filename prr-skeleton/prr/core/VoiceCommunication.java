package prr.core;

public class VoiceCommunication extends InteractiveCommunication{
    private double _duration;
    // Constructor
    public VoiceCommunication(Terminal to, Terminal from, boolean friends){
        super(to, from, friends);    /* Call the InteractiveCommunication constructor */
    }

    @Override
    protected double computeCost(TariffPlan plan){
        double cost = plan.computeCost(this.getFrom().getClient(), this);
        this.setCost(cost);
        return cost;
    }

    @Override
    public String getType(){
        return "VOICE";
    }


}
