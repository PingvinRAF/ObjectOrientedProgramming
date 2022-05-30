package rs.pingvin.d11.model;

import java.util.ArrayList;
import java.util.List;

public class Order {

    private String user;
    private List<String> items;
    private double price;

    public Order(String user, double price, List<String> items) {
        this.user = user;
        this.items = items;
        this.price = price;
    }

    public static List<String> parseOrder(List<MenuItem> items) {
        List<String> stringItems = new ArrayList<>();

        for (MenuItem item : items)
            stringItems.add(item.getName());

        return stringItems;
    }

    public List<String> getItems() {
        return items;
    }

    public String getUser() {
        return user;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(user).append(",").append(price).append('\n');

        stringBuilder.append(items.get(0));
        for (int i = 1; i < items.size(); ++i)
            stringBuilder.append(",").append(items.get(i));

        stringBuilder.append('\n');

        return stringBuilder.toString();
    }

}
