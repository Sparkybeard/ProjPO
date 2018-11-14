package sth.app.teaching;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import sth.core.SchoolManager;

//FIXME import other classes if needed

/**
 * 4.4.4. Show course students.
 */
public class DoShowDisciplineStudents extends Command<SchoolManager> {

  //FIXME add input fields if needed
  private Input<Discipline> _discipline;
  /**
   * @param receiver
   */
  public DoShowDisciplineStudents(SchoolManager receiver) {
    super(Label.SHOW_COURSE_STUDENTS, receiver);
    //FIXME initialize input fields if needed
    _discipline = _form.getStringInput("Introduza a disciplina: ");
  }
  //Possible bad implementation of exception DialogException
  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() throws DialogException {
    //FIXME implement command
    _form.parse();
    if (_receiver.isDisciplineValid(_discipline.value())) {
      _display.addLine(_receiver.getDisciplineStudents(_discipline.value()));
    } else {
      throw new NoSuchDisciplineException();
    }

  }
}
