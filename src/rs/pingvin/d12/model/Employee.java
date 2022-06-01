package rs.pingvin.d12.model;

import rs.pingvin.d12.database.Database;

import java.text.DecimalFormat;
import java.time.Year;
import java.util.Objects;

public class Employee {

    private String firstName;
    private String lastName;
    private String id;
    private Profession profession;
    private int started;
    private double salary;

    public Employee(String firstName, String lastName, String id, Profession profession, int started) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.id = id;
        this.profession = profession;
        this.started = started;
    }

    public String getFirstName() { //NOTE: Used by javafx
        return firstName;
    }

    public String getLastName() { //NOTE: Used by javafx
        return lastName;
    }

    public String getId() { //NOTE: Used by javafx
        return id;
    }

    public Profession getProfession() { //NOTE: Used by javafx
        return profession;
    }

    public int getStarted() { //NOTE: Never Used
        return started;
    }

    public double getSalary() {
        return salary;
    }

    public String getSalaryFormated() { //NOTE: Used by javafx
        return new DecimalFormat("0.00").format(salary);
    }

    public void calculateSalary() {
        salary = Database.Employee.getBaseSalary() * (profession.getCoefficient() + 0.05 * (Year.now().getValue() - started));
    }

    public boolean containts(String[] text) {
        return (firstName.toLowerCase().contains(text[0].toLowerCase()) || lastName.toLowerCase().contains(text[0].toLowerCase())) &&
                (!(text.length == 2) || firstName.toLowerCase().contains(text[1].toLowerCase()) || lastName.toLowerCase().contains(text[1].toLowerCase()));
    }

    @Override
    public String toString() {
        return firstName + "," + lastName + "," + id + "," + started;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof Employee))
            return false;

        Employee employee = (Employee) obj;
        return Objects.equals(id, employee.id);
    }

}
