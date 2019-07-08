package test;

import java.awt.geom.Point2D;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import items.Item;
import users.ExistingUserException;
import users.Restaurant;

public class ItemTest extends junit.framework.TestCase {
	
	private Item item;
	
	private Restaurant restaurant;
	
	
	public ItemTest(String name) {
		super(name);
		
		try {
			restaurant = new Restaurant(null, "resto du love", "resto1", "azerty", new Point2D.Double(5,8));
		} catch (ExistingUserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Before
	public void setUp() throws Exception {
		super.setUp();
		item = new Item("name", 50, Item.Type.Standard, false);
		item.setRestaurant(restaurant);
	}

	@After
	public void TearDown() throws Exception {
		Restaurant.getIds().clear();
		Restaurant.getUsernames().clear();
		System.out.print(Restaurant.getUsernames().size());
	}
	
	@Test
	public void testItem() {
		assertNotNull("Instance not created", item);
	}

	@Test
	public void testGetName() {
		assertEquals("Incorrect name", "name", item.getName());
	}

	@Test
	public void testSetName() {
		item.setName("name2");
		assertEquals("Incorrect name", "name2", item.getName());
	}

	@Test
	public void testGetPrice() {
		assertEquals("Incorrect price", 50, item.getPrice(), 0.1);
	}

	@Test
	public void testSetPrice() {
		item.setPrice(60);
		assertEquals("Incorrect price", 60, item.getPrice(), 0.1);
	}

	@Test
	public void testGetType() {
		assertEquals("Incorrect type", Item.Type.Standard, item.getType());
	}

	@Test
	public void testSetType() {
		item.setType(Item.Type.Vegetarian);
		assertEquals("Incorrect type", Item.Type.Vegetarian, item.getType());
	}

	@Test
	public void testIsGlutenFree() {
		assertEquals("Incorrect value of glutenFree", false, item.isGlutenFree());	
	}

	@Test
	public void testSetGlutenFree() {
		item.setGlutenFree(true);
		assertEquals("Incorrect value of glutenFree", true, item.isGlutenFree());	
	}

	@Test
	public void testGetRestaurant() {
		assertEquals("Incorrect restaurant", restaurant, item.getRestaurant());	
	}

	@Test
	public void testSetRestaurant() {
		Restaurant r;
		try {
			r = new Restaurant(null, "resto n°2", "resto2", "azerty", new Point2D.Double(5,8));
			item.setRestaurant(r);
			assertEquals("Incorrect restaurant", r, item.getRestaurant());	
		} catch (ExistingUserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("the restaurant you try to create already exists.");
		}
	}
}
