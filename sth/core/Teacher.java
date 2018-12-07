package sth.core;

import sth.core.exception.*;

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
        return "DOCENTE|" + super.toString();
    }


    @Override
    void parseContext(String lineContext, School school)
            throws BadEntryException {

        String components[] =  lineContext.split("\\|");

        if (components.length != 2)
            throw new BadEntryException("Invalid line context " + lineContext);

        Course course = school.parseCourse(components[0]);
        Discipline discipline = course.parseDiscipline(components[1]);

        discipline.addTeacher(this);
    }


    String getInformation() {
        String teacherString = toString();
        StringBuilder teacherInformation = new StringBuilder(teacherString);

        DisciplineComparator dc = new DisciplineComparator();
        _disciplines.sort(dc);

        List<Course> courseList = getCourseList();
        Iterator<Discipline> disciplineIterator = _disciplines.iterator();
        Discipline discipline;

        while(disciplineIterator.hasNext()){
            discipline = disciplineIterator.next();
            teacherInformation.append("\n* ");
            teacherInformation.append(discipline.getCourse().getName());

            teacherInformation.append(" - ");
            teacherInformation.append(discipline.getName());
        }

        return teacherInformation.toString();
    }


    private List<Course> getCourseList() {

        if (_disciplines != null ) {
            Iterator<Discipline> iterator = _disciplines.iterator();
            List<Course> courseList = new ArrayList<>();
            Course course;

            while (iterator.hasNext()) {
                course = iterator.next().getCourse();
                courseList.add(course);
            }

            CourseComparator cc = new CourseComparator();
            courseList.sort(cc);

            return courseList;
        }
        return null;
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

            }
        }

        return courseInformation.toString();
    }


    void addDiscipline(Discipline discipline) {
        if(!_disciplines.contains(discipline))
            _disciplines.add(discipline);
    }


    List<Discipline> getDisciplines() {
        return new ArrayList<>(_disciplines);
    }


    private Discipline getDiscipline(String disciplineName)
            throws NoSuchDisciplineIdException{

        Iterator<Discipline> iterator = _disciplines.iterator();
        Discipline d;

        while (iterator.hasNext()) {
            d = iterator.next();

            if(d.getName().equals(disciplineName))
                return d;
        }

        throw new NoSuchDisciplineIdException(disciplineName);
    }


    List<Student> getDisciplineStudents(String disciplineName)
            throws NoSuchDisciplineIdException {

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

        Discipline discipline =  getDiscipline(disciplineName);

        if (discipline == null) {
            throw new NoSuchDisciplineIdException(disciplineName);
        }

        return discipline.createProject(projectName);

    }


    String seeSubmissions(String disciplineName, String projectName)
            throws NoSuchDisciplineIdException, NoSuchProjectIdException{

        return getDiscipline(disciplineName).seeSubmissions(projectName);
    }


    String showSurveyResults(String disciplineName, String projectName)
            throws NoSuchDisciplineIdException, NoSuchProjectIdException,
            NoSurveyIdException {

        Discipline discipline = getDiscipline(disciplineName);
        return discipline.showSurveyResults(projectName, this);
    }

    //FIXME falta acentos
    @Override
    String getMessage(String message, String disciplineName, String projectName){
        if(message.equals("finalizado")) {
            return "Resultados do inquerito do projeto " + projectName
                    + " da disciplina " + disciplineName;
        }

        else return null;
    }
}
