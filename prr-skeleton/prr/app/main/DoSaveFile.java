package prr.app.main;
import java.io.FileNotFoundException;
import java.io.IOException;
import pt.tecnico.uilib.menus.CommandException;
import prr.core.NetworkManager;
import prr.core.exception.MissingFileAssociationException;
import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;

/**
 * Command to save a file.
 */
class DoSaveFile extends Command<NetworkManager> {
  private NetworkManager _receiver;
  DoSaveFile(NetworkManager receiver) {
    super(Label.SAVE_FILE, receiver);
  }
  
  @Override
  protected final void execute()  throws CommandException {
    // FIXME what should we do with catches?
    try {
      _receiver.save();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (MissingFileAssociationException e) {
      _receiver.saveAs(Form.requestString("nome do ficheiro"));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
