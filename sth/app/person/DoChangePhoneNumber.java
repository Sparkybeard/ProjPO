package sth.app.person;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.Display;
import pt.tecnico.po.ui.Input;
import sth.core.SchoolManager;

//FIXME import other classes if needed

/**
 * 4.2.2. Change phone number.
 */
public class DoChangePhoneNumber extends Command<SchoolManager> {

  //FIXME add input fields if needed
  private Input<Integer> _phonenumber;
  private String _person;
  /**
   * @param receiver
   */
  public DoChangePhoneNumber(SchoolManager receiver) {
    super(Label.CHANGE_PHONE_NUMBER, receiver);
    _phonenumber = _form.addIntegerInput("Introduza o novo nr desejado: ");
    //FIXME initialize input fields if needed
  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() {
    //may be lacking try catch to fix NoSuchPersonIdException
    _form.parse();
    _person = _receiver.changePhoneNumber(_phonenumber.value());
    _display.addLine("Número de telemovel mudado");
    _display.addLine(_person);
    //FIXME implement command
  }

}
