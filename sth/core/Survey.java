package sth.core;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;


class Survey {
    private SurveyState _state; //FIXME enum
    private List<Answer> _answers;
    private List<Integer> _surveydList;
    private List<Integer> _submittedList;


    Survey(List<Integer> submittedList) {
        _state = new CreatedSurvey(this);
        _answers = new ArrayList<>();
        _surveydList = new ArrayList<>();
        _submittedList = new ArrayList<>(submittedList);
    }


    String getSurveyState() {
        return _state.getStateName();
    }


    void addAnswer(Student student, int hours, String comment){
        Answer answer = new Answer(comment, hours);
        _surveydList.add(student.getId());
        _answers.add(answer);
    }


    boolean noAnswers() {
        return _answers.isEmpty();
    }


    boolean canSubmit(Student student){
        return !hasSurveyd(student) && hasSubmitted(student);
    }


    /**
     *
     * @param student taking the survey
     * @return true if the student made a submission in the project.
     * False otherwise.
     *
     */
    private boolean hasSubmitted(Student student){
        return _submittedList.contains(student.getId());
    }


    /**
     *
     * @param student taking the survey
     * @return true if the student has already taken this survey.
     * False otherwise.
     *
     */
    boolean hasSurveyd(Student student){
        return _surveydList.contains(student.getId());
    }


    int numberSubmissions() {
        return _submittedList.size();
    }


    int numberAnswers () {
        return _answers.size();
    }


    /**
     *
     * @return int[0] = min, int[1] = max, int[2] = average.
     */
    int[] numberHours() {
        int[] hoursArray = new int[3];

        int currentHour;
        int minHours;
        int maxHours;
        int averageHours;

        Iterator<Answer> iterator = _answers.iterator();

        if(iterator.hasNext()){
            minHours = iterator.next().getHours();
            maxHours = minHours;
            averageHours = minHours;
        }

        else return null;


        while (iterator.hasNext()){
            currentHour = iterator.next().getHours();

            if(currentHour > maxHours)
                maxHours = currentHour;

            if(currentHour < minHours)
                minHours = currentHour;

            averageHours += currentHour;
        }

        hoursArray[0] = minHours;
        hoursArray[1] = maxHours;
        hoursArray[2] = averageHours / _answers.size();

        return hoursArray;
    }


    /* State Dependant Functions */

    void setState(SurveyState state) {
        _state = state;
    }

    boolean cancelSurvey() {
        return _state.cancelSurvey();
    }

    boolean openSurvey() {
        return _state.openSurvey();
    }

    boolean closeSurvey() {
        return _state.closeSurvey();
    }

    boolean finalizeSurvey() {
        return _state.finalizeSurvey();
    }

    boolean fillSurvey(Student student, int hours, String comment) {
        return _state.fillSurvey(student, hours, comment);
    }

    String personResults(Student student,
                         String disciplineName, String projectName) {
        return _state.studentResults(student, disciplineName, projectName);
    }

    String showSurveyResults(String disciplineName, String projectName) {
        return _state.showSurveyResults(disciplineName, projectName);
    }


    String representativeResults(String disciplineName, String projectName) {
        return _state.representativeResults(disciplineName, projectName);
    }
}


abstract class SurveyState{
    Survey _survey;
    private String _stateName;

    SurveyState(Survey survey){
        _survey = survey;
    }

    void setStateName(String stateName) {
        _stateName = stateName;
    }

    String getStateName() {
        return _stateName;
    }

    abstract boolean cancelSurvey();
    abstract boolean openSurvey();
    abstract boolean closeSurvey();
    abstract boolean finalizeSurvey();
    abstract boolean fillSurvey(Student student, int hours, String comment);

    abstract String studentResults(Student student,
                                   String disciplineName, String projectName);

    abstract String showSurveyResults(String displineName, String projectName);

    abstract String representativeResults(String disciplineName,
                                          String projectName);
}


class CreatedSurvey extends SurveyState {

    CreatedSurvey(Survey survey) {
        super(survey);
        setStateName("created");
    }


    @Override
    boolean cancelSurvey() {
        return _survey.noAnswers();
    }


    @Override
    boolean openSurvey() {
        _survey.setState(new OpenSurvey(_survey));
        return true;
    }


    @Override
    boolean closeSurvey() {
        return false;
    }


    @Override
    boolean finalizeSurvey() {
        return false;
    }


    @Override
    boolean fillSurvey(Student student, int hours, String comment) {
        return false;
    }


