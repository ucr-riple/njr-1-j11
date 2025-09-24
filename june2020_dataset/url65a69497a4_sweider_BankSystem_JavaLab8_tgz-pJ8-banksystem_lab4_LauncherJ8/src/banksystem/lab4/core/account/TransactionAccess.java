/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package banksystem.lab4.core.account;

import banksystem.lab4.core.moneyamount.MoneyAmount;

/**
 *
 * @author alex
 */
public class TransactionAccess implements ITransactionAccess{
    
    private final Account account;

    public TransactionAccess(Account account) {
        this.account = account;
    }

    @Override
    public void deposite(MoneyAmount moneyToDeposite) {
        this.account.deposite(moneyToDeposite);
    }

    @Override
    public void withdraw(MoneyAmount moneyToWithdraw) {
        this.account.withdraw(moneyToWithdraw);
    }
    
}
