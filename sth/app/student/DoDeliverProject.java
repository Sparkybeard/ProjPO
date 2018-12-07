package sth.app.student;

import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import sth.core.SchoolManager;

import sth.core.exception.NoSuchProjectIdException;
import sth.core.exception.NoSuchDisciplineIdException;

/**
 * 4.5.1. Deliver project.
 */
public class DoDeliverProject extends sth.app.common.ProjectCommand {

  //FIXME add input fields if needed
  private Input<String> _project;
  private Input<String> _discipline;
  private Input<String> _submission;

  /**
   * @param receiver
   */
  public DoDeliverProject(SchoolManager receiver) {
    super(Label.DELIVER_PROJECT, receiver);
      _discipline = _form.addStringInput(Message.requestDisciplineName());
      _project = _form.addStringInput(Message.requestProjectName());
      _submission = _form.addStringInput(Message.requestDeliveryMessage());
  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void myExecute() throws NoSuchProjectIdException, NoSuchDisciplineIdException, DialogException {
    _form.parse();
    _receiver.doDeliverProject(_discipline.value(), _project.value(), _submission.value());

  }

}
