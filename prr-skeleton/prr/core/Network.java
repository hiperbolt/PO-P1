package prr.core;

import java.io.Serializable;
import java.io.IOException;
import prr.core.exception.UnrecognizedEntryException;
import java.util.ArrayList;
import java.util.List;

// FIXME add more import if needed (cannot import from pt.tecnico or prr.app)

/**
 * Class Store implements a store.
 */
public class Network implements Serializable {

  /** Serial number for serialization. */
  private static final long serialVersionUID = 202208091753L;

  private List<Communication> _communications;
  private List<Client> _clients;
  private List<Terminal> _terminals;
  private List<TariffPlan> _tariffPlans;
  
  // FIXME define attributes
  // FIXME define contructor(s)
  // FIXME define methods
  
  /**
   * Read text input file and create corresponding domain entities.
   * 
   * @param filename name of the text input file
   * @throws UnrecognizedEntryException if some entry is not correct
   * @throws IOException if there is an IO erro while processing the text file
   */
  void importFile(String filename) throws UnrecognizedEntryException, IOException {
    //FIXME implement method
  }
  public Network(){
    _communications = new ArrayList<Communication>();
    _clients = new ArrayList<Client>();
    _terminals = new ArrayList<Terminal>();
    _tariffPlans = new ArrayList<TariffPlan>();
  }

  /**
   * Registers a new clients. All checks are done app-side, we simply register a new client object.
   *
   * @param c - Client object.
   */
  public void registerClient(Client c) {
    _clients.add(c);
  }

  /**
   * Registers a new terminal. All checks are done app-side, we simply register a new terminal object.
   *
   * @param t - Terminal object
   */
  public void registerTerminal(Terminal t){
    _terminals.add(t);
  }

  public List<Terminal> getAllTerminals(){
    return _terminals;
  }
  public List<Client> getAllClients(){
    return _clients;
  }
  public boolean clientExists(String id){
    for (Client c : _clients){
      if (c.getKey().equals(id)){
        return true;
      }
    }
    return false;
  }
  public boolean terminalExists(String id){
    for (Terminal t : _terminals){
      if (t.getId().equals(id)){
        return true;
      }
    }
    return false;
  }


  public Client clientById(String id){
    for (Client c : _clients){
      if (c.getKey().equals(id)){
        return c;
      }
    }
    return null;
  }

  public Terminal terminalById(String id){
    for (Terminal t : _terminals){
      if (t.getId().equals(id)){
        return t;
      }
    }
    return null;
  }
}

