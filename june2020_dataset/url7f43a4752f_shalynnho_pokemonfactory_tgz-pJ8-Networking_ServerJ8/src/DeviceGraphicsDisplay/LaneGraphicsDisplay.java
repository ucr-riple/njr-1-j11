package DeviceGraphicsDisplay;

import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JComponent;

import Networking.Client;
import Networking.Request;
import Utils.Constants;
import Utils.Location;
import Utils.PartData;
import factory.PartType;

/**
 * This class contains the graphics display components for a lane.
 * 
 * @author Shalynn Ho
 * 
 */
public class LaneGraphicsDisplay extends DeviceGraphicsDisplay {
	// max number of parts that can be on a Lane
	private static final int MAX_PARTS = Constants.LANE_LENGTH
			/ (Constants.PART_WIDTH + Constants.PART_PADDING / 2);
	// number of lines on the lane
	private static final int NUMLINES = 1 + Constants.LANE_LENGTH
			/ (4 * Constants.PART_WIDTH);
	// space between each line
	private static final int LINESPACE = Constants.LANE_LENGTH / NUMLINES;
	// width of lane lines
	private static final int LINE_WIDTH = 3;

	// stores the parts on the lane
	private final ArrayList<PartGraphicsDisplay> partsOnLane;

	// start location of parts on this lane
	private final Location partStartLoc;

	// location of a part jam on the lane
	private Location jamLoc;
	private int jamLocX = 0;

	// array list of locations of the lane lines
	private ArrayList<Location> laneLines;

	// the ID of this Lane
	private final int laneID;

	// the amplitude of this lane
	private int speed = 2;
	private int amplitude = 2;

	// true if Lane is on
	private boolean laneOn = true;

	// use to make sure only 1 message is sent to agent for each part that
	// reaches end of lane
	private boolean receivePartDoneSent = false;
	private boolean purgeDoneSent = false;

	// state of the parts on lane
	private boolean partAtLaneEnd = false;
	private boolean purging = false;
	private boolean jammed = false;
	private boolean unjamming = false;
	private int jamSeq = 0;

	/**
	 * LGD constructor
	 * 
	 * @param lm
	 *            - the lane manager (client)
	 * @param lid
	 *            - lane ID
	 */
	public LaneGraphicsDisplay(Client c, int lid) {
		client = c;
		laneID = lid;

		partsOnLane = new ArrayList<PartGraphicsDisplay>();
		// generate lane start location
		location = new Location(Constants.LANE_END_X, 53 + laneID * 75);

		jamLoc = new Location(Constants.LANE_END_X + Constants.LANE_LENGTH / 2,
				location.getY());

		// for reference only
		partStartLoc = new Location(Constants.LANE_BEG_X, location.getY()
				+ Constants.PART_WIDTH / 2 - Constants.PART_OFFSET);

		resetLaneLineLocs();
	}

