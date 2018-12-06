package sth.core;

import java.util.Comparator;


class Submission implements java.io.Serializable {

    /** Serial number for serialization. */
    private static final long serialVersionUID = 201810051538L;

    private String _message;
    private int _studentId;


    Submission(String message, int studentId){
        _message = message;
        _studentId = studentId;
    }


    int getStudentId(){
        return _studentId;
    }


    public String toString(){
        return "* " + _studentId + " - " + _message;
    }
}


class SubmissionComparator implements Comparator<Submission>{
    public int compare(Submission s1, Submission s2){
        return Integer.compare(s1.getStudentId(), s2.getStudentId());
    }
}