    @Override
    String studentResults(Student student,
                          String disciplineName, String projectName) {

        return showSurveyResults(disciplineName, projectName);
    }


    @Override
    String showSurveyResults(String disciplineName, String projectName) {
        return disciplineName + " - " + projectName + " " + getStateName();
    }


    @Override
    String representativeResults(String disciplineName, String projectName) {
        return showSurveyResults(disciplineName, projectName);
    }
}


class OpenSurvey extends SurveyState {

    OpenSurvey(Survey survey) {
        super(survey);
        setStateName("open");
    }


    @Override
    boolean cancelSurvey() {
        return _survey.noAnswers();
    }


    @Override
    boolean openSurvey() {
        return true;
    }


    @Override
    boolean closeSurvey() {
        _survey.setState(new ClosedSurvey(_survey));
        return true;
    }


    @Override
    boolean finalizeSurvey() {
        return false;
    }


    @Override
    boolean fillSurvey(Student student, int hours, String comment) {
        if(_survey.canSubmit(student)){
            _survey.addAnswer(student, hours, comment);
            return true;
        }

        return false;
    }


    @Override
    String studentResults(Student student,
                          String disciplineName, String projectName) {

        return showSurveyResults(disciplineName, projectName);
    }


    @Override
    String showSurveyResults(String disciplineName, String projectName) {
        return disciplineName + " - " + projectName + " " + getStateName();
    }


    @Override
    String representativeResults(String disciplineName, String projectName) {
        return showSurveyResults(disciplineName, projectName);
    }
}


class ClosedSurvey extends SurveyState{

    ClosedSurvey(Survey survey) {
        super(survey);
        setStateName("closed");
    }

    @Override
    boolean cancelSurvey() {
        _survey.setState(new OpenSurvey(_survey));
        return true;
    }


    @Override
    boolean openSurvey() {
        _survey.setState(new OpenSurvey(_survey));
        return true;
    }


    @Override
    boolean closeSurvey() {
        return true;
    }


    @Override
    boolean finalizeSurvey() {
        _survey.setState(new FinalizedSurvey(_survey));
        return true;
    }


    @Override
    boolean fillSurvey(Student student, int hours, String comment) {
        return false;
    }


    @Override
    String studentResults(Student student,
                          String disciplineName, String projectName) {

        return showSurveyResults(disciplineName, projectName);
    }


    @Override
    String showSurveyResults(String disciplineName, String projectName) {
        return disciplineName + " - " + projectName + " " + getStateName();
    }


    @Override
    String representativeResults(String disciplineName, String projectName) {
        return showSurveyResults(disciplineName, projectName);
    }
}


class FinalizedSurvey extends SurveyState {

    FinalizedSurvey(Survey survey) {
        super(survey);
        setStateName("finalized");
    }


    @Override
    boolean cancelSurvey() {
        return false;
    }


    @Override
    boolean openSurvey() {
        return false;
    }


    @Override
    boolean closeSurvey() {
        return false;
    }


    @Override
    boolean finalizeSurvey() {
        return true;
    }


    @Override
    boolean fillSurvey(Student student, int hours, String comment) {
        return false;
    }


    //FIXME falta acento no medio
    @Override
    String studentResults(Student student,
                          String disciplineName, String projectName) {

        int hours = _survey.numberHours()[2];
        if(_survey.hasSurveyd(student)){
            return disciplineName + " - " + projectName + "\n"
                    + " * Numero de respostas: " + _survey.numberSubmissions()
                    + "\n"
                    + " * Tempo medio (horas): " + hours;
        }

        return null;
    }


    //FIXME falta acentos
    @Override
    String showSurveyResults(String disciplineName, String projectName) {
        String results;
        int[] hours = _survey.numberHours();
        int minHours = hours[0];
        int maxHours = hours[1];
        int avgHours = hours[2];

        results = disciplineName + " - " + projectName + "\n";
        results += " * Numero de submissoes: " + _survey.numberSubmissions()
                + "\n";

        results += " * Numero de respostas: " + _survey.numberAnswers()
                + "\n";

        results += " * Tempo de resolucao (horas) (minimo, medio, maximo): "
                + minHours + ", " + avgHours + ", " + maxHours;

        return results;
    }


    @Override
    String representativeResults(String disciplineName, String projectName) {
        int avgHours = _survey.numberHours()[2];

        return disciplineName + " - " + projectName + " - "
                + _survey.numberAnswers() + " respostas - " + avgHours;
    }
}