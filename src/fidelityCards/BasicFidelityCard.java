package fidelityCards;

import items.*;
import meals.*;
import orders.Order;

/**
 * A class which represents a basic fidelity card 
 * 
 * @author Guy Tayou
 */
public class BasicFidelityCard extends FidelityCard {

	public BasicFidelityCard() {
		super();
	}

	/**
	 * Calculate the price of the order
	 */
	@Override
	public void use(Order order) {
		double price = 0;
		
		for(Meal m:order.getMeals()){
			price += m.price();
			m.getOrders().add(order);
		}
		
		for(Item i:order.getItems()) {
			price += i.getPrice();
			i.getOrders().add(order);
		}
		
		order.setPrice(price);
	}

}
