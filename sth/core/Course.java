package sth.core;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;


class Course implements java.io.Serializable {

    /** Serial number for serialization. */
    private static final long serialVersionUID = 201810051538L;


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

    String getName() {
        return _name;
    }

    public String toString(){
        return "* " + _name;
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
        return new ArrayList<>(_disciplineList);
    }


    boolean hasDiscipline(Discipline discipline) {
        return _disciplineList.contains(discipline);
    }


    Discipline parseDiscipline(String disciplineName){
        Iterator<Discipline> iterator = _disciplineList.iterator();
        Discipline d;

        /* Check if discipline already exists */
        while(iterator.hasNext()){
            d = iterator.next();

            if(d.getName().equals(disciplineName))
                return d;
        }

        /* If there isn't, create one */
        d = new Discipline(disciplineName, this);
        addDiscipline(d);
        return d;
    }
}


class CourseComparator implements Comparator<Course> {
    public int compare(Course c1, Course c2){
        return c1.getName().compareTo(c2.getName());
    }
}
