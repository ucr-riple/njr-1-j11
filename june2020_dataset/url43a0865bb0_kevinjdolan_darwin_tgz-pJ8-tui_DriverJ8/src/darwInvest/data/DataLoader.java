package darwInvest.data;

import java.io.File;

import darwInvest.data.index.TickerIndex;
import darwInvest.data.utility.Serializer;

/**
 * The Data Loader class contains static utility methods
 * for accessing and loading serialized tickers
 * @author Kevin Dolan
 */
public class DataLoader {

	/**
	 * Access the StockData folder, and return the indexes for
	 * all DataSources
	 * @return an array of ticker indexes
	 */
	public static TickerIndex[] getDataSources(Serializer serializer) {
		File dataDir = new File("StockData");
		File[] sources = dataDir.listFiles();
		TickerIndex[] result = new TickerIndex[sources.length];
		for(int i = 0; i < sources.length; i++) {
			File file = new File(sources[i], sources[i].getName() + ".index");
			result[i] = (TickerIndex) serializer.readObject(file);
		}
		return result;
	}
	
}
