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
import java.util.Map;

/**
 *
 * @author alex
 */
public interface IBank {
    
    public AccountProxy getAccountProxy(int id);
    
    public int generateNewAccountId();
    
    public void addNewAccount(Account newAccount);
    
    public Map<Integer, AccountProxy> getAccountProxies(); 
    
    public MoneyAmount getSummary();
    
    public Collection<Integer> getIdList();
    
}
