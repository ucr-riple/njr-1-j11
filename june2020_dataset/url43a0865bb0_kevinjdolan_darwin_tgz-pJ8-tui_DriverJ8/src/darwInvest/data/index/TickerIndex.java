package darwInvest.data.index;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;
import darwInvest.data.Ticker;

/**
 * The TickerIndex contains an index of tickers.
 * 
 * @author Andrew Perrault, Kevin Dolan
 */
public class TickerIndex implements Serializable {

	private static final long serialVersionUID = 693612329281611960L;
	private String source;
	private Map<String, IndexItem> index;
	
	/**
	 * Instantiate a new blank ticker index
	 * @param source the name of the data source
	 */
	public TickerIndex(String source) {
		this.source = source;
		index = new TreeMap<String, IndexItem>();
	}

	/**
	 * Add a ticker to this index, replace it if it
	 * already exists
	 * @param ticker the ticker to add
	 */
	public void addTicker(Ticker ticker) {
		IndexItem item = new IndexItem(ticker);
		index.put(ticker.getSymbol(), item);
	}

	/**
	 * Determine whether a symbol exists in the index
	 * @param symbol the symbol to check for
	 * @return		 true if it exists
	 */
	public boolean symbolExists(String symbol) {
		return index.get(symbol) == null;
	}
	
	/**
	 * Return a ticker object pointed to by the index
	 * @param symbol the symbol to load
	 * @return		 the unserialized ticker object
	 */
	public Ticker getTickerFile(String symbol) {
		File fileName = new File("StockData/" + source + "/" + symbol + ".stock");
		
		Ticker ticker = null;
		FileInputStream fis = null;
		ObjectInputStream in = null;
		try {
			fis = new FileInputStream(fileName);
			in = new ObjectInputStream(fis);
			ticker = (Ticker)in.readObject();
			in.close();
		}
		catch(IOException e) {} 
		catch (ClassNotFoundException e) {}
		
		return ticker;
	}
	
	/**
	 * @return the name of this data source
	 */
	public String getSource() {
		return source;
	}
	
	/**
	 * @return the number of entries in the index
	 */
	public int getSize() {
		return index.size();
	}
	
	/**
	 * @return a collection of all the items in the index
	 */
	public Collection<IndexItem> getIndex() {
		return index.values();
	}

	public String toString() {
		String result = "Data Source: " + source + "\n";
		result += "Ticker count: " + getSize() + "\n";
		
		result += "List of Tickers:\n";
		Collection<IndexItem> items = getIndex();
		for(IndexItem item : items)
			result += item + "\n";
		
		return result;
	}
}