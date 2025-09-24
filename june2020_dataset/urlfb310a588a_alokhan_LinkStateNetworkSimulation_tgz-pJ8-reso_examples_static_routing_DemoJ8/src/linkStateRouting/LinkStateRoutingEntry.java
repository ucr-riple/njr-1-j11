/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package linkStateRouting;

import reso.ip.IPAddress;
import reso.ip.IPInterfaceAdapter;
import reso.ip.IPRouteEntry;

/**
 *
 * @author alo
 */
public class LinkStateRoutingEntry extends IPRouteEntry {

    public LinkState ls;

    public LinkStateRoutingEntry(IPAddress dst, IPInterfaceAdapter oif, LinkState packet) {
        super(dst, oif, LinkStateRoutingProtocol.PROTOCOL_NAME);
        this.ls = packet;
    }

    public String toString() {
        String s = super.toString();
        s += ", metric=" + ls.metric;
        return s;
    }
}
