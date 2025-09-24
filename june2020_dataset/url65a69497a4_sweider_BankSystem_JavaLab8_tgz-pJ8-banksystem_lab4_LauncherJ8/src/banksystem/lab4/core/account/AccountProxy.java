/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package banksystem.lab4.core.account;

import java.util.concurrent.Semaphore;

/**
 *
 * @author alex
 */
public class AccountProxy implements IAccountProxy {
    
    private final Account account;
    
    
    private final ITransactionAccess transactionInterface;
    private final ICashierAccess cashierInterface;
    private final ISummaryCheckerAccess summaryCheckerInterface;
    
    private final Semaphore cashierInterfaceSemaphore;
    
    
    
    public AccountProxy(Account account){
        this.account=account;
        this.transactionInterface = new TransactionAccess(this.account);
        this.cashierInterface = new CashierAccess(this.account);
        this.summaryCheckerInterface = new SummaryCheckerAccess(this.account);
        this.cashierInterfaceSemaphore=new Semaphore(1);
        
    }
    
    @Override
    public ITransactionAccess getTransactionInterface(){
        return this.transactionInterface;
    }
    
    @Override
    public ICashierAccess acquireCashierInterface(){
        try {
            this.cashierInterfaceSemaphore.acquire();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
            this.cashierInterfaceSemaphore.release();
        }
       return this.cashierInterface;
    }
    
    @Override
    public void releaseCashierInterface(){
        this.cashierInterfaceSemaphore.release();
    }

    @Override
    public ISummaryCheckerAccess getSummaryCheckerInterface() {
       return this.summaryCheckerInterface;
    }
 
}
