package Selecting_Algothrim;


//import CoupleReciept;
//import resultObjectL;

import java.sql.Time;
import java.util.LinkedList;

import Trading_Engine.ResultData;
public class EvaluatorLec {

	/*
	public EvaluatorLec(lecMS lecMs, LinkedList<TradeObject> aTradeList){
		listOfCoupleReciept = lecMs.getAllReciept();
		this.tradeList = aTradeList;
	}
	*/
	
	public EvaluatorLec(lecMS strategy, LinkedList<ResultData> completedTrade) {
		listOfCoupleReciept = strategy.getAllReciept();
		
		tradeList = completedTrade;
		ListOfResultsFromTrade = strategy.getResultListFromStrategy();
	}


	public LinkedList<resultObjectL> run(){
		
		/*
		ResultData tempTrade; 
		LinkedList<resultObjectL> results = new LinkedList<resultObjectL>();

		
		int count = 0;
		
		while (  count < tradeList.size()){
			//System.out.println("count: " + count);
			tempTrade = tradeList.get(count);
			count++;
			//tempTrade = tradeList.poll();
			for ( CoupleReciept tempCR : listOfCoupleReciept){
				//System.out.println("ARMINNNNNNNNNNNNNNNNNNn");
				// printing something
				//for (long number : tempCR.geSellList()){
					//System.out.println("Sell ID for stragey :"  + number);
				//}
				//System.out.println("--------------------------------- TRADE ID: " + tempTrade.getAskID());
				
				
				
				if ( tempCR.geSellList().indexOf(tempTrade.getAskID()) != -1){ // here we found that  sell belongs to us
					
					//System.out.println("I FOUND SOMETHING&&&&&&&&7777777777777777777&&&&&&&&&&");
					
					tempCR.addToCredit(tempTrade.getPrice());
					tempCR.addToTime(tempTrade.getTime());
				}
				else if ( tempCR.getBuyList().indexOf(tempTrade.getBuyID()) != -1){
					tempCR.addToDebit(tempTrade.getPrice());
					tempCR.addToDebit(tempTrade.getPrice());
					tempCR.addToTime(tempTrade.getTime());
					//System.out.println("I FOUND SOMETHING#############################################");

				}
				//else it doesn't belong to us we don't care 
			}
		}
				

		for ( CoupleReciept CR : listOfCoupleReciept ){
			int numberOFTrade = CR.geSellList().size() + CR.getBuyList().size();
			double moneyGet = CR.getCredit();
			double moneyPaid = CR.getDebit();
			long tempLong = CR.getTime().getTime();
			tempLong = ( long) (tempLong/numberOFTrade);
			Time averageTime = new Time(tempLong);
			// this is where we have to work more
			results.add( new resultObjectL ( averageTime , ((moneyGet - moneyPaid)/ moneyPaid) ));
		}
		
			
		//return results;  chenged part
		 *
		 */
		return ListOfResultsFromTrade;
	}
			
			
	

	
	public LinkedList<CoupleReciept> getCR(){
		return listOfCoupleReciept;
		
	}
	private LinkedList<resultObjectL> ListOfResultsFromTrade;
	private LinkedList<ResultData> tradeList;
	//private LinkedList<TradeObject> tradeList;
	private LinkedList<CoupleReciept> listOfCoupleReciept;
}
