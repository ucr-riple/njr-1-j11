package soc.robot;
import java.io.IOException;
import java.net.ConnectException;
import java.util.Hashtable;

//import soc.client.SOCPlayerClient.SOCPlayerLocalStringReader;
import soc.debug.D;
import soc.message.SOCJoinGame;
import soc.message.SOCMessage;
import soc.message.SOCNewGameWithOptionsRequest;
import soc.message.SOCVersion;
import soc.ourRobot.SOCGameStarterRobot;
import soc.server.SOCServer;
import soc.server.genericServer.LocalStringConnection;
import soc.server.genericServer.LocalStringServerSocket;
import soc.server.genericServer.StringConnection;
import soc.util.Version;


public class robotGame {

	
	/**
     * for connection to local-practice server {@link #practiceServer}.
     * Null before it's started in {@link #startPracticeGame()}.
     */
    protected static StringConnection prCli = null;
    

    /** For debug, our last messages sent, over the net and locally (pipes) */
    protected static String lastMessage_N, lastMessage_L;
    protected static Exception ex_L = null;  // Local errors (stringport pipes)
	
	/**
	 * @param args
	 * @throws IllegalArgumentException 
	 * @throws ConnectException 
	 */
	public static void main(String[] args) throws ConnectException, IllegalArgumentException {
		// Code adapted from SOCPlayerClient.startPracticeGame()
		SOCServer server = new SOCServer(SOCServer.PRACTICE_STRINGPORT, 30, null, null);
        server.setPriority(5);  // same as in SOCServer.main
        server.start();
        
        server.setupLocalRobots(5, 0);	// 5 fast robots, 0 smart robots
        
        SOCGameStarterRobot starter = new SOCGameStarterRobot();
        starter.init();
        
        /*
        prCli = LocalStringServerSocket.connectTo(SOCServer.PRACTICE_STRINGPORT);
        new SOCPlayerLocalStringReader((LocalStringConnection) prCli);
        
        
        putLocal(SOCVersion.toCmd(Version.versionNumber(), Version.version(), Version.buildnum()));


        //putLocal(SOCJoinGame.toCmd("Player", "", null, "Practice"));
        
        Hashtable opts = new Hashtable();
        
        putLocal(SOCNewGameWithOptionsRequest.toCmd("Player", "", null, "Practice", gameOpts));
        */
	}
	
	
	 /**
     * write a message to the practice server. {@link #localTCPServer} is not
     * the same as the practice server; use {@link #putNet(String)} to send
     * a message to the local TCP server.
     *
     * @param s  the message
     * @return true if the message was sent, false if not
     * @see #put(String, boolean)
     */
    public static synchronized boolean putLocal(String s)
    {
        lastMessage_L = s;

        if ((ex_L != null) || !prCli.isConnected())
        {
            return false;
        }

        if (D.ebugIsEnabled())
            D.ebugPrintln("OUT L- " + SOCMessage.toMsg(s));

        prCli.put(s);

        return true;
    }
   

    /**
     * For local practice games, reader thread to get messages from the
     * local server to be treated and reacted to.
     */
    protected static class SOCPlayerLocalStringReader implements Runnable
    {
        LocalStringConnection locl;

        /** 
         * Start a new thread and listen to local server.
         *
         * @param localConn Active connection to local server
         */
        protected SOCPlayerLocalStringReader (LocalStringConnection localConn)
        {
            locl = localConn;

            Thread thr = new Thread(this);
            thr.setDaemon(true);
            thr.start();
        }

        /**
         * continuously read from the local string server in a separate thread
         */
        public void run()
        {
            Thread.currentThread().setName("cli-stringread");  // Thread name for debug
            try
            {
                while (locl.isConnected())
                {
                    String s = locl.readNext();
                    //treat((SOCMessage) SOCMessage.toMsg(s), true);
                }
            }
            catch (IOException e)
            {
                // purposefully closing the socket brings us here too
                if (locl.isConnected())
                {
                    ex_L = e;
                    System.out.println("could not read from string localnet: " + ex_L);
                    //destroy();
                }
            }
        }
    }
    
    
    
    
}
