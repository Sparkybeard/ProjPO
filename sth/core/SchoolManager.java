package sth.core;

import sth.core.exception.BadEntryException;
import sth.core.exception.ImportFileException;
import sth.core.exception.NoSuchPersonIdException;

import java.io.IOException;
import java.io.FileNotFoundException;


/**
 * The façade class.
 */
public class SchoolManager {

  private School _school;
  private Person _loggedUser;


  public SchoolManager(String _schoolName) {
    _school = new School(_schoolName);
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

   * @param id identifier of the user to login
   * @throws NoSuchPersonIdException if there are no users with the given identifier
   */
  public void login(int id) throws NoSuchPersonIdException{
    _loggedUser =_school.getPerson(id);
  }


  /*
   * @return true when the currently logged in person is an administrative
   */
  /*public boolean isLoggedUserAdministrative() {
    //FIXME implement predicate
  }
  */

  /**
   * @return true when the currently logged in person is a professor
   */
  public boolean isLoggedUserProfessor() { return _loggedUser instanceof Teacher; }


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


  public boolean changePhoneNumber(int phoneNumber){
      _loggedUser.changePhoneNumber(phoneNumber);
      return true;
  }
}
