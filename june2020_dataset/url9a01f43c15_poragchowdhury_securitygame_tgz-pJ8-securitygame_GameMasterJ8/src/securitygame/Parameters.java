package securitygame;
/**
 * Defines constant variables used in simulation
 */

public class Parameters {
    /**Identifies the number of nodes in an initial graph*/
    public static final int NUMBER_OF_NODES = 10;
    /**Identifies the number of public nodes in an initial graph*/
    public static final int NUMBER_OF_PUBLIC_NODES = 2;
    /**Identifies the number of routers in an initial graph*/
    public static final int NUMBER_OF_ROUTER_NODES = 2;
    /**Identifies the maximum number of neighbors in an initial graph for non-router nodes*/
    public static final int MAX_NEIGHBORS = 3;
    /**Identifies the minimum number of neighbors in an initial graph for non-router nodes*/
    public static final int MIN_NEIGHBORS = 2;
    /**The maximum PointValue*/
    public static final int MAX_POINT_VALUE = 20;
    /**The maximum number of router edges*/
    public static final int MAX_ROUTER_EDGES = 5;

    /**The value that the defender gets for each node in a graph*/
    public static final int DEFENDER_RATE = 10;
    /**Defender's Budget*/
    public static final int DEFENDER_BUDGET = DEFENDER_RATE * NUMBER_OF_NODES;
    /**The cost to strengthen a node*/
    public static final int STRENGTHEN_RATE = 2;
    /**The cost for having invalid actions*/
    public static final int INVALID_RATE = 10;
    /**The cost to remove an edge (add a firewall)*/
    public static final int FIREWALL_RATE = 10;
    /**The cost to add a honeypot*/
    public static final int HONEYPOT_RATE = 50;

    /**The value that the attacker gets for each node in a graph*/
    public static final int ATTACKER_RATE = 10;
    /**The maximum value for an attacker roll*/
    public static final int ATTACK_ROLL = 20;
    /**The cost for performing an attack*/
    public static final int ATTACK_RATE = 8;
    /**The maximum value for a super attack*/
    public static final int SUPERATTACK_ROLL = 50;
    /**The cost for performing a super attack*/
    public static final int SUPERATTACK_RATE = 20;
    /**The cost to probe for the security value*/
    public static final int PROBE_SECURITY_RATE = 2;
    /**The cost to probe for the point value*/
    public static final int PROBE_POINT_RATE = 2;
    /**The cost to probe for the number of connections*/
    public static final int PROBE_CONNECTIONS_RATE = 1;
    /**The cost to identify if a node is a honeypot*/
    public static final int PROBE_HONEY_RATE = 1;
    /**Attacker's Budget*/
    public static final int ATTACKER_BUDGET = ATTACK_RATE * NUMBER_OF_NODES;
}
