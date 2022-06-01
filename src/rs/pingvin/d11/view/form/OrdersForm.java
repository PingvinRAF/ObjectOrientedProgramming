package rs.pingvin.d11.view.form;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import rs.pingvin.d11.database.DbCart;
import rs.pingvin.d11.database.DbOrders;
import rs.pingvin.d11.model.Order;
import rs.pingvin.d11.view.Application;
import rs.pingvin.d11.view.form.config.Config;

class OrdersForm extends TilePane {

    private final Label labelUser = new Label();

    private final ScrollPane scrollOrders = new ScrollPane();

    private final VBox boxOrders = new VBox();

    private final Button buttonBack = new Button("Back");

    OrdersForm() {
        configure();
        initialize();
        associate();
    }

    private void configure() {
        setAlignment(Pos.TOP_CENTER);

        labelUser.setFont(Config.HEADING_FONT);
        buttonBack.setFont(Config.REGULAR_FONT);

        scrollOrders.setPrefWidth(480);
        scrollOrders.setFitToWidth(true);
        scrollOrders.setContent(boxOrders);

        boxOrders.setAlignment(Pos.CENTER_RIGHT);
        boxOrders.setPadding(new Insets(10));
        boxOrders.setSpacing(10);

        addOrders();

        labelUser.setText(DbCart.getUser().getUsername() + "'s Orders");
    }

    private void initialize() {
        VBox main = new VBox();
        main.setAlignment(Pos.TOP_CENTER);
        main.setPadding(new Insets(10));
        main.setSpacing(10);

        main.getChildren().addAll(labelUser, scrollOrders, buttonBack);

        getChildren().add(main);
    }

    private void associate() {

        buttonBack.setOnAction(event -> {
            Application.setForm(Form.menuForm());
        });
    }

    private void addOrders() {
        for (Order order : DbOrders.getOrders(DbCart.getUser()))
            boxOrders.getChildren().add(addOrder(order));
    }

    private Node addOrder(Order order) {
        VBox boxOrder = new VBox();
        boxOrder.setAlignment(Pos.CENTER_RIGHT);
        boxOrder.setPadding(new Insets(10));
        boxOrder.setSpacing(15);

        ListView<String> listOrder = new ListView();
        listOrder.setCellFactory(param -> new ListCellFactory());
        listOrder.getItems().addAll(order.getItems());

        Label labelPrice = new Label("Price: $ " + Config.FORMAT(order.getPrice()));

        boxOrder.getChildren().addAll(listOrder, labelPrice);

        return boxOrder;
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
