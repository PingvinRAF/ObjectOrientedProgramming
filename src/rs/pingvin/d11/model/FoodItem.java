package rs.pingvin.d11.model;

import java.util.ArrayList;
import java.util.List;

public class FoodItem extends MenuItem {

    private List<String> ingredients = new ArrayList<>();

    public FoodItem(String name, double price, Category category) {
        super(name, price, category);
    }

    public List<String> getIngredients() {
        return ingredients;
    }

}
