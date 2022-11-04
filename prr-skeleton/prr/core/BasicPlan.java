package prr.core;

import java.io.Serializable;

/**
 * Basic Tariff Plan extending the abstract Tariff Plan.
 */
public class BasicPlan extends TariffPlan implements Serializable{

    
    /** 
     * @return plantype
     */
    public String getPlanType(){
        String _planType = "BasicPlan";
        return _planType;
    }

    
    /** 
     * compute the cost of a text communication
     * 
     * @param cl client who made the text communication
     * @param vc text communication
     * @return text communication cost based on number of characters and client's plan
     */
    @Override
    protected double computeCost(Client cl, TextCommunication tc){
        if (tc.getSize() < 50){ //checks if the text communication is less than 50 characters
            switch (cl.getLevel()){ //checks the client level
                case NORMAL, GOLD -> {
                    return 10;
                }
                case PLATINUM -> {
                    return 0;
                }
            }
        } else if (tc.getSize() < 100) { //checks if the text communication is less than 100 characters
            switch (cl.getLevel()){ //checks the client level
                case NORMAL -> {
                    return 16;
                }
                case GOLD -> {
                    return 10;
                }
                case PLATINUM -> {
                    return 4;
                }
            }
        } else {
            switch (cl.getLevel()){  //checks the client level
                case NORMAL, GOLD -> {
                    return 2 * tc.getSize();
                }
                case PLATINUM -> {
                    return 4;
                }
            }
        }
        return 0;
    }

    
    /** 
     * compute the cost of a voice communication
     * 
     * @param cl client who made the voice communication
     * @param vc voice communication
     * @return voice communication cost based on duration and client level
     */
    protected double computeCost(Client cl,VoiceCommunication vc){
        switch (cl.getLevel()){ //checks the client level
            case NORMAL -> {
                return 20 * vc.getDuration(); 
            }
            case GOLD, PLATINUM -> {
                return 10 * vc.getDuration();
            }
        }
        return 0;
    }

    
    /** 
     * compute the cost of a video communication
     * 
     * @param cl client who made the video communication
     * @param vc video communication
     * @return video communication cost based on duration and client level
     */
    protected double computeCost(Client cl, VideoCommunication vc){
        switch (cl.getLevel()){ //checks the client level
            case NORMAL -> {
                return 30 * vc.getDuration();
            }
            case GOLD -> {
                return 20 * vc.getDuration();
            }
            case PLATINUM -> {
                return 10 * vc.getDuration();
            }
        }
        return 0;
    }
}
