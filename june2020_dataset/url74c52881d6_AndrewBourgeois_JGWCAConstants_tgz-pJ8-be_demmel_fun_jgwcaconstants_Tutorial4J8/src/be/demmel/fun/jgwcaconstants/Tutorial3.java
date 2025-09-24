package be.demmel.fun.jgwcaconstants;

import be.demmel.fun.jgwcaconstants.bll.GWCAConnection;
import be.demmel.fun.jgwcaconstants.bll.GWCAOperations;
import be.demmel.fun.jgwcaconstants.bll.NamedPipeGWCAConnection;
import be.demmel.fun.jgwcaconstants.bll.clean.GuildWarsInstance;
import java.io.File;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This shows you how to use operations like the functions in the AutoIt version of the GWCAConstants, but sorted per panel.
 * It's more verbose but should be easier to use.
 */
public class Tutorial3 {
    
    private static final Logger LOG = LoggerFactory.getLogger(MainTestingAllFunctions.class);

    public static void main(String... args) throws InterruptedException {

        GWCAConnection gwcaConnection = null;
        try {
            LOG.info("Executing \"Java GWCAConstants\" (version {})", Version.getVersion());

            //TODO: Fill in the PID here
            gwcaConnection = new NamedPipeGWCAConnection(new File("\\\\.\\pipe\\GWCA_" + 3100));
            gwcaConnection.open();

            GWCAOperations op = new GWCAOperations(gwcaConnection);
            GuildWarsInstance gwInstance = new GuildWarsInstance(op);
            
            //TODO: START code your program below this point
            LOG.debug("getMyMaxHp(): {{},{}}", gwInstance.getPlayer().getMyMaxHp()[0], gwInstance.getPlayer().getMyMaxHp()[1]);
            LOG.debug("getTitleAsura(): {}", gwInstance.getHeroPanel().getTitlesTab().getTitleAsura());
            LOG.debug("getBuildNumber(): {}", gwInstance.getRemainingCommands().getBuildNumber());
            gwInstance.getRemainingCommands().setMaxZoom(4000);
            LOG.debug("getPing(): {}", gwInstance.getRemainingCommands().getBuildNumber());
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
