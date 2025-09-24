package DeviceGraphicsDisplay;

import java.awt.Graphics2D;

import javax.swing.JComponent;

import Networking.Client;
import Networking.Request;
import Utils.Constants;

/**
 * Graphics display side of the inspection stand.
 * @author Shalynn Ho
 *
 */
public class InspectionStandGraphicsDisplay extends StandGraphicsDisplay {

	private int cameraTimer = -1;
		
	public InspectionStandGraphicsDisplay(Client kam) {
		super(kam, 0);
	}
	
	@Override
	public void draw(JComponent c, Graphics2D g) {
		if (cameraTimer >= 0) {
			if (cameraTimer >= 80 || (cameraTimer < 60 && cameraTimer >= 40) || (cameraTimer < 20)) {
				g.drawImage(Constants.ORANGE_STAND_IMAGE, location.getX() + client.getOffset(), location.getY(), c);
			} else {
				g.drawImage(Constants.STAND_IMAGE, location.getX() + client.getOffset(), location.getY(), c);
			}
			cameraTimer--;
		} else {
			g.drawImage(Constants.STAND_IMAGE, location.getX() + client.getOffset(), location.getY(), c);
		}
		if (!isEmpty) {
			kit.drawKit(c,g);
		}	
	}
	
	public void receiveData(Request r) {
		super.receiveData(r);
		if (r.getCommand().equals(Constants.CAMERA_TAKE_KIT_PHOTO_COMMAND)) {
			cameraTimer = 100;
//			client.stopMusic();
//			client.stopPokeflute();
			client.startRecovery();
		}
	}


}
