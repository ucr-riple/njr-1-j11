/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package banksystem.lab4.core.bank;

import banksystem.lab4.core.account.Account;
import banksystem.lab4.core.account.AccountProxy;
import banksystem.lab4.core.moneyamount.MoneyAmount;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author alex
 */
public class Bank implements IBank{

    private int nextAllowedId;
    private final Map<Integer, AccountProxy> accounts;
    private MoneyAmount bankSummary;
    
    public Bank(){
        this.nextAllowedId=1;
        this.accounts=(Map<Integer, AccountProxy>) Collections.synchronizedMap(new HashMap<Integer, AccountProxy>());
        this.bankSummary=new MoneyAmount(0);
    }
    
    @Override
    public AccountProxy getAccountProxy(int id) {
        if (!(this.accounts.containsKey(id))) return null;
        return this.accounts.get(id);
    }

    @Override
    public synchronized int generateNewAccountId() {
        int resultId = this.nextAllowedId;
        this.nextAllowedId++;
        return resultId;
    }

    @Override
    public synchronized void addNewAccount(Account newAccount) {
        this.accounts.put(newAccount.getId(), new AccountProxy(newAccount));
        this.bankSummary=this.bankSummary.add(newAccount.getAvailableMoney());
    }

    @Override
    public Map<Integer, AccountProxy> getAccountProxies() {
        return this.accounts;
    }

    @Override
    public MoneyAmount getSummary() {
        return this.bankSummary;
    }

    @Override
    public Collection<Integer> getIdList() {
        return this.accounts.keySet();
    }
    
}
