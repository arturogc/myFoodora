package users;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import fidelityCards.*;
import items.*;
import meals.*;
import notifications.*;
import orders.*;
import system.*;

/**
 * 
 * @author Arturo
 * 
 * a customer of the MyFoodora system
 *
 */

public class Customer extends User implements Observer {
	
	/**
	 * surname of the user
	 */
	protected String surname;
	
	/**
	 * Adress of the user
	 */
	protected Point2D.Double adress;
	
	/**
	 * email adress of the user
	 */
	protected String email_adress;
	
	/**
	 * user's phone number
	 */
	protected String phone;
	
	/**
	 * fidelity cards of the user
	 */
	protected FidelityCard fidelityCard = new BasicFidelityCard();
	
	/**
	 * consensus to be notified when there is a new special offer
	 */
	protected Boolean consensus = false;
	
	/**
	 * enumeration of contacts to be used to send the offers
	 *
	 */
	public enum Contact_offers {
		email,
		phone;
	}
	
	/**
	 * contact to be used to send the offers
	 */
	protected Contact_offers contact_offers = Contact_offers.email;
	
	protected ArrayList<Order> orders = new ArrayList<Order>();
	
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
	 * @param name
	 * @param username
	 * @param surname
	 * @param adress
	 * @param email_adress
	 * @param phone
	 * @throws ExistingUserException
	 */
	public Customer(MyFoodora myfoodora, String name, String username, String surname, String password, Point2D.Double adress, String email_adress, String phone) throws ExistingUserException {
		if (!usernames.add(username))
            throw new ExistingUserException();
		this.ID = IDs.size()+1;
        IDs.add(IDs.size()+1);
        this.myfoodora = myfoodora;
		this.name = name;
		this.username = username;
		this.surname = surname;
		this.adress = adress;
		this.email_adress = email_adress;
		this.phone = phone;
		this.password = password;
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
	 * @return the adress
	 */
	public Point2D.Double getAdress() {
		return adress;
	}



	/**
	 * @param adress the adress to set
	 */
	public void setAdress(Point2D.Double adress) {
		this.adress = adress;
	}



	/**
	 * @return the email adress
	 */
	public String getEmail_adress() {
		return email_adress;
	}
	
	/**
	 * @param email adress the email_adress to set
	 */
	public void setEmail_adress(String email_adress) {
		this.email_adress = email_adress;
	}
	
	/**
	 * @return the phone number
	 */
	public String getPhone() {
		return phone;
	}
	
	/**
	 * @param phone the phone number to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}



	/**
	 * @return the fidelityCard
	 */
	public FidelityCard getFidelityCard() {
		return fidelityCard;
	}



	/**
	 * @param fidelityCard the fidelityCard to set
	 */
	public void setFidelityCard(FidelityCard fidelityCard) {
		this.fidelityCard = fidelityCard;
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
	 * @return the consensus
	 */
	public Boolean getConsensus() {
		return consensus;
	}


	/**
	 * @return the contact_offers
	 */
	public Contact_offers getContact_offers() {
		return contact_offers;
	}
	
	

	/**
	 * @param contact_offers the contact_offers to set
	 */
	public void setContact_offers(Contact_offers contact_offers) {
		this.contact_offers = contact_offers;
	}



	/**
	 * sets the parameter consensus and updates the lists of observers
	 * @param consensus the consensus to set
	 */
	public void setConsensus(Boolean consensus) {
		//If it didn't give consensus and now it does, add it to the list
		if(!this.consensus) {
			if(consensus){
				this.myfoodora.registerObserver(this);
			}
		}
		
		//If it gave consensus but now it doesn't, eliminate form the list
		if(this.consensus) {
			if(!consensus){
				this.myfoodora.removeObserver(this);
			}
		}
		
		this.consensus = consensus;
	}



	/**
	 * the information of the order
	 * @param restaurant
	 * @param starters
	 * @param maindishes
	 * @param desserts
	 * @param fullmeals
	 * @param halfmeals
	 */
	public void order(String name, Restaurant restaurant, ArrayList<Item> items, ArrayList<Meal> meals) {
		OrderFactory orderfactory = new OrderFactory();
		Order order = orderfactory.CreateOrder(name, this, restaurant, items, meals);
		orders.add(order);
		/*
		order.process(myfoodora);
		*/
	}
	
	/**
	 * 
	 * @return history of orders
	 */
	public ArrayList<Order> historyOrders(){
		return orders;
	}


	/**
	 * Notify the user that a restaurant has a new special offer
	 */
	@Override
	public void update(Restaurant restaurant, Offer offer) {
		if(consensus) {
			switch(offer){
			case mealOfTheWeek:
				switch(this.contact_offers) {
				case email:
					System.out.println("New email received at " + this.email_adress + ": Restaurant " + restaurant.getName() + " has a new special offer: "
							+ " New meal of the week: " + restaurant.getMealsOfTheWeek().get(restaurant.getMealsOfTheWeek().size()-1).toString());
					break;
				case phone:
					System.out.println("New sms received at " + this.phone + ": Restaurant " + restaurant.getName() + " has a new special offer: "
							+ " New meal of the week: " + restaurant.getMealsOfTheWeek().get(restaurant.getMealsOfTheWeek().size()-1).toString());
					break;
				}
				break;
				
			case genericDiscount:
				switch(this.contact_offers) {
				case email:
					System.out.println("New email received at " + this.email_adress + ": Restaurant " + restaurant.getName() + " has a new special offer: "
							+ " New generic discount factor: " + restaurant.getGenericDiscountFactor());
					break;
				case phone:
					System.out.println("New sms received at " + this.phone + ": Restaurant " + restaurant.getName() + " has a new special offer: "
							+ " New generic discount factor: " + restaurant.getGenericDiscountFactor());
					break;
				}
				break;
				
			case specialDiscount:
				switch(this.contact_offers) {
				case email:
					System.out.println("New email received at " + this.email_adress + ": Restaurant " + restaurant.getName() + " has a new special offer: "
							+ " New special discount factor: " + restaurant.getSpecialDiscountFactor());
					break;
				case phone:
					System.out.println("New sms received at " + this.phone + ": Restaurant " + restaurant.getName() + " has a new special offer: "
							+ " New special discount factor: " + restaurant.getSpecialDiscountFactor());
					break;
				}
				break;
			}
		}
	}
	
	

}
