package notifications;

import users.*;

/**
 * interface that will be implemented in users
 * @author Arturo Garrido
 *
 */
public interface Observer {
	
	/**
	 * 
	 * @param restaurant the restaurant that set a new special offer
	 * @param meal the new meal-of-the-week
	 * @param genericDiscountFactor the new generic discount factor
	 * @param specialDiscountFactor the new special discount factor
	 */
	public void update(Restaurant restaurant, Offer offer);
}
