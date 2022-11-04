package prr.app.client;

import prr.core.Client;
import prr.core.Network;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

import java.util.List;

/**
 * Show all clients.
 */
class DoShowAllClients extends Command<Network> {

  DoShowAllClients(Network receiver) {
    super(Label.SHOW_ALL_CLIENTS, receiver);
  }
  
  @Override
  protected final void execute() throws CommandException {
    // We order the clients by their keys (lowercase or uppercase does not matter).
    List<Client> clients = _receiver.getAllClients();
    clients.sort((c1, c2) -> c1.getKey().compareToIgnoreCase(c2.getKey()));
    for (Client c : clients) {
      _display.addLine(c.toString());
    }
    _display.display();
  }
}
