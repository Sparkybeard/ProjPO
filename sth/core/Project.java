package sth.core;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;


public class Project {
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


    Project(String name, String discription){
        _name = name;
        _closed = false;
        _discription = discription;
        _submissions = new ArrayList<>();
    }


    public String getName() {
        return _name;
    }


    void setDiscription(String discription){
        _discription = discription;
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


    boolean createSurvey() {
        if(_survey != null)
            return false;

        _survey = new Survey(getStudentList());
        return true;
    }


    boolean cancelSurvey() {
        if(_survey.cancelSurvey()){
            _survey = null;
            return true;
        }

        return false;
    }


    boolean openSurvey() {
        if(_survey == null)
            return false;

        return _survey.openSurvey();
    }


    boolean closeSurvey() {
        if(_survey == null)
            return false;

        return _survey.closeSurvey();
    }


    boolean finalizeSurvey() {
        if(_survey == null)
            return false;

        return _survey.finalizeSurvey();
    }


    boolean fillSurvey(Student student, int hours, String comment) {
        if(_survey == null)
            return false;

        return _survey.fillSurvey(student, hours, comment);
    }


    String showSurveyResults(String disciplineName) {
        return _survey.showSurveyResults(disciplineName, getName());
    }


    String showPersonResults(String disciplineName, Student student) {
        return _survey.personResults(student, disciplineName, getName());
    }


    String representativeResults(String disciplineName) {
        return _survey.representativeResults(disciplineName, getName());
    }
}