	/**
	 * Animates lane movement and sets location of parts moving down lane
	 * 
	 * @param c
	 *            - component on which this is drawn
	 * @param g
	 *            - the graphics component on which this draws
	 */
	@Override
	public void draw(JComponent c, Graphics2D g) {
		if (laneOn) {
			if (laneID % 2 == 0) {
				g.drawImage(Constants.LANE_IMAGE1,
						location.getX() + client.getOffset(), location.getY(),
						c);
			} else {
				g.drawImage(Constants.LANE_IMAGE2,
						location.getX() + client.getOffset(), location.getY(),
						c);
			}

			// animate lane movements using lines
			for (int i = 0; i < laneLines.size(); i++) {
				g.drawImage(Constants.LANE_LINE, laneLines.get(i).getX()
						+ client.getOffset(), laneLines.get(i).getY(), c);
			}
			moveLane();

			// animate parts moving down lane
			if (partsOnLane.size() > 0) {

				int min = Math.min(MAX_PARTS, partsOnLane.size());
				for (int i = 0; i < min; i++) {

					if (i >= partsOnLane.size()) {
						continue;
					}
					PartGraphicsDisplay pgd = partsOnLane.get(i);
					Location loc = pgd.getLocation();

					if (i == 0) { // first part on the lane

						// first part not at lane end
						if (loc.getX() > Constants.LANE_END_X
								- Constants.PART_PADDING) {
							if (!jammed) { // carry on as usual
								updateXLoc(loc, Constants.LANE_END_X
										- Constants.PART_PADDING, speed);

							} else { // jammed
								// first part already passed jam loc
								if (loc.getX() < jamLocX
										- Constants.PART_PADDING) {
									updateXLoc(loc, Constants.LANE_END_X
											- Constants.PART_PADDING, speed);

									// first part is before jamLoc
								} else if (!unjamming
										&& loc.getX() > jamLocX
												- Constants.PART_PADDING) {
									updateXLoc(loc, jamLocX
											- Constants.PART_PADDING, speed);
								} else if (unjamming) {
									g.drawImage(Constants.POKEFLUTE, jamLocX
											- 65 + client.getOffset(),
											location.getY()
													- Constants.PART_PADDING
													+ 10, c);
									animateUnjam(loc);
								}
							}

							partAtLaneEnd = false;

						} else { // first part at end of lane

							if (!purging) {
								partAtLaneEnd = true;
								msgAgentReceivePartDone();

							} else { // purging, continue till off lane

								if (loc.getX() > Constants.LANE_END_X
										- Constants.PART_WIDTH
										- Constants.PART_PADDING) {
									updateXLoc(loc, Constants.LANE_END_X
											- Constants.PART_WIDTH
											- Constants.PART_PADDING, speed);
								} else { // once off lane and not visible,
											// remove
									if (partsOnLane.size() > 0) {
										partsOnLane.remove(0);
										break;
									} else { // all parts removed, done purging
										purging = false;
										msgAgentPurgingDone();
									}
								}
							}
						}

					} else { // all other parts on lane (not first)

						// part in front of i
						PartGraphicsDisplay pgdInFront = partsOnLane.get(i - 1);
						Location locInFront = pgdInFront.getLocation();

						// makes sure parts are spaced out as they appear on
						// lane, but don't overlap part in front
						if (locInFront.getX() <= Constants.LANE_BEG_X - 2
								* Constants.PART_WIDTH - Constants.PART_PADDING
								&& loc.getX() > locInFront.getX()
										+ Constants.PART_WIDTH
										+ Constants.PART_PADDING / 2) {

							if (!jammed) { // carry on as usual
								updateXLoc(loc, Constants.LANE_END_X
										- Constants.PART_PADDING, speed);

							} else { // jammed

								// already passed jam loc
								if (loc.getX() < jamLocX
										- Constants.PART_PADDING) {
									updateXLoc(loc, Constants.LANE_END_X
											- Constants.PART_PADDING, speed);

									// part is before jamLoc
								} else if (loc.getX() > jamLocX
										- Constants.PART_PADDING) {
									updateXLoc(loc, jamLocX
											- Constants.PART_PADDING, speed);
								}
							}

							partAtLaneEnd = false;

						}
					}
					vibrateParts(loc);
					pgd.setLocation(loc);
					pgd.drawWithOffset(c, g, client.getOffset());

				} // end for loop

			} else if (purging) {
				purging = false;
				msgAgentPurgingDone();
			}
		} else { // lane is off
			if (laneID % 2 == 0) {
				g.drawImage(Constants.LANE_IMAGE1,
						location.getX() + client.getOffset(), location.getY(),
						c);
			} else {
				g.drawImage(Constants.LANE_IMAGE2,
						location.getX() + client.getOffset(), location.getY(),
						c);
			}

			// draw lane lines
			for (int i = 0; i < laneLines.size(); i++) {
				g.drawImage(Constants.LANE_LINE, laneLines.get(i).getX()
						+ client.getOffset(), laneLines.get(i).getY(), c);
			}

			// draw the current parts on the lane
			for (PartGraphicsDisplay pgd : partsOnLane) {
				pgd.drawWithOffset(c, g, client.getOffset());
			}
		}

		if (jammed) { // draw snorlax
			g.drawImage(Constants.SNORLAX, jamLocX - Constants.PART_PADDING
					- 48 + client.getOffset(), location.getY()
					- Constants.PART_PADDING, c);
		}
	}

