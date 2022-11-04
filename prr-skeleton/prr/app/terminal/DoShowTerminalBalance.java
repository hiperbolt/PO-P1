package prr.app.terminal;

import prr.core.Network;
import prr.core.Terminal;
import pt.tecnico.uilib.menus.CommandException;
//FIXME add more imports if needed

/**
 * Show balance.
 */
class DoShowTerminalBalance extends TerminalCommand {
  private Terminal _terminal;
  DoShowTerminalBalance(Network context, Terminal terminal) {
    super(Label.SHOW_BALANCE, context, terminal);
    _terminal = terminal;
  }
  
  @Override
  protected final void execute() throws CommandException {
    _display.popup(Message.terminalPaymentsAndDebts(_terminal.getId(), Math.round(_terminal.getPayments()), Math.round(_terminal.getDebt())));
  }
}
