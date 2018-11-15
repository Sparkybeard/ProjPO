package sth.core;

import sth.core.exception.BadEntryException;
import sth.core.exception.NoSuchDisciplineIdException;
import sth.core.exception.NoSuchProjectIdException;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

class Teacher extends Person {

    private List<Discipline> _disciplines;


    Teacher(int id, String name, int phoneNumber) {
        super(id, name, phoneNumber);
        _disciplines = new ArrayList<>();
    }


    @Override
    public String toString(){
        return "Teacher | " + super.toString();
    }


    @Override
    void parseContext(String lineContext, School school) throws BadEntryException {
        String components[] =  lineContext.split("\\|");

        if (components.length != 2)
            throw new BadEntryException("Invalid line context " + lineContext);

        Course course = school.parseCourse(components[0]);
        Discipline discipline = course.parseDiscipline(components[1]);

        discipline.addTeacher(this);
    }


    String getInformation() {
        String teacherString = toString();
        StringBuilder teacherInformation = new StringBuilder(teacherString + "\n");

        DisciplineComparator dc = new DisciplineComparator();
        _disciplines.sort(dc);

        List<Course> courseList = getCourseList();
        Iterator<Course> courseIterator = courseList.iterator();
        Course course;

        while(courseIterator.hasNext()){
            course = courseIterator.next();

            teacherInformation.append(getCourseInformation(course));
        }

        return teacherInformation.toString();
    }


    private List<Course> getCourseList() {
        Iterator<Discipline> iterator = _disciplines.iterator();
        List<Course> courseList = new ArrayList<>();
        Course course;

        while(iterator.hasNext()){
            course = iterator.next().getCourse();

            if(!courseList.contains(course))
                courseList.add(iterator.next().getCourse());
        }

        CourseComparator cc = new CourseComparator();
        courseList.sort(cc);

        return courseList;
    }


    private String getCourseInformation(Course course){
        StringBuilder courseInformation = new StringBuilder(course.toString());
        Iterator<Discipline> iterator = _disciplines.iterator();
        Discipline discipline;

        while(iterator.hasNext()){
            discipline = iterator.next();

            if(course.hasDiscipline(discipline)){
                courseInformation.append(" - ");
                courseInformation.append(discipline.getName());
                courseInformation.append("\n");
            }
        }

        return courseInformation.toString();
    }


    void addDiscipline(Discipline discipline) {
        if(!_disciplines.contains(discipline))
            _disciplines.add(discipline);
    }


    List<Discipline> getDisciplines() {
        List<Discipline> listCopy = new ArrayList<>(_disciplines);
        return listCopy;
    }


    Discipline getDiscipline(String disciplineName) throws NoSuchDisciplineIdException{
        Iterator<Discipline> iter = _disciplines.iterator();
        Discipline d;

        while (iter.hasNext()) {
            d = iter.next();

            if(d.getName().equals(disciplineName))
                return d;
        }

        throw new NoSuchDisciplineIdException(disciplineName);
    }


    List<Student> getDisciplineStudents(String disciplineName) throws NoSuchDisciplineIdException {
        Discipline d = getDiscipline(disciplineName);
        return d.getStudentList();
    }


    void closeProject(String projectName, String disciplineName)
            throws NoSuchProjectIdException, NoSuchDisciplineIdException {

        Iterator<Discipline> iterator = _disciplines.iterator();
        Discipline discipline;

        while(iterator.hasNext()){
            discipline = iterator.next();

            if(discipline.getName().equals(disciplineName))
                discipline.closeProject(projectName);
        }

        throw new NoSuchDisciplineIdException(disciplineName);
    }


    boolean createProject(String projectName, String disciplineName)
            throws NoSuchDisciplineIdException {

        Iterator<Discipline> iterator = _disciplines.iterator();
        Discipline discipline;

        while(iterator.hasNext()){
            discipline = iterator.next();

            if(discipline.getName().equals(disciplineName))
                return discipline.createProject(projectName);
        }

        throw new NoSuchDisciplineIdException(disciplineName);
    }
}
