package sth.core;

import java.util.Comparator;

class DisciplineComparator implements Comparator<Discipline> {
    public int compare(Discipline d1, Discipline d2){
        return d1.getName().compareTo(d2.getName());
    }
}
