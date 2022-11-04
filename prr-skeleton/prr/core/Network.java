package prr.core;

import java.io.Serializable;
import java.io.IOException;

import prr.core.exception.InvalidKeyException;
import prr.core.exception.DuplicateKeyException;
import prr.core.exception.UnknownKeyException;
import prr.core.exception.UnrecognizedEntryException;
import prr.core.notification.NotificationType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Class Network implements a network.
 */
public class Network implements Serializable {

  /** Serial number for serialization. */
  private static final long serialVersionUID = 202208091753L;
  private List<Client> _clients;

  // Should eventually be a Set to prevent duplication.
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
    _clients = new ArrayList<Client>();
    _terminals = new ArrayList<Terminal>();
    _tariffPlans = new ArrayList<TariffPlan>();
    _tariffPlans.add(new BasicPlan());
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

    // If a client with the same key does not exist, we create it and add it to the Network. We give it the first (base) tariff plan.
    Client c = new Client(id, name, taxID, _tariffPlans.get(0));
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
    TerminalType processedType;

    switch(ttype) {
      case "BASIC" -> processedType = TerminalType.BASIC;
      case "FANCY" -> processedType = TerminalType.FANCY;
      default -> throw new UnrecognizedEntryException(ttype);
    }

    // First, we check if the terminal id is in a valid format (if its length is exactly 6 and if it contains only numbers).
    if(id.length() != 6 || !id.matches("[0-9]+")){
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

    // We add the terminal to its owner's list of terminals.
    owner.addTerminal(returnTerminal);

    return returnTerminal;
  }

  /**
   * Register terminal and set its status.
   *
   * @param ttype - Terminal type (in TerminalType format)
   * @param id - Terminal id
   * @param ownerId - Owner client's id
   * @param status - Terminal status (TerminalMode)
   *
   * @throws UnrecognizedEntryException - If the provided terminal type is not recognized.
   * @throws InvalidKeyException - If the provided terminal id is not valid.
   * @throws DuplicateKeyException - If the provided terminal key already exists.
   * @throws UnknownKeyException - If the provided owner id does not exist.
   */
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

  /**
   * Check if a client with the provided id exists.
   *
   * @param id - Client id
   * @return True if the client exists, false otherwise.
   */
  public boolean clientExists(String id){
    for (Client c : _clients){
      if (c.getKey().equals(id)){
        return true;
      }
    }
    return false;
  }

  /**
   * Check if a terminal with the provided id exists.
   *
   * @param id - terminal id
   * @return True if the terminal exists, false otherwise.
   */
  public boolean terminalExists(String id){
    for (Terminal t : _terminals){
      if (t.getId().equals(id)){
        return true;
      }
    }
    return false;
  }

    /**
     * Get a client by its id.
     *
     * @param id - Client id
     * @return Client - The client with the provided id.
     */
  public Client clientById(String id){
    for (Client c : _clients){
      if (c.getKey().equals(id)){
        return c;
      }
    }
    return null;
  }

    /**
     * Get a terminal by its id.
     *
     * @param id - Terminal id
     * @return Terminal - The terminal with the provided id.
     */
  public Terminal terminalById(String id){
    for (Terminal t : _terminals){
      if (t.getId().equals(id)){
        return t;
      }
    }
    return null;
  }

  /**
   * Get all terminals.
   * @return List<Terminal> - List of all terminals.
   */
  public List<Terminal> getAllTerminals(){
    return _terminals;
  }

    /**
     * Get all clients.
     * @return List<Client> - List of all clients.
     */
  public List<Client> getAllClients(){
    return _clients;
  }

  /**
   * Get all communications.
   *
   * @return List<Communication> - List of all communications.
   */
  public List<String> getAllCommunications(){
    List<String> communications = new ArrayList<>();
    for (Terminal t : _terminals){
      for (Communication c : t.getMadeCommunications()){
        communications.add(c.toString());
      }
    }
    return communications;
  }

