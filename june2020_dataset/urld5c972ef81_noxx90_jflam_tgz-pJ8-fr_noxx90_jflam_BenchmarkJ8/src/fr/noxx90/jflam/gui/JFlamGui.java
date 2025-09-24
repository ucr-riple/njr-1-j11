package fr.noxx90.jflam.gui;

import org.apache.pivot.beans.BXMLSerializer;
import org.apache.pivot.collections.Map;
import org.apache.pivot.wtk.Application;
import org.apache.pivot.wtk.DesktopApplicationContext;
import org.apache.pivot.wtk.Display;
import org.apache.pivot.wtk.Window;

public class JFlamGui implements Application
{
	private Window window = null;
	
	public void resume() throws Exception {
		
	}

	public boolean shutdown(boolean optional) throws Exception {
		if (window != null) {
            window.close();
        }
 
        return false;
	}

	public void startup(Display display, Map<String, String> properties) throws Exception {
		BXMLSerializer bxmlSerializer = new BXMLSerializer();
        window = (Window) bxmlSerializer.readObject(JFlamGui.class, "gui.bxml");
        window.open(display);
	}

	public void suspend() throws Exception {
		
	}

	public static void main(String[] args) {
		DesktopApplicationContext.main(JFlamGui.class, args);
	}
}
