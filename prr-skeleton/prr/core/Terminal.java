package prr.core;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

// FIXME add more import if needed (cannot import from pt.tecnico or prr.app)

/**
 * Abstract Terminal.
 */
abstract public class Terminal implements Serializable /* FIXME maybe add more interfaces */{

  /** Serial number for serialization. */
  private static final long serialVersionUID = 202208091753L;
  
  private String _id;
  // private String _mode;  FIXME Estava no UML do stor mas não faz sentido??
  private double _debt;
  private double _payments;
  private TerminalMode _mode;
  private ArrayList<Terminal> _friends = new ArrayList<Terminal>();
  private Client _owner;
  private List<Communication> _madeCommunications;
  private List<Communication> _receivedCommunications;
  private Communication _ongoingCommunication;
  // FIXME define notifications

  // FIXME define contructor(s)
  // FIXME define methods


  public void makeSMS(Terminal to, String message){
    TextCommunication newTextComm = new TextCommunication();


  }

  /**
   * Checks if this terminal can end the current interactive communication.
   *
   * @return true if this terminal is busy (i.e., it has an active interactive communication) and
   *          it was the originator of this communication.
   **/
  public boolean canEndCurrentCommunication() {
    // FIXME add implementation code
  }
  
  /**
   * Checks if this terminal can start a new communication.
   *
   * @return true if this terminal is neither off neither busy, false otherwise.
   **/
  public boolean canStartCommunication() {
    // FIXME add implementation code
  }

  /**
   * If possible, sets on Idle.
   *
   * @return true if it succeeds, false if not.
   **/
  public boolean setOnIdle(){
    if (_mode == TerminalMode.OFF || _mode == TerminalMode.SILENCE || _mode == TerminalMode.BUSY){
      _mode = TerminalMode.ON;
      return True;
    }
    return False;
  }

  /**
   * If possible, sets on Silent.
   *
   * @return true if it succeeds, false if not.
   **/
  public boolean setOnSilent(){
    if (_mode == TerminalMode.ON || _mode == TerminalMode.BUSY){
      _mode = TerminalMode.SILENCE;
      return True;
    }
    return False;
  }

  /**
   * If possible, turns off terminal.
   *
   * @return true if it succeeds, false if not.
   **/
  public boolean turnOff(){
    if (_mode == TerminalMode.ON || _mode == TerminalMode.SILENCE){
        _mode = TerminalMode.OFF;
        return True;
    }
    return False;
  }

}
