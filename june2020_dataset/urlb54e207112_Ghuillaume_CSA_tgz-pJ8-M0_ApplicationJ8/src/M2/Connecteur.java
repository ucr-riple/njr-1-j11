package M2;

import java.util.HashMap;


public class Connecteur extends Element {
	
	protected HashMap<String, RoleFrom> rolesF;
	protected HashMap<String, RoleTo> rolesT;
	protected Glue glue;
	
	
	public Connecteur(String name, Configuration parent) {
		super(name, parent);
		this.rolesT = new HashMap<String, RoleTo>();
		this.rolesF = new HashMap<String, RoleFrom>();
		this.glue = new Glue();
	}
	
	public final void addConnectedRoles(RoleFrom rf, RoleTo rt) {
		this.rolesF.put(rf.getName(), rf);
		this.rolesT.put(rt.getName(), rt);
		this.glue.addConnection(rf, rt);
	}
	
	public void setGlue(Glue glue) {
		this.glue = glue;
	}

	
	// run
	
	public final void run(Role sender, String message) {
		// si pas composite, pas appeler la glue et exécuter au niveau du dessous
		if(this.subConfiguration == null) {
			this.glue.bind(sender, message);
		}
		else {
			((Configuration)this.getParent()).callBindings(sender, message);
		}
	}
	
	/*
	 * GETTERS
	 */

	public final RoleFrom getRoleFrom(String name) {
		return this.rolesF.get(name);
	}
	
	public final RoleTo getRoleTo(String name) {
		return this.rolesT.get(name);
	}
}
