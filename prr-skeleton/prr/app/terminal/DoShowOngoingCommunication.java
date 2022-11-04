package prr.app.terminal;

import prr.core.Communication;
import prr.core.Network;
import prr.core.Terminal;
import pt.tecnico.uilib.menus.CommandException;
//FIXME add more imports if needed

/**
 * Command for showing the ongoing communication.
 */
class DoShowOngoingCommunication extends TerminalCommand {
  private Terminal _terminal;
  DoShowOngoingCommunication(Network context, Terminal terminal) {
    super(Label.SHOW_ONGOING_COMMUNICATION, context, terminal);
    _terminal = terminal;
  }
  
  @Override
  protected final void execute() throws CommandException {
    Communication c = _terminal.getOngoingCommunication();
    if (c == null) {
      _display.popup(Message.noOngoingCommunication());
    } else {
      _display.popup(c.toString());
    }
  }
}
