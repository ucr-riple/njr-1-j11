package soc.ourRobot;

//import soc.client.SOCDisplaylessPlayerClient;
//import soc.disableDebug.D;
import soc.robot.*;
import soc.game.*;
//import soc.message.*;
//import soc.server.SOCServer;
import soc.util.*;
import java.io.*;
import java.util.*;


public class SOCOurBrain extends SOCRobotBrain {

	/** Strategy we are playing in this game
	 * 
	 *  0 = Balanced, 1 = Roads/Settlements, 2 = Cities/Development Cards
	 */
	private int strategy;
	
	/**
	 * 
	 */
	private static Vector<SearchTracker> searchResults = new Vector<SearchTracker>();
	
	private Move moveToMake = null;

	static final float[] FPROB = 
	{
		0.0f, 0.0f, 0.03f, 0.06f, 0.08f, 0.11f, 0.14f, 0.17f, 0.14f, 0.11f,
		0.08f, 0.06f, 0.03f
	};

	static final double[] PROB = 
	{
		0.0, 0.0, 0.03, 0.06, 0.08, 0.11, 0.14, 0.17, 0.14, 0.11,
		0.08, 0.06, 0.03
	};

	private final int cityWeight= 5;
	private final int settlementWeight= 3;
	private final int roadWeight= 1;
	private final int devCardWeight= 2; // should depend on strategy
	
	private Vector<SOCTradeOffer> trades;
	private int devCardsToBuy=0;

	/**
	 * Create a robot brain to play a game.
	 *<P>
	 * Depending on {@link SOCGame#getGameOptions() game options},
	 * constructor might copy and alter the robot parameters
	 * (for example, to clear {@link SOCRobotParameters#getTradeFlag()}).
	 *
	 * @param rc  the robot client
	 * @param params  the robot parameters
	 * @param ga  the game we're playing
	 * @param mq  the message queue
	 */
	public SOCOurBrain(SOCRobotClient rc, SOCRobotParameters params, SOCGame ga, CappedQueue mq)
	{
		super(rc, params, ga, mq);
	}


	/**
	 * Current Trading AI
	 * Rejects all offers
	 */
	protected int considerOffer(SOCTradeOffer offer)
	{
		return SOCRobotNegotiator.REJECT_OFFER;
	}

	/**
	 * figure out where to place the two settlements
	 */

	protected void planInitialSettlements()
	{
		double bestScore = 0.0;
		double secondBestScore = 0.0;
		firstSettlement = -1;
		secondSettlement = -1;
		SOCBoard board = game.getBoard();
		for (int firstNode = board.getMinNode(); firstNode <= SOCBoard.MAXNODE; firstNode++)
		{
			if (ourPlayerData.isPotentialSettlement(firstNode))
			{
				double currentScore = 0.0;
				Integer nodeInt = new Integer(firstNode);
				Enumeration hexes = SOCBoard.getAdjacentHexesToNode(firstNode).elements();

				while (hexes.hasMoreElements())
				{
					Integer hex = (Integer) hexes.nextElement();
					int number = board.getNumberOnHexFromCoord(hex.intValue());
					currentScore += PROB[number];
				}
				if(firstSettlement == -1 || currentScore > bestScore) {
					secondSettlement = firstSettlement;
					firstSettlement = nodeInt;
					secondBestScore = bestScore;
					bestScore = currentScore;
				}
				else if(secondSettlement == -1 || currentScore > secondBestScore) {
					secondSettlement = nodeInt;
					secondBestScore = currentScore;
				}
			}
		}
	}

	/**
	 * figure out where to place the second settlement
	 */
	protected void planSecondSettlement()
	{
		//client.sendText(game,"placing our second settlement.");
		if(ourPlayerData.isPotentialSettlement(secondSettlement))
			return;

		double bestScore = 0.0;
		secondSettlement = -1;
		SOCBoard board = game.getBoard();
		for (int firstNode = board.getMinNode(); firstNode <= SOCBoard.MAXNODE; firstNode++)
		{
			if (ourPlayerData.isPotentialSettlement(firstNode))
			{
				double currentScore = 0.0;
				Integer nodeInt = new Integer(firstNode);
				Enumeration hexes = SOCBoard.getAdjacentHexesToNode(firstNode).elements();

				while (hexes.hasMoreElements())
				{
					Integer hex = (Integer) hexes.nextElement();
					int number = board.getNumberOnHexFromCoord(hex.intValue());
					currentScore += PROB[number];
				}
				if(secondSettlement == -1 || currentScore > bestScore) {
					secondSettlement = nodeInt;
					bestScore = currentScore;
				}
			}
		}
	}

	/**
	 * place planned first settlement
	 */
	protected void placeFirstSettlement()
	{
		//D.ebugPrintln("BUILD REQUEST FOR SETTLEMENT AT "+Integer.toHexString(firstSettlement));
		//pause(500);
		lastStartingPieceCoord = firstSettlement;
		client.putPiece(game, new SOCSettlement(ourPlayerData, firstSettlement, null));
		//pause(1000);
	}

	/**
	 * place planned second settlement
	 */
	protected void placeSecondSettlement()
	{
		if (secondSettlement == -1)
		{
			// This could mean that the server (incorrectly) asked us to
			// place another second settlement, after we've cleared the
			// potentialSettlements contents.
			System.err.println("robot assert failed: secondSettlement -1, " + ourPlayerData.getName() + " leaving game " + game.getName());
			failedBuildingAttempts = 2 + (2 * MAX_DENIED_BUILDING_PER_TURN);
			waitingForGameState = false;
			return;
		}

		//D.ebugPrintln("BUILD REQUEST FOR SETTLEMENT AT "+Integer.toHexString(secondSettlement));
		//pause(500);
		lastStartingPieceCoord = secondSettlement;
		client.putPiece(game, new SOCSettlement(ourPlayerData, secondSettlement, null));
		// pause(1000);
	}

	/**
	 * Plan and place a road attached to our most recently placed initial settlement,
	 * in game states {@link SOCGame#START1B START1B}, {@link SOCGame#START2B START2B}.
	 *<P>
	 * Road choice is based on the best nearby potential settlements, and doesn't
	 * directly check {@link SOCPlayer#isPotentialRoad(int) ourPlayerData.isPotentialRoad(edgeCoord)}.
	 * If the server rejects our road choice, then {@link #cancelWrongPiecePlacementLocal(SOCPlayingPiece)}
	 * will need to know which settlement node we were aiming for,
	 * and call {@link SOCPlayer#clearPotentialSettlement(int) ourPlayerData.clearPotentialSettlement(nodeCoord)}.
	 * The {@link #lastStartingRoadTowardsNode} field holds this coordinate.
	 */
	public void placeInitRoad()
	{
		//client.sendText(game,"Placing our road.");
		final int settlementNode = ourPlayerData.getLastSettlementCoord();

		int[] choices = game.getBoard().getAdjacentEdgesToNode_arr(settlementNode);

		//randomly pick road direction
		int rand = (int)(Math.random()*(3));

		//Pick based on possible future progress
		int bestSpot = choices[0];
		float bestVal = -1;
		float curVal = -1;
		for (int i=0;i < 3;i++) {
			if(choices[i] != -1) {
				Vector<Integer> nextRoads = game.getBoard().getAdjacentEdgesToEdge(choices[i]);
				for (int j=0;j<nextRoads.size();j++) {
					if(nextRoads.get(j) != -1) {
						Vector<Integer> nextNodes = game.getBoard().getAdjacentNodesToEdge(nextRoads.get(j));
						for (int k=0;k<nextNodes.size();k++) {
							if (ourPlayerData.isPotentialSettlement(nextNodes.get(k))) {
								Vector<Integer> adjHexes = SOCBoard.getAdjacentHexesToNode(nextNodes.get(k));
								curVal = 0;
								for(int l=0;l<adjHexes.size();l++) {
									curVal = curVal + FPROB[game.getBoard().getNumberOnHexFromNumber(adjHexes.get(l))];
								}
								if (curVal > bestVal) {
									bestVal = curVal;
									bestSpot = choices[i];
								}
							}
						}
					}
				}
			}
		}

		//random
		int roadEdge = choices[rand];
		//"best"
		// int roadEdge = bestSpot;

		int[] endPoints = game.getBoard().getAdjacentNodesToEdge_arr(roadEdge);
		int destination = endPoints[0];
		if(destination == settlementNode)
			destination = endPoints[1];

		//D.ebugPrintln("!!! PUTTING INIT ROAD !!!");
		//pause(500);

		//D.ebugPrintln("Trying to build a road at "+Integer.toHexString(roadEdge));
		lastStartingPieceCoord = roadEdge;
		lastStartingRoadTowardsNode = destination;
		client.putPiece(game, new SOCRoad(ourPlayerData, roadEdge, null));
		// pause(1000);
	}



	/** Returns a ranking of the given resource set based on how much can be built
	 * using it. Does not consider bank trades.
	 * Different constructs are given different weights, using tunable parameters.
	 *  
	 * @param set SOCResourceSet to rank
	 * @return ranking of this set
	 */
	private int rankResourceSet(SOCResourceSet set){
		//int city, settl, rd, card;
		//max(cityWeight*city+settlementWeight*settl+roadWeight*rd+devCardWeight*dev);
		//s.t. resource constraints hold

		int rank=0;
		while(SOCResourceSet.gte(set,SOCGame.CITY_SET)){
			set.subtract(SOCGame.CITY_SET);
			rank+=cityWeight;
		}
		while(SOCResourceSet.gte(set,SOCGame.SETTLEMENT_SET)){
			set.subtract(SOCGame.SETTLEMENT_SET);
			rank+=settlementWeight;
		}
		while(SOCResourceSet.gte(set,SOCGame.CARD_SET)){
			set.subtract(SOCGame.CARD_SET);
			rank+=devCardWeight;
		}
		while(SOCResourceSet.gte(set,SOCGame.ROAD_SET)){
			set.subtract(SOCGame.ROAD_SET);
			rank+=roadWeight;
		}

		return rank;
	}


	/** Decide whether or not to accept a given trade offer
	 *  Will accept an offer if something additional can be built
	 * @param offer the SOCTradeOffer offered to the robot
	 * @return true if the trade should be accepted
	 */
	protected boolean considerOffer2(SOCTradeOffer offer){
		return false;
	}


	// TODO: maybe examine the 25 possible sets, and see which one gets us the best combo?
	protected void getDiscoveryResources(){
		//SOCResourceSet current= player.getResources();
		resourceChoices= new SOCResourceSet(2,0,0,0,0,0); // always request 2 clay for now
	}


	/**
	 * Choose which resource we want to monopolize
	 */
	
	// TODO: do the same with 1 or 2 copies of the resources to be chosen?
	protected void getMonopolyResources(){
		monopolyChoice= SOCResourceConstants.ORE; // for now just pick ore
	}

