package prr.app.client;

import prr.app.exception.UnknownClientKeyException;
import prr.core.Network;
import prr.app.exception.DuplicateClientKeyException;
import prr.core.Client;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
//FIXME add more imports if needed

/**
 * Register new client.
 */
class DoRegisterClient extends Command<Network> {

  Network _receiver;
  DoRegisterClient(Network receiver) {
    super(Label.REGISTER_CLIENT, receiver);
    _receiver = receiver;
  }
  
  @Override
  protected final void execute() throws CommandException {
    // Firstly, we receive the provided client key. We check if it already exists and throw an exception if it does.
    _form.addStringField("0", prr.app.client.Message.key());
    for (Client c : _receiver.getAllClients()){
      if (c.getKey().equals(stringField("0"))){
        throw new DuplicateClientKeyException(stringField("0"));
      }
    }

    // We add the client name to the string field.
    _form.addStringField("1", prr.app.client.Message.name());

    // We add the client tax ID to the string field.
    _form.addStringField("2", prr.app.client.Message.taxId());
    // And we convert it to an integer.
    int taxIdInt= Integer.parseInt(_form.stringField("2"));

    _receiver.registerClient(new Client(_form.stringField("0"), _form.stringField("1"), taxIdInt));

  }
}

