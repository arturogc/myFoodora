package deliveryPolicies;

import java.util.ArrayList;

import orders.Order;
import users.Courier;
import users.Restaurant;

/**
 * This class provides a method to allocate a courier to an order
 * 
 * @author Guy Tayou
 */
public class FastestDelivery implements DeliveryPolicy {

	public FastestDelivery() {
	}

	/**
	 * The courier which has the shortest distance to cover to retrieve
     * the order from the chosen restaurant and delivering it to 
     * the customer is chosen
     * 
	 * @param order the order to deliver
	 * @param couriers the list of couriers
	 * */
	public Courier allocateCourier(Order order, ArrayList<Courier> couriers) {
		
		Courier c = couriers.get(0);
		
		Restaurant restaurant = order.getRestaurant();
		
		for(Courier courier : couriers) {

			if(c.getState() == Courier.State.OffDuty && courier.getState() == Courier.State.OnDuty) {
				c = courier;
			}
			
			if( courier.getPosition().distance(restaurant.getAddress()) < c.getPosition().distance(restaurant.getAddress()) && courier.getState() == Courier.State.OnDuty) {
				c = courier;
			}
		}
		
		return c;
	}

}
