package users;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import items.Item;
import meals.Meal;
import notifications.*;
import system.MyFoodora;

/**
 * The class used to represent a restaurant
 * @author Guy Tayou
 * @author Arturo Garrido
 */
public class Restaurant extends User {
	
	/**
	 * Stores the usernames of the existing restaurants
	 */
	protected static final Set<String> usernames = new HashSet<String>();

	/**
	 * Stores the IDs of the existing restaurants
	 */
	protected static final Set<Integer> IDs = new HashSet<Integer>();
	
	/**
	 * address of a restaurant
	 */
	protected Point2D.Double address;
	
	/**
	 * whether or not a restaurant is enabled
	 */
	protected boolean enabled = true;

	/**
	 * generic discount factor of a restaurant
	 */
	protected double genericDiscountFactor = 0.05;

	/**
	 * special discount factor of a restaurant
	 */
	protected double specialDiscountFactor = 0.1;

	/**
	 * menu of a restaurant
	 */
	protected ArrayList<Item> menu = new ArrayList<Item>();

	/**
	 * meals provided by a restaurant
	 */
	protected ArrayList<Meal> meals = new ArrayList<Meal>();

	/**
	 * meals of the week of a restaurant
	 */
	protected ArrayList<Meal> mealsOfTheWeek = new ArrayList<Meal>();

	/**
	 * meal of the week of a restaurant
	 */
	protected int number_orders;
	
	
	public Restaurant(MyFoodora myfoodora, String name, String username, String password, Point2D.Double address) throws ExistingUserException {
		super();
        if (!usernames.add(username))
            throw new ExistingUserException();
        this.ID = IDs.size()+1;
        IDs.add(IDs.size()+1);
        this.username = username;
        this.password = password;
        this.name = name;
        this.address = address;
        this.myfoodora = myfoodora;
	}

	/**
	 * @return the address
	 */
	public Point2D.Double  getAddress() {
		return address;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(Point2D.Double address) {
		this.address = address;
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
	 * @return the genericDiscountFactor
	 */
	public double getGenericDiscountFactor() {
		return genericDiscountFactor;
	}

	/**
	 * @param genericDiscountFactor the genericDiscountFactor to set
	 */
	public void setGenericDiscountFactor(double genericDiscountFactor) {
		this.genericDiscountFactor = genericDiscountFactor;
		myfoodora.notifyObservers(this, Offer.genericDiscount);
	}

	/**
	 * @return the specialDiscountFactor
	 */
	public double getSpecialDiscountFactor() {
		return specialDiscountFactor;
	}

	/**
	 * @param specialDiscountFactor the specialDiscountFactor to set
	 */
	public void setSpecialDiscountFactor(double specialDiscountFactor) {
		this.specialDiscountFactor = specialDiscountFactor;
		myfoodora.notifyObservers(this, Offer.specialDiscount);
	}

	/**
	 * @return the menu
	 */
	public ArrayList<Item> getMenu() {
		return menu;
	}

	/**
	 * @param menu the menu to set
	 */
	public void setMenu(ArrayList<Item> menu) {
		this.menu = menu;
	}
	
	/**
	 * @param item the item to add to the menu
	 */
	public void addItem(Item item) {
		item.setRestaurant(this);
		menu.add(item);
	}
	
	/**
	 * 
	 * @param item the item to remove from the menu
	 */
	public void removeItem(Item item) {
		menu.remove(item);
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

	/**
	 * @param item the meal to add to the meals
	 */
	public void addMeal(Meal meal) {
		meal.setRestaurant(this);
		meals.add(meal);
	}
	
	/**
	 * @param meal the meal to remove from the meals
	 */
	public void removeMeal(Meal meal) {
		meals.remove(meal);
	}	

	/**
	 * @return the number_orders
	 */
	public int getNumber_orders() {
		return number_orders;
	}

	/**
	 * @param number_orders the number_orders to set
	 */
	public void setNumber_orders(int number_orders) {
		this.number_orders = number_orders;
	}
	

	/**
	 * @return the mealsOfTheWeek
	 */
	public ArrayList<Meal> getMealsOfTheWeek() {
		return mealsOfTheWeek;
	}

	/**
	 * @param mealsOfTheWeek the mealsOfTheWeek to set
	 */
	public void setMealOfTheWeek(ArrayList<Meal> mealsOfTheWeek) {
		this.mealsOfTheWeek = mealsOfTheWeek;
	}
	
	/**
	 * @param meal the mealOfTheWeek to add
	 */
	public void addMealOfTheWeek(Meal meal) {
		this.mealsOfTheWeek.add(meal);
		myfoodora.notifyObservers(this, Offer.mealOfTheWeek);
	}
	
	/**
	 * @param meal the mealOfTheWeek to remove
	 */
	public void removeMealOfTheWeek(Meal meal) {
		this.mealsOfTheWeek.remove(meal);
	}

}
