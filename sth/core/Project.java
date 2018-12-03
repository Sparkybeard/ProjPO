package sth.core;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;


public class Project {
    private String _name;
    private String _discription;
    private boolean _closed;

    private List<Submission> _submissions;


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
}
