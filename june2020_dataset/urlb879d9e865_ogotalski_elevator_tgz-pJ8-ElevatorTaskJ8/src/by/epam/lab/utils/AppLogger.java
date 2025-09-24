package by.epam.lab.utils;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
public class AppLogger {
	private static final String LOG4J_CONFIG_FILENAME = "log4j.xml";
	static{
		DOMConfigurator.configure(LOG4J_CONFIG_FILENAME);
	}
	public final static Logger LOG = Logger.getRootLogger();

}
