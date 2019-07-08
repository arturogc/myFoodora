package test;

import java.awt.geom.Point2D;
import java.util.ArrayList;

import org.junit.Test;

import deliveryPolicies.FastestDelivery;
import items.Dessert;
import items.Item;
import items.MainDish;
import items.Starter;
import items.Item.Type;
import junit.framework.TestCase;
import meals.FullMeal;
import meals.InvalidItemException;
import meals.Meal;
import system.MyFoodora;
import users.ExistingUserException;
import users.Restaurant;

public class RestaurantTest extends TestCase {

	private MyFoodora myfoodora;
	private Restaurant restaurant;
	private Item i1;
	private Item i2;
	private Item i3;
	private Item i4;
	private Item i5;
	private Item i6;
	private ArrayList<Item> items;
	private Meal meal1;
	private ArrayList<Meal> meals;

	protected void setUp() throws Exception, ExistingUserException, InvalidItemException {
		super.setUp();
		
		/**
		 * The MyFoodora system 
		 */
		myfoodora = new MyFoodora(1, 0.05, 1, new FastestDelivery());
		
		/**
		 * the restaurant we will use for the tests
		 */
		restaurant = new Restaurant(myfoodora, "Bonheur", "bonheur", "1986", new Point2D.Double(12,7));
		
		/**
		 * restaurant's items
		 */
		i1 = new Item("Soup", 8.5, Type.Standard, true);
		i2 = new Item("Cake", 5, Type.Vegetarian, false);
		i3 = new Starter("Chips", 4.5, Type.Standard, false);
		i4 = new MainDish("Burguer", 10.5, Type.Standard, false);
		i5 = new Dessert("Chocolate cake", 6.5, Type.Standard, false);
		i6 = new Dessert("Mushrooms", 2.5, Type.Standard, false);
		restaurant.addItem(i1);
		restaurant.addItem(i2);
		restaurant.addItem(i3);
		restaurant.addItem(i4);
		restaurant.addItem(i5);
		items = new ArrayList<Item>();
		items.add(i2);
		items.add(i1);
		
		/**
		 * meals of the restaurant
		 */
		meal1 = new FullMeal("meal_1", Type.Standard);
		meal1.prepare((Starter)i3, (MainDish)i4, (Dessert)i5);
		restaurant.addMeal(meal1);
		restaurant.addMealOfTheWeek(meal1);
		meals = new ArrayList<Meal>();
		meals.add(meal1);
		
	}
	
	
	protected void tearDown() throws Exception, ExistingUserException, InvalidItemException {
		super.tearDown();
		myfoodora = null;
		Restaurant.getUsernames().clear();
		Restaurant.getIds().clear();
		restaurant = null;
		i1 = null;
		i2 = null;
		i3 = null;
		i4 = null;
		i5 = null;
		i6 = null;
		items = null;
		meal1 = null;
		meals = null;
	}
	
	@Test
	public void testGetAddress() {
		assertTrue(restaurant.getAddress().equals(new Point2D.Double(12,7)));
	}

	@Test
	public void testSetAddress() {
		restaurant.setAddress(new Point2D.Double(1,2));
		assertTrue(restaurant.getAddress().equals(new Point2D.Double(1,2)));
	}

	@Test
	public void testGetGenericDiscountFactor() {
		assertTrue(restaurant.getGenericDiscountFactor() == 0.05);
	}

	@Test
	public void testSetGenericDiscountFactor() {
		restaurant.setGenericDiscountFactor(0.02); 
		assertTrue(restaurant.getGenericDiscountFactor() == 0.02);
	}

	@Test
	public void testGetSpecialDiscountFactor() {
		assertTrue(restaurant.getSpecialDiscountFactor() == 0.1);
	}

	@Test
	public void testGetMenu() {
		assertTrue(restaurant.getMenu().size() == 5);
		assertTrue(restaurant.getMenu().get(0).getName().equals("Soup"));
	}

	@Test
	public void testSetMenu() {
		restaurant.setMenu(items);
		assertTrue(restaurant.getMenu().size() == 2);
		assertTrue(restaurant.getMenu().get(0).getName().equals("Cake"));
	}

	@Test
	public void testAddItem() {
		restaurant.addItem(i6);
		assertTrue(restaurant.getMenu().size() == 6);
		assertTrue(restaurant.getMenu().get(5).getName().equals("Mushrooms"));
	}

	@Test
	public void testRemoveItem() {
		restaurant.removeItem(i3);
		assertTrue(restaurant.getMenu().size() == 4);
	}

	@Test
	public void testGetMeals() {
		assertTrue(restaurant.getMeals().size() == 1);
		assertTrue(restaurant.getMeals().get(0).getDessert().getName().equals("Chocolate cake"));
	}

	@Test
	public void testSetMeals() throws InvalidItemException {
		Meal meal2 = new FullMeal("meal_2", Type.Standard);
		meal2.prepare((Starter)i3, (MainDish)i4, (Dessert)i6);
		ArrayList<Meal> meals2 = new ArrayList<Meal>();
		meals2.add(meal2);
		restaurant.setMeals(meals2);
		assertTrue(restaurant.getMeals().size() == 1);
		assertTrue(restaurant.getMeals().get(0).getDessert().getName().equals("Mushrooms"));
	}

	@Test
	public void testAddMeal() throws InvalidItemException {
		Meal meal2 = new FullMeal("meal_2", Type.Standard);
		meal2.prepare((Starter)i3, (MainDish)i4, (Dessert)i6);
		restaurant.addMeal(meal2);
		assertTrue(restaurant.getMeals().size() == 2);
		assertTrue(restaurant.getMeals().get(1).getDessert().getName().equals("Mushrooms"));
	}

	@Test
	public void testRemoveMeal() {
		restaurant.removeMeal(meal1);
		assertTrue(restaurant.getMeals().size() == 0);
	}

	@Test
	public void testAddMealOfTheWeek() throws InvalidItemException {
		Meal meal2 = new FullMeal("meal_2", Type.Standard);
		meal2.prepare((Starter)i3, (MainDish)i4, (Dessert)i6);
		restaurant.addMeal(meal2);
		restaurant.addMealOfTheWeek(meal2);
		assertTrue(restaurant.getMealsOfTheWeek().get(restaurant.getMealsOfTheWeek().size()-1).getDessert().equals(i6));
	}

}
