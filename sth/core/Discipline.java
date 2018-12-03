package sth.core;

import sth.core.exception.NoSuchDisciplineIdException;
import sth.core.exception.NoSuchPersonIdException;
import sth.core.exception.NoSuchProjectIdException;

import java.util.Comparator;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

class Discipline {

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
}


class DisciplineComparator implements Comparator<Discipline> {
    public int compare(Discipline d1, Discipline d2){
        return d1.getName().compareTo(d2.getName());
    }
}
