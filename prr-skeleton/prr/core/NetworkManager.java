package prr.core;

import java.io.IOException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import prr.core.exception.ImportFileException;
import prr.core.exception.MissingFileAssociationException;
import prr.core.exception.UnavailableFileException;
import prr.core.exception.UnrecognizedEntryException;

/**
 * Manage access to network and implement load/save operations.
 */
public class NetworkManager {

  /** The network itself. */
  private Network _network = new Network();
  private String _filename = null;
  public Network getNetwork() {
    return _network;
  }
  
  /**
   * @param filename name of the file containing the serialized application's state
   *        to load.
   * @throws UnavailableFileException if the specified file does not exist or there is
   *         an error while processing this file.
   */ 
  public void load(String filename) throws UnavailableFileException {
    try{
      FileInputStream fileInputStream = new FileInputStream(filename);
      ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
      _network = (Network) objectInputStream.readObject();
      this._filename = filename;
      objectInputStream.close();
    } catch (FileNotFoundException e) {
      throw new UnavailableFileException(filename);
    } catch (IOException e) {
      throw new UnavailableFileException(filename);
    } catch (ClassNotFoundException e) {
      throw new UnavailableFileException(filename);
    }
  }
  
  /**
   * Saves the serialized application's state into the file associated to the current network.
   *
   * @throws FileNotFoundException if for some reason the file cannot be created or opened. 
   * @throws MissingFileAssociationException if the current network does not have a file.
   * @throws IOException if there is some error while serializing the state of the network to disk.
   */
  public void save() throws FileNotFoundException, MissingFileAssociationException, IOException {
    if (_filename == null) {
      throw new MissingFileAssociationException("");
    }
    try{
      saveAs(_filename);
    } catch (FileNotFoundException e) {
      throw new FileNotFoundException();
    } catch (IOException e) {
      throw new IOException();
    }
  }
  
  /**
   * Saves the serialized application's state into the specified file. The current network is
   * associated to this file.
   *
   * @param filename the name of the file.
   * @throws FileNotFoundException if for some reason the file cannot be created or opened.
   * @throws MissingFileAssociationException if the current network does not have a file.
   * @throws IOException if there is some error while serializing the state of the network to disk.
   */
  public void saveAs(String filename) throws FileNotFoundException, IOException{
    try{
      _filename = filename;
      FileOutputStream fileOutputStream
        = new FileOutputStream(filename);
      ObjectOutputStream objectOutputStream 
        = new ObjectOutputStream(fileOutputStream);
      objectOutputStream.writeObject(_network);
      objectOutputStream.flush();
      objectOutputStream.close();
    } catch (FileNotFoundException e) {
      throw new FileNotFoundException(filename);
    } catch (IOException e) {
      throw new IOException(filename);
    }
  }

  
  /**
   * Read text input file and create domain entities..
   * 
   * @param filename name of the text input file
   *
   * @throws ImportFileException - In case something goes wrong wit the file importing.
   */
  public void importFile(String filename) throws ImportFileException {
    try {
      _network.importFile(filename);
    } catch (IOException | UnrecognizedEntryException e) {
      throw new ImportFileException(filename, e);
    }
  }  

  public boolean hasFile() {
    return _filename != null;
  }

  public String getFilename() {
    return _filename;
  }
}
