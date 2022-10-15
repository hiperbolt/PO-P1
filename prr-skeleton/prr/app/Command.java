package prr.app;

public abstract class Command<Receiver> extends java.lang.Object{
    protected Receiver _receiver;
    protected Form _form;
    protected Display _display;
    public Command(boolean last, String title){
        return;
    }
}
