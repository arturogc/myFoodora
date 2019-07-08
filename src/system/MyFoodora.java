package system;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import deliveryPolicies.*;
import items.*;
import meals.*;
import notifications.Observable;
import notifications.Observer;
import notifications.Offer;
import orders.*;
import users.*;

/**
 * 
 * @author Arturo Garrido
 *
 */
public class MyFoodora implements Observable {
	/**
	 * List of restaurants
	 */
	private ArrayList<Restaurant> restaurants = new ArrayList<Restaurant>();
	
	/**
	 * List of customers
	 */
	private ArrayList<Customer> customers = new ArrayList<Customer>();
	
	/**
	 * List of couriers
	 */
	private ArrayList<Courier> couriers = new ArrayList<Courier>();
	
	/**
	 * List of managers
	 */	
	private ArrayList<Manager> managers = new ArrayList<Manager>();

	/**
	 * List of orders
	 */	
	private ArrayList<Order> orders = new ArrayList<Order>();
	
	/**
	 * list of customers subscripted to special offer notifications
	 */
	private ArrayList<Customer> observers = new ArrayList<Customer>();
	
	/**
	 * the service fee of the system
	 */
	private double servicefee;
	
	/**
	 * the markup percentage of the system
	 */
	private double markup_per;
	
	/**
	 * the delivery cost of the system
	 */
	private double delivery_cost;

	/**
	 * The delivery policy of the system
	 */
	private DeliveryPolicy deliveryPolicy;
	
	
	public MyFoodora(double servicefee, double markup_per, double delivery_cost, DeliveryPolicy deliveryPolicy) {
		this.servicefee = servicefee;
		this.markup_per = markup_per;
		this.delivery_cost = delivery_cost;
		this.deliveryPolicy = deliveryPolicy;
	}

	/**
	 * @return the restaurants
	 */
	public ArrayList<Restaurant> getRestaurants() {
		return restaurants;
	}

	/**
	 * @param restaurants the restaurants to set
	 */
	public void setRestaurants(ArrayList<Restaurant> restaurants) {
		this.restaurants = restaurants;
	}

	/**
	 * @return the customers
	 */
	public ArrayList<Customer> getCustomers() {
		return customers;
	}
	
	/**
	 * @return the managers
	 */
	public ArrayList<Manager> getManagers() {
		return managers;
	}

	/**
	 * @param customers the customers to set
	 */
	public void setCustomers(ArrayList<Customer> customers) {
		this.customers = customers;
	}

	/**
	 * @param managers the managers to set
	 */
	public void setManagers(ArrayList<Manager> managers) {
		this.managers = managers;
	}
	
	/**
	 * @return the couriers
	 */
	public ArrayList<Courier> getCouriers() {
		return couriers;
	}

	/**
	 * @param couriers the couriers to set
	 */
	public void setCouriers(ArrayList<Courier> couriers) {
		this.couriers = couriers;
	}
	
	/**
	 * @return the servicefee
	 */
	public double getServiceFee() {
		return servicefee;
	}

	/**
	 * @param servicefee the service fee to set
	 */
	public void setServiceFee(double servicefee) {
		this.servicefee = servicefee;
	}

	/**
	 * @return the markup percentage
	 */
	public double getMarkupPercentage() {
		return markup_per;
	}

	/**
	 * @param markup_per the markup percentage to set
	 */
	public void setMarkupPercentage(double markup_per) {
		this.markup_per = markup_per;
	}

	/**
	 * @return the delivery cost
	 */
	public double getDeliveryCost() {
		return delivery_cost;
	}

	/**
	 * @param delivery_cost the delivery cost to set
	 */
	public void setDeliveryCost(double delivery_cost) {
		this.delivery_cost = delivery_cost;
	}

	/**
	 * get the delivery policy
	 * @return
	 */
	public DeliveryPolicy getDeliveryPolicy() {
		return deliveryPolicy;
	}

	/**
	 * @param deliveryPolicy the delivery policy to set
	 */
	public void setDeliveryPolicy(DeliveryPolicy deliveryPolicy) {
		this.deliveryPolicy = deliveryPolicy;
	}

	/**
	 * remove a restaurant from the list of restaurants
	 * @param restaurant the restaurant to remove
	 */
	public void removeRestaurant(Restaurant restaurant) {
		this.restaurants.remove(restaurant);
	}
	
	/**
	 * remove a customer from the list of customers
	 * @param customer the customer to remove
	 */
	public void removeCustomer(Customer customer) {
		this.customers.remove(customer);
	}
	
	/**
	 * remove a courier from the list of couriers
	 * @param courier the courier to remove
	 */
	public void removeCourier(Courier courier) {
		this.couriers.remove(courier);
	}
	
	/**
	 * remove a manager from the list of managers
	 * @param manager the manager to remove
	 */
	public void removeManager(Manager manager) {
		this.managers.remove(manager);
	}

