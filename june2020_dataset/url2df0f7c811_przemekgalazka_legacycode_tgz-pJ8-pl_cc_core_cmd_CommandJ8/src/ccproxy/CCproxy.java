package ccproxy;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;

import pl.cc.core.PauseType;

public class CCproxy extends Thread {
	static final String VERSION = "363";
	static Parametry p = new Parametry();
	static String version;
	
	org.apache.log4j.Logger log = p.log;

	public void start(String configFile) throws IOException {
		version="DEV";
		InputStream vis=getClass().getResourceAsStream("/.version");
		if (vis!=null) version=new BufferedReader(new InputStreamReader((vis))).readLine();
		System.out.println("CCProxy version: "+version);
		p.c = new ReadConfig(p, configFile);
		ReadConfig c = p.c;
		if (!checkLicense(p.c.licensedAgents, p.c.licenseKey, p.c.licenseExpires)){
			System.out.println("License error");
			System.exit(-1);
		}
		p.dbConn = new DBConnection(c.pghost, c.pgport, c.pgdbname, c.pguser, c.pgpass);
		if (!c.configOk)
			System.exit(-1);
		loadPauseTypeList();
		GadajAsterisk gadajAsteriskReader, gadajAsteriskWriter;
		try {
			gadajAsteriskReader = new GadajAsterisk(c.asteriskHost, c.asteriskPort,
					c.asteriskUser, c.asteriskSecret, p);
			p.gadajAsterisk = gadajAsteriskReader;
			gadajAsteriskReader.start();
			gadajAsteriskWriter = new GadajAsterisk();
			gadajAsteriskWriter.start();
			Listener listener = new Listener(c.listenPort, p);
			listener.start();

			while (true) {
				sleep(1000);
				if (gadajAsteriskReader.connected)
					continue;
				p.log.info("Stracono polaczenie z asterisk.");
				System.exit(1);	// niestety na razie reconnect powoduje że nie przetwarzają się dalej wiadomości
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public CCproxy() throws IOException {
	}

	public CCproxy(String configFile) throws IOException {
		start(configFile);
	}

	/**
	 * Załadowanie z bazy danych listy definicji pauz
	 */
	private void loadPauseTypeList(){
		log.debug("Loading pause definition list from database ...");
		ResultSet rs = p.dbConn.query("select id_pause_def, name from t_pause_def;");

		try {
			while (rs.next()) {
				PauseType pauseType = new PauseType(rs.getInt("id_pause_def"), rs.getString("name"));
				p.pauseTypeList.add(pauseType);
			}
			p.pauseTypeList.sort();
			log.info("Pause definition list loaded. Size: "+p.pauseTypeList.size());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	static boolean checkLicense(String max_agents, String key, String expirydate) {

		try {
			File file = new File("/usr/sbin/dmidecode");
			FileInputStream fis = new FileInputStream(file);
			byte[] bytes = new byte[(int) file.length()];

			int offset = 0;
			int numRead = 0;
			while (numRead >= 0) {
				numRead = fis.read(bytes, offset, bytes.length - offset);
			}
			fis.close();

			String dmidecode_digest = MD5calc(bytes);

			String uuid = "";
			String line;
			Process p = Runtime.getRuntime().exec(
					"/usr/sbin/dmidecode -s system-uuid");

			BufferedReader input = new BufferedReader(new InputStreamReader(p
					.getInputStream()));
			while ((line = input.readLine()) != null) {
				uuid=line;

			}
			input.close();

			String uuid_digest = MD5calc(uuid.getBytes());
			String str = dmidecode_digest + "thulium" + max_agents + expirydate
					+ uuid_digest;

			if (key.equals(MD5calc(str.getBytes())))
				return true;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	static String MD5calc(byte[] bytes) {
		MessageDigest m;
		try {
			m = MessageDigest.getInstance("MD5");
			m.update(bytes, 0, bytes.length);
			BigInteger i = new BigInteger(1, m.digest());
			return String.format("%1$032X", i).toLowerCase();

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}

	
	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		CCproxy ccproxy;

		if (args.length > 0)
			ccproxy = new CCproxy(args[0]);
		else
			ccproxy = new CCproxy();
	}
}
