/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package banksystem.lab4.cashier;

import banksystem.lab4.core.bank.IBank;
import banksystem.lab4.core.transaction.TransactionQueue;

/**
 *
 * @author alex
 */
public class CashierFactory {
    
    private final TransactionQueue transactionQueue;
    private final IBank bank;

    public CashierFactory(TransactionQueue transactionQueue, IBank bank) {
        this.transactionQueue = transactionQueue;
        this.bank = bank;
    }
    
    public Cashier newCashier(){
        return new Cashier(bank, transactionQueue);
    }
    
    
}
