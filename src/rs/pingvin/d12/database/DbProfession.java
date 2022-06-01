package rs.pingvin.d12.database;

import rs.pingvin.d12.model.Profession;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class DbProfession {

    private static String fullFilePath = "resources/database/D12/zanimanja.txt";

    private static List<Profession> professions = new ArrayList<>();

    static void load() {
        try {
            Scanner scanner = new Scanner(new File(fullFilePath));

            while (scanner.hasNext()) {
                String[] attributes = scanner.nextLine().split(",");

                if (attributes[0].isEmpty())
                    continue;

                professions.add(new Profession(attributes[0], Double.parseDouble(attributes[1])));
            }

            scanner.close();
        }
        catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    static Profession getProfession(String name) {
        for (Profession profession : professions)
            if (profession.getName().equals(name))
                return profession;

        return null;
    }

    public static List<Profession> getProfessions() {
        return professions;
    }

    public static List<String> getProfessionNames() {
        List<String> names = new ArrayList<>();

        for (Profession profession : professions)
            names.add(profession.getName());

        return names;
    }

}
