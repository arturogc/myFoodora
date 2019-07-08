package meals;

import items.Dessert;
import items.Item;
import items.MainDish;
import items.Starter;

/**
 * The class used to represent a full meal
 * 
 * @author Guy Tayou
 */
public class FullMeal extends Meal {

	public FullMeal(String name, Item.Type type) {
		super(name, type);
	}
	
	/**
	 * 
	 * 
	 * @param Starter
	 * @param MainDish
	 * @param Dessert
	 * 
	 * @throws InvalidItemException
	 */
	public void prepare(Starter starter, MainDish mainDish, Dessert dessert) throws InvalidItemException {
		
		if(starter == null || dessert == null || mainDish == null) {
			throw new InvalidItemException();
		}
		
		if(this.getType() == Item.Type.GlutenFree) {
			if(starter.isGlutenFree() == false || mainDish.isGlutenFree() == false || dessert.isGlutenFree() == false) {
				throw new InvalidItemException();
			}
		} else if(this.getType() == Item.Type.Vegetarian) {
			if(starter.getType() == Item.Type.Vegetarian || mainDish.getType() == Item.Type.Vegetarian || dessert.getType() == Item.Type.Vegetarian) {
				throw new InvalidItemException();
			}		
		}

		this.starter = starter;
		this.mainDish = mainDish;
		this.dessert = dessert;
		
	}

	@Override
	public double price() {
		
		double price = starter.getPrice() + mainDish.getPrice() + dessert.getPrice();
	
		price *= (1-discountFactor());
		
		return price;
	}
	
	
}
