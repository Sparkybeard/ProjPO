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

    _filename = _form.addStringInput("Por favor indique o nome do ficheiro a abrir: ");
  }


  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() throws DialogException {
    try {
      _form.parse();
      _id = _receiver.getLoggedUser();
      _receiver.importFile(_filename.value());
      //falta implementar condicao caso de bosta
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
