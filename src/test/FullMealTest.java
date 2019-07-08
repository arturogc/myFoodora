package test;

import static org.junit.Assert.*;

import java.awt.geom.Point2D;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import items.Dessert;
import items.Item;
import items.MainDish;
import items.Starter;
import meals.FullMeal;
import meals.InvalidItemException;
import meals.Meal;
import orders.Order;
import users.ExistingUserException;
import users.Restaurant;

public class FullMealTest {
	
	private FullMeal meal;
	
	private Starter starter;
	
	private Dessert dessert;
	
	private MainDish mainDish;
	
	private Restaurant restaurant;
	
	@Before
	public void setUp() throws Exception {
		meal = new FullMeal("meal_1", Item.Type.GlutenFree);
		starter = new Starter("name", 50, Item.Type.Standard, true);
		dessert = new Dessert("name", 50, Item.Type.Standard, true);
		mainDish = new MainDish("name", 50, Item.Type.Standard, true);
		restaurant = new Restaurant(null, "resto du love", "resto1", "azerty", new Point2D.Double(5,8));
		meal.setStarter(starter);
		meal.setMainDish(mainDish);
		meal.setDessert(dessert);
		meal.setRestaurant(restaurant);
	}

	@After
	public void tearDown() throws Exception {
		Restaurant.getIds().clear();
		Restaurant.getUsernames().clear();
	}

	@Test
	public void testPrepare() {
		// We try to throw an exception with the preparation
		try {
			// test a null item
			starter = null;
			meal = new FullMeal("meal_1", Item.Type.GlutenFree);
			meal.prepare(starter, mainDish, dessert);

			// test gluten free effects
			starter = new Starter("name", 50, Item.Type.Standard, true);
			meal = new FullMeal("meal_1", Item.Type.GlutenFree);
			starter.setGlutenFree(false);
			meal.prepare(starter, mainDish, dessert);
			
			starter = new Starter("name", 50, Item.Type.Standard, true);
			meal = new FullMeal("meal_1", Item.Type.Vegetarian);
			meal.prepare(starter, mainDish, dessert);
			
			fail("InvalidItemException not thrown during meal object preparation");

		} catch (InvalidItemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testFullMeal() {
		assertNotNull("Instance not created", meal);
	}

	@Test
	public void testGetType() {
		assertEquals("Incorrect type", Item.Type.GlutenFree, meal.getType());
	}

	@Test
	public void testSetType() {
		meal.setType(Item.Type.Vegetarian);
		assertEquals("Incorrect type", Item.Type.Vegetarian, meal.getType());
	}

	@Test
	public void testGetStarter() {
		assertEquals("Incorrect starter", starter, meal.getStarter());
	}

	@Test
	public void testSetStarter() {
		Starter starter2 = new Starter("name2", 100, Item.Type.Standard, true);
		meal.setStarter(starter2);
		assertEquals("Incorrect starter", starter2, meal.getStarter());
	}

	@Test
	public void testGetMainDish() {
		assertEquals("Incorrect main dish", mainDish, meal.getMainDish());	
	}

	@Test
	public void testSetMainDish() {
		MainDish mainDish2 = new MainDish("name2", 2, Item.Type.Standard, false);
		meal.setMainDish(mainDish2);
		assertEquals("Incorrect starter", mainDish2, meal.getMainDish());	
	}

	@Test
	public void testGetDessert() {
		assertEquals("Incorrect dessert", dessert, meal.getDessert());	
	}

	@Test
	public void testSetDessert() {
		Dessert dessert2 = new Dessert("name2", 2, Item.Type.Standard, false);
		meal.setDessert(dessert2);
		assertEquals("Incorrect dessert", dessert2, meal.getDessert());	
	}

	@Test
	public void testGetRestaurant() {
		assertEquals("Incorrect restaurant", restaurant, meal.getRestaurant());	
	}

	@Test
	public void testSetRestaurant() {
		Restaurant resto;
		try {
			resto = new Restaurant(null, "resto beta", "resto2", "azertyiop", new Point2D.Double(5,8));
			meal.setRestaurant(resto);
			assertEquals("Incorrect restaurant", resto, meal.getRestaurant());	
		} catch (ExistingUserException e) {
			fail("tryid to create an existing restaurant");
		}

	}

	@Test
	public void testAddOrder() {
		Order order = new Order(null, null, null, new ArrayList<Item>(), new ArrayList<Meal>());
		meal.getOrders().add(order);
		assertEquals("Incorrect order", order, meal.getOrders().get(meal.getOrders().size()-1));	
	}

	@Test
	public void testRemoveOrder() {
		Order order = new Order(null, null, null, new ArrayList<Item>(), new ArrayList<Meal>());
		meal.getOrders().add(order);
		meal.removeOrder(order);
		assertEquals("order not removed", false, meal.getOrders().contains(order));	
	}

}
