package prr.app.terminals;

import prr.core.Network;
import prr.app.exception.DuplicateTerminalKeyException;
import prr.app.exception.InvalidTerminalKeyException;
import prr.app.exception.UnknownClientKeyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
//FIXME add more imports if needed

/**
 * Register terminal.
 */
class DoRegisterTerminal extends Command<Network> {

  DoRegisterTerminal(Network receiver) {
    super(Label.REGISTER_TERMINAL, receiver);
    Form _form = new Form();
  }

  @Override
  protected final void execute() throws CommandException {
    _form.addStringField("0", Message.terminalKey());
    if (stringField("0").lenght() != 6)
      throw 
    _form.addStringField("1", Message.terminalType());
    while(stringField("1") != "FANCY" || stringField("1") != "BASIC"){
      _form.addStringField("1", "Terminal type must be FANCY or SIMPLE:");
    }
    _form.addIntegerField("2", Message.clientKey());
    
  }
}
