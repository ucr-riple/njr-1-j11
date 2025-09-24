package securitygame;

import java.util.ArrayList;

/**
 * Node class is used for creating nodes for the network.
 * Important variables information for Attacker agents:
 * 
 * nodeId - integer representing the node
 * sv - security value of a node (-1 means unknown)
 * pv - point value of a node (-1 means unknown)
 * isHoneyPot - boolean for if the node is a honeypot (-1 means unknown, 0 means false, 1 means true)
 * captured - if the node is a public entry node or has been successfully captured via attack
 * bestRoll - the highest roll on this node (if -1, the node has never been attacked), if the security value is -1, bestRoll may be used to reason what the sv might be (actual security value >= bestRoll if sv == -1)
 * neighborAmount - number of connections this node has. Will be different than neighbor.size() if the node has not been captured. (-1 means unknown)
 * neighbor - list of neighbors to this node. Will be empty if the node has not been captured
 *
 * @author      Porag Chowdhury, Anjon Basak, Marcus Gutierrez
 * @version     11/14/2014
 */

public class Node 
{
	private int nodeID;
	private int sv;
	private int pv;
	private int isHoneyPot; //-1 means honeypot is unknown, 0 means false, 1 means true
	private boolean captured;
	private int bestRoll = -1;
	private int neighborAmount = -1;
	public ArrayList<Node> neighbor = new ArrayList<Node>();
    
	/**
     * Empty Constructor.
     */
	public Node(){}
	
	/**
     * used for comparison purposes.
     */
	public Node(int id){
		nodeID = id;
	}

	/**
     * Constructor.
     * @param nodeID An integer indicates nodeId
     * @param sv An integer indicates security value
     * @param pv An integer indicates point value
     * @param isHoneyPot A boolean indicates HoneyPot
     */
	public Node(int nodeID, int sv, int pv, boolean isHoneyPot) {
		super();
		this.nodeID = nodeID;
		this.sv = sv;
		this.pv = pv;
		if(isHoneyPot)
			this.isHoneyPot = 1;
		else
			this.isHoneyPot = 0;
		if(sv == 0 && pv == 0)
			captured = true;
		else
			captured = false;
	}
	
	/**
     * Constructor.
     * @param nodeID An integer indicates nodeId
     * @param sv An integer indicates security value
     * @param pv An integer indicates point value
     * @param isHoneyPot indicates HoneyPot status
     */
	public Node(int nodeID, int sv, int pv, int isHoneyPot) {
		super();
		this.nodeID = nodeID;
		this.sv = sv;
		this.pv = pv;
		this.isHoneyPot = isHoneyPot;
		if(sv == 0 && pv == 0)
			captured = true;
		else
			captured = false;
	}
	
	/**
     * Constructor.
     * @param nodeID An integer indicates nodeId
     * @param sv An integer indicates security value
     * @param pv An integer indicates point value
     * @param isHoneyPot indicates HoneyPot status
     * @param captured indicates if a node has been captured
     */
	public Node(int nodeID, int sv, int pv, int isHoneyPot, boolean captured) {
		super();
		this.nodeID = nodeID;
		this.sv = sv;
		this.pv = pv;
		this.isHoneyPot = isHoneyPot;
		this.captured = captured;
	}

	/**
     * Returns the nodeId
     * @return the nodeId
     */
	public int getNodeID()
	{
		return nodeID;
	}

	/**
     * Sets the nodeId
     */
	public void setNodeID(int nodeID)
	{
		this.nodeID = nodeID;
	}

	/**
     * Returns the security value of the node
     * @return the security value of the node
     */
	public int getSv()
	{
		return sv;
	}
	
	/**
     * Sets the security value of the node
     */
	public void setSv(int sv)
	{
		if(sv == 0)
			captured = true;
		this.sv = sv;
	}

	/**
     * Returns the point value of the node
     * @return the point value of the node
     */
	public int getPv()
	{
		return pv;
	}
	
	/**
     * Sets the point value of the node
     */
	public void setPv(int pv)
	{
		this.pv = pv;
	}

	/**
	 * Sends the known honeypot status of this node. If the honeypot status is unknown (-1),
	 * false will be returned. knowsHoneyPot() should be used in conjunction with this
	 * method or use getHoneyPot() to find the specific status of a honeypot.
	 * @return if the node is a known honey pot, returns false if -1
	 */
	public boolean isHoneyPot()
	{
		if(isHoneyPot == 1)
			return true;
		return false;
	}
	
	/**
	 * Returns the current status of a honey pot.
	 * -1 -> true honeypot status is unknown
	 * 0 -> this node is not a honeypot
	 * 1 -> this node is a honeypot
	 * @return honeypot's status
	 */
	public int getHoneyPot(){
		return isHoneyPot;
	}
	
	/**
	 * Returns the knowledge of this node's honeypot status.
	 * If isHoneyPot is 0 or 1, this method returns true, because these
	 * values represent node status with certainty.
	 * If isHoneyPot is -1, this method returns false, because the true
	 * honeypot status of this node is unknown.
	 * @return the visibility of the honeypot status of this node
	 */
	public boolean knowsHoneyPot(){
		if(isHoneyPot == -1)
			return false;
		return true;
	}

	/**
	 * Sets the honeypot status of this node
	 * @param honeyPot sets the isHoneyPot to a known value (not -1)
	 */
	public void setHoneyPot(boolean honeyPot)
	{
		if(honeyPot)
			isHoneyPot = 1;
		else
			isHoneyPot = 0;
	}
	
	/**
	 * Sets the honeypot status of this node
	 * -1 -> true honeypot status is unknown
	 * 0 -> this node is not a honeypot
	 * 1 -> this node is a honeypot
	 * @param honeyPot sets the isHoneyPot field variable
	 */
	public void setHoneyPot(int honeyPot)
	{
		isHoneyPot = honeyPot;
	}
	
	/**
	 * Returns captured
	 * @return captured
	 */
	public boolean isCaptured(){
		return captured;
	}
	
	/**
	 * sets this.captured attribute
	 * @param captured value to set this.captured to
	 */
	public void setCaptured(boolean captured){
		this.captured = captured;
	}
	
	/**
	 * Returns bestRoll
	 */
	public int getBestRoll(){
		return bestRoll;
	}
	
	/**
	 * sets this.bestRoll attribute
	 * @param bestRoll new best roll
	 */
	public void setBestRoll(int bestRoll){
		this.bestRoll = bestRoll;
	}
	
	/**
	 * Returns neighborAmount
	 */
	public int getNeighborAmount(){
		return neighborAmount;
	}
	
	/**
	 * sets this.neighborAmount attribute
	 * @param amt new neighbor amount
	 */
	public void setNeighborAmount(int amt){
		neighborAmount = amt;
	}
	
	/**
     * Add Neighbor to the current node
     * @param neighborNode neighbor node which will the added as a neighbor to the current node
     */
	public void addNeighbor(Node neighborNode)
	{
		neighborAmount++;
		this.neighbor.add(neighborNode);
	}

	/**
     * Returns the neighbor
     * @return Node of idx
     */
	public Node getNeighbor(int idx)
	{
		return neighbor.get(idx);
	}
	
	/**
     * Returns the neighbor
     * @return Node of idx
     */
	public ArrayList<Node> getNeighborList()
	{
		return neighbor;
	}
	
	/**
	 * Overridden equals method that just compares NodeID
	 */
	public final boolean equals(Object o){
		Node n = (Node)o;
		if(n.getNodeID() == nodeID)
			return true;
		return false;
	}
}