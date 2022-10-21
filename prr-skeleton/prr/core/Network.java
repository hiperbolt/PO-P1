package prr.core;

import java.io.Serializable;
import java.io.IOException;
import prr.core.exception.InvalidKeyException;
import prr.core.exception.DuplicateKeyException;
import prr.core.exception.UnrecognizedEntryException;

import java.util.ArrayList;
import java.util.List;

/**
 * Class Store implements a store.
 */
public class Network implements Serializable {

  /** Serial number for serialization. */
  private static final long serialVersionUID = 202208091753L;
  private List<Communication> _communications;
  private List<Client> _clients;
  private List<Terminal> _terminals;
  private List<TariffPlan> _tariffPlans; //FIXME shoud we store the tariff plans in the network?

  
  /**
   * Read text input file and create corresponding domain entities.
   * 
   * @param filename name of the text input file
   * @throws UnrecognizedEntryException if some entry is not correct
   * @throws IOException if there is an IO erro while processing the text file
   */
  void importFile(String filename) throws UnrecognizedEntryException, IOException {
    // FIXME implement importFile method
  }
  public Network(){
    _communications = new ArrayList<Communication>();
    _clients = new ArrayList<Client>();
    _terminals = new ArrayList<Terminal>();
    _tariffPlans = new ArrayList<TariffPlan>();
  }

  /**
   * Register new client.
   * @param id - Client ID
   * @param name - Client name
   * @param taxID - Client tax ID
   *
   * @throws DuplicateKeyException - If the provided client key already exists.
   */
  public void registerClient(String id, String name, int taxID) throws DuplicateKeyException {
    // We check if the client already exists, and throw an exception if it does.
    for(Client client : _clients){
      if(client.getKey().equals(id)){
        throw new DuplicateKeyException();
      }
    }

    // If a client with the same key does not exist, we create it and add it to the Network.
    Client c = new Client(id, name, taxID);
    _clients.add(c);
  }

    /**
     * Register new terminal.
     *
     * @param type  - Terminal type
     * @param id    - Terminal ID
     * @param ownerId - Owner client's ID
     *
     * @return Terminal - The newly created terminal object.
     * @throws DuplicateKeyException - If the provided terminal key already exists.
     */
  public Terminal registerTerminal(TerminalType type, String id, String ownerId) throws DuplicateKeyException, InvalidKeyException {
    Terminal _returnTerminal = null;

    // First, we check if the terminal id is in a valid format (if its length is at least 6 and if it contains only numbers).
    if(id.length() < 6 || !id.matches("[0-9]+")){
      throw new InvalidKeyException();
    }

    // We check if the terminal already exists, and throw an exception if it does.
    for(Terminal terminal : _terminals){
      if(terminal.getId().equals(id)){
        throw new DuplicateKeyException();
      }
    }

    // With all checks passed, we create the terminal and add it to the Network.
    if(type == TerminalType.BASIC){
      Terminal t = new BasicTerminal(id, clientById(ownerId));
      _terminals.add(t);
      _returnTerminal = t;
    }
    else if(type == TerminalType.FANCY){
      Terminal t = new FancyTerminal(id, clientById(ownerId));
      _terminals.add(t);
      _returnTerminal = t;
    }
    return _returnTerminal;
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

  public List<Terminal> getAllTerminals(){
    return _terminals;
  }

  public List<Client> getAllClients(){
    return _clients;
  }

  public List<Communication> getAllCommunications(){
    return _communications;
  }

  public List<Terminal> getUnusedTerminals(){
    List<Terminal> unusedTerminals = new ArrayList<>();
    for(Terminal t : _terminals){
      if(t.isUnused()){
        unusedTerminals.add(t);
      }
    }
    return unusedTerminals;
  }

  public void addFriend(String terminalId, String friend) {
    Terminal _targetTerminal = terminalById(terminalId);
    Terminal _friendTerminal = terminalById(friend);
    _targetTerminal.addFriend(_friendTerminal);
  }
}


