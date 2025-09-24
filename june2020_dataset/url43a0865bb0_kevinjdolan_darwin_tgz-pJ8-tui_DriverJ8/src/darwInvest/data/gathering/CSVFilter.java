package darwInvest.data.gathering;

import java.io.File;
import java.io.FilenameFilter;

/**
 * The CSVfilter reads filters out non-csv files.
 * 
 * @author Andrew Perrault
 */
public class CSVFilter implements FilenameFilter {

	public boolean accept(File dir, String name) {
		if (name.contains(".csv"))
			return true;
		else
			return false;
	}

}
