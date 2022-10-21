package prr.app.client;

import prr.core.Client;
import prr.core.Network;
import prr.app.exception.UnknownClientKeyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Show specific client: also show previous notifications.
 */
class DoShowClient extends Command<Network> {

  DoShowClient(Network receiver) {
    super(Label.SHOW_CLIENT, receiver);
    addStringField("clientId", Message.key());
  }
  
  @Override
  protected final void execute() throws CommandException {
    Client c = _receiver.clientById(stringField("clientId"));
    if (c == null){
      throw new UnknownClientKeyException(stringField("clientId"));
    }
    // Popup calls display(), so it should call toString() automatically.
    _display.popup(c);
  }
}
