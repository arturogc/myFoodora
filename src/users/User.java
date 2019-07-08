package users;

import system.MyFoodora;

/**
 * 
 * @author Arturo Garrido
 * 
 * a manager in charge of overseeing the MyFoodora system
 *
 */

public class User {
	/**
	 * name of the user
	 */
	protected String name;
	
	/**
	 * unique numerical ID of a user
	 */
	protected int ID;
	
	/**
	 * username of the user
	 */
	protected String username;
	
	/**
	 * password of the user
	 */
	protected String password;
	
	/**
	 * core system
	 */
	protected MyFoodora myfoodora;
	
	/**
	 * whether or not a courier is enabled
	 */
	protected boolean enabled = true;
	
	
	/**
	 * Default constructor
	 */
	public User() {
	}

	/**
	 * Creates a user with given name, id and username
	 * @param name
	 * @param iD
	 * @param username
	 */
	public User(String name, int iD, String username) {
		this.name = name;
		ID = iD;
		this.username = username;
	}

	/**
	 * get the name of the user
	 * @return the name of the user
	 */
	public String getName() {
		return name;
	}

	/** 
	 * set the name of the user
	 * @param name the name of the user
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * get the ID of the user
	 * @return the ID of the user
	 */
	public int getID() {
		return ID;
	}
	
	/**
	 * get the username of the user
	 * @return the username of the user
	 */
	public String getUsername() {
		return username;
	}
	
	/** 
	 * set the username of the user
	 * @param username the username of the user
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the myfoodora
	 */
	public MyFoodora getMyfoodora() {
		return myfoodora;
	}

	/**
	 * @param myfoodora the myfoodora to set
	 */
	public void setMyfoodora(MyFoodora myfoodora) {
		this.myfoodora = myfoodora;
	}

	/**
	 * @return the enabled
	 */
	public boolean isEnabled() {
		return enabled;
	}

	/**
	 * @param enabled the enabled to set
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	
	
}
