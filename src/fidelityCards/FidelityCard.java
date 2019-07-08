package fidelityCards;

import orders.Order;

/**
 * An abstract class which represents a fidelity card 
 * 
 * @author Guy Tayou
 * 
 */
public abstract class FidelityCard {
	
	public FidelityCard() {
		
	}
	
	/**
	 * The method to call to use a fidelity card for an order
	 * 
	 * @param order
	 */
	public abstract void use(Order order);

}
