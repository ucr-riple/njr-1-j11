package pl.cc.core.cmd;


/**
 * @since 2010-05-10
 */
public class InfoType extends Command {
	String message;
	boolean reconnect=true;

	//+INFO Type 'connection_replaced', Message 'Connection replaced', Ip '127.0.0.1'

	
	public boolean getReconnect() {
		return reconnect;
	}

	private InfoType(String orginalLine, String message){
		super(orginalLine);
		this.message = message;
	}
	
	public static Command factoryInt(String s){
		String [] names = {"type","message"};
		String [] values = getValuesINFO(s, names); 
		if (values==null) return null;
		InfoType it=new InfoType(s, values[1]);
		if ("connection_replaced".equalsIgnoreCase(values[0])){
			it.reconnect=false;
		}
		if ("license_error".equalsIgnoreCase(values[0])){
			it.reconnect=false;
		}
		return it;
	}
	
	@Override
	public int getType() {
		return CMD_INFO_TYPE;
	}

	public String getMessage() {
		return message;
	}

	
}
