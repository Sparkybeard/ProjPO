package sth.core;

import sth.core.exception.BadEntryException;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

class Teacher extends Person {

    private List<Discipline> _disciplines;


    Teacher(int id, String name, int phoneNumber) {
        super(id, name, phoneNumber);
        _disciplines = new ArrayList<>();
    }


    @Override
    public String toString(){
        return "Teacher | " + super.toString();
    }


    @Override
    void parseContext(String lineContext, School school) throws BadEntryException {
        String components[] =  lineContext.split("\\|");

        if (components.length != 2)
            throw new BadEntryException("Invalid line context " + lineContext);

        Course course = school.parseCourse(components[0]);
        Discipline discipline = course.parseDiscipline(components[1]);

        discipline.addTeacher(this);
    }


    void addDiscipline(Discipline discipline){
        if(!_disciplines.contains(discipline))
            _disciplines.add(discipline);
    }


    List<Discipline> getDisciplines() {
        List<Discipline> listCopy = new ArrayList<>(_disciplines);
        return listCopy;
    }


    Discipline getDiscipline(String name){
        Iterator<Discipline> iter = _disciplines.iterator();
        Discipline d;

        while (iter.hasNext()) {
            d = iter.next();

            if(d.getName().equals(name))
                return d;
        }

        return null;
    }


    List<Student> getDisciplineStudents(String name){
        Discipline d = this.getDiscipline(name);
        return d.getStudentList();
    }
}
