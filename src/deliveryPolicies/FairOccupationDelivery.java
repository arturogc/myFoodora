package deliveryPolicies;

import java.util.ArrayList;

import orders.Order;
import users.Courier;

/**
 * This class provides a method to allocate a courier to an order
 * 
 * @author Guy Tayou
 */
public class FairOccupationDelivery implements DeliveryPolicy {

	public FairOccupationDelivery() {
	}

	/**
	 * the courier with the least number of completed delivery is chosen
	 * 
	 * @param order the order to deliver
	 * @param couriers the list of couriers
	 * */
	public Courier allocateCourier(Order order, ArrayList<Courier> couriers) {
		
		Courier c = couriers.get(0);
				
		for(Courier courier : couriers) {

			if((c.getState() == Courier.State.OffDuty || courier.getDeliveredOrders() < c.getDeliveredOrders()) && courier.getState() == Courier.State.OnDuty) {
				c = courier;
			}
		}
		
		return c;
	}

}
