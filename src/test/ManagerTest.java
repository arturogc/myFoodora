package test;


import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Date;

import org.junit.Test;

import deliveryPolicies.*;
import items.Dessert;
import items.Item;
import items.MainDish;
import items.Starter;
import items.Item.Type;
import junit.framework.TestCase;
import meals.FullMeal;
import meals.InvalidItemException;
import meals.Meal;
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
public class ManagerTest extends TestCase {

	private MyFoodora myfoodora;
	private Manager m;
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
		/**
		 * The MyFoodora system that we will use for the tests
		 */
		myfoodora = new MyFoodora(1, 0.05, 1, new FastestDelivery());
		
		/**
		 * The manager that oversees the system
		 */
		m = new Manager(myfoodora, "Arturo", "arturo_95", "Garrido", "realmadrid");
		
		/**
		 * users to add/remove from the system
		 */
		c = new Customer(myfoodora, "Guy", "guytayou", "Tayou", "jordan23", new Point2D.Double(5,1), "guy.tayou@student.ecp.fr", "0682647985");
		m.add_user("customer", c);
		c2 = new Customer(myfoodora, "Alexandre", "aflorez", "Florez", "bouffe", new Point2D.Double(-2,6), "alexandre.florez@student.ecp.fr", "06512347985");
		
		/**
		 * to test ComputeTotalIncome(), ComputeTotalProfit(), etc. we need
		 * to create at least one order (with its elements):
		 */
		
		/**
		 * restaurants to which the customer will order
		 */
		restaurant = new Restaurant(myfoodora, "Bonheur", "bonheur", "1986", new Point2D.Double(12,7));
		myfoodora.addRestaurant(restaurant);
		
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
		
		/**
		 * orders
		 */
		c.order("order1", restaurant, items, meals);
		c2.order("order1", restaurant, items, meals);
		
	}
	
	protected void tearDown() throws Exception, ExistingUserException, InvalidItemException {
		myfoodora = null;
		m = null;
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
	
	
	@Test
	public void testGetSurname() {
		assertTrue(m.getSurname().equals("Garrido"));
	}

	@Test
	public void testSetSurname() {
		m.setSurname("Tayou");
		assertTrue(m.getSurname().equals("Tayou"));
	}

	@Test
	public void testRemove_user() throws ExistingUserException {
		m.remove_user("customer", c);
		m.remove_user("restaurant", restaurant);
		m.remove_user("courier", courier1);
		assertTrue("Customer not correctly removed", myfoodora.getCustomers().size() == 0);
		assertTrue("Restaurant not correctly removed", myfoodora.getRestaurants().size() == 0);
		assertTrue("Courier not correctly removed", myfoodora.getCouriers().size() == 0);
	}

	@Test
	public void testAdd_user() {
		m.add_user("customer", c2);
		m.add_user("restaurant", restaurant);
		m.add_user("courier", courier1);
		assertTrue("Customer not correctly added", myfoodora.getCustomers().size() == 2);
		assertTrue("Restaurant not correctly added", myfoodora.getRestaurants().size() == 2);
		assertTrue("Courier not correctly added", myfoodora.getCouriers().size() == 2);
	}

	@Test
	public void testSetServiceFee() {
		m.setServiceFee(8.2);
		assertTrue("Service fee not correctly set", myfoodora.getServiceFee() == 8.2);
	}

	@Test
	public void testSetMarkupPercentage() {
		m.setMarkupPercentage(1.3);
		assertTrue("Markup percentage not correctly set", myfoodora.getMarkupPercentage() == 1.3);
	}

	@Test
	public void testSetDeliveryCost() {
		m.setDeliveryCost(1.2);
		assertTrue("Delivery cost not correctly set", myfoodora.getDeliveryCost() == 1.2);
	}

	/**
	 * we only need to check that the order is sent to MyFoodora
	 */
	@SuppressWarnings("deprecation")
	@Test
	public void testComputeTotalIncome() {
		assertNotNull("Inside call to the myfoodora function not processed correctly", m.ComputeTotalIncome(new Date(2013, 10, 22), new Date(2018, 10, 22)));
	}

	/**
	 * we only need to check that the order is sent to MyFoodora
	 */
	@SuppressWarnings("deprecation")
	@Test
	public void testComputeTotalProfit() {
		assertNotNull("Inside call to the myfoodora function not processed correctly", m.ComputeTotalProfit(new Date(2013, 10, 22), new Date(2018, 10, 22)));
	}

	/**
	 * we only need to check that the order is sent to MyFoodora
	 */
	@SuppressWarnings("deprecation")
	@Test
	public void testComputeAverageIncome() {
		assertNotNull("Inside call to the myfoodora function not processed correctly", m.ComputeAverageIncome(new Date(2013, 10, 22), new Date(2018, 10, 22)));
	}

	/*
	@Test
	public void testTargetProfit_DeliveryCost() {
		fail("Not yet implemented");
	}

	@Test
	public void testTargetProfit_ServiceFee() {
		fail("Not yet implemented");
	}

	@Test
	public void testTargetProfit_Markup() {
		fail("Not yet implemented");
	}
	*/

	/**
	 * we only need to check that the order is sent to MyFoodora
	 */
	@Test
	public void testMostSellingRestaurant() {
		assertNotNull("Inside call to the myfoodora function not processed correctly", m.mostSellingRestaurant());
	}

	/**
	 * we only need to check that the order is sent to MyFoodora
	 */
	@Test
	public void testLeastSellingRestaurant() {
		assertNotNull("Inside call to the myfoodora function not processed correctly", m.leastSellingRestaurant());
	}

	/**
	 * we only need to check that the order is sent to MyFoodora
	 */
	@Test
	public void testMostActiveCourier() {
		assertNotNull("Inside call to the myfoodora function not processed correctly", m.mostActiveCourier());
	}

	/**
	 * we only need to check that the order is sent to MyFoodora
	 */
	@Test
	public void testLeastActiveCourier() {
		assertNotNull("Inside call to the myfoodora function not processed correctly", m.leastActiveCourier());
	}

	@Test
	public void testSetDeliveryPolicy() {
		m.setDeliveryPolicy(new FairOccupationDelivery());
		assertTrue(myfoodora.getDeliveryPolicy() instanceof FairOccupationDelivery);
	}
	

	/**
	 * this test is correct only if the following one,
	 * testDisactivateUser, is also correct 
	 */
	@Test
	public void testActivateUser() {
		m.disactivateUser(c);
		m.activateUser(c);
		assertTrue("Customer not correctly activated", c.isEnabled());
	}

	@Test
	public void testDisactivateUser() {
		m.disactivateUser(c);
		assertFalse("Customer not correctly disactivated", c.isEnabled());
	}

}
