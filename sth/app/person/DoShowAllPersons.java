package sth.app.person;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.Display;
import sth.core.SchoolManager;

//FIXME import other classes if needed

/**
 * 4.2.3. Show all persons.
 */
public class DoShowAllPersons extends Command<SchoolManager> {

  //FIXME add input fields if needed
  private List<person> _people;
  /**
   * @param receiver
   */
  public DoShowAllPersons(SchoolManager receiver) {
    super(Label.SHOW_ALL_PERSONS, receiver);
    //FIXME initialize input fields if needed
    //pode faltar adicionar objecto da escola para importar pessoas
    _people = _form.ImportPeople();
  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() {
    _form.parse();
    Iterator<person> iterator = _people.iterator();
    while (iterator.hasNext()) {
	    Person aux = iterator.next();
	    display.addLine(aux.showPerson());
    }
	  //FIXME implement command

  }

}
