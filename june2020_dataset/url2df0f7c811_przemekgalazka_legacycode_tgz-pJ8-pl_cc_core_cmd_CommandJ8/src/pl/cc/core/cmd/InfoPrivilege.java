package pl.cc.core.cmd;

import pl.cc.real.RealAgent;

/**
 * 
 * +INFO Privilege 'grand', Agent '301'
 *
 * @since 2009-06-24
 */
public class InfoPrivilege extends Command {
	RealAgent agent;
	boolean havePermission;
	
	private InfoPrivilege(String orginalLine,  RealAgent agent, String privMode){
		super(orginalLine);
		this.agent=agent;
		if ("grand".equalsIgnoreCase(privMode)) {
			havePermission = true;
		} else { 
			havePermission = false;
		}
	}
	
	public static InfoPrivilege factoryInt(String s){
		String [] names = {"privilege", "agent"};
		String [] v = getValuesINFO(s, names); 
		if (v==null) return null;
		try {
			Integer agentId = new Integer(v[1]);
			return new InfoPrivilege(
					s,
					new RealAgent(agentId),
					v[0]
			);
		} catch (NumberFormatException e){
			return null;
		}
	}

	@Override
	public int getType() {
		return CMD_INFO_PRIVILEGE;
	}

	public boolean getPermission() {
		return havePermission;
	}

	public RealAgent getAgent() {
		return agent;
	}
	
}
