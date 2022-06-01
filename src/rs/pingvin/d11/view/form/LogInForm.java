package rs.pingvin.d11.view.form;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import rs.pingvin.d11.database.DbBridge;
import rs.pingvin.d11.database.DbCart;
import rs.pingvin.d11.model.User;
import rs.pingvin.d11.view.Application;
import rs.pingvin.d11.view.form.config.Config;


class LogInForm extends TilePane {

    private final Label labelLogIn    = new Label("Login Form");
    private final Label labelUsername = new Label("Username");
    private final Label labelPassword = new Label("Password");

    private TextField textFieldUsername         = new TextField();
    private PasswordField passwordFieldPassword = new PasswordField();

    private final Button buttonSubmit = new Button("Submit");

    private final Label     labelToRegister     = new Label("Don't have an account?");
    private final Hyperlink hyperlinkToRegister = new Hyperlink("Register");

    LogInForm() {
        configure();
        initialize();
        associate();
    }

    private void configure() {
        setAlignment(Pos.CENTER);
        setPadding(new Insets(0, 0, 60, 0));

        labelLogIn.setFont(Config.HEADING_FONT);
        labelUsername.setFont(Config.REGULAR_FONT);
        buttonSubmit.setFont(Config.REGULAR_FONT);
        buttonSubmit.setPadding(new Insets(6, 10, 6, 10));
    }

    private void initialize() {
        GridPane paneMain = new GridPane();
        paneMain.setHgap(20);
        paneMain.setVgap(10);

        HBox boxHeading = new HBox();
        boxHeading.setAlignment(Pos.CENTER);
        boxHeading.setPadding(new Insets(0, 0, 40, 0));
        boxHeading.getChildren().add(labelLogIn);

        HBox boxToRegister = new HBox();
        boxToRegister.setSpacing(10);
        boxToRegister.setAlignment(Pos.CENTER);
        boxToRegister.getChildren().addAll(labelToRegister, hyperlinkToRegister);

        paneMain.add(boxHeading, 0, 0, 2, 1);
        paneMain.add(labelUsername, 0, 1);
        paneMain.add(textFieldUsername, 1, 1);
        paneMain.add(labelPassword, 0, 2);
        paneMain.add(passwordFieldPassword, 1, 2);
        paneMain.add(buttonSubmit, 1, 3);
        paneMain.add(boxToRegister, 1, 4);

        getChildren().add(paneMain);
    }

    private void associate() {
        buttonSubmit.setOnAction(event -> {
            if (textFieldUsername.getText().isEmpty() || passwordFieldPassword.getText().isEmpty()) {
                new Alert(Alert.AlertType.INFORMATION, "All fields must be filled", ButtonType.OK).show();
                passwordFieldPassword.clear();
                return;

            }

            if (!DbBridge.Users.getUsers().contains(textFieldUsername.getText())) {
                new Alert(Alert.AlertType.INFORMATION, "The user with the given username does not exist", ButtonType.OK).show();
                passwordFieldPassword.clear();
                return;
            }

            User user = DbBridge.Users.getUser(textFieldUsername.getText());

            if (!user.getPassword().equals(passwordFieldPassword.getText())) {
                new Alert(Alert.AlertType.INFORMATION, "The password is incorrect", ButtonType.OK).show();
                passwordFieldPassword.clear();
                return;
            }
            DbCart.setUser(user);
            Application.setForm(Form.menuForm());
        });

        hyperlinkToRegister.setOnAction(event -> {
            Application.setForm(Form.registrationForm());
        });
    }

}
