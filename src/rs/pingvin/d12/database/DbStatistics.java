package rs.pingvin.d12.database;

import rs.pingvin.d12.model.Employee;
import rs.pingvin.d12.model.Profession;
import rs.pingvin.d12.model.ProfessionStatistics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class DbStatistics {

    private static List<ProfessionStatistics> statistics = new ArrayList<>();

    static void initialize() {
        statistics.clear();
        for (Profession profession : DbProfession.getProfessions())
            statistics.add(new ProfessionStatistics(profession));
    }

    public static void calculate() {
        for (Employee employee : DbEmployee.getEmployees()) {
            getProfessionStatistics(employee).addEmployee(employee);
        }

        Collections.sort(statistics);
    }

    private static ProfessionStatistics getProfessionStatistics(Employee employee) {
        for (ProfessionStatistics professionStatistics : statistics)
            if (professionStatistics.getProfession().equals(employee.getProfession()))
                return professionStatistics;

        return null;
    }

    public static List<ProfessionStatistics> getStatistics() {
        return statistics;
    }

    public static double getTotalSalary() {
        double total = 0;

        for (ProfessionStatistics professionStatistics : statistics)
            total += professionStatistics.getTotal();

        return total;
    }

    public static double getAverageSalary() {
        double sum = 0;

        for (Employee employee : DbEmployee.getEmployees())
            sum += employee.getSalary();

        return sum / DbEmployee.getEmployees().size();
    }

}
