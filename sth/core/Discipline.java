package sth.core;

import sth.core.exception.NoSuchDisciplineIdException;
import sth.core.exception.NoSuchDisciplineIdException;
import sth.core.exception.NoSuchProjectIdException;

import java.util.Comparator;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

class Discipline implements java.io.Serializable {

    /** Serial number for serialization. */
    private static final long serialVersionUID = 201810051538L;

    private String _name;
    private int _capacity;
    private int _numberStudents;

    private List<Student> _studentList;
    private List<Teacher> _teacherList;
    private List<Project> _projectList;
    private Course _course;


    Discipline(String name, Course course) {
        _name = name;
        _capacity = 100;
        _numberStudents = 0;

        _course = course;
        _studentList = new ArrayList<>();
        _teacherList = new ArrayList<>();
        _projectList = new ArrayList<>();
    }


    public String getName() {
        return _name;
    }


    void setCapacity(int capacity){
        _capacity = capacity;
    }


    Course getCourse() {
        return _course;
    }


    void addTeacher(Teacher teacher) {
        _teacherList.add(teacher);
        teacher.addDiscipline(this);
    }


    private boolean hasTeacher(Teacher teacher) {
        return _teacherList.contains(teacher);
    }


    private boolean hasStudent(Student student) {
        return _studentList.contains(student);
    }


    void enrollStudent(Student student) {

            if (_numberStudents + 1 <= _capacity) {
                _studentList.add(student);
                student.addDiscipline(this);
                _numberStudents++;
                _studentList.sort(new PersonComparator());
        }
    }


    List<Student> getStudentList(){
        return new ArrayList<>(_studentList);
    }


    void closeProject(String projectName) throws NoSuchProjectIdException {
        Iterator<Project> iterator = _projectList.iterator();
        Project project;

        while(iterator.hasNext()){
            project = iterator.next();

            if(project.getName().equals(projectName)){
                project.close();
                return;
            }
        }

        throw new NoSuchProjectIdException(projectName);
    }


    private void addNotificationTeachers(String projectName) {
        Iterator<Teacher> iterator = _teacherList.iterator();

        while(iterator.hasNext()) {
            iterator.next().addNotification(getName(), projectName);
        }
    }


    private void addNotificationStudents(String projectName) {
        Iterator<Student> iterator = _studentList.iterator();

        while(iterator.hasNext()) {
            iterator.next().addNotification(getName(), projectName);
        }
    }


    private void addNotifications(String projectName){
        addNotificationTeachers(projectName);
        addNotificationStudents(projectName);
    }


    /**
     *
     * @param projectName name of the project to create
     * @return false if project already exists. True otherwise
     */
    boolean createProject(String projectName) {
        Iterator<Project> iterator = _projectList.iterator();
        Project project;

        while(iterator.hasNext()){
            project = iterator.next();

            if(project.getName().equals(projectName))
                return false;
        }

        project = new Project(projectName);
        _projectList.add(project);
        addNotifications(projectName);
        return true;
    }


    private Project getProject(String projectName)
            throws NoSuchProjectIdException {

        Iterator<Project> iterator = _projectList.iterator();
        Project project;

        while(iterator.hasNext()){
            project = iterator.next();

            if(project.getName().equals(projectName))
                return project;
        }

        throw new NoSuchProjectIdException(projectName);
    }


    void addSubmission(String projectName, String message, int id)
            throws NoSuchProjectIdException {

        Project project = getProject(projectName);
        project.addSubmission(message, id);
    }


    String seeSubmissions(String projectName) throws NoSuchProjectIdException {
        Project project = getProject(projectName);

        return getName() + " - " + projectName +
                "\n" + project.seeSubmissions();
    }


    boolean createSurvey(String projectName, Student student)
            throws NoSuchProjectIdException, NoSuchDisciplineIdException{

        if(!hasStudent(student))
            throw new NoSuchDisciplineIdException(getName());

        return getProject(projectName).createSurvey();
    }


    boolean cancelSurvey(String projectName, Student student)
            throws NoSuchProjectIdException, NoSuchDisciplineIdException{

        if(!hasStudent(student))
            throw new NoSuchDisciplineIdException(getName());

        return getProject(projectName).cancelSurvey();
    }


    boolean openSurvey(String projectName, Student student)
            throws NoSuchProjectIdException, NoSuchDisciplineIdException {

        if (!hasStudent(student))
            throw new NoSuchDisciplineIdException(getName());

        return getProject(projectName).openSurvey();
    }


    boolean closeSurvey(String projectName, Student student)
            throws NoSuchProjectIdException, NoSuchDisciplineIdException {

        if (!hasStudent(student))
            throw new NoSuchDisciplineIdException(getName());

        return getProject(projectName).closeSurvey();
    }


    boolean finalizeSurvey(String projectName, Student student)
            throws NoSuchProjectIdException, NoSuchDisciplineIdException {

        if (!hasStudent(student))
            throw new NoSuchDisciplineIdException(getName());

        return getProject(projectName).finalizeSurvey();
    }


    boolean fillSurvey(String projectName, Student student,
                       int hours, String comment)
            throws NoSuchProjectIdException, NoSuchDisciplineIdException {

        if (!hasStudent(student))
            throw new NoSuchDisciplineIdException(getName());

        return getProject(projectName).fillSurvey(student, hours, comment);
    }


    String showSurveyResults(String projectName, Teacher teacher)
            throws NoSuchProjectIdException, NoSuchDisciplineIdException{

        if(!hasTeacher(teacher))
            throw new NoSuchDisciplineIdException(getName());

        return getProject(projectName).showSurveyResults(getName());
    }


    String showSurveyResults(String projectName, Student student)
            throws NoSuchProjectIdException, NoSuchDisciplineIdException {

        if(!hasStudent(student))
            throw new NoSuchDisciplineIdException(getName());

        return getProject(projectName).showPersonResults(getName(), student);
    }


    String representativeResults(Student student)
            throws NoSuchDisciplineIdException {

        if(!hasStudent(student))
            throw new NoSuchDisciplineIdException(getName());

        StringBuilder results = new StringBuilder();
        Project project;
        Iterator<Project> iterator = _projectList.iterator();

        while(iterator.hasNext()) {
            project = iterator.next();

            results.append(project.representativeResults(getName()));
            results.append("\n");
        }

        return results.toString();
    }
}


class DisciplineComparator implements Comparator<Discipline> {
    public int compare(Discipline d1, Discipline d2){
        return d1.getName().compareTo(d2.getName());
    }
}
