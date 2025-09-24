package DeviceGraphicsDisplay;

import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.JComponent;

import Networking.Client;
import Networking.Request;
import Utils.Constants;
import Utils.Location;

public class MessagingBoxGraphicsDisplay extends DeviceGraphicsDisplay {
	private static final int LINE_LENGTH = 53;
	private static final Image image = Constants.MESSAGE_BOX_IMAGE.getScaledInstance(480, 80, Image.SCALE_DEFAULT);
	private static final Image arrowImage = Constants.MESSAGE_BOX_ARROW_IMAGE.getScaledInstance(12, 8,
			Image.SCALE_DEFAULT);

	private String msgToDisplay = "";
	private int charsDisplayed = 0;

	private int flashCounter = 0;

	public MessagingBoxGraphicsDisplay(Client c) {
		client = c;
		location = new Location(30, 580);
		msgToDisplay = "Professor Oak: Welcome to Neetu's Pokemon Factory!";
	}

	@Override
	public void draw(JComponent c, Graphics2D g) {
		g.setFont(Client.pokeFont);
		g.drawImage(image, location.getX() + client.getOffset(), location.getY(), c);

		if (charsDisplayed < Math.min(LINE_LENGTH * 2, msgToDisplay.length())) {
			drawMessage(msgToDisplay.substring(0, charsDisplayed), c, g);
			charsDisplayed++;
		} else {
			drawMessage(msgToDisplay, c, g);
			if (flashCounter % 30 < 15) {
				g.drawImage(arrowImage, location.getX() + 445, location.getY() + 60, c);
			}
		}

		flashCounter++;
	}

	@Override
	public void receiveData(Request req) {
		if (req.getCommand().equals(Constants.MSGBOX_DISPLAY_MSG)) {
			updateDisplayMessage((String) req.getData());

		}
	}

	public void updateDisplayMessage(String message) {
		client.startMessageTone();
		msgToDisplay = message;
		charsDisplayed = 0;
	}

	public void drawMessage(String s, JComponent c, Graphics2D g) {
		s = s.trim();
		if (s.length() > LINE_LENGTH) {
			g.drawString(s.substring(0, LINE_LENGTH), location.getX() + 23, location.getY() + 35);
			s = s.substring(LINE_LENGTH, Math.min(s.length(), LINE_LENGTH * 2));
			s = s.trim();
			g.drawString(s, location.getX() + 23, location.getY() + 57);
		} else {
			g.drawString(s, location.getX() + 23, location.getY() + 35);
		}
	}

}
