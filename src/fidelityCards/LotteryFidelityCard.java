package fidelityCards;

import java.util.Random;

import items.Item;
import meals.Meal;
import orders.Order;


/**
 * A class which represents a lottery fidelity card.
 * A member that has this card will have a certain probability to gain her meal for free each day.
 * 
 *  @author Guy Tayou
 */
public class LotteryFidelityCard extends FidelityCard{
	
	/**
	 * The probability to gain a meal for free
	 * Must be a number between 0 and and max
	 */
	protected int chance = 1;
	
	/**
	 * Allows to set the precision of the probability to win
	 */
	protected int max = 100;
	
	public LotteryFidelityCard() {
		super();
	}

	@Override
	public void use(Order order) {
		Random rand = new Random();
		
		int randInt = rand.nextInt(max);
		
		if(randInt <= chance) {
			order.setPrice(0);
		} else {
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

	/**
	 * @return the chance
	 */
	public double getChance() {
		return chance;
	}

	/**
	 * @param probability the probability to set
	 */
	public void setChance(int chance) {
		
		if(chance > max) {
			chance = max;
		}
		
		if(chance < 0) {
			chance = 0;
		}
		
		this.chance = chance;
	}

	/**
	 * @return the max
	 */
	public int getMax() {
		return max;
	}

	/**
	 * @param max the max to set
	 */
	public void setMax(int max) {
		this.max = max;
	}	
}
