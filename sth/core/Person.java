package sth.core;

abstract class Person {

    private int _id;
    private String _name;
    private int _phoneNumber;


    Person(int id, String name, int phoneNumber){
        _id = id;
        _name = name;
        _phoneNumber = phoneNumber;
    }


    public String toString(){
        return _id + " | " + _phoneNumber + " | " + _name;
    }


    String getName() {
        return _name;
    }


    int get_id() {
        return _id;
    }


    int getPhoneNumber() {
        return _phoneNumber;
    }
}
