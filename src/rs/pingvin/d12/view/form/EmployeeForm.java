package rs.pingvin.d12.view.form;

import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.TilePane;
import rs.pingvin.d12.database.Database;
import rs.pingvin.d12.view.Application;

class EmployeeForm extends TilePane {

    private Label labelFirstName = new Label("Ime");
    private Label labelLastName = new Label("Prezime");
    private Label labelId = new Label("JMBG");
    private Label labelProfession = new Label("Pozicija");
    private Label labelStarted = new Label("Godina zaposlenja");

    private TextField textFieldFirstName = new TextField();
    private TextField textFieldLastName = new TextField();
    private TextField textFieldId = new TextField();
    private TextField textFieldStarted = new TextField();

    private ComboBox<String> comboBoxProfession = new ComboBox<>();

    private Button buttonAdd = new Button("Dodaj");

    EmployeeForm() {
        configure();
        initialize();
        assiciate();
    }

    private void configure() {
        setAlignment(Pos.CENTER);

        comboBoxProfession.setItems(FXCollections.observableArrayList(Database.Profession.getProfessionNames()));
        comboBoxProfession.getSelectionModel().selectFirst();
    }

    private void initialize() {
        GridPane main = new GridPane();
        main.setVgap(10);
        main.setHgap(10);
        main.add(labelFirstName, 0, 0);
        main.add(textFieldFirstName, 1, 0);
        main.add(labelLastName, 0, 1);
        main.add(textFieldLastName, 1, 1);
        main.add(labelId, 0, 2);
        main.add(textFieldId, 1, 2);
        main.add(labelProfession, 0, 3);
        main.add(comboBoxProfession, 1, 3);
        main.add(labelStarted, 0, 4);
        main.add(textFieldStarted, 1, 4);
        main.add(buttonAdd, 0, 5);

        getChildren().add(main);
    }

    private void assiciate() {
        buttonAdd.setOnAction(event -> {
            Database.Employee.addEmployee(textFieldFirstName.getText(), textFieldLastName.getText(), textFieldId.getText(),
                                          comboBoxProfession.getSelectionModel().getSelectedItem(),textFieldStarted.getText());

            Application.setForm(Form.mainForm());
        });
    }

}
