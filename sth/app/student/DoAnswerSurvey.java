package sth.app.student;

import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import sth.app.exception.NoSuchDisciplineException;
import sth.app.exception.NoSuchProjectException;
import sth.core.SchoolManager;

import sth.core.exception.NoSuchProjectIdException;
import sth.core.exception.NoSuchDisciplineIdException;

import javax.print.DocFlavor;

/**
 * 4.5.2. Answer survey.
 */
public class DoAnswerSurvey extends sth.app.common.ProjectCommand {

  private Input<String> _comment;
  private Input<Integer> _hours;
  /**
   * @param receiver
   */
  public DoAnswerSurvey(SchoolManager receiver) {
    super(Label.ANSWER_SURVEY, receiver);
    _hours = _form.addIntegerInput(Message.requestProjectHours());
    _comment = _form.addStringInput(Message.requestComment());
  }

  /** @see sth.app.common.ProjectCommand#myExecute() */
  @Override
  public final void myExecute() throws NoSuchProjectIdException, NoSuchDisciplineIdException, DialogException {

    try {
      _form.parse();


      _receiver.doFillSurvey(_discipline.value(), _project.value(), _hours.value(), _comment.value());

    } catch (NoSuchProjectIdException e) {
      throw new NoSuchProjectException(_discipline.value(), _project.value());
    } catch (NoSuchDisciplineIdException e) {
      throw new NoSuchDisciplineException(_discipline.value());
    }
  }
}
