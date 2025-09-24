package com.realtimecep.scribe;

import com.facebook.fb303.fb_status;
import org.apache.thrift.TException;
import scribe.thrift.LogEntry;
import scribe.thrift.ResultCode;
import scribe.thrift.scribe;

import java.util.List;
import java.util.Map;

/**
 * Scribe Server Log Impl Class.
 */
public class ScribeLogImpl implements scribe.Iface {

    @Override
    public ResultCode Log(List<LogEntry> messages) throws TException {

        System.out.println("Log");

        System.out.println(messages.get(0));

        return ResultCode.OK;
    }

    @Override
    public String getName() throws TException {
        System.out.println("test");
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getVersion() throws TException {
        System.out.println("test");
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public fb_status getStatus() throws TException {
        System.out.println("test");
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getStatusDetails() throws TException {
        System.out.println("test");
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Map<String, Long> getCounters() throws TException {
        System.out.println("test");
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public long getCounter(String key) throws TException {
        System.out.println("test");
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setOption(String key, String value) throws TException {
        System.out.println("test");
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getOption(String key) throws TException {
        System.out.println("test");
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Map<String, String> getOptions() throws TException {
        System.out.println("test");
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getCpuProfile(int profileDurationInSec) throws TException {
        System.out.println("test");
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public long aliveSince() throws TException {
        System.out.println("test");
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void reinitialize() throws TException {
        System.out.println("test");
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void shutdown() throws TException {
        System.out.println("test");
        //To change body of implemented methods use File | Settings | File Templates.
    }
}

