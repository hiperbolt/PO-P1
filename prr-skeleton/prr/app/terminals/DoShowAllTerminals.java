package prr.app.terminals;

import java.util.Collections;
import java.util.List;

import prr.core.Network;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
//FIXME add more imports if needed

/**
 * Show all terminals.
 */
class DoShowAllTerminals extends Command<Network> {

  DoShowAllTerminals(Network receiver) {
    super(Label.SHOW_ALL_TERMINALS, receiver);
  }

  @Override
  protected final void execute() throws CommandException {
    List<Terminal> TerminalsList = receiver.getTerminals();
    for (Terminal t : TerminalsList) {
      _display.addLine(t.toString());
    }
    _display.display();
  }
}
