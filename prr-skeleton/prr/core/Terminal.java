package prr.core;

import prr.core.notification.Notification;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;


/**
 * Abstract Terminal.
 */
abstract public class Terminal implements Serializable /* FIXME maybe add more interfaces */{

  /** Serial number for serialization. */
  private static final long serialVersionUID = 202208091753L;

  private String _id;
  private double _debt;
  private double _payments;
  private TerminalMode _mode;

  private List<Terminal> _friends;
  private Client _owner;
  private List<Communication> _madeCommunications;
  private List<Communication> _receivedCommunications;
  private Communication _ongoingCommunication;
  private List<Notification> _toNotify;


  /**
   * Abstract Terminal constructor.
   *
   * @param id - Terminal key
   * @param owner - Owner client's id
   */
  public Terminal(String id, Client owner){
    _id = id;
    _owner = owner;
    _debt = 0;
    _payments = 0;
    _mode = TerminalMode.ON;
    _madeCommunications = new ArrayList<Communication>();
    _receivedCommunications = new ArrayList<Communication>();
    _ongoingCommunication = null;
    _toNotify = new ArrayList<Notification>();
  }

  /**
   * Creates and sends an SMS (if terminal is not BUSY or OFF).
   *
   * @param outboundTerminal Destination terminal
   * @param message message string
   **/
  public void makeSMS(Terminal outboundTerminal, String message){
    // A Terminal is only capable of making a text communication if it is not busy or off. We check for that.
    if (this.canStartCommunication()){
      // Then, we offer the communication to the outbound terminal. Possibly an exception may arise if the outbound terminal is OFF.
      TextCommunication newTextComm = new TextCommunication(outboundTerminal, this, message);
      outboundTerminal.acceptSMS(newTextComm);
      // If the communication is successful, we add it to _madeCommunications
      _madeCommunications.add(newTextComm);
    }
  }

  /**
   * If terminal is not OFF, receives an SMS.
   *
   * @param communication Received communication.
   **/
  protected void acceptSMS(Communication communication){
    // A terminal can only *not* receive an SMS when it is off, so we check for that.
    // FIXME maybe raise an exception?
    if(this._mode != TerminalMode.OFF) {
      this._receivedCommunications.add(communication);
    }
  }


  /**
   * Checks if this terminal can end the current interactive communication.
   *
   * @return true if this terminal is busy (i.e., it has an active interactive communication) and
   *          it was the originator of this communication.
   **/
  public boolean canEndCurrentCommunication() {
    if (_mode == TerminalMode.BUSY && this._ongoingCommunication.getFrom() == this){
      return true;
    }
    return false;
  }
  
  /**
   * Checks if this terminal can start a new communication.
   *
   * @return true if this terminal is neither off neither busy, false otherwise.
   **/
  public boolean canStartCommunication() {
    if (_mode != TerminalMode.OFF && _mode != TerminalMode.BUSY){
      return true;
    }
    return false;
  }

  /**
   * If possible, sets on Idle.
   *
   * @return true if it succeeds, false if not.
   **/
  public boolean setOnIdle(){
    if (_mode == TerminalMode.OFF || _mode == TerminalMode.SILENCE || _mode == TerminalMode.BUSY){
      _mode = TerminalMode.ON;
      return true;
    }
    return false;
  }

  /**
   * If possible, sets on Silent.
   *
   * @return true if it succeeds, false if not.
   **/
  public boolean setOnSilent(){
    if (_mode == TerminalMode.ON || _mode == TerminalMode.BUSY){
      _mode = TerminalMode.SILENCE;
      return true;
    }
    return false;
  }

  /**
   * If possible, turns off terminal.
   *
   * @return true if it succeeds, false if not.
   **/
  public boolean turnOff(){
    if (_mode == TerminalMode.ON || _mode == TerminalMode.SILENCE){
        _mode = TerminalMode.OFF;
        return true;
    }
    return false;
  }
  
  public String getId() {
    return _id;
  }
  public boolean removeFriend(String friendId){
    return _friends.removeIf(t -> t.getId().equals(friendId));
  }

  public boolean addFriend(Terminal friend){
    return _friends.add(friend);
  }

  public boolean isUnused(){
    if (_madeCommunications.isEmpty() && _receivedCommunications.isEmpty()){
      return true;
    }
    return false;
  }

  // terminalType|terminalId|clientId|terminalStatus|balance-paid|balance-debts|friend1,...,friend

  public String toString(){
    String terminalType = "";
    if (this instanceof BasicTerminal){
      terminalType = "BASIC";
    }
    else if (this instanceof FancyTerminal){
      terminalType = "FANCY";
    }
    String terminalId = this._id;
    String clientId = this._owner.getKey();
    String terminalStatus = this._mode.toString();
    String balancePaid = Double.toString(this._payments);
    String balanceDebts = Double.toString(this._debt);
    String friends = "";

    // Now we sort terminal friends by growing id
    if (!_friends.isEmpty()){
      ArrayList<Terminal> sortedFriends = new ArrayList<Terminal>();
      for (Terminal friend : this._friends){
        if (sortedFriends.isEmpty()){
          sortedFriends.add(friend);
        }
        else {
          for (int i = 0; i < sortedFriends.size(); i++){
            if (friend._id.compareTo(sortedFriends.get(i)._id) < 0){
              sortedFriends.add(i, friend);
              break;
            }
            else if (i == sortedFriends.size() - 1){
              sortedFriends.add(friend);
              break;
            }
          }
        }
      }
      for (Terminal friend : sortedFriends){
        friends += friend._id + ",";
      }
      friends = friends.substring(0, friends.length() - 1);
    }
  else{
    friends = "";
  }
    return terminalType + "|" + terminalId + "|" + clientId + "|" + terminalStatus + "|" + balancePaid + "|" + balanceDebts + "|" + friends;
  };

  public int getBalance() {
    return 0; // Temporary while balances and communications are not implemented.
  }

}
