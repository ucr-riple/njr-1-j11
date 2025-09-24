package be.demmel.fun.jgwcaconstants;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ShutdownHook implements Runnable {

    private static final Logger LOG = LoggerFactory.getLogger(ShutdownHook.class);

    @Override
    public void run() {
        Thread.currentThread().setName("Shutdown");
        try {
            LOG.info("The \"UCP and SMMP Proxy\" is shutting down");
            // Do whatever has to be done here to cleanup the module before complete shutdown
            LOG.info("The \"UCP and SMMP Proxy\" shutted down");
        } catch (Throwable throwable) {
            LOG.error("The \"UCP and SMMP Proxy\" shutdown encountered a problem: ", throwable);
        }
    }
}
