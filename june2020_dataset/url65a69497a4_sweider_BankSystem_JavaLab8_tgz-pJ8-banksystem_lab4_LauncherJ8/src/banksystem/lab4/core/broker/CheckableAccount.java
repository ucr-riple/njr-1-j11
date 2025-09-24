/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package banksystem.lab4.core.broker;

import banksystem.lab4.core.account.AccountProxy;
import banksystem.lab4.core.account.ISummaryCheckerAccess;
import banksystem.lab4.core.account.ITransactionAccess;
import banksystem.lab4.core.moneyamount.MoneyAmount;

/**
 *
 * @author alex
 */
public class CheckableAccount implements ITransactionAccess, ISummaryCheckerAccess{    
    
    public static enum AccountState{
        CHECKED,
        UNCHECKED,
        BUSY
    }
    
    private final ITransactionAccess transactionInterface;
    private final ISummaryCheckerAccess summaryCheckerInterface;
    private AccountState accountState;
    
    

    public CheckableAccount(AccountProxy accountProxy) {
        this.transactionInterface=accountProxy.getTransactionInterface();
        this.summaryCheckerInterface=accountProxy.getSummaryCheckerInterface();
        
        
        this.accountState = AccountState.UNCHECKED;
    }
       
   @Override
    public void deposite(MoneyAmount moneyToDeposite) {
       this.transactionInterface.deposite(moneyToDeposite);
    }

    @Override
    public void withdraw(MoneyAmount moneyToWithdraw) {
        this.transactionInterface.withdraw(moneyToWithdraw);
    }

    @Override
    public MoneyAmount getAccountSummary() {
        return this.summaryCheckerInterface.getAccountSummary();
    }

    
    public void setState(AccountState state){
        this.accountState = state;
    }

    public AccountState getAccountState() {
        return accountState;
    }
    
    
}
