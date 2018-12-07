package sth.core;


import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;


class Notification implements Subject {
    private String _disciplineName;
    private String _projectName;
    private String _surveyState;

    private List<Observer> _observerList = new ArrayList<>();


    Notification(String disciplineName, String projectName){
        _disciplineName = disciplineName;
        _projectName = projectName;
    }


    String getDisciplineName() {
        return _disciplineName;
    }


    String getProjectName() {
        return _projectName;
    }


    void setState(String state) {
        _surveyState = state;
    }


    public void registerObserver(Observer observer) {
        _observerList.add(observer);
    }


    public void removeObserver(Observer observer) {
        _observerList.remove(observer);
    }


    public void notifyObservers(){
        Iterator<Observer> iterator = _observerList.iterator();

        while (iterator.hasNext()) {
            iterator.next().update(_surveyState, this);
        }
    }
}
