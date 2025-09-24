package ikrs.yuccasrv.ui;

import java.awt.Container;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.Collator;
import java.util.Calendar;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;
import javax.swing.*;

import ikrs.typesystem.*;
import ikrs.util.Environment;
import ikrs.yuccasrv.Constants;
import ikrs.yuccasrv.socketmngr.BindManager;

/**
 * 
 *
 * @author Ikaros Kappler
 * @date 2012-04-23
 * @version 1.0.0
 **/


public class ServerManagerDialog
    extends JDialog 
    implements ActionListener {

    private JTable serverSocketTable;
    private ServerSocketTableModel serverSocketTableModel;

    private JTextArea infoArea;


    private JButton buttonStartServer;
    private JButton buttonStopServer;

    private JLabel labelStatus;


    private NewServerDialog newServerDialog;

    private BindManager bindManager;

    public ServerManagerDialog( BindManager m ) {
	super( (JFrame)null, "Server Manager" );

	this.bindManager = m;

	this.serverSocketTableModel = new ServerSocketTableModel();
	this.bindManager.addBindListener( this.serverSocketTableModel );
	this.serverSocketTable = new JTable( this.serverSocketTableModel );

	JScrollPane sp = new JScrollPane( this.serverSocketTable,
					  JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
					  JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );

	
	this.infoArea = new JTextArea();
	this.infoArea.setEditable( false );
	this.infoArea.setLineWrap( true );
	JScrollPane sp2 = new JScrollPane( this.infoArea,
					   JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
					   JScrollPane.HORIZONTAL_SCROLLBAR_NEVER );
	

	Container main = new Container();
	main.setLayout( new GridLayout(1,2) );
	main.add( sp );
	main.add( sp2 );
	
	Container buttons_A = new Container();
	buttons_A.setLayout( new GridLayout(1,2) );
	buttons_A.add( this.buttonStartServer = new JButton("Start new Server ...") );
	buttons_A.add( this.buttonStopServer = new JButton("Stop selected server") );
	this.buttonStartServer.addActionListener( this );
	this.buttonStopServer.addActionListener( this );

	Container south = new Container();
	south.setLayout( new BorderLayout() );
	south.add( buttons_A, BorderLayout.NORTH );
	south.add( this.labelStatus = new JLabel("Status"), BorderLayout.SOUTH );


	this.getContentPane().setLayout( new BorderLayout() );
	this.getContentPane().add( main, BorderLayout.CENTER );
	this.getContentPane().add( south, BorderLayout.SOUTH );
	
      

	this.newServerDialog = new NewServerDialog( this );


	this.setBounds( 50, 50, 600, 400 );

	this.addInfo( "Welcome" );
    }


    public void actionPerformed( ActionEvent e ) {

	if( e.getSource() == this.buttonStartServer ) {
	    
	    this.newServerDialog.setVisible( true );


	} else if( e.getSource() == buttonStopServer ) {

	    int row = this.serverSocketTable.getSelectedRow();
	    if( row == -1 ) {
		setStatus( "No row is selected." );
		return;
	    }
	    
	    this.addInfo( "Releasing server socket ..." );

	    // else: row selected -> get UUID
	    BasicType t = this.serverSocketTableModel.getRow( row ).get( Constants.KEY_ID );
	    UUID id = t.getUUID(); // this should not cause no BasicTypeException here ...
	    
	    try {
		
		this.bindManager.release( id );
		this.addInfo( "Sever socket "+id+" successfully released." );

	    } catch( IOException exc ) {

		this.addInfo( "[IOException] "+exc.getMessage() );

	    }

	}
    }

    protected boolean performStartServer( InetAddress address,
					  int port,
					  Environment<String,BasicType> bindSettings ) {

	try {
	    
	    this.bindManager.bind( address, port, bindSettings );
	    this.addInfo( "Server started at "+address+":"+port+"." );
	    return true;
	    
	} catch( IOException exc ) {

	    setStatus( "[IOException] "+exc.getMessage(), true );
	    exc.printStackTrace();
	    return false;

	} catch( java.security.GeneralSecurityException exc ) {

	    setStatus( "[GeneralSecurityException] "+exc.getMessage(), true );
	    exc.printStackTrace();
	    return false;
	}
    }
				       

    public void setStatus( String msg ) {
	this.setStatus( msg, false );
    }

    public void setStatus( String msg, boolean addInfo ) {

	this.labelStatus.setText( msg );
	if( addInfo )
	    this.addInfo( msg );

    }

    public void addInfo( String msg ) {

	Calendar cal = Calendar.getInstance();
	String prefix = "[" + 
	    cal.get( Calendar.HOUR_OF_DAY ) + ":" + 
	    cal.get( Calendar.MINUTE ) +  ":" +
	    cal.get( Calendar.SECOND ) + "] ";

	this.infoArea.setText( this.infoArea.getText() + prefix + msg + "\n" );
	this.infoArea.setCaretPosition( this.infoArea.getText().length() );

    }

    public static void main( String[] argv ) {
	
	BindManager m = new BindManager();

	ServerManagerDialog d = new ServerManagerDialog( m );
	d.setVisible( true );
	
    }


}