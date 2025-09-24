package login;

import java.util.ArrayList;

import viewcontroller.GeneralView;

/**
 * The abstract view for the Login module
 * 
 * @author 	Casey Klimkowsky	cek3403@g.rit.edu
 */
public class LoginView extends GeneralView {
	
	/**
	 * The input channels for this view.
	 */
	public enum UserInChan implements InputChannel {
		
		ICLoginIdInput,
		ICPasswordInput,
		ICStringInput;
		
	}
	
	/**
	 * The output channels for this view.
	 */
	public enum UserOutChan implements OutputChannel {
		
		OCInstructions,
		OCError;
		
	}

	@Override
	public void setChannelEnabled(InputChannel inChannel, boolean enabled) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void displayString(String message, OutputChannel outChannel) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void displayObject(Object object, OutputChannel outChannel) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <T> void displayList(ArrayList<T> list, OutputChannel outChannel) {
		// TODO Auto-generated method stub
		
	}

}
