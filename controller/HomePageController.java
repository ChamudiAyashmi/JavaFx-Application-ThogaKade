package controller;

import db.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class HomePageController implements Initializable {
    public Label lblCustomerCount;
    public Label lblItemCount;
    public Label lblOderCount;
    public void setCounts(){
        String SQL1="Select Count(*) AS customerCount From Customer";
        String SQL2="Select Count(*) AS itemCount From Item";
        String SQL3="Select Count(*) AS orderCount From Orders";

        Connection connection = null;
        try {
            connection = DBConnection.getInstance().getConnection();
            PreparedStatement pstm1 = connection.prepareStatement(SQL1);
            ResultSet rst1 = pstm1.executeQuery();
            int customerCount=0;
            while(rst1.next()){
                customerCount = rst1.getInt("customerCount");
            }
            lblCustomerCount.setText(String.valueOf(customerCount));
            rst1.beforeFirst();

            PreparedStatement pstm2 = connection.prepareStatement(SQL2);
            ResultSet rst2 = pstm2.executeQuery();
            int itemCount = 0;
            if (rst2.next()) {
                itemCount = rst2.getInt("itemCount");
            }
            lblItemCount.setText(String.valueOf(itemCount));

            PreparedStatement pstm3 = connection.prepareStatement(SQL3);
            ResultSet rst3 = pstm3.executeQuery();
            int orderCount = 0;
            if (rst3.next()) {
                orderCount = rst3.getInt("orderCount");
            }
            lblOderCount.setText(String.valueOf(orderCount));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setCounts();
    }
}
