/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package banksystem.lab4.core.summaryChecker;

import banksystem.lab4.core.bank.IBank;
import banksystem.lab4.core.broker.IBroker;
import banksystem.lab4.core.moneyamount.MoneyAmount;
import java.util.Collection;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 *
 * @author alex
 */
public class SummaryChecker implements Runnable{
    private static final Logger logger = LogManager.getLogger(SummaryChecker.class);
    private final Collection<Integer> accountList;
    private final IBroker accountBroker;
    private final MoneyAmount summary;
    
    public SummaryChecker(IBank bank, IBroker broker){
        this.accountBroker=broker;
        this.accountList=bank.getIdList();
        this.summary=bank.getSummary();
    }

    @Override
    public void run() {
        MoneyAmount summ;
        while(true){
            summ = new MoneyAmount(0);            
            this.accountBroker.uncheckAll();
            
            for (Integer currenAccount : this.accountList){
                summ=summ.add(this.accountBroker.getAmountAndSetChecked(currenAccount.intValue()));
            }
            
            if (summ.compareTo(this.summary)==0){
                logger.info("Summary is valid.");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    logger.error("Interrupted", ex);
                }
            }
            else{
                
            }
            
        }
    }
    
    
}
