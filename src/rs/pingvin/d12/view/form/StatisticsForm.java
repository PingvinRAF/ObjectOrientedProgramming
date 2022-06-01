package rs.pingvin.d12.view.form;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import rs.pingvin.d12.database.Database;
import rs.pingvin.d12.model.ProfessionStatistics;

import java.text.DecimalFormat;

class StatisticsForm extends TilePane {

    private ListView<ProfessionStatistics> listStatistics = new ListView<>();
    private Label labelTotalMonthlySalary = new Label();
    private Label labelNumberOfEmployees = new Label();
    private Label labelAverageSalary = new Label();

    StatisticsForm() {
        configure();
        initialize();
    }

    private void configure() {
        setAlignment(Pos.CENTER);

        listStatistics.setPrefWidth(580);

        listStatistics.setItems(FXCollections.observableArrayList(Database.Statistics.getStatistics()));

        labelTotalMonthlySalary.setText("Ukupni mesecni izdatak po osnovu zarada: " + new DecimalFormat("0.00").format(Database.Statistics.getTotalSalary()));
        labelNumberOfEmployees.setText("Broj zaposlenih: " + Database.Employee.getEmployees().size());
        labelAverageSalary.setText("Prosecna zarada po zaposlenom: " + new DecimalFormat("0.00").format(Database.Statistics.getAverageSalary()));
    }


    private void initialize() {
        VBox main = new VBox();
        main.setAlignment(Pos.CENTER_LEFT);
        main.setPadding(new Insets(10));
        main.setSpacing(10);

        main.getChildren().addAll(listStatistics, labelTotalMonthlySalary, labelNumberOfEmployees, labelAverageSalary);

        getChildren().add(main);
    }

}
