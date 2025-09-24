package ikrs.yuccasrv.ui;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Enumeration;
import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;

/**
 * This class populates a combobox list model containing all available local inet addresses
 * on the running system.
 *
 * @author Ikaros Kappler
 * @date 2012-04-24
 * @version 1.0.0
 **/


public class InetAddressComboBoxModel 
    extends AbstractListModel 
    implements ComboBoxModel {


    private ArrayList<InetAddress> list;
    private int selectedIndex;
    
    public InetAddressComboBoxModel() 
	throws java.net.SocketException {

	super();

	this.list = new ArrayList<InetAddress>( 2 );
	
	InetAddress localhost = null;
	try {
	    localhost = InetAddress.getLocalHost();
	} catch( java.net.UnknownHostException e ) {

	    System.out.println( "[Warning] No localhost found." );
	    // No localhost available ... continue though
	}
	//System.out.println( "localhost="+localhost.getHostAddress() );
	
	
	Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
	int i = 0;
	while( en.hasMoreElements() ) {
	    NetworkInterface iface = en.nextElement();
	    
	    Enumeration<InetAddress> addresses = iface.getInetAddresses();
	    while( addresses.hasMoreElements() ) {
	
		InetAddress addr = addresses.nextElement();
		//System.out.println( addr.getHostAddress() );
		this.list.add( addr );

		// Preselect local host
		if( localhost != null && (addr == localhost || addr.equals(localhost) || addr.getHostAddress().equals(localhost.getHostAddress()) || addr.getHostAddress().equals("127.0.0.1")) ) {
		    this.selectedIndex = i;
		    //System.out.println( "EQUALS LOCALHOST: "+addr );
		}
		    
		i++;
	    }
	}
    
    }

    public InetAddress getSelectedAddress() {
	if( this.selectedIndex < 0 || this.selectedIndex >= this.list.size() )
	    return null; // none selected
	else
	    return this.list.get( this.selectedIndex );
    }

    //---BEGIN---------------------- ComboBoxModel ----------------------
    public InetAddress getSelectedItem() {
	return this.list.get( this.selectedIndex );
    }

    public void setSelectedItem( Object anItem ) {
	for( int i = 0; i < this.list.size(); i++ ) {
	    if( this.list.get(i) == anItem || this.list.get(i).equals(anItem) ) {
		this.selectedIndex = i;
		return;
	    }
	}
    }
    //---BEGIN---------------------- ComboBoxModel ----------------------	
    
    
    //---BEGIN---------------------- ListModel ----------------------
    public InetAddress getElementAt( int index ) {
	return this.list.get( index );
    }

    public int getSize() {
	return this.list.size();
    }
    //---END------------------------ ListModel ----------------------


}