package rs.pingvin.d11.database;

import rs.pingvin.d11.model.MenuItem;
import rs.pingvin.d11.model.User;

import java.util.ArrayList;
import java.util.List;

public class DbBridge {

    public static class Users {

        private static List<String> users = new ArrayList<>();

        public static User getUser(int index) {
            return DbUsers.getUsers().get(index);
        }

        public static User getUser(String user) {
            return getUser(getIndex(user));
        }

        private static int getIndex(String user) {
            return users.indexOf(user);
        }

        public static List<String> getUsers() {
            return users;
        }

        private static void initialize() {
            for (User user : DbUsers.getUsers())
                users.add(user.getUsername());
        }

    }

    public static class MenuItems { //NOTE: Never Used

        private static List<String> items = new ArrayList<>();

        public static MenuItem getItem(int index) {
            return DbMenuItems.getMenuItems().get(index);
        }

        public static MenuItem getItem(String item) {
            return getItem(getIndex(item));
        }

        private static int getIndex(String item) {
            return items.indexOf(item);
        }

        private static void initialize() {
            for (MenuItem item : DbMenuItems.getMenuItems())
                items.add(item.getName());
        }

    }

    public static void initialize() {
         Users.initialize();
         MenuItems.initialize();
    }

}
