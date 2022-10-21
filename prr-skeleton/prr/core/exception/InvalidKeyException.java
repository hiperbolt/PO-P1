package prr.core.exception;

public class InvalidKeyException extends Exception{
    public InvalidKeyException(String err){
        super("Key inválida: " + err);
    }
}
