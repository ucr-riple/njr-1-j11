package manager;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.Timer;

import manager.util.NetworkingButtonListener;
import manager.util.OverlayPanel;
import DeviceGraphicsDisplay.CameraGraphicsDisplay;
import DeviceGraphicsDisplay.DeviceGraphicsDisplay;
import DeviceGraphicsDisplay.KitGraphicsDisplay;
import DeviceGraphicsDisplay.NestGraphicsDisplay;
import DeviceGraphicsDisplay.PartsRobotDisplay;
import Networking.Client;
import Networking.Request;
import Utils.Constants;

@Deprecated
public class PartsRobotManager extends Client implements ActionListener {
	// Temp values. Feel free to change
	private static final int WINDOW_WIDTH = 800;
	private static final int WINDOW_HEIGHT = 600;

	private Timer timer;

	public PartsRobotManager() {
		super();
		clientName = Constants.PARTS_ROBOT_MNGR_CLIENT;

		initStreams();
		initGUI();
		initDevices();
	}

	public void initGUI() {
		JLabel label = new JLabel("Parts Robot Manager");
		label.setForeground(Color.WHITE);
		label.setFont(new Font("SansSerif", Font.PLAIN, 40));
		label.setHorizontalAlignment(JLabel.CENTER);
		add(label);

		OverlayPanel panel = new OverlayPanel();
		add(panel, BorderLayout.SOUTH);
		panel.setVisible(true);

		JButton picture = new JButton("Take Picture");
		picture.addActionListener(new NetworkingButtonListener(
				Constants.CAMERA_TAKE_NEST_PHOTO_COMMAND,
				Constants.CAMERA_TARGET, writer));
		panel.add(picture);

		timer = new Timer(Constants.TIMER_DELAY, this);
		timer.start();
	}

	public void initDevices() {
		// example:
		addDevice(Constants.NEST_TARGET + 0, new NestGraphicsDisplay(this, 0));
		addDevice(Constants.NEST_TARGET + 1, new NestGraphicsDisplay(this, 1));
		addDevice(Constants.CAMERA_TARGET, new CameraGraphicsDisplay(this));
		addDevice(Constants.PARTS_ROBOT_TARGET, new PartsRobotDisplay(this));
		addDevice(Constants.KIT_TARGET, new KitGraphicsDisplay());
	}

	@Override
	public void receiveData(Request req) {
		if (req.getTarget().equals(Constants.ALL_TARGET)) {
			// TODO code to overwrite ArrayList with req.getData()
		} else {
			devices.get(req.getTarget()).receiveData(req);
		}
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		Client.setUpJFrame(frame, WINDOW_WIDTH, WINDOW_HEIGHT,
				"Parts Robot Manager");

		PartsRobotManager mngr = new PartsRobotManager();
		frame.add(mngr);
		mngr.setVisible(true);
		frame.validate();
	}

	@Override
	public void paintComponent(Graphics gg) {
		Graphics2D g = (Graphics2D) gg;
		g.drawImage(Constants.CLIENT_BG_IMAGE, 0, 0, this);

		synchronized (devices) {
			for (DeviceGraphicsDisplay device : devices.values()) {
				device.draw(this, g);
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		repaint();

	}
}
