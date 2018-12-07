package sth.core;

/* Collections Import */
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;


/* Exceptions Import */
import sth.core.exception.*;

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
     * @throw No one with the given id found
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
      Iterator<Course> iterator = _courseList.iterator();
      Course c;

      while(iterator.hasNext()){
          c = iterator.next();

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

    allPeopleInformation.sort(null);

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
      people.sort(new PersonComparator());
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

    /**
     *
     * @param id of the student making the submission
     * @param disciplineName Discipline with the project
     * @param projectName Project to make a submission
     * @param message with the Submission
     *
     * @throws NoSuchDisciplineIdException No discipline with the given name
     * @throws NoSuchProjectIdException No project with the given name
     */
  void addSubmission(
          int id, String disciplineName, String projectName, String message)
          throws NoSuchDisciplineIdException, NoSuchProjectIdException{

      Student student = (Student) _peopleMap.get(id);
      student.addSubmission(disciplineName, projectName, message);
  }

    /**
     *
     * @param id of the student seeing the submissions
     * @param disciplineName Discipline with the project
     * @param projectName Project to see submissions
     * @return String with submissions
     *
     * @throws NoSuchDisciplineIdException No discipline with the given name
     * @throws NoSuchProjectIdException No project with the given name
     */
  String seeSubmissions(int id, String disciplineName, String projectName)
          throws NoSuchDisciplineIdException, NoSuchProjectIdException{

      Teacher teacher = (Teacher)_peopleMap.get(id);
      return teacher.seeSubmissions(disciplineName, projectName);
  }


  boolean createSurvey(int id, String disciplineName, String projectName)
          throws NoSuchDisciplineIdException, NoSuchProjectIdException,
          DuplicateSurveyException {

      Student student = (Student) _peopleMap.get(id);
      return student.createSurvey(disciplineName, projectName);
  }


  boolean cancelSurvey(int id, String disciplineName, String projectName)
          throws NoSuchDisciplineIdException, NoSuchProjectIdException,
          NoSurveyIdException, SurveyNonEmptyException, FinishedSurveyException {

      Student student = (Student) _peopleMap.get(id);
      return student.cancelSurvey(disciplineName, projectName);
  }


  boolean openSurvey(int id, String disciplineName, String projectName)
          throws NoSuchDisciplineIdException, NoSuchProjectIdException,
          NoSurveyIdException {

      Student student = (Student) _peopleMap.get(id);
      return student.openSurvey(disciplineName, projectName);
  }


  boolean closeSurvey(int id, String disciplineName, String projectName)
          throws NoSuchDisciplineIdException, NoSuchProjectIdException,
          NoSurveyIdException {

      Student student = (Student) _peopleMap.get(id);
      return student.closeSurvey(disciplineName, projectName);
  }


  boolean finalizeSurvey(int id, String disciplineName, String projectName)
          throws NoSuchDisciplineIdException, NoSuchProjectIdException,
          NoSurveyIdException {

      Student student = (Student) _peopleMap.get(id);
      return student.finalizeSurvey(disciplineName, projectName);
  }


  boolean fillSurvey(int id, String disciplineName, String projectName,
                     int hours, String comment)
          throws NoSuchDisciplineIdException, NoSuchProjectIdException,
          NoSurveyIdException {

      Student student = (Student) _peopleMap.get(id);
      return student.fillSurvey(disciplineName, projectName, hours, comment);
  }


  String showSurveyResults(Student student,
                           String disciplineName, String projectName)
          throws NoSuchDisciplineIdException, NoSuchProjectIdException,
          NoSurveyIdException {

      if(_peopleMap.containsKey(student.getId()))
        return student.showSurveyResults(disciplineName, projectName);

      return null;
  }


  String showSurveyResults(Teacher teacher,
                           String disciplineName, String projectName)
          throws NoSuchDisciplineIdException, NoSuchProjectIdException,
          NoSurveyIdException {

      if(_peopleMap.containsKey(teacher.getId()))
          return teacher.showSurveyResults(disciplineName, projectName);

      return null;
  }



  String showDisciplineSurveys(int id, String disciplineName)
          throws NoSuchDisciplineIdException {

      Student student = (Student) _peopleMap.get(id);
      return student.showDisciplineSurveys(disciplineName);
  }


  List<String> getNotifications(int id) {
      return _peopleMap.get(id).getNotifications();
  }
}