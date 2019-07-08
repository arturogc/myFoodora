package test;

import java.awt.geom.Point2D;
import java.util.ArrayList;

import org.junit.Test;

import deliveryPolicies.FastestDelivery;
import items.Dessert;
import items.Item;
import items.MainDish;
import items.Starter;
import items.Item.Type;
import junit.framework.TestCase;
import meals.FullMeal;
import meals.InvalidItemException;
import meals.Meal;
import orders.Order;
import system.MyFoodora;
import users.Courier;
import users.Customer;
import users.ExistingUserException;
import users.Manager;
import users.Restaurant;

/**
 * 
 * @author Arturo Garrido
 *
 */
public class OrderTest extends TestCase {

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
	private Order order;
	
	protected void setUp() throws Exception, ExistingUserException, InvalidItemException {
		/**
		 * The MyFoodora system that we will use for the tests
		 */
		myfoodora = new MyFoodora(1, 0.05, 1, new FastestDelivery());
		
		/**
		 * we will create different instances of the inputs of
		 * Order to test the getters and setters
		 */
		
		/**
		 * users that will order
		 */
		c = new Customer(myfoodora, "Guy", "guytayou", "Tayou", "jordan23", new Point2D.Double(5,1), "guy.tayou@student.ecp.fr", "0682647985");
		c2 = new Customer(myfoodora, "Alexandre", "aflorez", "Florez", "bouffe", new Point2D.Double(-2,6), "alexandre.florez@student.ecp.fr", "06512347985");
		
		/**
		 * restaurants that will receive the order
		 */
		restaurant = new Restaurant(myfoodora, "Bonheur", "bonheur", "1986", new Point2D.Double(12,7));
		restaurant2 = new Restaurant(myfoodora, "Burguer Lobby", "bgl", "1999", new Point2D.Double(2,7));
		
		/**
		 * couriers that will deliver the order to the customer
		 */
		courier1 = new Courier("Megan", "Kardashian" , "meg", "kitty", new Point2D.Double(1,0));
		myfoodora.addCourier(courier1);
		courier2 = new Courier("Paul", "Kardashian" , "pgk", "1234", new Point2D.Double(4,1));
		
		/**
		 * the items that will be ordered à la carte
		 */
		i1 = new Starter("Soup", 8.5, Type.Standard, true);
		i2 = new Dessert("Cake", 5, Type.Vegetarian, false);
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

		/**
		 * un exemple de order
		 */
		order = new Order("test_order", c, restaurant, items, meals);
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
		order = null;
	}
	
	@Test
	public void testGetItems() {
		assertTrue("Items not obtained correctly", order.getItems().get(0).getName().equals("Soup"));
		assertTrue("Items not obtained correctly", order.getItems().get(1).getName().equals("Cake"));
	}

	/**
	 * this test is only valid if the previous one works correctly
	 */
	@Test
	public void testSetItems() {
		ArrayList<Item> new_items = new ArrayList<Item>();
		new_items.add(i2);
		new_items.add(i3);
		new_items.add(i4);
		order.setItems(new_items);
		assertTrue("Items not set correctly", order.getItems().get(0).getName().equals("Cake"));
		assertTrue("Items not set correctly", order.getItems().get(1).getName().equals("Chips"));
		assertTrue("Items not set correctly", order.getItems().get(2).getName().equals("Burguer"));
	}

	@Test
	public void testGetRestaurant() {
		assertTrue("Restaurant not obtained correctly",order.getRestaurant().getName().equals("Bonheur"));
	}

	/**
	 * this test is only valid if the previous one works correctly
	 */
	@Test
	public void testSetRestaurant() {
		order.setRestaurant(restaurant2);
		assertTrue("Restaurant not set correctly", order.getRestaurant().getName().equals("Burguer Lobby"));
	}

	@Test
	public void testGetCourier() {
		order.process(myfoodora);
		assertTrue("Courier not obtained correctly", order.getCourier().getName().equals("Megan"));
	}

	/**
	 * this test is only valid if the previous one works correctly
	 */
	@Test
	public void testSetCourier() {
		order.setCourier(courier2);
		assertTrue("Courier not set correctly", order.getCourier().getName().equals("Paul"));
	}

	@Test
	public void testGetCustomer() {
		assertTrue("Courier not obtained correctly", order.getCustomer().getName().equals("Guy"));
	}

	/**
	 * this test is only valid if the previous one works correctly
	 */
	@Test
	public void testSetCustomer() {
		order.setCustomer(c2);
		assertTrue("Courier not set correctly", order.getCustomer().getName().equals("Alexandre"));
	}

	@Test
	public void testGetPrice() {
		order.process(myfoodora);
		assertTrue("Price not obtained correctly", order.getPrice() == 32.85);
	}

	/**
	 * this test is only valid if the previous one works correctly
	 */
	@Test
	public void testSetPrice() {
		order.setPrice(28.9);
		assertTrue("Price not obtained correctly", order.getPrice() == 28.9);
	}


	@Test
	public void testGetMeals() {
		assertTrue("Meals not obtained correctly", order.getMeals().get(0).getDessert().getName().equals("Chocolate cake"));
	}

	/**
	 * this test is only valid if the previous one works correctly
	 */
	@Test
	public void testSetMeals() throws InvalidItemException {
		ArrayList<Meal> new_meals = new ArrayList<Meal>();
		Meal new_meal = new FullMeal("meal_2", Type.Standard);
		new_meal.prepare((Starter)i1, (MainDish)i4, (Dessert)i2);
		new_meals.add(new_meal);
		order.setMeals(new_meals);
		assertTrue("Meals not set correctly", order.getMeals().get(0).getDessert().getName().equals("Cake"));
	}

	/**
	 * We only need to verify if the command to process the
	 * order is sent to the myfoodora system
	 */
	@Test
	public void testProcess() {
		order.process(myfoodora);
		assertNotNull(order.getCourier());
	}

}
