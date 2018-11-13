package sth.core;

import java.util.List;
import java.util.ArrayList;

class Course {

    private String _name;

    private List<Integer> _studentList;
    private List<Discipline> _disciplineList;


    Course(String name) {
        _name = name;

        _studentList = new ArrayList<>();
        _disciplineList = new ArrayList<>();
    }

    String getName(){
        return _name;
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
}
