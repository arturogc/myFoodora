package test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import fidelityCards.LotteryFidelityCard;
import items.Item;
import meals.Meal;
import orders.Order;

public class LotteryFidelityCardTest {
	
	private LotteryFidelityCard card;
	
	@Before
	public void setUp() throws Exception {
		card = new LotteryFidelityCard();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testUse() {
		Order order = new Order(null, null, null, new ArrayList<Item>(), new ArrayList<Meal>());
		order.setPrice(45);
		card.use(order);
		boolean valid = false;	
		if(order.getPrice() < 1 || Math.abs(order.getPrice() - 45) < 1) {
			valid = true;
		}
		
		assertTrue("invalid price", valid);

	}

	@Test
	public void testLotteryFidelityCard() {
		assertNotNull("Instance not created", card);
	}

	@Test
	public void testGetChance() {
		assertEquals("Incorrect chance", 1, card.getChance(), 0.1);
	}

	@Test
	public void testSetChance() {
		card.setChance(10);
		assertEquals("Incorrect chance", 10, card.getChance(), 0.1);
	}

	@Test
	public void testGetMax() {
		assertEquals("Incorrect max", 100, card.getMax(), 0.1);
	}

	@Test
	public void testSetMax() {
		card.setMax(200);
		assertEquals("Incorrect max", 200, card.getMax(), 0.1);
	}

}
