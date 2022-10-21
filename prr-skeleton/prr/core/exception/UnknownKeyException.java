package prr.core.exception;

public class UnknownKeyException extends Exception{
    public UnknownKeyException(String err){
        super("Chave desconhecida: " + err);
    }
}
