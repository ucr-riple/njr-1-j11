package be.demmel.fun.jgwcaconstants;

import be.demmel.fun.jgwcaconstants.bll.GWCAConnection;
import be.demmel.fun.jgwcaconstants.bll.GWCAOperation;
import be.demmel.fun.jgwcaconstants.bll.GWCAOperations;
import be.demmel.fun.jgwcaconstants.bll.GWCAPacket;
import be.demmel.fun.jgwcaconstants.bll.NamedPipeGWCAConnection;
import java.io.File;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This shows you how to use operations like the "Cmd" and "CmdCB" in the AutoIt version of the GWCAConstants.
 */
public class Tutorial1 {

    private static final Logger LOG = LoggerFactory.getLogger(MainTestingAllFunctions.class);
    private static final int ZERO = 0;

    public static void main(String[] args) throws InterruptedException {

        GWCAConnection gwcaConnection = null;
        try {
            LOG.info("Executing \"Java GWCAConstants\" (version {})", Version.getVersion());

            //TODO: Fill in the PID here
            gwcaConnection = new NamedPipeGWCAConnection(new File("\\\\.\\pipe\\GWCA_" + 3100));
            gwcaConnection.open();

            //TODO: START code your program below this point
            GWCAPacket gwcaPacketToSend = new GWCAPacket(GWCAOperation.GET_MY_MAX_HP, ZERO, ZERO);
            gwcaConnection.sendPacket(gwcaPacketToSend);
            GWCAPacket gwcaPacketReturned = gwcaConnection.receivePacket();
            int[] returnValues = gwcaPacketReturned.getParamsAsIntArray();
            LOG.debug("getMyMaxHp(): {{},{}}", returnValues[0], returnValues[1]);

            gwcaPacketToSend = new GWCAPacket(GWCAOperation.GET_MY_MAX_ENERGY, ZERO, ZERO);
            gwcaConnection.sendPacket(gwcaPacketToSend);
            gwcaPacketReturned = gwcaConnection.receivePacket();
            returnValues = gwcaPacketReturned.getParamsAsIntArray();
            LOG.debug("getMyMaxEnergy(): {{},{}}", returnValues[0], returnValues[1]);

            gwcaPacketToSend = new GWCAPacket(GWCAOperation.GET_BUILD_NUMBER, ZERO, ZERO);
            gwcaConnection.sendPacket(gwcaPacketToSend);
            gwcaPacketReturned = gwcaConnection.receivePacket();
            int returnValue = gwcaPacketReturned.getWparamAsInt();
            LOG.debug("getBuildNumber(): {}", returnValue);

            gwcaPacketToSend = new GWCAPacket(GWCAOperation.CHANGE_MAX_ZOOM, 4000, ZERO);
            gwcaConnection.sendPacket(gwcaPacketToSend);
            
            gwcaPacketToSend = new GWCAPacket(GWCAOperation.GET_PING, ZERO, ZERO);
            gwcaConnection.sendPacket(gwcaPacketToSend);
            gwcaPacketReturned = gwcaConnection.receivePacket();
            returnValue = gwcaPacketReturned.getWparamAsInt();
            LOG.debug("getPing(): {}", returnValue);

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
