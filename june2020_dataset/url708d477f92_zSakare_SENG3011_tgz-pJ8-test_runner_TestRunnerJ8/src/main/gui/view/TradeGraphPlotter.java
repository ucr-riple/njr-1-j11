package main.gui.view;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.JDialog;

import main.implementations.order.AlgorithmicTrade;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.DateTickUnit;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.Day;
import org.jfree.data.time.Millisecond;
import org.jfree.data.time.Minute;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;

public class TradeGraphPlotter extends JDialog {

	private static final long serialVersionUID = 1L;
	
	private Map<AlgorithmicTrade, AlgorithmicTrade> tradePair;

	public TradeGraphPlotter(String appTitle, String chartTitle, Map<AlgorithmicTrade, AlgorithmicTrade> tradePair) {
		this.tradePair = tradePair;
		XYDataset xySeries = createDataSet();
		JFreeChart chart = createChart(xySeries, chartTitle);
		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new Dimension(1024, 720));
		setContentPane(chartPanel);
	}

	private XYDataset createDataSet() {
		final TimeSeriesCollection dataset = new TimeSeriesCollection();
		
		TimeSeries bidData = new TimeSeries("Bid Trades");
		TimeSeries askData = new TimeSeries("Ask Trades");
		
		List<AlgorithmicTrade> keys = new ArrayList<AlgorithmicTrade>();
		keys.addAll(tradePair.keySet());
		
		for (AlgorithmicTrade bid : keys) {
			AlgorithmicTrade ask = tradePair.get(bid);
			bidData.add(new Millisecond(bid.getBidOrder().getDateTime()), bid.getBidOrder().getPrice());
			askData.add(new Millisecond(ask.getAskOrder().getDateTime()), ask.getAskOrder().getPrice());
		}
		
		dataset.addSeries(bidData);
		dataset.addSeries(askData);
		
		return dataset;
	}

	private JFreeChart createChart(XYDataset dataSet, String chartTitle) {
		JFreeChart chart = ChartFactory.createTimeSeriesChart(chartTitle, 
				"Time", 
				"Price", 
				dataSet, 
				true, 
				true, 
				false);
		
		return chart;
	}
}
