package notifications;

import users.Restaurant;

/**
 * 
 * @author Arturo Garrido
 *
 */
public interface Observable {
	
	/**
	 * 
	 * @param observer new observer to be added
	 */
	public void registerObserver(Observer observer);
	
	/**
	 * 
	 * @param observer new observer to be removed
	 */
	public void removeObserver(Observer observer);
	
	/**
	 * notify about an offer
	 */
	public void notifyObservers(Restaurant restaurant, Offer offer);
}
