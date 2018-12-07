package sth.core;

class Answer implements java.io.Serializable {

    /** Serial number for serialization. */
    private static final long serialVersionUID = 201810051538L;

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
