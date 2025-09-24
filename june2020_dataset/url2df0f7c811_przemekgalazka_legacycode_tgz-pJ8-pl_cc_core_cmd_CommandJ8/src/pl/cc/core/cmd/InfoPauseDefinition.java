package pl.cc.core.cmd;

import pl.cc.core.PauseType;

/**
 * Definicja rodzaju pauzy
 * 
 * +INFO PauseDefinition 'administracyjna', Id '1' 
 * 	
 * @since 2009-06-24
 */
public class InfoPauseDefinition extends Command {
	PauseType pauseType;
	
	private InfoPauseDefinition(String orginalLine, String name, int id ){
		super(orginalLine);
		pauseType = new PauseType(id, name);
	}
	
	public InfoPauseDefinition(final PauseType pauseType) {
		super("n/a");
		this.pauseType = pauseType;
	}

	public static InfoPauseDefinition factoryInt(String s) {
		String[] names = { "PauseDefinition", "Id" };
		String[] v = getValuesINFO(s, names);
		if (v == null){
			return null;
		}
		return new InfoPauseDefinition(s, 
				v[0], 
				new Integer(v[1]).intValue() 
		);
	}

	@Override
	public String serializeToString(){
		if (pauseType!=null){
			return "+INFO PauseDefinition '" + pauseType.getName() + "', " +
					"Id '" + pauseType.getId()+ "'";
		} else {
			return null;
		}
	}
	
	@Override
	public int getType() {
		return CMD_INFO_PAUSETYPE;
	}

	public PauseType getPauseType(){
		return pauseType;
	}


	
}
