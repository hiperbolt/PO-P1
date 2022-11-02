package prr.app.terminal;

import prr.core.Network;
import prr.core.Terminal;
import prr.app.exception.UnknownTerminalKeyException;
import prr.core.exception.TerminalBusyException;
import prr.core.exception.TerminalOffException;
import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.CommandException;
//FIXME add more imports if needed

/**
 * Command for sending a text communication.
 */
class DoSendTextCommunication extends TerminalCommand {
  Terminal _terminal;
  Network _context;
  DoSendTextCommunication(Network context, Terminal terminal) {
    super(Label.SEND_TEXT_COMMUNICATION, context, terminal, receiver -> receiver.canStartCommunication());
    _terminal = terminal;
    _context = context;
    addStringField("to", Message.terminalKey());
    addStringField("message", Message.textMessage());
  }
  
  @Override
  protected final void execute() throws CommandException {
    // We get the destination terminal object.
    Terminal destinationTerminal =_context.terminalById(stringField("to"));

    // If it does not exist, we throw an UnknownTerminalKeyException.
    if(destinationTerminal == null){
      throw new UnknownTerminalKeyException(stringField("to"));
    }
    try {
      // We send the text communication, this might throw an exception if the destination is Off, or if we are Off or Busy.
      _terminal.makeSMS(destinationTerminal, stringField("message"));
    } catch (TerminalBusyException ignored) {
    } catch (TerminalOffException e) {
      // If the exception came from the destination, we show the destinationIsOff message.
      if (e.getMessage() == stringField("to")) {
        _display.popup(Message.destinationIsOff(stringField("to")));
      }
    }
  }
} 
