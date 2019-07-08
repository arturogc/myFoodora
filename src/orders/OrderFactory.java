package orders;

import java.util.ArrayList;

import items.*;
import meals.*;
import users.*;


/**
 * @author Arturo Garrido
 *
 */
public class OrderFactory {
	
	public Order CreateOrder(String name, Customer customer, Restaurant restaurant, ArrayList<Item> items, ArrayList<Meal> meals) {
		Order order = new Order(name, customer, restaurant, items, meals);
        return order;
	}
}
