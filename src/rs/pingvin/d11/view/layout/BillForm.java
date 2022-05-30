package rs.pingvin.d11.view.layout;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import rs.pingvin.d11.database.DbCart;
import rs.pingvin.d11.model.MenuItem;
import rs.pingvin.d11.view.Application;
import rs.pingvin.d11.view.layout.config.Config;

class BillForm extends TilePane {

    private final Label labelUser          = new Label();
    private final Label labelTotal         = new Label("Total: ");
    private final Label labelDiscount      = new Label("Discount: ");
    private final Label labelSubTotal      = new Label("Sub Total: ");
    private final Label labelTotalValue    = new Label();
    private final Label labelDiscountValue = new Label();
    private final Label labelSubTotalValue = new Label();

    private final ListView<MenuItem> listItemsInCart = new ListView();

    private final Button buttonRemove = new Button("Remove item");
    private final Button buttonFinish = new Button("Finish order");

    BillForm() {
        configure();
        initialize();
        associate();
    }

    private void configure() {
        setAlignment(Pos.TOP_CENTER);

        labelUser.setFont(Config.HEADING_FONT);
        labelTotal.setFont(Config.REGULAR_FONT);
        labelDiscount.setFont(Config.REGULAR_FONT);
        labelSubTotal.setFont(Config.REGULAR_FONT);
        labelTotalValue.setFont(Config.REGULAR_FONT);
        labelDiscountValue.setFont(Config.REGULAR_FONT);
        labelSubTotalValue.setFont(Config.REGULAR_FONT);

        listItemsInCart.setPrefWidth(400);
        listItemsInCart.setCellFactory(param -> new ListCellFactory());

        buttonRemove.setFont(Config.REGULAR_FONT);
        buttonFinish.setFont(Config.REGULAR_FONT);

        listItemsInCart.getItems().addAll(FXCollections.observableArrayList(DbCart.getItems()));

        labelUser.setText(DbCart.getUser().getUsername() + "'s Bill");
        labelTotalValue.setText("$ " + Config.FORMAT(DbCart.getTotal()));
        labelDiscountValue.setText(DbCart.getDiscount() + " %");
        labelSubTotalValue.setText("$ " + Config.FORMAT(DbCart.getSubTotal()));
    }

    private void initialize() {
        VBox main = new VBox();

        VBox boxTop = new VBox();
        boxTop.setAlignment(Pos.CENTER);
        boxTop.setPadding(new Insets(10, 10, 15, 10));
        boxTop.setSpacing(10);

        GridPane gridStats = new GridPane();
        gridStats.setAlignment(Pos.CENTER_RIGHT);
        gridStats.setPadding(new Insets(10, 0, 0, 0));
        gridStats.setHgap(10);
        gridStats.setVgap(10);
        gridStats.add(labelTotal, 0, 0);
        gridStats.add(labelTotalValue, 1, 0);
        gridStats.add(labelDiscount, 0, 1);
        gridStats.add(labelDiscountValue, 1, 1);
        gridStats.add(labelSubTotal, 0, 2);
        gridStats.add(labelSubTotalValue, 1, 2);

        boxTop.getChildren().addAll(labelUser, listItemsInCart, buttonRemove, gridStats);

        VBox boxBottom = new VBox();
        boxBottom.setAlignment(Pos.CENTER_RIGHT);
        boxBottom.getChildren().add(buttonFinish);

        main.getChildren().addAll(boxTop, boxBottom);

        getChildren().add(main);
    }

    private void associate() {
        buttonRemove.setOnAction(event -> {
            DbCart.remove(listItemsInCart.getSelectionModel().getSelectedItem());
            listItemsInCart.getItems().clear();
            listItemsInCart.getItems().addAll(DbCart.getItems());

            labelTotalValue.setText("$ " + Config.FORMAT(DbCart.getTotal()));
            labelDiscountValue.setText(DbCart.getDiscount() + " %");
            labelSubTotalValue.setText("$ " + Config.FORMAT(DbCart.getSubTotal()));
        });

        buttonFinish.setOnAction(event -> {
            DbCart.checkout();
            Application.setForm(Form.menuForm());
        });
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
