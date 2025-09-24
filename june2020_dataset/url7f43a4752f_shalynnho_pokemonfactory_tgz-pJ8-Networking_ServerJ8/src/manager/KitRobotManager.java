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
import DeviceGraphicsDisplay.ConveyorGraphicsDisplay;
import DeviceGraphicsDisplay.DeviceGraphicsDisplay;
import DeviceGraphicsDisplay.InspectionStandGraphicsDisplay;
import DeviceGraphicsDisplay.KitRobotGraphicsDisplay;
import DeviceGraphicsDisplay.StandGraphicsDisplay;
import Networking.Client;
import Networking.Request;
import Utils.Constants;

@Deprecated
public class KitRobotManager extends Client implements ActionListener {
	// Temp values. Feel free to change
	private static final int WINDOW_WIDTH = 800;
	private static final int WINDOW_HEIGHT = 600;

	private final Timer timer;

	public KitRobotManager() {
		clientName = Constants.KIT_ROBOT_MNGR_CLIENT;

		initStreams();
		initGUI();

		timer = new Timer(Constants.TIMER_DELAY, this);
		timer.start();

		initDevices();
	}

	public void initGUI() {
		JLabel label = new JLabel("Kit Robot Manager");
		label.setForeground(Color.WHITE);
		label.setFont(new Font("SansSerif", Font.PLAIN, 40));
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setVisible(true);
		add(label);

		OverlayPanel panel = new OverlayPanel();
		// panel.add(new JLabel("hello"));
		add(panel, BorderLayout.SOUTH);
		panel.setVisible(true);

		JButton newKit = new JButton("FCS: Make kits");
		newKit.addActionListener(new NetworkingButtonListener(
				Constants.CONVEYOR_MAKE_NEW_KIT_COMMAND,
				Constants.CONVEYOR_TARGET, writer));

		JButton moveKitToLocation1 = new JButton("ToLocation1");
		moveKitToLocation1.addActionListener(new NetworkingButtonListener(
				Constants.KIT_ROBOT_LOGIC_PICKS_CONVEYOR_TO_LOCATION1,
				Constants.KIT_ROBOT_TARGET, writer));
		JButton moveKitToLocation2 = new JButton("ToLocation2");
		moveKitToLocation2.addActionListener(new NetworkingButtonListener(
				Constants.KIT_ROBOT_LOGIC_PICKS_CONVEYOR_TO_LOCATION2,
				Constants.KIT_ROBOT_TARGET, writer));
		JButton moveKitFromLocation1ToInspection = new JButton(
				"Location1ToInspection");
		moveKitFromLocation1ToInspection
				.addActionListener(new NetworkingButtonListener(
						Constants.KIT_ROBOT_LOGIC_PICKS_LOCATION1_TO_INSPECTION,
						Constants.KIT_ROBOT_TARGET, writer));
		JButton moveKitFromLocation2ToInspection = new JButton(
				"Location2ToInspection");
		moveKitFromLocation2ToInspection
				.addActionListener(new NetworkingButtonListener(
						Constants.KIT_ROBOT_LOGIC_PICKS_LOCATION2_TO_INSPECTION,
						Constants.KIT_ROBOT_TARGET, writer));
		JButton moveKitFromInspectionToGoodConveyor = new JButton(
				"InspectionToGoodConveyor");
		moveKitFromInspectionToGoodConveyor
				.addActionListener(new NetworkingButtonListener(
						Constants.KIT_ROBOT_LOGIC_PICKS_INSPECTION_TO_GOOD_CONVEYOR,
						Constants.KIT_ROBOT_TARGET, writer));
		JButton receivePart = new JButton("receivePart");
		receivePart
				.addActionListener(new NetworkingButtonListener(
						Constants.KIT_RECEIVES_PART,
						Constants.KIT_ROBOT_TARGET, writer));
		JButton Kit1Done = new JButton("Kit1Done");
		Kit1Done.addActionListener(new NetworkingButtonListener(
				Constants.KIT_ROBOT_AGENT_RECEIVES_KIT1_DONE,
				Constants.KIT_ROBOT_TARGET, writer));

		JButton Kit2Done = new JButton("Kit2Done");
		Kit2Done.addActionListener(new NetworkingButtonListener(
				Constants.KIT_ROBOT_AGENT_RECEIVES_KIT2_DONE,
				Constants.KIT_ROBOT_TARGET, writer));

		JButton KitInspected = new JButton("KitInspected");
		KitInspected.addActionListener(new NetworkingButtonListener(
				Constants.KIT_ROBOT_AGENT_RECEIVES_KIT_INSPECTED,
				Constants.KIT_ROBOT_TARGET, writer));

		panel.add(newKit);
		panel.add(Kit1Done);
		panel.add(Kit2Done);
		panel.add(KitInspected);
		panel.add(moveKitToLocation1);
		panel.add(receivePart);
		panel.add(moveKitToLocation2);
		panel.add(moveKitFromLocation1ToInspection);
		panel.add(moveKitFromLocation2ToInspection);
		panel.add(moveKitFromInspectionToGoodConveyor);
	}

	public void initDevices() {
		addDevice(Constants.STAND_TARGET + 0,
				new InspectionStandGraphicsDisplay(this));

		for (int i = 1; i < Constants.STAND_COUNT; i++) {
			addDevice(Constants.STAND_TARGET + i, new StandGraphicsDisplay(
					this, i));
		}

		addDevice(Constants.CONVEYOR_TARGET, new ConveyorGraphicsDisplay(this));
		addDevice(Constants.KIT_ROBOT_TARGET, new KitRobotGraphicsDisplay(this));
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
				"Kit Robot Manager");

		KitRobotManager mngr = new KitRobotManager();
		frame.add(mngr);
		mngr.setVisible(true);
		frame.validate();
		frame.setResizable(true);
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
	public void actionPerformed(ActionEvent ae) {
		repaint();
	}

}
