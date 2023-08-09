package controller;

import db.DBConnection;
import model.Order;
import model.OrderDetails;

import java.sql.*;

public class OrderController {
    public static String getLastOrderId() throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT id FROM Orders ORDER BY id DESC LIMIT 1");
        return resultSet.next() ? resultSet.getString("id") : null;

    }

    public static boolean placeOrder(Order order) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement("Insert into Orders values (?,?,?)");
        pstm.setObject(1,order.getOrderId());
        pstm.setObject(2,order.getDate());
        pstm.setObject(3,order.getCustomerId());
        boolean orderIsAdded= pstm.executeUpdate()>0;
        if(orderIsAdded){
            boolean orderDetailsAdded= OrderDetailsController.addOrderDetails(order.getOrderDetails());
            if (orderDetailsAdded){
                boolean isUpdateItem= ItemController.updateStock(order.getOrderDetails());
                if (isUpdateItem){
                    return true;
                }
            }
        }
        return false;
    }

}
