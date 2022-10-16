package prr.core;

import java.io.Serializable;
import java.io.IOException;
import prr.core.exception.UnrecognizedEntryException;

import java.util.ArrayList;
import java.util.List;
import prr.core.TerminalType;

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
  void importFile(String filename) throws UnrecognizedEntryException, IOException /* FIXME maybe other exceptions */  {
    //FIXME implement method
  }
  public Network(){
    _communications = new ArrayList<Communication>();
    _clients = new ArrayList<Client>();
    _terminals = new ArrayList<Terminal>();
    _tariffPlans = new ArrayList<TariffPlan>();
  }
  public void registerClient(int id, String name, String address, int vat, int phone) throws DuplicateClientKeyException, InvalidClientKeyException{
    for (Client c : _clients){
      if (c.getId() == id){
        throw new DuplicateClientKeyException(id);
      }
    }
    if (id < 0){
      throw new InvalidClientKeyException(id);
    }
    Client c = new Client(id, name, address, vat, phone);
    _clients.add(c);
  }
  public void registerTerminal(Terminal terminal){
    _terminals.add(terminal);
  }
  public List<Terminal> getAllTerminals(){
    return _terminals;
  }
  public List<Client> getAllClients(){
    return _clients;
  }
  public boolean clientExists(String id){
    for (Client c : _clients){
      if (c.getId().equals(id)){
        return true;
      }
    }
    return false;
  }
  public TerminalType stringToType(String type){
    if (type.equals("FANCY")){
      return TerminalType.FANCY;
    }
    if (type.equals("BASIC")){
      return TerminalType.BASIC;
    }
  }
  public Client clientById(String id){
    for (Client c : _clients){
      if (c.getId().equals(id)){
        return c;
      }
    }
    return null;
  }
}

