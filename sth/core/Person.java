package sth.core;
import sth.core.exception.BadEntryException;


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

    String getInformation() {
        return toString();
    }

    String getName() {
        return _name;
    }


    int getId() {
        return _id;
    }


    int getPhoneNumber() {
        return _phoneNumber;
    }

    void changePhoneNumber(int phoneNumber){
        _phoneNumber = phoneNumber;
    }

    /**
     * Parses the context information for a person from the import file.
     * This method defines the default behavior: no extra information is needed
     * thus it throws the exception.
     **/
    void parseContext(String context, School school) throws BadEntryException {
        throw new BadEntryException("Should not have extra context: " + context);
    }
}
