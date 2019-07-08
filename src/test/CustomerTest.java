package test;


import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import org.junit.Test;

import deliveryPolicies.*;
import items.*;
import items.Item.Type;
import junit.framework.TestCase;
import meals.*;
import system.MyFoodora;
import users.*;
import users.Customer.Contact_offers;

/**
 * 
 * @author Arturo Garrido
 *
 */
public class CustomerTest extends TestCase {
	
	private MyFoodora myfoodora;
	private Customer c;
	private Customer c2;
	private Restaurant restaurant;
	private Courier courier1;
	private Item i1;
	private Item i2;
	private Item i3;
	private Item i4;
	private Item i5;
	private ArrayList<Item> items;
	private Meal meal1;
	private ArrayList<Meal> meals;

	protected void setUp() throws Exception, ExistingUserException, InvalidItemException {
		super.setUp();
		
		/**
		 * The MyFoodora system that we will use for the tests
		 */
		myfoodora = new MyFoodora(1, 0.05, 1, new FastestDelivery());
		
		/**
		 * the customers
		 */
		c = new Customer(myfoodora, "Arturo", "agc20", "Garrido", "realmadrid", new Point2D.Double(2,3), "arturo.garrido@student.ecp.fr", "0782683879");
		c2 = new Customer(myfoodora, "Guy", "guytayou", "Tayou", "jordan23", new Point2D.Double(5,1), "guy.tayou@student.ecp.fr", "0682647985");
		
		/**
		 * a restaurant to which the customer will order
		 */
		restaurant = new Restaurant(myfoodora, "Bonheur", "bonheur", "1986", new Point2D.Double(12,7));
		
		/**
		 * a courier that will deliver the order to the customer
		 */
		courier1 = new Courier("Megan", "Kardashian", "meg", "kitty", new Point2D.Double(1,0));
		myfoodora.addCourier(courier1);
		
		/**
		 * the items he will order à la carte
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
		
	}
	
	
	protected void tearDown() throws Exception, ExistingUserException, InvalidItemException {
		super.tearDown();
		myfoodora = null;
		Customer.getUsernames().clear();
		Restaurant.getUsernames().clear();
		Courier.getUsernames().clear();
		Customer.getIds().clear();
		Restaurant.getIds().clear();
		Courier.getIds().clear();
		c = null;
		c2 = null;
		restaurant = null;
		courier1 = null;
		i1 = null;
		i2 = null;
		i3 = null;
		i4 = null;
		i5 = null;
		items = null;
		meal1 = null;
		meals = null;
	}
	

	/**
	 * check that a history of orders is well created and returned
	 */
	@Test
	public void testHistoryOrders() {
		c.order("order1", restaurant, items, meals);
		c2.order("order2", restaurant, items, meals);
		assertNotNull("History of orders not created", c.historyOrders());
		assertTrue("History of orders not correct", c.historyOrders().size() == 1);
		assertTrue("History of orders not correct", c.historyOrders().get(0).getCustomer().getName().equals("Arturo"));
	}
	
	
	/**
	 * the only job of customer is to create the order
	 */
	@Test
	public void testOrder() throws ExistingUserException, InvalidItemException{
		c.order("order1", restaurant, items, meals);
		c2.order("order2", restaurant, items, meals);
		assertNotNull("Order not created", c.historyOrders().get(0));
		assertTrue("Order not created correctly", c.historyOrders().get(0).getItems().get(0).getName().equals("Soup"));
	}
	

	@Test
	public void testUpdate() {
		c.setConsensus(true);
		restaurant.addMealOfTheWeek(meal1);
	}

	
	@Test
	public void testGetSurname() {
		assertTrue(c.getSurname().equals("Garrido"));
	}

	@Test
	public void testSetSurname() {
		c.setSurname("Hollande");
		assertTrue(c.getSurname().equals("Hollande"));
	}

	@Test
	public void testGetAdress() {
		assertTrue(c.getAdress().equals(new Point(2,3)));
	}

	@Test
	public void testSetAdress() {
		c.setAdress(new Point2D.Double(0,0));
		assertTrue(c.getAdress().equals(new Point(0,0)));
	}

	@Test
	public void testGetEmail_adress() {
		assertTrue(c.getEmail_adress().equals("arturo.garrido@student.ecp.fr"));
	}

	@Test
	public void testSetEmail_adress() {
		c.setEmail_adress("artu_95@hotmail.com");
		assertTrue(c.getEmail_adress().equals("artu_95@hotmail.com"));
	}

	@Test
	public void testGetPhone() {
		assertTrue(c.getPhone().equals("0782683879"));
	}

	@Test
	public void testSetPhone() {
		c.setPhone("664672602");
		assertTrue(c.getPhone().equals("664672602"));
	}

	@Test
	public void testGetConsensus() {
		assertFalse(c.getConsensus());
	}

	@Test
	public void testSetConsensus() {
		c.setConsensus(true);
		assertTrue(c.getConsensus());
	}
	
	@Test
	public void testGetContact_offers() {
		assertTrue(c.getContact_offers() == Contact_offers.email);
	}

	@Test
	public void testSetContact_offers() {
		c.setContact_offers(Contact_offers.phone);
		assertTrue(c.getContact_offers() == Contact_offers.phone);
	}

}
