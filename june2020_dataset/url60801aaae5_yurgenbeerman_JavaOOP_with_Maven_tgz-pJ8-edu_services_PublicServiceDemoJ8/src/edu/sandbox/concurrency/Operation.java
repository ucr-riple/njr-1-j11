package edu.sandbox.concurrency;

import javax.naming.InsufficientResourcesException;
import java.util.concurrent.TimeUnit;

/**
 * Created by yurii.pyvovarenko on 23.04.14.
 */
public class Operation {
    public static void main(String[] args) {
        final Account a = new Account(1000);
        final Account b = new Account(2000);
        System.out.println("START: Account a = " + a.getBalance() + ", Account b = " + b.getBalance());


        new Thread( new Runnable() {
            @Override
            public void run() {
                try {
                    transfer( a, b, 500);
                } catch (InsufficientResourcesException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        try {
            transfer( b, a, 300);
        } catch (InsufficientResourcesException e) {
            e.printStackTrace();
        }

//        try {
//            Thread.currentThread().sleep(1000);
//            System.out.println("After DELAY: Account a = " + a.getBalance() + ", Account b = " + b.getBalance());
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        System.out.println("FINISH: Account a = " + a.getBalance() + ", Account b = " + b.getBalance());
    }

    static void transfer(
            Account acc1, Account acc2, int amount)
            throws InsufficientResourcesException {
        final int WAIT_SEC = 2;

        if (acc1.getBalance() < amount)
            throw new InsufficientResourcesException();

// the next code leads to a deadlock:
//        synchronized (acc1) {
//            System.out.println("transfer of " + amount + ": synchronized (acc1)");
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//
//            synchronized (acc2) {
//                System.out.println("transfer of " + amount + ": synchronized (acc2)");
//                acc1.withdraw(amount);
//                acc2.deposit(amount);
//            }
//        }

//        boolean isLocked1 = false;
//        try {
//            isLocked1 = acc1.getLock().tryLock(WAIT_SEC, TimeUnit.SECONDS);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        try {
            if (acc1.getLock().tryLock(WAIT_SEC, TimeUnit.SECONDS)) {
                try {
                    if (acc2.getLock().tryLock(WAIT_SEC, TimeUnit.SECONDS))
                        try {
                            //System.out.println("Before transfer of " + amount + ": Account acc1 = " + acc1.getBalance() + ", Account acc2 = " + acc2.getBalance());
                            acc1.withdraw(amount);
                            acc2.deposit(amount);
                            //System.out.println("After transfer of " + amount + ": Account acc1 = " + acc1.getBalance() + ", Account acc2 = " + acc2.getBalance());
                            //System.out.println("transfer: tranfer of " + amount + " is done");
                        } finally {
                            acc2.getLock().unlock();
                        }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    acc1.getLock().unlock();
                }
            } else {
                System.out.println("transfer: tranfer of " + amount + " ERROR waiting lock!");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
