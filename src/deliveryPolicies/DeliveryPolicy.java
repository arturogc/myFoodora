package deliveryPolicies;

import java.util.ArrayList;

import orders.Order;
import users.Courier;

/**
 * The Delivery policy interface
 * It provides a method to allocate a courier to an order
 * 
 * @author Guy Tayou
 */
public interface DeliveryPolicy {
	
	/**
	 * @param order the order to deliver
	 * @param couriers the list of couriers
	 * */
	Courier allocateCourier(Order order, ArrayList<Courier> couriers);
}
