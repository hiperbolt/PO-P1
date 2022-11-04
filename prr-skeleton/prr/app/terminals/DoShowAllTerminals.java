package prr.app.terminals;
import prr.core.Client;
import prr.core.Terminal;
import prr.core.Network;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

import java.util.List;

/**
 * Show all terminals.
 */
class DoShowAllTerminals extends Command<Network> {
  DoShowAllTerminals(Network receiver) {
    super(Label.SHOW_ALL_TERMINALS, receiver);
  }

  @Override
  protected final void execute() throws CommandException {
    // We order the clients by their keys (lowercase or uppercase does not matter).
    List<Terminal> terminals = _receiver.getAllTerminals();
    terminals.sort((t1, t2) -> t1.getId().compareToIgnoreCase(t2.getId()));
    _display.addAll(terminals);
    _display.display();
  }
}
