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
    _form.addStringField("0", Message.terminalKey());
    if (_form.stringField("0").length() != 6){
      throw new InvalidTerminalKeyException(stringField("0"));
    }
    for (Terminal t : _receiver.getAllTerminals()){
      if (t.getId() == stringField("0")){
        throw new DuplicateTerminalKeyException(stringField("0"));
      }
    }
    _form.addStringField("1", Message.terminalType());
    while(!_form.stringField("1").equals("FANCY")|| !_form.stringField("1").equals("BASIC")){
      _form.addStringField("1", "Terminal type must be FANCY or SIMPLE:");
    }
    _form.addStringField("2", Message.clientKey());
    if (!_receiver.clientExists(stringField("2"))){
      throw new UnknownClientKeyException(stringField("2"));
    }
    if (_form.stringField("1").equals("FANCY")){
      _receiver.registerTerminal(new FancyTerminal(_form.stringField("0"), _receiver.clientById(_form.stringField("2"))));
    }
    else{
      _receiver.registerTerminal(new BasicTerminal(_form.stringField("0"), _receiver.clientById(_form.stringField("2"))));
    }
  }
}
