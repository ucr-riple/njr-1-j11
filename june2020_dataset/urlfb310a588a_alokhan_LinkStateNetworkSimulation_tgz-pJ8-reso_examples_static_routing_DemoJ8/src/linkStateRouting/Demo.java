/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package linkStateRouting;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.Writer;
import reso.common.Network;
import reso.common.Node;
import reso.ip.IPAddress;
import reso.ip.IPHost;
import reso.ip.IPInterfaceAdapter;
import reso.ip.IPLayer;
import reso.ip.IPRouter;
import reso.scheduler.AbstractScheduler;
import reso.scheduler.Scheduler;
import reso.utilities.FIBDumper;
import reso.utilities.NetworkBuilder;
import reso.utilities.NetworkGrapher;

/**
 *
 * @author alo
 */
public class Demo {

    public static final String TOPO_FILE = "reso/data/topology.txt";

    private static IPAddress getRouterID(IPLayer ip) {
        IPAddress routerID = null;
        for (IPInterfaceAdapter iface : ip.getInterfaces()) {
            IPAddress addr = iface.getAddress();
            if (routerID == null) {
                routerID = addr;
            } else if (routerID.compareTo(addr) < 0) {
                routerID = addr;
            }
        }
        return routerID;
    }

    public static void main(String[] args) throws Exception {
        String filename = Demo.class.getClassLoader().getResource(TOPO_FILE).getFile();

        AbstractScheduler scheduler = new Scheduler();

        Network network = NetworkBuilder.loadTopology(filename, scheduler);

        // Add routing protocol application to each router
        for (Node n : network.getNodes()) {
            if (!(n instanceof IPRouter)) {
                continue;
            }
            IPRouter router = (IPRouter) n;
            router.addApplication(new LinkStateRoutingProtocol(router, 2, 5));
            router.start();
        }

        // timer to change some attr value
        //AttrChangeTimer attrTimer = new AttrChangeTimer(scheduler, 30, true, network);
        //attrTimer.start();

        // Run simulation
        scheduler.runUntil(50);

        // Display forwarding table for each node
        FIBDumper.dumpForAllRouters(network);

        for (Node n : network.getNodes()) {
            //IPAddress ndst= ((IPHost) n).getIPLayer().getInterfaceByName("lo0").getAddress();
            IPAddress ndst = getRouterID(((IPHost) n).getIPLayer());
            File f = new File("/tmp/topology-routing-" + ndst + ".graphviz");
            System.out.println("Writing file " + f);
            Writer w = new BufferedWriter(new FileWriter(f));
            NetworkGrapher.toGraphviz2(network, ndst, new PrintWriter(w));
            w.close();
        }
    }

}
