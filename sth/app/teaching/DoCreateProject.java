package sth.app.teaching;

import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import sth.app.exception.DuplicateProjectException;
import sth.core.SchoolManager;

import sth.core.exception.NoSuchDisciplineIdException;
import sth.core.exception.NoSuchProjectIdException;

/**
 * 4.4.1. Create project.
 */
public class DoCreateProject extends sth.app.common.ProjectCommand {
    private Input<String> _projectname;
    private Input<String> _disciplinename;
  /**
   * @param receiver
   */
  public DoCreateProject(SchoolManager receiver) {
    super(Label.CREATE_PROJECT, receiver);
    _disciplinename = _form.addStringInput("Introduza o nome da Disciplina: ");
    _projectname = _form.addStringInput("Introduza o nome do Projecto: ");

  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void myExecute() throws DialogException, NoSuchDisciplineIdException, NoSuchProjectIdException, DuplicateProjectException {
   //chama o constructor do pai, e a funcao create project ao school manager
   _form.parse();

   _receiver.doCreateProject(_projectname.value(), _disciplinename.value());
   //FIXME implement command
  }

}