	/** Decide whether or not to play a dev card during a turn
	 * @param none
	 * @return 
	 */
	// TODO: Ryan: is this right? I commented some stuff out...
	protected void playDevCards(){
		//SOCDevCardSet
		SOCDevCardSet cards = ourPlayerData.getDevCards();
		if(cards.getNumUnplayed()==0) return;
		int vptot= ourPlayerData.getTotalVP();
		// Play victory point cards if we can win
		if(vptot==10){
			//TODO fix this
			// play all vp cards
			for(int i=SOCDevCardConstants.CAP;i<SOCDevCardConstants.TOW;i++){
				if(cards.getAmount(SOCDevCardSet.OLD,i)>0){
					client.playDevCard(game, i);
				}
			}
			return;
		}
		// Play Knight card if we are threatened by robber
		if(robberThreatening() && cards.getAmount(SOCDevCardSet.OLD,SOCDevCardConstants.KNIGHT)>0){
			client.playDevCard(game, SOCDevCardConstants.KNIGHT);
			return;
		}
		/*
		// Play Roads card if we have one
		if(cards.getAmount(SOCDevCardSet.OLD,SOCDevCardConstants.ROADS)>0){
			client.playDevCard(game, SOCDevCardConstants.ROADS);
			return;
		}
		// Play discovery card if we have one
		if(cards.getAmount(SOCDevCardSet.OLD,SOCDevCardConstants.DISC)>0){
			getDiscoveryResources();
			client.playDevCard(game, SOCDevCardConstants.DISC);
			return;
		}
		if(cards.getAmount(SOCDevCardSet.OLD, SOCDevCardConstants.MONO)>0){
			getMonopolyResources();
			client.playDevCard(game, SOCDevCardConstants.MONO);
			return;
		}
		*/
	}

	/** Indicates whether player is currently threatened by robber
	 * 
	 * @return true if robber adjacent to one of our settlements/cities, false otherwise
	 */
	private boolean robberThreatening(){
		SOCBoard board= game.getBoard();
		int robHex= board.getRobberHex();
		Vector threatened= game.getPlayersOnHex(robHex);
		return threatened.contains(ourPlayerData.getPlayerNumber());
	}

	// Added by Ryan
	// Discards numDiscards resources at random
	// TODO discard so that we have the best combo remaining?
	protected void discard(int numDiscards) {
		SOCResourceSet discards = new SOCResourceSet();
		SOCGame.discardPickRandom(ourPlayerData.getResources(), numDiscards, discards, rand);
		client.discard(game, discards);
	}

	
	
	//Added by Katie
	protected void planBuilding(){
		buildingPlan.clear();
		trades= new Vector<SOCTradeOffer>();
		SOCResourceSet needed= new SOCResourceSet();
		int maxDepth= 2;
		Move move= max4Search2WithTracking(new SOCGame(game), ourPlayerData.getPlayerNumber(),maxDepth);
		//pause(3000);
		// do nothing if we can do nothing
		if(move==null) {
			client.sendText(game, "No options :(");
			return;
		}

		for(SOCPlayingPiece p:move.getPieces()){
			if (p instanceof SOCRoad) needed.add(SOCGame.ROAD_SET);
			else if(p instanceof SOCSettlement) needed.add(SOCGame.SETTLEMENT_SET);
			else if(p instanceof SOCCity) needed.add(SOCGame.CITY_SET);
		}
		for(int i=0; i<move.devCardsToBuy(); i++){
			needed.add(SOCGame.CARD_SET);
		}

		//pause(3000);
		// do nothing if we can do nothing
		if(move.pieces.size() > 0)
			client.sendText(game, " before: "+move.toString());
		move= addTrades(move, needed ,ourPlayerData);
		if(move == null)
			return;
		if(move.pieces.size() > 0)
			client.sendText(game, " after: "+move.toString());

		if(move.pieces.size() > 0)
			client.sendText(game,"Chose: "+move.toString());
		// otherwise...
		Vector<SOCPlayingPiece> pieces= move.getPieces();
		buildingPlan.push(null); // so it won't be empty in case of devCards
		for (SOCPlayingPiece p:pieces) {
			buildingPlan.push(p);
		}
		trades= move.getTrades();
		devCardsToBuy= move.devCardsToBuy();
	}

	
	// Added by Ryan
	// Tries to build, in order: City, Settlement, Road, Dev Card
	// TODO Placeholder for real planBuilding()
	protected void planBuilding2()
	{

		//this.client.sendText(this.game, " planning building.");
		buildingPlan.clear();
		
		
		
		 // Get best move:
		 // int maxDepth = 5;
		 // moveToMake = max4Search(game.copy(), ourPlayerData.getPlayerNumber(), maxDepth);
		
		if(game.getNumDevCards() > 0)
			buildingPlan.push(new SOCPossibleCard(ourPlayerData, 1));
		 
		if(ourPlayerData.getNumPieces(SOCPlayingPiece.ROAD) > 0)
			for(int i=0; i<=game.getBoard().MAXEDGE; i++) {
				if(ourPlayerData.isPotentialRoad(i)) {
					buildingPlan.push(new SOCPossibleRoad(ourPlayerData, i, new Vector()));
					break;
				}
			}

		if(ourPlayerData.getNumPieces(SOCPlayingPiece.SETTLEMENT) > 0)
			for(int i=0; i<=game.getBoard().MAXNODE; i++) {
				if(ourPlayerData.isPotentialSettlement(i)) {
					buildingPlan.push(new SOCPossibleSettlement(ourPlayerData, i, new Vector()));
					break;
				}
			}

		if(ourPlayerData.getNumPieces(SOCPlayingPiece.CITY) > 0)
			for(int i=0; i<=game.getBoard().MAXNODE; i++) {
				if(ourPlayerData.isPotentialCity(i)) {
					buildingPlan.push(new SOCPossibleCity(ourPlayerData, i));
					break;
				}
			}


		if(!buildingPlan.empty())
			lastTarget = (SOCPossiblePiece) buildingPlan.peek();
		else
			lastTarget = null;
	}
	
	private SOCGame putPossiblePiece(SOCGame g, SOCPossiblePiece piece, int pn) {
		if (piece.getType() == 0) {
			g.buyRoad(pn);
			g.putPiece(new SOCRoad(g.getPlayer(pn), piece.getCoordinates(), g.getBoard()));
		}
		else if (piece.getType() == 1) {
			g.buySettlement(pn);
			g.putPiece(new SOCSettlement(g.getPlayer(pn), piece.getCoordinates(), g.getBoard()));
		}
		else if (piece.getType() == 2) {
			g.buyCity(pn);
			g.putPiece(new SOCCity(g.getPlayer(pn), piece.getCoordinates(), g.getBoard()));
		}
		else if (piece.getType() == 4) {
			g.buyDevCard();
		}
		return g;
	}

	/*
    protected void tradeWithBank(){
    	Vector settlements= ourPlayerData.getSettlements();
    	Vector cities= ourPlayerData.getCities();
    	boolean havePort= false;
    	for(int i=0; i<settlements.size(); i++){
    		int coord= ((SOCPlayingPiece) settlements.get(i)).getCoordinates();
    		if(game.getBoard().getPortTypeFromNodeCoord(coord)==SOCBoard.MISC_PORT)
    			havePort=true; 	
    	}

    	for(int i=0; i<cities.size(); i++){
    		int coord= ((SOCPlayingPiece) settlements.get(i)).getCoordinates();
    		if(game.getBoard().getPortTypeFromNodeCoord(coord)==SOCBoard.MISC_PORT)
    			havePort=true; 	
    	}

    	for(int i= 0; i<=SOCResourceConstants.WOOD; i++){
    		int numRes= ourPlayerData.getResources().getAmount(i);
    		SOCResourceSet give= new SOCResourceSet();
    		SOCResourceSet get= new SOCResourceSet();
    		get.add(1,(i+1)%SOCResourceConstants.WOOD);
    		if(numRes>=3 && havePort){
    			give.add(3,i);
    			client.bankTrade(game,give,get);   			
    		} else if(numRes>=4){
    			give.add(4,i);
    			client.bankTrade(game,give, get);
    		}

    	}

    }
	 */
	
	// Added by Ryan
	// Copies an SOCPlayingPiece into an identical SOCPossiblePiece, for placement
	private SOCPossiblePiece playAsPoss(SOCPlayingPiece p) {
		if(p instanceof SOCRoad)
			return new SOCPossibleRoad(p.getPlayer(), p.getCoordinates(), new Vector());
		if(p instanceof SOCSettlement)
			return new SOCPossibleSettlement(p.getPlayer(), p.getCoordinates(), new Vector());
		if(p instanceof SOCCity)
			return new SOCPossibleCity(p.getPlayer(), p.getCoordinates());
		return new SOCPossibleCard(p.getPlayer(), 1);
	}

	// Added by Ryan
	// Adds trades to a move, based on the resources pl needs to acquire to buy needSet
	// Returns the modified Move, or null if no trade can be made
	private Move addTrades(Move m, SOCResourceSet needSet, SOCPlayer pl) {
		SOCResourceSet haveSet = pl.getResources();
		if(haveSet.contains(needSet))
			// We already have the resources, don't need to wait
			return m;

		// excess contains exactly the resources that we have but don't need to pay costSet
		SOCResourceSet excess = haveSet.copy();
		excess.subtract(needSet);

		// needed contains exactly the resources that we need to get to pay costSet
		SOCResourceSet needed = needSet.copy();
		needed.subtract(haveSet);
		
		// we need to trade this many times to be successful
		int numTradesNeeded = needed.getTotal();

		// Note: ports = [has3to1, hasClay, hasOre, hasSheep, hasWheat, hasWood]
		// These match up with SOCResourceConstants
		boolean[] ports = pl.getPortFlags();
		int pn = pl.getPlayerNumber();

		// Go through excess in order, trying to trade
		for(int res=1; res<ports.length; res++) {
			int quantity = excess.getAmount(res);
			if(ports[res]) {
				int numTrades = quantity / 2;
				numTrades = Math.min(numTrades, numTradesNeeded);
				for(int i=0; i<numTrades; i++) {
					int j=1;
					while(j < ports.length && needed.getAmount(j) == 0)
						j++;
					if(j < ports.length) {
						excess.subtract(2, res);
						needed.subtract(1, j);
						SOCResourceSet give = new SOCResourceSet();
						give.add(2, res);
						SOCResourceSet get = new SOCResourceSet();
						get.add(1, j);
						m.addTrade(new SOCTradeOffer("", pn, null, give, get));
						numTradesNeeded--;
					}
				}
			}
			else if(ports[0]) {
				int numTrades = quantity / 3;
				numTrades = Math.min(numTrades, numTradesNeeded);
				for(int i=0; i<numTrades; i++) {
					int j=1;
					while(j < ports.length && needed.getAmount(j) == 0)
						j++;
					if(j < ports.length) {
						excess.subtract(3, res);
						needed.subtract(1, j);
						SOCResourceSet give = new SOCResourceSet();
						give.add(3, res);
						SOCResourceSet get = new SOCResourceSet();
						get.add(1, j);
						m.addTrade(new SOCTradeOffer("", pn, null, give, get));
						numTradesNeeded--;
					}
				}
			}
			else {
				int numTrades = quantity / 4;
				numTrades = Math.min(numTrades, numTradesNeeded);
				for(int i=0; i<numTrades; i++) {
					int j=1;
					while(j < ports.length && needed.getAmount(j) == 0)
						j++;
					if(j < ports.length) {
						excess.subtract(4, res);
						needed.subtract(1, j);
						SOCResourceSet give = new SOCResourceSet();
						give.add(4, res);
						SOCResourceSet get = new SOCResourceSet();
						get.add(1, j);
						m.addTrade(new SOCTradeOffer("", pn, null, give, get));
						numTradesNeeded--;
					}
				}
			}
		}

		if(numTradesNeeded > 0) {
			// We can't trade for the necessary resources
			return null;
		}
		
		return m;
	}
	
