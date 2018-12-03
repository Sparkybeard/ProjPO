package sth.app.teaching;

import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import sth.app.exception.DuplicateProjectException;
import sth.core.SchoolManager;
import sth.app.common.ProjectCommand;
import sth.app.exception.NoSuchDisciplineException;

import sth.core.exception.NoSuchDisciplineIdException;
import sth.core.exception.NoSuchProjectIdException;

/**
 * 4.4.1. Create project.
 */
public class DoCreateProject extends ProjectCommand {
  /**
   * @param receiver
   */
  public DoCreateProject(SchoolManager receiver) {
    super(Label.CREATE_PROJECT, receiver);

  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void myExecute() throws DialogException, NoSuchDisciplineException, DuplicateProjectException {
    try {
       if(_receiver.doCreateProject(_project.value(), _discipline.value()) == false ) {
         throw new DuplicateProjectException(_discipline.value(), _project.value());
       }
    } catch(NoSuchDisciplineIdException e) {
      throw new NoSuchDisciplineException(_discipline.value());
    }
  }
}

