package prr.app.terminal;

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
    _terminal.addFriend(stringField("terminalId"));
  }
}
