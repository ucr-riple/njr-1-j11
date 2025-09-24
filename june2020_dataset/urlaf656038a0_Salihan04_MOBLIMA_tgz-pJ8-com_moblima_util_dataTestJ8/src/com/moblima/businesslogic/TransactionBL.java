package com.moblima.businesslogic;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.moblima.dataaccess.TransactionDAO;
import com.moblima.dataaccess.TransactionDAOImpl;
import com.moblima.model.Transaction;
import com.moblima.model.TransactionMethod;

public class TransactionBL {
	private TransactionDAO transactionDAO;
	
	public TransactionBL(){
		transactionDAO = TransactionDAOImpl.getInstance();
	}
	
	public Transaction createTransaction(String transactionID, float amount, TransactionMethod transactionMethod) {
		Date date = Calendar.getInstance().getTime();
		
		Transaction transaction = new Transaction(transactionID, amount, date, transactionMethod);
		transactionDAO.createTransaction(transaction);
		
		return transaction;
	}
	
//	public void updateTransaction(String transactionID, float amount, Date date,TransactionMethod transactionMethod){
//		Transaction transaction = transactionDAO.getTransaction(transactionID);
//		transaction.setTransactionID(transactionID);
//		transaction.setAmount(amount);
//		transaction.setDate(date);
//		transaction.setTransactionMethod(transactionMethod);
//		transactionDAO.updateTransaction(transaction);
//	}
	
//	public void removeTransaction(int transactionID){
//		Transaction transaction = transactionDAO.getTransaction(transactionID);
//		transactionDAO.deleteTransaction(transaction);
//	}
//	
//	public Transaction getTransaction(int transactionID){
//		return transactionDAO.getTransaction(transactionID);
//	}
	
	public List<Transaction> getTransactions(){
		return transactionDAO.getTransactions();
	}
}
