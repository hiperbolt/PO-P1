package prr.app.terminal;

import prr.core.Network;
import prr.core.Terminal;
import prr.core.TerminalMode;
import pt.tecnico.uilib.menus.CommandException;
//FIXME add more imports if needed

/**
 * Turn off the terminal.
 */
class DoTurnOffTerminal extends TerminalCommand {
  private Terminal _terminal;
  DoTurnOffTerminal(Network context, Terminal terminal) {
    super(Label.POWER_OFF, context, terminal);
    _terminal = terminal;
  }
  
  @Override
  protected final void execute() throws CommandException {
    if (_terminal.getMode() == TerminalMode.OFF){
      _display.popup(Message.alreadyOff());
      return;
    }
    _terminal.turnOff();
  }
}
