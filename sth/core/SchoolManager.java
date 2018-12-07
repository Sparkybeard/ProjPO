package sth.core;

import java.io.*;
import java.util.List;
import java.util.ArrayList;

import sth.app.exception.*;
import sth.core.exception.*;
import sth.app.exception.DuplicateSurveyException;

/**
 * The façade class.
 */
public class SchoolManager {

  private School _school;
  private Person _loggedUser;
  private String _saveFileName = null;


  public SchoolManager() {
    _school = new School("school");
  }


  /**
   * @param datafile Contains school information to import
   * @throws ImportFileException problem with reading from the file
   */
  /* @throws InvalidCourseSelectionException */
  public void importFile(String datafile) throws ImportFileException {
    try {
      _school.importFile(datafile);

    } catch (IOException | BadEntryException e) {
      throw new ImportFileException(e);
    }
  }


  /**
   * Do the login of the user with the given identifier.
   *
   * @param id identifier of the user to login
   * @throws NoSuchPersonIdException if there are no users with the given identifier
   */
  public void login(int id) throws NoSuchPersonIdException {
    _loggedUser = _school.getPerson(id);
  }


  /**
   * @return true when the currently logged in person is an administrative
   */
  public boolean isLoggedUserAdministrative() {
    return _loggedUser instanceof Employee;
  }


  /**
   * @return true when the currently logged in person is a professor
   */
  public boolean isLoggedUserProfessor() {
    return _loggedUser instanceof Teacher;
  }


  /**
   * @return true when the currently logged in person is a student
   */
  public boolean isLoggedUserStudent() {
    return _loggedUser instanceof Student;
  }


  /**
   * @return true when the currently logged in person is a representative
   */
  public boolean isLoggedUserRepresentative() {
    return _loggedUser instanceof Student && ((Student) _loggedUser).isRepresentative();
  }


  public String changePhoneNumber(int phoneNumber) {
    _loggedUser.changePhoneNumber(phoneNumber);
    return _loggedUser.getInformation();
  }


    /**
     *
     * @return List of strings with every user's information
     */
  public List<String> getAllUsers() {
    return _school.showAllPeople();
  }


  public int getLoggedUser() {
    return _loggedUser.getId();
  }


  public List<String> getDisciplineStudents(String discipline)
          throws NoSuchDisciplineIdException {

      return _school.getDisciplineStudents(discipline, _loggedUser.getId());
  }


  public void newSaveAs(String filename) throws IOException {
      ObjectOutputStream obOut = null;
      try {
          FileOutputStream fpout = new FileOutputStream(filename);
          obOut = new ObjectOutputStream(fpout);
          obOut.writeObject(_school);

      } finally {
          if (obOut != null)
              obOut.close();
      }
  }


  public String getFileName() {
      return _saveFileName;
  }


  public void doOpen(String filename)
          throws IOException, ClassNotFoundException, NoSuchPersonIdException {

      ObjectInputStream objectInputStream = null;

      try {
          FileInputStream fileInputStream = new FileInputStream(filename);
          objectInputStream = new ObjectInputStream(fileInputStream);
          _school = (School) objectInputStream.readObject();

          _school.getPerson(_loggedUser.getId());

      }catch (NoSuchPersonIdException e){
          _school = null;
          throw new NoSuchPersonIdException(_loggedUser.getId());

      }finally {
          if(objectInputStream != null)
              objectInputStream.close();
      }
  }


  public String showPerson(){
      return _school.showPerson(_loggedUser.getId());
  }


  public List<String> searchPerson(String personName){
    List<Person> people = _school.searchPersonByName(personName);
    List<String> str = new ArrayList<>();
    for (Person p: people){

        str.add(p.getInformation());

    }
    return str;
  }

  
  public void doCloseProject(String _projectName, String disciplineName)
          throws NoSuchProjectIdException, NoSuchDisciplineIdException{

      _school.closeProject(_projectName, disciplineName, _loggedUser.getId());
  }


  public boolean doCreateProject(String projectName, String disciplineName)
          throws NoSuchDisciplineIdException{

      return _school.createProject(projectName, disciplineName, _loggedUser.getId());
  }


  public String doShowProjectSubmissions(String disciplineName, String projectName)
          throws NoSuchDisciplineIdException, NoSuchProjectIdException {

      return _school.seeSubmissions(_loggedUser.getId(), disciplineName, projectName);
  }


