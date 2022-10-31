package prr.core;

import java.io.Serializable;

/**
 * Basic Tariff Plan extending the abstract Tariff Plan.
 */
public class BasicPlan extends TariffPlan implements Serializable{

    public String getPlanType(){
        String _planType = "BasicPlan";
        return _planType;
    }

    @Override
    protected double computeCost(Client cl, TextCommunication tc){
        if (tc.getSize() < 50){
            switch (cl.getLevel()){
                case NORMAL, GOLD -> {
                    return 10;
                }
                case PLATINUM -> {
                    return 0;
                }
            }
        } else if (tc.getSize() < 100) {
            switch (cl.getLevel()){
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
            switch (cl.getLevel()){
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

    protected double computeCost(Client cl,VoiceCommunication vc){
        switch (cl.getLevel()){
            case NORMAL -> {
                return 20 * vc.getDuration();
            }
            case GOLD, PLATINUM -> {
                return 10 * vc.getDuration();
            }
        }
        return 0;
    }

    protected double computeCost(Client cl, VideoCommunication vc){
        switch (cl.getLevel()){
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
