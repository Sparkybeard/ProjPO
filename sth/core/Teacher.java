package sth.core;

import java.util.List;
import java.util.ArrayList;

class Teacher extends Person{

    private List<Discipline> _disciplines;


    Teacher(int id, String name, int phoneNumber) {
        super(id, name, phoneNumber);
        _disciplines = new ArrayList<>();
    }


    @Override
    public String toString(){
        return "Teacher | " + super.toString();
    }


}
