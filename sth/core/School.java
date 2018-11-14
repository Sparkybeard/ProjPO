package sth.core;

/* Collections Import */
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
 * School implementation.
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


  Person getPerson(int id) throws NoSuchPersonIdException {
      if(_peopleMap.containsKey(id))
          return _peopleMap.get(id);

      else throw new NoSuchPersonIdException(id);
  }


  void addPerson(Person person) {
      _peopleMap.put(person.get_id(), person);
  }


  boolean addCourse(Course course) {
      if(_courseList.contains(course))
          return false;

      else {
          _courseList.add(course);

          return true;
      }
  }


  Course parseCourse(String courseName){
      Iterator<Course> iter = _courseList.iterator();
      Course c;

      /* Check if course already exists */
      while(iter.hasNext()){
          c = iter.next();

          if(c.getName().equals(courseName))
              return c;
      }

      /* If there isn't, create one */
      c = new Course(courseName);
      _courseList.add(c);
      return c;
  }


  List getAllUsers() {
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
}
