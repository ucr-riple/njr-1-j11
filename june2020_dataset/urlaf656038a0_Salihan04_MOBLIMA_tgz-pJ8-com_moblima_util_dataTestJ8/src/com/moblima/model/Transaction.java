package com.moblima.model;

import java.io.Serializable;
import java.util.Date;

public class Transaction implements Serializable {
	private String tid;
	private Float amount;
	private Date date;
	private TransactionMethod transactionMethod;
	
	public Transaction(String tid, float amount, Date date, TransactionMethod transactionMethod) {
		this.tid = tid;
		this.amount = amount;
		this.date = date;
		this.transactionMethod = transactionMethod; 
	}
	
	public String getTID() {
		return tid;
	}
	
	public float getAmount() {
		return amount;
	}
	
	public void setAmount(float amount) {
		this.amount = amount;
	}
	
	public Date getDate() {
		return date;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}
	
	public TransactionMethod getTransactionMethod() {
		return transactionMethod;
	}
	
	public void setTransactionMethod(TransactionMethod transactionMethod) {
		this.transactionMethod = transactionMethod;
	}
}
