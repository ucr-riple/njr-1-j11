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
public class Account {
    
    private final int id;
    
    private MoneyAmount availableMoneyAmount;
    private MoneyAmount reservedMoneyAmount;
    
    
    public Account(int id, MoneyAmount availableMoney){
        this.id=id;
        this.availableMoneyAmount=availableMoney;
        this.reservedMoneyAmount = new MoneyAmount(0);
    }

    public int getId(){
        return this.id;
    }
    
    public MoneyAmount getAvailableMoney(){
        return this.availableMoneyAmount;
    }
    
    public MoneyAmount getAccountSummary(){
        return this.availableMoneyAmount.add(this.reservedMoneyAmount);
    }
    
    /**
     * Пополняет основной (доступный клиенту) счёт
     * @param moneyToDeposite 
     */
    public synchronized void deposite(MoneyAmount moneyToDeposite){
        this.availableMoneyAmount = this.availableMoneyAmount.add(moneyToDeposite);
    }
    
    /**
     * Переводить часть денег с доступного счета на зарезервированный
     * @param moneyToReserve 
     */
    public synchronized void reserve(MoneyAmount moneyToReserve){
        this.availableMoneyAmount=this.availableMoneyAmount.substract(moneyToReserve);
        this.reservedMoneyAmount=this.reservedMoneyAmount.add(moneyToReserve);
    }
    
    /**
     * Списывает деньги с зарезервированного счёта
     * @param moneyToWithdraw 
     */
    public synchronized void withdraw(MoneyAmount moneyToWithdraw){
        this.reservedMoneyAmount = this.reservedMoneyAmount.substract(moneyToWithdraw);
    }
    
}
