package sth.app.representative;

import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import sth.core.SchoolManager;

import sth.core.exception.NoSuchDisciplineIdException;
import sth.core.exception.NoSuchProjectIdException;

/**
 * 4.5.1. Create survey.
 */
public class DoCreateSurvey extends sth.app.common.ProjectCommand {

  private Input<String> _discipline;
  /**
   * @param receiver
   */
  public DoCreateSurvey(SchoolManager receiver) {
    super(Label.CREATE_SURVEY, receiver);
    _discipline = _form.addStringInput(Message.requestDisciplineName());
  }

  /** @see sth.app.common.ProjectCommand#myExecute() */ 
  @Override
  public final void myExecute() throws DialogException, NoSuchDisciplineIdException, NoSuchProjectIdException {
    _receiver.doCreateSurvey(_discipline.value());
  }

}
