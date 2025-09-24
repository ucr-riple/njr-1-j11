package spotting;

// Who, what, where, and when.
// Tie in with unit generation 
// In this case, built only for one side.
public class SpotReport {
	
	public int timeSpotted;
	public unit.Unit spotter;
	public unit.Unit target;
	public hex.HexOff targetLoc;
	
	public SpotReport(int t, unit.Unit s, unit.Unit tgt, hex.HexOff loc) {
		timeSpotted = t;
		spotter = s;
		target = tgt;
		targetLoc = loc;
	}
	
	public String displaySPOTREP() {
		return "Time " + timeSpotted + " || " + spotter.callsign + " spotted " + target.callsign + " at " + targetLoc.DisplayHexStr();
	}
	
	public String saveSPOTREP() {
		String output = timeSpotted + ", " + 
						spotter.unitID + ", " +
						target.unitID + ", " +
						targetLoc.getX() + ", " + targetLoc.getY();
		
		return output;
	}
}
