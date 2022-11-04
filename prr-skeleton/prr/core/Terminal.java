package prr.core;

import prr.core.exception.TerminalBusyException;
import prr.core.exception.TerminalOffException;
import prr.core.exception.TerminalSilentException;
import prr.core.notification.Notification;
import prr.core.notification.NotificationType;

import java.io.Serializable;
import java.util.*;


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
  private TerminalMode _previousMode; // Mode before communication was started.
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
   *
   * @param from - The terminal that is trying to communicate with us.
   * @param c - The communication that is being attempted.
   */
  public void createAttempt(Terminal from, Communication c){
    // We create the attempt with our current Terminal mode and with the provided arguments.
    CommunicationAttempt attempt = new CommunicationAttempt(this._mode,this, from, c);
    // We add it to the toNotify list.
    _toNotify.add(attempt);
  }

  /**
   * Creates and sends an SMS.
   *
   * @param outboundTerminal Destination terminal
   *
   * @param message message string
   *
   * @throws TerminalBusyException if the terminal is busy
   * @throws TerminalOffException if the terminal is off
   */
  public void makeSMS(Terminal outboundTerminal, String message) throws TerminalOffException, TerminalBusyException {
    // We check if we are in the right mode to make an SMS.
    if(this.canStartCommunication()){
      // We create the SMS.
      TextCommunication newTextComm = new TextCommunication(outboundTerminal, this, this.isFriend(outboundTerminal), message);
      // We try to send the SMS.
      outboundTerminal.acceptSMS(newTextComm);
      // If we are here, the SMS was sent successfully.
      // We add the SMS to our made communications.
      this._madeCommunications.add(newTextComm);
      _debt += newTextComm.computeCost(this.getClient().getTariffPlan());
    } else {
      switch (this.getMode()) {
        case OFF -> throw new TerminalOffException(this.getId());
        case BUSY -> throw new TerminalBusyException(this.getId());
      }
    }
  }

  /**
   * Receives an SMS.
   *
   * @param communication Received communication.
   *
   **/
  protected void acceptSMS(Communication communication) throws TerminalOffException{
    // We check if we are in the right mode to receive an SMS.
    if(this.getMode() != TerminalMode.OFF){
      // If we are here, we are in the right mode.
      // We add the SMS to our received communications.
      this._receivedCommunications.add(communication);
    } else {
      // If we are here, we are not in the right mode.
      // We create an attempt and throw the exception.
      this.createAttempt(communication.getFrom(), communication);
      throw new TerminalOffException(this.getId());
    }
  }

  /**
   * Creates and sends a Voice call.
   * Our terminal mode is changed to BUSY, the communication is set as the onGoing, and is added to our made communications.
   *
   * @param
   *
   */
  public void makeVoiceCall(Terminal outboundTerminal) throws TerminalOffException, TerminalBusyException, TerminalSilentException {
    // We check if we are in the right mode to make a Voice call.
    if(this.canStartCommunication()){
      // We create the Voice call.
      VoiceCommunication newVoiceComm = new VoiceCommunication(outboundTerminal, this, this.isFriend(outboundTerminal));
      // We try to send the Voice call.
      // This might throw an exception if the destination is Off, Busy or Silent.
      outboundTerminal.acceptVoiceCall(newVoiceComm);
      // If we are here, the Voice call was started successfully.
      // We set the voice call as our onGoing communication.
      this.setOnGoingCommunication(newVoiceComm);
    } else {
      switch (this.getMode()) {
        case OFF -> throw new TerminalOffException(this.getId());
        case BUSY -> throw new TerminalBusyException(this.getId());
      }
    }
  }

  /**
   * Accepts a voice call. The terminal mode is changed, and the communication is added to the received communications list and to the ongoing communication attribute.
   * This communication will end when endOnGoingCommunication is called.
   *
   * @param communication
   */
  protected void acceptVoiceCall(VoiceCommunication communication) throws TerminalOffException, TerminalSilentException, TerminalBusyException {
    // We check if we are in the right mode to receive a Voice call.
    if(this.getMode() == TerminalMode.ON){
      // If we are here, we are in the right mode.
      // We set the voice call as our onGoing communication.
      this.setOnGoingCommunication(communication);
    } else {
      // If we are here, we are not in the right mode.
      // We create an attempt and throw the exception.
      this.createAttempt(communication.getFrom(), communication);
      switch (this.getMode()) {
        case OFF -> throw new TerminalOffException(this.getId());
        case BUSY -> throw new TerminalBusyException(this.getId());
        case SILENCE -> throw new TerminalSilentException(this.getId());
      }
    }
  }

  public abstract void makeVideoCall(Terminal to) throws TerminalOffException, TerminalSilentException, TerminalBusyException, prr.core.exception.UnsupportedOperationException;

  protected abstract void acceptVideoCall(VideoCommunication communication) throws TerminalOffException, TerminalSilentException, TerminalBusyException, prr.core.exception.UnsupportedOperationException;

  public void setOnGoingCommunication(InteractiveCommunication c) {
    // We save our current mode in the previousMode attribute.
    this._previousMode = this._mode;
    // We set the communication as our onGoing communication.
    this._ongoingCommunication = c;
    // We change our mode to BUSY.
    this._mode = TerminalMode.BUSY;
  }

  /**
   * Checks if this terminal can end the current interactive communication.
   *
   * @return true if this terminal is busy (i.e., it has an active interactive communication) and
   *          it was the originator of this communication.
   **/
  public boolean canEndCurrentCommunication() {
    return _mode == TerminalMode.BUSY && _ongoingCommunication.getFrom() == this;
  }
  
  /**
   * Checks if this terminal can start a new interactive or SMS communication (same requirements).
   *
   * @return true if this terminal is neither off neither busy neither has an active interactive communication. False otherwise.
   **/
  public boolean canStartCommunication() {
    return _mode != TerminalMode.OFF && _mode != TerminalMode.BUSY;
  }

  /**
   * Gets current Terminal mode.
   *
   * @return Terminal mode.
   */
  public TerminalMode getMode(){
    return _mode;
  }

  public void receiveNotification(Notification n){
    // We check if our client has failed contact reception active.
    if(this.getClient().getNotificationReception()){
      // If we are here, our client has failed contact reception active.
      // We add the notification to our client's notifications.
      this.getClient().addNotification(n);
    }
  }

  public void sendNotifications(TerminalMode to) {
    // FIXME there is probably a more elegant way to do this
    // We iterate over the toNotify list.
    for (CommunicationAttempt attempt : _toNotify) {
      TerminalMode from = attempt.getMode();
      Notification n;
      NotificationType type = null;
      switch (from) {
        case OFF -> {
          // We are either going from OFF to ON/IDLE or from OFF to SILENCE.
          if (to == TerminalMode.ON) {
            // We are going from OFF to ON/IDLE.
            type = NotificationType.O2I;
          } else {
            // We are going from OFF to SILENCE.
            type = NotificationType.O2S;
          }
        }
        case BUSY -> {
          // We are going from BUSY to ON/IDLE
            type = NotificationType.B2I;
          }
        case SILENCE -> {
          // We are going from SILENCE to ON/IDLE
          type = NotificationType.S2I;
        }
      }
      // We create the notification.
      n = new Notification(type,this, attempt.getTo());
      // We send the notification.
      attempt.getFrom().receiveNotification(n);
    }
    // We clear the toNotify list.
    _toNotify.clear();
  }



  /**
   * If possible, sets on Idle.
   *
   * @return true if it succeeds, false if not.
   **/
  public boolean setOnIdle(){
    if (_mode == TerminalMode.OFF || _mode == TerminalMode.SILENCE || _mode == TerminalMode.BUSY){
      _mode = TerminalMode.ON;
      sendNotifications(TerminalMode.ON);
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
      sendNotifications(TerminalMode.SILENCE);
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
    if (_friends.contains(friend) || friend == this){
      return false;
    }
    return _friends.add(friend);
  }

  public boolean isFriend(Terminal terminal){
    return _friends.contains(terminal);
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

    // Now we sort terminal friends by growing id and add them to the string.
    if (!_friends.isEmpty()) {
      List<Terminal> sortedFriends = new ArrayList<>(_friends);
      sortedFriends.sort(Comparator.comparing(Terminal::getId));
      for (Terminal t : sortedFriends) {
        friends += t.getId() + ",";
      }
      // We remove the last comma.
      friends = friends.substring(0, friends.length() - 1);

      if(!friends.isEmpty()){
        friends = "|" + friends;
      }
    }

    return terminalType + "|" + terminalId + "|" + clientId + "|" + terminalStatus + "|" + balancePaid + "|" + balanceDebts + friends;
  }

  public double getBalance() {
    return _payments - _debt;
  }



  public Client getClient() {
    return _owner;
  }

  public List<Communication> getMadeCommunications() {
      return _madeCommunications;
  }

  public List<Communication> getReceivedCommunications() {
      return _receivedCommunications;
  }

  public double getDebt() {
    return _debt;
  }

  public double getPayments() {
    return _payments;
  }

  public void payCommunication(Communication communication){
    // First we check if the communication is finished, hasn't been paid yet and the terminal is the one that made it.
    if (!communication.isOngoingCommunication() && !communication.isPaid() && communication.getFrom().equals(this)) {
      _payments += communication.getCost();
      _debt -= communication.getCost();
      communication.setPaid(true);
    }
  }

  public Communication getCommunicationByID(int communication) throws AlreadyPaidException {
    // FIXME
    for (Communication c : _madeCommunications) {
      if (c.getId() == communication) {
        return c;
      }
    }
    for (Communication c : _receivedCommunications) {
      if (c.getId() == communication) {
        return c;
      }
    }
    return null;
  }


  public double endOngoingCommunication(int duration) {
    double cost =  _ongoingCommunication.end(duration, _owner.getTariffPlan());
    switch (_previousMode) {
      case ON:
        setOnIdle();
        break;
      case SILENCE:
        setOnSilent();
        break;
    }
    _ongoingCommunication.getTo().endOngoingCommunicationAsReceiver(duration, _owner.getTariffPlan());
    _madeCommunications.add(_ongoingCommunication);
    _ongoingCommunication = null;
    _debt += cost;
    return cost;
  }

  public void endOngoingCommunicationAsReceiver(int duration, TariffPlan tariffPlan) {
    _ongoingCommunication.end(duration, _owner.getTariffPlan());
    switch (_previousMode) {
      case ON:
        setOnIdle();
        break;
      case SILENCE:
        setOnSilent();
        break;
    }
    _receivedCommunications.add(_ongoingCommunication);
    _ongoingCommunication = null;
  }

}
