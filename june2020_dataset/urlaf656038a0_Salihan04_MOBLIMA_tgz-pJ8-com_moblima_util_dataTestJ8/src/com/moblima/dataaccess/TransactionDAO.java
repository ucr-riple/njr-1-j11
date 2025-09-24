package com.moblima.dataaccess;

import com.moblima.model.Transaction;
import java.util.List;

public interface TransactionDAO {
	public void createTransaction(Transaction transaction);
	public void updateTransaction(Transaction transaction);
	public void deleteTransaction(Transaction transaction);
	
	public Transaction getTransaction(String tid);
	public List<Transaction> getTransactions();
}