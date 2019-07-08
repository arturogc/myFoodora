package users;
import java.util.HashSet;
import java.util.Set;

import java.awt.geom.Point2D;

/**
 * The class used to represent a courier
 * 
 * @author Guy Tayou
 */
public class Courier extends User {
	
	/**
	 * Enumeration of the possible states of a courier
	 */
	public enum State {
		OnDuty,
		OffDuty;
	}
	
	/**
	 * Stores the usernames of the existing couriers
	 */
	protected static final Set<String> usernames = new HashSet<String>();

	/**
	 * Stores the IDs of the existing couriers
	 */
	protected static final Set<Integer> IDs = new HashSet<Integer>();

	/**
	 * surname of a courier
	 */
	protected String surname;
	
	/**
	 * 2-dimensional position of a courier		
	 */
	protected Point2D.Double position;
	
	/**
	 * phone number of a courier
	 */
	protected String phoneNumber;
	
	/**
	 * A counter of delivered orders
	 */
	protected int deliveredOrders;
	
	/**
	 * state of a courier
	 */
	protected State state;
	

	public Courier(String name, String surname, String username, String password, Point2D.Double position) throws ExistingUserException {
        if (!usernames.add(username))
            throw new ExistingUserException();
        this.ID = IDs.size()+1;
        IDs.add(IDs.size()+1);
        this.username = username;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.position = position;
        this.state = State.OnDuty;
	}

	/**
	 * @return the surname
	 */
	public String getSurname() {
		return surname;
	}

	/**
	 * @param surname the surname to set
	 */
	public void setSurname(String surname) {
		this.surname = surname;
	}

	/**
	 * @return the position
	 */
	public Point2D.Double getPosition() {
		return position;
	}

	/**
	 * @param position the position to set
	 */
	public void setPosition(Point2D.Double position) {
		this.position = position;
	}

	/**
	 * @return the phoneNumber
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}

	/**
	 * @param phoneNumber the phoneNumber to set
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	/**
	 * @return the state
	 */
	public State getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(State state) {
		this.state = state;
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
	
	/**
	 * @return the deliveredOrders
	 */
	public int getDeliveredOrders() {
		return deliveredOrders;
	}

	/**
	 * @param deliveredOrders the deliveredOrders to set
	 */
	public void setDeliveredOrders(int deliveredOrders) {
		this.deliveredOrders = deliveredOrders;
	}

	/**
	 * @return the usernames
	 */
	public static Set<String> getUsernames() {
		return usernames;
	}

	/**
	 * @return the ids
	 */
	public static Set<Integer> getIds() {
		return IDs;
	}

}
