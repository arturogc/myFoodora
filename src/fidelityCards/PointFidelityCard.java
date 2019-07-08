package fidelityCards;

import items.*;
import meals.*;
import orders.Order;

/**
 * A class which represents a 2. point fidelity card
 * the card will gain a point for each 10 euros spent in the restaurant. Once
 * it will reach 100 points it will provide a 10% discount on the next order.
 * 
 * @author Guy Tayou
 *
 */
public class PointFidelityCard extends FidelityCard {
	
	/**
	 * the number of points of a card
	 */
	protected int points;
	
	/**
	 * Whether or not the card have reached 100 points the last time he used his card
	 */
	protected boolean reached;

	public PointFidelityCard() {
		super();
		reached = false;
		points = 0;
	}

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
		
		if(reached) {
			order.setPrice(0.9*price);
		} else {
			order.setPrice(price);
		}
		
		points += (int)order.getPrice()/10;
		
		if(points >= 100) {
			points = 0; // reset
			reached = true;
		} else {
			reached = false;
		}
		
	}
	
	/**
	 * @return the points
	 */
	public int getPoints() {
		return points;
	}

	/**
	 * @param points the points to set
	 */
	public void setPoints(int points) {
		this.points = points;
	}

	/**
	 * @return the reached
	 */
	public boolean isReached() {
		return reached;
	}

	/**
	 * @param reached the reached to set
	 */
	public void setReached(boolean reached) {
		this.reached = reached;
	}
}
