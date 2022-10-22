package prr.app.lookup;

import prr.core.Network;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Show clients with positive balance.
 */
class DoShowClientsWithoutDebts extends Command<Network> {
  private Network _receiver;
  DoShowClientsWithoutDebts(Network receiver) {
    super(Label.SHOW_CLIENTS_WITHOUT_DEBTS, receiver);
    _receiver = receiver;
  }

  @Override
  protected final void execute() throws CommandException {
    for(String s : _receiver.getAllClientsWithoutDebts()) {
      _display.addLine(s);
      _display.display();
    }
  }
}
