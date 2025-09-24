package securitygame;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.File;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

/**
 * Axillary class for creating and parsing defence files.
 * Defender will use the three parameter constructor and combination of strengthen(), firewall, and honeypot()
 * to generate defence file. Have Defender remember to call close() when finished for safety.
 * Game Master will use the two parameter constructor for parsing original network and defence file to generate new network.
 *
 * Actions deemed invalid will be charged the Parameters.INVALID_RATE value.
 *
 * @author      Oscar Veliz
 * @version     2014/11/01
 */

public class DefenderHelper
{
    private Network net;
    private String name;
    private PrintWriter pw;
    private int budget;

    /**
     * Constructor used by Defender to initialize defence file and keep track of network changes.
     * @param network Graph being secured given a budget
     * @param graphFile Contains original name of graph i.e. "1" for 1.graph
     * @param defenderName Name of defender will be prepended to defence file i.e. "tower" for tower-1.defence
     */
    public DefenderHelper(Network network, String graphFile, String defenderName)
    {
        budget = Parameters.DEFENDER_BUDGET;
        net = network;
        name = defenderName;
        try
        {
            pw = new PrintWriter(name+"-"+graphFile + ".defence", "UTF-8");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Constructor used by GameMaster to create new secured graph based on Defender's defence actions.
     * @param defenderName Name of defender prepended to defence file i.e. "tower" for tower-1.defence
     * @param graphFile Contains original name of graph i.e. "1" for 1.graph
     */
    public DefenderHelper(String defenderName, String graphFile){
        name = defenderName;
        budget = Parameters.DEFENDER_BUDGET;
        net = Parser.parseGraph(graphFile+".graph");
        File csv = new File(defenderName+"-"+graphFile+".defence");
		try
		{
			CSVParser parser = CSVParser.parse(csv, StandardCharsets.US_ASCII, CSVFormat.DEFAULT);
			for (CSVRecord csvRecord : parser)
			{
				Iterator<String> itr = csvRecord.iterator();
                int mode = Integer.parseInt(itr.next());
                switch (mode){
                    case 0://strengthen
                        int id = Integer.parseInt(itr.next());
                        if(isValidStrengthen(id))
                        {
                            budget -= Parameters.STRENGTHEN_RATE;
                            Node n = net.getNode(id);
                            n.setSv(n.getSv()+1);
                        }
                        else
                            budget -= Parameters.INVALID_RATE;
                        break;
                    case 1://firewall
                        int id1 = Integer.parseInt(itr.next());
                        int id2 = Integer.parseInt(itr.next());
                        if(isValidFirewall(id1,id2)){
                            Node n1 = net.getNode(id1);
                            Node n2 = net.getNode(id2);
                            n1.neighbor.remove(n2);
                            n2.neighbor.remove(n1);
                            budget -= Parameters.FIREWALL_RATE;
                        }
                        else
                            budget -= Parameters.INVALID_RATE;

                        break;
                    case 2://honeypot
                        int sv = Integer.parseInt(itr.next());
                        int pv = Integer.parseInt(itr.next());
                        ArrayList<Integer> newNeighbors = new ArrayList<Integer>();
                        while (itr.hasNext())
                            newNeighbors.add(Integer.parseInt(itr.next()));
                        int[] n = new int[newNeighbors.size()];
                        for(int i = 0; i < n.length; i++)
                            n[i] = newNeighbors.get(i);
                        if(isValidHoneypot(sv, pv, n))
                        {
                            net.addHoneypot(sv, pv, n);
                            budget -= Parameters.HONEYPOT_RATE;
                        }
                        else
                            budget -= Parameters.INVALID_RATE;
                     break;
                     default://some other case not defined
                        budget -= Parameters.INVALID_RATE;
                     break;
                }
            }
            parser.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        net.setName(name+"-"+graphFile);
        net.shuffleNetwork();//avoid predictable location of honeypot (last node in list)
        net.printNetwork();
        net.printHiddenNetwork();
    }

    /**
     * Defender should call this method when done adding actions.
     */
    public void close()
    {
        pw.close();
    }

    /**
     * Action has been deemed invalid
     */
    public void invalid()
    {
        budget -= Parameters.INVALID_RATE;
        pw.write("-1");
        pw.println();
    }

    /**
     * Adds 1 to a node security value if the node is not public or not already at maximum security.
     * @param id The id of the node being strengthened
     */
    public void strengthen(int id)
    {
        if(isValidStrengthen(id))
        {
            Node n = net.getNode(id);
            budget -= Parameters.STRENGTHEN_RATE;
            n.setSv(n.getSv()+1);
            pw.write("0,"+id);
            pw.println();
        }
        else
            invalid();
    }

    /**
     * Removes the edge between two nodes. Will not remove if doing so will isolate a node. Will not remove if there is no
     * edge to remove.
     *
     * @param id1 First node's id
     * @param id2 Second node's id
     */
    public void firewall(int id1, int id2)
    {
        if(isValidFirewall(id1, id2))
        {
            Node n1 = net.getNode(id1);
            Node n2 = net.getNode(id2);
            n1.neighbor.remove(n2);
            n2.neighbor.remove(n1);
            budget -= Parameters.FIREWALL_RATE;
            pw.write("1,"+id1+","+id2);
            pw.println();
        }
        else
            invalid();

    }

    /**
     * Adds a honeypot node to the graph if possible. Otherwise charges an invalid.
     * @param sv Security Value for the honeypot
     * @param pv Point Value for the honeypot
     * @param newNeighbors Array of Node ID's specifying which nodes to connect the honeypot to
     */
    public void honeypot(int sv, int pv, int[] newNeighbors)
    {
        if(isValidHoneypot(sv, pv, newNeighbors))
        {
            net.addHoneypot(sv, pv, newNeighbors);
            budget -= Parameters.HONEYPOT_RATE;
            String s = "2,"+sv+","+pv+",";
            for(int i =0; i < newNeighbors.length-1;i++)
                s = s + newNeighbors[i]+",";
            s = s + newNeighbors[newNeighbors.length-1];
            pw.write(s);
            pw.println();
        }
        else
            invalid();
    }

    /**
     * Returns current budget.
     * @return current budget
     */
    public int getBudget()
    {
        return budget;
    }

    public boolean isValidStrengthen(int id)
    {
        if(budget < Parameters.STRENGTHEN_RATE)
            return false;
        else
        {
            Node n = net.getNode(id);
            return (n != null && n.getSv() != 20 && n.getSv() != 0);//can't strengthen public node or maxed out node
        }
    }

    public boolean isValidFirewall(int id1, int id2)
    {
        if(budget < Parameters.FIREWALL_RATE)
            return false;
        else
        {
            Node n1 = net.getNode(id1);
            Node n2 = net.getNode(id2);
            if(n1 == null || n2 == null)
                return false;
            else if(n1.neighbor.size()==1 || n2.neighbor.size()==1)
                return false;
            else return n1.neighbor.contains(n2);
        }
    }

    public boolean isValidHoneypot(int sv, int pv, int[] newNeighbors){
        if(budget < Parameters.HONEYPOT_RATE)
            return false;
        else{
            if(sv <= 0 || sv > 20 || pv < 0 || pv > 20)//validate sv and pv
                return false;
            //check if there are two of the same neighbor (indicator that something is wrong
            //and that all of the nodes being connected to exist
            Arrays.sort(newNeighbors);
            for(int i = 0; i < newNeighbors.length-1;i++)
                if(newNeighbors[i]==newNeighbors[i+1] || net.getNode(newNeighbors[i])==null)
                    return false;
            return true;
        }
    }
}
