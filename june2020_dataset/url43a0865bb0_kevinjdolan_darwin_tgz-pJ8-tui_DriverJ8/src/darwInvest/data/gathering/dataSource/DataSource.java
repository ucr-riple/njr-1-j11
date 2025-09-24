package darwInvest.data.gathering.dataSource;

import java.io.File;
import java.util.Date;

import darwInvest.data.Ticker;
import darwInvest.data.index.TickerIndex;
import darwInvest.data.utility.Serializer;

/**
 * The DataSource superclass is to be extended by data sources for
 * abstraction purposes
 * @author Kevin Dolan
 */
public abstract class DataSource {

	private Serializer serializer;
	private String name;
	protected TickerIndex index;
	protected Date startdate;
	protected Date enddate;
	
	/**
	 * Create a new DataSource
	 * @param name 		 the name of the data source (should be a valid directory name)
	 * @param serializer the serializer to use for reading/writing
	 */
	public DataSource(String name, Serializer serializer) {
		this.name = name;
		this.serializer = serializer;
		
		//Ensure the directories exist
		File topDir = new File("StockData");
		if(!topDir.isDirectory())
			topDir.mkdir();
		File dir = new File("StockData/" + name);
		if(!dir.isDirectory())
			dir.mkdir();
		
		//Load the index if it already exists, or create it
		File indexFile = new File ("StockData/" + name + "/" + name + ".index");
		if(indexFile.exists())
			index = (TickerIndex) serializer.readObject(indexFile);
		else
			index = new TickerIndex(name);
	}
	
	public DataSource(String name, Serializer serializer, Date startdate, Date enddate) {
		this.name = name;
		this.serializer = serializer;
		this.startdate = startdate;
		this.enddate = enddate;
		
		//Ensure the directories exist
		File topDir = new File("StockData");
		if(!topDir.isDirectory())
			topDir.mkdir();
		File dir = new File("StockData/" + name);
		if(!dir.isDirectory())
			dir.mkdir();
		
		//Load the index if it already exists, or create it
		File indexFile = new File ("StockData/" + name + "/" + name + ".index");
		if(indexFile.exists())
			index = (TickerIndex) serializer.readObject(indexFile);
		else
			index = new TickerIndex(name);
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Write a ticker object to the appropriate directory
	 * @param ticker the ticker to be written
	 */
	protected void addTicker(Ticker ticker) {
		index.addTicker(ticker);
		File file = new File("StockData/" + name + "/" + ticker.getSymbol() + ".stock");
		serializer.writeObject(ticker, file);
	}
	
	/**
	 * @return the current ticker index
	 */
	public TickerIndex getTickerIndex() {
		return index;
	}
	
	/**
	 * Write the current ticker index
	 */
	public void writeTickerIndex() {
		File file = new File("StockData/" + name + "/" + name + ".index");
		serializer.writeObject(index, file);
	}
	
	/**
	 * Run this data source's data collection procedures,
	 * making calls to this.addTicker(...) for adding
	 * tickers
	 */
	public abstract void gatherData();

	public Date getStartdate() {
		return startdate;
	}

	public Date getEnddate() {
		return enddate;
	}
}
