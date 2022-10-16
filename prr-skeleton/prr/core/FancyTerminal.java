package prr.core;

/**
 * Basic Terminal, extends abstract Terminal.
 */

public class FancyTerminal extends Terminal{
    public FancyTerminal(String id, Client client) {
        super(id, client);
    }
    public boolean isBasic(){
        return false;
    }
    public boolean isFancy(){
        return true;
    } 
}
