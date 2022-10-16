package prr.app.terminals;
import prr.core.Terminal;
import java.util.List;
import prr.core.Network;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import pt.tecnico.uilib.Display;
//FIXME add more imports if needed

/**
 * Show all terminals.
 */
class DoShowAllTerminals extends Command<Network> {
  private Display _display;
  private Network _receiver;
  DoShowAllTerminals(Network receiver) {
    super(Label.SHOW_ALL_TERMINALS, receiver);
    new Display();
  }

  @Override
  protected final void execute() throws CommandException {
    List<Terminal> TerminalsList = _receiver.getAllTerminals();
    for (Terminal t : TerminalsList) {
      _display.addLine(t.toString());
    }
    _display.display();
  }
}
