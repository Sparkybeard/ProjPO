package sth.core;

/* Collections Import */
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.Iterator;


/* Exceptions Import */
import sth.core.exception.BadEntryException;
import sth.core.exception.NoSuchPersonIdException;

import java.io.IOException;

/**
 * Represents the school of the program
 * Contains all the people in the school
 * Contains all the courses in the school
 */
class School implements java.io.Serializable {

  /** Serial number for serialization. */
  private static final long serialVersionUID = 201810051538L;

  private String _name;
  private int _nextPersonId;

  private Map<Integer, Person> _peopleMap;
  private List<Course> _courseList;


  School(String name) {
      _name = name;
      _nextPersonId = 10000;

      _peopleMap = new HashMap<>();
      _courseList = new ArrayList<>();
  }


  String getName() {
      return _name;
  }


  int getNextPersonId(){
      return _nextPersonId;
  }


    /**
     *
     * @param id id of the person to get
     * @return the Person object with the given id
     * @throws NoSuchPersonIdException No one with the given id found
     */
  Person getPerson(int id) throws NoSuchPersonIdException {
      if(_peopleMap.containsKey(id))
          return _peopleMap.get(id);

      else throw new NoSuchPersonIdException(id);
  }


    /**
     *
     * @param person to add to the collection of people
     */
  void addPerson(Person person) {
      _peopleMap.put(person.getId(), person);
  }


    /**
     *
     * @param name of the course
     * @return Course object with the given name
     */
  Course getCourse(String name){
      Iterator<Course> iter = _courseList.iterator();
      Course c;

      while(iter.hasNext()){
          c = iter.next();

          if(c.getName().equals(name))
              return c;
      }

      return null;
  }


    /**
     *
     * @param course to add to the collection of courses
     * @return false if course already in. True otherwise
     */
  boolean addCourse(Course course) {
      if(_courseList.contains(course))
          return false;

      else {
          _courseList.add(course);

          return true;
      }
  }


    /**
     * Returns the course with the given name if already in
     * Creates one and returns if otherwise
     *
     * @param courseName name of the course to add/find
     * @return Course object of the given name
     */
  Course parseCourse(String courseName){
      Course c;
      c = this.getCourse(courseName);

      /* Check if course already exists */
      if(c != null)
          return c;

      /* If there isn't, create one */
      c = new Course(courseName);
      _courseList.add(c);
      return c;
  }


    /**
     *
     * @return list of all people in the school
     */
  List<Person> getAllUsers() {
      List<Person> userList = new ArrayList<>();
      Set<Integer> idList = _peopleMap.keySet();
      Iterator<Integer> iter = idList.iterator();

      /* Iterate over the peopleMap and put them on a list */
      while(iter.hasNext())
          userList.add(_peopleMap.get(iter.next()));

      return userList;
  }


  /**
   * @param filename Contains school information to import
   * @throws BadEntryException Exception for unknown import file entries.
   * @throws IOException
   */
  void importFile(String filename) throws IOException, BadEntryException {
    Parser parser = new Parser(this);
    parser.parseFile(filename);
  }


    /**
     * Saves in the object information in a file
     *
     * @param filename to save the information
     */
  void saveSerialiazable(String filename) throws IOException{
      ObjectOutputStream obOut = null;

      try{
          FileOutputStream fpout = new FileOutputStream(filename);
          obOut = new ObjectOutputStream(fpout);
          obOut.writeObject(this);

      }finally {
          if(obOut != null) {
              obOut.close();
          }
      }
  }


  String showPerson(int id) {
      Person person = _peopleMap.get(id);
      return person.getInformation();
  }


  List<String> showAllPeople() {
    List<String> allPeopleInformation = new ArrayList<>();
    Set<Integer> idList = _peopleMap.keySet();
    Iterator<Integer> iterator = idList.iterator();

    while(iterator.hasNext()){
        allPeopleInformation.add(_peopleMap.get(iterator.next()).getInformation());
    }

    return allPeopleInformation;
  }
}
