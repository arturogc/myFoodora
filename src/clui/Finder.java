package clui;

import items.Item;
import meals.Meal;
import orders.Order;
import system.MyFoodora;
import users.Courier;
import users.Customer;
import users.Restaurant;
import users.User;

public class Finder {
	
	/**
	 * The logged in user 
	 */
	private User user;
	
	/**
	 * MyFoodora system
	 */
	private MyFoodora myFoodora;
	
	public Finder(User user, MyFoodora myfoodora) {
		this.user = user;
		this.myFoodora = myfoodora;
	}
	
	
	
	/**
	 * Finds a meal provided by the logged restaurant by its name
	 * 
	 * @param the name of the meal
	 * @return the meal
	 */
	public Meal findMeal(String mealName) {
		for(Meal m : ((Restaurant)user).getMeals()) {
			if(m.getName().equals(mealName)) {
				return m;
			}
		}
		return null;
	}
	
	/**
	 * Finds a meal from a restaurant when the logged in
	 * user is not a restuarant
	 * 
	 * @param the name of the meal
	 * @return the meal
	 */
	public Meal findMeal(String mealName, Restaurant restaurant) {
		for(Meal m : restaurant.getMeals()) {
			if(m.getName().equals(mealName)) {
				return m;
			}
		}
		return null;
	}
	
	/**
	 * Finds an order provided by the logged customer by its name
	 * 
	 * @param orderName the name of the order
	 * @return the order
	 */
	public Order findOrder(String orderName) {
		for(Order o : ((Customer)user).historyOrders()) {
			if(o.getName().equals(orderName)) {
				return o;
			}
		}
		return null;
	}
	
	/**
	 * Finds an order in the system, with no need to log in
	 * 
	 * @param orderName the name of the order
	 * @return the order
	 */
	public Order findOrder(String orderName, MyFoodora myfoodora) {
		for(Order o : myfoodora.getOrders()) {
			if(o.getName().equals(orderName)) {
				return o;
			}
		}
		return null;
	}

	/**
	 * Finds an item from a restaurant when the logged in user
	 * is not a restaurant
	 * 
	 * @param itemName the name of the item
	 * @return the item
	 */
	public Item findItem(String itemName, Restaurant restaurant) {
		for(Item i : restaurant.getMenu()) {
			if(i.getName().equals(itemName)) {
				return i;
			}
		}
		return null;
	}
	
	/**
	 * Finds an item provided by the logged restaurant by its name
	 * 
	 * @param itemName the name of the item
	 * @return the item
	 */
	public Item findItem(String itemName) {
		for(Item i : ((Restaurant)user).getMenu()) {
			if(i.getName().equals(itemName)) {
				return i;
			}
		}
		return null;
	}
	
	/**
	 * Finds a registered courier by its username
	 * 
	 * @param username the name of the courier
	 * @return the courier
	 */
	public Courier findCourier(String username) {
		for(Courier c : myFoodora.getCouriers()) {
			if(c.getUsername().equals(username)) {
				return c;
			}
		}
		return null;
	}

	/**
	 * Finds a registered customer by its username
	 * 
	 * @param username the name of the customer
	 * @return the customer
	 */
	public Customer findCustomer(String username) {
		for(Customer c : myFoodora.getCustomers()) {
			if(c.getUsername().equals(username)) {
				return c;
			}
		}
		return null;
	}
	

	/**
	 * Finds a registered restaurant by its name
	 * 
	 * @param restaurantName the name of the restaurant
	 * @return the restaurant
	 */
	public Restaurant findRestaurant(String restaurantName) {
		for(Restaurant r : myFoodora.getRestaurants()) {
			if(r.getName().equals(restaurantName)) {
				return r;
			}
		}
		return null;
	}



	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}



	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}



	/**
	 * @return the myFoodora
	 */
	public MyFoodora getMyFoodora() {
		return myFoodora;
	}



	/**
	 * @param myFoodora the myFoodora to set
	 */
	public void setMyFoodora(MyFoodora myFoodora) {
		this.myFoodora = myFoodora;
	}
	
	
}
