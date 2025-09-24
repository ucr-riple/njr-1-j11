package cz.mff.dpp.args.examples;

import java.util.List;

import cz.mff.dpp.args.Argument;
import cz.mff.dpp.args.Constraint;
import cz.mff.dpp.args.Option;
import cz.mff.dpp.args.ParseException;
import cz.mff.dpp.args.Parser;



/**
 * Parser.usage() demo.
 *
 */
public class HelpTest {

	private static enum Day {
		SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY
	}

	@SuppressWarnings("unused")
	static final class HelpTestOptions {


		@Option(name = "-V", aliases = "--version", description = "Display the version number and exit.")
		boolean version;

		@Option(name = "-p", aliases = "--port", description = "Port to connect to on the remote host.")
		@Constraint(min="1", max="65535")
		void setPort(int port) {
				this.port = port;
		}

		private int port;

		@Option(name = "-l", description = " Specifies the user to log in as on the remote machine.")
		String login_name;

		@Option(name = "--config-file", description = "Specifies an alternative per-user configuration file.", incompatible = {
				"--some-list"}, mustUseWith = { "--enum" })
		String config_file = "~/.ssh/config";

		@Argument(index = 0, name = "hostname", description = "The host to connect to.", required = true)
		String hostname;

		@Option(name = "--some-list", description = "Just list of things.")
		String[] list;

		@Option(name = "-c", description = "Just char test.", mustUseWith = { "--enum" })
		char c;

		@Option(name = "--enum", description = "Just Enum test", required = true)
		Day day;

	}


	public static void main(String[] args) throws ParseException {
		HelpTestOptions testOptions = new HelpTestOptions();
		Parser parser = new Parser(testOptions);

		parser.usage();

	}

}
