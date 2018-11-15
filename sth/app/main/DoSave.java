package sth.app.main;

import java.io.IOException;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.Input;
import sth.core.SchoolManager;
import sth.core.exception.ImportFileException;
import sth.app.main.Label;

//FIXME import other classes if needed

/**
 * 4.1.1. Save to file under current name (if unnamed, query for name).
 */
public class DoSave extends Command<SchoolManager> {
  protected Input<String> _filename;

  //FIXME add input fields if needed
  /**
   * @param receiver
   */
  public DoSave(SchoolManager receiver) {
    super(Label.SAVE, receiver);
    _filename = _form.addStringInput("Introduza o nome do ficheiro: ");
    //FIXME initialize input fields if needed
  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() {
      //FIXME implement command
      _form.parse();
      try {
          _receiver.saveSerializable(_filename.value());
          _display.addLine("Ficheiro guardado: ");

      } catch (ImportFileException e) {
          _display.addLine("Impossível importar ficheiro.");
      }

  }

}