	// added by Katie
	// either completes one trade, buys a devCard, or requests a piece
	protected void buildOrGetResourceByTradeOrCard(){

		if(trades.size()!=0){
			// take the last trade
			SOCTradeOffer trade= trades.get(trades.size()-1);
			trades.remove(trades.size()-1);
			client.bankTrade(game, trade.getGiveSet(), trade.getGetSet());
			waitingForTradeMsg = true;
			pause(1500);
		} else if(devCardsToBuy>0){
			waitingForDevCard = true;
			devCardsToBuy--;
			client.buyDevCard(game);
		} else {
			SOCPlayingPiece piece= (SOCPlayingPiece) buildingPlan.pop();
			if(piece!=null){
				whatWeWantToBuild = piece;
				if(piece instanceof SOCRoad){
		            waitingForGameState = true;
		            counter = 0;
		            expectPLACING_ROAD = true;
		            client.buildRequest(game, SOCPlayingPiece.ROAD);
				} else if (piece instanceof SOCSettlement){
					waitingForGameState = true;
		            counter = 0;
		            expectPLACING_SETTLEMENT = true;
		            client.buildRequest(game, SOCPlayingPiece.SETTLEMENT);
				} else { // city
					waitingForGameState = true;
		            counter = 0;
		            expectPLACING_CITY = true;
		            client.buildRequest(game, SOCPlayingPiece.CITY);
				}
			}
		}
	}
	
	
	// Added by Ryan
	protected void buildOrGetResourceByTradeOrCard2() {
		/*
		while(!buildingPlan.empty()) {
			piece = (SOCPossiblePiece) buildingPlan.pop();
			if(tradeWithBank(SOCPlayingPiece.getResourcesToBuild(piece.getType())))
				buildingPlan.clear();
		}
		if(piece == null || !tradeWithBank(SOCPlayingPiece.getResourcesToBuild(piece.getType())))
			return;
			*/
		

		// Once moveToMake is implemented correctly, we'll have more than one piece
		SOCPossiblePiece piece = (SOCPossiblePiece) buildingPlan.pop();
		piece = (SOCPossiblePiece) buildingPlan.peek();
		moveToMake = new Move();
		//moveToMake.addPossPiece(piece);
		moveToMake = addTrades(moveToMake, SOCPlayingPiece.getResourcesToBuild(piece.getType()), ourPlayerData);
		
		
		if(!buildingPlan.empty()) {
			SOCPossiblePiece piece2 = (SOCPossiblePiece) buildingPlan.pop();
			Move move2 = new Move();
			SOCResourceSet cost2 = SOCPlayingPiece.getResourcesToBuild(piece.getType());
			cost2.add(SOCPlayingPiece.getResourcesToBuild(piece2.getType()));
			move2 = addTrades(move2, cost2, ourPlayerData);
			if(move2 != null) {
				moveToMake = new Move();
				//moveToMake.addPossPiece(piece);
				//moveToMake.addPossPiece(piece2);
				moveToMake = addTrades(moveToMake, cost2, ourPlayerData);
			}
		}
		
		int count = 0;
		if(moveToMake != null) {
			// make trades
			for(SOCTradeOffer t : moveToMake.getTrades()) {
				client.bankTrade(game, t.getGiveSet(), t.getGetSet());
				waitingForTradeMsg = true;
				pause(1500);
			}
			// make/place purchases
			/*
			for(SOCPossiblePiece p : moveToMake.getPossPieces()) {
				buildingPlan.clear();
				buildingPlan.push(p);
				count++;
				this.client.sendText(game, " trying to buy a "+p.toFriendlyString());
				if(count == 2)
					this.client.sendText(game, " sending it in...");
				buildRequestPlannedPiece(p);
				pause(1500);
				if(count == 2)
					this.client.sendText(game, " did we make it?");
			}
			*/
			return;
		}
	}

	/** Moves the robber when a seven is rolled (Nick)
	 * 
	 */
	protected void moveRobber() {
		//Find the best hex to place the robber
		int current = game.getBoard().getRobberHex();
		int [] board = game.getBoard().getHexLandCoords();
		int bestSpot = current;
		float bestVal = -1;
		float curVal = -1;
		for(int i=0;i<board.length;i++) {
			//Don't rank hex if ours or current robber hex
			if(ourPlayerData.getNumbers().getNumberResourcePairsForHex(board[i]).isEmpty() && board[i] != current) {
				curVal = FPROB[(game.getBoard().getNumberOnHexFromNumber(board[i]))]*rankHexValue(board[i]);
				//If value is higher, this becomes our best spot
				if (curVal > bestVal) {
					bestVal = curVal;
					bestSpot = board[i];
				}
			}
		}
		client.moveRobber(game, ourPlayerData, bestSpot);
	}

	/**
	 * Chooses victim with most confirmed VPs, or most resources if VPs are equal
	 */
	protected void chooseRobberVictim(boolean[] choices) {
		int choice = -1;
		for (int i = 0; i < game.maxPlayers; i++)
		{
			if (! game.isSeatVacant (i))
			{
				if (choices[i])
				{
					if (choice == -1)
					{
						choice = i;
					}
					else
					{
						SOCPlayer current = game.getPlayer(i);
						SOCPlayer best = game.getPlayer(choice);

						if (current.getPublicVP() > best.getPublicVP()) {
							choice = i;
						} else if (current.getPublicVP() == best.getPublicVP()) {
							if (current.getResources().getTotal() > best.getResources().getTotal()) {
								choice = i;
							}
						}
					}
				}
			}
		}

		client.choosePlayer(game, choice);
	}

	/** Returns the value given of a hex based on what is built there (Nick)
	 * Rank: 	1 point for settlements
	 * 			2 points for cities
	 * 
	 * @param hex coordinates of the hex to find the value of
	 * @return value of the hex
	 */
	private int rankHexValue(int hex) {
		int val = 0;
		for(int i=0;i<6;i++) {
			SOCPlayingPiece piece = game.getBoard().settlementAtNode(game.getBoard().getAdjacentNodeToHex(hex,i));
			if (piece instanceof SOCCity) {
				val = val+2;
			} else if (piece instanceof SOCSettlement) {
				val = val+1;
			}
		}
		return val;
	}

	/** Returns the player ID of the player to steal from (Nick)
	 * 
	 * @param hex coordinates of the hex to find the value of
	 * @return player ID
	 */
	// TODO Ryan: added the AI in this method to chooseRobberVictim(boolean[] choices) above
	private int playerToStealFrom(int hex) {
		//Possible players
		ArrayList<SOCPlayer> possible = new ArrayList<SOCPlayer>();
		for(int i=0;i<6;i++) {
			SOCPlayingPiece piece = game.getBoard().settlementAtNode(game.getBoard().getAdjacentNodeToHex(hex,i));
			if (piece != null && !possible.contains(piece.getPlayer())) {
				possible.add(piece.getPlayer());
			}
		}
		//Select the best player
		SOCPlayer best = possible.get(0);
		SOCPlayer current;
		for (int i=1;i<3;i++) {
			current = possible.get(i);
			if (current.getPublicVP() > best.getPublicVP()) {
				best = current;
			} else if (current.getPublicVP() == best.getPublicVP()) {
				if (current.getResources().getTotal() > best.getResources().getTotal()) {
					best = current;
				}
			}
		}
		return best.getPlayerNumber();
	}

	/** Returns the possible settlements a player can build (Nick)
	 * 
	 * @return Hash-table with probabilities as keys for node coordinates
	 */
	protected Hashtable<Float,Integer> possibleSettlementSpots() {
		Hashtable<Float,Integer> nodeProbPairs = new Hashtable<Float,Integer>();

		//Go through each node on the board adding those that are legal
		for (int i=SOCBoard.MINNODE; i<SOCBoard.MAXNODE;i++) {
			if(ourPlayerData.isPotentialSettlement(i)) {
				Vector<Integer> adjHexes = SOCBoard.getAdjacentHexesToNode(i);
				Float current = 0f;
				for(int j=0;j<adjHexes.size();j++) {
					current = current + FPROB[game.getBoard().getNumberOnHexFromNumber(adjHexes.get(i))];
				}
				// Add rarity index here (current version prevents hash collision)
				if (!nodeProbPairs.containsValue(current)) {
					nodeProbPairs.put(current,i);
				} else {
					nodeProbPairs.put(current + 0.0001f, i);
				}
			}
		}
		return nodeProbPairs;
	}

	/**
	 * Chooses the first settlement during the initial set-up phase (Nick)
	 */
	protected void pickFirstSettlement() {
		strategy = 0;

		//Obtain possible settlements
		/*
    	Hashtable<Float,Integer> possible = possibleSettlementSpots();
    	Float[] probs = possible.keySet().toArray(new Float[1]);
    	Arrays.sort(probs);	    	

    	//Choose best option
    	client.putPiece(game, new SOCSettlement(ourPlayerData,possible.get(probs[0]),null));
		 */

		// Just testing this out...

		//identify settlement
		double bestScore = 0.0;
		firstSettlement = -1;
		SOCBoard board = game.getBoard();
		for (int firstNode = board.getMinNode(); firstNode <= SOCBoard.MAXNODE; firstNode++)
		{
			if (ourPlayerData.isPotentialSettlement(firstNode))
			{
				double currentScore = 0.0;
				Integer firstNodeInt = new Integer(firstNode);
				Vector<Integer> hexes = (Vector<Integer>) SOCBoard.getAdjacentHexesToNode(firstNode).elements();
				for(Integer i: hexes) {
					currentScore += PROB[i];
				}
				if(firstSettlement == -1 || currentScore < bestScore) {
					firstSettlement = firstNodeInt;
				}
			}
		}

		//place it
		gameStrategy(firstSettlement,null);
		//pause(500);
		lastStartingPieceCoord = firstSettlement;
		client.putPiece(game, new SOCSettlement(ourPlayerData, firstSettlement, null));
		//pause(1000);
	}

	/** Chooses the second settlement during the initial set-up phase (Nick)
	 * 
	 */
	protected void pickSecondSettlement() {
		//Obtain possible settlements
		/*
    	Hashtable<Float,Integer> possible = possibleSettlementSpots();
    	Float[] probs = possible.keySet().toArray(new Float[1]);
    	Arrays.sort(probs);	    	

    	//Choose best option
    	client.putPiece(game, new SOCSettlement(ourPlayerData,possible.get(probs[0]),null));
		 */

		// Just testing this out...

		//identify settlement
		double bestScore = 0.0;
		secondSettlement = -1;
		SOCBoard board = game.getBoard();
		for (int secondNode = board.getMinNode(); secondNode <= SOCBoard.MAXNODE; secondNode++)
		{
			if (ourPlayerData.isPotentialSettlement(secondNode))
			{
				double currentScore = 0.0;
				Integer secondNodeInt = new Integer(secondNode);
				Vector<Integer> hexes = (Vector<Integer>) SOCBoard.getAdjacentHexesToNode(secondNode).elements();
				for(Integer i: hexes) {
					currentScore += PROB[i];
				}
				if(secondSettlement == -1 || currentScore < bestScore) {
					secondSettlement = secondNodeInt;
				}
			} 
		}

		//place it
		gameStrategy(firstSettlement,secondSettlement);
		//pause(500);
		lastStartingPieceCoord = secondSettlement;
		client.putPiece(game, new SOCSettlement(ourPlayerData, secondSettlement, null));
		//pause(1000);
	}

