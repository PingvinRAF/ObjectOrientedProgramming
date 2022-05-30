package rs.pingvin.d11.view.layout;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import rs.pingvin.d11.database.DbBridge;
import rs.pingvin.d11.database.DbCart;
import rs.pingvin.d11.database.DbUsers;
import rs.pingvin.d11.model.User;
import rs.pingvin.d11.view.Application;
import rs.pingvin.d11.view.layout.config.Config;

class RegisterForm extends TilePane {

    private final Label labelRegistration    = new Label("Registration Form");
    private final Label labelName            = new Label("Name");
    private final Label labelSurname         = new Label("Surname");
    private final Label labelUsername        = new Label("Username");
    private final Label labelPassword        = new Label("Password");
    private final Label labelConfirmPassword = new Label("Confirm password");

    private TextField textFieldName                    = new TextField();
    private TextField textFieldSurname                 = new TextField();
    private TextField textFieldUsername                = new TextField();
    private PasswordField passwordFieldPassword        = new PasswordField();
    private PasswordField passwordFieldConfirmPassword = new PasswordField();

    private final Button buttonSubmit = new Button("Submit");

    RegisterForm() {
        configure();
        initialize();
        associate();
    }

    private void configure() {
        setAlignment(Pos.CENTER);
        setPadding(new Insets(0, 0, 60, 0));

        labelRegistration.setFont(Config.HEADING_FONT);
        labelName.setFont(Config.REGULAR_FONT);
        labelSurname.setFont(Config.REGULAR_FONT);
        labelUsername.setFont(Config.REGULAR_FONT);
        labelPassword.setFont(Config.REGULAR_FONT);
        labelConfirmPassword.setFont(Config.REGULAR_FONT);
        buttonSubmit.setFont(Config.REGULAR_FONT);
        buttonSubmit.setPadding(new Insets(6, 10, 6, 10));

        textFieldName.setPrefWidth(186.0);
        textFieldSurname.setPrefWidth(186.0);
        textFieldUsername.setPrefWidth(186.0);
        passwordFieldPassword.setPrefWidth(186.0);
        passwordFieldConfirmPassword.setPrefWidth(186.0);
    }

    private void initialize() {
        GridPane paneMain = new GridPane();
        paneMain.setHgap(20);
        paneMain.setVgap(8);

        HBox boxHeading = new HBox();
        boxHeading.setAlignment(Pos.CENTER);
        boxHeading.setPadding(new Insets(0, 0, 40, 0));
        boxHeading.getChildren().add(labelRegistration);

        paneMain.add(boxHeading, 0, 0, 2, 1);
        paneMain.add(labelName, 0, 1);
        paneMain.add(textFieldName, 1, 1);
        paneMain.add(labelSurname, 0, 2);
        paneMain.add(textFieldSurname, 1, 2);
        paneMain.add(labelUsername, 0, 3);
        paneMain.add(textFieldUsername, 1, 3);
        paneMain.add(labelPassword, 0, 4);
        paneMain.add(passwordFieldPassword, 1, 4);
        paneMain.add(labelConfirmPassword, 0, 5);
        paneMain.add(passwordFieldConfirmPassword, 1, 5);
        paneMain.add(buttonSubmit, 1, 6);

        getChildren().add(paneMain);
    }

    private void associate() {
        buttonSubmit.setOnAction(event -> {
            if (DbBridge.Users.getUsers().contains(textFieldUsername)) {
                new Alert(Alert.AlertType.INFORMATION, "Username you entered already exists", ButtonType.OK).show();
                passwordFieldPassword.clear();
                passwordFieldConfirmPassword.clear();
                return;
            }

            if (passwordFieldPassword.getText().length() < 5) {
                new Alert(Alert.AlertType.INFORMATION, "Password must be at least 5 characters long", ButtonType.OK).show();
                passwordFieldPassword.clear();
                passwordFieldConfirmPassword.clear();
                return;
            }

            if (!passwordFieldPassword.getText().equals(passwordFieldConfirmPassword.getText())) {
                new Alert(Alert.AlertType.INFORMATION, "Passwords do not match", ButtonType.OK).show();
                passwordFieldPassword.clear();
                passwordFieldConfirmPassword.clear();
                return;
            }

            User user = new User(textFieldName.getText(), textFieldSurname.getText(), textFieldUsername.getText(), passwordFieldPassword.getText());

            DbUsers.addToDatebase(user);
            DbCart.setUser(user);
            Application.setForm(Form.menuForm());
        });
    }

}
