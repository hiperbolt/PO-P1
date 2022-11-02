package prr.core;

import prr.core.exception.UnsupportedOperationException;

/**
 * Basic Terminal, extends abstract Terminal.
 */
public class BasicTerminal extends Terminal{
    public BasicTerminal(String id, Client client) {
        super(id, client);
    }

    @Override
    public void makeVideoCall(Terminal outboundTerminal) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Video calls are not supported by this terminal.");
    }
    @Override
    public void acceptVideoCall(VideoCommunication communication) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Video calls are not supported by this terminal.");
    }

}
