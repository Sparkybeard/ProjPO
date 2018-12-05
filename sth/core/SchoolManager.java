package sth.core;

import java.io.*;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

import sth.core.exception.BadEntryException;
import sth.core.exception.ImportFileException;
import sth.core.exception.NoSuchPersonIdException;
import sth.core.exception.NoSuchDisciplineIdException;
import sth.core.exception.NoSuchProjectIdException;
import sth.core.Person;
import sth.core.School;


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


/*
  public void newSaveAs(String filename) throws IOException {
      ObjectOutputStream objectOutputStream = null;
      FileOutputStream fileOutputStream = new FileOutputStream(filename);
      objectOutputStream = new ObjectOutputStream(fileOutputStream);
      objectOutputStream.writeObject(_school);
      _saveFileName = filename;

      if(objectOutputStream != null)
        objectOutputStream.close();
  }
*/


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
          throws IOException, ClassNotFoundException,
          NoSuchPersonIdException {

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


  public boolean doCreateProject(String projName, String disciplineName)
          throws NoSuchDisciplineIdException{

      return _school.createProject(projName, disciplineName, _loggedUser.getId());
  }


  public boolean doCreateSurvey(String disciplineName, String projectName)
          throws NoSuchDisciplineIdException,
          NoSuchProjectIdException,
          NoSuchPersonIdException {

      if(isLoggedUserRepresentative())
          return _school.createSurvey(_loggedUser.getId(),
                  disciplineName, projectName);

      return false;
  }


  public boolean doCancelSurvey(String disciplineName, String projectName)
          throws NoSuchDisciplineIdException,
          NoSuchProjectIdException,
          NoSuchPersonIdException {

      if(isLoggedUserRepresentative())
          return _school.cancelSurvey(_loggedUser.getId(),
                  disciplineName, projectName);

      return false;
  }


  public boolean doOpenSurvey(String disciplineName, String projectName)
          throws NoSuchDisciplineIdException,
          NoSuchProjectIdException,
          NoSuchPersonIdException {

      if(isLoggedUserRepresentative())
          return _school.openSurvey(_loggedUser.getId(),
                  disciplineName, projectName);

      return false;
  }


  public boolean doCloseSurvey(String disciplineName, String projectName)
          throws NoSuchDisciplineIdException,
          NoSuchProjectIdException,
          NoSuchPersonIdException {

      if(isLoggedUserRepresentative())
          return _school.closeSurvey(_loggedUser.getId(),
                  disciplineName, projectName);

      return false;
  }


  public boolean doFinalizeSurvey(String disciplineName, String projectName)
          throws NoSuchDisciplineIdException,
          NoSuchProjectIdException,
          NoSuchPersonIdException {

      if(isLoggedUserRepresentative())
          return _school.finalizeSurvey(_loggedUser.getId(),
                  disciplineName, projectName);

      return false;
  }


  public boolean doFillSurvey(String disciplineName, String projectName,
                              int hours, String comment)
          throws NoSuchDisciplineIdException,
          NoSuchProjectIdException,
          NoSuchPersonIdException {

      return _school.fillSurvey(_loggedUser.getId(),
              disciplineName, projectName, hours, comment);
  }


  public String doShowSurveyResults(String disciplineName,
                                    String projectName)
          throws NoSuchDisciplineIdException,
          NoSuchProjectIdException,
          NoSuchPersonIdException {

      if(isLoggedUserStudent())
          return _school.showSurveyResults((Student) _loggedUser,
                  disciplineName, projectName);

      if(isLoggedUserProfessor())
          return _school.showSurveyResults((Teacher) _loggedUser,
                  disciplineName, projectName);

      else return null;
  }


  public String doShowDisciplineSurveys(String disciplineName)
          throws NoSuchDisciplineIdException,
          NoSuchPersonIdException {

      return _school.showDisciplineSurveys(_loggedUser.getId(),
              disciplineName);
  }
}