package clui;

import java.awt.geom.Point2D;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;

import deliveryPolicies.FairOccupationDelivery;
import deliveryPolicies.FastestDelivery;
import fidelityCards.BasicFidelityCard;
import fidelityCards.LotteryFidelityCard;
import fidelityCards.PointFidelityCard;
import items.*;
import items.Item.Type;
import meals.Meal;
import meals.MealFactory;
import meals.MealFactory.Category;
import orders.Order;
import system.MyFoodora;
import users.Courier;
import users.Customer;
import users.ExistingUserException;
import users.Manager;
import users.Restaurant;
import users.User;

public class Application {
	
	/**
	 * The maximum number of arguments a command can have
	 */
	private final int max_args = 6;
	
	/**
	 * The logged user 
	 */
	private User user;
	
	/**
	 * MyFoodora
	 */
	private MyFoodora myFoodora;
	
	/**
	 * A Finder object to do all the searching actions
	 */
	private Finder finder;
	
	/**
	 * the meals that the restaurant creates, before adding them to
	 * its menu
	 */
	private ArrayList<Meal> createdMeals = new ArrayList<Meal>();
	
	/**
	 * Default constructor
	 */
	public Application() {
		user = null;
		myFoodora = new MyFoodora(4, 0.1, 1, new FastestDelivery());
		finder = new Finder(null, myFoodora);
		try {
			Manager ceo = new Manager(myFoodora, "King", "ceo", "James", "123456789");
			myFoodora.addManager(ceo);
		} catch (ExistingUserException e) {
			
		}
	}

	/**
	 * Converts a string with the syntax "12,9" into a Point2D.Double
	 * 
	 * @param s the string to convert
	 * @return the converted point 
	 */
	private Point2D.Double stringToPoint(String s) {
		String[] tab = s.split(",");
		
		if(tab.length != 2) {
			return null;
		}

		Point2D.Double point = new Point2D.Double(Double.parseDouble(tab[0]), Double.parseDouble(tab[1]));
		
		return point;
	}
	
	/**
	 * Converts a string with the syntax into a Date
	 * 
	 * @param s the string to convert
	 * @return the converted date 
	 */
	private Date stringToDate(String s) {
		DateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
		Date date = null;
		try {
			date = format.parse(s);

		} catch (ParseException e) {
			System.out.print("Invalid date format.");
		}
		return date;
	}
	
	
	/**
	 * performs the login
	 * 
	 * @param type the type of the user
	 * @param username the username of the user
	 * @param password the password of the user
	 */
	public void login(String type, String username, String password) {
			
		if(type == null) {
			System.out.println("Bad user type.");
			return;
		}

		if(type.equals("manager")) {
			ArrayList<Manager> managers = myFoodora.getManagers();
			for(User u: managers) {
				if(u.getUsername().equals(username) && u.getPassword().equals(password)) {
					user = u;
					finder.setUser(user);
				}
			}
		} else if(type.equals("courier")) {
			ArrayList<Courier> couriers = myFoodora.getCouriers();
			for(User u: couriers) {
				if(u.getUsername().equals(username) && u.getPassword().equals(password)) {
					user = u;
					finder.setUser(user);
				}
			}
		} else if(type.equals("customer")) {
			ArrayList<Customer> customers = myFoodora.getCustomers();
			for(User u: customers) {
				if(u.getUsername().equals(username) && u.getPassword().equals(password)) {
					user = u;
					finder.setUser(user);
				}
			}
		} else if(type.equals("restaurant")) {
			ArrayList<Restaurant> restaurants = myFoodora.getRestaurants();
			for(User u: restaurants) {
				if(u.getUsername().equals(username) && u.getPassword().equals(password)) {
					user = u;
					finder.setUser(user);
				}
			}
		} else {
			System.out.println("Invalid user type. Supported types are: courier, customer, manager and restaurant.");
			return;
		}
	
		if(user == null) {
			System.out.println("Bad credentials.");
		}
	}

