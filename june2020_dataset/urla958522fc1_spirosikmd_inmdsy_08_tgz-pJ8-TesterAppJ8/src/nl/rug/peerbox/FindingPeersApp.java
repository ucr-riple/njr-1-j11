package nl.rug.peerbox;

import java.io.IOException;

import nl.rug.peerbox.logic.Context;
import nl.rug.peerbox.logic.Peerbox;
import nl.rug.peerbox.ui.PeerboxUI;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.eclipse.swt.widgets.Display;

public class FindingPeersApp {

	public static final String LOGGER_PROPERTIES_FILE = "logger.properties";

	private static Logger logger = Logger.getLogger(FindingPeersApp.class);

	/**
	 * @param args
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws IOException,
			InterruptedException {
		Thread.currentThread().setName("Main");


		//BasicConfigurator.configure();
		PropertyConfigurator.configure(LOGGER_PROPERTIES_FILE);
		
		Context peerbox = Peerbox.getInstance();
		//peerbox.join();
		//peerbox.requestFiles(true);

		Display.setAppName("Peerbox");
		Display display = new Display();

		PeerboxUI view = new PeerboxUI(display);
		view.getShell().setText("Peerbox");
		
		
		view.getShell().open();
		
		while (!view.getShell().isDisposed()) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }
		display.dispose();
		peerbox.leave();
		System.exit(0);
	}

}
