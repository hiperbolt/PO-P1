package prr.app.terminal;

import prr.core.Network;
import prr.core.Terminal;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Remove friend.
 */
class DoRemoveFriend extends TerminalCommand {
  private Terminal _terminal;

  DoRemoveFriend(Network context, Terminal terminal) {
    super(Label.REMOVE_FRIEND, context, terminal);
    _terminal = terminal;
    addStringField("terminalId",Message.terminalKey());
  }
  
  @Override
  protected final void execute() throws CommandException {
    _terminal.removeFriend(stringField("terminalId"));
  }
}
