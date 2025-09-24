package com.moblima.dataaccess;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import com.moblima.model.MovieGoer;
import com.moblima.model.Transaction;

public class TransactionDAOImpl implements TransactionDAO {
	
	private static TransactionDAO transactionDAO;
	private SerializeDB serializeDB;
	private List<Transaction> transactions;
	
	public static TransactionDAO getInstance() {
		if(transactionDAO == null) {
			transactionDAO = new TransactionDAOImpl();
		}
		
		return transactionDAO;
	}
	
	private TransactionDAOImpl() {
		serializeDB = SerializeDB.getInstance();
		transactions = serializeDB.getTransactions();
	}
	
	public void createTransaction(Transaction transaction) {
		transactions.add(transaction);		
		
		serializeDB.saveData();//need to save other way. this is not efficient.
	}
	
	
	public void updateTransaction(Transaction transaction) {
		serializeDB.saveData();
	}
	
	public void deleteTransaction(Transaction transaction) {
		transactions.remove(transaction);
		serializeDB.saveData();
	}
	
	public Transaction getTransaction(String tid) {
		for(Transaction t: transactions) {
			if(t.getTID().equals(tid)) {
				return t;
			}
		}
		
		return null;
	}
	
	public List<Transaction> getTransactions() {
		return transactions;
	}
	
}
