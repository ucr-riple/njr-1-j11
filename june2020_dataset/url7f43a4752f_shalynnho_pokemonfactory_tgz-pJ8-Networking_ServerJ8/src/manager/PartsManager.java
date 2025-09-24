package manager;
import java.util.ArrayList;

import javax.swing.JFrame;

import manager.panel.PartsManagerPanelV2;
import Networking.Client;
import Networking.Request;
import Utils.Constants;
import Utils.ReadSaveData;
import factory.PartType;

/**
 * This class handles creation, change, and deletion of parts.
 * The GUI portion of this class is contained within the PartsManagerGUI class.
 * @author Harry Trieu
 *
 */
public class PartsManager extends Client {
	// Window dimensions
	private static final int WINDOW_WIDTH = 800;
	private static final int WINDOW_HEIGHT = 600;

	private PartsManagerPanelV2 pmPanel;

	/**
	 * Constructor
	 */
	public PartsManager() {
		super();
		clientName = Constants.PARTS_MNGR_CLIENT;
		
		initStreams();
		initGUI();
		
		//if there's a read error, comment this out --Neetu
		pmPanel.updatePartTypes(ReadSaveData.readPartType());
	}
	
	/**
	 * This function processes network requests.
	 * @param req the request to be processed
	 */
	@Override
	public void receiveData(Request req) {
		if (req.getTarget().equals(Constants.ALL_TARGET)) {
			if (req.getCommand().equals(Constants.FCS_UPDATE_PARTS)) {
				pmPanel.updatePartTypes((ArrayList<PartType>)req.getData());
			}
		} else {
			System.out.println("PartsManager received a request addressed to: " + req.getTarget());
			System.out.println("PartsManager cannot parse this request.");
		}
	}
	
	/**
	 * This function tells FCS to create new part.
	 * @param pt PartType selected by the user.
	 */
	public void createPart(PartType pt) {
		this.sendData(new Request(Constants.FCS_NEW_PART, Constants.FCS_TARGET, pt));
	}
	
	/**
	 * This function tells FCS to edit an existing part.
	 * @param pt PartType selected by the user.
	 */
	public void editPart(PartType pt) {
		this.sendData(new Request(Constants.FCS_EDIT_PART, Constants.FCS_TARGET, pt));
	}
	
	/**
	 * This function tells FCS to delete an existing part.
	 * @param pt PartType selected by the user.
	 */
	public void deletePart(PartType pt) {
		System.out.println("[Delete pt] : name = " + pt.getName());
		this.sendData(new Request(Constants.FCS_DELETE_PART, Constants.FCS_TARGET, pt));
	}
	
	/**
	 * This function initializes the GUI panel.
	 */
	public void initGUI() {
		pmPanel = new PartsManagerPanelV2(this);
		
		add(pmPanel);
		pmPanel.setVisible(true);
	}
	
	/**
	 * This main initializes the JFrame.
	 * @param args
	 */
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		Client.setUpJFrame(frame, WINDOW_WIDTH, WINDOW_HEIGHT, "Parts Manager");
		
		PartsManager pm = new PartsManager();
		frame.add(pm);
		pm.setVisible(true);
		frame.validate();
	}
}
