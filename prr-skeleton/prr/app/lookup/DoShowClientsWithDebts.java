package prr.app.lookup;

import prr.core.Network;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;


/**
 * Show clients with negative balance.
 */
class DoShowClientsWithDebts extends Command<Network> {
  private Network _receiver;
  DoShowClientsWithDebts(Network receiver) {
    super(Label.SHOW_CLIENTS_WITH_DEBTS, receiver);
    _receiver = receiver;
  }

  @Override
  protected final void execute() throws CommandException {
    for(String s : _receiver.getClientsWithDebts()) {
      _display.addLine(s);
      _display.display();
    }
  }
}
