/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package banksystem.lab4.core.broker;

import banksystem.lab4.core.moneyamount.MoneyAmount;
import banksystem.lab4.core.transaction.Transaction;

/**
 *
 * @author alex
 */
public interface IBroker {
    boolean execute(Transaction transaction);
    boolean uncheckAll();
    MoneyAmount getAmountAndSetChecked(int accountId);
}
