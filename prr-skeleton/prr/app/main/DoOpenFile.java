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
  DoOpenFile(NetworkManager receiver)  {
    super(Label.OPEN_FILE, receiver);
  }
  
  @Override
  protected final void execute() throws CommandException {
    // FIXME what should we do with catches?
      try {
      _receiver.load(Form.requestString(Message.openFile()));
      } catch (UnavailableFileException e) {
          throw new FileOpenFailedException(e);
      }
  }
}
