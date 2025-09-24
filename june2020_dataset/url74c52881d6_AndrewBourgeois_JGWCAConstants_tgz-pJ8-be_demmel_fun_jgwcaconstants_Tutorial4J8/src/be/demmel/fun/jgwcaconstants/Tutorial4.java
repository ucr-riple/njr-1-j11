package be.demmel.fun.jgwcaconstants;

import be.demmel.fun.jgwcaconstants.bll.GWCAConnection;
import be.demmel.fun.jgwcaconstants.bll.GWCAOperations;
import be.demmel.fun.jgwcaconstants.bll.NamedPipeGWCAConnection;
import be.demmel.fun.jgwcaconstants.bll.clean.Agent;
import be.demmel.fun.jgwcaconstants.bll.clean.GuildWarsInstance;
import java.io.File;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This shows you how to use operations like the functions in the AutoIt version of the GWCAConstants, but sorted per panel.
 * It's more verbose but should be easier to use.
 * 
 * This specific example will run outside Ascalon City (pre-searing), go to the first mob, kill it and return to the outpost
 */
public class Tutorial4 {

    private static final Logger LOG = LoggerFactory.getLogger(MainTestingAllFunctions.class);

    public static void main(String[] args) throws InterruptedException {

        GWCAConnection gwcaConnection = null;
        try {
            LOG.info("Executing \"Java GWCAConstants\" (version {})", Version.getVersion());

            //TODO: Fill in the PID of the running Guild Wars instance here (so replace the 6500)
            gwcaConnection = new NamedPipeGWCAConnection(new File("\\\\.\\pipe\\GWCA_" + 6500));
            gwcaConnection.open();

            GWCAOperations op = new GWCAOperations(gwcaConnection);
            GuildWarsInstance gwInstance = new GuildWarsInstance(op);

            //TODO: Add your code below this point
            goKillSomething(gwInstance);
            //TODO: Add your code above this point

            LOG.info("The \"Java GWCAConstants\" finished executing");
        } catch (Throwable throwable) {
            LOG.error("Initializing the \"Java GWCAConstants\" failed because: ", throwable);
        } finally {
            if (gwcaConnection != null) {
                try {
                    gwcaConnection.close();
                } catch (IOException ex) {
                    LOG.error("IO closing", ex);
                }
            }
        }
    }

    private static void goKillSomething(GuildWarsInstance gwInstance) throws IOException {
        // Moving to the exit portal
        gwInstance.getPlayer().moveToEx(7510, 5615, 100);

        // Make the movement look a little less suspicous (this can be done way better though...)
        gwInstance.getRemainingCommands().pingSleep(750);

        // Exiting through the portal
        gwInstance.getPlayer().moveToEx(7000, 5100, 150);

        // Wait until the map is loaded before doing anythnig else (this could use the mapLoaded or soemthing crap instead...)
        gwInstance.getRemainingCommands().pingSleep(5000);

        // Moving to the actual farm location
        gwInstance.getPlayer().moveToEx(5406, -3053, 100);

        // workaround: the getNextAliveFoe doesn't work with agent ID = -2 (player)
        gwInstance.getPlayer().setId(gwInstance.getPlayer().getMyId());

        LOG.debug("Find a target");
        int agentIdOfTarget = gwInstance.getPlayer().getNextAliveFoe();
        Agent target = new Agent(gwInstance.getGwcaOperations(), agentIdOfTarget);
        LOG.debug("Found a target: {}", agentIdOfTarget);

        LOG.debug("Killing target");
        // Keep casting your first skill until the target is dead (or until you are (very unlikely with this awesome fight algo)))
        do {
            // Workaround: useSkillEx should sleep but that isn't implemented just yet
            gwInstance.getRemainingCommands().pingSleep(500);

            // I just noticed that this isn't implemented. I'll check later whether they did use that in the AutoCrap version.
            // Using first skill");
            int notWorkingDelay = 500;
            gwInstance.getSkillBar().getFirstSkillSlot().useSkillEx(target.getId(), notWorkingDelay);

        } while (!target.isDead() && !gwInstance.getPlayer().isDead());
        LOG.debug("Killed target");

        // Wait before resigning
        gwInstance.getRemainingCommands().pingSleep(1500);

        // Resign
        gwInstance.getRemainingCommands().resign();

        // Giving yourself some time to die
        gwInstance.getRemainingCommands().pingSleep(3500);

        // Returning to outpost
        gwInstance.getRemainingCommands().returnToOutpost();
    }
}
