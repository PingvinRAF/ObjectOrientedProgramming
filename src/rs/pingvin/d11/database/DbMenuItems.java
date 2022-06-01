package rs.pingvin.d11.database;

import rs.pingvin.d11.model.Category;
import rs.pingvin.d11.model.DrinkItem;
import rs.pingvin.d11.model.FoodItem;
import rs.pingvin.d11.model.MenuItem;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DbMenuItems {

    private static final String fullFilePath = "resources/database/D11/menu.txt";

    private static List<FoodItem>  foodItems  = new ArrayList<>();
    private static List<DrinkItem> drinkItems = new ArrayList<>();
    private static List<MenuItem>  menuItems  = new ArrayList<>();

    public static void load() {
        try {
            Scanner scanner = new Scanner(new File(fullFilePath));

            while (scanner.hasNext()) {
                Category category = null;
                try {
                    category = Category.valueOf(scanner.nextLine());
                }
                catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }

                if (category.equals(Category.DRINKS))
                    loadDrinkItems(scanner);
                else
                    loadFoodItems(scanner, category);
            }

            scanner.close();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void loadDrinkItems(Scanner scanner) {
        String line;

        while (scanner.hasNext() && !(line = scanner.nextLine()).isEmpty()) {
            String[] attributes = line.split(" ");

            DrinkItem drinkItem = new DrinkItem(attributes[0], Double.parseDouble(attributes[1].substring(1)));

            drinkItems.add(drinkItem);
            menuItems.add(drinkItem);
        }
    }

    private static void loadFoodItems(Scanner scanner, Category category) {
        String line;

        while (scanner.hasNext() && !(line = scanner.nextLine()).isEmpty()) {
            String[] attributes  = line.split(" ");
            String[] ingredients = scanner.nextLine().split(",");

            FoodItem foodItem = new FoodItem(attributes[0], Double.parseDouble(attributes[1].substring(1)), category);
            foodItem.getIngredients().addAll(List.of(ingredients));

            foodItems.add(foodItem);
            menuItems.add(foodItem);
        }
    }

    public static List<FoodItem> getFoodItems() {
        return foodItems;
    }

    public static List<FoodItem> getFoodItems(Category category) {
        if (category.equals(Category.ALL))
            return getFoodItems();

        List<FoodItem> foodInCategory = new ArrayList<>();

        for (FoodItem item : foodItems)
            if (item.getCategory().equals(category))
                foodInCategory.add(item);

        return foodInCategory;
    }

    public static List<FoodItem> getFoodItems(Category category, String ingredient) {
        if (ingredient.isEmpty())
            return getFoodItems(category);

        List<FoodItem> foodInCategoryWithFilter = new ArrayList<>();

        for (FoodItem item : getFoodItems(category))
            if (item.getIngredients().contains(ingredient))
                foodInCategoryWithFilter.add(item);

        return foodInCategoryWithFilter;
    }

    public static List<DrinkItem> getDrinkItems() {
        return drinkItems;
    }

    public static List<MenuItem> getMenuItems() { //NOTE Usage: Database Bridge
        return menuItems;
    }

}
