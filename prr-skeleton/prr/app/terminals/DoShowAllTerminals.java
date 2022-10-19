package prr.app.terminals;
import prr.core.Terminal;
import java.util.List;
import prr.core.Network;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Show all terminals.
 */
class DoShowAllTerminals extends Command<Network> {
  private Network _receiver;
  DoShowAllTerminals(Network receiver) {
    super(Label.SHOW_ALL_TERMINALS, receiver);
    _receiver = receiver;
  }

  @Override
  protected final void execute() throws CommandException {
    // We're going to get the list of all terminals from core.
    for(Terminal t : _receiver.getAllTerminals()){
      _display.addLine(t.toString());
    }
    _display.display();
  }
}
