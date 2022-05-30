package rs.pingvin.d11;

import rs.pingvin.d11.database.Database;
import rs.pingvin.d11.view.Application;

public class Main {

    public static void main(String[] args) {
        Database.initialize();
        Application.run(args);
    }

}
