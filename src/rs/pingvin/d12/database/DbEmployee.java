package rs.pingvin.d12.database;

import rs.pingvin.d12.model.Employee;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class DbEmployee {

    private static String fullFilePath = "resources/database/D12/zaposleni.txt";

    private static double baseSalary = -1;

    private static List<Employee> employees = new ArrayList<>();

    static void load() {
        try {
            Scanner scanner = new Scanner(new File(fullFilePath));

            String line;
            String profession = null;

            while (scanner.hasNext()) {
                if ((line = scanner.nextLine()).isEmpty())
                    continue;

                if (line.contains(",")) {
                    String[] attributes = line.split(",");
                    employees.add(new Employee(attributes[0], attributes[1], attributes[2], DbProfession.getProfession(profession), Integer.parseInt(attributes[3])));
                }
                else
                    profession = line;
            }

            scanner.close();
        }
        catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void calculateSalaries() {
        for (Employee employee : employees)
            employee.calculateSalary();
    }

    public static void addEmployee(String firstName, String lastName, String id, String profession, String started) {
        Employee employee = new Employee(firstName, lastName, id, DbProfession.getProfession(profession), Integer.parseInt(started));
        employee.calculateSalary();

        int index;
        for (index = 0; index < employees.size(); index++)
            if (employees.get(index).getProfession().equals(employee.getProfession()))
                break;

        employees.add(index, employee);
    }

    public static List<Employee> getEmployees() {
        return employees;
    }

    public static double getBaseSalary() {
        return baseSalary;
    }

    public static void setBaseSalary(String baseSalary) {
        DbEmployee.baseSalary = Double.parseDouble(baseSalary);
    }

    public static List<Employee> getEmployees(String profession, String filter) {
        List<Employee> list = getEmployees(filter.split(" "));

        list.retainAll(getEmployees(profession));

        return list;
    }

    private static List<Employee> getEmployees(String profession) {
        if (profession == "Sve pozicije")
            return getEmployees();

        List<Employee> list = new ArrayList<>();

        for (Employee employee : employees)
            if (employee.getProfession().getName().equals(profession))
                list.add(employee);

        return list;
    }

    private static List<Employee> getEmployees(String[] filter) {
        List<Employee> list = new ArrayList<>();

        if (filter.length > 2)
            return list;

        for (Employee employee : employees)
            if (employee.containts(filter))
                list.add(employee);

        return list;
    }

    public static void save() {
        try {
            FileWriter fileWriter = new FileWriter(fullFilePath);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            String profession = "";

            for (Employee employee : employees) {
                if (!profession.equals(employee.getProfession().getName())) {
                    if (!profession.isEmpty())
                        bufferedWriter.newLine();

                    bufferedWriter.write(profession = employee.getProfession().getName());
                    bufferedWriter.newLine();
                }

                bufferedWriter.write(employee.toString());
                bufferedWriter.newLine();
            }

            bufferedWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
