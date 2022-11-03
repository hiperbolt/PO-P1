package prr.app.lookup;

import prr.core.Client;
import prr.core.Network;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
//FIXME add more imports if needed

/**
 * Show communications from a client.
 */
class DoShowCommunicationsFromClient extends Command<Network> {
  Network _receiver;

  DoShowCommunicationsFromClient(Network receiver) {
    super(Label.SHOW_COMMUNICATIONS_FROM_CLIENT, receiver);
    _receiver = receiver;
    addStringField("client", Message.clientKey());
  }

  @Override
  protected final void execute() throws CommandException {
    Client client = _receiver.clientById(stringField("client"));
    _display.addAll(client.getMadeCommunications());
    _display.display();
  }
}
