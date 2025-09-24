package coursescheduleapp.model;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * 
 * @author Steven Schwell
 * http://www2.sys-con.com/ITSG/virtualcd/java/archives/0210/schwell/index.html
 */
public interface Subscriber extends Remote {  
	  public void update(Object pub, Object code)  
	    throws RemoteException; 
	} 

