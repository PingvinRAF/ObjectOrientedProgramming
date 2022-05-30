package rs.pingvin.d11.view.layout;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import rs.pingvin.d11.database.DbCart;
import rs.pingvin.d11.database.DbMenuItems;
import rs.pingvin.d11.model.Category;
import rs.pingvin.d11.model.DrinkItem;
import rs.pingvin.d11.model.FoodItem;
import rs.pingvin.d11.view.Application;
import rs.pingvin.d11.view.layout.config.Config;

import java.util.ArrayList;
import java.util.List;

class MenuForm extends TilePane {

    private final Label labelFilter        = new Label("Filter by");
    private final Label labelDrinksHeading = new Label("Drinks");
    private final Label labelSelectedItems = new Label("0 items selected");

    private final Button buttonFinish      = new Button("Finish");
    private final Button buttonSearch      = new Button("Search");
    private final Button buttonAddFood     = new Button("Add");
    private final Button buttonAddDrink    = new Button("Add drink");
    private final Button buttonUserHistory = new Button("Orders");

    private final ComboBox<String> comboBoxFilter = new ComboBox();

    private final TextField textFieldFilter = new TextField();

    private final ListView<DrinkItem> listDrinkMenu = new ListView();

    private final TableView<FoodItem> tableFoodMenu = new TableView();
    private final TableColumn<FoodItem, String> tableColumnCategory = new TableColumn<>("Category");
    private final TableColumn<FoodItem, String> tableColumnItemName = new TableColumn<>("Menu Item");
    private final TableColumn<FoodItem, String> tableColumnPrice = new TableColumn<>("Price $");
    private final TableColumn<FoodItem, String> tableColumnIngredients = new TableColumn<>("Ingredients");

    MenuForm() {
        configure();
        initialize();
        associate();
    }

    private void configure() {
        setAlignment(Pos.TOP_LEFT);

        labelFilter.setFont(Config.REGULAR_FONT);
        labelDrinksHeading.setFont(Config.HEADING_FONT);
        labelSelectedItems.setFont(Config.MEDIUM_FONT);

        buttonFinish.setFont(Config.REGULAR_FONT);
        buttonSearch.setFont(Config.REGULAR_FONT);
        buttonAddFood.setFont(Config.REGULAR_FONT);
        buttonAddDrink.setFont(Config.REGULAR_FONT);
        buttonUserHistory.setFont(Config.REGULAR_FONT);

        labelSelectedItems.setTextFill(Color.SKYBLUE);

        comboBoxFilter.getItems().addAll(comboBoxCategories());
        comboBoxFilter.getSelectionModel().selectFirst();
        comboBoxFilter.setCellFactory(param -> new ListCellFactory());
        comboBoxFilter.setButtonCell(new ListCellFactory());

        textFieldFilter.setFont(Config.REGULAR_FONT);

        listDrinkMenu.setCellFactory(param -> new ListCellFactory());
        listDrinkMenu.getItems().addAll(FXCollections.observableArrayList(DbMenuItems.getDrinkItems()));

        tableColumnCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        tableColumnItemName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableColumnPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        tableColumnIngredients.setCellValueFactory(new PropertyValueFactory<>("ingredients"));

        listDrinkMenu.setPrefHeight(150);
        listDrinkMenu.setPrefWidth(160);

        tableFoodMenu.setPrefHeight(380);

        tableColumnCategory.setPrefWidth(90);
        tableColumnItemName.setPrefWidth(150);
        tableColumnPrice.setPrefWidth(80);
        tableColumnIngredients.setPrefWidth(290);

        tableFoodMenu.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableFoodMenu.getColumns().addAll(tableColumnCategory, tableColumnItemName, tableColumnPrice, tableColumnIngredients);
        tableFoodMenu.setItems(FXCollections.observableArrayList(DbMenuItems.getFoodItems()));
    }

    private void initialize() {
        BorderPane main = new BorderPane();

        HBox boxTop = new HBox();
        boxTop.setAlignment(Pos.CENTER_LEFT);
        boxTop.setPadding(new Insets(10, 10, 5, 10));
        boxTop.setSpacing(10);
        boxTop.getChildren().addAll(buttonUserHistory, labelFilter, comboBoxFilter, textFieldFilter, buttonSearch);

        VBox boxLeft = new VBox();
        boxLeft.setAlignment(Pos.CENTER_RIGHT);
        boxLeft.setPadding(new Insets(5, 5, 5, 10));
        boxLeft.setSpacing(10);
        boxLeft.getChildren().addAll(tableFoodMenu, buttonAddFood);

        VBox boxRight = new VBox();
        boxRight.setAlignment(Pos.TOP_CENTER);
        boxRight.setPadding(new Insets(5, 10, 5, 5));
        boxRight.setSpacing(10);
        boxRight.getChildren().addAll(labelDrinksHeading, listDrinkMenu, buttonAddDrink);

        HBox boxBottom = new HBox();
        boxBottom.setAlignment(Pos.CENTER_RIGHT);
        boxBottom.setPadding(new Insets(5, 10, 10, 10));
        boxBottom.setSpacing(10);
        boxBottom.getChildren().addAll(labelSelectedItems, buttonFinish);

        main.setTop(boxTop);
        main.setLeft(boxLeft);
        main.setRight(boxRight);
        main.setBottom(boxBottom);

        BorderPane.setAlignment(boxTop, Pos.CENTER_LEFT);
        getChildren().add(main);
    }

    private void associate() {
        comboBoxFilter.setOnAction(event -> {
            tableFoodMenu.getItems().clear();
            tableFoodMenu.getItems().addAll(DbMenuItems.getFoodItems(Category.valueOf(comboBoxFilter.getSelectionModel().getSelectedItem()), textFieldFilter.getText()));
        });

        buttonUserHistory.setOnAction(event -> {
            Application.setForm(Form.ordersForm());
        });

        buttonSearch.setOnAction(event -> {
            tableFoodMenu.getItems().clear();
            tableFoodMenu.getItems().addAll(DbMenuItems.getFoodItems(Category.valueOf(comboBoxFilter.getSelectionModel().getSelectedItem()), textFieldFilter.getText()));
        });

        buttonAddFood.setOnAction(event -> {
            DbCart.add(tableFoodMenu.getSelectionModel().getSelectedItem());
            labelSelectedItems.setText(DbCart.getSize() + " items selected");
        });

        buttonAddDrink.setOnAction(event -> {
            DbCart.add(listDrinkMenu.getSelectionModel().getSelectedItem());
            labelSelectedItems.setText(DbCart.getSize() + " items selected");
        });

        buttonFinish.setOnAction(event -> {
            Application.setForm(Form.billForm());
        });
    }

    private List<String> comboBoxCategories() {
        List<String> categories = new ArrayList<>();

        for (Category category: Category.values())
            if (!category.equals(Category.DRINKS))
                categories.add(category.toString());

        return categories;
    }

    private static final class ListCellFactory<T> extends ListCell<T> {
        @Override
        protected void updateItem(T item, boolean empty) {
            super.updateItem(item, empty);

            if (empty || item == null) {
                setText(null);
                setGraphic(null);
                return;
            }

            setText(item.toString());
            setFont(Config.REGULAR_FONT);
        }
    }

}
