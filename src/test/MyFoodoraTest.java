package test;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Date;

import org.junit.Test;

import deliveryPolicies.FairOccupationDelivery;
import deliveryPolicies.FastestDelivery;
import items.*;
import items.Item.Type;
import junit.framework.TestCase;
import meals.*;
import notifications.Offer;
import orders.Order;
import system.MyFoodora;
import users.*;

public class MyFoodoraTest extends TestCase {

	private MyFoodora myfoodora;
	private Manager m;
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
	private HalfMeal halfmeal1;
	private HalfMeal halfmeal2;
	private HalfMeal halfmeal3;
	
	protected void setUp() throws Exception, ExistingUserException, InvalidItemException {
		/**
		 * The MyFoodora system
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
		 * restaurants
		 */
		restaurant = new Restaurant(myfoodora, "Bonheur", "bonheur", "1986", new Point2D.Double(12,7));
		myfoodora.addRestaurant(restaurant);
		restaurant2 = new Restaurant(myfoodora, "Burguer Lobby", "bgl", "1999", new Point2D.Double(2,7));
		
		/**
		 * a courier that will deliver the order to the customer
		 */
		courier1 = new Courier("Megan", "Kardashian" , "meg", "kitty", new Point2D.Double(1,0));
		courier2 = new Courier("Paul", "Kardashian" , "pgk", "1234", new Point2D.Double(4,1));
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
		
		
		i1 = new Starter("Soup", 8.5, Type.Standard, true);
		i2 = new Dessert("Cake", 5, Type.Vegetarian, false);
		halfmeal1 = new HalfMeal("meal_2", Type.Standard);
		halfmeal1.setStarter((Starter)i1);
		halfmeal1.setMainDish((MainDish)i4);
		halfmeal2 = new HalfMeal("meal_3", Type.Standard);
		halfmeal2.setDessert((Dessert)i2);
		halfmeal2.setMainDish((MainDish)i4);
		halfmeal3 = new HalfMeal("meal_4", Type.Standard);
		halfmeal3.setDessert((Dessert)i5);
		halfmeal3.setMainDish((MainDish)i4);	
		restaurant.addMeal(halfmeal1);
		restaurant.addMeal(halfmeal2);
		restaurant.addMeal(halfmeal3);

		/**
		 * orders
		 */
		c.order("order1", restaurant, items, meals);
		myfoodora.processOrder(c.historyOrders().get(0));
		c2.order("order2", restaurant, items, meals);
		myfoodora.processOrder(c2.historyOrders().get(0));
		
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
	}
	
	
	@Test
	public void testGetRestaurants() {
		assertTrue(myfoodora.getRestaurants().get(0).getUsername().equals("bonheur"));
	}

	@Test
	public void testSetRestaurants() {
		ArrayList<Restaurant> restaurants = new ArrayList<Restaurant>();
		restaurants.add(restaurant);
		restaurants.add(restaurant2);
		myfoodora.setRestaurants(restaurants);
		assertTrue(myfoodora.getRestaurants().size() == 2);
	}

	@Test
	public void testGetCustomers() {
		assertTrue(myfoodora.getCustomers().get(0).getName().equals("Guy"));
	}

	@Test
	public void testSetCustomers() {
		ArrayList<Customer> customers = new ArrayList<Customer>();
		customers.add(c);
		customers.add(c2);
		myfoodora.setCustomers(customers);
		assertTrue(myfoodora.getCustomers().size() == 2);
	}

	@Test
	public void testGetCouriers() {
		assertTrue(myfoodora.getCouriers().get(0).getName().equals("Megan"));
	}

	@Test
	public void testSetCouriers() {
		ArrayList<Courier> couriers = new ArrayList<Courier>();
		couriers.add(courier1);
		couriers.add(courier2);
		myfoodora.setCouriers(couriers);
		assertTrue(myfoodora.getCouriers().size() == 2);
	}

	@Test
	public void testGetServiceFee() {
		assertTrue(myfoodora.getServiceFee() == 1);
	}

