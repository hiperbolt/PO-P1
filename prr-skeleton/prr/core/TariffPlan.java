package prr.core;

/**
 * Abstract Tariff Plan.
 */
abstract public class TariffPlan {
    public abstract String getPlanType();
    protected abstract double computeCost(Client cl, TextCommunication tc);
    protected abstract double computeCost(Client cl, VoiceCommunication vc);
    protected abstract double computeCost(Client cl, VideoCommunication vc);

}
