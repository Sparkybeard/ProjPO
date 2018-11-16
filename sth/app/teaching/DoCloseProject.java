package sth.app.teaching;

import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import sth.core.SchoolManager;
import sth.core.exception.NoSuchProjectIdException;
import sth.core.exception.NoSuchDisciplineIdException;

/**
 * 4.4.2. Close project.
 */
public class DoCloseProject extends sth.app.common.ProjectCommand {


  Input<String> _projectname;
  /**
   * @param receiver
   */
  public DoCloseProject(SchoolManager receiver) {
    super(Label.CLOSE_PROJECT, receiver);
    _projectname = _form.addStringInput("Introduza o nome do ficheiro: ");
  }

  /**
   * @see sth.app.common.ProjectCommand#myExecute()
   */
  // FFT instead of NoSuchDis and NoSuchProj.. have "throws Project Exception
  @Override
  public final void myExecute() throws DialogException, NoSuchDisciplineIdException, NoSuchProjectIdException {
    //FIXME implement command
    _form.parse();
    try {
      _receiver.doCloseProject(_discipline.value(), _project.value());
    } catch (NoSuchProjectIdException e) {

    } catch (NoSuchDisciplineIdException i) {

    }

  }
}
