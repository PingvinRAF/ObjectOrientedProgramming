package rs.pingvin.d12;

import rs.pingvin.d12.database.Database;
import rs.pingvin.d12.view.Application;

public class Main {

    public static void main(String[] args) {
        Database.load();
        Application.run(args);
    }

}
