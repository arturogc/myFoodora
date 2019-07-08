package meals;

import items.Dessert;
import items.Item;
import items.MainDish;
import items.Starter;

/**
 * Class representing a half meal
 * 
 * @author Guy Tayou
 * @author Arturo Garrido
 
 */
public class HalfMeal extends Meal {
	

	public HalfMeal(String name, Item.Type type) {
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
		if(starter == null && dessert == null || starter != null && dessert != null || mainDish == null) {
			throw new InvalidItemException();
		}
		
		if(this.type == Item.Type.GlutenFree && (starter.isGlutenFree() == false || mainDish.isGlutenFree() == false || dessert.isGlutenFree() == false)
			|| this.type != Item.Type.GlutenFree && (starter.getType() != this.type || mainDish.getType() != this.type || dessert.getType() != this.type)
		) {
			throw new InvalidItemException();
		}
		
		
		if(this.getType() == Item.Type.GlutenFree) {
			if(starter != null) {
				if(starter.isGlutenFree() == false || mainDish.isGlutenFree() == false) {
					throw new InvalidItemException();
				}				
			} else { // then dessert is not null
				if(mainDish.isGlutenFree() == false || dessert.isGlutenFree() == false) {
					throw new InvalidItemException();
				}					
			}

		} else if(this.getType() == Item.Type.Vegetarian) {
			if(starter != null) {
				if(starter.getType() == Item.Type.Vegetarian || mainDish.getType() == Item.Type.Vegetarian) {
					throw new InvalidItemException();
				}					
			} else { // then dessert is not null
				if(mainDish.getType() == Item.Type.Vegetarian || dessert.getType() == Item.Type.Vegetarian) {
					throw new InvalidItemException();
				}					
			}
	
		}
		
		this.starter = starter;
		this.mainDish = mainDish;
		this.dessert = dessert;
		
	}
	
	@Override
	public double price() {
		
		double price = mainDish.getPrice();
				
		if(starter != null) {
			price += starter.getPrice();
		}
	
		
		if(dessert != null){
			price += dessert.getPrice();
		}
		
		price *=(1-discountFactor());
		
		return price;
	}

	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "HalfMeal [type=" + type + ", starter=" + starter + ", mainDish=" + mainDish + ", dessert=" + dessert
				+ ", restaurant=" + restaurant.getName() + ", times ordered=" + orders.size() + "]";
	}
	
	
	

}
