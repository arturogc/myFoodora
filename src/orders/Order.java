package orders;

import items.*;
import meals.*;
import system.MyFoodora;
import users.*;

import java.util.Date;
import java.util.ArrayList;

/**
 * @author Arturo Garrido
 *
 */
public class Order {
	/**
	 * List of items ordered à la carte
	 */
	protected ArrayList<Item> items = new ArrayList<Item>();
	
	/**
	 * List of ordered meals (both half and full)
	 */
	protected ArrayList<Meal> meals = new ArrayList<Meal>();
	
	/**
	 * Restaurant that receives the order
	 */
	protected Restaurant restaurant;
	
	/**
	 * Courier that delivers the order
	 */
	protected Courier courier;
	
	/**
	 * Customer that orders
	 */
	protected Customer customer;
	
	/**
	 * Price of the order
	 */
	protected double price;
	
	/**
	 * Date of the order
	 */
	protected Date date;
	
	/**
	 * name of the order
	 */
	protected String name;

	public Order(String name, Customer customer, Restaurant restaurant, ArrayList<Item> items, ArrayList<Meal> meals) {
		this.name = name;
		this.customer = customer;
		this.restaurant = restaurant;
		this.items = items;
		this.meals = meals;
		date = new Date();
	}



	/**
	 * @return the items
	 */
	public ArrayList<Item> getItems() {
		return items;
	}


	/**
	 * @param items the items to set
	 */
	public void setItems(ArrayList<Item> items) {
		this.items = items;
	}

	/**
	 * @param item the item to add to the order
	 */
	public void addItem(Item item) {
		items.add(item);
	}
	
	/**
	 * @return the restaurant
	 */
	public Restaurant getRestaurant() {
		return restaurant;
	}



	/**
	 * @param restaurant the restaurant to set
	 */
	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}




	/**
	 * @return the courier
	 */
	public Courier getCourier() {
		return courier;
	}




	/**
	 * @param courier the courier to set
	 */
	public void setCourier(Courier courier) {
		this.courier = courier;
	}




	/**
	 * @return the customer
	 */
	public Customer getCustomer() {
		return customer;
	}




	/**
	 * @param customer the customer to set
	 */
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}




	/**
	 * @return the price
	 */
	public double getPrice() {
		return price;
	}




	/**
	 * @param price the price to set
	 */
	public void setPrice(double price) {
		this.price = price;
	}


	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}


	/**
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}


	/**
	 * @return the meals
	 */
	public ArrayList<Meal> getMeals() {
		return meals;
	}


	/**
	 * @param meals the meals to set
	 */
	public void setMeals(ArrayList<Meal> meals) {
		this.meals = meals;
	}

	public void addMeal(Meal meal) {
		this.meals.add(meal);
	}

	/**
	 * @param myfoodora the system that handles the orders
	 */
	public void process(MyFoodora myfoodora){
		myfoodora.processOrder(this);
	}



	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}



	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	

}
