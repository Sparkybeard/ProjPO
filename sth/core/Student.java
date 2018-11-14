package sth.core;

import sth.app.exception.NoSuchDisciplineException;
import sth.core.exception.BadEntryException;

import java.util.ArrayList;
import java.util.List;

class Student extends Person {

    private boolean _isRepresentative;

    private Course _course;
    private List<Discipline> _disciplines;


    Student(int id, String name, int phoneNumber, boolean isRepresentative) {
        super(id, name, phoneNumber);
        _isRepresentative = isRepresentative;

        _disciplines = new ArrayList<>();
    }


    @Override
    public String toString() {
        return "Student | " + super.toString();
    }


    @Override
    void parseContext(String lineContext, School school) throws BadEntryException {
        String components[] =  lineContext.split("\\|");

        if (components.length != 2)
            throw new BadEntryException("Invalid line context " + lineContext);

        if (_course == null) {
            _course = school.parseCourse(components[0]);
            _course.addStudent(this);
        }

        Discipline dis = _course.parseDiscipline(components[1]);

        dis.enrollStudent(this);
    }


    void setCourse(Course course) {
        _course = course;
        _disciplines.clear();
    }


    Course getCourse() {
        return _course;
    }


    boolean addDiscipline(Discipline discipline) throws NoSuchDisciplineException {
        if(_disciplines.size() >= 6)
            return false;

        else if(_course.hasDiscipline(discipline)) {
            _disciplines.add(discipline);
            return true;
        }

        else throw new NoSuchDisciplineException(discipline.getName());
    }


    void setRepresentative(boolean value) {
        _isRepresentative = value;
    }


    boolean isRepresentative() {
        return _isRepresentative;
    }


}
