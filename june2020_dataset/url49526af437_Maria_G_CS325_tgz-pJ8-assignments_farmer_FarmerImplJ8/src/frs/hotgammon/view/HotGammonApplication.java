package frs.hotgammon.view;

import minidraw.framework.*;
import minidraw.standard.ImageManager;
import minidraw.standard.MiniDrawApplication;
import minidraw.standard.NullTool;
import minidraw.standard.StdViewWithBackground;

import javax.swing.*;

import frs.hotgammon.framework.Game;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class HotGammonApplication
  extends MiniDrawApplication {

	private JButton newGameBtn = new JButton("New Game");
	private Game game;

  public HotGammonApplication( String title, Factory f, Game game ) {
    super(title, f);
    
	this.game = game;
	this.setLayout(new BorderLayout());
	            
	      newGameBtn.addMouseListener(new MouseAdapter()
	  		{
	    	  public void mouseClicked(MouseEvent e)
	  		{
	    		 newGameButtonCalled();
	  		}	   
	  		});
	      
	      this.add(newGameBtn, BorderLayout.SOUTH);
	}
	
	private void newGameButtonCalled(){
		this.open();
		this.game.newGame();
	}
  
  @Override
  public void open() {
    Container pane = getContentPane();

    // create the underlying model in the MVC triad 
    fDrawing = factory.createDrawing(this);
    ((HotGammonDrawing) fDrawing).setGame(game);

    //&& AddObserver to game
    game.addObserver((HotGammonDrawing)fDrawing);

    // create a view for the MVC 
    fView = factory.createDrawingView(this);
    statusField = factory.createStatusField(this);

    JPanel panel = createContents(fView, statusField);

    pane.add(panel, BorderLayout.CENTER);
    
    pack();
    setVisible(true);
    
  }
}
