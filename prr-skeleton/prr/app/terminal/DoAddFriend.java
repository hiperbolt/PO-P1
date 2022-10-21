package prr.app.terminal;

import prr.app.exception.UnknownTerminalKeyException;
import prr.core.Network;
import prr.core.Terminal;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Add a friend.
 */
class DoAddFriend extends TerminalCommand {
  Terminal _terminal;

  DoAddFriend(Network context, Terminal terminal) {
    super(Label.ADD_FRIEND, context, terminal);
    addStringField("terminalId", Message.terminalKey());
    _terminal = terminal;
  }
  
  @Override
  protected final void execute() throws CommandException {
    Terminal friend = _network.terminalById(stringField("terminalId"));
    if(friend == null){
      throw new UnknownTerminalKeyException(stringField("terminalId"));
    }
    _terminal.addFriend(friend);
  }
}
