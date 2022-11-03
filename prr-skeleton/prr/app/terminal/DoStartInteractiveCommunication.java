package prr.app.terminal;

import prr.core.Network;
import prr.core.Terminal;
import prr.app.exception.UnknownTerminalKeyException;
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
    switch (optionField("commType")) {
      case "VOICE":
        try {
        _terminal.makeVoiceCall(t);
        } catch (Exception e) {
          //placeholder
        }
        break;
      case "VIDEO":
        //placeholder
        break;
    }
  }
}
