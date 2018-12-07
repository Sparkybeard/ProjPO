package sth.core;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

import sth.core.exception.DuplicateSurveyException;
import sth.core.exception.FinishedSurveyException;
import sth.core.exception.NoSurveyIdException;
import sth.core.exception.SurveyNonEmptyException;


public class Project implements java.io.Serializable {

    /** Serial number for serialization. */
    private static final long serialVersionUID = 201810051538L;


    private String _name;
    private String _discription;
    private boolean _closed;

    private List<Submission> _submissions;
    private Survey _survey;


    Project(String name){
        _name = name;
        _closed = false;
        _submissions = new ArrayList<>();
    }


    public String getName() {
        return _name;
    }


    void close() {
        _closed = true;
    }


    void addSubmission(String message, int studentId){
        if(!_closed){
            Submission submission = new Submission(message, studentId);
            _submissions.add(submission);
            _submissions.sort(new SubmissionComparator());
        }
    }


    String seeSubmissions(){
        StringBuilder projectSubmissions = new StringBuilder();
        Iterator<Submission> iterator = _submissions.iterator();
        Submission submission;

        while(iterator.hasNext()){
            submission = iterator.next();

            projectSubmissions.append(submission.toString());
            projectSubmissions.append("\n");
        }

        return projectSubmissions.toString();
    }


    private List<Integer> getStudentList() {
        List<Integer> studentList = new ArrayList<>();
        Iterator<Submission> iterator = _submissions.iterator();

        while(iterator.hasNext()){
            studentList.add(iterator.next().getStudentId());
        }

        return studentList;
    }


    boolean createSurvey() throws DuplicateSurveyException {
        if(_survey != null)
            throw new DuplicateSurveyException();

        _survey = new Survey(getStudentList());
        return true;
    }


    boolean cancelSurvey() throws NoSurveyIdException,
            SurveyNonEmptyException, FinishedSurveyException {

        if(_survey == null)
            throw new NoSurveyIdException();

        return _survey.cancelSurvey();
    }


    boolean openSurvey() throws NoSurveyIdException {
        if(_survey == null)
            throw new NoSurveyIdException();

        return _survey.openSurvey();
    }


    boolean closeSurvey() throws NoSurveyIdException {
        if(_survey == null)
            throw new NoSurveyIdException();

        return _survey.closeSurvey();
    }


    boolean finalizeSurvey() throws NoSurveyIdException {
        if(_survey == null)
            throw new NoSurveyIdException();

        return _survey.finalizeSurvey();
    }


    boolean fillSurvey(Student student, int hours, String comment) throws NoSurveyIdException {
        if(_survey == null)
            throw new NoSurveyIdException();

        return _survey.fillSurvey(student, hours, comment);
    }


    String showSurveyResults(String disciplineName) throws NoSurveyIdException {
        if(_survey == null)
            throw new NoSurveyIdException();

        return _survey.showSurveyResults(disciplineName, getName());
    }


    String showPersonResults(String disciplineName, Student student) throws NoSurveyIdException {
        if(_survey == null)
            throw new NoSurveyIdException();

        return _survey.personResults(student, disciplineName, getName());
    }


    String representativeResults(String disciplineName) {
        if(_survey == null)
            return "";

        return _survey.representativeResults(disciplineName, getName());
    }
}
