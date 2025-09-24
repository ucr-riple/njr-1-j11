package pl.cc.core.cmd;

/**
 * Poprawna autoryzacja - dostajemy dane zalogowanego właśnie agenta/supervisora
 * +OK Welcome Name 'Kowalski Jan'
 * 
 * @since Mar 5, 2008
 */
public class OkWelcome extends Command {
	String name;
	String extension;
	int agentNumber;
	String type;

	//+INFO Name 'Kowalski Jan', Extension '877', Type: 'agent'

	private OkWelcome(String orginalLine, String name, String extension, String agentNumber, String type){
		super(orginalLine);
		this.name=name;
		this.extension = extension;
		this.type = type;
		this.agentNumber = new Integer(agentNumber);
	}
	
	public static Command factoryInt(String s){
		String [] names = {"name", "extension", "agentnumber", "type"};
		String [] v = getValuesINFO(s, names); 
		if (v==null) return null;
		return new OkWelcome(
				s,
				v[0],	v[1],v[2],v[3]
		);
	}
	
	@Override
	public int getType() {
		return CMD_OK_WELCOME;
	}

	public String getName() {
		return name;
	}

	public String getExtension() {
		return extension;
	}
	
	public String getAgentType() {
		return type;
	}
	
	public int getAgentNumber() {
		return agentNumber;
	}
	
	public boolean isSupervisor(){
		return "supervisor".equals(type);
	}

	public boolean isNormalAgent(){
		return "agent".equals(type);
	}

	
}
