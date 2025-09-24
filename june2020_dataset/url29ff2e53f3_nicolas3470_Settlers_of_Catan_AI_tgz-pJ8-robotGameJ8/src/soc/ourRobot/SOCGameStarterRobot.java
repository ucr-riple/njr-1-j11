package soc.ourRobot;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Hashtable;

import soc.disableDebug.D;
import soc.game.SOCGameOption;
import soc.message.SOCAcceptOffer;
import soc.message.SOCAdminPing;
import soc.message.SOCAdminReset;
import soc.message.SOCBoardLayout;
import soc.message.SOCBoardLayout2;
import soc.message.SOCCancelBuildRequest;
import soc.message.SOCChoosePlayerRequest;
import soc.message.SOCClearOffer;
import soc.message.SOCClearTradeMsg;
import soc.message.SOCDeleteGame;
import soc.message.SOCDevCard;
import soc.message.SOCDevCardCount;
import soc.message.SOCDiceResult;
import soc.message.SOCDiscardRequest;
import soc.message.SOCFirstPlayer;
import soc.message.SOCGameMembers;
import soc.message.SOCGameState;
import soc.message.SOCGameTextMsg;
import soc.message.SOCGames;
import soc.message.SOCImARobot;
import soc.message.SOCJoinGame;
import soc.message.SOCJoinGameAuth;
import soc.message.SOCJoinGameRequest;
import soc.message.SOCLeaveGame;
import soc.message.SOCMakeOffer;
import soc.message.SOCMessage;
import soc.message.SOCMoveRobber;
import soc.message.SOCNewGameWithOptions;
import soc.message.SOCNewGameWithOptionsRequest;
import soc.message.SOCPlayerElement;
import soc.message.SOCPotentialSettlements;
import soc.message.SOCPutPiece;
import soc.message.SOCRejectConnection;
import soc.message.SOCRejectOffer;
import soc.message.SOCResetBoardAuth;
import soc.message.SOCResourceCount;
import soc.message.SOCRobotDismiss;
import soc.message.SOCServerPing;
import soc.message.SOCSetPlayedDevCard;
import soc.message.SOCSetTurn;
import soc.message.SOCSitDown;
import soc.message.SOCStartGame;
import soc.message.SOCStatusMessage;
import soc.message.SOCTurn;
import soc.message.SOCUpdateRobotParams;
import soc.message.SOCVersion;
import soc.robot.SOCRobotClient;
import soc.server.genericServer.LocalStringServerSocket;
import soc.util.Version;

public class SOCGameStarterRobot extends SOCRobotClient {

	 /**
     * Constructor for connecting to a local game (practice) on a local stringport.
     *
     * @param s    the stringport that the server listens on
     * @param nn   nickname for robot
     * @param pw   password for robot
     */
	public SOCGameStarterRobot(String s, String nn, String pw) {
		super(s, nn, pw, false);
	}
	
	public SOCGameStarterRobot() {
		super("SOCPRACTICE", "Player", "", false);
	}
	
	/**
     * Initialize the robot player; connect to server, send first messages
     */
    public void init()
    {
        try
        {
            if (strSocketName == null)
            {
                s = new Socket(host, port);
                s.setSoTimeout(300000);
                in = new DataInputStream(s.getInputStream());
                out = new DataOutputStream(s.getOutputStream());
            }
            else
            {
                sLocal = LocalStringServerSocket.connectTo(strSocketName);
            }               
            connected = true;
            reader = new Thread(this);
            reader.start();

            //resetThread = new SOCRobotResetThread(this);
            //resetThread.start();
            put(SOCVersion.toCmd(Version.versionNumber(), Version.version(), Version.buildnum()));
            //put(SOCImARobot.toCmd(nickname, SOCImARobot.RBCLASS_BUILTIN)); 
            
            // this is where the magic happens...
           // put(SOCJoinGame.toCmd(nickname, password, host, "Practice"));
            Hashtable gameOpts = SOCGameOption.initAllOptions();
            put(SOCNewGameWithOptionsRequest.toCmd(nickname, password, host, "Practice", gameOpts));
            //put(SOCJoinGame.toCmd(nickname, password, host, "Practice"));
        }
        catch (Exception e)
        {
            ex = e;
            System.err.println("Could not connect to the server: " + ex);
        }
    }
    