  public void doDeliverProject(String disciplineName, String projectName, String message)
          throws NoSuchDisciplineIdException, NoSuchProjectIdException {

      _school.addSubmission(_loggedUser.getId(), disciplineName, projectName, message);
  }


  public void doCreateSurvey(String disciplineName, String projectName)
          throws NoSuchDisciplineIdException, NoSuchProjectIdException,
          DuplicateSurveyException {

      try {
          if (isLoggedUserRepresentative())
              _school.createSurvey(_loggedUser.getId(), disciplineName, projectName);

      } catch (sth.core.exception.DuplicateSurveyException e) {
          throw new DuplicateSurveyException(disciplineName, projectName);
      }
  }


  public void doCancelSurvey(String disciplineName, String projectName)
          throws NoSuchDisciplineIdException, NoSuchProjectIdException,
          NoSurveyException, NonEmptySurveyException, SurveyFinishedException {

      try {
          if (isLoggedUserRepresentative())
              _school.cancelSurvey(_loggedUser.getId(), disciplineName, projectName);

      } catch (NoSurveyIdException e) {
          throw new NoSurveyException(disciplineName, projectName);

      } catch (SurveyNonEmptyException e) {
          throw new NonEmptySurveyException(disciplineName, projectName);

      } catch (FinishedSurveyException e) {
          throw new SurveyFinishedException(disciplineName, projectName);
      }
  }


  public void doOpenSurvey(String disciplineName, String projectName)
          throws NoSuchDisciplineIdException, NoSuchProjectIdException,
          NoSurveyException, OpeningSurveyException {

      try {
          if (isLoggedUserRepresentative())
              if(!_school.openSurvey(_loggedUser.getId(), disciplineName, projectName))
                  throw new OpeningSurveyException(disciplineName, projectName);

      } catch (NoSurveyIdException e) {
          throw new NoSurveyException(disciplineName, projectName);
      }
  }


  public void doCloseSurvey(String disciplineName, String projectName)
          throws NoSuchDisciplineIdException, NoSuchProjectIdException,
          NoSurveyException, ClosingSurveyException {

      try {
          if (isLoggedUserRepresentative())
              if(!_school.closeSurvey(_loggedUser.getId(), disciplineName, projectName))
                  throw new ClosingSurveyException(disciplineName, projectName);

      } catch (NoSurveyIdException e) {
          throw new NoSurveyException(disciplineName, projectName);
      }
  }


  public void doFinishSurvey(String disciplineName, String projectName)
          throws NoSuchDisciplineIdException, NoSuchProjectIdException,
          NoSurveyException, FinishingSurveyException {

      try {
          if (isLoggedUserRepresentative())
              if(!_school.finalizeSurvey(_loggedUser.getId(), disciplineName, projectName))
                  throw new FinishingSurveyException(disciplineName, projectName);

      } catch (NoSurveyIdException e) {
          throw new NoSurveyException(disciplineName, projectName);
      }
  }


  public void doFillSurvey(String disciplineName, String projectName,
                              int hours, String comment)
          throws NoSuchDisciplineIdException, NoSuchProjectIdException,
          NoSurveyException {

      try {
          _school.fillSurvey(_loggedUser.getId(), disciplineName, projectName, hours, comment);

      } catch (NoSurveyIdException e) {
          throw new NoSurveyException(disciplineName, projectName);
      }
  }


  public String doShowSurveyResults(String disciplineName,
                                    String projectName)
          throws NoSuchDisciplineIdException, NoSuchProjectIdException,
          NoSurveyException {

      try {

          if (isLoggedUserStudent())
              return _school.showSurveyResults((Student) _loggedUser,
                      disciplineName, projectName);

          if (isLoggedUserProfessor())
              return _school.showSurveyResults((Teacher) _loggedUser,
                      disciplineName, projectName);

          else return null;

      } catch (NoSurveyIdException e) {
          throw new NoSurveyException(disciplineName, projectName);
      }
  }


  public String doShowDisciplineSurveys(String disciplineName)
          throws NoSuchDisciplineIdException {

      return _school.showDisciplineSurveys(_loggedUser.getId(),
              disciplineName);
  }


  public List<String> getNotifications() {
      return _school.getNotifications(_loggedUser.getId());
  }
}