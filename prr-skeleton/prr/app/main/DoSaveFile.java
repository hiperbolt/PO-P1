package prr.app.main;
import java.io.FileNotFoundException;
import java.io.IOException;

import prr.app.exception.FileOpenFailedException;
import pt.tecnico.uilib.menus.CommandException;
import prr.core.NetworkManager;
import prr.core.exception.MissingFileAssociationException;
import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;

/**
 * Command to save a file.
 */
class DoSaveFile extends Command<NetworkManager> {
  DoSaveFile(NetworkManager receiver) {
    super(Label.SAVE_FILE, receiver);
  }
  
  @Override
  protected final void execute()  throws FileOpenFailedException{
    try {
      if (_receiver.getFilename() != null){
        _receiver.save();
      } else {
        _receiver.saveAs(Form.requestString(Message.newSaveAs()));
      }
    } catch (MissingFileAssociationException | IOException e) {
      throw new FileOpenFailedException(e);
    }
    }


  }