    /**
     * Treat the incoming messages.
     * Messages of unknown type are ignored (mes will be null from {@link SOCMessage#toMsg(String)}).
     *
     * @param mes    the message
     */
    public void treat(SOCMessage mes)
    {
        if (mes == null)
            return;  // Message syntax error or unknown type

        D.ebugPrintln("IN - " + mes);

        try
        {
            switch (mes.getType())
            {
            
            // TODO: Added by Ryan
            // Attempts to join game
            case SOCMessage.NEWGAMEWITHOPTIONS:
                handleNEWGAMEWITHOPTIONS((SOCNewGameWithOptions) mes);
                break;
            
            /**
             * status message
             */
            case SOCMessage.STATUSMESSAGE:
                handleSTATUSMESSAGE((SOCStatusMessage) mes);

                break;

            /**
             * server ping
             */
            case SOCMessage.SERVERPING:
                handleSERVERPING((SOCServerPing) mes);

                break;

            /**
             * admin ping
             */
            case SOCMessage.ADMINPING:
                handleADMINPING((SOCAdminPing) mes);

                break;

            /**
             * admin reset
             */
            case SOCMessage.ADMINRESET:
                handleADMINRESET((SOCAdminReset) mes);

                break;

            /**
             * update the current robot parameters
             */
            case SOCMessage.UPDATEROBOTPARAMS:
                handleUPDATEROBOTPARAMS((SOCUpdateRobotParams) mes);

                break;

            /**
             * join game authorization
             */
            case SOCMessage.JOINGAMEAUTH:
                handleJOINGAMEAUTH((SOCJoinGameAuth) mes);

                break;

            /**
             * someone joined a game
             */
            case SOCMessage.JOINGAME:
                handleJOINGAME((SOCJoinGame) mes);

                break;

            /**
             * someone left a game
             */
            case SOCMessage.LEAVEGAME:
                handleLEAVEGAME((SOCLeaveGame) mes);

                break;

            /**
             * game has been destroyed
             */
            case SOCMessage.DELETEGAME:
                handleDELETEGAME((SOCDeleteGame) mes);

                break;

            /**
             * list of game members
             */
            case SOCMessage.GAMEMEMBERS:
                handleGAMEMEMBERS((SOCGameMembers) mes);

                break;

            /**
             * game text message
             */
            case SOCMessage.GAMETEXTMSG:
                handleGAMETEXTMSG((SOCGameTextMsg) mes);

                break;

            /**
             * someone is sitting down
             */
            case SOCMessage.SITDOWN:
                handleSITDOWN((SOCSitDown) mes);

                break;

            /**
             * receive a board layout
             */
            case SOCMessage.BOARDLAYOUT:
                handleBOARDLAYOUT((SOCBoardLayout) mes);  // in soc.client.SOCDisplaylessPlayerClient
                break;

            /**
             * receive a board layout (new format, as of 20091104 (v 1.1.08))
             */
            case SOCMessage.BOARDLAYOUT2:
                handleBOARDLAYOUT2((SOCBoardLayout2) mes);  // in soc.client.SOCDisplaylessPlayerClient
                break;

            /**
             * message that the game is starting
             */
            case SOCMessage.STARTGAME:
                handleSTARTGAME((SOCStartGame) mes);

                break;

            /**
             * update the state of the game
             */
            case SOCMessage.GAMESTATE:
                handleGAMESTATE((SOCGameState) mes);

                break;

            /**
             * set the current turn
             */
            case SOCMessage.SETTURN:
                handleSETTURN((SOCSetTurn) mes);

                break;

            /**
             * set who the first player is
             */
            case SOCMessage.FIRSTPLAYER:
                handleFIRSTPLAYER((SOCFirstPlayer) mes);

                break;

            /**
             * update who's turn it is
             */
            case SOCMessage.TURN:
                handleTURN((SOCTurn) mes);

                break;

            /**
             * receive player information
             */
            case SOCMessage.PLAYERELEMENT:
                handlePLAYERELEMENT((SOCPlayerElement) mes);

                break;

            /**
             * receive resource count
             */
            case SOCMessage.RESOURCECOUNT:
                handleRESOURCECOUNT((SOCResourceCount) mes);

                break;

            /**
             * the latest dice result
             */
            case SOCMessage.DICERESULT:
                handleDICERESULT((SOCDiceResult) mes);

                break;

            /**
             * a player built something
             */
            case SOCMessage.PUTPIECE:
                handlePUTPIECE((SOCPutPiece) mes);

                break;

            /**
             * the current player has cancelled an initial settlement
             */
            case SOCMessage.CANCELBUILDREQUEST:
                handleCANCELBUILDREQUEST((SOCCancelBuildRequest) mes);

                break;

            /**
             * the robber moved
             */
            case SOCMessage.MOVEROBBER:
                handleMOVEROBBER((SOCMoveRobber) mes);

                break;

            /**
             * the server wants this player to discard
             */
            case SOCMessage.DISCARDREQUEST:
                handleDISCARDREQUEST((SOCDiscardRequest) mes);

                break;

            /**
             * the server wants this player to choose a player to rob
             */
            case SOCMessage.CHOOSEPLAYERREQUEST:
                handleCHOOSEPLAYERREQUEST((SOCChoosePlayerRequest) mes);

                break;

            /**
             * a player has made an offer
             */
            case SOCMessage.MAKEOFFER:
                handleMAKEOFFER((SOCMakeOffer) mes);

                break;

            /**
             * a player has cleared her offer
             */
            case SOCMessage.CLEAROFFER:
                handleCLEAROFFER((SOCClearOffer) mes);

                break;

            /**
             * a player has rejected an offer
             */
            case SOCMessage.REJECTOFFER:
                handleREJECTOFFER((SOCRejectOffer) mes);

                break;

            /**
             * a player has accepted an offer
             */
            case SOCMessage.ACCEPTOFFER:
                handleACCEPTOFFER((SOCAcceptOffer) mes);

                break;

            /**
             * the trade message needs to be cleared
             */
            case SOCMessage.CLEARTRADEMSG:
                handleCLEARTRADEMSG((SOCClearTradeMsg) mes);

                break;

            /**
             * the current number of development cards
             */
            case SOCMessage.DEVCARDCOUNT:
                handleDEVCARDCOUNT((SOCDevCardCount) mes);

                break;

            /**
             * a dev card action, either draw, play, or add to hand
             */
            case SOCMessage.DEVCARD:
                handleDEVCARD((SOCDevCard) mes);

                break;

            /**
             * set the flag that tells if a player has played a
             * development card this turn
             */
            case SOCMessage.SETPLAYEDDEVCARD:
                handleSETPLAYEDDEVCARD((SOCSetPlayedDevCard) mes);

                break;

            /**
             * get a list of all the potential settlements for a player
             */
            case SOCMessage.POTENTIALSETTLEMENTS:
                handlePOTENTIALSETTLEMENTS((SOCPotentialSettlements) mes);

                break;

            /**
             * the server is requesting that we join a game
             */
            case SOCMessage.JOINGAMEREQUEST:
                handleJOINGAMEREQUEST((SOCJoinGameRequest) mes);

                break;

            /**
             * message that means the server wants us to leave the game
             */
            case SOCMessage.ROBOTDISMISS:
                handleROBOTDISMISS((SOCRobotDismiss) mes);

                break;

            /**
             * handle the reject connection message - JM TODO: placement within switch? (vs displaylesscli, playercli) 
             */
            case SOCMessage.REJECTCONNECTION:
                handleREJECTCONNECTION((SOCRejectConnection) mes);

                break;

            /**
             * handle board reset (new game with same players, same game name, new layout).
             */
            case SOCMessage.RESETBOARDAUTH:
                handleRESETBOARDAUTH((SOCResetBoardAuth) mes);

                break;
            }
        }
        catch (Throwable e)
        {
            System.err.println("SOCRobotClient treat ERROR - " + e + " " + e.getMessage());
            e.printStackTrace();
            while (e.getCause() != null)
            {
                e = e.getCause();
                System.err.println(" -> nested: " + e.getClass());
                e.printStackTrace();
            }
            System.err.println("-- end stacktrace --");
        }
    }
    
