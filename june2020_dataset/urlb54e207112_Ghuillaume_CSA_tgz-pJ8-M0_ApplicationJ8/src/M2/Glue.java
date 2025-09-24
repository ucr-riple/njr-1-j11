package M2;

import java.util.HashMap;

import M0.Trace;

public class Glue {

	protected HashMap<RoleFrom, RoleTo> connection;
	
	public Glue() {
		this.connection = new HashMap<RoleFrom, RoleTo>();
	}
	
	public void addConnection(RoleFrom rf, RoleTo rt) {
		this.connection.put(rf, rt);
	}
	
	public RoleTo getDest(RoleFrom rf) {
		return this.connection.get(rf);
	}
	
	public void bind(Role sender, String message) {
		
		if(sender instanceof RoleFrom) {
			RoleTo dest = this.connection.get(((RoleFrom)sender));
			if(dest != null) {
				dest.activate(message);
			}
			else {
				Trace.printError("Role " + sender.getName() + " isn't connected inside the connector. Message ignored");
			}
		}
	}
	
}
