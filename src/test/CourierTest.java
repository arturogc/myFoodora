package test;

import java.awt.geom.Point2D;

import org.junit.Test;

import junit.framework.TestCase;
import meals.InvalidItemException;
import users.Courier;
import users.ExistingUserException;
import users.Courier.State;

/**
 * 
 * @author Arturo Garrido
 *
 */

public class CourierTest extends TestCase {

	private Courier courier1;

	protected void setUp() throws Exception, ExistingUserException, InvalidItemException {
		super.setUp();
		/**
		 * The instance of Courier
		 */
		courier1 = new Courier("Megan", "Kardashian", "meg", "kitty", new Point2D.Double(1,0));

	}
	
	protected void tearDown() throws Exception, ExistingUserException, InvalidItemException {
		super.tearDown();
		courier1 = null;
		Courier.getUsernames().clear();
		Courier.getIds().clear();
	}

	@Test
	public void testGetSurname() {
		assertTrue(courier1.getSurname().equals("Kardashian"));
	}

	/**
	 * This test is only valid if the previous one works
	 */
	@Test
	public void testSetSurname() {
		assertTrue(courier1.getSurname().equals("Kardashian"));
	}

	@Test
	public void testGetPosition() {
		assertTrue(courier1.getPosition().equals(new Point2D.Double(1,0)));
	}

	/**
	 * This test is only valid if the previous one works
	 */
	@Test
	public void testSetPosition() {
		courier1.setPosition(new Point2D.Double(5,5));
		assertTrue(courier1.getPosition().equals(new Point2D.Double(5,5)));
	}

	/**
	 * This test actually works for both getPhoneNumber()
	 * and setPhoneNumber()
	 */
	@Test
	public void testGetPhoneNumber() {
		courier1.setPhoneNumber("782568944");
		assertTrue(courier1.getPhoneNumber().equals("782568944"));
	}

	@Test
	public void testGetState() {
		assertTrue(courier1.getState().equals(State.OnDuty));
	}

	/**
	 * This test is only valid if the previous one works
	 */
	@Test
	public void testSetState() {
		courier1.setState(State.OffDuty);
		assertTrue(courier1.getState().equals(State.OffDuty));
	}


}
