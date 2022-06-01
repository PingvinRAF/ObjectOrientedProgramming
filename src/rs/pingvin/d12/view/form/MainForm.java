package rs.pingvin.d12.view.form;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import rs.pingvin.d12.database.Database;
import rs.pingvin.d12.model.Employee;
import rs.pingvin.d12.view.Application;

class MainForm extends TilePane {

    private Label labelBaseSalary = new Label("Cena rada: ");
    private Label labelFilter = new Label("Filter");
    private Label labelProfession = new Label("Pozicija:");

    private TextField textFieldBaseSalary = new TextField();
    private TextField textFieldFilter = new TextField();

    private Button buttonCalculate = new Button("Obracunaj");
    private Button buttonFilter = new Button("Filtriraj");
    private Button buttonAddEmployee = new Button("Dodaj zaposlenog");
    private Button buttonSave = new Button("Snimi");
    private Button buttonStatistics = new Button("Statistika");

    private ComboBox<String> comboBoxProfessions = new ComboBox<>();

    private TableView<Employee> tableEmployees = new TableView();
    private TableColumn<Employee, String> columnFirstName = new TableColumn("Ime");
    private TableColumn<Employee, String> columnLastName = new TableColumn("Prezime");
    private TableColumn<Employee, String> columnId = new TableColumn("JMBG");
    private TableColumn<Employee, String> columnProfession = new TableColumn("Pozicija");
    private TableColumn<Employee, String> columnSalary = new TableColumn("Plata");

    MainForm() {
        configure();
        initialize();
        assiciate();
    }

    private void configure() {
        setAlignment(Pos.CENTER);

        tableEmployees.setPrefWidth(580);

        columnFirstName.setPrefWidth(100);
        columnLastName.setPrefWidth(120);
        columnId.setPrefWidth(125);
        columnProfession.setPrefWidth(100);
        columnSalary.setPrefWidth(120);

        comboBoxProfessions.setItems(FXCollections.observableArrayList(Database.Profession.getProfessionNames()));
        comboBoxProfessions.getItems().add(0, "Sve pozicije");
        comboBoxProfessions.getSelectionModel().selectFirst();

        columnFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        columnLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        columnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnProfession.setCellValueFactory(new PropertyValueFactory<>("profession"));
        columnSalary.setCellValueFactory(new PropertyValueFactory<>("SalaryFormated"));

        tableEmployees.getColumns().addAll(columnFirstName, columnLastName, columnId, columnProfession, columnSalary);
    }

    private void initialize() {
        VBox main = new VBox();

        HBox boxCalculate = new HBox();
        boxCalculate.setAlignment(Pos.CENTER);
        boxCalculate.setPadding(new Insets(10));
        boxCalculate.setSpacing(10);
        boxCalculate.getChildren().addAll(labelBaseSalary, textFieldBaseSalary, buttonCalculate);

        VBox boxTable = new VBox();
        boxTable.setAlignment(Pos.CENTER);
        boxTable.setPadding(new Insets(0, 10, 0, 10));
        boxTable.setSpacing(10);
        boxTable.getChildren().addAll(tableEmployees);

        HBox boxFilter = new HBox();
        boxFilter.setAlignment(Pos.CENTER);
        boxFilter.setPadding(new Insets(10));
        boxFilter.setSpacing(10);
        boxFilter.getChildren().addAll(labelFilter, textFieldFilter, labelProfession, comboBoxProfessions, buttonFilter);

        HBox boxOther = new HBox();
        boxOther.setAlignment(Pos.CENTER);
        boxOther.setPadding(new Insets(10));
        boxOther.setSpacing(10);
        boxOther.getChildren().addAll(buttonAddEmployee, buttonSave, buttonStatistics);

        main.getChildren().addAll(boxCalculate, boxTable, boxFilter, boxOther);

        getChildren().add(main);
    }

    private void assiciate() {
        buttonCalculate.setOnAction(event -> {
            Database.Employee.setBaseSalary(textFieldBaseSalary.getText());
            Database.Employee.calculateSalaries();
            Database.Statistics.calculate();

            tableEmployees.getItems().clear();
            tableEmployees.setItems(FXCollections.observableArrayList(Database.Employee.getEmployees()));

        });

        buttonFilter.setOnAction(event ->  {
            tableEmployees.getItems().clear();
            tableEmployees.setItems(FXCollections.observableArrayList(Database.Employee.getEmployees(comboBoxProfessions.getSelectionModel().getSelectedItem(), textFieldFilter.getText())));
        });

        buttonAddEmployee.setOnAction(event -> Application.setForm(Form.employeeForm()));

        buttonSave.setOnAction(event -> Database.Employee.save());

        buttonStatistics.setOnAction(event -> Application.setForm(Form.statisticsForm()));

        comboBoxProfessions.setOnAction(buttonFilter.getOnAction());

        if (Database.Employee.getBaseSalary() >= 0) {
            Database.Employee.calculateSalaries();
            Database.Statistics.calculate();

            tableEmployees.getItems().clear();
            tableEmployees.setItems(FXCollections.observableArrayList(Database.Employee.getEmployees()));
        }
    }

}
