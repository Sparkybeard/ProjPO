package sth.core;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import sth.core.exception.BadEntryException;

import java.util.Comparator;


abstract class Person implements java.io.Serializable, Observer {

    /** Serial number for serialization. */
    private static final long serialVersionUID = 201810051538L;

    private int _id;
    private String _name;
    private int _phoneNumber;

    private List<Notification> _notificationList = new ArrayList<>();
    private List<String> _messages = new ArrayList<>();


    Person(int id, String name, int phoneNumber){
        _id = id;
        _name = name;
        _phoneNumber = phoneNumber;
    }


    @Override
    public String toString(){
        return _id + "|" + _phoneNumber + "|" + _name;
    }

    String getInformation() {
        return toString();
    }

    String getName() {
        return _name;
    }


    int getId() {
        return _id;
    }

    int getPhoneNumber() {
        return _phoneNumber;
    }

    void changePhoneNumber(int phoneNumber){
        _phoneNumber = phoneNumber;
    }

    /**
     * Parses the context information for a person from the import file.
     * This method defines the default behavior: no extra information is needed
     * thus it throws the exception.
     **/
    void parseContext(String context, School school) throws BadEntryException {
        throw new BadEntryException("Should not have extra context: " + context);
    }


    void addNotification(String disciplineName, String projectName) {
        _notificationList.add(new Notification(disciplineName, projectName));
    }


    private boolean rightNotification(String disciplineName, String projectName,
                                      Notification notification){

        return notification.getDisciplineName().equals(disciplineName) &&
                notification.getProjectName().equals(projectName);
    }



    Notification getNotification(String disciplineName, String projectName){
        Iterator<Notification> iterator = _notificationList.iterator();
        Notification notification;

        while (iterator.hasNext()) {
            notification = iterator.next();

            if(rightNotification(disciplineName, projectName, notification))
                return notification;
        }

        return null;
    }


    void recieveNotification(String disciplineName, String projectName) {
        Notification notification = getNotification(disciplineName, projectName);

        if(notification == null)
            return;

        notification.registerObserver(this);
    }


    void muteNotification(String disciplineName, String projectName) {
        Notification notification = getNotification(disciplineName, projectName);

        if(notification == null)
            return;

        notification.removeObserver(this);
    }


    abstract String getMessage(String message, String disciplineName, String projectName);


    @Override
    public void update(String state, Subject subject) {
        Notification notification = (Notification) subject;
        String disciplineName = notification.getDisciplineName();
        String projectName = notification.getProjectName();
        String message = getMessage(state, disciplineName, projectName);

        if(message == null)
            return;

        _messages.add(message);
    }


    List<String> getNotifications(){
        List<String> messages = new ArrayList<>(_messages);
        _messages.clear();
        return messages;
    }
}


class PersonComparator implements Comparator<Person> {
    public int compare(Person p1, Person p2){
        return Integer.compare(p1.getId(), p2.getId());
    }
}
