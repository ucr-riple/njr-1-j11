/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package banksystem.lab4.core.account;

/**
 *
 * @author alex
 */
public interface IAccountProxy {

    ICashierAccess acquireCashierInterface();

    ITransactionAccess getTransactionInterface();
    
    ISummaryCheckerAccess getSummaryCheckerInterface();

    void releaseCashierInterface();
    
}
