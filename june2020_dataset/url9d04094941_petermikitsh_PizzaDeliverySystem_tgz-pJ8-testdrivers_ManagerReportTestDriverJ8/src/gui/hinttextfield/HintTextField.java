package gui.hinttextfield;

import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.*;

/**
 * @author http://stackoverflow.com/questions/1738966/java-jtextfield-with-input-hint
 */
public class HintTextField extends JTextField implements FocusListener {

	/**
	 * Default serial version ID.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * The hint to be displayed in the text field.
	 */
    private final String hint;

    /**
     * Constructor for a HintTextField.
     * 
     * @param hint   the hint to be displayed in the text field
     * @param columns   the specified number of columns
     */
    public HintTextField(final String hint, int columns) {
       
    	super(hint, columns);
        this.hint = hint;
        setForeground(Color.LIGHT_GRAY);
        super.addFocusListener(this);
        
    }

    @Override
    public void focusGained(FocusEvent e) {
    	
        if( this.getText().isEmpty() ) {
           
        	super.setText("");
            setForeground(null);
            
        }
        
    }
    
    @Override
    public void focusLost(FocusEvent e) {
    	
        if(this.getText().isEmpty()) {
           
        	super.setText(hint);
            setForeground(Color.LIGHT_GRAY);
            
        }
        
    }

    @Override
    public String getText() {
    	
        String typed = super.getText();
        return typed.equals(hint) ? "" : typed;
        
    }
    
    @Override
    public void setText(String text) {
    	
    	super.setText(text);
    	setForeground(null);
    	
    }
    
    /**
     * Clears the text field of its current text, once again
     * displaying the hint text.
     */
    public void clearTextField() {
    	
    	super.setText(hint);
        setForeground(Color.LIGHT_GRAY);
        
    }
    
}
