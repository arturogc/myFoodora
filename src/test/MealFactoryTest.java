package test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import items.Item;
import meals.FullMeal;
import meals.HalfMeal;
import meals.Meal;
import meals.MealFactory;

public class MealFactoryTest {
	
	MealFactory factory;

	@Before
	public void setUp() throws Exception {
		factory = new MealFactory();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCreateMeal() {
		Meal meal = factory.createMeal("meal_1", MealFactory.Category.Full, Item.Type.Standard);
		assertEquals("Wrong meal category", true, (meal instanceof FullMeal));

		meal = factory.createMeal("meal_1", MealFactory.Category.Half, Item.Type.Standard);
		assertEquals("Wrong meal category", true, (meal instanceof HalfMeal));
		
		meal = factory.createMeal("meal_1", MealFactory.Category.Half, Item.Type.Vegetarian);
		assertEquals("Wrong item type", Item.Type.Vegetarian, meal.getType());

		meal = factory.createMeal("meal_1", MealFactory.Category.Half, Item.Type.GlutenFree);
		assertEquals("Wrong item type", Item.Type.GlutenFree, meal.getType());

		meal = factory.createMeal("meal_1", MealFactory.Category.Half, Item.Type.Vegetarian);
		assertEquals("Wrong item type", Item.Type.Vegetarian, meal.getType());
	}

}
