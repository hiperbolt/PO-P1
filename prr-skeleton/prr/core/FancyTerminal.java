package prr.core;

import prr.core.exception.TerminalBusyException;
import prr.core.exception.TerminalOffException;
import prr.core.exception.TerminalSilentException;

/**
 * Fancy Terminal, extends abstract Terminal.
 */

public class FancyTerminal extends Terminal{
    public FancyTerminal(String id, Client client) {
        super(id, client);
    }

    @Override
    public void makeVideoCall(Terminal outboundTerminal) throws TerminalOffException, TerminalBusyException, TerminalSilentException {
    // We check if we are in the right mode to make a Video call.
    if(this.canStartCommunication()){
        // We create the Video call.
        VideoCommunication newVideoComm = new VideoCommunication(outboundTerminal, this, this.isFriend(outboundTerminal));
        // We try to send the Voice call.
        // This might throw an exception if the destination is Off, Busy or Silent.
        outboundTerminal.acceptVideoCall(newVideoComm);
        // If we are here, the Voice call was started successfully.
        // We set the voice call as our onGoing communication.
        this.setOnGoingCommunication(newVideoComm);
    } else {
        switch (this.getMode()) {
            case OFF -> throw new TerminalOffException(this.getId());
            case BUSY -> throw new TerminalBusyException(this.getId());
        }
    }
    }

    @Override
    protected void acceptVideoCall(VideoCommunication communication) throws TerminalOffException, TerminalBusyException, TerminalSilentException {
    // We check if we are in the right mode to receive a Video call.
    if(this.getMode() == TerminalMode.ON){
        // If we are here, we are in the right mode.
        // We set the voice call as our onGoing communication.
        this.setOnGoingCommunication(communication);
    } else {
        // If we are here, we are not in the right mode.
        // We throw the exception.
        switch (this.getMode()) {
            case OFF -> throw new TerminalOffException(this.getId());
            case BUSY -> throw new TerminalBusyException(this.getId());
            case SILENCE -> throw new TerminalSilentException(this.getId());
        }
    }
    }
}
