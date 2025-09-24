/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package linkStateRouting;

import reso.common.AbstractTimer;
import reso.scheduler.AbstractScheduler;

/**
 *
 * @author alo
 */
public class HelloTimer extends AbstractTimer {

    private LinkStateRoutingProtocol protocol;

    public HelloTimer(AbstractScheduler scheduler, double interval, boolean repeat) {
        super(scheduler, interval, repeat);
    }

    public HelloTimer(AbstractScheduler scheduler, double interval, boolean repeat, LinkStateRoutingProtocol protocol) {
        super(scheduler, interval, repeat);
        this.protocol = protocol;
    }

    @Override
    protected void run() throws Exception {
        protocol.SendHelloToAllInterfaces();
    }

}
