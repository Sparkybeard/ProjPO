package sth.core;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

class Course {

    private String _name;
    private int _numberRepresentatives;

    private List<Student> _studentList;
    private List<Discipline> _disciplineList;


    Course(String name) {
        _name = name;

        _studentList = new ArrayList<>();
        _disciplineList = new ArrayList<>();
        _numberRepresentatives = 0;
    }

    String getName(){
        return _name;
    }


    boolean addStudent(Student student) {
        if(student.isRepresentative() && _numberRepresentatives < 7){
            _studentList.add(student);
            return true;
        }

        else if(student.isRepresentative() && _numberRepresentatives >= 7)
            return false;

        else {
            _studentList.add(student);
            return true;
        }
    }


    void addDiscipline(Discipline discipline) {
        _disciplineList.add(discipline);
    }


    List getDisciplines() {
        List listCopy = new ArrayList<Discipline>(_disciplineList);
        return listCopy;
    }


    boolean hasDiscipline(Discipline discipline) {
        return _disciplineList.contains(discipline);
    }


    Discipline parseDiscipline(String disciplineName){
        Iterator<Discipline> iter = _disciplineList.iterator();
        Discipline d;

        /* Check if discipline already exists */
        while(iter.hasNext()){
            d = iter.next();

            if(d.getName().equals(disciplineName))
                return d;
        }

        /* If there isn't, create one */
        d = new Discipline(disciplineName, this);
        _disciplineList.add(d);
        return d;
    }
}
