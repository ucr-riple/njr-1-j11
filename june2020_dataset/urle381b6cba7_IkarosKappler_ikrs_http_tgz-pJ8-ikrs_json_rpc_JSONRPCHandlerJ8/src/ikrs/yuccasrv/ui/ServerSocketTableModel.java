package ikrs.yuccasrv.ui;

import java.net.Socket;
import java.net.DatagramSocket;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.text.Collator;
import java.util.TreeMap;
import java.util.Map;
import java.util.UUID;

import ikrs.typesystem.*;
import ikrs.yuccasrv.Constants;
import ikrs.yuccasrv.ConnectionUserID;
import ikrs.yuccasrv.socketmngr.*;

public class ServerSocketTableModel 
    extends AbstractTableModel 
    implements BindListener {
    
    private String[] columnNames = { "ID", "Address", "Port", "Protocol", "#Connections" };
    
    //private ArrayList<ServerSocketThread> rows;
    private ArrayList<Map<String,BasicType>> rows;

    public ServerSocketTableModel() {
	super();

	//this.rows = new ArrayList<ServerSocketThread>( 2 );
	this.rows = new ArrayList<Map<String,BasicType>>(2);
    }

    protected Map<String,BasicType> getRow( int row ) {
	return this.rows.get(row);
    }

    public synchronized int resolveTableRowBySocketID( UUID socketID ) {
	for( int i = 0; i < this.rows.size(); i++ ) {
	    if( this.rows.get(i).get(Constants.KEY_ID).equals(socketID) ) 
		return i;
	}
	
	return -1;
    }

    //---START-------------------------- BindManager ----------------------------
    /**
     * @param source The BindManager that reports the event.
     * @param socketID A unique ID to identify the created socket by the use of
     *                 BindManager.getServer*( socketID ).
     **/
    public void serverCreated( BindManager source,
			       UUID socketID ) {

	/*Collator caseInsensitive = Collator.getInstance();
	caseInsensitive.setStrength( Collator.PRIMARY );
	Map<String,BasicType> map = new TreeMap<String,BasicType>( caseInsensitive );
	map.put( "ID",                                new BasicUUIDType(socketID) );
	map.put( Constants.CONFIG_SERVER_ADDRESS,     source.getServerSettings(Constants.CONFIG_SERVER_ADDRESS) );
	map.put( Constants.CONFIG_SERVER_PORT,        new BasicStringType(Constants.CONFIG_SERVER_PORT) );
	map.put( Constants.CONFIG_SERVER_PROTOCOL,    source.getServerSettings(socketID).get(Constants.CONFIG_SERVER_PROTOCOL) );

	this.rows.add( map ); */

	this.rows.add( source.getServerSettings(socketID) );

	fireTableRowsInserted( this.rows.size()-1, this.rows.size()-1 );
    }
    

    /**
     * @param source The BindManager that reports the event.
     * @param socketID The server's unique ID.
     * @param e The reported exception.
     * @param isTraumatic This flag tell if the server socket can still
     *                    be used or if it's (probably) broken and should be
     *                    restarted. In the second case the BindManager will
     *                    automatically close and remove the socket to free
     *                    the resources.
     **/
    public void serverError( BindManager source,
			     UUID socketID,
			     Exception e,
			     boolean isTraumatic ) {

    }


    /**
     * @param source The BindManager that reports the event.
     * @param sockedID The server socket's unique ID.
     * @param sock The accepted connection socket.
     **/
    public void serverAcceptedTCPConnection( BindManager source,
					     UUID socketID,
					     Socket sock,
					     ConnectionUserID userID ) {
	int row = this.resolveTableRowBySocketID( socketID );
	if( row != -1 )
	    fireTableCellUpdated( row, 4 ); // Update connectionCount cell
	
	
    }

    /**
     * @param source The BindManager that reports the event.
     * @param sockedID The server socket's unique ID.
     * @param sock The accepted connection socket.
     **/
    public void serverAcceptedUDPConnection( BindManager source,
					     UUID socketID,
					     DatagramSocket sock,
					     ConnectionUserID userID ) {
	int row = this.resolveTableRowBySocketID( socketID );
	if( row != -1 )
	    fireTableCellUpdated( row, 4 ); // Update connectionCount cell
    }

    
    /**
     * @param source The BindManager that reports the event.
     * @param sockedID The server socket's unique ID.
     **/
    public synchronized void serverClosed( BindManager source,
			      UUID socketID ) {
	
	int row = this.resolveTableRowBySocketID( socketID );
	if( row == -1 ) 
	    return;

	this.rows.remove( row );
	fireTableRowsDeleted( row, row );

    }

    public void finalize( long time,
			  java.util.concurrent.TimeUnit unit ) {

    }
    //---START-------------------------- BindManager ----------------------------

    /*public Class<?> getColumnClass( int columnIndex ) {

      }*/

    public String getColumnName( int column ) {
	return this.columnNames[ column ];
    }


    public int getRowCount() {
	return this.rows.size();
    }

    public int getColumnCount() {
	return this.columnNames.length;
    }

    public Object getValueAt( int row, int column ) {
	Map<String,BasicType> t = this.rows.get( row );

	switch( column ) {
	case 0:	return t.get(Constants.KEY_ID); 
	case 1: return t.get(Constants.CONFIG_SERVER_ADDRESS); 
	case 2: return t.get(Constants.CONFIG_SERVER_PORT); 
	case 3: return t.get(Constants.CONFIG_SERVER_PROTOCOL); 
	case 4: 
	    BasicType connectionCount = t.get( Constants.KEY_CONNECTION_COUNT );
	    if( connectionCount == null ) {
		connectionCount = new BasicNumberType(0);
		t.put( Constants.KEY_CONNECTION_COUNT, connectionCount );
	    }
	    
	    return connectionCount;
	    
	    
	default: return "NA"; 
	}
    }



}