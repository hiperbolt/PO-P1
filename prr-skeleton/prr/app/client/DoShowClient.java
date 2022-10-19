package prr.app.client;

import prr.core.Network;
import prr.app.exception.UnknownClientKeyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
//FIXME add more imports if needed

/**
 * Show specific client: also show previous notifications.
 */
class DoShowClient extends Command<Network> {
  private Network _receiver;
  DoShowClient(Network receiver) {
    super(Label.SHOW_CLIENT, receiver);
    _receiver = receiver;
  }
  
  @Override
  protected final void execute() throws CommandException {
    // FIXME posso aceder a este codigo diretamente no client ou tenho de fazer sempre pela network?
    _receiver.showClient();
  }
}
