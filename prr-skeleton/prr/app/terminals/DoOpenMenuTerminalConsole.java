package prr.app.terminals;

import prr.core.Network;
import prr.app.exception.UnknownTerminalKeyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import prr.app.terminal.Menu;

/**
 * Open a specific terminal's menu.
 */
class DoOpenMenuTerminalConsole extends Command<Network> {
  Network _receiver;

  DoOpenMenuTerminalConsole(Network receiver) {
    super(Label.OPEN_MENU_TERMINAL, receiver);
    _receiver = receiver;
  }

  @Override
  protected final void execute() throws CommandException {
    _form.addStringField("0", Message.terminalKey());

    // If the terminal does not exist, we throw a UnknownTerminalKeyException
    // FIXME isto tá bue feio
    if(!_receiver.terminalExists(stringField("0"))){
      throw new UnknownTerminalKeyException(stringField("0"));
    }

    // FIXME como é que abrimos isto.. perguntar ao mendo
    new prr.app.terminal.Menu(_receiver, _receiver.terminalById(stringField("0")));
  }
}
