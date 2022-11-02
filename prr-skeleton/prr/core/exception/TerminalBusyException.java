package prr.core.exception;

public class TerminalBusyException extends Exception {
    public TerminalBusyException(String terminalId){
        super(terminalId);
    }
}
