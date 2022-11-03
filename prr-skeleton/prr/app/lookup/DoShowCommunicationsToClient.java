package prr.app.lookup;

import prr.core.Client;
import prr.core.Network;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
//FIXME add more imports if needed

/**
 * Show communications to a client.
 */
class DoShowCommunicationsToClient extends Command<Network> {
  Network _receiver;
  DoShowCommunicationsToClient(Network receiver) {
    super(Label.SHOW_COMMUNICATIONS_TO_CLIENT, receiver);
    receiver = receiver;
    addStringField("client", Message.clientKey());
  }

  @Override
  protected final void execute() throws CommandException {
    Client client = _receiver.clientById(stringField("client"));
    _display.addAll(client.getReceivedCommunications());
    _display.display();
  }
}
