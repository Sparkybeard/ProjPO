package sth.app.person;


import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.Input;
import sth.core.SchoolManager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


//FIXME import other classes if needed

/**
 * 4.2.4. Search person.
 */
public class DoSearchPerson extends Command<SchoolManager> {

    //FIXME add input fields if needed
    private Input<String> _person;
    private List<String> _people = new ArrayList<>();

    /**
     * @param receiver
     */
    public DoSearchPerson(SchoolManager receiver) {
        super(Label.SEARCH_PERSON, receiver);
        _person = _form.addStringInput(Message.requestPersonName());

    }


    /*y* @see pt.tecnico.po.ui.Command#execute() */
    @Override
    public final void execute() {

        _form.parse();

        _people = _receiver.searchPerson(_person.value());

        for (String aux : _people) {
            _display.addLine(aux);
        }
        _display.display();
    }
}
