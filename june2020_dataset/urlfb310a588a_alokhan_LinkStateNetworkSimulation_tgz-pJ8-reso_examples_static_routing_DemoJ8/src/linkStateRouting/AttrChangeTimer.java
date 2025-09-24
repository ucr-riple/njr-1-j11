/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package linkStateRouting;

import reso.common.AbstractTimer;
import reso.common.Network;
import reso.ip.IPHost;
import reso.scheduler.AbstractScheduler;

/**
 * This class change attributes every interval 
 * the interval should be smaller than the runUntil value to work properly.
 * For example here we set the metric of the interface eth1 from R3 to 200
 * and we put eth0 from R3 down
 * @author alo
 */
public class AttrChangeTimer extends AbstractTimer{

    private final Network network;
    
    public AttrChangeTimer(AbstractScheduler scheduler, double interval, boolean repeat,Network network) {
        super(scheduler, interval, repeat);
        this.network = network;
    }

    @Override
    protected void run() throws Exception {
        ((IPHost) network.getNodeByName("R3")).getIPLayer().getInterfaceByName("eth1").setMetric(200);
        network.getNodeByName("R3").getInterfaceByName("eth0").down();
    }
    
}