	@Test
	public void testSetServiceFee() {
		myfoodora.setServiceFee(2);
		assertTrue(myfoodora.getServiceFee() == 2);
	}

	@Test
	public void testGetMarkupPercentage() {
		assertTrue(myfoodora.getMarkupPercentage() == 0.05);
	}

	@Test
	public void testSetMarkupPercentage() {
		myfoodora.setMarkupPercentage(2);
		assertTrue(myfoodora.getMarkupPercentage() == 2);
	}

	@Test
	public void testGetDeliveryCost() {
		assertTrue(myfoodora.getDeliveryCost() == 1);
	}

	@Test
	public void testSetDeliveryCost() {
		myfoodora.setDeliveryCost(2);
		assertTrue(myfoodora.getDeliveryCost() == 2);
	}

	@Test
	public void testGetDeliveryPolicy() {
		assertTrue(myfoodora.getDeliveryPolicy() instanceof FastestDelivery);
	}

	@Test
	public void testSetDeliveryPolicy() {
		myfoodora.setDeliveryPolicy(new FairOccupationDelivery());
		assertTrue(myfoodora.getDeliveryPolicy() instanceof FairOccupationDelivery);
	}

	@Test
	public void testRemoveRestaurant() {
		assertTrue(myfoodora.getRestaurants().size() == 1);
		myfoodora.removeRestaurant(restaurant);
		assertTrue(myfoodora.getRestaurants().size() == 0);
	}

	@Test
	public void testRemoveCustomer() {
		assertTrue(myfoodora.getCustomers().size() == 1);
		myfoodora.removeCustomer(c);
		assertTrue(myfoodora.getCustomers().size() == 0);
	}

	@Test
	public void testRemoveCourier() {
		assertTrue(myfoodora.getCouriers().size() == 1);
		myfoodora.removeCourier(courier1);
		assertTrue(myfoodora.getCouriers().size() == 0);
	}

	@Test
	public void testAddRestaurant() {
		assertTrue(myfoodora.getRestaurants().size() == 1);
		myfoodora.addRestaurant(restaurant2);
		assertTrue(myfoodora.getRestaurants().size() == 2);
	}

	@Test
	public void testAddCustomer() {
		assertTrue(myfoodora.getCustomers().size() == 1);
		myfoodora.addCustomer(c2);
		assertTrue(myfoodora.getCustomers().size() == 2);
	}

	@Test
	public void testAddCourier() {
		assertTrue(myfoodora.getCouriers().size() == 1);
		myfoodora.addCourier(courier2);
		assertTrue(myfoodora.getCouriers().size() == 2);
	}

	@Test
	public void testGetOrders() {
		assertTrue(myfoodora.getOrders().size() == 2);
	}

	@Test
	public void testSetOrders() {
		ArrayList<Order> ord = new ArrayList<Order>();
		ord.add(c2.historyOrders().get(0));
		myfoodora.setOrders(ord);
		assertTrue(myfoodora.getOrders().size() == 1);
	}

	/**
	 * compare it to the value calculated with the calculator
	 */
	@SuppressWarnings("deprecation")
	@Test
	public void testComputeTotalIncome() {
		assertTrue(myfoodora.ComputeTotalIncome(new Date(106, 11, 10), new Date(117, 11, 9)) == 67.7);
	}

	@SuppressWarnings("deprecation")
	@Test
	public void testComputeTotalProfit() {
		assertTrue(myfoodora.ComputeTotalProfit(new Date(106, 11, 10), new Date(117, 11, 9)) == 3.285);
	}

	@SuppressWarnings("deprecation")
	@Test
	public void testComputeAverageIncome() {
		
		assertEquals(myfoodora.ComputeAverageIncome(new Date(106, 11, 10), new Date(118, 11, 9)), 67.7/2, 0.1);
	}

	@Test
	public void testTargetProfit_DeliveryCost() {
		myfoodora.targetProfit_DeliveryCost(2);
		//We round to the third decimal
		assertTrue(Math.round(myfoodora.getDeliveryCost()*1000)/1000.0 == 0.643);
	}

