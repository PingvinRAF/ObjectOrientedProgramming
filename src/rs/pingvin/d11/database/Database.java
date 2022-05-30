package rs.pingvin.d11.database;

public class Database {

    public static void initialize() {
        DbOrders.load();
        DbUsers.load();
        DbMenuItems.load();
        DbBridge.initialize();
    }

}