	/** Chooses where to build roads during the initial set-up phase (Nick)
	 * 
	 * @param node coordinate where the road must start from
	 */
	protected void pickInitialRoad(int node) {
		int[] choices = game.getBoard().getAdjacentEdgesToNode_arr(node);

		//randomly pick road direction
		int rand = (int)(Math.random()*(3));

		//Pick based on possible future progress
		int bestSpot = choices[0];
		float bestVal = -1;
		float curVal = -1;
		for (int i=0;i < 3;i++) {
			if(choices[i] != -1) {
				Vector<Integer> nextRoads = game.getBoard().getAdjacentEdgesToEdge(choices[i]);
				for (int j=0;j<nextRoads.size();j++) {
					if(nextRoads.get(j) != -1) {
						Vector<Integer> nextNodes = game.getBoard().getAdjacentNodesToEdge(nextRoads.get(j));
						for (int k=0;k<nextNodes.size();k++) {
							if (ourPlayerData.isPotentialSettlement(nextNodes.get(k))) {
								Vector<Integer> adjHexes = SOCBoard.getAdjacentHexesToNode(nextNodes.get(k));
								curVal = 0;
								for(int l=0;l<adjHexes.size();l++) {
									curVal = curVal + FPROB[game.getBoard().getNumberOnHexFromNumber(adjHexes.get(l))];
								}
								if (curVal > bestVal) {
									bestVal = curVal;
									bestSpot = choices[i];
								}
							}
						}
					}
				}
			}
		}

		client.putPiece(game, new SOCRoad(ourPlayerData,choices[rand],null));
		//client.putPiece(game, new SOCRoad(player,bestSpot,null));
	}

	/** Returns the rarity index (probability distribution) for resources on the board (Nick)
	 * 
	 * @return Vector of float probabilities with indexes(in order): Other, Clay, Ore, Sheep, Wheat, Wood
	 */
	private Vector<Float> rarityIndex() {
		Vector<Float> index = new Vector<Float>();
		for(int i=0;i<6;i++) {
			index.add(i, 0f);
		}
		int [] board = game.getBoard().getHexLandCoords();
		for(int i=0;i<board.length;i++) {
			float curProb = FPROB[game.getBoard().getNumberOnHexFromNumber(board[i])];
			switch (game.getBoard().getHexTypeFromCoord(board[i])) {
			case SOCResourceConstants.CLAY:
				index.set(SOCResourceConstants.CLAY, index.get(SOCResourceConstants.CLAY)+curProb);
				break;
			case SOCResourceConstants.ORE:
				index.set(SOCResourceConstants.ORE, index.get(SOCResourceConstants.ORE)+curProb);
				break;
			case SOCResourceConstants.SHEEP:
				index.set(SOCResourceConstants.SHEEP, index.get(SOCResourceConstants.SHEEP)+curProb);
				break;	
			case SOCResourceConstants.WHEAT:
				index.set(SOCResourceConstants.WHEAT, index.get(SOCResourceConstants.WHEAT)+curProb);
				break;
			case SOCResourceConstants.WOOD:
				index.set(SOCResourceConstants.WOOD, index.get(SOCResourceConstants.WOOD)+curProb);
				break;
			default:
				//Add all other hexes to first index for debugging purposes
				index.set(0, index.get(0)+curProb);
			}
		}
		return index;
	}

	/** Sets the game strategy for our agent using a combination of rarity index and settlements available (Nick)
	 * 
	 * @param firstSettlement node coordinates of first settlement location
	 * @param secondSettlement node coordinates of second settlement location (null if only first chosen)
	 */
	private void gameStrategy(int firstSettlement, Integer secondSettlement) {
		Vector<Float> rarity = rarityIndex();
		Vector<Integer> firstHexes = (Vector<Integer>) SOCBoard.getAdjacentHexesToNode(firstSettlement);
		Integer temp = firstHexes.get(0);
		if (PROB[game.getBoard().getNumberOnHexFromCoord(firstHexes.get(0))] > FPROB[game.getBoard().getNumberOnHexFromCoord(firstHexes.get(1))]) {
			temp = firstHexes.get(1);
		} else {
			temp = firstHexes.get(2);
		}
		switch (game.getBoard().getHexTypeFromCoord(temp)) {
		case SOCResourceConstants.CLAY:
			strategy = 1;
			break;
		case SOCResourceConstants.ORE:
			strategy = 2;
			break;
		case SOCResourceConstants.SHEEP:
			strategy = 0;
			break;	
		case SOCResourceConstants.WHEAT:
			strategy = 2;
			break;
		case SOCResourceConstants.WOOD:
			strategy = 1;
			break;
		default:
			//Add all other hexes to first index for debugging purposes
			strategy = 0;
		}
		/*
    	if(secondSettlement != null) {
    		Vector<Integer> secondHexes = (Vector<Integer>) SOCBoard.getAdjacentHexesToNode(secondSettlement);
    	}*/

	}

	/** Prints out list of resources for debugging purposes */
	private void printResources(SOCResourceSet set){
		client.sendText(game, ourPlayerData.getName()+" "+set.toFriendlyString());
	}



	/** Added by Katie
	 * 
	 * @return expected number of each type of resource per roll
	 */
	protected double[] expectedNumResources(){
		double[] resources= new double[6];
		Vector settlements= ourPlayerData.getSettlements();
		for (int i=0; i<=settlements.size(); i++){
			int coord= ((SOCSettlement) settlements.get(i)).getCoordinates();
			int type= game.getBoard().getHexTypeFromCoord(coord);
			int number= game.getBoard().getNumberOnHexFromNumber(coord);
			resources[type-1]+= PROB[number];
		}
		Vector cities= ourPlayerData.getCities();
		for (int i=0; i< cities.size(); i++){
			int coord= ((SOCCity) cities.get(i)).getCoordinates();
			int type= game.getBoard().getHexTypeFromCoord(coord);
			int number= game.getBoard().getNumberOnHexFromNumber(coord);
			resources[type-1]+= 2*PROB[number];
		}
		return resources;
	}

	// Added by Ryan
	// Returns true if we can trade with the bank to get the resources in needSet, and asks client to do so
	// Returns false otherwise
	/*
	protected boolean tradeWithBank (SOCResourceSet costSet) {
		//this.client.sendText(this.game, " trading?");
		if(ourPlayerData.getResources().contains(costSet))
			// We already have the resources, don't need to wait
			return false;

		// excess contains exactly the resources that we have but don't need to pay costSet
		SOCResourceSet excess = ourPlayerData.getResources().copy();
		excess.subtract(costSet);

		// needed contains exactly the resources that we need to get to pay costSet
		SOCResourceSet needed = costSet.copy();
		needed.subtract(ourPlayerData.getResources());

		SOCResourceSet givenSet = new SOCResourceSet();

		// we need to trade this many times to be successful
		int numTradesNeeded = needed.getTotal();

		// Note: ports = [has3to1, hasClay, hasOre, hasSheep, hasWheat, hasWood]
		// These match up with SOCResourceConstants
		boolean[] ports = ourPlayerData.getPortFlags();

		// Go through excess in order, trying to trade
		for(int res=1; res<ports.length; res++) {
			int quantity = excess.getAmount(res);
			if(ports[res]) {
				int numTrades = quantity / 2;
				numTrades = Math.min(numTrades, numTradesNeeded);
				excess.subtract(2*numTrades, res);
				givenSet.add(2*numTrades, res);
				numTradesNeeded -= numTrades;
			}
			else if(ports[0]) {
				int numTrades = quantity / 3;
				numTrades = Math.min(numTrades, numTradesNeeded);
				excess.subtract(3*numTrades, res);
				givenSet.add(3*numTrades, res);
				numTradesNeeded -= numTrades;
			}
			else {
				int numTrades = quantity / 4;
				numTrades = Math.min(numTrades, numTradesNeeded);
				excess.subtract(4*numTrades, res);
				givenSet.add(4*numTrades, res);
				numTradesNeeded -= numTrades;
			}
		}

		if(numTradesNeeded > 0) {
			//this.client.sendText(this.game, " no trade to be made.");
			// We can't trade for the necessary resources
			return false;
		}
		if(!game.canMakeBankTrade(givenSet, needed))
			// maybe the bank is out of resources?
			return false;
		
		//this.client.sendText(this.game, " sending offer: "+givenSet.toFriendlyString()+" for "+needed.toFriendlyString());
		// Success! Put in the call to the client.
		//this.client.sendText(this.game, " needs {"+costSet.toFriendlyString()+"}");
		//this.client.sendText(this.game, " has {"+ourPlayerData.getResources().toFriendlyString()+"}");
		//this.client.sendText(this.game, " trades {"+givenSet.toFriendlyString()+"} for {"+needed.toFriendlyString()+"}" );
		//client.bankTrade(game, givenSet, needed);
		//waitingForTradeMsg = true;
		//pause(1500);
		//this.client.sendText(this.game, " has {"+ourPlayerData.getResources().toFriendlyString()+"} after trading");
		// this.client.sendText(this.game, " done trading.");
		return true;
	}
	*/


	protected void tradeWithBank(){
		Vector settlements= ourPlayerData.getSettlements();
		Vector cities= ourPlayerData.getCities();
		boolean havePort= false;
		for(int i=0; i<settlements.size(); i++){
			int coord= ((SOCPlayingPiece) settlements.get(i)).getCoordinates();
			if(game.getBoard().getPortTypeFromNodeCoord(coord)==SOCBoard.MISC_PORT)
				havePort=true; 	
		}

		for(int i=0; i<cities.size(); i++){
			int coord= ((SOCPlayingPiece) settlements.get(i)).getCoordinates();
			if(game.getBoard().getPortTypeFromNodeCoord(coord)==SOCBoard.MISC_PORT)
				havePort=true; 	
		}

		for(int i= 0; i<=SOCResourceConstants.WOOD; i++){
			int numRes= ourPlayerData.getResources().getAmount(i);
			SOCResourceSet give= new SOCResourceSet();
			SOCResourceSet get= new SOCResourceSet();
			get.add(1,(i+1)%SOCResourceConstants.WOOD);
			if(numRes>=3 && havePort){
				give.add(3,i);
				client.bankTrade(game,give,get);   			
			} else if(numRes>=4){
				give.add(4,i);
				client.bankTrade(game,give, get);
			}

		}

	}


	/** Added by Katie 
	 * A Move is a list of actions to perform in a turn, including
	 * optionally a devCard to play, a series of bankTrades to make
	 * and a series of pieces (including devCards to buy)*/

	private class Move {
		private Vector<SOCTradeOffer> trades; // trades to make with the bank
		private Vector<SOCPlayingPiece> pieces; // pieces to buy
		private int buyDevCards; // the number of devCards to buy
		private int devCard; // type of devCard to play, -1 if no devCard to play

		public Move(){
			trades= new Vector<SOCTradeOffer>();
			pieces= new Vector<SOCPlayingPiece>();
			buyDevCards= 0;
			devCard= -1; 
		}

		public String toString(){
			String str= "";
			for(SOCPlayingPiece p: pieces){
				str= str+"Piece "+p.getType()+", ";
			}
			str= str+ "buy " + buyDevCards + " dev Cards";
			return str;
		}
		
		public void addTrade(SOCTradeOffer t){
			trades.add(t);
		}

