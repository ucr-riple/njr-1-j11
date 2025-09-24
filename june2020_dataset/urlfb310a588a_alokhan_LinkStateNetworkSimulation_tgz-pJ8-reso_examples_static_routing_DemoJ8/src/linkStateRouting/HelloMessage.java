/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package linkStateRouting;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import reso.common.Message;
import reso.ip.IPAddress;

/**
 * Hello message send by router to let their neighbors they exist.
 * and construct the neighbor table.
 * @author alo
 */
public class HelloMessage implements Message {

    public final IPAddress routerId;
    public final List<IPAddress> neighborList;
    public final int neighborNumber;

    public HelloMessage(IPAddress routerId, Set<IPAddress> keySet) {
        this.routerId = routerId;
        this.neighborList = new ArrayList<IPAddress>(keySet);
        this.neighborNumber = this.neighborList.size();
    }
}
