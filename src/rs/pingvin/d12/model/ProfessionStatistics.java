package rs.pingvin.d12.model;

import java.text.DecimalFormat;

public class ProfessionStatistics implements Comparable<ProfessionStatistics> {

    private Profession profession;
    private double averageSalary;
    private int numberOfEmployees;

    public ProfessionStatistics(Profession profession) {
        this.profession = profession;
        averageSalary = 0;
        numberOfEmployees = 0;
    }

    public void addEmployee(Employee employee) {
        averageSalary += (employee.getSalary() - averageSalary) / ++numberOfEmployees;
    }

    public Profession getProfession() {
        return profession;
    }

    public double getAverageSalary() {
        return averageSalary;
    }

    public int getNumberOfEmployees() { //NOTE: Never Used
        return numberOfEmployees;
    }

    public double getTotal() {
        return averageSalary * numberOfEmployees;
    }

    @Override
    public String toString() {
        return profession + " (" + numberOfEmployees + ") - " + new DecimalFormat("0.00").format(averageSalary);
    }

    @Override
    public int compareTo(ProfessionStatistics statistics) {
        return Double.compare(statistics.getAverageSalary(), averageSalary);
    }

}
