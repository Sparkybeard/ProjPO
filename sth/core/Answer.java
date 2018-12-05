package sth.core;

class Answer {
    private String _comment;
    private int _hours;


    Answer(String comment, int hours){
        _comment = comment;
        _hours = hours;
    }


    int getHours() {
        return _hours;
    }


    String getComment() {
        return _comment;
    }
}
