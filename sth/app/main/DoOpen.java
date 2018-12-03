package sth.app.main;

import java.io.FileNotFoundException;
import java.io.IOException;
import sth.app.main.Message;
import sth.app.exception.NoSuchPersonException;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import sth.core.SchoolManager;
import sth.core.exception.ImportFileException;
import sth.core.exception.NoSuchPersonIdException;


/**
 * 4.1.1. Open existing document.
 */
public class DoOpen extends Command<SchoolManager> {


  private Input<String> _filename;
  private int _id;
  
  /**
   * @param receiver
   */
  public DoOpen(SchoolManager receiver) {
    super(Label.OPEN, receiver);

    _filename = _form.addStringInput(Message.openFile());
  }


  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() throws DialogException {
    try {
      _form.parse();


      _receiver.doOpen(_filename.value());
      _id = _receiver.getLoggedUser();


    } catch (FileNotFoundException f) {
      _display.popup(Message.fileNotFound());
    } catch (ClassNotFoundException | IOException e) {
      e.printStackTrace();
    } catch (NoSuchPersonIdException e) {
      throw new NoSuchPersonException(_id);
    }
  }

}
