package prr.app.terminals;
import prr.core.*;
import prr.app.exception.*;
import prr.core.exception.DuplicateKeyException;
import prr.core.exception.InvalidKeyException;
import prr.core.exception.UnknownKeyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Register terminal.
 */
class DoRegisterTerminal extends Command<Network> {
  DoRegisterTerminal(Network receiver) {
    super(Label.REGISTER_TERMINAL, receiver);
    addStringField("terminalId", Message.terminalKey());
    addOptionField("terminalType", Message.terminalType(), "BASIC", "FANCY");
    addStringField("ownerID", Message.clientKey());
  }

  @Override
  protected final void execute() throws CommandException {
    // We try to register the terminal, and catch the exceptions if they occur, then throw our own exception.
    try {
      if(stringField("terminalType").equals("FANCY")){
        _receiver.registerTerminal(TerminalType.FANCY, stringField("terminalId"), stringField("ownerID"));
      }
      else if(stringField("terminalType").equals("BASIC")){
        _receiver.registerTerminal(TerminalType.BASIC, stringField("terminalId"), stringField("ownerID"));
      }
    } catch (DuplicateKeyException e) {
      throw new DuplicateTerminalKeyException(stringField("terminalId"));
    } catch (InvalidKeyException e) {
      throw new InvalidTerminalKeyException(stringField("terminalId"));
    } catch (UnknownKeyException e){
      throw new UnknownClientKeyException(stringField("ownerID"));
    }
  }
}
