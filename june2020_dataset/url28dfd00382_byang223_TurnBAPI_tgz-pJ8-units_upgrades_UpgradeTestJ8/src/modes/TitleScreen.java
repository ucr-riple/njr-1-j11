package modes;


import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Random;

import javax.swing.JFileChooser;

import com.golden.gamedev.GameEngine;
import com.golden.gamedev.GameObject;
import com.golden.gamedev.object.Background;
import com.golden.gamedev.object.GameFont;
import com.golden.gamedev.object.background.ImageBackground;

/**
 * Basic Title Screen
 * @author 
 *
 */

public class TitleScreen extends GameObject {

	private GameFont		font;
	private BufferedImage	arrow;
	private int				option;
	private Background		mainMenuTitle;
	private HashMap<Integer, Background> titlemap;
	private double randomNum;

	public TitleScreen(GameEngine parent) {
		super(parent);
	}

	public void initResources() {
		//Basic icons, we can change it up but this is just meant to be filler
		mainMenuTitle = new ImageBackground( getImage( "resources/titlescreen/title8.png" ), 700, 500 );
		Background mainMenuTitle1 = new ImageBackground( getImage( "resources/titlescreen/title1.png" ), 700, 500 );
		Background mainMenuTitle2 = new ImageBackground( getImage( "resources/titlescreen/title2.png" ), 700, 500 );
		Background mainMenuTitle3 = new ImageBackground( getImage( "resources/titlescreen/title3.png" ), 700, 500 );
		Background mainMenuTitle4 = new ImageBackground( getImage( "resources/titlescreen/title4.png" ), 700, 500 );
		Background mainMenuTitle5 = new ImageBackground( getImage( "resources/titlescreen/title5.png" ), 700, 500 );
		Background mainMenuTitle6 = new ImageBackground( getImage( "resources/titlescreen/title6.png" ), 700, 500 );
		Background mainMenuTitle7 = new ImageBackground( getImage( "resources/titlescreen/title7.png" ), 700, 500 );
		Background mainMenuTitle8 = new ImageBackground( getImage( "resources/titlescreen/title8.png" ), 700, 500 );
		Background mainMenuTitle9 = new ImageBackground( getImage( "resources/titlescreen/title9.png" ), 700, 500 );
		Background mainMenuTitle10 = new ImageBackground( getImage( "resources/titlescreen/title10.png" ), 700, 500 );

		titlemap = new HashMap<Integer, Background>();
		titlemap.put(1, mainMenuTitle1);
		titlemap.put(2, mainMenuTitle2);
		titlemap.put(3, mainMenuTitle3);
		titlemap.put(4, mainMenuTitle4);
		titlemap.put(5, mainMenuTitle5);
		titlemap.put(6, mainMenuTitle6);
		titlemap.put(7, mainMenuTitle7);
		titlemap.put(8, mainMenuTitle8);
		titlemap.put(9, mainMenuTitle9);
		titlemap.put(10, mainMenuTitle10);


		arrow = getImage("resources/titlescreen/MenuArrow.png");
		font = fontManager.getFont(getImage("resources/titlescreen/BitmapFont.png"));
		bsInput.setMouseVisible(true);
	}

	public void update(long elapsedTime) {

		switch (bsInput.getKeyPressed()) {
			case KeyEvent.VK_ENTER :
				if (option == 0) {
					parent.nextGameID = TBGame.GAME_MODE;
					finish();
				}
				if (option == 1) {
					parent.nextGameID = TBGame.LOAD_GAME_MODE;
					finish();
				}
				if (option == 2) {
					parent.nextGameID = TBGame.EDIT_MODE;
					finish();
				}
				
				if (option == 3) {
					finish();
				}
				
				if (option == 4) {
					randomNum = Math.ceil(Math.random()*10);
					mainMenuTitle = titlemap.get((int) randomNum);
				}
			break;

			case KeyEvent.VK_UP :
				option--;
				if (option <= 0) option = 0;
			break;

			case KeyEvent.VK_DOWN :
				option++;
				if (option >= 4) option = 4;
			break;

			case KeyEvent.VK_ESCAPE :
				finish();
			break;
		} 
	}

	public void render(Graphics2D g) {

		mainMenuTitle.render(g);

		font.drawString(g, "RANDOM", 440, 340);
		font.drawString(g, "START GAME", 440, 260);
		font.drawString(g, "LOAD GAME", 440, 280);
		font.drawString(g, "LEVEL EDITOR", 440, 300);
		font.drawString(g, "EXIT", 440, 320);
		
		g.drawImage(arrow, 410, 260+(option*20), null);
	}
}