	@Test
	public void testTargetProfit_ServiceFee() {
		myfoodora.targetProfit_ServiceFee(2);
		// We round to the third decimal
		assertTrue(Math.round(myfoodora.getServiceFee()*1000)/1000.0 == 1.358);
	}

	@Test
	public void testTargetProfit_Markup() {
		myfoodora.targetProfit_Markup(2);
		//We round to the third decimal
		assertTrue(Math.round(myfoodora.getMarkupPercentage()*1000)/1000.0 == 0.061);
	}

	@Test
	public void testMostSellingRestaurant() {
		myfoodora.addRestaurant(restaurant2);
		assertTrue(myfoodora.mostSellingRestaurant().getName().equals("Bonheur"));
	}

	@Test
	public void testLeastSellingRestaurant() {
		myfoodora.addRestaurant(restaurant2);
		assertTrue(myfoodora.leastSellingRestaurant().getName().equals("Burguer Lobby"));
	}

	@Test
	public void testMostActiveCourier() {
		myfoodora.addCourier(courier2);
		assertTrue(myfoodora.mostActiveCourier().getName().equals("Megan"));
	}

	@Test
	public void testLeastActiveCourier() {
		myfoodora.addCourier(courier2);
		assertTrue(myfoodora.leastActiveCourier().getName().equals("Paul"));
	}

	@Test
	public void testProcessOrder() {
		Order o = new Order("test_order", c2, restaurant2, items, meals);
		myfoodora.processOrder(o);
		assertTrue("Price not calculated", o.getPrice() == 32.85);
		assertTrue("Order not stored correctly in the system", myfoodora.getOrders().size() == 3);
		assertNotNull("Courier not allocated", o.getCourier());
		assertTrue("Courier not allocated correctly", o.getCourier().getName().equals("Megan"));
		assertTrue("Number of orders of the restaurant not updated", restaurant2.getNumber_orders() == 1);
		assertTrue("Number of orders delivered by the courier not updated", courier1.getDeliveredOrders() == 3);
	}


	@Test
	public void testSortOrderedHalfMeals() {
		ArrayList<Meal> meals = new ArrayList<Meal>();
		meals.add(halfmeal1);
		meals.add(halfmeal2);
		
		c.order("order3", restaurant, items, meals);
		myfoodora.processOrder(c.historyOrders().get(1));
		
		assertTrue(myfoodora.sortOrderedHalfMeals().size() == 3);
		
		assertEquals(myfoodora.sortOrderedHalfMeals().get(0).getDessert().getName(), "Chocolate cake");
		assertEquals(myfoodora.sortOrderedHalfMeals().get(1).getStarter().getName(), "Soup");
		assertEquals(myfoodora.sortOrderedHalfMeals().get(2).getDessert().getName(), "Cake");
	}


	@Test
	public void testSortOrderedItems() {
		assertTrue(myfoodora.sortOrderedItems().size() == 5);
		assertEquals(myfoodora.sortOrderedItems().get(0).getName(), "Chips");
		assertEquals(myfoodora.sortOrderedItems().get(1).getName(), "Burguer");
		assertEquals(myfoodora.sortOrderedItems().get(2).getName(), "Chocolate cake");
		assertEquals(myfoodora.sortOrderedItems().get(3).getName(), "Soup");
		assertEquals(myfoodora.sortOrderedItems().get(4).getName(), "Cake");

	}

	@Test
	public void testRegisterObserver() {
		myfoodora.registerObserver(c);
		assertTrue(myfoodora.getObservers().size() == 1);
	}

	/**
	 * this test is only valid if the previous one also is
	 */
	@Test
	public void testRemoveObserver() {
		myfoodora.registerObserver(c);
		myfoodora.removeObserver(c);
		assertTrue(myfoodora.getObservers().size() == 0);
	}

	/**
	 * if there is a notification printed i the screen, the method
	 * notifyObservers is working.
	 */
	@Test
	public void testNotifyObservers() {
		c.setConsensus(true);
		myfoodora.registerObserver(c);
		myfoodora.notifyObservers(restaurant, Offer.specialDiscount);
	}
	
}
