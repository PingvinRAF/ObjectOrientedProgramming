package rs.pingvin.d11.view.form;

import javafx.scene.Scene;

public class Form {

    public static Scene logInForm() {
        return new Scene(new LogInForm(), 650, 400);
    }

    public static Scene registrationForm() {
        return new Scene(new RegisterForm(), 650, 450);
    }

    public static Scene menuForm() {
        return new Scene(new MenuForm(), 800, 450);
    }

    public static Scene billForm() {
        return new Scene(new BillForm(), 500, 650);
    }

    public static Scene ordersForm() {
        return new Scene(new OrdersForm(), 500, 650);

    }

}