		public void addPiece(SOCPlayingPiece p){
			pieces.add(p);
		}
		

		public void setDevCards(int i){
			buyDevCards=i;
		}

		public Vector<SOCTradeOffer> getTrades(){
			return trades;
		}

		public Vector<SOCPlayingPiece> getPieces(){
			return pieces;
		}
		public int devCardsToBuy(){
			return buyDevCards;
		}
		public int devCardToPlay(){
			return devCard;
		}
	}

	
    // Added by Ryan
    // Modified by Katie: returns true if player can ... Does not request the trade
    // Returns true if we can trade with the bank to get the resources in needSet, and asks client to do so
    // Returns false otherwise
    protected SOCTradeOffer tradeWithBank (SOCResourceSet costSet, SOCGame ga, int player) {
    	SOCPlayer PlayerData= ga.getPlayers()[player];
    	if(PlayerData.getResources().contains(costSet))
    		// We already have the resources, don't need to wait
    		return null;

    	
    	// excess contains exactly the resources that we have but don't need to pay costSet
    	SOCResourceSet excess = PlayerData.getResources().copy();
    	excess.subtract(costSet);
    	
    	// needed contains exactly the resources that we need to get to pay costSet
    	SOCResourceSet needed = costSet.copy();
    	needed.subtract(PlayerData.getResources());
    	
    	SOCResourceSet givenSet = new SOCResourceSet();
    	
    	// we need to trade this many times to be successful
    	int numTradesNeeded = needed.getTotal();
    	
    	// Note: ports = [has3to1, hasClay, hasOre, hasSheep, hasWheat, hasWood]
    	// These match up with SOCResourceConstants
    	boolean[] ports = PlayerData.getPortFlags();
    	
    	// Go through excess in order, trying to trade
    	for(int res=1; res<ports.length; res++) {
    		int quantity = excess.getAmount(res);
    		if(ports[res]) {
    			int numTrades = quantity / 2;
    			numTrades = Math.min(numTrades, numTradesNeeded);
    			excess.subtract(2*numTrades, res);
    			givenSet.add(2*numTrades, res);
    			numTradesNeeded -= numTrades;
    		}
    		else if(ports[0]) {
    			int numTrades = quantity / 3;
    			numTrades = Math.min(numTrades, numTradesNeeded);
    			excess.subtract(3*numTrades, res);
    			givenSet.add(3*numTrades, res);
    			numTradesNeeded -= numTrades;
    		}
    		else {
    			int numTrades = quantity / 4;
    			numTrades = Math.min(numTrades, numTradesNeeded);
    			excess.subtract(4*numTrades, res);
    			givenSet.add(4*numTrades, res);
    			numTradesNeeded -= numTrades;
    		}
    	}
    	
    	if(numTradesNeeded > 0) {
    		//this.client.sendText(this.game, " no trade to be made.");
    		// We can't trade for the necessary resources
        	return null;
    	}
    	//this.client.sendText(this.game, " sending offer: "+givenSet.toFriendlyString()+" for "+needed.toFriendlyString());
    	// Success! Put in the call to the client.
    /*	this.client.sendText(this.game, " needs {"+costSet.toFriendlyString()+"}");
    	this.client.sendText(this.game, " has {"+PlayerData.getResources().toFriendlyString()+"}");
    	this.client.sendText(this.game, " trades {"+givenSet.toFriendlyString()+"} for {"+needed.toFriendlyString()+"}" );
*/
    	//TODO: Katie moved this to buildOrGetResourceByTrade...
    	
    	//client.bankTrade(game, givenSet, needed);
		//waitingForTradeMsg = true;
        //pause(500);
    	//this.client.sendText(this.game, " has {"+PlayerData.getResources().toFriendlyString()+"} after trading");
       // this.client.sendText(this.game, " done trading.");
    	// ignore everything except givenSet and needed...
        return new SOCTradeOffer("", -1, new boolean[4], givenSet, needed);
    }
    
    
    
    // SOCGame.ROAD_SET
    
    private int numTrue(boolean[] a){
    	int sum=0;
    	for(int i=0; i<a.length; i++){
    		if (a[i]) sum++;
    	}
    	return sum;
    }
    
    
    private class Node{
    	private int[] buildingCombo;
    	private SOCTradeOffer trade;
    	private Vector<Node> children;
    	
    	public Node(int[] build, SOCTradeOffer trade){
    		buildingCombo= build;
    		this.trade= trade;
    		children= new Vector<Node>();
    	}
    	
    	public void addChild(Node n){
    		children.add(n);
    	}
    	public int[] getBuildSet(){
    		return buildingCombo;
    	}
    }
    
    /** returns the total number of resources needed to build this plan */
    private SOCResourceSet sumOfPlan(int[] plan){
    	SOCResourceSet roads= SOCGame.ROAD_SET.copy();
		roads.times(plan[0]);
		SOCResourceSet settlements= SOCGame.SETTLEMENT_SET.copy();
    	settlements.times(plan[1]);
		SOCResourceSet cities= SOCGame.CITY_SET.copy();
	    cities.times(plan[2]);
	    SOCResourceSet cards= SOCGame.CARD_SET.copy();
		cards.times(plan[3]);
		cards.add(roads);
		cards.add(settlements);
		cards.add(cities);
		return cards;
    }
        
    private static String arrToStr(int [] arr){
    	String str= "";
    	for(int i:arr){
    		str= str+i;
    	}
    	return str;
    }
    
    
    //TODO: Katie: no duplicates in the tree
    // take care of more than one road, city, etc.
    // Ryan: For a given set of size n, we'd have n! duplicates... that's bad, but how often are we going to see sets of size more than, say, 4?
    
    
    
    /** Added by Katie: builds a tree */
    private void buildTree(Node node, SOCGame game, int player){
    	SOCPlayer PlayerData= game.getPlayer(player);
    	boolean[] potentialSettlements= PlayerData.getPotentialSettlements();
    	boolean[] potentialRoads= PlayerData.getPotentialRoads();
    	boolean[] potentialCities= PlayerData.getPotentialCities();
    	int numSet= numTrue(potentialSettlements);
    	int numRoad= numTrue(potentialRoads);
    	int numCit= numTrue(potentialCities);
    	
    	HashSet<String> combos= new HashSet<String>();
    	int[] build= node.getBuildSet();
    	SOCResourceSet resources= game.getPlayers()[player].getResources();
    	for(int i=0; i<build.length; i++){
    		if(build[i]<2){
	    		build[i]++;
	    		if(i==0&&build[i]>numRoad || i==1&&build[i]>numSet ||i==2&&build[i]>numCit){
	    			build[i]--;
	    			return;
	    		}
	    		String next= arrToStr(build);
	    		if(combos.contains(next)) {
	    			build[i]--;
	    			return; }
	    		combos.add(next);
	    		SOCResourceSet wanted= sumOfPlan(build);
	    		if(SOCResourceSet.gte(resources, wanted)){
	    			Node child= new Node((int[])build.clone(), null);
	    			buildTree(child, game, player);
	    			node.addChild(child);
	    		} else {
		    		SOCTradeOffer trade=tradeWithBank(wanted, game, player);
		    		if(trade!=null){
		    			Node child= new Node((int[])build.clone(),trade);
		    			buildTree(child,game, player);
		    			node.addChild(child);
		    		}
	    		}
	    		build[i]--;
    		}
    	}
    }
    
    private int[] fillArr(int size, boolean[] arr) {
    	int[] ret = new int[size];
    	int j=0;
    	for(int i=0; i<arr.length; i++) {
    		if(arr[i]) {
    			ret[j] = i;
    			j++;
    		}
    	}
    	return ret;
    }
    
