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
      return _name
  }

  Person getPerson(int id) throws NoSuchPersonIdException {
      if(_peopleMap.containsKey(id))
          return _peopleMap.get(id);

      else throw new NoSuchPersonIdException(id);
  }

  boolean addPerson(Person person) {
      if(_peopleMap.containsKey(person.get_id()))
          return false;

      else {
          int id = person.get_id();
          _peopleMap.put(id, person);

          return true;
      }
  }

  boolean addCourse(Course course) {
      if(_courseList.contains(course))
          return false;

      else {
          _courseList.add(course);

          return true;
      }
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
   * @param filename name of file to import from
   * @throws BadEntryException Exception for unknown import file entries.
   * @throws IOException
   */
  void importFile(String filename) throws IOException, BadEntryException {
    //FIXME implement text file reader
  }
}