    /**
     * handle the "join game" message
     * @param mes  the message
     */
    protected void handleJOINGAME(SOCJoinGame mes) {
    	
    }
    
    /**
     * handle the "game members" message, which indicates the entire game state has now been sent.
     * If we have a {@link #seatRequests} for this game, sit down now.
     * @param mes  the message
     */
    protected void handleGAMEMEMBERS(SOCGameMembers mes)
    {
        /**
         * sit down to play
         */
        Integer pn = (Integer) seatRequests.get(mes.getGame());

        try
        {
            //wait(Math.round(Math.random()*1000));
        }
        catch (Exception e)
        {
            ;
        }

        if (pn != null)
        {
            put(SOCSitDown.toCmd(mes.getGame(), nickname, pn.intValue(), true));
        } else {
        	System.out.println(this.nickname);
            System.err.println("** Cannot sit down: Assert failed: null pn for game " + mes.getGame());
        }
    }
    
    /**
     * process the "new game with options" message
     * @since 1.1.07
     */
    private void handleNEWGAMEWITHOPTIONS(SOCNewGameWithOptions mes)
    {
    	
        String gname = mes.getGame();
        String opts = mes.getOptionsString();
        boolean canJoin = (mes.getMinVersion() <= Version.versionNumber());
        if (gname.charAt(0) == SOCGames.MARKER_THIS_GAME_UNJOINABLE)
        {
            gname = gname.substring(1);
            canJoin = false;
        }
        //addToGameList(! canJoin, gname, opts, false);
        put(SOCJoinGame.toCmd(nickname, password, host, gname));
    }
}
