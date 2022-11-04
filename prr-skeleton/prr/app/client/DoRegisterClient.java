package prr.app.client;

import prr.core.Network;
import prr.app.exception.DuplicateClientKeyException;
import prr.core.exception.DuplicateKeyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Register new client.
 */
class DoRegisterClient extends Command<Network> {

  DoRegisterClient(Network receiver) {
    super(Label.REGISTER_CLIENT, receiver);
    addStringField("clientId", prr.app.client.Message.key());
    addStringField("clientName", prr.app.client.Message.name());
    addIntegerField("taxId", prr.app.client.Message.taxId());
  }

  @Override
  protected final void execute() throws CommandException {
    // We try to register the client, and catch the exceptions if they occur, then throw our own exception.
    try {
        _receiver.registerClient(stringField("clientId"), stringField("clientName"), integerField("taxId"));
    } catch (DuplicateKeyException e) {
        throw new DuplicateClientKeyException(stringField("clientId"));
    }
  }
}

