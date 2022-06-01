package rs.pingvin.d12.database;

public class Database {

    public static void load() {
        DbProfession.load();
        DbEmployee.load();
        DbStatistics.initialize();
    }

    public static class Profession extends DbProfession { }

    public static class Employee extends DbEmployee { }

    public static class Statistics extends DbStatistics { }

}
