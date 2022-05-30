package rs.pingvin.d11.view;

import javafx.scene.Scene;
import javafx.stage.Stage;
import rs.pingvin.d11.view.layout.Form;

public class Application extends javafx.application.Application {

    private static Stage window;

    public static void run(String... args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        window = stage;
        setForm(Form.logInForm());
        window.show();
    }

    public static void setForm(Scene form) {
        window.setScene(form);
        window.centerOnScreen();
    }

}
