package test;

import java.awt.geom.Point2D;

import org.junit.Test;

import junit.framework.TestCase;
import meals.InvalidItemException;
import users.Courier;
import users.Customer;
import users.ExistingUserException;
import users.Manager;
import users.Restaurant;
import users.User;

public class UserTest extends TestCase {

	private Manager m;
	private Customer c;
	private Restaurant restaurant;
	private Courier courier1;
	
	protected void setUp() throws Exception, ExistingUserException, InvalidItemException {
		/**
		 * we create a user of each kind
		 */
		m = new Manager(null, "Arturo", "arturo_95", "Garrido", "realmadrid");
		restaurant = new Restaurant(null, "Bonheur", "bonheur", "1986", new Point2D.Double(12,7));
		courier1 = new Courier("Megan", "Kardashian", "meg", "kitty", new Point2D.Double(1,0));
		c = new Customer(null, "Arturo", "agc20", "Garrido", "realmadrid", new Point2D.Double(2,3), "arturo.garrido@student.ecp.fr", "0782683879");
	}
	
	protected void tearDown() throws Exception, ExistingUserException, InvalidItemException {
		m = null;
		Customer.getUsernames().clear();
		Restaurant.getUsernames().clear();
		Courier.getUsernames().clear();
		Manager.getUsernames().clear();
		Customer.getIds().clear();
		Restaurant.getIds().clear();
		Courier.getIds().clear();
		Manager.getIds().clear();
		c = null;
		restaurant = null;
		courier1 = null;
	}
	
	@Test
	public void testGetName() {
		assertTrue("Name not obtained correctly", c.getName().equals("Arturo"));
		assertTrue("Name not obtained correctly", restaurant.getName().equals("Bonheur"));
		assertTrue("Name not obtained correctly", courier1.getName().equals("Megan"));
		assertTrue("Name not obtained correctly", m.getName().equals("Arturo"));
	}

	/**
	 * this test is only valid if the previous one also is
	 */
	@Test
	public void testSetName() {
		((User)c).setName("NewName");
		assertTrue("Name not set correctly", c.getName().equals("NewName"));
		((User)restaurant).setName("NewName");
		assertTrue("Name not set correctly", restaurant.getName().equals("NewName"));
		((User)courier1).setName("NewName");
		assertTrue("Name not set correctly", courier1.getName().equals("NewName"));
		((User)m).setName("NewName");
		assertTrue("Name not set correctly", m.getName().equals("NewName"));
	}

	@Test
	public void testGetID() {
		assertTrue("ID not obtained correctly", c.getID() == 1);
		assertTrue("ID not obtained correctly", restaurant.getID() == 1);
		assertTrue("ID not obtained correctly", courier1.getID() == 1);
		assertTrue("ID not obtained correctly", m.getID() == 1);
	}

	@Test
	public void testGetUsername() {
		assertTrue("Userame not obtained correctly", c.getUsername().equals("agc20"));
		assertTrue("Userame not obtained correctly", restaurant.getUsername().equals("bonheur"));
		assertTrue("Userame not obtained correctly", courier1.getUsername().equals("meg"));
		assertTrue("Userame not obtained correctly", m.getUsername().equals("arturo_95"));
	}

	/**
	 * this test is only valid if the previous one also is
	 */
	@Test
	public void testSetUsername() {
		((User)c).setUsername("NewName");
		assertTrue("Username not set correctly", c.getUsername().equals("NewName"));
		((User)restaurant).setUsername("NewName");
		assertTrue("Username not set correctly", restaurant.getUsername().equals("NewName"));
		((User)courier1).setUsername("NewName");
		assertTrue("Username not set correctly", courier1.getUsername().equals("NewName"));
		((User)m).setUsername("NewName");
		assertTrue("NUsername not set correctly", m.getUsername().equals("NewName"));
	}

	@Test
	public void testGetPassword() {
		assertTrue("Password not obtained correctly", c.getPassword().equals("realmadrid"));
		assertTrue("Password not obtained correctly", restaurant.getPassword().equals("1986"));
		assertTrue("Password not obtained correctly", courier1.getPassword().equals("kitty"));
		assertTrue("Password not obtained correctly", m.getPassword().equals("realmadrid"));
	}

	/**
	 * this test is only valid if the previous one also is
	 */
	@Test
	public void testSetPassword() {
		((User)c).setPassword("NewPassword");
		assertTrue("Password not set correctly", c.getPassword().equals("NewPassword"));
		((User)restaurant).setPassword("NewPassword");
		assertTrue("Password not set correctly", restaurant.getPassword().equals("NewPassword"));
		((User)courier1).setPassword("NewPassword");
		assertTrue("Password not set correctly", courier1.getPassword().equals("NewPassword"));
		((User)m).setPassword("NewPassword");
		assertTrue("Password not set correctly", m.getPassword().equals("NewPassword"));
	}

	@Test
	public void testIsEnabled() {
		assertTrue("User's activation state not obtained correctly", c.isEnabled());
		assertTrue("User's activation state not obtained correctly", restaurant.isEnabled());
		assertTrue("User's activation state not obtained correctly", courier1.isEnabled());
		assertTrue("User's activation state not obtained correctly", m.isEnabled());
	}

	/**
	 * this test is only valid if the previous one also is
	 */
	@Test
	public void testSetEnabled() {
		((User)c).setEnabled(false);
		assertFalse("User not activated/disactivated correctly", c.isEnabled());
		((User)restaurant).setEnabled(false);
		assertFalse("User not activated/disactivated correctly", restaurant.isEnabled());
		((User)courier1).setEnabled(false);
		assertFalse("User not activated/disactivated correctly", courier1.isEnabled());
		((User)m).setEnabled(false);
		assertFalse("User not activated/disactivated correctly", m.isEnabled());
	}

}