	/**
	 * Receives and sorts messages/data from the server
	 * 
	 * @param r
	 *            - the request to be parsed
	 */
	@Override
	public void receiveData(Request r) {
		String cmd = r.getCommand();
		// parse data request here

		if (cmd.equals(Constants.LANE_PURGE_COMMAND)) {
			purge();

		}

		else if (cmd.equals(Constants.LANE_SET_AMPLITUDE_COMMAND)) {
			amplitude = (Integer) r.getData();
			speed = (int) 1.5 * amplitude;
		}

		else if (cmd.equals(Constants.LANE_TOGGLE_COMMAND)) {
			laneOn = (Boolean) r.getData();

		} else if (cmd.equals(Constants.LANE_SET_STARTLOC_COMMAND)) {
			location = (Location) r.getData();

		} else if (cmd.equals(Constants.LANE_RECEIVE_PART_COMMAND)) {
			PartData pd = (PartData) r.getData();
			receivePart(pd.getPartType(), pd.getQuality());

		} else if (cmd.equals(Constants.LANE_GIVE_PART_TO_NEST)) {
			givePartToNest();

		} else if (cmd.equals(Constants.LANE_SET_JAM_COMMAND)) {
			unjamming = false;
			jammed = true;
			jamLoc = (Location) r.getData();
			jamLocX = location.getX() + jamLoc.getX();
			// System.out.println("	LANEGD" + laneID + " JAM LOC SET");

		} else if (cmd.equals(Constants.LANE_UNJAM_COMMAND)) {
			unjamming = true;
			if (jammed) {
				client.startPokeflute();
			} else {
//				client.sendData(new Request(Constants.MSGBOX_DISPLAY_MSG,
//						Constants.LANE_TARGET + laneID, 
//						"Professor Oak: The lane is working again!"));
			}
			// System.out.println("	LANEGD" + laneID +
			// "RECEIVED UNJAM COMMAND");

		} else {
			System.out.println("LANE_GD: command not recognized.");
		}
	}

	/**
	 * Set location of this lane
	 */
	@Override
	public void setLocation(Location newLocation) {
		location = newLocation;
	}

	private void animateUnjam(Location loc) {
		// System.out.println("	LANEGD" + laneID + "ANIMATING UNJAM, SEQ: "+
		// jamSeq);

		// TODO
		// 8-step sequence
		if (jamSeq % 2 == 0) { // even
			loc.setY(partStartLoc.getY());
		} else { // odd
			if ((jamSeq - 1) % 4 == 0) { // 1 or 5
				loc.incrementY(-6);
			} else { // 3 or 7
				loc.incrementY(6);
			}
		}

		if (jamSeq == 264) { // reset sequence
			jamSeq = 0;
			unjamming = false;
			jammed = false;
			client.sendData(new Request(Constants.LANE_SET_JAM_COMMAND
					+ Constants.DONE_SUFFIX, Constants.LANE_TARGET + laneID,
					null));
		} else {
			jamSeq++;
		}
	}

	/**
	 * Give part to nest, removes from this lane
	 */
	private void givePartToNest() {
		partsOnLane.remove(0);
		receivePartDoneSent = false; // reset
		client.sendData(new Request(Constants.LANE_GIVE_PART_TO_NEST
				+ Constants.DONE_SUFFIX, Constants.LANE_TARGET + laneID, null));
	}

