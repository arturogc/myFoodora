package items;

import java.util.ArrayList;

import orders.Order;
import users.Restaurant;

/**
 * A class which represents an item
 * 
 * @author Guy Tayou
 * @author Arturo Garrido
 */
public class Item {

	/**
	 * Enumeration of the different types of item
	 * 
	 */
	public enum Type {
		Standard,
		Vegetarian,
		GlutenFree;
		
		/*
		public static Type findByAbbr(String abbr){
		    for(Type v : values()){
		        if( v.abbr().equals(abbr)){
		            return v;
		        }
		    }
		    return null;
		}
		*/
	}
	
	/**
	 * name of an item
	 */
	protected String name;
	
	/**
	 * price of an item
	 */
	protected double price;

	/**
	 * type of an item
	 */
	protected Type type;
	
	/**
	 * whether or not this item is gluten free
	 */
	protected boolean glutenFree;
	
	/**
	 * the owner Restaurant, by default null
	 */
	protected Restaurant restaurant = null;
	
	/**
	 * orders that contained the item
	 */
	protected ArrayList<Order> orders = new ArrayList<Order>();
	
	public Item(String name, double price, Type type, boolean glutenFree) {
		this.name = name;
		this.price = price;
		this.type = type;
		this.glutenFree = glutenFree;
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
	 * @return the type
	 */
	public Type getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(Type type) {
		this.type = type;
	}

	/**
	 * @return the glutenFree
	 */
	public boolean isGlutenFree() {
		return glutenFree;
	}

	/**
	 * @param glutenFree the glutenFree to set
	 */
	public void setGlutenFree(boolean glutenFree) {
		this.glutenFree = glutenFree;
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

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (glutenFree ? 1231 : 1237);
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		long temp;
		temp = Double.doubleToLongBits(price);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		Item other = (Item) obj;
		if (glutenFree != other.glutenFree)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (Double.doubleToLongBits(price) != Double.doubleToLongBits(other.price))
			return false;
		if (type != other.type)
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Item [name=" + name + ", price=" + price + ", type=" + type + ", glutenFree=" + glutenFree
				+ ", restaurant=" + restaurant.getName() + ", times ordered=" + orders + "]";
	}
	
	

}
