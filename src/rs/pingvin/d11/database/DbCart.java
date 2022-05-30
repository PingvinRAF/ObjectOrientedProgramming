package rs.pingvin.d11.database;

import rs.pingvin.d11.model.MenuItem;
import rs.pingvin.d11.model.User;

import java.util.ArrayList;
import java.util.List;

public class DbCart {

    private static User activeUser;
    private static List<MenuItem> itemsInCart = new ArrayList<>();
    private static double price    = 0;
    private static byte   discount = 0;

    private static void clear() {
        itemsInCart.clear();
        price = 0;
        discount = 0;
    }

    public static void setUser(User user) {
        DbCart.activeUser = user;
    }

    public static User getUser() {
        return activeUser;
    }

    public static void add(MenuItem item) {
        if (item == null)
            return;

        itemsInCart.add(item);

        price += item.getPrice();
        if (price >= 30)
            discount = 10;
    }

    public static void remove(MenuItem item) {
        if (item == null)
            return;

        itemsInCart.remove(item);

        price -= item.getPrice();
        if (price < 30)
            discount = 0;
    }

    public static double getTotal() {
        return price;
    }

    public static double getSubTotal() {
        return price - discount * price / 100;
    }

    public static byte getDiscount() {
        return discount;
    }

    public static void checkout() {
        DbOrders.addToDatebase(activeUser.getUsername(), getSubTotal(), itemsInCart);
        clear();
    }

    public static int getSize() {
        return itemsInCart.size();
    }

    public static List<MenuItem> getItems() {
        return itemsInCart;
    }

}
