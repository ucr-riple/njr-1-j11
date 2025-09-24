package manager;

import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.JFrame;
import manager.panel.KitManagerPanelV2;
import Networking.Client;
import Networking.Request;
import Utils.Constants;
import Utils.ReadSaveData;
import factory.KitConfig;
import factory.PartType;

/**
 * This class handles creation, change, and deletion of kits.
 * The GUI portion of this class is contained within KitManagerPanel class.
 * @author Harry Trieu
 *
 */
public class KitManager extends Client {
	// Window dimensions
	private static final int WINDOW_WIDTH = 800;
	private static final int WINDOW_HEIGHT = 600;
	
	private KitManagerPanelV2 kmPanel;
	
	/**
	 * Constructor
	 */
	public KitManager() {
		super();
		clientName = Constants.KIT_MNGR_CLIENT;
		
		initStreams();
		initGUI();
		
		//If there's a read error, comment this out. -- Neetu
		kmPanel.updateKitConfig(ReadSaveData.readKitConfig());
	}

	/**
	 * This function parses requests sent to the KitManager client.
	 */
	@Override
	public void receiveData(Request req) {
		if (req.getTarget().equals(Constants.ALL_TARGET)) {
			if (req.getCommand().equals(Constants.FCS_UPDATE_PARTS)) {
				kmPanel.updatePartTypes((ArrayList<PartType>)req.getData());
			} else if (req.getCommand().equals(Constants.FCS_UPDATE_KITS)) {
				kmPanel.updateKitConfig((ArrayList<KitConfig>)req.getData());
			}
		} else {
			System.out.println("KitManager received a request not addressed to: " + req.getTarget());
			System.out.println("PartsManager cannot parse this request.");
		}		
	}
	
	/**
	 * This function tells FCS to create a new kit.
	 * @param kc KitConfig created by the user.
	 */
	public void addKit(KitConfig kc) {
		this.sendData(new Request(Constants.FCS_NEW_KIT, Constants.FCS_TARGET, kc));
	}
	
	/**
	 * This function tells FCS to edit an existing kit.
	 * @param kc KitConfig selected by the user.
	 */
	public void editKit(KitConfig kc) {
		this.sendData(new Request(Constants.FCS_EDIT_KIT, Constants.FCS_TARGET, kc));
	}
	
	/**
	 * This function tells FCS to delete an existing kit.
	 * @param kc KitConfig selected by the user.
	 */
	public void deleteKit(KitConfig kc) {
		this.sendData(new Request(Constants.FCS_DELETE_KIT, Constants.FCS_TARGET, kc));
	}

	/**
	 * This function creates the GUI panel and adds it to the frame.
	 */
	public void initGUI() {
		kmPanel = new KitManagerPanelV2(this);
		
		add(kmPanel, BorderLayout.CENTER);
		kmPanel.setVisible(true);
	}
	
	/**
	 * The main function sets up the JFrame.
	 * @param args
	 */
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		Client.setUpJFrame(frame, WINDOW_WIDTH, WINDOW_HEIGHT, "Kit Manager");
		
		KitManager km = new KitManager();
		frame.add(km);
		km.setVisible(true);
		frame.validate();
	}
}