    /** Add all possible moves with this combination of things to build*/
    private void addMoves(Vector<Move> moves, Node node, SOCGame game, int player){
    	SOCPlayer PlayerData= game.getPlayer(player);
    	boolean[] potentialSettlements= PlayerData.getPotentialSettlements();
    	boolean[] potentialRoads= PlayerData.getPotentialRoads();
    	boolean[] potentialCities= PlayerData.getPotentialCities();
    	int numSet= numTrue(potentialSettlements);
    	int numRoad= numTrue(potentialRoads);
    	int numCit= numTrue(potentialCities);
    	
    	int[] Roads= fillArr(numRoad, potentialRoads);
    	int[] Sets= fillArr(numSet, potentialSettlements);
    	int[] Cities= fillArr(numCit, potentialCities);
    	
    	int [] combo= node.getBuildSet();
    	  	
    	//client.sendText(game,"Combo: "+arrToStr(combo));
    	
    	int roadChoices= choose(numRoad,combo[0]);
    	int setChoices= choose(numSet,combo[1]);
    	int cityChoices= choose(numCit,combo[2]); 
    	
    	int poss= roadChoices*setChoices*cityChoices;
    	
    	//client.sendText(game,"Road choices: "+roadChoices+" settle choices: "
    	//		+ setChoices+ " city choices: "+cityChoices);
    	
    	//client.sendText(game,"Number of combos: "+poss);
    	    	
    	if(combo[0] > 0 && combo[1] > 0 && combo[2] > 0) {
    		for(int r=0; r<Roads.length; r++) {
        		for(int s=0; s<Sets.length; s++) {
        			for(int c=0; c<Cities.length; c++) {
        				Move move= new Move();
        				move.addPiece(new SOCRoad(PlayerData,Roads[r],game.getBoard()));
        				move.addPiece(new SOCSettlement(PlayerData,Sets[s],game.getBoard()));
        				move.addPiece(new SOCCity(PlayerData, Cities[c], game.getBoard()));
        				move.setDevCards(combo[3]);
        	    		moves.add(move);
        			}
        		}
        	}
    	}
    	else if(combo[0] > 0 && combo[2] > 0) {
    		for(int r=0; r<Roads.length; r++) {
    			for(int c=0; c<Cities.length; c++) {
    				Move move= new Move();
    				move.addPiece(new SOCRoad(PlayerData,Roads[r],game.getBoard()));
    				move.addPiece(new SOCCity(PlayerData, Cities[c], game.getBoard()));
    				move.setDevCards(combo[3]);
    	    		moves.add(move);
    			}
        	}
    	}
    	else if(combo[0] > 0 && combo[1] > 0) {
    		for(int r=0; r<Roads.length; r++) {
        		for(int s=0; s<Sets.length; s++) {
        			Move move= new Move();
        			move.addPiece(new SOCRoad(PlayerData,Roads[r],game.getBoard()));
        			move.addPiece(new SOCSettlement(PlayerData,Sets[s],game.getBoard()));
        			move.setDevCards(combo[3]);
        	    	moves.add(move);
        		}
        	}
    	}
    	else if(combo[1] > 0 && combo[2] > 0) {
    		for(int c=0; c<Cities.length; c++) {
        		for(int s=0; s<Sets.length; s++) {
        			Move move= new Move();
    				move.addPiece(new SOCCity(PlayerData, Cities[c], game.getBoard()));
        			move.addPiece(new SOCSettlement(PlayerData,Sets[s],game.getBoard()));
        			move.setDevCards(combo[3]);
        	    	moves.add(move);
        		}
        	}
    	}
    	else if(combo[0]>0) {
        	for(int r=0; r<Roads.length; r++) {
        		Move move= new Move();
    			move.addPiece(new SOCRoad(PlayerData,Roads[r],game.getBoard()));
        		move.setDevCards(combo[3]);
        	   	moves.add(move);
        	}
    	}
    	else if(combo[1]>0) {
        	for(int s=0; s<Sets.length; s++) {
        		Move move= new Move();
        		move.addPiece(new SOCSettlement(PlayerData,Sets[s],game.getBoard()));
        		move.setDevCards(combo[3]);
        	   	moves.add(move);
        	}
    	}
    	else if(combo[2] > 0) {
    		for(int c=0; c<Cities.length; c++) {
        		Move move= new Move();
    			move.addPiece(new SOCCity(PlayerData, Cities[c], game.getBoard()));
        		move.setDevCards(combo[3]);
        	    moves.add(move);
        	}
    	}
    	else {
    		Move move= new Move();
    		move.setDevCards(combo[3]);
    	    moves.add(move);
    	}
    	
    	/*
    	for(int r=-1; r<Roads.length; r++) {
    		for(int s=-1; s<Sets.length; s++) {
    			for(int c=-1; c<Cities.length; c++) {
    				Move move= new Move();
    				if(r>=0 && combo[0] > 0)
    					move.addPiece(new SOCRoad(PlayerData,Roads[r],game.getBoard()));
    				if(s>=0 && combo[1] > 0)
    					move.addPiece(new SOCSettlement(PlayerData,Sets[s],game.getBoard()));
    				if(c>=0 && combo[2] > 0)
    					move.addPiece(new SOCCity(PlayerData, Cities[c], game.getBoard()));
    				move.setDevCards(combo[3]);
    	    		if(combo[3] >=1)
    	    			move.setDevCards(1);
    	    		//client.sendText(game,"Added move "+j+" to consider");
    	    		// add the move to the set
    	    		moves.add(move);
    			}
    		}
    	}
    	*/
    
    	
    	
    	/*
    
    	for(int j=0; j<poss; j++){
				
    		Move move= new Move();
    		int road= j/setChoices/cityChoices;
    		int set= (j/cityChoices)%setChoices;
    		int city= j%cityChoices;
    		    		
    		if(combo[0]==1){
    			SOCRoad r= new SOCRoad(PlayerData,Roads[road],game.getBoard());

        		//client.sendText(game, " adding a road at "+r.getCoordinates());
    			move.addPiece(r);
    		} else if(combo[0]==2) {
            	int first=0;
            	int second= road+1;
            	int i= 1;
            	while(road>= i*numRoad-(i*(i-1)/2)-1){
            		first= i;
            		second= road- numRoad+((i+1)*i/2)+1;
            		i++;
            	}
            	SOCRoad r1= new SOCRoad(PlayerData, Roads[first],game.getBoard());
            	SOCRoad r2= new SOCRoad(PlayerData, Roads[second], game.getBoard());
            	move.addPiece(r1);
            	move.addPiece(r2);
    		}
    		if(combo[1]==1){
    			SOCSettlement s= new SOCSettlement(PlayerData,Sets[set],game.getBoard());
    			move.addPiece(s);
    		} else if(combo[1]==2){
            	int first=0;
            	int second= set+1;
            	int i= 1;
            	while(set>= i*numSet-(i*(i-1)/2)-1){
            		first= i;
            		second= set- numSet+((i+1)*i/2)+1;
            		i++;
            	}
            	SOCSettlement s1= new SOCSettlement(PlayerData, Sets[first],game.getBoard());
            	SOCSettlement s2= new SOCSettlement(PlayerData, Sets[second], game.getBoard());
            	move.addPiece(s1);
            	move.addPiece(s2);
    		}
    		if(combo[2]==1){
    			SOCCity c= new SOCCity(PlayerData,Cities[city],game.getBoard());
    			move.addPiece(c);
    		} else if(combo[2]==2){
            	int first=0;
            	int second= city+1;
            	int i= 1;
            	while(city>= i*numCit-(i*(i-1)/2)-1){
            		first= i;
            		second= city- numCit+((i+1)*i/2)+1;
            		i++;
            	}
            	SOCCity c1= new SOCCity(PlayerData, Cities[first],game.getBoard());
            	SOCCity c2= new SOCCity(PlayerData, Cities[second], game.getBoard());
            	move.addPiece(c1);
            	move.addPiece(c2);
    		}
    		move.setDevCards(combo[3]);
    		if(combo[3] >=1)
    			move.setDevCards(1);
    		//client.sendText(game,"Added move "+j+" to consider");
    		// add the move to the set
    		moves.add(move);
    		
>>>>>>> 1.35
    	}
    	*/
		for (Node child:node.children) {
			//client.sendText(game,"Size of moves: "+moves.size());
			addMoves(moves,child,game,player);
		}
    }
    
    
    protected Vector<Move> possibleMoves(SOCGame game, int player){
    	Node root= new Node(new int[4], null);
    	buildTree(root, game, player);
    	Vector<Move> moves= new Vector<Move>();
    	// Add the moves for all nodes in the tree
    	//client.sendText(game,"Start Adding moves");
    	addMoves(moves, root,game,player);
    	return moves;    	
    }
    
    
    /** Added by Katie- returns a Vector of all possible moves in a given turn
     * a move consists of a set of trades, a set of SOCpieces to build, and a
     * devCard to play
     * @return 
     */
  
    //TODO: Note*** Always trade first, then build cities, then the rest

  /*  protected Vector<Move> possibleMoves(SOCGame game, int player){
    	SOCPlayer PlayerData= game.getPlayer(player);
    	Vector<Move> moves= new Vector<Move>();
    	
    	SOCResourceSet resources= PlayerData.getResources().copy();
    	
    	boolean[] potentialSettlements= PlayerData.getPotentialSettlements();
    	boolean[] potentialRoads= PlayerData.getPotentialRoads();
    	boolean[] potentialCities= PlayerData.getPotentialCities();
    	int numSet= numTrue(potentialSettlements);
    	int numRoad= numTrue(potentialRoads);
    	int numCit= numTrue(potentialCities);
    	
    	int remainingSettlements= PlayerData.getNumPieces(SOCPlayingPiece.SETTLEMENT);
    	
    	/*
    	int maxRoads=0;
    	while(SOCResourceSet.gte(resources,SOCGame.ROAD_SET)){
    		resources.subtract(SOCGame.ROAD_SET);
    		maxRoads++;
    	}
    	// min of roads available and resource limitations
    	maxRoads= Math.min(maxRoads,PlayerData.getNumPieces(SOCPlayingPiece.ROAD));
    	
    	// reset resources to initial values
    	resources= PlayerData.getResources().copy();
    	int maxSettlements=0;
    	while(SOCResourceSet.gte(resources,SOCGame.SETTLEMENT_SET)){
    		resources.subtract(SOCGame.SETTLEMENT_SET);
    		maxSettlements++;
    	} // can build a city in which case we have a settlement free
    	//maxSettlements= Math.min(maxSettlements, PlayerData.getNumPieces(SOCPlayingPiece.SETTLEMENT));
    	resources= PlayerData.getResources().copy();
    	int maxCities=0;
    	while(SOCResourceSet.gte(resources,SOCGame.CITY_SET)){
    		resources.subtract(SOCGame.CITY_SET);
    		maxCities++;
    	}
    	maxCities= Math.min(maxCities,PlayerData.getNumPieces(SOCPlayingPiece.CITY));
    	
    	resources= PlayerData.getResources().copy();
    	int maxDevCards=0;
    	while(SOCResourceSet.gte(resources,SOCGame.CARD_SET)){
    		resources.subtract(SOCGame.CARD_SET);
    		maxDevCards++;
    	}    
    	maxDevCards= Math.min(maxDevCards,game.getNumDevCards());
    	
    	// building combos contains an array for each possible combination of number
    	// of each type of thing to be built
    	// e.g. [0 1 0 0] [1 1 0 0] [2 0 0 0] [1 0 0 0] [0 0 0 0] means:
    	// nothing built, or 1 road, or 2 roads, or 1 road 1 settlement, or 1 settlement 
    	Vector<int[]> buildingCombos= new Vector<int[]>();
    	
    	    	
    	resources= PlayerData.getResources().copy();
    	for(int r=0; r<maxRoads; r++){
    		SOCResourceSet roads= SOCGame.ROAD_SET.copy();
    		roads.times(r);
    		for(int s=0; s<maxSettlements; s++){
    			SOCResourceSet settlements= SOCGame.SETTLEMENT_SET.copy();
        		settlements.times(s);
    			for(int c=0; c<maxCities; c++){
    				SOCResourceSet cities= SOCGame.CITY_SET.copy();
    	    		cities.times(c);
    				for(int d=0; d<maxDevCards; d++){
    					SOCResourceSet dCards= SOCGame.CARD_SET.copy();
    		    		roads.times(d);
    		    		
    		    		dCards.add(roads);
    		    		dCards.add(settlements);
    		    		dCards.add(cities);
    		    		
    		    		// we get a settlement back to use when we build a city
    		    		if(SOCResourceSet.gte(resources,dCards) && s-c<=remainingSettlements){
    		    			int [] combo= new int[] {r, s, c, d};
    		    			buildingCombos.add(combo);
    		    		}
    					
    				}
    			}
    		}
    	}
    	*/
    	
    	// [road set cit dev]
    	// all possible Moves based just on roads
    	/*Vector<Vector<Integer>> roadCombos= new Vector<Vector<Integer>>();
    	
    	for(int i=0; i<buildingCombos.size(); i++){
    		int[] combo= buildingCombos.get(i);
    		int roadsLeft= combo[0];
    		int roadsBuilt= 0;
    	//	int potRoads= numTrue(potentialRoads);
    		for(int j=0; j<potentialRoads.length; j++){
    			if(potentialRoads[j]){
	    			Vector<Integer> roadList= new Vector<Integer>();
	    			roadList.add(j);
	    			roadCombos.add(roadList);
	    			break;
    			}
    		}
    		roadsBuilt++;
    		roadsLeft--;
    		while(roadsLeft>0){
    			for(int j=0; j<roadCombos.size(); j++){
    				Vector<Integer> roadList= roadCombos.get(j);
    				if(roadList.size()==roadsBuilt-1){
    					// clone the game and add the road, then add all potential roads
    					for(int k=0; k<roadList.size(); k++){
    						SOCRoad r= new SOCRoad(PlayerData,k,game.getBoard());
    					}
    				}
    			}
    		}
    	}*/
    	
    /*	for(int i=0; i<buildingCombos.size(); i++){
    		int[] combo= buildingCombos.get(i);
    		int poss= choose(numRoad,combo[0])*choose(numSet,combo[1])
    					*choose(numCit,combo[2])*combo[3];
    		for(int r=0; r<choose(numRoad,combo[0]); r++){
    			
    			if(combo[0]>1){
    				
    			} else if(combo[0]==1){
    				
    			} else{
    				
    			}
    			
    		}
    		
    	}
    	
    	boolean[] portFlags= PlayerData.getPortFlags();
    	
        boolean[] to= new boolean[game.maxPlayers];
    	for (int i = 0; i < game.maxPlayers; i++)
        {
            to[i] = false;
        }

	/** will hold weight information for evaluation function */
	double [] weights= {230.0, 520.0, 5.0,
			0.1, 0.1, 0.1, 10.0, 10.0, 10.0, 10.0, 10.0, 10.0, // dev cards
			//0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,  // dev cards
			3.0, 3.0, 1.0, 1.0, 2.0, // expected resources
			5.0, 5.0, 5.0, 5.0, 5.0, // ports
			7.0, -5.0, 7.0, -5.0, 1.0, 6.0, -1.0 // army, road, misc		
	};

