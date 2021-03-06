package sth.app.student;

import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import sth.app.exception.NoSuchDisciplineException;
import sth.app.exception.NoSuchProjectException;
import sth.core.SchoolManager;

import sth.core.exception.NoSuchDisciplineIdException;
import sth.core.exception.NoSuchProjectIdException;
import sth.app.exception.NoSurveyException;

/**
 * 4.5.3. Show survey results.
 */
public class DoShowSurveyResults extends sth.app.common.ProjectCommand {

  /**
   * @param receiver
   */
  public DoShowSurveyResults(SchoolManager receiver) {
    super(Label.SHOW_SURVEY_RESULTS, receiver);

  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void myExecute() throws NoSuchProjectException, NoSuchDisciplineException, NoSurveyException {
    try {

      _display.addLine(_receiver.doShowSurveyResults(_discipline.value(), _project.value()));
      _display.display();
    } catch(NoSuchProjectIdException e){
        throw new NoSuchProjectException(_discipline.value(), _project.value());

    } catch(NoSuchDisciplineIdException e) {
      throw new NoSuchDisciplineException(_discipline.value());

    }
  }

}
