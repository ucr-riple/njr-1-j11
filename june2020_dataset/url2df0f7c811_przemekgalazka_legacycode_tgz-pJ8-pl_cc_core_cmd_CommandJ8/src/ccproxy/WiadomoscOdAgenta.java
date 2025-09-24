package ccproxy;

import java.util.Date;

import pl.cc.core.cmd.Command;

public class WiadomoscOdAgenta {
	AgentConnection agent;
	String wiadomosc;
	String param = null;
	int numer = 1;
	boolean wyslana = false;
	Date time = null;
	
	Command cmd;

	public WiadomoscOdAgenta(AgentConnection agent, int numer, Date time, Command cmd) {
		super();
		this.agent = agent;
		this.numer = numer;
		this.time = time;
		this.cmd = cmd;
		this.wiadomosc = cmd.getOrginalLine();
	}
	
	public WiadomoscOdAgenta(AgentConnection agent, String wiadomosc, int numer) {
		super();
		this.agent = agent;
		this.wiadomosc = wiadomosc;
		this.numer = numer;
	}

	public WiadomoscOdAgenta(AgentConnection agent, String wiadomosc,
			String param, int numer) {
		super();
		this.agent = agent;
		this.wiadomosc = wiadomosc;
		this.param = param;
		this.numer = numer;
	}

	public Command getCmd() {
		return cmd;
	}
	
}
