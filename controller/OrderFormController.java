package controller;

import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Customer;
import model.Order;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class OrderFormController implements Initializable {

    public TableView tblOrders;
    public TableColumn colOrderId;
    public TableColumn colCustomerID;
    public TableColumn colDate;

    public void viewTable() throws SQLException, ClassNotFoundException {
        colOrderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colCustomerID.setCellValueFactory(new PropertyValueFactory<>("customerId"));

        ObservableList<Order> list = FXCollections.observableArrayList();
        String SQL = "select * from orders";
        Connection connection = DBConnection.getInstance().getConnection();
        Statement stm = connection.createStatement();
        ResultSet rst = stm.executeQuery(SQL);
        while (rst.next()){
            Order order = new Order(rst.getString(1),rst.getString(2),rst.getString(3));
            list.add(order);
        }
        tblOrders.setItems(list);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            viewTable();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        tblOrders.getSelectionModel().selectedItemProperty().addListener((observable,
                                                                            oldValue,
                                                                            newValue) -> {

        });
    }
}
