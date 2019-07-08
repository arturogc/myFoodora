package test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import fidelityCards.PointFidelityCard;
import items.Item;
import meals.Meal;
import orders.Order;

public class PointFidelityCardTest {
	
	private PointFidelityCard card;

	@Before
	public void setUp() throws Exception {
		card = new PointFidelityCard();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testUse() {
		ArrayList<Item> items = new ArrayList<Item>();
		items.add(new Item("name", 500, Item.Type.Standard, false));
		Order order = new Order(null, null, null, items, new ArrayList<Meal>());
		card.use(order);
		System.out.println(card.getPoints());
		assertFalse("invalid value of reached", card.isReached());		
		assertEquals("invalid value of points", 50, card.getPoints(), 0.1);

		items.add(new Item("name", 500, Item.Type.Standard, false));
		items.add(new Item("name", 500, Item.Type.Standard, false));
		card.setPoints(0);
		card.use(order);
		assertTrue("invalid value of reached", card.isReached());		
		assertEquals("invalid value of points", 0, card.getPoints(), 0.1);
	}

	@Test
	public void testPointFidelityCard() {
		assertNotNull("Instance not created", card);
	}

	@Test
	public void testGetPoints() {
		assertEquals("Incorrect points value", 0, card.getPoints());		
	}

	@Test
	public void testSetPoints() {
		card.setPoints(10);
		assertEquals("Incorrect points value", 10, card.getPoints());		
	}

	@Test
	public void testIsReached() {
		assertEquals("Incorrect reach value", false, card.isReached());
	}

	@Test
	public void testSetReached() {
		card.setReached(true);
		assertEquals("Incorrect reached value", true, card.isReached());
	}

}