	/**
	 * Animates the lane lines
	 */
	private void moveLane() {
		for (int i = 0; i < laneLines.size(); i++) {
			int xCurrent = laneLines.get(i).getX();
			if (xCurrent <= Constants.LANE_END_X - LINE_WIDTH) {
				if (i == 0) {
					int xPrev = laneLines.get(laneLines.size() - 1).getX();
					laneLines.get(i).setX(xPrev + LINESPACE);
				} else {
					int xPrev = laneLines.get(i - 1).getX();
					laneLines.get(i).setX(xPrev + LINESPACE);
				}
			} else {
				laneLines.get(i).incrementX(-speed);
			}
		}
	}

	/**
	 * Tells the agent that purging is done. Make sure only sends message once
	 * for each part, not on every call to draw.
	 */
	private void msgAgentPurgingDone() {
		if (partsOnLane.size() == 0 && !purgeDoneSent) {
			client.sendData(new Request(Constants.LANE_PURGE_COMMAND
					+ Constants.DONE_SUFFIX, Constants.LANE_TARGET + laneID,
					null));
			// System.out.println("	LANEGD" + laneID + ": purge done sent.");
			purgeDoneSent = true;
			receivePartDoneSent = false;
		}
	}

	/**
	 * Tells the agent that the part has reached the end of the lane. Make sure
	 * only sends message once for each part, not on every call to draw.
	 */
	private void msgAgentReceivePartDone() {
		if (partAtLaneEnd && !receivePartDoneSent) {
			client.sendData(new Request(Constants.LANE_RECEIVE_PART_COMMAND
					+ Constants.DONE_SUFFIX, Constants.LANE_TARGET + laneID,
					null));
			// System.out.println("	LANEGD" + laneID +
			// ": receive part done sent.");
			receivePartDoneSent = true;
		}
	}

	/**
	 * Purges lane of all parts
	 */
	private void purge() {
		// lane should continue as is, parts fall off the lane
		purgeDoneSent = false;
		purging = true;
		if (jammed) {
			unjamming = true;
			client.startPokeflute();
		}
	}

	/**
	 * Creates an array list of Locations for the lane lines
	 */
	private void resetLaneLineLocs() {
		// create array list of location for lane lines
		laneLines = new ArrayList<Location>(NUMLINES);
		int startLineX = Constants.LANE_END_X + LINE_WIDTH;
		for (int i = 0; i < NUMLINES; i++) {
			laneLines.add(new Location(startLineX, location.getY()));
			startLineX += LINESPACE;
		}
	}

	private void receivePart(PartType type, boolean quality) {
		PartGraphicsDisplay pg = new PartGraphicsDisplay(type);
		Location newLoc = new Location(location.getX() + Constants.LANE_LENGTH,
				location.getY() + Constants.PART_WIDTH / 2
						- Constants.PART_OFFSET);
		pg.setLocation(newLoc);
		pg.setQuality(quality);
		partsOnLane.add(pg);
		// System.out.println("LANEGD" + laneID + " RECEIVING PART " +
		// partsOnLane.size());
	}

	/**
	 * Increments the X-coordinate
	 * 
	 * @param loc
	 *            - the location being incremented
	 * @param end
	 *            - the end location toward which loc is being incremented
	 * @param increment
	 *            - a POSITIVE value representing number of pixels moved each
	 *            call to draw
	 */
	private void updateXLoc(Location loc, int end, int increment) {
		// System.out.println("loc.getX(): "+loc.getX()+", end: "+end+", abs(dif): "+(Math.abs(end
		// - loc.getX()) <
		// increment));

		if (Math.abs(end - loc.getX()) < increment) {
			loc.setX(end);
		}

		if (loc.getX() > end) { // moving left
			loc.incrementX(-increment);
		}

	}

	/**
	 * Changes y-coords to show vibration down lane (may have to adjust values)
	 * 
	 * @param i
	 *            - counter, increments every call to draw
	 * @param loc
	 *            - location of the current part
	 */
	private void vibrateParts(Location loc) {

		// to show vibration down lane (may have to adjust values)
		if (loc.getY() <= partStartLoc.getY() + amplitude) {
			loc.incrementY(2);

		} else if (loc.getY() > partStartLoc.getY() - amplitude) {
			loc.incrementY(-2);
		}
	}

}
