package prr.core;

import java.io.Serializable;
import java.io.IOException;

import prr.core.exception.InvalidKeyException;
import prr.core.exception.DuplicateKeyException;
import prr.core.exception.UnknownKeyException;
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
  private List<TariffPlan> _tariffPlans;

  
  /**
   * Read text input file and create corresponding domain entities.
   * 
   * @param filename name of the text input file
   * @throws UnrecognizedEntryException if some entry is not correct
   * @throws IOException if there is an IO erro while processing the text file
   */
  void importFile(String filename) throws UnrecognizedEntryException, IOException {
    Parser parser = new Parser(this);
    parser.parseFile(filename);
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
     * @param ttype  - Terminal type
     * @param id    - Terminal ID
     * @param ownerId - Owner client's ID
     *
     * @return Terminal - The newly created terminal object.
     * @throws DuplicateKeyException - If the provided terminal key already exists.
     */
  public Terminal registerTerminal(String ttype, String id, String ownerId) throws DuplicateKeyException, InvalidKeyException, UnknownKeyException, UnrecognizedEntryException {
    Terminal returnTerminal = null;
    TerminalType processedType = null;

    switch(ttype) {
      case "BASIC" -> processedType = TerminalType.BASIC;
      case "FANCY" -> processedType = TerminalType.FANCY;
      default -> throw new UnrecognizedEntryException(ttype);
    }

    // First, we check if the terminal id is in a valid format (if its length is at least 6 and if it contains only numbers).
    if(id.length() < 6 || !id.matches("[0-9]+")){
      throw new InvalidKeyException(id);
    }

    // We check if the terminal already exists, and throw an exception if it does.
    for(Terminal terminal : _terminals){
      if(terminal.getId().equals(id)){
        throw new DuplicateKeyException();
      }
    }

    // We check the Client exists.
    Client owner = clientById(ownerId);
    if (owner == null){
      throw new UnknownKeyException(ownerId);
    }

    // With all checks passed, we create the terminal and add it to the Network.
    if(processedType == TerminalType.BASIC){
      Terminal t = new BasicTerminal(id, clientById(ownerId));
      _terminals.add(t);
      returnTerminal = t;
    }
    else if(processedType == TerminalType.FANCY){
      Terminal t = new FancyTerminal(id, clientById(ownerId));
      _terminals.add(t);
      returnTerminal = t;
    }
    return returnTerminal;
  }

  void registerTerminalWithStatus(String ttype, String id, String ownerId, String status) throws UnrecognizedEntryException, InvalidKeyException, DuplicateKeyException, UnknownKeyException {
    Terminal t = registerTerminal(ttype, id, ownerId);
    switch (status){
      case "ON" -> t.setOnIdle();
      case "OFF"-> t.turnOff();
      case "SILENCE" -> t.setOnSilent();
      default -> throw new UnrecognizedEntryException(status);
    }
    _terminals.add(t);
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


