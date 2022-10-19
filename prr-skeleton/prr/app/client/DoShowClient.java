package prr.app.client;

import prr.core.Client;
import prr.core.Network;
import prr.app.exception.UnknownClientKeyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
//FIXME add more imports if needed

/**
 * Show specific client: also show previous notifications.
 */
class DoShowClient extends Command<Network> {
  private Network _receiver;
  DoShowClient(Network receiver) {
    super(Label.SHOW_CLIENT, receiver);
    _receiver = receiver;
    addStringField("clientId", Message.key());
  }
  
  @Override
  protected final void execute() throws CommandException {
    Client c = _receiver.clientById(stringField("clientId"));
    _display.popup(c.toString());
  }
}
