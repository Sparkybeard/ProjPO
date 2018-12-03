package sth.core;

/* Collections Import */
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;


/* Exceptions Import */
import sth.core.exception.BadEntryException;
import sth.core.exception.NoSuchDisciplineIdException;
import sth.core.exception.NoSuchPersonIdException;
import sth.core.exception.NoSuchProjectIdException;

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


    /**
     *
     * @return name of the school
     */
  String getName() {
      return _name;
  }


    /**
     *
     * @return id for the next person to create
     */
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
     *
     * @param id of the person to show
     * @return String with type of person
     */
  String showPerson(int id) {
      Person person = _peopleMap.get(id);
      return person.getInformation();
  }


    /**
     * Creates a list of strings with the information
     * of each person on the school
     *
     * @return list of strings with information of each person
     */
  List<String> showAllPeople() {
    List<String> allPeopleInformation = new ArrayList<>();
    Set<Integer> idList = _peopleMap.keySet();
    Iterator<Integer> iterator = idList.iterator();

    while(iterator.hasNext()){
        allPeopleInformation.add(_peopleMap.get(iterator.next()).getInformation());
    }

    return allPeopleInformation;
  }


    /**
     * Creates a list of strings with the information
     * of the people in the school with a name
     * that is contains the personName
     *
     * @param personName partial name of the people to search
     * @return list of strings with information of each person
     */


  List<Person> searchPersonByName(String personName) {
      List<Person> pre = new ArrayList<>(_peopleMap.values());
      List<Person> people = pre.stream().filter(p -> p.getName().contains(personName)).collect(Collectors.toList());
      Collections.sort(people);
      return people;
  }

    /**
     * Closes a project of a given discipline
     *
     * @param projName name of the project to close
     * @param disciplineName name of the discipline with the project
     * @param id of the teacher trying to close the project
     * @throws NoSuchProjectIdException No project with the given name
     * @throws NoSuchDisciplineIdException No discipline with the given name
     */
  void closeProject(String projName, String disciplineName, int id)
          throws NoSuchProjectIdException, NoSuchDisciplineIdException {

      ((Teacher) _peopleMap.get(id)).closeProject(projName, disciplineName);
  }


    /**
     * Creates a project with the given name
     * In the given discipline
     *
     * @param projName name of the project to create
     * @param disciplineName name of the discipline with the project
     * @param id of the teacher trying to create the project
     * @return false if project already exists. True otherwise
     * @throws NoSuchDisciplineIdException No discipline with the given name
     */
  boolean createProject(String projName, String disciplineName, int id)
          throws NoSuchDisciplineIdException{

      return ((Teacher) _peopleMap.get(id)).createProject(projName, disciplineName);
  }


    /**
     * Creates a list of strings with the information
     * of each student in a given discipline
     *
     * @param discipline name of the discipline
     * @param id of the teacher trying to obtain the students' information
     * @return list of strings with information of students in given discipline
     * @throws NoSuchDisciplineIdException no discipline with the given name
     */
  List<String> getDisciplineStudents(String discipline, int id)
            throws NoSuchDisciplineIdException {

      List<Student> list = ((Teacher) _peopleMap.get(id)).getDisciplineStudents(discipline);
      List<String> studentList = new ArrayList<>();
      Iterator<Student> iterator = list.iterator();

      while (iterator.hasNext()){
          studentList.add(iterator.next().getInformation());
      }

      return studentList;
  }


  void addSubmission(
          int id, String displineName, String projectName, String message)
          throws NoSuchDisciplineIdException, NoSuchProjectIdException{

      Student student = (Student) _peopleMap.get(id);
      student.addSubmission(displineName, projectName, message);
  }


  String seeSubmissions(int id, String projectName, String disciplineName)
          throws NoSuchDisciplineIdException, NoSuchProjectIdException{

      Teacher teacher = (Teacher)_peopleMap.get(id);
      return teacher.seeSubmissions(disciplineName, projectName);
  }
}

/*

TODO List

1. seeSurveyResults (Person)
2. fillSurvey (Student)
3. makeSurvey (Representative)
4. cancelSurvey
5. openSurvey
6. closeSurvey
7. finalizeSurvey
8. showSurveyResults
9. showDisciplineSurveys

 */