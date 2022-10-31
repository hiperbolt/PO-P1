package prr.core;

import prr.core.notification.Notification;
import prr.core.notification.NotificationType;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;


/**
 * Abstract Terminal.
 */
abstract public class Terminal implements Serializable {

  /** Serial number for serialization. */
  private static final long serialVersionUID = 202208091753L;

  private String _id;
  private double _debt;
  private double _payments;
  private TerminalMode _mode;

  private Set<Terminal> _friends;
  private Client _owner;
  private List<Communication> _madeCommunications;
  private List<Communication> _receivedCommunications;
  private Communication _ongoingCommunication;
  private List<CommunicationAttempt> _toNotify;


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
    _friends = new HashSet<Terminal>();
    _madeCommunications = new ArrayList<Communication>();
    _receivedCommunications = new ArrayList<Communication>();
    _ongoingCommunication = null;
    _toNotify = new ArrayList<CommunicationAttempt>();
  }

  /**
   * Create a communication attempt. It will be store in the toNotify list, and will be used when our Terminal changes mode.
   * It's protected because it's only used by the subclasses.
   *
   * @param mode - Our terminal mode at the time of the communication attempt.
   * @param from - The terminal that is trying to communicate with us.
   * @param c - The communication that is being attempted.
   */
  protected void createAttempt(TerminalMode mode, Terminal from, Communication c){
    CommunicationAttempt attempt = new CommunicationAttempt(mode,this, from, c);
    _toNotify.add(attempt);
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
      // We check if the terminal is our friend.

      TextCommunication newTextComm = new TextCommunication(outboundTerminal, this, _friends.contains(outboundTerminal), message);
      outboundTerminal.acceptSMS(newTextComm);
      // If the communication is successful, we add it to _madeCommunications
      _madeCommunications.add(newTextComm);
      // And we add its value to our debt.
        _debt += newTextComm.getCost();
    }
  }

  /**
   * If terminal is not OFF, receives an SMS.
   *
   * @param communication Received communication.
   **/
  protected void acceptSMS(Communication communication){
    // A terminal can only *not* receive an SMS when it is off, so we check for that.
    // FIXME maybe raise an exception? What should we do?
    if(this._mode == TerminalMode.OFF) {
      // Since the terminal is off, we create an attempt to notify the sending terminal when our state changes.
      createAttempt(TerminalMode.OFF, communication.getFrom(), communication);
    }
    else{
      // If the terminal is not off, we add the communication to the received communications list.
      _receivedCommunications.add(communication);
    }
  }

  public void makeVoiceCall(Terminal to){
    // A terminal can only make a voice call if it is not busy or off.
  }

  /**
   * Accepts a voice call. The terminal mode is changed, and the communication is added to the received communications list and to the ongoing communication attribute.
   * This communication will end when endOnGoingCommunication is called.
   *
   * @param communication
   */
  protected void acceptVoiceCall(Communication communication){
    // A terminal can only receive a voice call if it is IDLE (ON).
    if(this._mode == TerminalMode.ON){
      // We accept the communication.
      _receivedCommunications.add(communication);
      // We set the ongoing communication to the one we just received.
      _ongoingCommunication = communication;
      // We set our mode to BUSY.
      _mode = TerminalMode.BUSY;
    }
    else{
      // If we are not IDLE, we create an attempt to notify the sending terminal when our state changes.
      createAttempt(this._mode, communication.getFrom(), communication);
    }

  }

  /**
   * Checks if this terminal can end the current interactive communication.
   *
   * @return true if this terminal is busy (i.e., it has an active interactive communication) and
   *          it was the originator of this communication.
   **/
  public boolean canEndCurrentCommunication() {
    return _mode == TerminalMode.BUSY && this._ongoingCommunication.getFrom() == this;
  }
  
  /**
   * Checks if this terminal can start a new communication.
   *
   * @return true if this terminal is neither off neither busy, false otherwise.
   **/
  public boolean canStartCommunication() {
    return _mode != TerminalMode.OFF && _mode != TerminalMode.BUSY;
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
    if (_friends.isEmpty()){
      return false;
    }

    return _friends.removeIf(t -> t.getId().equals(friendId));
  }

  public boolean addFriend(Terminal friend){
    return _friends.add(friend);
  }

  public boolean isUnused(){
    return _madeCommunications.isEmpty() && _receivedCommunications.isEmpty();
  }

  public int getBalancePaid(){
    return (int) Math.round(_payments);
  }

  public int getBalanceDebt(){
    return (int) Math.round(_debt);
  }

  /**
   * Gets ongoing communication.
   *
   * @return Communication - ongoing communication
   */
    public Communication getOngoingCommunication() {
        return _ongoingCommunication;
    }


  // terminalType|terminalId|clientId|terminalStatus|balance-paid|balance-debts|friend1,...,friend
  @Override // FIXME make sure this makes sense to be an override.
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
    String terminalStatus;
    if (_mode == TerminalMode.ON){
      terminalStatus = "IDLE";
    } else {
      terminalStatus = this._mode.toString();
    }
    String balancePaid = Integer.toString(this.getBalancePaid());
    String balanceDebts = Integer.toString(this.getBalanceDebt());
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
      friends = "|" + friends.substring(0, friends.length() - 1);
    }
  else{
    friends = "";
  }
    return terminalType + "|" + terminalId + "|" + clientId + "|" + terminalStatus + "|" + balancePaid + "|" + balanceDebts + friends;
  }

  public int getBalance() {
    return 0; // Temporary while balances and communications are not implemented.
  }

  public TerminalMode getMode(){
    return _mode;
  }

  public Client getClient() {
    return _owner;
  }
}