  /**
   * Get unused terminals (terminals which have neither received nor made any communication).
   *
   * @return List<Terminal> - List of all unused terminals.
   */
  public List<Terminal> getUnusedTerminals(){
    List<Terminal> unusedTerminals = new ArrayList<>();
    for(Terminal t : _terminals){
      if(t.isUnused()){
        unusedTerminals.add(t);
      }
    }
    return unusedTerminals;
  }

  /**
   * Add a terminal friend.
   *
   * @param terminalId - Terminal id
   * @param friend - Friend terminal id
   */
  public void addFriend(String terminalId, String friend) {
    Terminal _targetTerminal = terminalById(terminalId);
    Terminal _friendTerminal = terminalById(friend);
    _targetTerminal.addFriend(_friendTerminal);
  }

  /** Returns the communications started by all of a Client's terminals.
   *
   * @param clientId - Client id
   *
   * @return List<String> - toString of all communications started by the client's terminals.
   */
  public List<String> getClientMadeCommunications(String clientId){
    List<String> returnList = new ArrayList<>();
    for (Terminal t: clientById(clientId).getTerminals()){
      for (Communication c: t.getMadeCommunications()){
        returnList.add(c.toString());
      }
    }
    return returnList;
  }

  /** Returns the communications received by all of a Client's terminals.
   *
   * @param clientId - Client id
   *
   * @return List<String> - toString of all communications received by the client's terminals.
   */
  public List<String> getClientReceivedCommunications(String clientId){
    List<String> returnList = new ArrayList<>();
    for (Terminal t: clientById(clientId).getTerminals()){
      for (Communication c: t.getReceivedCommunications()){
        returnList.add(c.toString());
      }
    }
    return returnList;
  }

  /** Returns clients that do not have a current debt.
   *
   * @return List<String> - toString of all clients that do not have a current debt.
   */
  public List<String> getClientsWithoutDebts(){
    List<String> returnList = new ArrayList<>();
    for (Client c : _clients){
      if (c.calculateDebts() == 0){
        returnList.add(c.toString());
      }
    }
    return returnList;
  }

  
  /** 
   * @return List<String>
   */
  public List<String> getClientsWithDebts(){
    // First we're going to get all clients with debts.
    ArrayList<Client> inDebtClients = new ArrayList<>();
    for (Client c : _clients) {
      if (c.calculateDebts() > 0) {
        inDebtClients.add(c);
      }
    }
    // Then we're going to sort them by decreasing debt, or by increasing ID if they have the same debt.
    inDebtClients.sort((o1, o2) -> {
    if (o1.calculateDebts() == o2.calculateDebts()) {
        return o1.getKey().compareTo(o2.getKey());
    }
    return o2.calculateDebts() - o1.calculateDebts();
    });
    // Finally we're going to return the list of clients as strings.
      List<String> returnList = new ArrayList<>();
      for (Client c : inDebtClients){
        returnList.add(c.toString());
      }
      return returnList;
  }

  /** Return all terminals with positive balance.
   *
   * @return List<String> - toString of all terminals with positive balance.
   */
  public List<String> getTerminalsWithPositiveBalance(){
      ArrayList<String> positiveBalanceTerminals = new ArrayList<>();
        for (Terminal t : _terminals){
            //checks if terminal has positive balance
            if (t.getBalance() > 0){
              positiveBalanceTerminals.add(t.toString());
            }
        }
        return positiveBalanceTerminals;
    }

  
  /** 
   * gets the sum global payments
   * 
   * @return global payment
   */
  public long getGlobalPayments() {
    long total = 0;
    for (Client c: _clients) {
      total += c.calculatePayments();
    }
    return total;
  }

  
  /** 
   * gets the sum global debts
   * 
   * @return global debts
   */
  public long getGlobalDebts() {
    long total = 0;
    for (Client c: _clients) {
      total += c.calculateDebts();
    }
    return total;
  }
}

