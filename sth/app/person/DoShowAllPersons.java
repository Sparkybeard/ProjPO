package sth.app.person;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.Display;
import sth.core.SchoolManager;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;



/**
 * 4.2.3. Show all persons.
 */
public class DoShowAllPersons extends Command<SchoolManager> {


  private List<String> _people = new ArrayList<>();
  /**
   * @param receiver
   */
  public DoShowAllPersons(SchoolManager receiver) {
    super(Label.SHOW_ALL_PERSONS, receiver);

    //pode faltar adicionar objecto da escola para importar pessoas

  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() {

    _form.parse();
    _people = _receiver.getAllUsers();
    for(String a : _people) {
      _display.addLine(a);
    }
  }

}
