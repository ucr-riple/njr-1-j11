package pl.cc.core.cmd;


/**
 * Nadpisanie połączenia
 * 
 *  	Nie typowe - nie użwać jako schematu
 *
 * @since 2009-06-01
 */
public class ErrSameAgentConnection extends Command {
	String ip;
	String message;

	//+INFO Type 'connection_replaced', Message 'Connection replaced', Ip '127.0.0.1'

	
	private ErrSameAgentConnection(String orginalLine, String ip, String message){
		super(orginalLine);
		this.ip = ip;
		this.message = message;
	}
	
	public static Command factoryInt(String s){
		String [] names = {"type","message","ip"};
		String [] values = getValuesINFO(s, names); 
		if (values==null) return null;
		if ("connection_replaced".equalsIgnoreCase(values[0])){
			return new ErrSameAgentConnection(s, values[2], values[1]);	
		} else { 
			return null;
		}
	}
	
	@Override
	public int getType() {
		return CMD_ERR_CONNECTION_REPLACED;
	}

	public String getIp() {
		return ip;
	}
	
}
