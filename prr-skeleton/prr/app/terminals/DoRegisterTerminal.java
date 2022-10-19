package prr.app.terminals;
import prr.core.Network;
import prr.core.Terminal;
import prr.core.FancyTerminal;
import prr.core.BasicTerminal;
import prr.app.exception.*;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Register terminal.
 */
class DoRegisterTerminal extends Command<Network> {
  Network _receiver;
  DoRegisterTerminal(Network receiver) {
    super(Label.REGISTER_TERMINAL, receiver);
    _receiver = receiver;
  }

  @Override
  protected final void execute() throws CommandException {
    // We add the provided terminal key to the string field.
    _form.addStringField("0", Message.terminalKey());
    // We check its valid, and throw an exception if not.
    if (_form.stringField("0").length() != 6){
      throw new InvalidTerminalKeyException(stringField("0"));
    }
    // Then, we check if its unique, and throw an exception if not.
    for (Terminal t : _receiver.getAllTerminals()){
      if (t.getId() == stringField("0")){
        throw new DuplicateTerminalKeyException(stringField("0"));
      }
    }
    // Then we add the terminal type to the String field, and make sure it's valid.
    _form.addStringField("1", Message.terminalType());
    while(!_form.stringField("1").equals("FANCY")|| !_form.stringField("1").equals("BASIC")){
      _form.addStringField("1", "Terminal type must be FANCY or SIMPLE:");
    }

    // Adding the client key to the string field, and making sure its valid (if the corresponding client object is registered in the network). We throw an exception if not.
    _form.addStringField("2", Message.clientKey());
    if (!_receiver.clientExists(stringField("2"))){
      throw new UnknownClientKeyException(stringField("2"));
    }

    // We then create a Basic or Fancy Terminal accordingly.
    if (_form.stringField("1").equals("FANCY")){
      _receiver.registerTerminal(new FancyTerminal(_form.stringField("0"), _receiver.clientById(_form.stringField("2"))));
    }
    else{
      _receiver.registerTerminal(new BasicTerminal(_form.stringField("0"), _receiver.clientById(_form.stringField("2"))));
    }
  }
}
