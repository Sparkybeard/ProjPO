package sth.core;

import sth.core.exception.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

class Student extends Person {

    private boolean _isRepresentative;

    private Course _course;
    private List<Discipline> _disciplines;


    Student(int id, String name, int phoneNumber, boolean isRepresentative) {
        super(id, name, phoneNumber);
        _isRepresentative = isRepresentative;

        _disciplines = new ArrayList<>();
    }


    @Override
    public String toString() {
        if(_isRepresentative)
            return "DELEGADO|" + super.toString();

        else return "ALUNO|" + super.toString();
    }


    @Override
    void parseContext(String lineContext, School school) throws BadEntryException {
        String components[] =  lineContext.split("\\|");

        if (components.length != 2)
            throw new BadEntryException("Invalid line context " + lineContext);

        if (_course == null) {
            _course = school.parseCourse(components[0]);
            _course.addStudent(this);
        }

        Discipline dis = _course.parseDiscipline(components[1]);

        dis.enrollStudent(this);
    }


    @Override
    String getInfo() {
        String courseString = _course.toString();
        StringBuilder studentInformation = new StringBuilder();

        DisciplineComparator dc = new DisciplineComparator();
        _disciplines.sort(dc);

        Iterator<Discipline> iterator = _disciplines.iterator();

        while (iterator.hasNext()){
            String disciplineInformation = iterator.next().getName();
            studentInformation.append("\n");
            studentInformation.append(courseString);
            studentInformation.append("-");
            studentInformation.append(disciplineInformation);
        }

        return studentInformation.toString();
    }


    boolean addDiscipline(Discipline discipline) {
        if(_disciplines.size() >= 6)
            return false;

        _disciplines.add(discipline);
        return true;
    }


    private Discipline getDiscipline(String disciplineName)
            throws NoSuchDisciplineIdException{

        Iterator<Discipline> iterator = _disciplines.iterator();
        Discipline discipline;

        while(iterator.hasNext()){
            discipline = iterator.next();

            if(discipline.getName().equals(disciplineName))
                return discipline;
        }

        throw new NoSuchDisciplineIdException(disciplineName);
    }


    void setRepresentative(boolean value) {
        _isRepresentative = value;
    }


    boolean isRepresentative() {
        return _isRepresentative;
    }


    void addSubmission(
            String disciplineName, String projectName, String message)
            throws NoSuchDisciplineIdException, NoSuchProjectIdException {

        Discipline discipline = getDiscipline(disciplineName);
        discipline.addSubmission(projectName, message, getId());
    }


    String showSurveyResults(String disciplineName, String projectName)
            throws NoSuchDisciplineIdException, NoSuchProjectIdException,
            NoSurveyIdException {

        Discipline discipline = getDiscipline(disciplineName);
        return discipline.showSurveyResults(projectName, this);
    }


    boolean createSurvey(String disciplineName, String projectName)
            throws NoSuchDisciplineIdException, NoSuchProjectIdException,
            DuplicateSurveyException {

        if(_isRepresentative){
            Discipline discipline = getDiscipline(disciplineName);

            Notification notification = getNotification(disciplineName, projectName);
            notification.setState("por abrir");

            return discipline.createSurvey(projectName, this);
        }

        return false;
    }


    boolean cancelSurvey(String disciplineName, String projectName)
            throws NoSuchDisciplineIdException, NoSuchProjectIdException,
            NoSurveyIdException, SurveyNonEmptyException, FinishedSurveyException {

        if(_isRepresentative){
            Discipline discipline = getDiscipline(disciplineName);
            return discipline.cancelSurvey(projectName, this);
        }

        return false;
    }


    boolean openSurvey(String disciplineName, String projectName)
            throws NoSuchDisciplineIdException, NoSuchProjectIdException,
            NoSurveyIdException {

        if(_isRepresentative){
            Discipline discipline = getDiscipline(disciplineName);

            Notification notification = getNotification(disciplineName, projectName);
            notification.setState("aberto");
            notification.notifyObservers();

            return discipline.openSurvey(projectName, this);
        }

        return false;
    }


    boolean closeSurvey(String disciplineName, String projectName)
            throws NoSuchDisciplineIdException, NoSuchProjectIdException,
            NoSurveyIdException {

        if(_isRepresentative){
            Discipline discipline = getDiscipline(disciplineName);

            Notification notification = getNotification(disciplineName, projectName);
            notification.setState("fechado");

            return discipline.closeSurvey(projectName, this);
        }

        return false;
    }


    boolean finalizeSurvey(String disciplineName, String projectName)
            throws NoSuchDisciplineIdException, NoSuchProjectIdException,
            NoSurveyIdException {

        if(_isRepresentative){
            Discipline discipline = getDiscipline(disciplineName);

            Notification notification = getNotification(disciplineName, projectName);
            notification.setState("finalizado");
            notification.notifyObservers();

            return discipline.finalizeSurvey(projectName, this);
        }

        return false;
    }


    boolean fillSurvey(String disciplineName, String projectName,
                       int hours, String comment)
            throws NoSuchDisciplineIdException, NoSuchProjectIdException,
            NoSurveyIdException{

        if(_isRepresentative){
            Discipline discipline = getDiscipline(disciplineName);
            return discipline.fillSurvey(projectName, this,
                    hours, comment);
        }

        return false;
    }


    String showDisciplineSurveys(String disciplineName)
            throws NoSuchDisciplineIdException {

        if(_isRepresentative){
            Discipline discipline = getDiscipline(disciplineName);
            return discipline.representativeResults(this);
        }

        return null;
    }


    @Override
    String getMessage(String message, String disciplineName, String projectName){
        if(message.equals("aberto")) {
            return "Pode preencher inquérito do projeto " + projectName
                    + " da disciplina " + disciplineName;
        }


        if(message.equals("finalizado")) {
            return "Resultados do inquérito do projeto " + projectName
                    + " da disciplina " + disciplineName;
        }

        else return null;
    }
}