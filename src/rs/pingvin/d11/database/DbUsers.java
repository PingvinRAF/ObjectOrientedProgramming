package rs.pingvin.d11.database;

import rs.pingvin.d11.model.User;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DbUsers {

    private static final String fullFilePath = "resources/database/D11/users.txt";

    private static List<User> users = new ArrayList<>();

    public static void load() {
        try {
            Scanner scanner = new Scanner(new File(fullFilePath));

            while (scanner.hasNext()) {
                String[] attributes = scanner.nextLine().split(" ");
                users.add(new User(attributes[0], attributes[1], attributes[2], attributes[3]));
            }

            scanner.close();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static List<User> getUsers() {
        return users;
    }

    public static void addToDatebase(User user) {
        users.add(user);
        DbBridge.Users.getUsers().add(user.getUsername());

        try {
            FileWriter fileWriter = new FileWriter(fullFilePath, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            bufferedWriter.newLine();
            bufferedWriter.write(user.toString());

            bufferedWriter.close();
        }
        catch (IOException e) {
            System.out.println("The given path is a directory.");
        }
    }

}
