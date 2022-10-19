package prr.app.client;

import prr.core.Network;
import prr.app.exception.UnknownClientKeyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
//FIXME add more imports if needed

/**
 * Disable client notifications.
 */
class DoDisableClientNotifications extends Command<Network> {

  DoDisableClientNotifications(Network receiver) {
    super(Label.DISABLE_CLIENT_NOTIFICATIONS, receiver);
    addStringField("clientId", Message.key());
  }
  
  @Override
  protected final void execute() throws CommandException {
    // If a client with the provided ID does not exist, we throw an exception.
    if(!_receiver.clientExists(stringField("clientId"))){
      throw new UnknownClientKeyException(stringField("clientId"));
    }

    // If it does, disable notifications.
    _receiver.clientById(stringField("clientId")).disableReceiveNotifications();
  }
}
