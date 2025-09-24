package pl.cc.core;

/**
 * Klasa zawierająca parametry logowania
 * 
 * @since 2007-07-06
 */
public class LoginCredentials {
	public String username;
	public String password;
	public String exten;
	public String host;
	
	public LoginCredentials(String username, String password, String exten) {
		super();
		this.username = username;
		this.password = password;
		this.exten = exten;
	}

	public LoginCredentials(LoginCredentials loginCredentials) {
		super();
		this.username = new String(loginCredentials.username);
		if (loginCredentials.password!=null){
			this.password = new String(loginCredentials.password);
		}
		this.exten = new String(loginCredentials.exten);
		if (loginCredentials.host!=null){
			this.host = new String(loginCredentials.host);
		}
	}
	
	public LoginCredentials(String username, String password) {
		super();
		this.username=username;
		this.password=password;
	}

	public LoginCredentials() {
		super();
	}

	/**
	 * Tworzenie na podstawie parametrów uruchomienia programu
	 * @param args
	 */
	public LoginCredentials(String [] args){
		if (args.length==1){
			host = args[0];
		} else if (args.length>=4){
			host = args[0];
			username = args[1];
			password = args[2];
			exten = args[3];
		}
	}

	public String toString() {
	    String retValue = "";
	    retValue = "LoginCredentials ("
			//+ super.toString() +" "
	        + "username: [" + this.username + "] "
	        + "password: [" + this.password + "] "
	        + "exten: [" + this.exten + "] "
	        + "host: [" + this.host + "] "
	        + ")";
	    return retValue;
	}

	public boolean areComplete() {
		if (username.equals("")) return false;
		if (password.equals("")) return false;
		return true;
	}

	
	
}