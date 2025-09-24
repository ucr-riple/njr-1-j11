package edu.sandbox.concurrency;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by yurii.pyvovarenko on 23.04.14.
 */
public class Account {
    private volatile int balance;
    private volatile Lock lock;

    public Account(int initialBalance) {
        this.balance = initialBalance;
        this.lock = new ReentrantLock();
    }

    public void withdraw(int amount) {
        balance -= amount;
    }

    public void deposit(int amount) {
        balance += amount;
    }

    public int getBalance() {
        return balance;
    }

    public Lock getLock() {
        return lock;
    }
}
