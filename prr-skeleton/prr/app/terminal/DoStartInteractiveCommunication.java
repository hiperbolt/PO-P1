package prr.app.terminal;

import prr.core.Network;
import prr.core.Terminal;
import prr.app.exception.UnknownTerminalKeyException;
import prr.core.exception.TerminalBusyException;
import prr.core.exception.TerminalOffException;
import prr.core.exception.TerminalSilentException;
import prr.core.exception.UnsupportedOperationException;
import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.CommandException;
//FIXME add more imports if needed

/**
 * Command for starting communication.
 */
class DoStartInteractiveCommunication extends TerminalCommand {
  Terminal _terminal;
  Network _network;
  DoStartInteractiveCommunication(Network context, Terminal terminal) {
    super(Label.START_INTERACTIVE_COMMUNICATION, context, terminal, receiver -> receiver.canStartCommunication());
    _terminal = terminal;
    _network = context;
    addStringField("destinationId", Message.terminalKey());
    addOptionField("commType", Message.commType(), "VOICE", "VIDEO");
  }
  
  @Override
  protected final void execute() throws CommandException {
    Terminal t = _network.terminalById(stringField("destinationId"));
    if (t == null){
      throw new UnknownTerminalKeyException(stringField("destinationId"));
    }

    try {
      if (optionField("commType").equals("VOICE")) {
        _terminal.makeVoiceCall(t);
      } else {
        _terminal.makeVideoCall(t);
      }
    } catch (TerminalBusyException e) {
      if(e.getMessage().equals(stringField("destinationId"))){
        _display.popup(Message.destinationIsBusy(stringField("destinationId")));
      } else {
        _display.popup(Message.originIsBusy(_terminal.getId()));
      }
    } catch (TerminalOffException e) {
      if(e.getMessage().equals(stringField("destinationId"))){
        _display.popup(Message.destinationIsOff(stringField("destinationId")));
      } else {
        _display.popup(Message.originIsOff(_terminal.getId()));
      }
    } catch (TerminalSilentException e) {
      _display.popup(Message.destinationIsSilent(stringField("destinationId")));
    } catch(UnsupportedOperationException e){
      if(e.getMessage().equals(stringField("destinationId"))){
        _display.popup(Message.unsupportedAtDestination(stringField("destinationId"), optionField("commType")));
      } else {
        _display.popup(Message.unsupportedAtOrigin(_terminal.getId(), optionField("commType")));
      }
    }


  }
}
