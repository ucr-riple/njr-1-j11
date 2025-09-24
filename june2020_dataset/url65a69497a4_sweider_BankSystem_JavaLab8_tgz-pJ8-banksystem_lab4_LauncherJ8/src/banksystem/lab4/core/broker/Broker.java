/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package banksystem.lab4.core.broker;

import banksystem.lab4.core.account.AccountProxy;
import banksystem.lab4.core.bank.IBank;
import banksystem.lab4.core.moneyamount.MoneyAmount;
import banksystem.lab4.core.transaction.Transaction;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 *
 * @author alex
 */
public class Broker implements IBroker{
    private final TransactionExecutor transactionExecutor;
    private final Map<Integer, CheckableAccount> accounts;

    public Broker(IBank bank) {
        Map<Integer, CheckableAccount> map = new HashMap<>();
        this.accounts = (Map<Integer, CheckableAccount>) Collections.synchronizedMap(map);
        initiateAccounts(bank);
        
        this.transactionExecutor = new TransactionExecutor(this);
    }

    @Override
    public synchronized boolean execute(Transaction transaction) {
        boolean executed = this.transactionExecutor.execute(transaction);
        return executed;
    }

    @Override
    public synchronized boolean uncheckAll() {
        for(CheckableAccount account : this.accounts.values()){
            account.setState(CheckableAccount.AccountState.UNCHECKED);
        }
        return true;
    }

    @Override
    public synchronized MoneyAmount getAmountAndSetChecked(int accountId) {
        CheckableAccount workingAccount = this.accounts.get(accountId);
        workingAccount.setState(CheckableAccount.AccountState.CHECKED);
        MoneyAmount result = workingAccount.getAccountSummary();
        return result;
    }
    
    CheckableAccount.AccountState getAccountStateById(int accountId){
        return this.accounts.get(accountId).getAccountState();
    }
    
    CheckableAccount getAccountById(int id){
        return this.accounts.get(id);
    }
    
    private void initiateAccounts(IBank bank) {
        for(Entry<Integer, AccountProxy> entry : bank.getAccountProxies().entrySet()){
            this.accounts.put(entry.getKey(), new CheckableAccount(entry.getValue()));
        }
    }
}
