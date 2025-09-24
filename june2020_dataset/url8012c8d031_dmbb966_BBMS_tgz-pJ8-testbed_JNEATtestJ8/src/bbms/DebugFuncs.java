package bbms;

public class DebugFuncs {

	public static double debugRotateHull = 0.0;
	public static double debugRotateTurret = 0.0;
	public static int debugRotateX = 0;
	public static int debugRotateY = 0;
	public static void rotateDebugDisplay() {
		// GUI_NB.GCO("Rotation debug: Angle " + String.format("%.1f", debugRotateHull) + " and " + String.format("%.1f", debugRotateTurret) + " with center at " + debugRotateX + ", " + debugRotateY);
		
		GlobalFuncs.gui.repaint();
	}

}
