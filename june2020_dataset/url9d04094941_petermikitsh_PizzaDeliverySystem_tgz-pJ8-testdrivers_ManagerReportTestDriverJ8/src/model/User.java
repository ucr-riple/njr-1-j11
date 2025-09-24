package model;

import model.database.Database;

/**
 * A class that represents a store user.
 * 
 * @author  Casey Klimkowsky	cek3403@g.rit.edu
 */
public class User implements Cerealizable<User>, Comparable<User> {
	
	/**
	 * Database containing all user information.
	 */
	private static Database<User> _userDatabase;
	
	/**
	 * The user's name.
	 */
	private String name;
	
	/**
	 * The user's login ID.
	 */
	private String loginId;
	
	/**
	 * The user's password. 
	 */
	private String password;
	
	/**
	 * The user's permissions.
	 */
	private UserPermissions _permissions;
	
	/**
	 * User groups.
	 */
	public enum UserPermissions {
		
		ADMIN,  
		NON_ADMIN;
		
	}
	
	/**
	 * Default constructor for an User.
	 */
	public User() {
		
		this.name = "";
		this.loginId = "";
		this.password = "";
		_permissions = UserPermissions.NON_ADMIN;
		
	}
	
	/**
	 * Constructor for an User.
	 * 
	 * @param name   the Customer's name
	 * @param password   the Customer's login password
	 */
	public User(String name, String loginId, String password) {
		
		this.name = name;
		this.loginId = loginId;
		this.password = password;
		
	}
	
	/**
	 * Gets the user's name.
	 * 
	 * @return   the User's name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Sets the user's name to the given name.
	 * 
	 * @param name   the String to set the User's name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Gets the user's login ID.
	 * 
	 * @return   the User's login ID
	 */
	public String getLoginId() {
		return loginId;
	}
	
	/**
	 * Sets the user's login ID.
	 * 
	 * @param lid User's login ID
	 */
	public void setLoginId( String lid) {
		this.loginId = lid;
	}
	
	/**
	 * Gets the user's password.
	 * 
	 * @return   the User's password
	 */
	public String getPassword() {
		return password;
	}
	
	/**
	 * Sets the user's password to the one given.
	 * 
	 * @param password   the String to set the User's password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Get's the user's access group
	 * 
	 * @return the user's permissions
	 */
	public UserPermissions getPermissions() {
		return _permissions;
	}
	
	/** 
	 * Sets the user's access group
	 * @param userPermissions
	 */
	public void setPermissions(UserPermissions userPermissions) {
		_permissions = userPermissions;
	}
	
	/**
	 * Hashes the user's login ID to be used as a key.
	 * 
	 * @return   the hash code made using the User's loginId
	 */
	public int getKey() {
		return loginId.hashCode();
	}
	
	/**
	 * Gets the user database.
	 * 
	 * @return   the Database containing all User profiles
	 */
	public static Database<User> getDb() {
		
		if ( null == _userDatabase ) {
			_userDatabase = new Database<User>("users");
		}
		
		return _userDatabase;
		
	}
	
	/**
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(User other) {
		return this.getName().compareTo(other.getName());
	}
	
}
