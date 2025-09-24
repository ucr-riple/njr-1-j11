package ikrs.yuccasrv.ui;

import java.awt.*;
import java.awt.event.*;
import java.net.InetAddress;
import java.text.Collator;
import java.util.Map;
import java.util.TreeMap;
import javax.swing.*;

import ikrs.yuccasrv.Constants;
import ikrs.typesystem.*;
import ikrs.util.DefaultEnvironment;
import ikrs.util.Environment;

/**
 * @author Ikaros Kappler
 * @date 2012-04-23
 * @version 1.0.0
 **/


public class NewServerDialog 
    extends JDialog 
    implements ActionListener {

    private JComboBox
	addressComboBox;
    private InetAddressComboBoxModel
	addressComboBoxModel;

    private JSpinner
	portSpinner;
    private SpinnerNumberModel
	portSpinnerModel;
    

    private JRadioButton
	radioTCP,
	radioUDP;

    private JButton
	buttonOK,
	buttonCancel;

    private JSpinner
	backlogSpinner;
    private SpinnerNumberModel
	backlogSpinnerModel;

    private JLabel
	labelStatus;
    

    private ServerManagerDialog
	serverManagerDialog;

    public NewServerDialog( ServerManagerDialog d ) {
	super( d, "Create new Server", true );

	this.labelStatus = new JLabel("Status");
	
	// This might already throw an exception!
	try {
	    this.addressComboBox = new JComboBox(this.addressComboBoxModel = new InetAddressComboBoxModel());
	} catch( java.net.SocketException e ) {
	    setStatus( "Failed to load network interface/address list: " + e.getMessage() );
	    this.addressComboBox = new JComboBox(); 
	} 

	Container protocolRadios = new Container();
	protocolRadios.setLayout( new GridLayout(1,2) );
	protocolRadios.add( this.radioTCP = new JRadioButton("TCP",true) );
	protocolRadios.add( this.radioUDP = new JRadioButton("UDP",false) );

	ButtonGroup bg = new ButtonGroup();
	bg.add( this.radioTCP );
	bg.add( this.radioUDP );

	Container main = new Container();
	main.setLayout( new GridLayout(5,2) );
	main.add( new JLabel("Address") );
	main.add( this.addressComboBox );

	main.add( new JLabel("Port") );
	main.add( this.portSpinner = new JSpinner(this.portSpinnerModel = new SpinnerNumberModel(9339,1,65535,1)) );	
	// Get rid of the comma in the spinner display: define a new spinner editor
	JSpinner.NumberEditor editor = new JSpinner.NumberEditor(portSpinner, "#");
	portSpinner.setEditor(editor);

	main.add( new JLabel("Protocol") );
	main.add( protocolRadios );

	main.add( new JLabel("Backlog") );
	main.add( this.backlogSpinner = new JSpinner(this.backlogSpinnerModel = new SpinnerNumberModel(10,1,65535,1)) );	
	// Get rid of the comma in the spinner display: define a new spinner editor
	editor = new JSpinner.NumberEditor(backlogSpinner, "#");
	backlogSpinner.setEditor(editor);

	main.add( this.buttonCancel = new JButton("Cancel") );
	main.add( this.buttonOK = new JButton("OK") );


	this.buttonCancel.addActionListener( this );
	this.buttonOK.addActionListener( this );

	

	this.getContentPane().setLayout( new BorderLayout() );	
	this.getContentPane().add( main, BorderLayout.CENTER );
	this.getContentPane().add( this.labelStatus, BorderLayout.SOUTH );
	
	this.setBounds( 100, 100, 350, 150 );
	

	this.serverManagerDialog = d;
    }


    public void actionPerformed( ActionEvent e ) {

	if( e.getSource() == this.buttonOK ) {

	    InetAddress address = this.addressComboBoxModel.getSelectedAddress();
	    int port = this.portSpinnerModel.getNumber().intValue();
	    String protocol;
	    if( this.radioTCP.isSelected() )  protocol = "TCP";
	    else		              protocol = "UDP";

	    int backlog = this.backlogSpinnerModel.getNumber().intValue();


	    //Collator caseInsensitive = Collator.getInstance();
	    //caseInsensitive.setStrength( Collator.PRIMARY );
	    // Map<String,BasicType> bindSettings = new TreeMap<String,BasicType>( caseInsensitive );
	    java.util.Comparator<String> caseInsensitiveComparator = ikrs.util.CaseInsensitiveComparator.sharedInstance;
	    Environment<String,BasicType> bindSettings = 
		new DefaultEnvironment<String,BasicType>( new ikrs.util.TreeMapFactory<String,BasicType>(caseInsensitiveComparator) );
	    bindSettings.put( Constants.CONFIG_SERVER_PROTOCOL, new BasicStringType(protocol) );
	    bindSettings.put( Constants.CONFIG_SERVER_BACKLOG,  new BasicNumberType(backlog) );
	    bindSettings.put( Constants.CONFIG_SERVER_ADDRESS,  new BasicStringType(address.toString()) );
	    bindSettings.put( Constants.CONFIG_SERVER_PORT,     new BasicNumberType(port) );


	    setStatus( "Starting server: { "+
		       "address: "+address+", "+
		       "port: "+port+", "+
		       "protocol: "+protocol+", "+
		       "backlog: "+backlog+" } ...",
		       true );

	    boolean started = this.serverManagerDialog.performStartServer( address, port, bindSettings );
	    if( started ) {
		this.setVisible( false );
	    }

	} else if( e.getSource() == this.buttonCancel ) {

	    this.setVisible( false );

	}
	    
    }
    
    public void setStatus( String msg ) {
	setStatus( msg, false );
    }

    public void setStatus( String msg, boolean forwardToParent ) {
	this.labelStatus.setText( msg );
	if( forwardToParent )
	    this.serverManagerDialog.setStatus( msg );
    }

}