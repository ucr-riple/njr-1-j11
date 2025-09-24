/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package banksystem.lab4;

import banksystem.lab4.cashier.Cashier;
import banksystem.lab4.cashier.CashierFactory;
import banksystem.lab4.client.Client;
import banksystem.lab4.client.ClientFactory;
import banksystem.lab4.core.account.Account;
import banksystem.lab4.core.bank.Bank;
import banksystem.lab4.core.broker.Broker;
import banksystem.lab4.core.moneyamount.MoneyAmount;
import banksystem.lab4.core.summaryChecker.SummaryChecker;
import banksystem.lab4.core.transaction.TransactionQueue;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.xml.DOMConfigurator;

/**
 *
 * @author alex
 */
public class Launcher {
    private static final int ACCOUNT_MONEY = 123500;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        DOMConfigurator.configure("./log4j.xml");  
        Bank bank = new Bank();
        initializeBank(bank);
       
        System.out.println("Bank initialized");
        
        Broker broker = new Broker(bank);
        System.out.println("Broker initialized");
        
        
        SummaryChecker daemonCalculator = new SummaryChecker(bank, broker);
        Thread daemonThread = new Thread(daemonCalculator);
        daemonThread.setDaemon(true);
        daemonThread.setName("daemon-calculator");
        daemonThread.start();
        
        System.out.println("Daemon started");
        
        TransactionQueue transactionQueue = new TransactionQueue(broker);
        Thread transactionQueueThread = new Thread(transactionQueue);
        transactionQueueThread.setName("TransactionQueue");
        transactionQueueThread.start();
        
        System.out.println("transaction queue started");
        
        CashierFactory cashierFactory = new CashierFactory(transactionQueue, bank);
        List<Cashier> cashiersList = new ArrayList<>();
        int i = 0;
        for(i = 0; i < 30; i++) { cashiersList.add(cashierFactory.newCashier()); }
        
        ClientFactory clientsFactory = new ClientFactory(bank.getIdList(), cashiersList);
    
        for(i = 0; i < 30; i++) {
            Client client = clientsFactory.getNewClient();
            Thread clientThread = new Thread(client);
            clientThread.setName("Client#" + client.getId());
            clientThread.start();
        } 
    }

    private static void initializeBank(Bank bank) {
        for(int i = 0; i < 50; i++){
            int accountId = bank.generateNewAccountId();
            MoneyAmount money = new MoneyAmount(Launcher.ACCOUNT_MONEY);
            Account account = new Account(accountId, money);
            bank.addNewAccount(account);
        }
    }
    
}