	/**
	 * logout the user 
	 */
	public void logout() {
		this.user = null;
		System.out.println("Logout performed.");
	}
	
	/**
	 * For the currently logged on manager to add a restaurant of given name, address (i.e. address should be a bi-dimensional co-ordinate), username and password to the system.
	 * 
	 * @param name
	 * @param username
	 * @param address
	 * @param password
	 */
	public void registerRestaurant(String name, String address, String username, String password) {	
		
		// Check if the logged user has the right to execute this action
		if(!(user instanceof Manager)) {
			System.out.print("Only a logged Manager can execute this command.");
			return;
		}
		
		try {
			Restaurant restaurant = new Restaurant(myFoodora, name, username, password, stringToPoint(address));
			
			((Manager)user).add_user("restaurant", restaurant);
			System.out.println("Restaurant successfully added");
			
		} catch (ExistingUserException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * For the currently logged on manager to add a customer to the system
	 * 
	 * @param name
	 * @param surname
	 * @param username
	 * @param address
	 * @param password
	 */
	public void registerCustomer(String name, String surname, String username, String address, String password) {
		
		if(!(user instanceof Manager)) {
			System.out.print("Only a logged Manager can execute this command.");
			return;
		}
		
		try {
			Customer customer = new Customer(myFoodora, name, username, surname, password, stringToPoint(address), null, null);
			((Manager)user).add_user("customer", customer);
			System.out.println("Customer successfully added");
		} catch (ExistingUserException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * for the currently logged on manager to add a courier to the system (by default each newly registered courier is on-duty).
	 * 
	 * @param name
	 * @param surname
	 * @param username
	 * @param position
	 * @param password
	 */
	public void registerCourier(String name, String surname, String username, String position, String password) {

		if(!(user instanceof Manager)) {
			System.out.print("Only a logged Manager can execute this command.");
			return;
		}
		
		try {
			Courier courier = new Courier(name, surname, username, password, stringToPoint(position));
			((Manager)user).add_user("courier", courier);
			System.out.println("Courier successfully added");

		} catch (ExistingUserException e) {
			e.printStackTrace();
		}
	}
	
	public void setCustomerInfo(String phone, String mail) {
		
		if(!(user instanceof Customer)) {
			System.out.print("Only a logged Customer can execute this command.");
			return;
		}
		
		((Customer)user).setPhone(phone);
		((Customer)user).setEmail_adress(mail);
	}

	/**
	 * for the currently logged on restaurant to add a dish with given name, given category (starter,main,dessert), food type (standard,vegetarian, gluten-free) and price to the menu of a restaurant with given name (this command can be executed by a restaurant-user only).
	 * 
	 * @param dishName
	 * @param dishCategory
	 * @param foodCategory
	 * @param unitPrice
	 */
	public void addDishRestaurantMenu(String dishName, String dishCategory, String foodCategory, String unitPrice) {
		
		if(!(user instanceof Restaurant)) {
			System.out.print("Only a logged Restaurant can execute this command.");
			return;
		}
		
		Item dish;
		switch(dishCategory) 
		{
		case "Starter":
			if(foodCategory.equals("Standard") || foodCategory.equals("Vegetarian") || foodCategory.equals("GlutenFree")) {
				if(foodCategory.equals("GlutenFree"))
					dish = new Starter(dishName, Double.parseDouble(unitPrice), Type.valueOf(foodCategory), true);
				else
					dish = new Starter(dishName, Double.parseDouble(unitPrice), Type.valueOf(foodCategory), false);
				((Restaurant)user).addItem(dish);
			} else {
				System.out.println("Invalid food category. Possible categories: Standard, Vegetarian, GlutenFree.");
			}
			break;
		
		case "Main":
			if(foodCategory.equals("Standard") || foodCategory.equals("Vegetarian") || foodCategory.equals("GlutenFree")) {
				if(foodCategory.equals("GlutenFree"))
					dish = new MainDish(dishName, Double.parseDouble(unitPrice), Type.valueOf(foodCategory), true);
				else
					dish = new MainDish(dishName, Double.parseDouble(unitPrice), Type.valueOf(foodCategory), false);
				((Restaurant)user).addItem(dish);
			} else {
				System.out.println("Invalid food category. Possible categories: Standard, Vegetarian, GlutenFree.");
			}
			break;
			
		case "Dessert":
			if(foodCategory.equals("Standard") || foodCategory.equals("Vegetarian") || foodCategory.equals("GlutenFree")) {
				if(foodCategory.equals("GlutenFree"))
					dish = new Dessert(dishName, Double.parseDouble(unitPrice), Type.valueOf(foodCategory), true);
				else
					dish = new Dessert(dishName, Double.parseDouble(unitPrice), Type.valueOf(foodCategory), false);
				((Restaurant)user).addItem(dish);
			} else {
				System.out.println("Invalid food category. Possible categories: Standard, Vegetarian, GlutenFree.");
			}
			break;
		
		default:
			System.out.println("Invalid dish category. Possible categories: Starter, Main, Dessert. You wrote: " + dishCategory);
		}
	}
	
	/**
	 * For the currently logged on restaurant to create a meal.
	 * 
	 * @param mealName
	 * @param foodType
	 * @param mealType
	 */
	public void createMeal(String mealName, String foodType, String mealType) {
		
		if(!(user instanceof Restaurant)) {
			System.out.print("Only a logged Restaurant can execute this command.");
			return;
		}
		
		
		MealFactory factory = new MealFactory();
		switch(foodType) 
		{
		case "Standard":
			if(mealType.equals("Half"))
				createdMeals.add(factory.createMeal(mealName, Category.Half, Type.Standard));
			else if(mealType.equals("Full"))
				createdMeals.add(factory.createMeal(mealName, Category.Full, Type.Standard));
			else
				System.out.println("Invalid meal type. Possible types: Half, Full");
			break;
			
		case "Vegetarian":
			if(mealType.equals("Half"))
				createdMeals.add(factory.createMeal(mealName, Category.Half, Type.Vegetarian));
			else if(mealType.equals("Full"))
				createdMeals.add(factory.createMeal(mealName, Category.Full, Type.Vegetarian));
			else
				System.out.println("Invalid meal type. Possible types: Half, Full");
			break;
			
		case "GlutenFree":
			if(mealType.equals("Half"))
				createdMeals.add(factory.createMeal(mealName, Category.Half, Type.GlutenFree));
			else if(mealType.equals("Full"))
				createdMeals.add(factory.createMeal(mealName, Category.Full, Type.GlutenFree));
			else
				System.out.println("Invalid meal type. Possible types: Half, Full");
			break;
			
		default:
			System.out.println("Invalid food type. Possible types: Standard, Vegetarian, GlutenFree");	
		}
	}

	/**
	 * For the currently logged on restaurant to add a dish to a meal.
	 * 
	 * @param dishName
	 * @param dishCategory
	 * @param mealName
	 */
	public void addDish2Meal(String dishName, String mealName) {
		
		if(!(user instanceof Restaurant)) {
			System.out.print("Only a logged Restaurant can execute this command.");
			return;
		}
		
		Item item = finder.findItem(dishName);
		Meal meal = null;
		
		for(Meal m : createdMeals) {
			if(m.getName().equals(mealName)) {
				meal = m;
			}
		}
		
		if(item == null) {
			System.out.println("The dish " + dishName + " does not exist for the logged restaurant.");
		} else if (meal == null) {
			System.out.println("The meal " + mealName + " does not exist for the logged restaurant.");			
		} else {
			if(item instanceof Starter) {
				meal.setStarter((Starter)item);
			} else if(item instanceof MainDish) {
				meal.setMainDish((MainDish)item);
			} else {
				meal.setDessert((Dessert)item);
			}
			System.out.println("The dish " + dishName + " has been added successfully the the meal " + mealName + ".");			
		}
	}

	/**
	 * For the currently logged on restaurant to show the dishes in a meal with given name
	 * 
	 * @param mealName
	 */
	public void showMeal(String mealName) {

		if(!(user instanceof Restaurant)) {
			System.out.print("Only a logged Restaurant can execute this command.");
			return;
		}
		
		
		Meal meal = finder.findMeal(mealName);
		String outputValue = "";
		if(meal == null) {
			outputValue = "The meal " + mealName + " does not exist for the logged restaurant.";
		} else {
			outputValue += meal.toString() + "\n" + "Content:" + "\n";
			if(meal.getStarter() != null) {
				outputValue += meal.getStarter().toString() + "\n";
			}
			if(meal.getMainDish()!= null) {
				outputValue += meal.getDessert().toString() + "\n";
			}
			if(meal.getDessert() != null) {
				outputValue += meal.getDessert().toString() + "\n";
			}		
		}
		System.out.println(outputValue);
	}
	
	/**
	 * For the currently logged on restaurant to save a meal with given name.
	 * 
	 * @param mealName
	 */
	public void saveMeal(String mealName) {
		
		if(!(user instanceof Restaurant)) {
			System.out.print("Only a logged Restaurant can execute this command.");
			return;
		}
		
		for(Meal m : createdMeals) {
			if(m.getName().equals(mealName)) {
				((Restaurant)user).addMeal(m);
			}
		}
	}
	
	/**
	 * For the currently logged on restaurant to add a meal in meal-of-the-week special offer.
	 * 
	 * @param mealName
	 */
	public void setSpecialOffer(String mealName) {
		
		if(!(user instanceof Restaurant)) {
			System.out.print("Only a logged Restaurant can execute this command.");
			return;
		}
		
		Meal meal = finder.findMeal(mealName);
		if(meal != null) {
			((Restaurant)user).addMealOfTheWeek(meal);
			System.out.println("The meal " + mealName + " has been successfully added to the meal-of-the-week special offer.");
		} else {
			System.out.println("The meal " + mealName + " does not exist for the logged restaurant.");
		}
		
	}
	
	/**
	 * For the currently logged on restaurant to remove a special offer.
	 * 
	 * @param mealName
	 */
	public void removeFromSpecialOffer(String mealName) {

		if(!(user instanceof Restaurant)) {
			System.out.print("Only a logged Restaurant can execute this command.");
			return;
		}
				
		Meal meal = finder.findMeal(mealName);
		if(meal != null) {
			((Restaurant)user).removeMealOfTheWeek(meal);
			System.out.println("The meal " + mealName + " has been successfully removed from the meal-of-the-week special offer.");
		} else {
			System.out.println("The meal " + mealName + " does not exist for the logged restaurant.");
		}
	}
	
	/**
	 * For the currently logged on customer to create an order from a given restaurant.
	 * 
	 * @param restaurantName
	 * @param orderName
	 */
	public void createOrder(String restaurantName, String orderName) {
		
		if(!(user instanceof Customer)) {
			System.out.print("Only a logged Customer can execute this command.");
			return;
		}
		
		((Customer)user).order(orderName, finder.findRestaurant(restaurantName), new ArrayList<Item>(), new ArrayList<Meal>());
	}

	/**
	 * For the currently logged on customer to add an item (either a menu item or a meal-deal) to an existing order.
	 * 
	 * @param orderName
	 * @param itemName
	 */
	public void addItem2Order(String orderName, String itemName) {
		
		if(!(user instanceof Customer)) {
			System.out.print("Only a logged Customer can execute this command.");
			return;
		}
		
		Order order = finder.findOrder(orderName);
		Meal meal = finder.findMeal(itemName, order.getRestaurant());
		Item item = finder.findItem(itemName, order.getRestaurant());
		if(meal != null) {
			order.addMeal(meal);
		} else if(item != null) {
			order.addItem(item);
		}
	}

	/**
	 * For the currently logged on customer to finalize an order at a given price and pay it.
	 * 
	 * @param orderName
	 * @param date
	 */
	public void endOrder(String orderName, String date) {
		
		if(!(user instanceof Customer)) {
			System.out.print("Only a logged Customer can execute this command.");
			return;
		}
		
		Order order = finder.findOrder(orderName);
		order.setDate(stringToDate(date));
		myFoodora.processOrder(order);
	}
	
	/**
	 * For the currently logged on customer to give or remove consensus to receive special offers
	 * @param consensus
	 */
	public void setConsensus(String consensus) {
		
		if(!(user instanceof Customer)) {
			System.out.print("Only a logged Customer can execute this command.");
			return;
		}
		
		if(consensus.equals("true")) {
			((Customer)user).setConsensus(true);
		} else if(consensus.equals("false")) {
			((Customer)user).setConsensus(false);
		} else {
			System.out.println("Invalid consensus. Possible consensus: true and false");
		}
	}
	
	/**
	 * For the currently logged on courier to set his state as on-duty.
	 * 
	 * @param username
	 */
	public void onDuty(String username) {
		if(!(user instanceof Courier)) {
			System.out.print("Only a logged Customer can execute this command.");
			return;
		}
		
		Courier courier = finder.findCourier(username);
		if(courier != null) {
			courier.setState(Courier.State.OnDuty);
			System.out.println("The state of the courier " + username + " has been successfully set as on-duty");
		} else {
			System.out.println("The courier " + username + " does not exist.");			
		}
	}
	
	/**
	 * For the currently logged on courier to set his state as off-duty
	 * 
	 * @param username
	 */
	public void offDuty(String username) {
		if(!(user instanceof Courier)) {
			System.out.print("Only a logged Customer can execute this command.");
			return;
		}
		
		Courier courier = finder.findCourier(username);
		if(courier != null) {
			courier.setState(Courier.State.OffDuty);
			System.out.println("The state of the courier " + username + " has been successfully set as off-duty");
		} else {
			System.out.println("The courier " + username + " does not exist.");			
		}
	}	
	
	/**
	 * for the currently logged on restaurant to allocate an order to a deliverer by application of the current delivery policy (remark this is just an extra facility of the CLUI, that will allow us to test whether deliverer allocation works properly. The actual allocation of a deliverer should be automatically triggered by the system on completion of an order by a customer).
	 *
	 * @param orderName
	 */
	public void findDeliverer(String orderName) {
		Order order = finder.findOrder(orderName, this.myFoodora);
		if(order == null) {
			System.out.println("The order " + orderName + " does not exist.");			
		} else {
			Courier courier = myFoodora.allocateCourier(order);
			
			if(courier == null) {
				System.out.println("There is no courier in the system");			
			} else {
				System.out.println("The courier " + courier.getName() + " " + courier.getSurname() + " has been successfully allocated.");			
			}
		}	
	}
	
	/**
	 * for the currently logged on myFoodora manager to set the delivery policy of the system to that passed as argument
	 * 
	 * @param deliveryPolicy
	 */
	public void setDeliveryPolicy(String deliveryPolicy) {
		if(!(user instanceof Manager)) {
			System.out.print("Only a logged Manager can execute this command.");
			return;
		}
		
		if(deliveryPolicy .equals("FairOccupationDelivery")) {
			((Manager)user).setDeliveryPolicy(new FairOccupationDelivery());
		} else if(deliveryPolicy.equals("FastestDelivery")){
			((Manager)user).setDeliveryPolicy(new FastestDelivery());			
		} else {
			System.out.println("Invalid delivery policy. Possible policies: FairOccupationDelivery and FastestDelivery.");
			return;
		}
		System.out.println("DeliveryPolicy successfully updated.");
	}
	
	/**
	 * for the currently logged on myFoodora manager set the profit policy of the system to that passed as argument
	 * 
	 * @param profitPolicy
	 */
	public void setProfitPolicy(String profitPolicy, String target) {
		if(!(user instanceof Manager)) {
			System.out.print("Only a logged Manager can execute this command.");
			return;
		}
		
		if(profitPolicy.equals("TargetProfitDeliveryCost")) {
			((Manager)user).targetProfit_DeliveryCost(Double.parseDouble(target));
		} else if(profitPolicy.equals("TargetProfitServiceFee")){
			((Manager)user).targetProfit_ServiceFee(Double.parseDouble(target));
		} else if(profitPolicy.equals("TargetProfitMarkup")){
			((Manager)user).targetProfit_Markup(Double.parseDouble(target));	
		} else {
			System.out.println("Invalid dprofit policy. Possible policies: TargetProfitDeliveryCost, TargetProfitServiceFee and TargetProfitMarkup.");
			return;
		}
		System.out.println("Profit policy successfully updated.");
		
	}
	
	public void associateCard(String username, String cardType) {
		if(!(user instanceof Manager)) {
			System.out.print("Only a logged Manager can execute this command.");
			return;
		}
		
		Customer customer = finder.findCustomer(username);
		if(customer == null) {
			System.out.println("The customer " + username + " does not exist.");			

		} else {
			if(cardType.equals("BasicFidelityCard")) {
				customer.setFidelityCard(new BasicFidelityCard());
			} else if(cardType.equals("LotteryFidelityCard")) {
				customer.setFidelityCard(new LotteryFidelityCard());
			} else if(cardType.equals("PointFidelityCard")) {
				customer.setFidelityCard(new PointFidelityCard());
			} else {
				System.out.println("Usupported card type. Possible types are BasicFidelityCard, LotteryFidelityCard and PointFidelityCard.");			
			}
		}
	}
	
	/**
	 * for the currently logged on myFoodora manager to display the list of couriers sorted in decreasing order w.r.t. the number of completed deliveries
	 */
	public void showCourierDeliveries() {
		if(!(user instanceof Manager)) {
			System.out.print("Only a logged Manager can execute this command.");
			return;
		}
		
		@SuppressWarnings("unchecked")
		ArrayList<Courier> list = (ArrayList<Courier>)myFoodora.getCouriers().clone();
		
		// Insertion sort
		for(int i = 0; i < list.size(); i++) {
			int j = i;
			while( j > 0 && list.get(j-1).getDeliveredOrders() < list.get(j).getDeliveredOrders()) {
				final Courier temp = list.get(j-1);
				list.set(j-1, list.get(j));
				list.set(j, temp);
			}
		}
		
		System.out.println("\nCouriers:");
		for(Courier c : list) {
			System.out.println("- " + c.getName());
		}
	}
	
	/**
	 * for the currently logged on myFoodora manager to display the list of restaurant sorted in decreasing order w.r.t. the number of delivered orders
	 */
	public void showRestaurantTop() {
		if(!(user instanceof Manager)) {
			System.out.print("Only a logged Manager can execute this command.");
			return;
		}
		
		@SuppressWarnings("unchecked")
		ArrayList<Restaurant> list = (ArrayList<Restaurant>)myFoodora.getRestaurants().clone();
		
		// Insertion sort
		for(int i = 0; i < list.size(); i++) {
			int j = i;
			while( j > 0 && list.get(j-1).getNumber_orders() < list.get(j).getNumber_orders()) {
				final Restaurant temp = list.get(j-1);
				list.set(j-1, list.get(j));
				list.set(j, temp);
			}
		}
		
		System.out.println("\nRestaurants: ");
		for(Restaurant r : list) {
			System.out.println("- " + r.getName());
		}
	}

	/**
	 * For the currently logged on myFoodora manager to display the list of customers.
	 */
	public void showCustomers() {
		if(!(user instanceof Manager)) {
			System.out.print("Only a logged Manager can execute this command.");
			return;
		}
		
		System.out.println("\nCustomers:");
		for(Customer c : myFoodora.getCustomers()) {
			System.out.println("- " + c.getName());
		}		
	}

	/**
     * For the currently logged on myFoodora manager to display the menu of a given restaurant.
	 * 
	 * @param restaurantName
	 */
	public void showMenuItem(String restaurantName) {
		if(!(user instanceof Manager)) {
			System.out.print("Only a logged Manager can execute this command.");
			return;
		}
		
		Restaurant resto = finder.findRestaurant(restaurantName);
		if(resto == null) {
			System.out.println("The restaurant " + restaurantName + " does not exist.");
		} else {
			System.out.println("Menu of " + restaurantName + ":");
			for(Item i : resto.getMenu()) {
				System.out.println("- " + i.getName());
			}	
		}
	}
	
	/**
   	 * for the currently logged on myFoodora manager to show the total profit of the system within a time interval
	 * 
	 * @param startDate
	 * @param endDate
	 */
	public void showTotalProfit(String startDate, String endDate) {
		if(!(user instanceof Manager)) {
			System.out.print("Only a logged Manager can execute this command.");
			return;
		}
		
		Double profit = ((Manager)user).ComputeTotalProfit(stringToDate(startDate), stringToDate(endDate));
		System.out.println("\nTotal profit from " + startDate + " to " + endDate + ": " + profit);	
	}
	
	/**
	 * For the currently logged on myFoodora manager to show the total profit of the system within a time interval.
	 */
	public void showTotalProfit() {
		if(!(user instanceof Manager)) {
			System.out.print("Only a logged Manager can execute this command.");
			return;
		}
		
		System.out.println("\nTotal profit: " + ((Manager)user).ComputeTotalProfit());
	}
	
	/**
	 * For a generic user of the CLUI (no need to login) to execute the list of CLUI commands contained in the testScenario file passed as argument.
	 * 
	 * @param ScenarioFile
	 */
	public void runTest(String ScenarioFile) {
		FileReader file = null;
		BufferedReader reader = null;
		File f = new File(ScenarioFile); 
		if(f.exists()) {
			try {
				file = new FileReader(ScenarioFile);
				reader = new BufferedReader(file);
				String line = "";
				while ((line = reader.readLine()) != null) { // read the file line by line
					execute(line); // execute the command
				}
			} catch (Exception e) {
				throw new RuntimeException(e);
			} finally {
				if (reader != null) {
					try {reader.close();}
					catch (IOException e) {/* Ignore issues*/ }
				}
				if (file != null) {
					try {file.close();}
					catch (IOException e) { /* Ignore issues during closing*/ }
				}
			}	
		} else {
			System.out.println("Invalid file name. Try again.");
		}
	}

	/**
	 * Displays the help
	 */
	public void help() {
		String value = "";
		String fileName = "help/help.txt"; // the help is stored in a file
		FileReader file = null;
		BufferedReader reader = null;
		try {
			file = new FileReader(fileName);
			reader = new BufferedReader(file);
			String line = "";
			while ((line = reader.readLine()) != null) { // read the file line by line
				value += line + "\n";
			}
			System.out.print("\nAvailable commands: \n");
			System.out.print(value);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (reader != null) {
				try {reader.close();}
				catch (IOException e) {/* Ignore issues*/ }
			}
			if (file != null) {
				try {file.close();}
				catch (IOException e) { /* Ignore issues during closing*/ }
			}
		}
	}
	
	
	/**
	 * Executes a command from its name and its arguments
	 * 
	 * @param commandName the name of the command
	 * @param p the arguments
	 */
	public void execute(String commandName, String [] p) {
		switch(commandName) {
			case "login":
				this.login(p[0], p[1], p[2]);
				break;
			case "logout":
				this.logout();
				break;
			case "registerCourier":
				this.registerCourier(p[0], p[1], p[2], p[3], p[4]);
				break;
			case "registerCustomer":
				this.registerCustomer(p[0], p[1], p[2], p[3], p[4]);
				break;
			case "registerRestaurant":
				this.registerRestaurant(p[0], p[1], p[2], p[3]);
				break;
			case "setCustomerInfo":
				this.setCustomerInfo(p[0], p[1]);
				break;
			case "addDishRestaurantMenu":
				this.addDishRestaurantMenu(p[0], p[1], p[2], p[3]);
				break;
			case "createMeal":
				this.createMeal(p[0], p[1], p[2]);
				break;
			case "addDish2Meal":
				this.addDish2Meal(p[0], p[1]);
				break;
			case "showMeal":
				this.showMeal(p[0]);
				break;
			case "saveMeal":
				this.saveMeal(p[0]);
				break;
			case "setSpecialOffer":
				this.setSpecialOffer(p[0]);
				break;
			case "removeFromSpecialOffer":
				this.removeFromSpecialOffer(p[0]);
				break;
			case "createOrder":
				this.createOrder(p[0], p[1]);
				break;
			case "addItem2Order":
				this.addItem2Order(p[0], p[1]);
				break;
			case "endOrder":
				this.endOrder(p[0], p[1]);
				break;
			case "setConsensus":
				this.setConsensus(p[0]);
				break;
			case "onDuty":
				this.onDuty(p[0]);
				break;
			case "offDuty":
				this.offDuty(p[0]);
				break;
			case "findDeliverer":
				this.findDeliverer(p[0]);
				break;
			case "setDeliveryPolicy":
				this.setDeliveryPolicy(p[0]);
				break;
			case "setProfitPolicy":
				this.setProfitPolicy(p[0], p[1]);
				break;
			case "associateCard":
				this.associateCard(p[0], p[1]);
				break;
			case "showCourierDeliveries":
				this.showCourierDeliveries();
				break;
			case "showRestaurantTop":
				this.showRestaurantTop();
				break;
			case "showCustomers":
				this.showCustomers();
				break;
			case "showMenuItem":
				this.showMenuItem(p[0]);
				break;
			case "showTotalProfit":
				if(p[0] == null)
					this.showTotalProfit();
				else
					this.showTotalProfit(p[0], p[1]);
				break;
			case "runTest":
				this.runTest(p[0]);
				break;
			case "help":
				this.help();
				break;
			case "exit":
				System.exit(0);
			default:
				System.out.println("This command is not supported.");
				break;
		}
	}
	
	/**
	 * Executes a command
	 * 
	 * @param line the full command
	 */
	public void execute(String line){
		// we split the string given by the user by whitespace
		String[] tokens = line.split("\"?( |$)(?=(([^\"]*\"){2})*[^\"]*$)\"?");
		
		// The first string is the name of the command
		String command = tokens[0]; 
		
		// The other strings are the parameters
		String[] params = new String[max_args]; 
		
		for(int i = 1; i < tokens.length && i < max_args + 1; i++) {
			
			params[i-1] = tokens[i];
		}
		
		
		// Execution of the command
		this.execute(command, params);
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
	
	
	/**
	 * @return the createdMeals
	 */
	public ArrayList<Meal> getCreatedMeals() {
		return createdMeals;
	}

	/**
	 * @param createdMeals the createdMeals to set
	 */
	public void setCreatedMeals(ArrayList<Meal> createdMeals) {
		this.createdMeals = createdMeals;
	}
	

	/**
	 * @return the finder
	 */
	public Finder getFinder() {
		return finder;
	}

	/**
	 * @param finder the finder to set
	 */
	public void setFinder(Finder finder) {
		this.finder = finder;
	}

	/**
	 * Main method
	 * 
	 * @param args
	 */
	public static void main(final String[] args) {
		
		Application app = new Application();
		
		// Header
		System.out.println("MyFoodora CLUI [version 1.0]"); 
		System.out.println("Enter help for a list of commands"); 
		System.out.println("      save to save the system"); 
		System.out.println("      exit to close the application\n"); 
		
		Scanner sc = new Scanner(System.in);
		
		boolean go = true;
		
		do {
			System.out.print(">");
			
			app.execute(sc.nextLine());
			
		} while(go == true);
		
		sc.close();	
	}
}
