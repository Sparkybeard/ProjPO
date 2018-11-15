package sth.app.person;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.Display;
import sth.core.SchoolManager;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

//FIXME import other classes if needed

/**
 * 4.2.3. Show all persons.
 */
public class DoShowAllPersons extends Command<SchoolManager> {

  //FIXME add input fields if needed
  private ArrayList<String> _people;
  /**
   * @param receiver
   */
  public DoShowAllPersons(SchoolManager receiver) {
    super(Label.SHOW_ALL_PERSONS, receiver);
    //FIXME initialize input fields if needed
    //pode faltar adicionar objecto da escola para importar pessoas

  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() {

    _form.parse();
    _people = _receiver.getAllUsers();
    display.addLine(_people.value());
    }
	  //FIXME implement command

  }
