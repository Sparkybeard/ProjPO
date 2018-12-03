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
  /**
   * @param receiver
   */
  public DoAnswerSurvey(SchoolManager receiver) {
    super(Label.ANSWER_SURVEY, receiver);
    //FIXME initialize input fields if needed
    _hours = _form.addIntegerInput(Message.getHours)
    _answer = _form.addStringInput(Message.getComment);
    
  }

  /** @see sth.app.common.ProjectCommand#myExecute() */
  @Override
  public final void myExecute() throws NoSuchProjectIdException, NoSuchDisciplineIdException, DialogException {
    _form.parse;
    _receiver.answerSurvey(_hours.value(), _answer.value());
      
  }

}
