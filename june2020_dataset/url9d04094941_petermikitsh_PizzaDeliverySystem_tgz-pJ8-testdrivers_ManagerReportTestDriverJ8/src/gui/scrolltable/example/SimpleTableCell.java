package gui.scrolltable.example;

import gui.scrolltable.ZScrollTableCell;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;

/**
 * A simple example of a ZScrollTableCell subclass.
 * 
 * @author Athena
 */
@SuppressWarnings("serial")
public class SimpleTableCell extends ZScrollTableCell {

	/** 
	 * This text is displayed in large font.
	 */
	private JLabel _mainLabel;

	/**
	 * This is displayed in a smaller font.
	 */
	private JLabel _detailLabel;
	
	/**
	 * Creates a new cell. 
	 * @param width
	 * @param height
	 */
	public SimpleTableCell( int width, int height ) {
		

		this.setSize( width, height );
		this.setLayout( null );

		// Create a large JLabel.
		_mainLabel = new JLabel( "" );
		_mainLabel.setFont( new Font("SansSerif", Font.PLAIN, 22 ) );
		this.add(_mainLabel);
		_mainLabel.setBounds( 15, -65, 500, 200 );

		// Create a smaller JLabel.
		_detailLabel = new JLabel( "" );
		_detailLabel.setFont( new Font("SansSerif", Font.PLAIN, 13 ) );
		_detailLabel.setForeground( new Color( 60, 60, 60 ) );
		this.add(_detailLabel);
		_detailLabel.setBounds( 35, -40, 500, 200 );

	}

	public String getMainLabelText() {
		return _mainLabel.getText();
	}

	public void setMainLabelText( String string ) {
		_mainLabel.setText( string );
	}
	public String getDetailLabelText() {
		return _detailLabel.getText();
	}

	public void setDetailLabelText( String string ) {
		_detailLabel.setText( string );
	}

}
