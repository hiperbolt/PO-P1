package prr.app.terminals;

import prr.core.Network;
import prr.app.exception.UnknownTerminalKeyException;
import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import prr.app.terminal.Menu;

/**
 * Open a specific terminal's menu.
 */
class DoOpenMenuTerminalConsole extends Command<Network> {
  DoOpenMenuTerminalConsole(Network receiver) {
    super(Label.OPEN_MENU_TERMINAL, receiver);
    addStringField("terminalId", Message.terminalKey());
  }

  @Override
  protected final void execute() throws CommandException {
    // If the terminal does not exist, we throw a UnknownTerminalKeyException
    if(!_receiver.terminalExists(stringField("terminalId"))){
      throw new UnknownTerminalKeyException(stringField("terminalId"));
    }

    new prr.app.terminal.Menu(_receiver, _receiver.terminalById(stringField("terminalId"))).open();
  }
}
