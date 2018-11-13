package sth.core;

import java.util.ArrayList;
import java.util.List;

class Discipline {

    private String _name;
    private int _capacity;
    private int _numberStudents;

    private List<Student> _studentList;
    private List<Teacher> _teacherList;
    private Course _course;


    Discipline(String name, int capacity, Course course) {
        _name = name;
        _capacity = capacity;
        _numberStudents = 0;

        _course = course;
        _studentList = new ArrayList<>();
        _teacherList = new ArrayList<>();
    }


    public String getName() {
        return _name;
    }


    Course getCourse() {
        return _course;
    }


    void addTeacher(Teacher teacher) {
        _teacherList.add(teacher);
    }


    boolean enrollStudent(Student student) {
        if(_numberStudents + 1 > _capacity)
           return false;

        else {
            _studentList.add(student);
            _numberStudents++;
            return true;
        }
    }
}
