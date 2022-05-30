package rs.pingvin.d11.model;

import java.util.Objects;

public abstract class MenuItem {

    private String name;
    private double price;
    private Category category;

    protected MenuItem(String name, double price, Category category) {
        this.name = name;
        this.price = price;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public Category getCategory() {
        return category;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof MenuItem))
            return false;

        MenuItem menuItem = (MenuItem) obj;
        return name.equals(menuItem.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, category);
    }
}