	/** Added by Katie
	 * returns a float estimated value of the current board state
	 * for game ga for player player
	 * 
	 * number of settlements
       return moves;    	
    	
    }
    */
    private int fact(int x){
    	if (x<=1) return 1;
    	else return x*fact(x-1);
    }
    
    /** return xCy, but if x=0,  return 1 */
    private int choose(int x, int y){
    	int res= fact(x)/(fact(y)*fact(x-y));
    	return res>0?res:1;
    }
    
    
    /** Added by Katie
     * returns a float estimated value of the current board state
     * for game ga for player player
     * 
     * number of settlements
		number of cities
		number of each type of resource
		have a 3:1 port?
		number of each type of unplayed dev card
		Expected number of each type of resource per turn
		Expected number of each type of resource per turn for each 2:1 port owned
		have largest army?
		size of largest army- size of our army +1
		have longest road?
		length of longest road- length of our longest road +1
		number spots available to build settlements
		number spots available to build roads?

	 * 
	 */

	protected double evaluateBoard(SOCGame ga, int player){
		//client.sendText(game, " entering eval");
		SOCPlayer playerData= ga.getPlayers()[player];
		int numSettlements= playerData.getSettlements().size();
		int numCities= playerData.getCities().size();
		int haveMiscPort= (playerData.getPortFlag(SOCBoard.MISC_PORT)? 1:0);
		int[] devCards= playerData.getDevCards().getTotalTypes();
		double[] expectedResources= playerData.expectedNumResources();
		boolean[] portFlags= playerData.getPortFlags();
		int hasArmy= playerData.hasLargestArmy()?1:0;
		int knightDifference;
		if( ga.getPlayerWithLargestArmy() == null)
			knightDifference = 3 - playerData.getNumKnights();
		else
			knightDifference= ga.getPlayerWithLargestArmy().getNumKnights()-playerData.getNumKnights();

		int hasRoad = playerData.hasLongestRoad()?1:0;
		
		int roadDif;
		if(ga.getPlayerWithLongestRoad() == null)
			roadDif = 5-playerData.getLongestRoadLength();
		else
			roadDif = ga.getPlayerWithLongestRoad().getLongestRoadLength()-playerData.getLongestRoadLength();
		int potentialSettlements= playerData.getNumPieces(SOCPlayingPiece.SETTLEMENT)>0
									? playerData.numPotentialSettlements():0;
		int potentialRoads= playerData.numPotentialRoads();

		/*SOCPlayer[] players= ga.getPlayers();
    	SOCPlayer opp= players[0];
    	for(int i=1; i<players.length; i++){
    		if (players[i].getPlayerNumber()!=playerData.getPlayerNumber()){
    			opp= players[i];
    		}
    	}
		 */
		double rank= 0.0;
		rank+=numSettlements*weights[0];
		rank+=numCities*weights[1];
		rank+=haveMiscPort*weights[2];
		for(int i=0; i<=SOCDevCardConstants.MAX_KNOWN; i++){
			rank+= devCards[i]*weights[i+3];
		}

		for(int i=0; i<SOCResourceConstants.WOOD; i++){
			rank+= expectedResources[i]*weights[i+12];
		}

		for(int i=1; i<portFlags.length; i++){
			rank+= (portFlags[i]? weights[i+17]:0);
		}
		rank+= hasArmy*weights[22];
		rank+= knightDifference*weights[23];
		rank+= hasRoad*weights[24];
		rank+= roadDif*weights[25];
		rank+= potentialSettlements*weights[26];
		rank+= potentialRoads*weights[27];
		rank+= playerData.getRoads().size()*weights[28];

		/*
    	int theirSettlements= opp.getSettlements().size();
    	int theirCities= opp.getCities().size();
    	int theirMiscPort= (opp.getPortFlag(SOCBoard.MISC_PORT)? 1:0);
    	int theirDevCards= opp.getDevCards().getTotal(); // don't know the type
    	double[] theirExpectedResources= opp.expectedNumResources();
    	boolean[] theirPortFlags= opp.getPortFlags();
    	int theirArmy= opp.hasLargestArmy()?1:0;
    	int theirKnightDifference= game.getPlayerWithLargestArmy().getNumKnights()-opp.getNumKnights();
    	int theirHasRoad= opp.hasLongestRoad()?1:0;
    	int theirRoadDif= game.getPlayerWithLongestRoad().getLongestRoadLength()-opp.getLongestRoadLength();
    	int theirPotentialSettlements= opp.numPotentialSettlements();
    	int theirPotentialRoads= opp.numPotentialRoads();
		 */
		return rank;

	}



	/** Holds a current game state used with cloned boards for max 4 search algorithm (Nick)
	 * 
	 */
	private class GameState {
		private SOCGame game;
		private int cutoff;
		private int player;

		/** Creates a new game state with given cloned board, current player number, and current cutoff
		 * 
		 * @param g cloned game board
		 * @param p player number of turn player
		 * @param c current cutoff counter
		 */
		public GameState (SOCGame g, int p, int c) {
			game = g;
			cutoff = c;
			player = p;
		}
	}	

	private float evalBoard(SOCGame game) {
		//place-holder for Katie's evaluation function
		return 0f;
	}

    
    public void performMoves(SOCGame g, Move moves, int pid) {
    	//Need to perform the moves on a cloned board (somehow bypass client)
    	
    	SOCPlayer player= g.getPlayers()[pid];
    	SOCResourceSet playerResources= player.getResources();
    	Vector<SOCTradeOffer> trades= moves.getTrades();
    	Vector<SOCPlayingPiece> pieces= moves.getPieces();
    	int devCards= moves.devCardsToBuy();
    	int playCard= moves.devCardToPlay();
    	
    	// Make all trades- assumes they are legal.
    	for(int i=0; i<trades.size(); i++){
    		SOCTradeOffer trade= trades.get(i);
    		playerResources.subtract(trade.getGiveSet());
    	    playerResources.add(trade.getGetSet());
    	}
    	
    	for(int i=0; i<pieces.size(); i++){
    		g.putPiece(pieces.get(i));
    	}
    	
    	for(int i=0; i<devCards; i++){
    		//client.sendText(game, "trying to buy a dev card");
    		g.buyDevCard2(pid);
    		//client.sendText(game, "bought a dev card");
    	}
    }
    
    
    /** Max4 Algorithm that searches through turns to find best available move for the current turn (Nick)
     * 
     * @param game Current game being played
     * @param player ID number of current player (player who wants to find best move)
     * @param maxDepth How deep the search algorithm searches
     * @return Best possible set of moves for current board
     */
    /*
    private Move max4Search (SOCGame game,int player,int maxDepth) {
    	Vector<Move> poss = possibleMoves(game,player);
    	float [] vals = new float[poss.size()];
    	for (int i=0;i<poss.size();i++) {
    		SOCGame clone = game.resetAsCopy();
    		performMoves(clone,poss.get(i));
        	GameState current = new GameState(clone,player,0);
    		vals[i] = maxValue(current,maxDepth, poss);
    	}
    	float max = Float.MAX_VALUE;
		int maxIndex = 0;
		for(int i=0; i<poss.size(); i++) {
			if (vals[i] > max) {
				max = vals[i];
				maxIndex = i;
			}
		}
		return poss.get(maxIndex);
    }
        
 //** Modified version created by Katie */       
    /** Max4 Algorithm that searches through turns to find best available move for the current turn (Nick)
     * 
     * @param game Current game being played
     * @param player ID number of current player (player who wants to find best move)
     * @param maxDepth How deep the search algorithm searches
     * @return Best possible set of moves for current board
     * @throws CloneNotSupportedException 
     */
 
    private int nodes_opened;
    
    private Move max4Search2 (SOCGame game,int player,int maxDepth){
    	nodes_opened=0;
    	//client.sendText(game,"starting Max4Search");
    	Vector<Move> poss = possibleMoves(game,player);
    	nodes_opened+= poss.size();
    	//client.sendText(game,"Found first set of possibleMoves. Size "+poss.size());
    	double max= Double.MIN_VALUE;
    	int maxIndex= 0;
    	if(poss.size()==1){
    		//client.sendText(game,"Only 1 possible move");
    		return poss.get(0);
    	}
    	double [] scores= new double[game.maxPlayers];
    	for (int i=0;i<poss.size();i++) {
    		//client.sendText(game, " in max4 loop, move #"+i);
    		SOCGame clone = new SOCGame(game);
    		performMoves(clone,poss.get(i),player);
    		
    		//client.sendText(game, " performed move "+poss.get(i));
    		
        	GameState current = new GameState(clone,player,0);
    		scores= maxValue2(current,maxDepth, player);

    		if(scores[player]>max){
    			max= scores[player];
    			maxIndex= i;
    		}
    	}
    	if(poss.size()==0) return null;
		return poss.get(maxIndex);
    }
    
	/** Returns the max value for a given game state with a certain depth cutoff (Nick)
	 * 
	 * @param gs Current GameState
	 * @param maxDepth Cutoff for search algorithm
	 * @return float value of a given move vector
	 */
    /*
    public float maxValue(GameState gs, int maxDepth, Vector<Move> poss) {
    	if (gs.cutoff == maxDepth) {
    		return evalBoard(gs.game);
    	} 
    	else {
    		SOCPlayer player = game.getPlayer(gs.player);
    		gs.game.checkForWinner();
        	SOCPlayer winner = gs.game.getPlayerWithWin();
    		if (winner == player) {
    			return Float.MAX_VALUE;
    		} 
    		else {
    			for (int i=1; i<poss.size(); i++){
    				SOCGame clone = gs.game.resetAsCopy();
    	    		performMoves(clone,poss.get(i));
    	    		int next = gs.player; //somehow get next player
    	        	GameState current = new GameState(clone,next,gs.cutoff+1);
    	        	return maxValue(current,maxDepth);
    			}
    		}
    	}
    }
    */
 
    /** Returns the max value for a given game state for player number player
     *  with a certain depth cutoff (Nick)
     * 
     * @param gs Current GameState
     * @param maxDepth Cutoff for search algorithm
     * @return float value of a given move vector 
     */

    public double[] maxValue2(GameState gs, int maxDepth, int pl) {
		// check if game is over
		int winner= gs.game.winningPlayer();
       	double [] scores= new double[gs.game.maxPlayers];
    	if(winner!=-1){	
    		scores[winner]= Double.MAX_VALUE;
    		return scores;
        }
		else {
			//client.sendText(game, " in else block");
			if (gs.cutoff == maxDepth) {
				for(int i=0; i<gs.game.maxPlayers; i++){
					scores[i]= evaluateBoard(gs.game, i);

		    		//client.sendText(game, " assigned a score: "+scores[i]);
				}
	    		return scores;
	    	}
			//client.sendText(game, " didn't meet cutoff"); 
			int nextPlayer= nextPlayer(pl, gs.game);
			Vector<Move> poss = possibleMoves(gs.game,nextPlayer);
			double[] results= new double[gs.game.maxPlayers];
			double max= Double.MIN_VALUE;
	    	for (int i=0;i<poss.size();i++) {
	    		SOCGame clone = new SOCGame(gs.game);
	    		//client.sendText(game, " performing moves");
	    		performMoves(clone,poss.get(i),nextPlayer);
	    		//client.sendText(game, " performed moves");
	        	GameState current = new GameState(clone,nextPlayer,gs.cutoff+1);
	        	//client.sendText(game, " going to the next level...");
	    		results= maxValue2(current,maxDepth, nextPlayer);
	    		//client.sendText(game, " got results from next level");
	    		if(results[nextPlayer]>max){
	    			max= scores[nextPlayer];
	    			scores= results;
	    		} 
	    		// in case of ties
	    		if(results[nextPlayer]==max && Math.random()>0.4){
	    			scores= results;
	    		}
	    		//client.sendText(game, " processed move "+i);
			}
	    	//client.sendText(game, " about to return scores");
	    	return scores;
    	}
    } 
    
    
    /** Returns the max value for a given game state for player number player
     *  with a certain depth cutoff (Nick) Takes random dice rolls into account
     * 
     * @param gs Current GameState
     * @param maxDepth Cutoff for search algorithm
     * @return float value of a given move vector
     */

