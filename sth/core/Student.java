package sth.core;

import sth.app.exception.NoSuchDisciplineException;

import java.util.ArrayList;
import java.util.List;

class Student extends Person {

    private boolean _isRepresentative;

    private Course _course;
    private List<Discipline> _disciplines;


    Student(int id, String name, int phoneNumber) {
        super(id, name, phoneNumber);
        _isRepresentative = false;

        _disciplines = new ArrayList<>();
    }


    @Override
    public String toString() {
        return "Student | " + super.toString();
    }


    void setCourse(Course course) {
        _course = course;
    }


    Course getCourse() {
        return _course;
    }


    void addDiscipline(Discipline discipline) throws NoSuchDisciplineException {
        if(_course.hasDiscipline(discipline))
            _disciplines.add(discipline);

        else throw new NoSuchDisciplineException(discipline.getName());
    }


    void setRepresentative(boolean value) {
        _isRepresentative = value;
    }


    boolean isRepresentative() {
        return _isRepresentative;
    }


}
