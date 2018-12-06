package sth.app.teaching;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import sth.core.SchoolManager;
import sth.core.exception.NoSuchDisciplineIdException;
import sth.app.exception.NoSuchDisciplineException;



/**
 * 4.4.4. Show course students.
 */
public class DoShowDisciplineStudents extends Command<SchoolManager> {


  private Input<String> _discipline;
  /**
   * @param receiver
   */
  public DoShowDisciplineStudents(SchoolManager receiver) {
    super(Label.SHOW_COURSE_STUDENTS, receiver);

    _discipline = _form.addStringInput(Message.requestDisciplineName());
  }
  //Possible bad implementation of exception DialogException
  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() throws DialogException {

    _form.parse();
    try {
      for(String a : _receiver.getDisciplineStudents(_discipline.value())) {
        _display.addLine(a);
      }
    } catch(NoSuchDisciplineIdException e) {
      throw new NoSuchDisciplineException(_discipline.value());
    }

  }
}