   /* public double[] maxValue3(GameState gs, int maxDepth, int pl) {
		SOCPlayer player = game.getPlayer(gs.player);
		gs.game.checkForWinner();
		// check if game is over
		boolean gameOver= true;
       	SOCPlayer winner = gs.game.getPlayerWithWin();
       	double [] scores= new double[gs.game.maxPlayers];
    	if(gameOver){	
    		int win= winner.getPlayerNumber();
    		scores[win]= Double.MAX_VALUE;
    		return scores;
        }
		else {
			if (gs.cutoff == maxDepth) {
				for(int i=0; i<gs.game.maxPlayers; i++){
					scores[i]= evaluateBoard(gs.game, i);
				}
	    		return scores;
	    	} 
			int nextPlayer= nextPlayer(pl, gs.game);
			Vector<Move> poss = possibleMoves(game,nextPlayer);
			int maxIndex= -1;
			double[] results= new double[gs.game.maxPlayers];
			double max= Double.MIN_VALUE;
	    	for (int i=0;i<poss.size();i++) {
	    		SOCGame clone = game.resetAsCopy();
	    		performMoves(clone,poss.get(i),nextPlayer);
	        	GameState current = new GameState(clone,nextPlayer,gs.cutoff+1);
	    		results= maxValue2(current,maxDepth, nextPlayer);
	    		if(results[nextPlayer]>max){
	    			max= scores[nextPlayer];
	    			scores= results;
	    		}
			}
	    	return scores;
    	}
    } */

    public int nextPlayer(int player, SOCGame game){
    	return (player+1) % 4;
    }
    
    /** Keeps records over search algorithm use (Nick)
     *
     */
    private static class SearchTracker {
    	private double numNodes;
    	private double depth;
    	private double maxDepth;
    	private long searchTime;
    	private Vector<Integer> branchingFactors;
    	private double avgBranchingFactor;
    	private Vector<Double> boardEvals;
    	private double avgEval;
    	private double finalEval;
    	private Move finalMove;
    	private double numRoadsBuilt;
    	private double numCitiesBuilt;
    	private double numDevCardsBuilt;
    	private double numSettlementsBuilt;
    	private double numTradesMade;
    	private double numDevCardsUsed;
    	
    	public SearchTracker() {
    		branchingFactors = new Vector<Integer>();
    		boardEvals = new Vector<Double>();
    	}
    	
    	public SearchTracker(int mDepth) {
    		branchingFactors = new Vector<Integer>();
    		boardEvals = new Vector<Double>();
    		maxDepth = mDepth;
    	}
    	
    	public void setAvgs() {
    		int sumBranch = 0;
    		double sumEval = 0;
    		for(int i=0;i<branchingFactors.size();i++) {
    			sumBranch = sumBranch + branchingFactors.get(i);
    		}
    		avgBranchingFactor = (double) (sumBranch/branchingFactors.size());
    		for(int i=0;i<boardEvals.size();i++) {
    			sumEval = sumEval + boardEvals.get(i);
    		}
    		avgEval = sumEval/(double) boardEvals.size();
    	}
    	
    	/** Averages results of multiple searches (Nick)
         * 
         * @param results Vector of SearchTracker's
         * @return SearchTracker with fields in terms of averages
         */
        private static SearchTracker avgTrack(Vector<SearchTracker> results) {
    		SearchTracker avg = new SearchTracker();
    		for(int i=0;i<results.size();i++) {
    			avg.numNodes = avg.numNodes + results.get(i).numNodes;
    			avg.depth = avg.depth + results.get(i).depth;
    			avg.maxDepth = avg.maxDepth + results.get(i).maxDepth;
    			avg.searchTime = avg.searchTime + results.get(i).searchTime;
    			avg.avgBranchingFactor = avg.avgBranchingFactor + results.get(i).avgBranchingFactor;
    			avg.avgEval = avg.avgEval + results.get(i).avgEval;
    			avg.finalEval = avg.finalEval + results.get(i).finalEval;
    			Vector<SOCPlayingPiece> pieces = results.get(i).finalMove.getPieces();
    			for(int j=0;j<pieces.size();j++) {
    				if(pieces.get(j) instanceof SOCCity) {
    					avg.numCitiesBuilt++;
    				} else if(pieces.get(j) instanceof SOCRoad) {
    					avg.numRoadsBuilt++;
    				} else {
    					avg.numSettlementsBuilt++;
    				}
    			}
    			avg.numTradesMade = avg.numTradesMade + results.get(i).finalMove.getTrades().size();
    			avg.numDevCardsBuilt = avg.numDevCardsBuilt + results.get(i).finalMove.devCardsToBuy();
    			if (results.get(i).finalMove.devCardToPlay() != -1) {
    				avg.numDevCardsUsed++;
    			}
    		}
    		return avg;
    	}
    }
    
    // Tracking Added by Nick
  //** Modified version created by Katie */       
    /** Max4 Algorithm that searches through turns to find best available move for the current turn (Nick)
     * 
     * @param game Current game being played
     * @param player ID number of current player (player who wants to find best move)
     * @param maxDepth How deep the search algorithm searches
     * @return Best possible set of moves for current board
     */
    private Move max4Search2WithTracking (SOCGame game,int player,int maxDepth) {
    	SearchTracker track = new SearchTracker(maxDepth);
    	long start = System.currentTimeMillis();
    	Vector<Move> poss = possibleMoves(game,player);
    	//**FIX**what if poss is empty? (we have no valid moves)
    	track.branchingFactors.add(poss.size());
    	track.depth ++;
    	track.numNodes = track.numNodes + poss.size();
    	double max= Double.MIN_VALUE;
    	int maxIndex= 0;
    	if(poss.size()==1){
    		return poss.get(0);
    	}
    	double [] scores= new double[game.maxPlayers];
    	for (int i=0;i<poss.size();i++) {
    		SOCGame clone = new SOCGame(game);
    		performMoves(clone,poss.get(i),player);
        	GameState current = new GameState(clone,player,0);
    		scores= maxValue2WithTracking(current,maxDepth, player,track);
    		if(scores[player]>max){
    			max= scores[player];
    			maxIndex= i;
    		}
    	}
    	track.setAvgs(); 
    	track.searchTime = System.currentTimeMillis()-start;
    	track.finalMove = poss.get(maxIndex);
    	track.finalEval = max;
    	searchResults.add(track);
    	if(poss.size()==0) return null;
		return poss.get(maxIndex);
    }
       
    // Tracking added by Nick
    /** Returns the max value for a given game state for player number player
     *  with a certain depth cutoff (Nick)
     * 
     * @param gs Current GameState
     * @param maxDepth Cutoff for search algorithm
     * @return float value of a given move vector
     */

    public double[] maxValue2WithTracking(GameState gs, int maxDepth, int pl,SearchTracker track) {
		// check if game is over
		int winner= gs.game.winningPlayer();
       	double [] scores= new double[gs.game.maxPlayers];
    	if(winner!=-1){	
    		scores[winner]= Double.MAX_VALUE;
    		return scores;
        }
		else {
			if (gs.cutoff == maxDepth) {
				for(int i=0; i<gs.game.maxPlayers; i++){
					scores[i]= evaluateBoard(gs.game, i);
					track.boardEvals.add(scores[i]);
				}
	    		return scores;
	    	} 
			int nextPlayer= nextPlayer(pl, gs.game);
			Vector<Move> poss = possibleMoves(gs.game,nextPlayer);
			track.branchingFactors.add(poss.size());
			track.depth ++;
			track.numNodes = track.numNodes + poss.size();
			double[] results= new double[gs.game.maxPlayers];
			double max= Double.MIN_VALUE;
	    	for (int i=0;i<poss.size();i++) {
	    		SOCGame clone = new SOCGame(gs.game);
	    		performMoves(clone,poss.get(i),nextPlayer);
	        	GameState current = new GameState(clone,nextPlayer,gs.cutoff+1);
	    		results= maxValue2WithTracking(current,maxDepth, nextPlayer,track);
	    		if(results[nextPlayer]>max){
	    			max= scores[nextPlayer];
	    			scores= results;
	    		} 
	    		// in case of ties
	    		if(results[nextPlayer]==max && Math.random()>0.4){
	    			scores= results;
	    		}
			}
	    	return scores;
    	}
    }  
    
    /** Output for search algorithm as an output summary text file
     * 
     */
    public static void writeFile() {
		try {
			FileOutputStream plop = new FileOutputStream("Search Output.txt",true);
			PrintWriter scribble = new PrintWriter(plop, true);
			SearchTracker avg = SearchTracker.avgTrack(searchResults);
			scribble.println("Search Results:");
			scribble.println();
			scribble.println("Summary (Average per search):");
			scribble.println("Number of Nodes Searched: " + avg.numNodes);
			scribble.println("Search Time: " + avg.searchTime);
			scribble.println("Average Branching Factor: " + avg.avgBranchingFactor);
			scribble.println("Depth: " + avg.depth);
			scribble.println("Max Depth: " + avg.maxDepth);
			scribble.println("Average Board Value (through Evaluation function): " + avg.avgEval);
			scribble.println("Final Board Value Chosen: " + avg.finalEval);
			scribble.println("Settlements Built: " + avg.numSettlementsBuilt);
			scribble.println("Cities Built: " + avg.numCitiesBuilt);
			scribble.println("Roads Built: " + avg.numRoadsBuilt);
			scribble.println("Development Cards Built: " + avg.numDevCardsBuilt);
			scribble.println("Developmet Cards Used: " + avg.numDevCardsUsed);
			scribble.println("Trades Made: " + avg.numTradesMade);
			scribble.println();
			scribble.println("Individual Searches: ");
			scribble.println();
			for(int i=0;i<searchResults.size();i++) {
				scribble.println("Search #" + i);
				scribble.println("Number of Nodes Searched: " + searchResults.get(i).numNodes);
				scribble.println("Search Time: " + searchResults.get(i).searchTime);
				scribble.println("Average Branching Factor: " + searchResults.get(i).avgBranchingFactor);
				scribble.println("Depth: " + searchResults.get(i).depth);
				scribble.println("Max Depth: " + searchResults.get(i).maxDepth);
				scribble.println("Average Board Value (through Evaluation function): " + searchResults.get(i).avgEval);
				scribble.println("Final Board Value Chosen: " + searchResults.get(i).finalEval);
				scribble.println();
			}
			scribble.close();
		} catch (FileNotFoundException e) {
			System.out.println("Unable to Write Output File");
		}
	}
}
