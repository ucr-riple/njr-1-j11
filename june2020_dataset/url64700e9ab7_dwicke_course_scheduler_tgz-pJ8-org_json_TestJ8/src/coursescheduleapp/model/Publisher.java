package coursescheduleapp.model;

import java.rmi.Remote;
import java.rmi.RemoteException;
/**
 * 
 * @author Steven Schwell
 * http://www2.sys-con.com/ITSG/virtualcd/java/archives/0210/schwell/index.html
 */
public interface Publisher extends Remote {  
	  public void addSubscriber(Subscriber s)  
	    throws RemoteException; 

	  public void removeSubscriber(Subscriber s)  
	    throws RemoteException; 

	  public void removeAllSubscribers()  
	    throws RemoteException; 
	} 

