package prr.app.terminal;

import prr.core.Network;
import prr.core.Terminal;
import prr.core.TerminalMode;
import pt.tecnico.uilib.menus.CommandException;
//FIXME add more imports if needed

/**
 * Silence the terminal.
 */
class DoSilenceTerminal extends TerminalCommand {
  Terminal _terminal;
  DoSilenceTerminal(Network context, Terminal terminal) {
    super(Label.MUTE_TERMINAL, context, terminal);
    _terminal = terminal;
  }
  
  @Override
  protected final void execute() throws CommandException {
    if (_terminal.getMode() == TerminalMode.SILENCE){
      _display.popup(Message.alreadySilent());
       return;
    }
    _terminal.setOnSilent();
  }
}
