package sth.app.person;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.Input;
import sth.core.SchoolManager;

//FIXME import other classes if needed

/**
 * 4.2.4. Search person.
 */
public class DoSearchPerson extends Command<SchoolManager> {

  //FIXME add input fields if needed
	private Input<String> _person;  
  /**
   * @param receiver
   */
  public DoSearchPerson(SchoolManager receiver) {
    super(Label.SEARCH_PERSON, receiver);
	_person = _form.addStringInput("Introduza o nome da pessoa a procurar: ");
    //FIXME initialize input fields if needed
  }


  /*y* @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() {
    //FIXME implement command
    _form.parse();
   try {   
	_display.addLine(_receiver.ShowPerson(_person.value())); 
   } catch (NoSuchPersonException e) {
	e.getMessage();
   }

