package assignments.frs.chap30;
import minidraw.standard.*;
import minidraw.framework.*;

import java.awt.*;
import javax.swing.*;

public class SnapToGridLogoPuzzle {
  
  public static void main(String[] args) {
	  
	final int GRID_ROWS = 3;
	final int GRID_COLUMNS = 3;
	
    DrawingEditor editor = 
      new MiniDrawApplication( "Put the pieces into place - Snap to Grid",
                               new PuzzleFactory() );
    editor.open();
    editor.setTool( new SnapToGridSelectionTool(editor, GRID_ROWS, GRID_COLUMNS) );

    Drawing drawing = editor.drawing();
    drawing.add(  new ImageFigure( "11", new Point(5, 5)) );
    drawing.add(  new ImageFigure( "12", new Point(10, 10)) );
    drawing.add(  new ImageFigure( "13", new Point(15, 15)) );
    drawing.add(  new ImageFigure( "21", new Point(20, 20)) );
    drawing.add(  new ImageFigure( "22", new Point(25, 25)) );
    drawing.add(  new ImageFigure( "23", new Point(30, 30)) );
    drawing.add(  new ImageFigure( "31", new Point(35, 35)) );
    drawing.add(  new ImageFigure( "32", new Point(40, 40)) );
    drawing.add(  new ImageFigure( "33", new Point(45, 45)) );
  }
}

class PuzzleFactory implements Factory {
	
  public DrawingView createDrawingView( DrawingEditor editor ) {
    DrawingView view = 
      new StdViewWithBackground(editor,  "au-seal-large");
    return view;
  }

  public Drawing createDrawing( DrawingEditor editor ) {
    return new StandardDrawing();
  }

  public JTextField createStatusField( DrawingEditor editor ) {
    return null;
  }
  
  
}
