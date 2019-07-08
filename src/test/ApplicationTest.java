package test;

import java.awt.geom.Point2D;
import java.util.ArrayList;

import org.junit.Test;

import clui.Application;
import clui.Finder;
import deliveryPolicies.FairOccupationDelivery;
import deliveryPolicies.FastestDelivery;
import fidelityCards.*;
import items.Dessert;
import items.Item;
import items.MainDish;
import items.Starter;
import junit.framework.TestCase;
import items.Item.Type;
import meals.FullMeal;
import meals.HalfMeal;
import meals.InvalidItemException;
import meals.Meal;
import system.MyFoodora;
import users.Courier;
import users.Courier.State;
import users.Customer;
import users.ExistingUserException;
import users.Manager;
import users.Restaurant;

public class ApplicationTest extends TestCase {

	private Application application;
	private Finder finder;
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
		 * The application
		 */
		application = new Application();
		application.setMyFoodora(myfoodora);
		application.setFinder(finder);
		
		/**
		 * add customers to the system
		 */
		c = new Customer(myfoodora, "Guy", "guytayou", "Tayou", "jordan23", new Point2D.Double(5,1), "guy.tayou@student.ecp.fr", "0682647985");
		myfoodora.addCustomer((Customer)c);
		c2 = new Customer(myfoodora, "Alexandre", "aflorez", "Florez", "bouffe", new Point2D.Double(-2,6), "alexandre.florez@student.ecp.fr", "06512347985");
		myfoodora.addCustomer((Customer)c2);
		
