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
//FIXME import other classes if needed

/**
 * 4.1.1. Open existing document.
 */
public class DoOpen extends Command<SchoolManager> {

  //FIXME add input fields if needed
  private Input<String> _filename;
  private int _id;
  
  /**
   * @param receiver
   */
  public DoOpen(SchoolManager receiver) {
    super(Label.OPEN, receiver);
    //FIXME initialize input fields if needed
    _filename = _form.addStringInput("Por favor indique o nome do ficheiro a abrir: ");
  }


  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() throws DialogException {
    try {
      _form.parse();
      _id = _receiver.getLoggedUser();
      _receiver.importFile(_filename.value());
      //FIXME implement command
    } catch (FileNotFoundException f) {
      _display.popup(Message.fileNotFound());
    } catch (ClassNotFoundException | IOException e) {
      e.printStackTrace();
    } catch (ImportFileException p) {
      _display.addLine("No such file.");
    } catch (NoSuchPersonIdException e) {
      throw new NoSuchPersonException(e.getId());
    }
  }

}
