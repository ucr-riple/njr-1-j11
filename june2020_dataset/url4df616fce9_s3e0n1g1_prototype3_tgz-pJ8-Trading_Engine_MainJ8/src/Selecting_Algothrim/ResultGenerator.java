package Selecting_Algothrim;

import java.sql.Time;
import java.util.LinkedList;
import java.util.Random;
public class ResultGenerator {

	
	/**
	 * It just convert the result which we have in more readable thing from 
	 * 
	 * @param evaluator
	 */
	public ResultGenerator( EvaluatorLec strategy){
		CRList = strategy.getCR();		
	}
	
	public LinkedList<resultObjectL> getResultList(){
		
		LinkedList<resultObjectL> results = new LinkedList<resultObjectL>();
		Random ran = new Random();
		for ( CoupleReciept CR : CRList ){
			int numberOFTrade = CR.geSellList().size() + CR.getBuyList().size();
			double moneyGet = CR.getCredit();
			double moneyPaid = CR.getDebit();
			long tempLong = CR.getTime().getTime();
			tempLong = ( long) (tempLong/numberOFTrade);
			Time averageTime = new Time(tempLong);
				results.add( new resultObjectL ( averageTime, ran.nextDouble()-0.5));
		}
		
		return results;
	}
	
	
	
	private LinkedList<CoupleReciept> CRList;
}
