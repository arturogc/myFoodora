package test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import fidelityCards.BasicFidelityCard;
import items.Item;
import meals.Meal;
import orders.Order;

public class BasicFidelityCardTest {

	private BasicFidelityCard card;

	@Before
	public void setUp() throws Exception {
		card = new BasicFidelityCard();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testUse() {
		ArrayList<Item> items = new ArrayList<Item>();
		Order order = new Order(null, null, null, items, new ArrayList<Meal>());
		items.add(new Item("name", 500, Item.Type.Standard, false));
		items.add(new Item("name", 500, Item.Type.Standard, false));
		items.add(new Item("name", 500, Item.Type.Standard, false));
		card.use(order);
		assertEquals("invalid value of price", 1500, order.getPrice(), 0.1);
	}

}
