/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package banksystem.lab4.client;

import banksystem.lab4.cashier.Cashier;
import banksystem.lab4.core.moneyamount.MoneyAmount;
import java.util.Collection;
import java.util.Random;
import java.util.logging.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 *
 * @author alex
 */
public class Client implements Runnable{
    private static final Logger logger = LogManager.getLogger(Client.class); 
    
    private final int id;
    private final Collection<Integer> accounts;
    private final Cashier cashier;

    public Client(int id, Collection<Integer> accounts, Cashier cashier) {
        this.id = id;
        this.accounts = accounts;
        this.cashier = cashier;
    }

    public int getId() {
        return id;
    }

    @Override
    public void run() {
        Random rand = new Random();
        logger.info("Start invocation");
        
        occupyCashier();
        logger.info("Cashier occupied");
        
        for(Integer accountId : this.accounts){
            boolean started = startWorkWithAccount(accountId);
            if (!started) { continue; }
            logger.info("Work with Account#" + accountId + " started");
            
            transferMoney(rand);
           
            cashier.finishWorkWithAccount(this.id);
            logger.info("Work with Account#" + accountId + " finished");
        }
        
        cashier.releaseCashier(this.id);
        logger.info("Cashier released");
        
        logger.info("Finish invokation");
    }

    private boolean transferMoney(Random rand) {
        int value = rand.nextInt();
        MoneyAmount moneyToTransfer = new MoneyAmount(value);
        
        Cashier.TransferMoneyToAccountOperationResult result;
         
        do{
            result = cashier.transferMoneyToAccount(this.id,this.accounts.iterator().next() ,
                    moneyToTransfer);
            
            switch (result) {
                case ACCOUNT_NOT_SETTED: {
                    logger.warn("Account not setted!");
                    return false;
                } 
                
                case CASHIER_IS_NOT_OCCUPIED: logger.warn("Cashier isn't occupied");return false;
                case CASHIER_IS_BUSY: logger.warn("Cashier work with other."); return false;
                case INVALID_RECIEVER_ID: logger.warn("Invalid reciever id"); return false;
                case TRANSACTION_CREATED: logger.info("Successfully created transaction about " + value +"$.");break;
                case NOT_ENOUGH_MONEY: {
                    logger.info("Not enough money to transfer "+value+"$. Trying other value.");
                    value = rand.nextInt();
                    moneyToTransfer = new MoneyAmount(value);
                } break; 
            }
     
        }while(result != Cashier.TransferMoneyToAccountOperationResult.TRANSACTION_CREATED);
        return true;
    }

    private boolean startWorkWithAccount(Integer accountId) {
        logger.debug("Try to start work with account#" + accountId);
        Cashier.StartWorkWithAccountOperationResult result = cashier.startWorkWithAccount(this.id, accountId);
        if (result == Cashier.StartWorkWithAccountOperationResult.ACCOUNT_NOT_FOUND) {
            logger.warn("Account#" + accountId + " was not found");
            return false;
        }
        
        while(result != Cashier.StartWorkWithAccountOperationResult.SUCCESSFULLY_STARTED){
            switch (result) {
                case CASHIER_IS_BUSY: logger.warn("Cashier work with other");break;
                case CASHIER_IS_NOT_OCCUPIED: logger.info("Cashier is not occupied"); break;

                default:  {
                    logger.warn("Other case: " + result);
                    this.occupyCashier();
                }
            }
            result = cashier.startWorkWithAccount(this.id, accountId);            
        }
        return true;
    }

    private void occupyCashier() {
        boolean occupied = cashier.occupyCashier(this.id);
        
        while(occupied == false){
            logger.info("Cashier is busy. Waiting.");
            try { Thread.sleep(200); } catch (InterruptedException ex) { logger.error(null, ex); }
            occupied = cashier.occupyCashier(this.id);;
        }
    }
    
    
}
