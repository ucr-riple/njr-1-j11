package main.parser;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import main.implementations.OrderBuilderImpl;
import main.implementations.OrderbookImpl;
import main.implementations.order.Order;
import main.interfaces.OrderBuilder;
import main.interfaces.Orderbook;
import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.bean.ColumnPositionMappingStrategy;
import au.com.bytecode.opencsv.bean.CsvToBean;

/**
 * CSV parser class for reading in Sirca formatted orderbook data.
 */
public class SircaCSVParser {
	
	public static Orderbook input(String fileName) {
		ColumnPositionMappingStrategy strat = new ColumnPositionMappingStrategy();
		strat.setType(SircaOrder.class);
		String[] columns = new String[] {"instrument", "date", "time", "recordType", "price", "volume", "undisclosedVolume", "value","qualifiers", "transactionId", "bidId","askId", "bidOrAsk","entryTime","oldPrice", "oldVolume", "buyerBrokerId", "sellerBrokerId" }; // the fields to bind do in your JavaBean
		strat.setColumnMapping(columns);
		 
		CsvToBean csv = new CsvToBean();
		 
		String csvFilename = "./resources/" + fileName;
		CSVReader csvReader = null;
		try {
			csvReader = new CSVReader(new FileReader(csvFilename), ',', '\'', 2);
		} catch (FileNotFoundException e) {
			System.err.println("Unable to find the specified file. Make sure it is in the resources folder.");
			return null;
		}

		List list = csv.parse(strat, csvReader);
		System.out.println("Sirca data loaded to system.....");
		
		System.out.println("Converting plain text data to descriptive orders.....");
		// bid and ask lists.
		List<Order> bidList = new ArrayList<Order>();
		List<Order> askList = new ArrayList<Order>();
		List<Order> tradeList = new ArrayList<Order>();
		long timeBefore = System.currentTimeMillis();
		for (Object object : list) {
		    SircaOrder order = (SircaOrder) object;
		    OrderBuilder orderBuilder = new OrderBuilderImpl(order);
		    if (("ENTER").equals(order.getRecordType())) {
			    if (("B").equals(order.getBidOrAsk())) {
			    	bidList.add(orderBuilder.build());
			    } else if (("A").equals(order.getBidOrAsk())) {
			    	askList.add(orderBuilder.build());
			    }
		    } else if (("TRADE").equals(order.getRecordType())) {
		    	tradeList.add(orderBuilder.build());
		    }
	    }
		
		System.out.println("Populating orderbook.....");
		Orderbook orderbook = new OrderbookImpl(bidList, askList, tradeList);
		long timeAfter = System.currentTimeMillis();
		
		System.out.println("Ready!");
		System.out.println("Total time taken: " + (timeAfter - timeBefore));
		return orderbook;
	}
}


