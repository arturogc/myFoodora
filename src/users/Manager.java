package users;

import deliveryPolicies.*;
import system.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * 
 * @author Arturo Garrido
 * 
 * a manager in charge of overseeing the MyFoodora system
 *
 */

public class Manager extends User {
	
	/**
	 * surname of the manager
	 */
	private String surname;
	
	/**
	 * Stores the usernames of the existing couriers
	 */
	protected static final Set<String> usernames = new HashSet<String>();

	/**
	 * Stores the IDs of the existing couriers
	 */
	protected static final Set<Integer> IDs = new HashSet<Integer>();
	
	

	/**
	 * 
	 * @param name the name of the manager
	 * @param username the unique username of the manager
	 * @param surname the surname of the manager
	 * @param password the password of the manager
	 * @throws ExistingUserException
	 */
	public Manager(MyFoodora myfoodora, String name, String username, String surname, String password) throws ExistingUserException {
		if (!usernames.add(username))
            throw new ExistingUserException();
		this.ID = IDs.size()+1;
        IDs.add(IDs.size()+1);
        this.myfoodora = myfoodora;
        this.username = username;
        this.password = password;
        this.name = name;
		this.surname = surname;
	}

	/**
	 * @param surname the surname of the manager
	 */
	public Manager(String surname) {
		super();
		this.surname = surname;
	}

	/**
	 * @return the surname of the manager
	 */
	public String getSurname() {
		return surname;
	}

	/**
	 * @param surname set the surname of the manager
	 */
	public void setSurname(String surname) {
		this.surname = surname;
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

	/**
	 * remove a user from the lists of users in the system.
	 *  
	 * @param type  type of user (restaurant, customer or courier) to remove 
	 * @param user specific user to remove
	 * @throws ExistingUserException 
	 */
	public void remove_user(String type, User user) throws ExistingUserException{
		if(type.equals("restaurant")) {
			ArrayList<Restaurant> temp = new ArrayList<Restaurant>();
			//temp = myfoodora.getRestaurants();
			for(Restaurant r:myfoodora.getRestaurants()) {
				temp.add(r);
			}
			for(Restaurant r:temp){
				if(((User) r).getID() == (user.getID())){
					myfoodora.removeRestaurant((Restaurant)user);
				}
			}
		}
		else if(type.equals("customer")) {
			ArrayList<Customer> temp = new ArrayList<Customer>();
			//temp = myfoodora.getCustomers();
			for(Customer c:myfoodora.getCustomers()) {
				temp.add(c);
			}
			for(Customer c:temp){
				if(((User) c).getID() == (user.getID())){
					myfoodora.removeCustomer((Customer)user);
				}
			}
		}
		else if(type.equals("courier")) {
			ArrayList<Courier> temp = new ArrayList<Courier>();
			//temp = myfoodora.getCouriers();
			for(Courier c:myfoodora.getCouriers()) {
				temp.add(c);
			}
			for(Courier c:temp){
				if(c.getID() == (user.getID())){
					myfoodora.removeCourier((Courier)user);
				}
			}
		}
	}
	
	/**
	 * add a user to the lists of users in the system.
	 *  
	 * @param type  type of user (restaurant, customer or courier) to add 
	 * @param user specific user to add
	 */
	public void add_user(String type, User user){
		if(type.equals("restaurant")) {
			myfoodora.addRestaurant((Restaurant)user);
		}
		else if(type.equals("customer")) {
			myfoodora.addCustomer((Customer)user);
		}
		else if(type.equals("courier")) {
			myfoodora.addCourier((Courier)user);
		}
	}
	
	/**
	 * change the service fee of the system
	 * @param fee the new service fee of the system
	 */
	public void setServiceFee(double fee) {
		myfoodora.setServiceFee(fee);
	}
	
	/**
	 * change the markup percentage of the system
	 * @param percentage the new markup percentage of the system
	 */
	public void setMarkupPercentage(double percentage) {
		myfoodora.setMarkupPercentage(percentage);
	}
	
	/**
	 * change the delivery cost of the system
	 * @param delivery the new delivery cost of the system
	 */
	public void setDeliveryCost(double delivery) {
		myfoodora.setDeliveryCost(delivery);
	}
	
	/**
	 * obtain the total income over a time period
	 * @param initial_date
	 * @param final_date
	 * @return the total income over the time period
	 */
	public double ComputeTotalIncome(Date initial_date, Date final_date) { 
		return myfoodora.ComputeTotalIncome(initial_date, final_date);
	}
	
	/**
	 * obtain the total profit over a time period
	 * @param initial_date
	 * @param final_date
	 * @return the total profit over the time period
	 */
	public double ComputeTotalProfit(Date initial_date, Date final_date){
		return myfoodora.ComputeTotalProfit(initial_date, final_date);
	}
	
	/**
	 * obtain the total profit
	 * @return the total profit
	 */
	public double ComputeTotalProfit(){
		return myfoodora.ComputeTotalProfit();
	}
	
	/**
	 * obtain the average income per customer over a time period
	 * @param initial_date
	 * @param final_date
	 * @return the average income per customer over the time period
	 */
	public double ComputeAverageIncome(Date initial_date, Date final_date){
		return myfoodora.ComputeAverageIncome(initial_date, final_date);
	}
	
	/**
	 * based on last month total income, given a markup percentage
	 * and a service fee, compute the delivery cost to meet the target profit
	 * @param targetprofit the profit we want to achieve
	 */
	public void targetProfit_DeliveryCost(double targetprofit){
		myfoodora.targetProfit_DeliveryCost(targetprofit);
	}
	

	/**
	 * based on last month total income, given a markup percentage
	 * and a delivery cost, compute the service fee to meet the target profit
	 * @param targetprofit the profit we want to achieve
	 */
	public void targetProfit_ServiceFee(double targetprofit){
		myfoodora.targetProfit_ServiceFee(targetprofit);
	}
	

	/**
	 * based on last month total income, given a delivery cost
	 * and a service fee, compute the markup percentage to meet the target profit
	 * @param targetprofit the profit we want to achieve
	 */
	public void targetProfit_Markup(double targetprofit){
		myfoodora.targetProfit_Markup(targetprofit);
	}
	
	/**
	 * determining the restaurant that sold the most
	 * @return the most selling restaurant
	 */
	public Restaurant mostSellingRestaurant() {
		return myfoodora.mostSellingRestaurant();
	}
	
	/**
	 * determining the restaurant that sold the least
	 * @return the least selling restaurant
	 */
	public Restaurant leastSellingRestaurant() {
		return myfoodora.leastSellingRestaurant();
	}
	
	/**
	 * determining the courier that delivered the most orders
	 * @return the most active courier
	 */
	public Courier mostActiveCourier() {
		return myfoodora.mostActiveCourier();
	}
	
	/**
	 * determining the courier that delivered the fewest orders
	 * @return the least active courier
	 */
	public Courier leastActiveCourier() {
		return myfoodora.leastActiveCourier();
	}
	
	public void setDeliveryPolicy(DeliveryPolicy deliveryPolicy){
		myfoodora.setDeliveryPolicy(deliveryPolicy);
	}
	
	/**
	 * 
	 * @param user the user to activate
	 */
	public void activateUser(User user) {
		user.setEnabled(true);
	}
	
	/**
	 * 
	 * @param user the user to disactivate
	 */
	public void disactivateUser(User user) {
		user.setEnabled(false);
	}
	
}


