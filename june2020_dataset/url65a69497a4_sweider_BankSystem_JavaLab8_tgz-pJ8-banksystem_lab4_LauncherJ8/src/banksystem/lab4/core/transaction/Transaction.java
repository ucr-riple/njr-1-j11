/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package banksystem.lab4.core.transaction;

import banksystem.lab4.core.moneyamount.MoneyAmount;

/**
 *
 * @author alex
 */
public class Transaction {
    private final MoneyAmount moneyAmount;
    private final int senderId;
    private final int recieverId;

    public Transaction(MoneyAmount moneyAmount, int senderId, int recieverId) {
        this.moneyAmount = moneyAmount;
        this.senderId = senderId;
        this.recieverId = recieverId;
    }

    public MoneyAmount getMoneyAmount() {
        return moneyAmount;
    }

    public int getSenderId() {
        return senderId;
    }

    public int getRecieverId() {
        return recieverId;
    }
    
    
}
