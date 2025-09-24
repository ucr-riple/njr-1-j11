package be.demmel.fun.jgwcaconstants;

import be.demmel.fun.jgwcaconstants.bll.GWCAConnection;
import be.demmel.fun.jgwcaconstants.bll.GWCAOperations;
import be.demmel.fun.jgwcaconstants.bll.NamedPipeGWCAConnection;
import java.io.File;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This shows you how to use operations like the functions in the AutoIt version of the GWCAConstants.
 * If you want to know the advantages compared to the AutoIt version, see the <code>be.demmel.fun.jgwcaconstants.bll.GWCAOperations</code> class
 */
public class Tutorial2 {
    
    private static final Logger LOG = LoggerFactory.getLogger(MainTestingAllFunctions.class);

    public static void main(String... args) throws InterruptedException {

        GWCAConnection gwcaConnection = null;
        try {
            LOG.info("Executing \"Java GWCAConstants\" (version {})", Version.getVersion());

            //TODO: Fill in the PID here
            gwcaConnection = new NamedPipeGWCAConnection(new File("\\\\.\\pipe\\GWCA_" + 3100));
            gwcaConnection.open();

            GWCAOperations op = new GWCAOperations(gwcaConnection);
            
            //TODO: START code your program below this point
            LOG.debug("getMyMaxHp(): {{},{}}", op.getMyMaxHp()[0], op.getMyMaxHp()[1]);
            LOG.debug("getMyMaxEnergy(): {{},{}}", op.getMyMaxEnergy()[0], op.getMyMaxEnergy()[1]);
            LOG.debug("getBuildNumber(): {}", op.getBuildNumber());
            op.setMaxZoom(4000);
            LOG.debug("getPing(): {}", op.getPing());
            //TODO: END code your program below this point

            LOG.info("The \"Java GWCAConstants\" finished executing");
        } catch (Throwable throwable) {
            LOG.error("Initializing the \"Java GWCAConstants\" failed because: ", throwable);
        } finally {
            try {
                gwcaConnection.close();
            } catch (IOException ex) {
                LOG.error("IO closing", ex);
            }
        }
    }
}
