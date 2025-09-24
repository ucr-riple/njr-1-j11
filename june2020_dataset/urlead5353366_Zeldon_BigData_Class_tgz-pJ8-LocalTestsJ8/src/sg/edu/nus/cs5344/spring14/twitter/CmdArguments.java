package sg.edu.nus.cs5344.spring14.twitter;

import java.util.ArrayList;
import java.util.List;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;


/**
 * List of the command parameters
 *
 * @see http://jcommander.org/
 * @author Tobias Bertelsen
 *
 */
public class CmdArguments {

	private static CmdArguments instance = null;

	@Parameter(names = {"-d"}, description="The working directory for the data. Input files must be in 'Input' ", required=false)
	private String workingDir = "/data";

	@Parameter(names = {"-s","--skip"}, variableArity = true, description="Skip jobs based on ID. You can specify multiple job IDs after this flag, e.g. --skip A B ")
	private List<String> skipTheseJob = new ArrayList<String>();

	@Parameter(names = {"-o","--only"}, variableArity = true, description="Run only these jobs based on ID. Takes precedence over --skip. You can specify multiple job IDs after this flag, e.g. --only A B ")
	private List<String> onlyTheseJob = new ArrayList<String>();

	// Do not allow clients to instantiate
	private CmdArguments(String[] args) {
		// Overwrite command line
		new JCommander(this, args);
	}

	public static CmdArguments getArgs() {
		if (instance == null) {
			throw new IllegalStateException("Arguments not instantiated");
		}
		return instance;
	}

	public static CmdArguments instantiate(String[] args) {
		return instance = new CmdArguments(args);
	}

	public String getWorkingDir() {
		return workingDir;
	}

	public boolean skipJob(String ID) {
		if (onlyTheseJob.isEmpty()) {
			return skipTheseJob.contains(ID);
		} else {
			return !onlyTheseJob.contains(ID);
		}

	}

}
