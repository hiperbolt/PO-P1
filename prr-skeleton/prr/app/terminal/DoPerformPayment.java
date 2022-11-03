package prr.app.terminal;

import prr.core.Communication;
import prr.core.Network;
import prr.core.Terminal;
import pt.tecnico.uilib.menus.CommandException;
// Add more imports if needed

/**
 * Perform payment.
 */
class DoPerformPayment extends TerminalCommand {
  Terminal _terminal;
  DoPerformPayment(Network context, Terminal terminal) {
    super(Label.PERFORM_PAYMENT, context, terminal);
    _terminal = terminal;
    addIntegerField("communication", Message.commKey());
  }
  
  @Override
  protected final void execute() throws CommandException {
    Communication c = _terminal.getCommunicationByID(integerField("communication"));
    if(c == null) {
      _display.popup(Message.invalidCommunication());
    }
    _terminal.payCommunication(c);
  }
}
