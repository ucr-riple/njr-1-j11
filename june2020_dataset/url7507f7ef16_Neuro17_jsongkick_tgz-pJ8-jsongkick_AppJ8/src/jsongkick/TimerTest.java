package jsongkick;

import java.net.URISyntaxException;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;

import search.ArtistSearch;

public class TimerTest {
	private static final Logger log = LogManager.getLogger();
	
	public static void main(String[] args) {
		final Timer timer = new Timer();
		
	    timer.schedule(new TimerTask() {
	        int n = 0;  
	        @Override
	        public void run() {
	        	log.debug(new DateTime().toString() + " n: " + n);
	        	log.debug("Total memory available to JVM (MegaBytes): " + 
	        	        Runtime.getRuntime().totalMemory() / Math.pow(2, 20));
	        	if(n == 3){
	        		try {
						App.run();
					} catch (URISyntaxException e) {
						log.error("Error Message: " + e.getMessage());
						log.error(e.getStackTrace().toString());
					}
	        	}
	        	
	            if (++n >= 5) {
	                timer.cancel();
	            }
	        }
	    },1000, 1000 * 60 * 60 * 1);
	}

}
