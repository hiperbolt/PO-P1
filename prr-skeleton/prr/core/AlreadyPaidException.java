package prr.core;


public class AlreadyPaidException extends Exception{
    public AlreadyPaidException(String message){
        super(message);
    }
}