		/**
		 * The manager that oversees the system
		 */
		m = new Manager(myfoodora, "Arturo", "arturo_95", "Garrido", "realmadrid");
		myfoodora.addManager(m);
		
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
		m = null;
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
		application = null;
	}
	
	@Test
	public void testLogin() {
		application.login("manager", "arturo_95", "realmadrid");
		assertTrue("Login not processed correctly", application.getUser() instanceof Manager);
		application.login("courier", "meg", "kitty");
		assertTrue("Login not processed correctly", application.getUser() instanceof Courier);
		application.login("customer", "guytayou", "jordan23");
		assertTrue("Login not processed correctly", application.getUser() instanceof Customer);
		application.login("restaurant", "bonheur", "1986");
		assertTrue("Login not processed correctly", application.getUser() instanceof Restaurant);
	}

	@Test
	public void testLogout() {
		application.logout();
		assertNull("Logout not processed correclty", application.getUser());
	}

	@Test
	public void testRegisterRestaurant() {
		application.login("manager", "arturo_95", "realmadrid");
		application.registerRestaurant("Hard rock", "HR_paris", "2,-3", "hardrock");
		assertNotNull("Restaurant registration not processed correctly",finder.findRestaurant("Hard rock"));
	}

	@Test
	public void testRegisterCustomer() {
		application.login("manager", "arturo_95", "realmadrid");
		application.registerCustomer("Magic", "Johnson", "MJ32", "0,5", "showtime");
		assertNotNull("Customer registration not processed correctly", finder.findCustomer("MJ32"));
	}

	@Test
	public void testRegisterCourier() {
		application.login("manager", "arturo_95", "realmadrid");
		application.registerCourier("Carl", "Gallagher", "carl2000", "1,1", "army");
		assertNotNull("Courier registration not processed correctly", finder.findCourier("carl2000"));
	}

	@Test
	public void testAddDishRestaurantMenu() {
		application.login("restaurant", "bonheur", "1986");
		application.addDishRestaurantMenu("Salmon", "Main", "Standard", "12");
		assertNotNull("Dish not correctly added", finder.findItem("Salmon"));
	}

	@Test
	public void testCreateMeal() {
		application.login("restaurant", "bonheur", "1986");
		application.createMeal("meat lovers", "Standard", "Full");
		assertNotNull("Meal not correctly added", application.getCreatedMeals().get(0));
	}

	@Test
	public void testAddDish2Meal() {
		application.login("restaurant", "bonheur", "1986");
		application.createMeal("meat lovers", "Standard", "Full");
		application.addDish2Meal("Burguer", "meat lovers");
		assertTrue("Dish not correctly added to meal", application.getCreatedMeals().get(0).getMainDish().getName().equals("Burguer"));
	}

	//No need of assert functions
	@Test
	public void testShowMeal() {
		application.login("restaurant", "bonheur", "1986");
		application.showMeal("meal_1");
	}

	@Test
	public void testSaveMeal() {
		application.login("restaurant", "bonheur", "1986");
		application.createMeal("meat lovers", "Standard", "Full");
		application.addDish2Meal("Burguer", "meat lovers");
		application.saveMeal("meat lovers");
		assertNotNull("Meal not correctly saved", finder.findMeal("meat lovers"));
	}

	@Test
	public void testSetSpecialOffer() {
		application.login("restaurant", "bonheur", "1986");
		application.setSpecialOffer("meal_2");
		assertTrue("Meal-of-the-week not correctly added", finder.findRestaurant("Bonheur").getMealsOfTheWeek().size() == 2);
		assertTrue("Meal-of-the-week not correctly added", finder.findRestaurant("Bonheur").getMealsOfTheWeek().get(1).getName().equals("meal_2"));
	}

	@Test
	public void testRemoveFromSpecialOffer() {
		application.login("restaurant", "bonheur", "1986");
		application.setSpecialOffer("meal_2");
		application.removeFromSpecialOffer("meal_2");
		assertTrue("Meal-of-the-week not correctly removed", finder.findRestaurant("Bonheur").getMealsOfTheWeek().size() == 1);
		assertTrue("Meal-of-the-week not correctly removed", finder.findRestaurant("Bonheur").getMealsOfTheWeek().get(0).getName().equals("meal_1"));
	}

	@Test
	public void testCreateOrder() {
		application.login("customer", "aflorez", "bouffe");
		application.createOrder("Bonheur", "morning order");
		assertNotNull("Order not correctly created", finder.findOrder("morning order"));
	}

	@Test
	public void testAddItem2Order() {
		application.login("customer", "aflorez", "bouffe");
		application.createOrder("Bonheur", "morning order");
		application.addItem2Order("morning order", "Soup");
		assertTrue("Item not correctly added to order", finder.findOrder("morning order").getItems().get(0).getName().equals("Soup"));
		application.addItem2Order("morning order", "meal_1");
		assertTrue("Meal not correctly added to order", finder.findOrder("morning order").getMeals().get(0).getName().equals("meal_1"));
	}

	@Test
	public void testEndOrder() {
		application.login("customer", "aflorez", "bouffe");
		application.createOrder("Bonheur", "morning order");
		application.addItem2Order("morning order", "Soup");
		application.endOrder("morning order", "25/11/2016");
		assertTrue("Order not ended correctly", myfoodora.getOrders().get(2).getName().equals("morning order"));
	}

	@Test
	public void testOnDuty() {
		application.login("courier", "meg", "kitty");
		application.offDuty("meg");
		application.onDuty("meg");
		assertTrue("State not correctly set", courier1.getState() == State.OnDuty);
	}

	@Test
	public void testOffDuty() {
		application.login("courier", "meg", "kitty");
		application.offDuty("meg");
		assertTrue("State not correctly set", courier1.getState() == State.OffDuty);
	}

	@Test
	public void testFindDeliverer() {
		application.findDeliverer("order1");
		//The output should be: The courier Paul Kardashian has been successfully allocated.
	}

	@Test
	public void testSetDeliveryPolicy() {
		application.login("manager", "arturo_95", "realmadrid");
		application.setDeliveryPolicy("FairOccupationDelivery");
		assertTrue("Fair Occupation Delivery policy not correctly set", myfoodora.getDeliveryPolicy() instanceof FairOccupationDelivery);
		application.setDeliveryPolicy("FastestDelivery");
		assertTrue("Fastest Delivery policy not correctly set", myfoodora.getDeliveryPolicy() instanceof FastestDelivery);
	}

	/*
	@Test
	public void testSetProfitPolicy() {
		fail("Not yet implemented");
	}
	*/

	@Test
	public void testAssociateCard() {
		application.login("manager", "arturo_95", "realmadrid");
		application.associateCard("aflorez", "BasicFidelityCard");
		assertTrue("Basic Fidelity Card not correctly associated", c2.getFidelityCard() instanceof BasicFidelityCard);
		application.associateCard("aflorez", "LotteryFidelityCard");
		assertTrue("Lottery Fidelity Card not correctly associated", c2.getFidelityCard() instanceof LotteryFidelityCard);
		application.associateCard("aflorez", "PointFidelityCard");
		assertTrue("Point Fidelity Card not correctly associated", c2.getFidelityCard() instanceof PointFidelityCard);
	}

	@Test
	public void testShowCourierDeliveries() {
		application.login("manager", "arturo_95", "realmadrid");
		application.showCourierDeliveries();
		/**
		 * The output should be:
		 * 
		 * Couriers' deliveries:
		 * - Paul has done 2 deliveries
		 * - Megan has done 0 deliveries
		 * 
		 */
	}

	@Test
	public void testShowRestaurantTop() {
		application.login("manager", "arturo_95", "realmadrid");
		application.showRestaurantTop();
		/**
		 * The output should be:
		 * 
		 * Restaurants' orders: 
		 * - Bonheur has processed 2 orders
		 * - Burguer Lobby has processed 0 orders
		 * 
		 */
	}

	@Test
	public void testShowCustomers() {
		application.login("manager", "arturo_95", "realmadrid");
		application.showCustomers();
		/**
		 * The output should be:
		 * Customers:
		 * - Guy
		 * - Alexandre
		*/
	}

	@Test
	public void testShowMenuItem() {
		application.login("manager", "arturo_95", "realmadrid");
		application.showMenuItem("Bonheur");
		/**
		 * The output should be:
		 * Menu of Bonheur:
		 * - Soup
		 * - Cake
		 * - Chips
		 * - Burguer
		 * - Chocolate cake
		*/
	}

	@Test
	public void testShowTotalProfitStringString() {
		//We introduce an order from a year ago
		application.login("customer", "aflorez", "bouffe");
		application.createOrder("Bonheur", "morning order");
		application.addItem2Order("morning order", "Soup");
		application.endOrder("morning order", "28/11/2015");
		application.logout();
		
		application.login("manager", "arturo_95", "realmadrid");
		application.showTotalProfit("20/11/2015", "28/11/2016");
	}

	@Test
	public void testShowTotalProfit() {
		//We introduce an order from a year ago
		application.login("customer", "aflorez", "bouffe");
		application.createOrder("Bonheur", "morning order");
		application.addItem2Order("morning order", "Soup");
		application.endOrder("morning order", "28/12/2015");
		application.logout();
		
		application.login("manager", "arturo_95", "realmadrid");
		application.showTotalProfit();
		/**
		 * The output should be:
		 * 
		 * Total profit: 3.22
		*/
	}


	@Test
	public void testHelp() {
		application.help();
	}

	@Test
	public void testExecuteStringStringArray() {
		application.execute("login", new String[] {"manager", "arturo_95", "realmadrid"});
		assertTrue("Command not executed correclty", application.getUser().getName().equals("Arturo"));
	}

	@Test
	public void testExecuteString() {
		application.execute("login \"manager\" \"arturo_95\" \"realmadrid\"");
		assertNotNull("Command not executed correclty", application.getUser());
	}

}
