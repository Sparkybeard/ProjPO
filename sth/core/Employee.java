package sth.core;

class Employee extends Person {

    Employee(int id, String name, int phoneNumber) {
        super(id, name, phoneNumber);
    }


    @Override
    public String toString() {
        return "Employee" + super.toString();
    }

    String getInformation() {
        return toString();
    }
}
