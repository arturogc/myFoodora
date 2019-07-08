package meals;

import java.util.ArrayList;

import items.Dessert;
import items.Item;
import items.MainDish;
import items.Starter;
import orders.Order;
import users.Restaurant;

/**
 * The abstract class representing a Meal
 * 
 * @author Guy Tayou
 * @author Arturo Garrido
 */
public abstract class Meal {
	/**
	 * name of the meal
	 */
	protected String name;
	
	/**
	 * type of meal
	 */
	protected Item.Type type;
	
	/**
	 * starter of a meal, can be null
	 */
	protected Starter starter;
	
	/**
	 * main dish of a meal, cannot be null
	 */
	protected MainDish mainDish;

	/**
	 * dessert of a meal, can be null
	 */
	protected Dessert dessert;	
	
	/**
	 * the owner restaurant, by default null
	 */
	protected Restaurant restaurant = null;
	
	/**
	 * orders that contained the meal
	 */
	protected ArrayList<Order> orders = new ArrayList<Order>();
	

	public Meal(String name, Item.Type type) {
		this.name = name;
		this.type = type;
	}

	/**
	 * 
	 * 
	 * @param Starter
	 * @param MainDish
	 * @param Dessert
	 * 
	 * @throws InvalidItemException
	 */
	public abstract void prepare(Starter starter, MainDish mainDish, Dessert dessert)  throws InvalidItemException;
	
	/**
	 * 
	 * @return the computed price of the meal
	 */
	public abstract double price();


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

	
	/**
	 * @return the type
	 */
	public Item.Type getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(Item.Type type) {
		this.type = type;
	}

	/**
	 * @return the starter
	 */
	public Starter getStarter() {
		return starter;
	}

	/**
	 * @param starter the starter to set
	 */
	public void setStarter(Starter starter) {
		this.starter = starter;
	}

	/**
	 * @return the mainDish
	 */
	public MainDish getMainDish() {
		return mainDish;
	}

	/**
	 * @param mainDish the mainDish to set
	 */
	public void setMainDish(MainDish mainDish) {
		this.mainDish = mainDish;
	}

	/**
	 * @return the dessert
	 */
	public Dessert getDessert() {
		return dessert;
	}

	/**
	 * @param dessert the dessert to set
	 */
	public void setDessert(Dessert dessert) {
		this.dessert = dessert;
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
	 * @return the orders
	 */
	public ArrayList<Order> getOrders() {
		return orders;
	}

	/**
	 * @param orders the orders to set
	 */
	public void setOrders(ArrayList<Order> orders) {
		this.orders = orders;
	}
	
	/**
	 * @param order the order to add
	 */
	public void addOrder(Order order) {
		this.orders.add(order);
	}
	
	/**
	 * @param order the order to remove
	 */
	public void removeOrder(Order order) {
		this.orders.remove(order);
	}

	/**
	 * @return the discount factor of the restaurant we need to use the campute the price of the meal
	 */
	protected double discountFactor() {
		
		if(restaurant ==  null) {
			return 0;
		}
		
		double discountFactor = restaurant.getGenericDiscountFactor();
		
		if(restaurant.getMealsOfTheWeek().contains(this)) {
			discountFactor = restaurant.getSpecialDiscountFactor();
		}
		
		return discountFactor;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dessert == null) ? 0 : dessert.hashCode());
		result = prime * result + ((mainDish == null) ? 0 : mainDish.hashCode());
		result = prime * result + ((starter == null) ? 0 : starter.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Meal other = (Meal) obj;
		if (dessert == null) {
			if (other.dessert != null)
				return false;
		} else if (!dessert.equals(other.dessert))
			return false;
		if (mainDish == null) {
			if (other.mainDish != null)
				return false;
		} else if (!mainDish.equals(other.mainDish))
			return false;
		if (starter == null) {
			if (other.starter != null)
				return false;
		} else if (!starter.equals(other.starter))
			return false;
		if (type != other.type)
			return false;
		return true;
	}
	
	
	@Override
	public String toString() {
		return "Meal [name=" + name + ", type=" + type + "]";
	}
	

}
