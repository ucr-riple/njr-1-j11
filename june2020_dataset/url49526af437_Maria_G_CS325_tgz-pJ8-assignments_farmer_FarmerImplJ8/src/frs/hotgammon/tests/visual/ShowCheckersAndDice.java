package frs.hotgammon.tests.visual;

import minidraw.standard.*;
import minidraw.framework.*;

import java.awt.*;
import javax.swing.*;

/** Show the dice and some checkers on the
 * backgammon board.  
 * 
   This source code is from the book 
     "Flexible, Reliable Software:
       Using Patterns and Agile Development"
     published 2010 by CRC Press.
   Author: 
     Henrik B Christensen 
     Computer Science Department
     Aarhus University
   
   This source code is provided WITHOUT ANY WARRANTY either 
   expressed or implied. You may study, use, modify, and 
   distribute it for non-commercial purposes. For any 
   commercial use, see http://www.baerbak.com/
 */
public class ShowCheckersAndDice {
  
  public static void main(String[] args) {
    DrawingEditor editor = 
      new MiniDrawApplication( "Show HotGammon figures...",  
                               new FirstHotGammonFactory() );
    editor.open();

    Figure redDie = new ImageFigure("die4", new Point(216, 202));
    Figure blackDie = new ImageFigure("die2", new Point(306, 202));
    editor.drawing().add(redDie);
    editor.drawing().add(blackDie);
    
    Figure bc = new ImageFigure("blackchecker", new Point(21,21));
    editor.drawing().add(bc);
    Figure rc = new ImageFigure("redchecker", new Point(507,390));
    editor.drawing().add(rc);

    editor.setTool( new SelectionTool(editor) );

  }
}

class FirstHotGammonFactory implements Factory {
  public DrawingView createDrawingView( DrawingEditor editor ) {
    DrawingView view = 
      new StdViewWithBackground(editor, "board");
    return view;
  }

  public Drawing createDrawing( DrawingEditor editor ) {
    return new StandardDrawing();
  }

  public JTextField createStatusField( DrawingEditor editor ) {
    JTextField statusField = new JTextField( "Hello HotGammon..." );
    statusField.setEditable(false);
    return statusField;
  }
}


