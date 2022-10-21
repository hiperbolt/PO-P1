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
}
