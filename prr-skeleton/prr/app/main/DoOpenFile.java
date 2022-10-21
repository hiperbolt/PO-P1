package prr.app.main;

import prr.core.NetworkManager;
import prr.core.exception.UnavailableFileException;
import prr.app.exception.FileOpenFailedException;
import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
//Add more imports if needed

/**
 * Command to open a file.
 */
class DoOpenFile extends Command<NetworkManager> {
  private NetworkManager _receiver;
  DoOpenFile(NetworkManager receiver)  {
    super(Label.OPEN_FILE, receiver);
    _receiver = receiver;
  }
  
  @Override
  protected final void execute() throws CommandException {
    // FIXME what should we do with catches?
      try {
      _receiver.load(Form.requestString("Nome do ficheiro?"));
      } catch (UnavailableFileException e) {
      }
  }
}
