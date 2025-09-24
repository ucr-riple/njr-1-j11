package ccproxy;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ReadConfig {
	boolean configOk = false;
	String pghost = null;
	String pgport = "5432";
	String pguser = null;
	String pgpass = null;
	String pgdbname = null;

	String asteriskHost = null;
	String asteriskPort = "5038";
	String asteriskUser = null;
	String asteriskSecret = null;
	String listenPort = null;

	String licensedAgents=null;
	String licenseKey=null;
	String licenseExpires="";
	
	String agentVersion = null;
	String agentUpdateURL = null;
	String agentContext = null;
	// Czy po zakończeniu połączenia telefonicznego agent powienien przejść w tryb pauzy
	boolean pauseAfterConnection = false;
	// Czy po zalogowaniu agent powinien pozostać w trybie pauza
	boolean pauseWhenStartup = false;
	// Czy logować eventy płynące z asteriska.
	boolean logAsteriskEvents = false;
	String externAuthURL = null;
	boolean monitorDbActivity = false;
	boolean pauseWhenUnavailable = false;
	boolean requestCallTag = false;
	String configFile;

	
	String spyMode="";
	
	org.apache.log4j.Logger log;
	

	public ReadConfig(Parametry p, String filename) {

		log = p.log;
		FileReader Fr;
		try {
			configFile=filename;
			p.log.info("Trying to open config file [" + filename + "]");
			Fr = new FileReader(filename);
			BufferedReader br = new BufferedReader(Fr);
			p.log.info("File ok. Reading configuration.");
			while (br.ready()) {
				String line = br.readLine();
				String[] sline = line.split("=");
				if (sline.length != 2) {
					p.log.error("Can't parse line: " + line);
				} else {
					if (sline[0].equals("listen_port")) {
						listenPort = sline[1];
						p.log.debug("Listen port: " + listenPort);
						continue;
					}
					if (sline[0].equals("licensedAgents")) {
						licensedAgents = sline[1];
						p.log.debug("Licensed agents: " + licensedAgents);
						continue;
					}
					if (sline[0].equals("licenseKey")) {
						licenseKey = sline[1];
						p.log.debug("License key: " + licenseKey);
						continue;
					}
					if (sline[0].equals("licenseExpires")) {
						licenseExpires = sline[1];
						p.log.debug("License expires: " + licenseExpires);
						continue;
					}
					if (sline[0].equals("spyMode")) {
						spyMode = sline[1];
						p.log.debug("Spy mode: " + spyMode);
						continue;
					}
					if (sline[0].equals("pghost")) {
						pghost = sline[1];
						p.log.debug("Postgres host: " + pghost);
						continue;
					}
					if (sline[0].equals("pgport")) {
						pgport = sline[1];
						p.log.debug("Postgres port: " + pgport);
						continue;
					}
					if (sline[0].equals("pguser")) {
						pguser = sline[1];
						p.log.debug("Postgres user: " + pguser);
						continue;
					}
					if (sline[0].equals("dbname")) {
						pgdbname = sline[1];
						p.log.debug("Postgres dbname: " + pgdbname);
						continue;
					}
					if (sline[0].equals("pgpass")) {
						pgpass = sline[1];
						p.log.debug("Postgres pass: *******");
						continue;
					}
					if (sline[0].equals("asterisk_host")) {
						asteriskHost = sline[1];
						p.log.debug("Asterisk host: " + asteriskHost);
						continue;
					}
					if (sline[0].equals("asterisk_port")) {
						asteriskPort = sline[1];
						p.log.debug("Asterisk port: " + asteriskPort);
						continue;
					}
					if (sline[0].equals("asterisk_user")) {
						asteriskUser = sline[1];
						p.log.debug("Asterisk user: " + asteriskUser);
						continue;
					}
					if (sline[0].equals("asterisk_secret")) {
						asteriskSecret = sline[1];
						p.log.debug("Asterisk secret: ***** ");
						continue;
					}
					if (sline[0].equals("agent_version")) {
						agentVersion = sline[1];
						p.log.debug("Agent version: " + agentVersion);
						continue;
					}
					if (sline[0].equals("agent_update_url")) {
						agentUpdateURL = sline[1];
						p.log.debug("Agent update url: " + agentUpdateURL);
						continue;
					}
					if (sline[0].equals("agentContext")) {
						agentContext = sline[1];
						p.log.debug("Agent context: " + agentContext);
						continue;
					}
					if (sline[0].equals("pauseAfterConnection")
							&& sline[1].equals("1")) {
						pauseAfterConnection = true;
						p.log.debug("Pause After Connection: true");
						continue;
					}
					if (sline[0].equals("pauseWhenStartup")
							&& sline[1].equals("1")) {
						pauseWhenStartup = true;
						p.log.debug("Pause when startup: true");
						continue;
					}
					if (sline[0].equals("logAsteriskEvents")
							&& sline[1].equals("1")) {
						logAsteriskEvents = true;
						p.log.debug("Log Asterisk events: true");
						continue;
					}
					if (sline[0].equals("externAuthURL")) {
						externAuthURL = sline[1];
						p.log.debug("External authorization URL: "+externAuthURL);
						continue;
					}
					if (sline[0].equals("monitorDbActivity")
							&& sline[1].equals("1")) {
						monitorDbActivity = true;
						p.log.debug("Monitor Db Activity: true");
						continue;
					}
					if (sline[0].equals("pauseWhenUnavailable")
							&& sline[1].equals("1")) {
						pauseWhenUnavailable = true;
						p.log.debug("Pause when unavailable: true");
						continue;
					}
					if (sline[0].equals("requestCallTag")
							&& sline[1].equals("1")) {
						requestCallTag = true;
						p.log.debug("Request call tag: true");
						continue;
					}
					p.log.debug("Unknown parametr: " + sline[0]);
				}
			}
			configOk = true;
			if (listenPort == null) {
				p.log
						.fatal("Brak kluczowego parametru konfiguracyjnego: listenPort");
				configOk = false;
			}
			if (agentVersion == null) {
				p.log
						.fatal("Brak kluczowego parametru konfiguracyjnego: agent_version");
				configOk = false;
			}
			if (agentUpdateURL == null) {
				p.log
						.fatal("Brak kluczowego parametru konfiguracyjnego: agentUpdateURL");
				configOk = false;
			}
			if (agentContext == null) {
				p.log
						.fatal("Brak kluczowego parametru konfiguracyjnego: agentContext");
				configOk = false;
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			p.log.error("Nie znaleziono pliku konfiguracyjnego: " + filename);
			System.exit(0);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
