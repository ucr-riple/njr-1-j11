/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package linkStateRouting;

import reso.ip.IPAddress;
import reso.ip.IPInterfaceAdapter;

/**
 * Distance to a router.
 *
 * @author alo
 */
public class LinkState {

    public final IPAddress routerId;
    public final int metric;
    public final IPInterfaceAdapter routerInterface;

    public LinkState(IPAddress dst, int metric, IPInterfaceAdapter routerInterface) {
        this.routerId = dst;
        this.metric = metric;
        this.routerInterface = routerInterface;
    }

    public String toString() {
        return "LS[" + routerId + "," + ((metric == Integer.MAX_VALUE) ? "inf" : metric);
    }
}
