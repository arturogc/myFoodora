package test;

import static org.junit.Assert.*;

import java.awt.geom.Point2D;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import items.Dessert;
import items.Item;
import items.MainDish;
import items.Starter;
import meals.HalfMeal;
import users.Restaurant;

public class HalfMealTest {

	private HalfMeal meal;
	
	private Starter starter;
	
	private Dessert dessert;
	
	private MainDish mainDish;
	
	private Restaurant restaurant;
	
	@Before
	public void setUp() throws Exception {
		meal = new HalfMeal("meal_1", Item.Type.GlutenFree);
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
	public void testHalfMeal() {
		assertNotNull("Instance not created", meal);
	}

}
