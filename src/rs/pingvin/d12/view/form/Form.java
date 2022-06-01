package rs.pingvin.d12.view.form;

import javafx.scene.Scene;

public class Form {

    public static Scene mainForm() {
        return new Scene(new MainForm(), 600, 450);
    }

    public static Scene employeeForm() {
        return new Scene(new EmployeeForm(), 600, 450);
    }

    public static Scene statisticsForm() {
        return new Scene(new StatisticsForm(), 600, 450);
    }

}
