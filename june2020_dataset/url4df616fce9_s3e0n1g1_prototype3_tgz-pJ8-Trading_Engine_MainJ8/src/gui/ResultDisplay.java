package gui;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.LinkedList;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import Selecting_Algothrim.lecMSMomentum;
import Selecting_Algothrim.lecMSReverse;
import Selecting_Algothrim.resultObjectL;
import Selecting_Algothrim.signalObject;
import Trading_Engine.GraphData;
import Trading_Engine.MyAskList;
import Trading_Engine.MyBidList;
import Trading_Engine.OneData;
import Trading_Engine.ResultData;
import Trading_Engine.myDatabase;

public class ResultDisplay extends JFrame {
	public static myDatabase myDB; 
	private LinkedList<String> overviewResult;
	public static double Threshold;
	public static int stockAmount;
	public static String Strategy;
	private LinkedList<String> strategyResult;
	private LinkedList<ResultData> completedTrade;
	private LinkedList<GraphData> bidFirstList;
	private LinkedList<GraphData> askFirstList;
	private LinkedList<GraphData> strategyAsk;
	private LinkedList<GraphData> strategyBid;
	private String title;
	public ResultDisplay(String frametitle, myDatabase db, LinkedList<String> result){
		final JFrame display = this;
		overviewResult = result;
		myDB = db;
		title = frametitle;
		JTabbedPane jtb = new JTabbedPane();
		Container con = this.getContentPane(); 
		con.add(jtb);

		setTitle(frametitle); 
		setSize(800,600);
		setLocationRelativeTo(null);

		setResizable(false);

		JMenuBar menubar = new JMenuBar();

		JMenu file = new JMenu("File");
		JMenuItem quit = new JMenuItem("Close");

		menubar.add(file);
		file.add(quit);

		JMenu strategy = new JMenu("Strategy");
		JMenuItem momstrategy = new JMenuItem("Run a Strategy");
		//JMenuItem revstrategy = new JMenuItem("Run Momentum Reversion Strategy");
		strategy.add(momstrategy);
		//strategy.add(revstrategy);
		menubar.add(strategy);
		setJMenuBar(menubar);

		jtb.addTab("Analysis", analysisPanel());
		jtb.addTab("Original Trades", orderbookPanel());
		jtb.addTab("Graph", graphPanel());

		quit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				setVisible(false);
				dispose();
			}
		});

		momstrategy.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent event) {	
						//open new frame for strategy analysis
						ChooseStrategy strat = new ChooseStrategy(display);
						if (strat.run == true) {
							Strategy = strat.getStrategy();
							Threshold = strat.getThreshold();
							stockAmount = strat.getStockAmount();
							if(Strategy.equalsIgnoreCase("Momentum")){
								runMomentumStrategy();
							}else if(Strategy.equalsIgnoreCase("Mean Reversion")){
								runReversionStrategy();
							}
							myStrategyResult = new StrategySelected(strategyResult,completedTrade,askFirstList,bidFirstList,strategyAsk,strategyBid);
							myStrategyResult.setVisible(true);				
						}
					}

				}
				);	
		setVisible(true);
	}

	public JLabel stockName;
	public JLabel stockDate;
	public JLabel stockTime;
	public JLabel LinesRead;
	public JLabel enterLines;
	public JLabel askLines;
	public JLabel bidLines;
	public JLabel tradeLines;
	public JLabel amendLines;
	public JLabel deleteLines;

	private JPanel analysisPanel() {
		JPanel toppanel = new JPanel();
		JPanel analysispanel = new JPanel();
		JPanel panel = new JPanel();
		JPanel panel2 = new JPanel();

		Dimension d = new Dimension(200,200);
		panel.setLayout((new BoxLayout(panel, BoxLayout.PAGE_AXIS)));

		panel.setSize(d);
		panel.setPreferredSize(d);
		panel.setMaximumSize(d);

		panel2.setLayout((new BoxLayout(panel2, BoxLayout.PAGE_AXIS)));
		panel2.setSize(d);
		panel2.setPreferredSize(d);
		panel2.setMaximumSize(d);

		stockName = new JLabel("Name text");
		stockDate = new JLabel("Date text");
		stockTime = new JLabel("Time text");
		LinesRead = new JLabel("lines read");
		enterLines = new JLabel("enterLines text");
		askLines = new JLabel("askLines text");
		bidLines = new JLabel("bidLines text");
		tradeLines = new JLabel("tradeLines text");
		amendLines = new JLabel("amendLines text");
		deleteLines = new JLabel("deleteLines text");

		panel.add(new JLabel("Name of Instrument: "));
		panel.add(new JLabel("Date: "));
		panel.add(new JLabel("Time period: "));
		panel.add(new JLabel("Total lines read: "));
		panel.add(new JLabel("Number of ENTER: "));	
		panel.add(new JLabel("Number of Ask: "));	
		panel.add(new JLabel("Number of Bid: "));
		panel.add(new JLabel("Number of TRADE: "));	
		panel.add(new JLabel("Number of AMEND: "));
		panel.add(new JLabel("Number of DELETE: "));

		panel2.add(stockName);
		panel2.add(stockDate);
		panel2.add(stockTime);
		panel2.add(LinesRead);
		panel2.add(enterLines);
		panel2.add(askLines);
		panel2.add(bidLines);
		panel2.add(tradeLines);
		panel2.add(amendLines);
		panel2.add(deleteLines);

		stockName.setText(overviewResult.get(0));
		stockDate.setText(overviewResult.get(1));
		stockTime.setText(overviewResult.get(2));
		LinesRead.setText(overviewResult.get(3));
		enterLines.setText(overviewResult.get(4));
		askLines.setText(overviewResult.get(5));
		bidLines.setText(overviewResult.get(6));
		tradeLines.setText(overviewResult.get(7));
		amendLines.setText(overviewResult.get(8));
		deleteLines.setText(overviewResult.get(6));

		analysispanel.add(panel);
		analysispanel.add(panel2);

		toppanel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		c.fill = GridBagConstraints.VERTICAL;
		c.weightx = 1;
		c.gridx = 0;
		c.gridy = 0;	
		analysispanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));

		toppanel.add(analysispanel,c);
		c.fill = GridBagConstraints.VERTICAL;
		c.weightx = 2;
		c.gridx = 1;
		c.gridy = 0;
		toppanel.add(new JPanel(),c);

		return toppanel;
	}
	private FinalOrderbookTable ordertable;

	private JPanel orderbookPanel() {
		JPanel panel = new JPanel();
		ordertable = new FinalOrderbookTable();
		JTable buybook = new JTable();
		buybook.setModel(ordertable);

		try {
			ResultSet set = myDB.getResultSet("SELECT * FROM old_trade_list;");
			while(set.next()){
				long bidID = set.getLong(12);
				long askID = set.getLong(13);
				double tmpPrice = set.getDouble(6);
				int tmpVol = set.getInt(7);
				Time tmpTime = set.getTime(3);
				Object [] fakedata1 = {bidID,askID, "$ " + tmpPrice, tmpVol, tmpTime};
				ordertable.addElement(fakedata1);
			};
			set.close();
		}catch (SQLException e) {
			System.out.println("In Mainmenu/insertTables : " + e);
		}


		Dimension d = new Dimension (650,500);

		JScrollPane scrollTable = new JScrollPane(buybook);
		buybook.setFillsViewportHeight(true);
		scrollTable.setPreferredSize(d);
		scrollTable.setMaximumSize(d);	

		panel.add(scrollTable);
		return panel;
	}

	private JPanel graphPanel(){

		JPanel panel = new JPanel();
		JPanel graphpanel = new JPanel();

		panel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		//create new graph and data set
		LineGraph returntimegraph = new LineGraph("All accounts from " + title.substring(13));
		//add plots to the graph
		try {
			ResultSet set = myDB.getResultSet("SELECT * FROM all_list;");
			String tmp;
			String tmpType;
			while(set.next()){
				tmp = set.getString(5);
				if(tmp.equalsIgnoreCase("ENTER")){
					tmpType = set.getString(14);
					Double tmpPrice = set.getDouble(6);
					Time tmpTime = set.getTime(3);
					double finishTime = tmpTime.getHours() + (tmpTime.getMinutes()/60.0);
					if(tmpType.equalsIgnoreCase("B")){
						LineGraph.addToDataset(finishTime, tmpPrice);
					}else if(tmpType.equalsIgnoreCase("A")){
						LineGraph.addToDataset3(finishTime, tmpPrice);
					}
				}else if (tmp.equalsIgnoreCase("TRADE")){
					double tmpPrice = set.getDouble(6);
					Time tmpTime = set.getTime(3);
					double finishTime = tmpTime.getHours() + (tmpTime.getMinutes()/60.0);
					LineGraph.addToDataset2(finishTime, tmpPrice);
				}
			};
			set.close();
		}catch (SQLException e) {
			System.out.println("In Mainmenu/insertGraph : " + e);
		}
		
		//finalise dataset for graph
		returntimegraph.finishGraph(true);
		returntimegraph.setVisible(true);
		graphpanel.add(returntimegraph);

		//Configurations for graph, not sure if can be done

		JPanel config = new JPanel();
		config.setLayout((new BoxLayout(config, BoxLayout.PAGE_AXIS)));
		config.add(new JLabel("Configure Display"));
		JButton updategraph = new JButton("Update");
		updategraph.setSize(new Dimension(150,75));
		config.add(updategraph);
		config.setBorder(new EmptyBorder(0, 0, 0, 100)) ;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		c.gridx = 0;
		c.gridy = 0;
		panel.add(graphpanel,c );
		c.gridx = 2;
		c.gridy = 1;
		c.fill = GridBagConstraints.VERTICAL;
		c.weightx = 2;

		return panel;
	}
	
	private StrategySelected myStrategyResult; 
	//IMPLEMENT TRADING STRATEGY HERE

	protected void runReversionStrategy() {
		try{
			ResultSet set = myDB.getResultSet("SELECT * FROM all_list;");
			MyBidList myBidList = new MyBidList();
			MyAskList myAskList = new MyAskList();
			completedTrade = new LinkedList<ResultData>();
			strategyResult = new LinkedList<String>();
			askFirstList = new LinkedList<GraphData>();
			bidFirstList = new LinkedList<GraphData>();
			strategyAsk = new LinkedList<GraphData>();
			strategyBid = new LinkedList<GraphData>();
			LinkedList<ResultData> strategyTrade = new LinkedList<ResultData>();
			int count = 0;
			String tmp;
			String tmpType;
			double tmpPrice;
			int tmpVol;
			long tmpID = 0;
			int updateLines = 0;
			int deleteLines = 0;
			int strategyCount = 0;
			int tmpCount = 0;
			Time tmpTime;
			Time startTime = Time.valueOf("10:00:00");
			Time endTime = Time.valueOf("16:00:00");
			LinkedList<signalObject> signalList;
			signalObject tmpSignal;
			LinkedList<Long> buyOrderID = new LinkedList<Long>();
			LinkedList<Long> sellOrderID = new LinkedList<Long>();
			LinkedList<Long> OrderID;
			lecMSReverse strategy = new lecMSReverse();
			strategy.setThreShold(Threshold);
			strategy.setAmountToTrade(stockAmount); 

			long currentID = 0;
			while (set.next()){
				tmp = set.getString(5);
				tmpType = set.getString(14);
				tmpTime = set.getTime(3);
				if(tmp.equalsIgnoreCase("ENTER")){
					tmpPrice = set.getDouble(6);
					tmpVol = set.getInt(7);
					if(tmpType.equalsIgnoreCase("B")){
						tmpID = set.getLong(12);
						myBidList.add(tmpID,tmpPrice,tmpVol,tmpTime);
						bidFirstList.add(new GraphData(myBidList.get(0).getPrice(),tmpTime));
					}else if(tmpType.equalsIgnoreCase("A")){
						tmpID = set.getLong(13);
						myAskList.add(tmpID,tmpPrice,tmpVol,tmpTime);
						askFirstList.add(new GraphData(myAskList.get(0).getPrice(),tmpTime));
					}

					if(tmpTime.after(startTime) && tmpTime.before(endTime)){
						tmpCount = matchTrade(myBidList,myAskList,completedTrade,tmpTime,strategyTrade);

						for(int i = 1; i <= tmpCount;i++){
							strategy.addTrade(completedTrade.get(completedTrade.size() - (tmpCount - i) - 1).getPrice());
							signalList = strategy.generateSignalList(myBidList, myAskList);
							strategyTrade = new LinkedList<ResultData>();
							OrderID = new LinkedList<Long>();
							while(!signalList.isEmpty()){
								tmpSignal = signalList.poll();
								currentID--;
								if(tmpSignal.getType().equalsIgnoreCase("sell")){
									count++;
									myAskList.add(currentID,tmpSignal.getPrice(),tmpSignal.getQuantity(),tmpTime);
									askFirstList.add(new GraphData(myAskList.get(0).getPrice(),tmpTime));
									strategyAsk.add(new GraphData(tmpSignal.getPrice(),tmpTime));
									matchTrade(myBidList,myAskList,completedTrade,tmpTime,strategyTrade);
									sellOrderID.add(currentID);
								}else if (tmpSignal.getType().equalsIgnoreCase("buy")){
									count++;
									myBidList.add(currentID,tmpSignal.getPrice(),tmpSignal.getQuantity(),tmpTime);
									bidFirstList.add(new GraphData(myBidList.get(0).getPrice(),tmpTime));
									strategyBid.add(new GraphData(tmpSignal.getPrice(),tmpTime));
									matchTrade(myBidList,myAskList,completedTrade,tmpTime,strategyTrade);
									buyOrderID.add(currentID);
								}
								OrderID.add(currentID);
							}
							strategyCount += strategyTrade.size();
							strategy.getSTrade(strategyTrade);
							strategy.getReceiptList(OrderID);
						}
					}
				}else if (tmp.equalsIgnoreCase("AMEND")){
					updateLines++;
					tmpPrice = set.getInt(6);
					tmpVol = set.getInt(7);
					if(tmpType.equalsIgnoreCase("B")){
						tmpID = set.getLong(12);
						myBidList.update(tmpID,tmpPrice,tmpVol,tmpTime);
					}else if(tmpType.equalsIgnoreCase("A")){
						tmpID = set.getLong(13);
						myAskList.update(tmpID,tmpPrice,tmpVol,tmpTime);
					}
				}else if (tmp.equalsIgnoreCase("DELETE")){
					deleteLines++;
					if(tmpType.equalsIgnoreCase("B")){
						tmpID = set.getLong(12);
						myBidList.deleteOne(tmpID);
					}else if(tmpType.equalsIgnoreCase("A")){
						tmpID = set.getLong(13);
						myAskList.deleteOne(tmpID);
					}
				}
				count++;
			};
			for(int i = 0; i < strategyTrade.size();i++){
				System.out.println("strategyTrade(" + i +") - Bid: " + strategyTrade.get(i).getBuyID() + " Ask: " 
						+ strategyTrade.get(i).getAskID() + " Price: " + strategyTrade.get(i).getPrice());
			}

			LinkedList<resultObjectL> listOfResult;
			System.out.println("listOfAllReciept size: " + strategy.getReceiptLength());
			listOfResult = strategy.getResultListFromStrategy();

			System.out.println("listOfResult size: " + listOfResult.size());

			Mainmenu.console.append("Total lines read : " + count + "\n");
			Mainmenu.console.append("Total lines matched : " + completedTrade.size() + " == tmpCount: " + tmpCount + "\n");
			Mainmenu.console.append("Total lines update : " + updateLines + "\n");
			Mainmenu.console.append("Total lines delete : " + deleteLines + "\n");
			Mainmenu.console.append("Total Bid Error : " + myBidList.getError() + " Total Ask Error: " 
					+ myAskList.getError()+ "\n");
			Mainmenu.console.append("bid list contains " +  myBidList.getLength() + ".\n");
			Mainmenu.console.append("ask list contains " +  myAskList.getLength() + ".\n");

			resultObjectL tempResult;
			double endResult = 0;
			double endCount = 0;
			while(!listOfResult.isEmpty()){
				tempResult = listOfResult.poll();
				if(tempResult.getPercentage() != Double.NEGATIVE_INFINITY){
					endResult += tempResult.getPercentage();
					endCount++;
				}
			}

			//update jlabels
			strategyResult.add(Integer.toString( count));
			strategyResult.add(Integer.toString( completedTrade.size()));
			strategyResult.add(Integer.toString( strategyCount));
			strategyResult.add(Integer.toString( updateLines));
			strategyResult.add(Integer.toString( deleteLines));
			strategyResult.add(Integer.toString( myBidList.getLength()));
			strategyResult.add(Integer.toString( myAskList.getLength()));
			strategyResult.add(Double.toString(endResult/endCount));

			set.close();
		} catch (SQLException e) {
			System.out.println("In Mainmenu/runStrategy : " + e);
		}
	}

	protected void runMomentumStrategy() {
		try {
			ResultSet set = myDB.getResultSet("SELECT * FROM all_list;");
			MyBidList myBidList = new MyBidList();
			MyAskList myAskList = new MyAskList();
			completedTrade = new LinkedList<ResultData>();
			strategyResult = new LinkedList<String>();
			askFirstList = new LinkedList<GraphData>();
			bidFirstList = new LinkedList<GraphData>();
			strategyAsk = new LinkedList<GraphData>();
			strategyBid = new LinkedList<GraphData>();
			LinkedList<ResultData> strategyTrade = new LinkedList<ResultData>();
			int count = 0;
			String tmp;
			String tmpType;
			double tmpPrice;
			int tmpVol;
			long tmpID = 0;
			int updateLines = 0;
			int deleteLines = 0;
			int strategyCount = 0;
			int tmpCount = 0;
			Time tmpTime;
			Time startTime = Time.valueOf("10:00:00");
			Time endTime = Time.valueOf("16:00:00");
			LinkedList<signalObject> signalList;
			signalObject tmpSignal;
			LinkedList<Long> buyOrderID = new LinkedList<Long>();
			LinkedList<Long> sellOrderID = new LinkedList<Long>();
			LinkedList<Long> OrderID;
			lecMSMomentum strategy = new lecMSMomentum();
			strategy.setThreShold(Threshold);
			strategy.setAmountToTrade(stockAmount); 

			long currentID = 0;
			while (set.next()){
				tmp = set.getString(5);
				tmpType = set.getString(14);
				tmpTime = set.getTime(3);
				if(tmp.equalsIgnoreCase("ENTER")){
					tmpPrice = set.getDouble(6);
					tmpVol = set.getInt(7);
					if(tmpType.equalsIgnoreCase("B")){
						tmpID = set.getLong(12);
						myBidList.add(tmpID,tmpPrice,tmpVol,tmpTime);
						bidFirstList.add(new GraphData(myBidList.get(0).getPrice(),tmpTime));
					}else if(tmpType.equalsIgnoreCase("A")){
						tmpID = set.getLong(13);
						myAskList.add(tmpID,tmpPrice,tmpVol,tmpTime);
						askFirstList.add(new GraphData(myAskList.get(0).getPrice(),tmpTime));
					}

					if(tmpTime.after(startTime) && tmpTime.before(endTime)){
						tmpCount = matchTrade(myBidList,myAskList,completedTrade,tmpTime,strategyTrade);

						for(int i = 1; i <= tmpCount;i++){
							strategy.addTrade(completedTrade.get(completedTrade.size() - (tmpCount - i) - 1).getPrice());
							signalList = strategy.generateSignalList(myBidList, myAskList);
							strategyTrade = new LinkedList<ResultData>();
							OrderID = new LinkedList<Long>();
							while(!signalList.isEmpty()){
								tmpSignal = signalList.poll();
								currentID--;
								if(tmpSignal.getType().equalsIgnoreCase("sell")){
									count++;
									myAskList.add(currentID,tmpSignal.getPrice(),tmpSignal.getQuantity(),tmpTime);
									askFirstList.add(new GraphData(myAskList.get(0).getPrice(),tmpTime));
									strategyAsk.add(new GraphData(tmpSignal.getPrice(),tmpTime));
									matchTrade(myBidList,myAskList,completedTrade,tmpTime,strategyTrade);
									sellOrderID.add(currentID);
								}else if (tmpSignal.getType().equalsIgnoreCase("buy")){
									count++;
									myBidList.add(currentID,tmpSignal.getPrice(),tmpSignal.getQuantity(),tmpTime);
									bidFirstList.add(new GraphData(myBidList.get(0).getPrice(),tmpTime));
									strategyBid.add(new GraphData(tmpSignal.getPrice(),tmpTime));
									matchTrade(myBidList,myAskList,completedTrade,tmpTime,strategyTrade);
									buyOrderID.add(currentID);
								}
								OrderID.add(currentID);
							}
							strategyCount += strategyTrade.size();
							strategy.getSTrade(strategyTrade);
							strategy.getReceiptList(OrderID);
						}
					}
				}else if (tmp.equalsIgnoreCase("AMEND")){
					updateLines++;
					tmpPrice = set.getInt(6);
					tmpVol = set.getInt(7);
					if(tmpType.equalsIgnoreCase("B")){
						tmpID = set.getLong(12);
						myBidList.update(tmpID,tmpPrice,tmpVol,tmpTime);
					}else if(tmpType.equalsIgnoreCase("A")){
						tmpID = set.getLong(13);
						myAskList.update(tmpID,tmpPrice,tmpVol,tmpTime);
					}
				}else if (tmp.equalsIgnoreCase("DELETE")){
					deleteLines++;
					if(tmpType.equalsIgnoreCase("B")){
						tmpID = set.getLong(12);
						myBidList.deleteOne(tmpID);
					}else if(tmpType.equalsIgnoreCase("A")){
						tmpID = set.getLong(13);
						myAskList.deleteOne(tmpID);
					}
				}
				count++;
			};
			for(int i = 0; i < strategyTrade.size();i++){
				System.out.println("strategyTrade(" + i +") - Bid: " + strategyTrade.get(i).getBuyID() + " Ask: " 
						+ strategyTrade.get(i).getAskID() + " Price: " + strategyTrade.get(i).getPrice());
			}

			LinkedList<resultObjectL> listOfResult;
			System.out.println("listOfAllReciept size: " + strategy.getReceiptLength());
			listOfResult = strategy.getResultListFromStrategy();

			System.out.println("listOfResult size: " + listOfResult.size());

			Mainmenu.console.append("Total lines read : " + count + "\n");
			Mainmenu.console.append("Total lines matched : " + completedTrade.size() + " == tmpCount: " + tmpCount + "\n");
			Mainmenu.console.append("Total lines update : " + updateLines + "\n");
			Mainmenu.console.append("Total lines delete : " + deleteLines + "\n");
			Mainmenu.console.append("Total Bid Error : " + myBidList.getError() + " Total Ask Error: " 
					+ myAskList.getError()+ "\n");
			Mainmenu.console.append("bid list contains " +  myBidList.getLength() + ".\n");
			Mainmenu.console.append("ask list contains " +  myAskList.getLength() + ".\n");

			resultObjectL tempResult;
			double endResult = 0;
			double endCount = 0;
			while(!listOfResult.isEmpty()){
				tempResult = listOfResult.poll();
				if(tempResult.getPercentage() != Double.NEGATIVE_INFINITY){
					endResult += tempResult.getPercentage();
					endCount++;
				}
			}

			//update jlabels
			strategyResult.add(Integer.toString( count));
			strategyResult.add(Integer.toString( completedTrade.size()));
			strategyResult.add(Integer.toString( strategyCount));
			strategyResult.add(Integer.toString( updateLines));
			strategyResult.add(Integer.toString( deleteLines));
			strategyResult.add(Integer.toString( myBidList.getLength()));
			strategyResult.add(Integer.toString( myAskList.getLength()));
			strategyResult.add(Double.toString(endResult/endCount));

			set.close();
		} catch (SQLException e) {
			System.out.println("In Mainmenu/runStrategy : " + e);
		}
	}

	private int matchTrade(MyBidList myBidList, MyAskList myAskList, LinkedList<ResultData> completedTrade, Time tmpTime, LinkedList<ResultData> strategyTrade) {
		int numberOfTrade = 0;
		if(myAskList.getLength() > 0 && myBidList.getLength() > 0){
			OneData firstInAsk = myAskList.get(0);
			OneData firstInBid = myBidList.get(0);

			if(firstInBid.getPrice() >= firstInAsk.getPrice()){
				ResultData tmpResultData;
				if(firstInBid.getVol() > firstInAsk.getVol()){
					tmpResultData = new ResultData(firstInBid.getID(),firstInAsk.getID(),firstInAsk.getPrice(),firstInAsk.getVol(),tmpTime);
					completedTrade.add(tmpResultData);
					numberOfTrade++;
					if(firstInBid.getID() < 0 || firstInAsk.getID() < 0){
						strategyTrade.add(tmpResultData);
					}
					myBidList.updateFirst(firstInBid.getVol() - firstInAsk.getVol());
					myAskList.deleteAtIndex(0);
					numberOfTrade += matchTrade(myBidList,myAskList,completedTrade,tmpTime,strategyTrade);
				}else if(firstInBid.getVol() == firstInAsk.getVol()){
					tmpResultData = new ResultData(firstInBid.getID(),firstInAsk.getID(),firstInAsk.getPrice(),firstInBid.getVol(),tmpTime);
					completedTrade.add(tmpResultData);
					numberOfTrade++;
					if(firstInBid.getID() < 0 || firstInAsk.getID() < 0){
						strategyTrade.add(tmpResultData);
					}
					myAskList.deleteAtIndex(0);
					myBidList.deleteAtIndex(0);
				}else{
					tmpResultData = new ResultData(firstInBid.getID(),firstInAsk.getID(),firstInAsk.getPrice(),firstInBid.getVol(),tmpTime);
					completedTrade.add(tmpResultData);
					numberOfTrade++;
					if(firstInBid.getID() < 0 || firstInAsk.getID() < 0){
						strategyTrade.add(tmpResultData);
					}
					myAskList.updateFirst(firstInAsk.getVol() - firstInBid.getVol());
					myBidList.deleteAtIndex(0);
				}
			}
		}
		return numberOfTrade;
	}

	public void insertBidList(MyBidList myBidList, MyAskList myAskList,
			LinkedList<ResultData> completedTrade, double tmpPrice, int tmpVol,
			long tmpID, Time tmpTime) {
		if(myAskList.getLength() > 0){
			long tmpAskFirstID = myAskList.get(0).getID();
			double tmpAskFirstPrice = myAskList.get(0).getPrice();
			int tmpAskFirstVol = myAskList.get(0).getVol();
			Time tmpAskFirstTime = myAskList.get(0).getTime();
			double finishTime = tmpTime.getHours() + (tmpTime.getMinutes()/60.0);
			if(tmpPrice >= tmpAskFirstPrice){
				if(tmpVol > tmpAskFirstVol){
					completedTrade.add(new ResultData(tmpID,tmpAskFirstID,tmpAskFirstPrice,tmpAskFirstVol,tmpTime));
					myAskList.deleteAtIndex(0);
					tmpAskFirstVol = tmpVol - tmpAskFirstVol;

					//series3.add(finishTime,tmpPrice);
					insertBidList(myBidList, myAskList,completedTrade, tmpPrice, tmpAskFirstVol ,tmpID, tmpTime);
				}else if(tmpVol == tmpAskFirstVol){
					completedTrade.add(new ResultData(tmpID,tmpAskFirstID,tmpAskFirstPrice,tmpVol,tmpTime));
					myAskList.deleteAtIndex(0);
					//series3.add(finishTime,tmpPrice);
				}else{
					completedTrade.add(new ResultData(tmpID,tmpAskFirstID,tmpAskFirstPrice,tmpVol,tmpTime));
					tmpAskFirstVol -= tmpVol;
					myAskList.updateFirst(tmpAskFirstVol);
					//series3.add(finishTime,tmpPrice);
				}
			}else{
				myBidList.add(tmpID,tmpPrice,tmpVol,tmpTime);
			}
		}else{
			myBidList.add(tmpID,tmpPrice,tmpVol,tmpTime);
		}
	}

	public void insertAskList(MyBidList myBidList, MyAskList myAskList,
			LinkedList<ResultData> completedTrade, double tmpPrice, int tmpVol,
			long tmpID, Time tmpTime) {
		if(myBidList.getLength() > 0){
			long tmpBidFirstID = myBidList.get(0).getID();
			double tmpBidFirstPrice = myBidList.get(0).getPrice();
			int tmpBidFirstVol = myBidList.get(0).getVol();
			Time tmpBidFirstTime = myBidList.get(0).getTime();
			double finishTime = tmpTime.getHours() + (tmpTime.getMinutes()/60.0);
			if(tmpPrice <= tmpBidFirstPrice){
				if(tmpVol > tmpBidFirstVol){
					completedTrade.add(new ResultData(tmpBidFirstID,tmpID,tmpPrice,tmpBidFirstVol,tmpTime));
					tmpBidFirstVol = tmpVol - tmpBidFirstVol;
					myBidList.deleteAtIndex(0);
					insertBidList(myBidList, myAskList,completedTrade, tmpPrice,tmpBidFirstVol ,tmpID, tmpTime);
				}else if(tmpVol == tmpBidFirstVol){
					completedTrade.add(new ResultData(tmpBidFirstID,tmpID,tmpPrice,tmpVol,tmpTime));
					myBidList.deleteAtIndex(0);
				}else{
					completedTrade.add(new ResultData(tmpBidFirstID,tmpID,tmpPrice,tmpVol,tmpTime));
					tmpBidFirstVol -= tmpVol;
					myBidList.updateFirst(tmpBidFirstVol);
				}
			}else{
				myAskList.add(tmpID,tmpPrice,tmpVol,tmpTime);
			}
		}else{
			myAskList.add(tmpID,tmpPrice,tmpVol,tmpTime);
		}
	}


}
