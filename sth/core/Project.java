package sth.core;

public class Project {
    private String _name;
    private String _discription;
    private boolean _closed;


    Project(String name){
        _name = name;
        _closed = false;
    }


    Project(String name, String discription){
        _name = name;
        _closed = false;
        _discription = discription;
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
}
