package prr.app.terminal;

import prr.core.Network;
import prr.core.Terminal;
import prr.core.TerminalMode;
import pt.tecnico.uilib.menus.CommandException;
//FIXME add more imports if needed

/**
 * Turn on the terminal.
 */
class DoTurnOnTerminal extends TerminalCommand {
  private Terminal _terminal;
  DoTurnOnTerminal(Network context, Terminal terminal) {
    super(Label.POWER_ON, context, terminal);
    _terminal = terminal;
  }
  
  @Override
  protected final void execute() throws CommandException {
    if (_terminal.getMode() == TerminalMode.ON){
      _display.popup(Message.alreadyOn());
      return;
    }
    _terminal.setOnIdle();
  }
}
