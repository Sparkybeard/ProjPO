package sth.app.student;

import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import sth.core.SchoolManager;

import sth.core.exception.NoSuchProjectIdException;
import sth.core.exception.NoSuchDisciplineIdException;
/**
 * 4.5.2. Answer survey.
 */
public class DoAnswerSurvey extends sth.app.common.ProjectCommand {

  //FIXME add input fields if needed
  private Input<String> _answer;
  private Input<Integer> _hours;
  private Input<String> _projName;
  private Input<String> _disciplineName;

  /**
   * @param receiver
   */
  public DoAnswerSurvey(SchoolManager receiver) {
    super(Label.ANSWER_SURVEY, receiver);
    //FIXME initialize input fields if needed
    _hours = _form.addIntegerInput(Message.requestProjectHours());
    _answer = _form.addStringInput(Message.requestComment());
    _disciplineName = _form.addStringInput(Message.requestDisciplineName());
    _projName = _form.addStringInput(Message.requestProjectName());
  }

  /** @see sth.app.common.ProjectCommand#myExecute() */
  @Override
  public final void myExecute() throws NoSuchProjectIdException, NoSuchDisciplineIdException, DialogException {
    _form.parse();
    _receiver.doFillSurvey(_disciplineName.value(), _projName.value(), _hours.value(), _answer.value());
  }

}
