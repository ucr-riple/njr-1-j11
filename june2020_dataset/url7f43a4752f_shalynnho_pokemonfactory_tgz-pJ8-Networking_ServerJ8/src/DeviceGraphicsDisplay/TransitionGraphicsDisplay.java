package DeviceGraphicsDisplay;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JComponent;

import Utils.Constants;
import Utils.Location;
import factory.PartType;

public class TransitionGraphicsDisplay {

	PartType pt;
	Image trans;
	Image pokeball;
	int seq;
	int animCount;
	boolean animate;

	public TransitionGraphicsDisplay(PartType type) {
		seq = 1;
		animCount = 12;
		animate = true;
		// TODO: change 1 to parttypenum later
		pokeball = Toolkit.getDefaultToolkit().getImage(
				Constants.BALL_IMAGE + seq + ".png");
	}

	public void drawTrans(int offset, Location loc, JComponent jc, Graphics2D g) {
		if (animate == true) {
			if (animCount % 3 == 0) {
				// TODO: change 1 to parttypenum later;
				trans = Toolkit.getDefaultToolkit().getImage(
						Constants.TRANS_IMAGE + 1 + seq + ".png");
				sequenceIncrease();
			} else if (animCount <= 0) {
				animate = false;
				animCount = 12;
				return;
			}
			animCount--;
			g.drawImage(trans, loc.getX() + offset, loc.getY(), jc);
		}
	}

	public void drawPokeball(int offset, Location loc, JComponent jc,
			Graphics2D g, Image pokeball) {
		g.drawImage(pokeball, loc.getX() + offset, loc.getY(), jc);
	}

	public void sequenceIncrease() {
		if (seq != 5) {
			seq++;
		} else if (seq == 5) {
			seq = 1;
		}
	}

	public void setAnimate(boolean t) {
		animate = t;
	}

}