	/**
	 * add a restaurant to the list of restaurants
	 * @param restaurant the restaurant to add
	 */
	public void addRestaurant(Restaurant restaurant) {
		this.restaurants.add(restaurant);
	}
	
	/**
	 * add a customer to the list of customers
	 * @param customer the customer to add
	 */
	public void addCustomer(Customer customer) {
		this.customers.add(customer);
	}
	
	/**
	 * add a courier to the list of couriers
	 * @param courier the courier to add
	 */
	public void addCourier(Courier courier) {
		this.couriers.add(courier);
	}
	
	/**
	 * add a manager to the list of managers
	 * @param courier the courier to add
	 */
	public void addManager(Manager manager) {
		this.managers.add(manager);
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
	 * computing the total income
	 * @return the total income
	 */
	public double ComputeTotalIncome(Date initial_date, Date final_date) {
		double total_income = 0;

		for(Order order: orders) {

			// We only compute the orders between the specified dates
			if(order.getDate().compareTo(initial_date) >= 0 && order.getDate().compareTo(final_date) <= 0) {
				total_income += order.getPrice() + this.servicefee;
			}
		}
		return total_income;
	}

	/**
	 * computing the total profit between two dates
	 * @return the total profit
	 */
	public double ComputeTotalProfit(Date initial_date, Date final_date) {
		double total_profit = 0;
		for(Order order: orders) {
			if(order.getDate().compareTo(initial_date) >= 0 && order.getDate().compareTo(final_date) <= 0){
				total_profit += order.getPrice()*markup_per + servicefee - delivery_cost;
			}
		}
		return total_profit;
	}

	/**
	 * computing the total profit
	 * @return the total profit
	 */
	public double ComputeTotalProfit() {
		double total_profit = 0;
		for(Order order: orders) {
			total_profit += order.getPrice()*markup_per + servicefee - delivery_cost;
		}
		return total_profit;
	}
	
	/**
	 * computing the average income per customer
	 * @return the average income per customer
	 */
	public double ComputeAverageIncome(Date initial_date, Date final_date) {
		double total_income = 0;
		ArrayList<Integer> idCustomers = new ArrayList<Integer>();
		for(Order order: orders) {
			if(order.getDate().compareTo(initial_date) >= 0 && order.getDate().compareTo(final_date) <= 0){
				total_income += order.getPrice() + this.servicefee;
				if(!idCustomers.contains(order.getCustomer().getID())) {
					idCustomers.add(order.getCustomer().getID());
				}
			}
		}
		return total_income/idCustomers.size();
	}
	
	/**
	 * @return the average price of the previous month
	 */
	private double priceAVG() { 
		Calendar currentCal = Calendar.getInstance();
		double income = 0;
		int counter = 0;
		//We go through all orders and see if they are form the last month
		for(Order order: orders) { 
			Calendar cal = Calendar.getInstance();
			cal.setTime(order.getDate());
			if( currentCal.get(Calendar.MONTH)*30 + currentCal.get(Calendar.DAY_OF_MONTH) - (cal.get(Calendar.MONTH)*30 + cal.get(Calendar.DAY_OF_MONTH)) < 30) {
				income += order.getPrice();
				counter++;
			}
				
		}
		return income/counter; 
	}
	
	/**
	 * based on last month total income, given a markup percentage
	 * and a service fee, compute the delivery cost to meet the target profit
	 * @param targetprofit the profit we want to achieve
	 */
	public void targetProfit_DeliveryCost(double targetprofit){
		this.delivery_cost = servicefee - targetprofit + priceAVG()*markup_per;				
	}
	

	/**
	 * based on last month total income, given a markup percentage
	 * and a delivery cost, compute the service fee to meet the target profit
	 * @param targetprofit the profit we want to achieve
	 */
	public void targetProfit_ServiceFee(double targetprofit){
		this.servicefee = targetprofit - priceAVG()*markup_per + delivery_cost;		
	}
	

	/**
	 * based on last month total income, given a delivery cost
	 * and a service fee, compute the markup percentage to meet the target profit
	 * @param targetprofit the profit we want to achieve
	 */
	public void targetProfit_Markup(double targetprofit){
		this.markup_per = (targetprofit + delivery_cost - servicefee)/priceAVG();
	}
	

	/**
	 * calculating the most selling restaurant
	 * @return the restaurant that sold the most orders in total
	 */
	public Restaurant mostSellingRestaurant() {
		Restaurant most_restaurant = restaurants.get(0);
		int max_orders =  restaurants.get(0).getNumber_orders();
		for(Restaurant r:this.restaurants){
			if(r.getNumber_orders() > max_orders){
				most_restaurant = r;
				max_orders = r.getNumber_orders();
			}
		}
		return most_restaurant;
	}

	/**
	 * calculating the least selling restaurant
	 * @return the restaurant that sold the least orders in total
	 */
	public Restaurant leastSellingRestaurant() {
		Restaurant least_restaurant = restaurants.get(0);
		int min_orders =  restaurants.get(0).getNumber_orders();
		for(Restaurant r:this.restaurants){
			if(r.getNumber_orders() < min_orders){
				least_restaurant = r;
				min_orders = r.getNumber_orders();
			}
		}
		return least_restaurant;
	}

	/**
	 * calculating the courier that delivered the most orders
	 * @return the most active courier
	 */
	public Courier mostActiveCourier() {
		Courier most_courier = couriers.get(0);
		int max_orders = couriers.get(0).getDeliveredOrders();
		for(Courier c:this.couriers){
			if(c.getDeliveredOrders() > max_orders){
				most_courier = c;
				max_orders = c.getDeliveredOrders();
			}
		}
		return most_courier;
	}
	
	/**
	 * calculating the courier that delivered the fewest orders
	 * @return the least active courier
	 */
	public Courier leastActiveCourier() {
		Courier least_courier = couriers.get(0);
		int min_orders = couriers.get(0).getDeliveredOrders();
		for(Courier c:this.couriers){
			if(c.getDeliveredOrders() < min_orders){
				least_courier = c;
				min_orders = c.getDeliveredOrders();
			}
		}
		return least_courier;
	} 

	/**
	 * allocate an order to a courier with the current delivery policy
	 * @param the name of the order
	 * @return the courier allocated
	 */
	public Courier allocateCourier(Order order) {
		Courier courier = this.deliveryPolicy.allocateCourier(order, this.couriers);
		order.setCourier(courier);
		return courier;
	}
	
	/**
	 * Process an order completed by the client.
	 * @param order
	 */
	public void processOrder(Order order) {
		
		//We calculate the order price with the user's fidelity card
		order.getCustomer().getFidelityCard().use(order);
		
		//Allocate a courier, depending on the delivery policy.
		//order.setCourier(deliveryPolicy.allocateCourier(order, couriers));
		this.allocateCourier(order);
		orders.add(order);
		//Update the number of orders of each restaurant and the number of delivers of each courier
		order.getRestaurant().setNumber_orders(order.getRestaurant().getNumber_orders() + 1);
		order.getCourier().setDeliveredOrders(order.getCourier().getDeliveredOrders() + 1);
		
		for(Item i:order.getItems()) {
			i.getOrders().add(order);
		}

		for(Meal m:order.getMeals()) {
			m.getOrders().add(order);
		}
				
	}
	
	/**
	 * sort half meals according to times ordered
	 */
	public ArrayList<HalfMeal> sortOrderedHalfMeals() {
		ArrayList<HalfMeal> meals = new ArrayList<HalfMeal>();
		ArrayList<HalfMeal> sorted = new ArrayList<HalfMeal>();

		for(Restaurant r: restaurants){
			for(Meal m : r.getMeals()) {
				if(m instanceof HalfMeal) {
					meals.add((HalfMeal)m);
				}
			}
		}
		
		int components = meals.size();
		
		for(int k = 0; k < components; k++) {
			
			HalfMeal min = meals.get(0);
			
			for(HalfMeal j : meals) {
				if(j.getOrders().size() < min.getOrders().size()) {
					min = j;
				}
			}
			sorted.add(min);

			meals.remove(min);
		}
				
		return sorted;
	}
	
	/**
	 * Sort the items selected a-la-carte according to times ordered
	 */
	public ArrayList<Item> sortOrderedItems() {
		
		ArrayList<Item> items = new ArrayList<Item>();
		ArrayList<Item> sorted = new ArrayList<Item>();

		for(Restaurant r: restaurants){
			for(Item i : r.getMenu() ) {
				items.add(i);
			}
		}
		
		int components = items.size();
		
		for(int k = 0; k < components; k++) {
			
			Item min = items.get(0);
			
			for(Item j : items) {
				if(j.getOrders().size() < min.getOrders().size()) {
					min = j;
				}
			}
			sorted.add(min);

			items.remove(min);
		}
				
		
		return sorted;
		
	}

	/**
	 * add new customer to the list of receivers of special offer notifications
	 * @param observer the observer to be registered
	 */
	@Override
	public void registerObserver(Observer observer) {
		this.observers.add((Customer)observer);
	}

	/**
	 * remove customer from the list of receivers of special offer notifications
	 * @param observer the observer to be removed
	 */
	@Override
	public void removeObserver(Observer observer) {
		this.observers.remove((Customer)observer);
	}

	
	/**
	 * notify the registered observers of a new offer
	 * @param restaurant the restaurant that set the special offer
	 * @param offer the special offer that was set
	 */
	@Override
	public void notifyObservers(Restaurant restaurant, Offer offer) {
		for(Observer o:observers){
			o.update(restaurant, offer);
		}
	}

	/**
	 * @return the observers
	 */
	public ArrayList<Customer> getObservers() {
		return observers;
	}

	/**
	 * @param observers the observers to set
	 */
	public void setObservers(ArrayList<Customer> observers) {
		this.observers = observers;
	}

	
		
}
