package sth.core;

class Employee extends Person {

    Employee(int id, String name, int phoneNumber) {
        super(id, name, phoneNumber);
    }


    @Override
    public String toString() {
        return "FUNCIONÁRIO|" + super.toString();
    }

    String getInfo() {
        return "";
    }

    @Override
    String getMessage(String message, String disciplineName, String projectName){
        return null;
    }
}
