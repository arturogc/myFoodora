package meals;

import items.Item;

/**
 * The Meal Factory class used to create new Meal objects
 * 
 * @author Guy Tayou
 */
public class MealFactory {

	/**
	 * Enumeration of the possible categories of a meal
	 */
	public enum Category {
		Half,
		Full;
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
	public Meal createMeal(String name, Category category, Item.Type type) {
		Meal meal = null;
        switch(category) {
        	case Half:
        		meal = new HalfMeal(name, type);
        		break;
        	case Full:
        		meal = new FullMeal(name, type);     
        		break;
        }
        return meal;
	}

}
