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
public class SummaryCheckerAccess implements ISummaryCheckerAccess{

    private final Account account;

    public SummaryCheckerAccess(Account account) {
        this.account = account;
    }
    
    @Override
    public MoneyAmount getAccountSummary() {
        return this.account.getAccountSummary();
    }
    
}
