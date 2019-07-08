package test;

import java.awt.geom.Point2D;
import java.util.ArrayList;

import org.junit.Test;

import clui.Finder;
import deliveryPolicies.FastestDelivery;
import items.Dessert;
import items.Item;
import items.MainDish;
import items.Starter;
import items.Item.Type;
import junit.framework.TestCase;
import meals.FullMeal;
import meals.HalfMeal;
import meals.InvalidItemException;
import meals.Meal;
import system.MyFoodora;
import users.Courier;
import users.Customer;
import users.ExistingUserException;
import users.Manager;
import users.Restaurant;

public class FinderTest extends TestCase {

	private Finder finder;
	private MyFoodora myfoodora;
	private Customer c;
	private Customer c2;
	private Restaurant restaurant;
	private Restaurant restaurant2;
	private Courier courier1;
	private Courier courier2;
	private Item i1;
	private Item i2;
	private Item i3;
	private Item i4;
	private Item i5;
	private ArrayList<Item> items;
	private Meal meal1;
	private ArrayList<Meal> meals;
	private ArrayList<Meal> meals2;
	private HalfMeal halfmeal1;
	
	
	protected void setUp() throws Exception, ExistingUserException, InvalidItemException {
		/**
		 * The MyFoodora system
		 */
		myfoodora = new MyFoodora(1, 0.05, 1, new FastestDelivery());
		
		/**
		 * the finder object
		 */
		finder = new Finder(null, myfoodora);
		
		/**
		 * add customers to the system
		 */
		c = new Customer(myfoodora, "Guy", "guytayou", "Tayou", "jordan23", new Point2D.Double(5,1), "guy.tayou@student.ecp.fr", "0682647985");
		myfoodora.addCustomer((Customer)c);
		c2 = new Customer(myfoodora, "Alexandre", "aflorez", "Florez", "bouffe", new Point2D.Double(-2,6), "alexandre.florez@student.ecp.fr", "06512347985");
		myfoodora.addCustomer((Customer)c2);
		
		/**
		 * add restaurants to the system
		 */
		restaurant = new Restaurant(myfoodora, "Bonheur", "bonheur", "1986", new Point2D.Double(12,7));
		myfoodora.addRestaurant(restaurant);
		restaurant2 = new Restaurant(myfoodora, "Burguer Lobby", "bgl", "1999", new Point2D.Double(2,7));
		myfoodora.addRestaurant(restaurant2);
		
		/**
		 * a courier that will deliver the order to the customer
		 */
		courier1 = new Courier("Megan", "Kardashian" , "meg", "kitty", new Point2D.Double(1,0));
		myfoodora.addCourier(courier1);
		courier2 = new Courier("Paul", "Kardashian" , "pgk", "1234", new Point2D.Double(4,1));
		myfoodora.addCourier(courier2);
		
		/**
		 * the items that will be ordered à la carte
		 */
		i1 = new Item("Soup", 8.5, Type.Standard, true);
		i2 = new Item("Cake", 5, Type.Vegetarian, false);
		i3 = new Starter("Chips", 4.5, Type.Standard, false);
		i4 = new MainDish("Burguer", 10.5, Type.Standard, false);
		i5 = new Dessert("Chocolate cake", 6.5, Type.Standard, false);
		restaurant.addItem(i1);
		restaurant.addItem(i2);
		restaurant.addItem(i3);
		restaurant.addItem(i4);
		restaurant.addItem(i5);
		items = new ArrayList<Item>();
		items.add(i1);
		items.add(i2);
		
		/**
		 * meals ordered
		 */
		meal1 = new FullMeal("meal_1", Type.Standard);
		meal1.prepare((Starter)i3, (MainDish)i4, (Dessert)i5);
		restaurant.addMeal(meal1);
		restaurant.addMealOfTheWeek(meal1);
		meals = new ArrayList<Meal>();
		meals.add(meal1);
		
		
		i1 = new Starter("Soup", 8.5, Type.Standard, true);
		i2 = new Dessert("Cake", 5, Type.Vegetarian, false);
		halfmeal1 = new HalfMeal("meal_2", Type.Standard);
		halfmeal1.setStarter((Starter)i1);
		halfmeal1.setMainDish((MainDish)i4);	
		restaurant.addMeal(halfmeal1);
		meals2 = new ArrayList<Meal>();
		meals2.add(halfmeal1);

		/**
		 * orders
		 */
		c.order("order1", restaurant, items, meals);
		myfoodora.processOrder(c.historyOrders().get(0));
		c.order("order2", restaurant, items, meals2);
		myfoodora.processOrder(c.historyOrders().get(1));
	}
	
	protected void tearDown() throws Exception, ExistingUserException, InvalidItemException {
		myfoodora = null;
		Customer.getUsernames().clear();
		Restaurant.getUsernames().clear();
		Courier.getUsernames().clear();
		Manager.getUsernames().clear();
		Customer.getIds().clear();
		Restaurant.getIds().clear();
		Courier.getIds().clear();
		Manager.getIds().clear();
		c = null;
		c2 = null;
		restaurant = null;
		restaurant2 = null;
		courier1 = null;
		courier2 = null;
		i1 = null;
		i2 = null;
		i3 = null;
		i4 = null;
		i5 = null;
		items = null;
		meal1 = null;
		meals = null;
		meals2 = null;
		halfmeal1 = null;
		finder = null;
	}
	
	@Test
	public void testFindMeal() {
		finder.setUser(restaurant);
		assertTrue("Meal not correctly found", finder.findMeal("meal_2").getStarter().getName().equals("Soup"));
	}

	@Test
	public void testFindOrder() {
		finder.setUser(c);
		assertTrue("Order not correctly found", finder.findOrder("order2").getMeals().get(0).getStarter().getName().equals("Soup"));
	}

	@Test
	public void testFindItem() {
		finder.setUser(restaurant);
		assertTrue("Item not correctly found", finder.findItem("Soup").getPrice() == 8.5);
	}

	@Test
	public void testFindCourier() {
		assertTrue("Courier not correctly found", finder.findCourier("pgk").getName().equals("Paul"));
	}

	@Test
	public void testFindCustomer() {
		assertTrue("Courier not correctly found", finder.findCustomer("guytayou").getName().equals("Guy"));
	}

	@Test
	public void testFindRestaurant() {
		assertTrue("Restaurant not correctly found", finder.findRestaurant("Bonheur").getUsername().equals("bonheur"));
	}

}
