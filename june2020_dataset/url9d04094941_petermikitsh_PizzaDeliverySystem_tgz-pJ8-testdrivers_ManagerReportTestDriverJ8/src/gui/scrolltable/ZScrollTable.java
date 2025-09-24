package gui.scrolltable;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class ZScrollTable extends JPanel 
	implements MouseListener, ActionListener {

	/**
	 * The serial id of this class.
	 */
	private static final long serialVersionUID = -6923290105348913774L;
	
	/**
	 * The delegate object of this table.
	 */
	private ZScrollTableDelegate _delegate;
	
	/**
	 * The JPanel that holds all of this table's cells.
	 */
	private JPanel _contentPanel;
	
	/**
	 * The JScrollPane that holds the content panel and makes it scrollable.
	 */
	private JScrollPane _scrollPanel;
	
	/**
	 * Constructs a new table with a delegate object.
	 * 
	 * @param delegate  The delegate object of this class.
	 */
	public ZScrollTable( ZScrollTableDelegate delegate ) {
		
		// Store the delegate.
		_delegate = delegate;
		
		// Create the content pane.
		_contentPanel = new JPanel();
		_contentPanel.setLayout( null );
		_contentPanel.setBackground( _delegate.getTableBGColor( this ) );
		
		// Create the scroll pane.
		_scrollPanel = new JScrollPane( _contentPanel,
								JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
								JScrollPane.HORIZONTAL_SCROLLBAR_NEVER );
		
		// Set the default size of this view based on the width of a 
		// cell.
		this.setLayout( null );
		
		// Add the header.
		ZScrollTableCell header = _delegate.getHeaderCell( this );
		if( header != null ) {
			
			this.add( header );
			header.setBounds( 0, 0, header.getPreferredSize().width,
					header.getPreferredSize().height );
			
		}
		
		// Add the scroll pane to this panel.
		this.add( _scrollPanel );
		int startHeight = (header == null) ? 0 : header.getPreferredSize().height;
		_scrollPanel.setBounds( 0, startHeight, _scrollPanel.getPreferredSize().width,
				_scrollPanel.getPreferredSize().height );
		
	}
	
	/**
	 * Updates the data in this table and redraws the display.
	 */
	public void updateTable() {
		
		// Clear the content panel.
		_contentPanel.removeAll();
		
		int numCells = _delegate.getNumberOfCells( this );

		if( numCells == 0 ) {
			
			// Set the size of the content to fill the scroll thing.
			_contentPanel.setLayout( new GridBagLayout() );
			//_contentPanel.setPreferredSize( _scrollPanel.getPreferredSize() );
		
			// Create the label.
			JLabel emptyMessageLabel = new JLabel( "<html><center>" + 
					_delegate.getEmptyTableMessage( this ) +
					"</html></center>" );
			emptyMessageLabel.setFont( new Font( "SanSerif", Font.BOLD, 14 ) );
			Color bgColor = _delegate.getTableBGColor( this );
			emptyMessageLabel.setForeground( new Color( 255 - bgColor.getRed() + 50,
														255 - bgColor.getGreen() + 50,
														255 - bgColor.getBlue() + 50 ) );
//			emptyMessageLabel.setPreferredSize( new Dimension( _contentPanel.getPreferredSize().width - 60, 
//					_contentPanel.getPreferredSize().height - 30 ) );

			// Center the label in the panel.
			_contentPanel.add( emptyMessageLabel, new GridBagConstraints() );

		} else {
			
			_contentPanel.setLayout( null );

			// Get data about the table.
			int cellWidth = _delegate.getCellWidth( this );
			int cellSpacing = _delegate.getCellSpacing(this);

			// Calculate and set the size of the panel.
			int tableHeight = 0;
			for( int index = 0; index < numCells; index++ ) {

				int cellHeight = _delegate.getCellHeight( this, index );
				tableHeight += cellHeight + cellSpacing;

			}

			_contentPanel.setPreferredSize( new Dimension( cellWidth, tableHeight - cellSpacing ) );

			// Add cells to the table.
			int cellTop = 0;
			for( int index = 0; index < numCells; index++ ) {

				// Get the cell from the delegate and set the tag.
				ZScrollTableCell cell = _delegate.getCell( this, index );
				cell.setTag( index );

				// Set this class as a mouse listener for the cell, and 
				// add it to the contentPanel.
				cell.addMouseListener( this );
				_contentPanel.add( cell );

				// Adjust the size and position of the cell.
				int cellHeight = _delegate.getCellHeight( this, index );
				cell.setBounds( 0, cellTop, cellWidth, cellHeight );
				cellTop += cellSpacing + cellHeight;

				// Check to see if any of the children of the ZTable are delete buttons.
				boolean shouldAddDelete = true;
				for( Component comp : cell.getComponents() ) {
					if( comp instanceof ZDeleteButton ) {
						((ZDeleteButton) comp).setTag(index);
						shouldAddDelete = false;
						break;
					}
				}

				// If we should, create a delete button and put it in the
				// top right corner of the cell.
				if( shouldAddDelete && _delegate.canDeleteCell( this, index ) ) {

					ZDeleteButton deleteButton = new ZDeleteButton( index );
					deleteButton.addActionListener( this );
					cell.add( deleteButton );
					deleteButton.setBounds( cellWidth - 45 , cellHeight / 2 - 10 , 24, 24 );

				}

				// Done creating this cell.

			}

		}

		// Finally, update the scrollpane to reflect the possible changeds.
		_scrollPanel.updateUI();
		
	}
	
	/**
	 * Scrolls the table to the bottom of its content.
	 */
	public void scrollToBottom() {
		
		// Calculate the size of the content panel.
		int tableHeight = 0;
		int numCells = _delegate.getNumberOfCells( this );
		int cellSpacing = _delegate.getCellSpacing(this);
		for( int index = 0; index < numCells; index++ ) {

			int cellHeight = _delegate.getCellHeight( this, index );
			tableHeight += cellHeight + cellSpacing;

		}
		
		// Calculate the bottom of the scrolling thing.
		int now = _scrollPanel.getVerticalScrollBar().getValue();
		
		_scrollPanel.getVerticalScrollBar().setValue( now + 61 );
		
		
	}

	/**
	 * Accessor for the table's delegate.
	 * 
	 * @return This table's delegate.
	 */
	public ZScrollTableDelegate getDelegate() {
		return _delegate;
	}
	
	/**
	 * Sets the dimensions of this table.
	 * 
	 * @param dim	The new dimension of this panel.
	 */
	public void setPreferredSize( Dimension dim ) {
		
		super.setPreferredSize( dim );
		
		// Update the bounds of the scroll panel.
		ZScrollTableCell header = _delegate.getHeaderCell( this );
		int startHeight = (header == null) ? 0 : header.getPreferredSize().height;
		_scrollPanel.setBounds( 0, startHeight, dim.width, dim.height - startHeight );
		
	}
	
	/** (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent aEvent) {
		
		// Check if a delete button was pressed.
		// If the object was a button, send the table the delete message.
		if ( aEvent.getSource() instanceof ZDeleteButton ) {
			int index = ((ZDeleteButton)aEvent.getSource()).getTag();
			_delegate.cellWasDeleted(this, index);
		}

		// Update the table.
		this.updateTable();

	}

	
	/** (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 */
	public void mouseClicked( MouseEvent mEvent ) {
		
		// Get the index of the cell that was clicked, and tell the table 
		// that that cell was clicked.
		if( mEvent.getSource() instanceof ZScrollTableCell ) {
			int index = ((ZScrollTableCell)mEvent.getSource()).getTag();
			_delegate.cellWasClicked( this, index );
		}
		
		// Update the table.
		this.updateTable();
		
	}

	
	/** (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
	 */
	public void mouseEntered(MouseEvent mEvent) {}

	
	/** (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
	 */
	public void mouseExited(MouseEvent mEvent) {}

	
	/** (non-Javadoc)
	 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
	 */
	public void mousePressed(MouseEvent mEvent) {}

	
	/** (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
	 */
	public void mouseReleased(MouseEvent mEvent) {}

}

/**
 * A simple delete button for use with the ZScrollTable class.
 * 
 * @author 	Andrew Zemke	drew.zemke@gmail.com
 */
class ZDeleteButton extends JButton {
	
	/**
	 * The serial identifier for this calss.
	 */
	private static final long serialVersionUID = 7435429368660558440L;
	
	/**
	 * The integer identifier of this button.
	 */
	private int _tag = 0;
	
	/**
	 * Constructor for a ZDeleteButton.
	 * 
	 * @param tag 	The identifier for this button.
	 */
	public ZDeleteButton( int tag ) {

		// Make this have an icon.
		ImageIcon icon = new ImageIcon( this.getClass().getResource("/resources/deletebutton.png") );
		this.setIcon( icon );

		setBorder(null);
	    setBackground(null);
		
		_tag = tag;
		
	}
	
	public void setTag( int tag ){
		_tag = tag;
	}
	
	public int getTag() {
		return _tag;
	}
	
}
