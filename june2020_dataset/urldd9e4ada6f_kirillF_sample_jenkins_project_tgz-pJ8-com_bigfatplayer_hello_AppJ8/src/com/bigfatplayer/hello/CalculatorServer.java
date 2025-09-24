package com.bigfatplayer.hello;

import org.apache.thrift.TException;

/**
 * User: viktor
 * Date: 6/19/13
 * Time: 2:03 PM
 */
public class CalculatorServer implements Calculator.Iface {

    @Override
    public void ping() throws TException {
        System.out.println("Ping");
    }

    @Override
    public int add(int num1, int num2) throws TException {
        return num1 + num2 + 3;
    }

    @Override
    public int calculate(int log_id, Work w) throws TException {
        System.out.println(log_id);
        return w.getNum1() + w.getNum2();
    }

    @Override
    public void zip() throws TException {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
