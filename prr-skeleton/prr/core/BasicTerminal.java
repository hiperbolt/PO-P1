package prr.core;

/**
 * Basic Terminal, extends abstract Terminal.
 */
public class BasicTerminal extends Terminal{
    public BasicTerminal(String id, Client client) {
        super(id, client);


    }
    public boolean isBasic(){
        return true;
    }
    public boolean isFancy(){
        return false;
    } 
}
