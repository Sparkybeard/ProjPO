package sth.app.main;

import java.io.FileNotFoundException;
import java.io.IOException;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.Input;
import sth.core.SchoolManager;
import sth.core.exception.ImportFileException;
import sth.app.main.Label;

/**
 * 4.1.1. Save to file under current name (if unnamed, query for name).
 */
public class DoSave extends Command<SchoolManager> {

  protected Input<String> _filename;
  private String auxfilename;
  /**
   * @param receiver
   */
  public DoSave(SchoolManager receiver) {
    super(Label.SAVE, receiver);
    _filename = _form.addStringInput(Message.newSaveAs());

  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() {

      if (_receiver.getFileName() != null) {
        auxfilename = _receiver.getFileName();

      } else {
          _form.parse();
      }
      try {
        if (_receiver.getFileName() != null) {
        _receiver.newSaveAs(auxfilename);
        }
        else {
          _receiver.newSaveAs(_filename.value());
        }
        }catch(FileNotFoundException ee){
        } catch (IOException e) {
          e.printStackTrace();
        }
  }
  }

