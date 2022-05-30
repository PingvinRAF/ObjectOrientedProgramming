package rs.pingvin.d11.database;

import rs.pingvin.d11.model.MenuItem;
import rs.pingvin.d11.model.Order;
import rs.pingvin.d11.model.User;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DbOrders {

    private static final String fullFilePath = "resources/database/orders.txt";

    private static List<Order> orders = new ArrayList<>();

    public static void load() {
        try {
            Scanner scanner = new Scanner(new File(fullFilePath));

            while (scanner.hasNext()) {
                String[] attributes = scanner.nextLine().split(",");
                String[] items = scanner.nextLine().split(",");

                orders.add(new Order(attributes[0], Double.parseDouble(attributes[1]), List.of(items)));

                if (scanner.hasNext())
                    scanner.nextLine();
            }

            scanner.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("Database file does not exist");;
        }
    }

    public static void addToDatebase(String user, Double price, List<MenuItem> items) {
        addToDatebase(new Order(user, price, Order.parseOrder(items)));
    }

    public static void addToDatebase(Order order) {
        if (order == null || order.getItems().isEmpty())
            return;

        File file = new File(fullFilePath);
        boolean isFileNotEmpty = file.length() > 0;

        try {
            FileWriter fileWriter = new FileWriter(fullFilePath, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            if(isFileNotEmpty) bufferedWriter.newLine();
            bufferedWriter.write(order.toString());

            bufferedWriter.close();
            orders.add(order);
        }
        catch (IOException e) {
            System.out.println("The given path is a directory.");
        }
    }

    public static List<Order> getOrders() {
        return orders;
    }

    public static List<Order> getOrders(User user) {
        List<Order> userOrders = new ArrayList<>();

        for (Order order : orders)
            if (order.getUser().equals(user.getUsername()))
                userOrders.add(order);

        return userOrders;
    }

}
