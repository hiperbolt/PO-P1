package prr.core;

abstract public class InteractiveCommunication extends Communication{
    private int _duration;

    // Constructor
    public InteractiveCommunication(Terminal to, Terminal from, boolean friends){
        super(to, from, friends, true);    /* Call the Communication constructor */
    }

    /**
     * Gets the Interactive Communication duration.
     *
     * @return size of _message
     **/
    protected int getSize(){
        return _duration;
    }

    
    /** 
     * gets the duration of the communication
     * 
     * @return size
     */
    @Override
    public int getUnits(){
        return this.getSize();
    }

    /**
     * Ends the interactive communication. Sets the duration, sets as no longer ongoing, calculates and returns cost.
     *
     * @param duration of the communication
     * @param tariffplan of the communication
     *
     * @return cost of the communication
     */
    public double end(int duration, TariffPlan tariffplan){
        this.setDuration(duration);
        this.setOngoing(false);
        this.computeCost(tariffplan);
        return this.getCost();

    }

    
    /** 
     * sets the communication duration
     * 
     * @param duration
     */
    public void setDuration(int duration) {
        this._duration = duration;
    }

    
    /** 
     * returns the communication duration
     * 
     * @return duration
     */
    public double getDuration(){
        return this._duration;
    }

}
