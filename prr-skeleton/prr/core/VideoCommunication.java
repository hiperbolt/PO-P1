package prr.core;

public class VideoCommunication extends InteractiveCommunication{
    private double _duration;
    // Constructor
    public VideoCommunication(Terminal to, Terminal from, boolean friend){
        super(to, from, friend);    /* Call the InteractiveCommunication constructor */
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

    @Override
    protected double computeCost(TariffPlan plan){
        double cost = plan.computeCost(this.getFrom().getClient(), this);
        this.setCost(cost);
        return cost;
    }

    @Override
    public String getType(){
        return "VIDEO";
    }
}
