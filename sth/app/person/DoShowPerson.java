package sth.app.person;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.Display;
import sth.core.SchoolManager;

//FIXME import other classes if needed

/**
 * 4.2.1. Show person.
 */
public class DoShowPerson extends Command<SchoolManager> {

  //FIXME add input fields if needed
	private String _person;
  /**
   * @param receiver
   */
  public DoShowPerson(SchoolManager receiver) {
    super(Label.SHOW_PERSON, receiver);

    //FIXME initialize input fields if needed
  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() {
    //mostra a pessoa registada no formato serialized
    _form.parse();
    _person = _receiver.getLoggedUser();
    _display.addLine(_receiver.showPerson(_person.value()));
	
	  //FIXME implement command
  }

}
