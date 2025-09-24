/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package banksystem.lab4.core.broker;

import banksystem.lab4.core.account.ITransactionAccess;
import banksystem.lab4.core.transaction.Transaction;

/**
 *
 * @author alex
 */
public class TransactionExecutor {
    private final Broker broker;

    
    
    public TransactionExecutor(Broker broker) {
        this.broker = broker;
    }
    
    
    public boolean execute(Transaction transaction){
        CheckableAccount.AccountState senderState = this.broker.getAccountStateById(transaction.getSenderId());
        CheckableAccount.AccountState recieverState = this.broker.getAccountStateById(transaction.getRecieverId());
        
        boolean isSameState = (senderState == recieverState);
        
        if(isSameState){
            ITransactionAccess reciever = this.broker.getAccountById(transaction.getRecieverId());
            reciever.deposite(transaction.getMoneyAmount());
            ITransactionAccess sender = this.broker.getAccountById(transaction.getSenderId());
            sender.withdraw(transaction.getMoneyAmount());
        }
        
        return isSameState;
    }
}
