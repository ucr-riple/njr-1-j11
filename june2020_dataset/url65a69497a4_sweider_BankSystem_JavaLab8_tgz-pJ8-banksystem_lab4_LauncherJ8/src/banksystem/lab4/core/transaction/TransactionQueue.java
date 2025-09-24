/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package banksystem.lab4.core.transaction;

import banksystem.lab4.core.broker.IBroker;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 *
 * @author alex
 */
public class TransactionQueue implements Runnable{
    private static final Logger logger = LogManager.getLogger(TransactionQueue.class);
    
    private static final int SLEEP_TIME = 500;
    private final List<Transaction> mainTransactionsQueue;
    private final List<Transaction> bufferTransactionQueue;
    private final List<Transaction> executedTransactions;
    private final IBroker broker;

    public TransactionQueue(IBroker broker) {
        List<Transaction> list = new ArrayList<>();
        List<Transaction> list2 = new ArrayList<>();
        this.mainTransactionsQueue = Collections.synchronizedList(list);
        this.bufferTransactionQueue = Collections.synchronizedList(list2);
        this.executedTransactions = new ArrayList<>();
        this.broker = broker;
    }
    
    public synchronized void add(Transaction transaction){
        this.bufferTransactionQueue.add(transaction);
        logger.info("Transaction added to buffer");
    }
    
    private synchronized void updateFromBuffer(){
        this.mainTransactionsQueue.addAll(this.bufferTransactionQueue);
        this.bufferTransactionQueue.clear();
    }
    @Override
    public void run() {        
        while(true){
            this.waitForTransactions();
            this.processExistingTransactions();
            this.removeExecutedTransactions();
            this.updateFromBuffer();
        }
    }

    private void waitForTransactions() {
        while(this.mainTransactionsQueue.isEmpty()){
            try {
                Thread.sleep(TransactionQueue.SLEEP_TIME);
                this.updateFromBuffer();
            } catch (InterruptedException ex) { ex.printStackTrace(); }
        }
    }

    private void processExistingTransactions() {
        for(Transaction t : this.mainTransactionsQueue){
            boolean executed = this.broker.execute(t);
            if (executed) {
                this.executedTransactions.add(t);
                logger.info("Transaction#" + t + " invoked");
            }
        }
    }

    private void removeExecutedTransactions() {
        for(Transaction t : this.executedTransactions){
            this.mainTransactionsQueue.remove(t);
        }
    }
    
}
