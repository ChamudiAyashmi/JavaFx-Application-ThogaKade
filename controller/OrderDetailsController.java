package controller;

import db.DBConnection;
import model.OrderDetails;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDetailsController {
    public static boolean addOrderDetails(ArrayList<OrderDetails> orderDetaillist) throws SQLException, ClassNotFoundException {
        for (OrderDetails orderDetail:orderDetaillist) {
            boolean isAdded=addOrderDetails(orderDetail);
            if (!isAdded){
                return false;
            }
        }
        return true;
    }
    public static boolean addOrderDetails(OrderDetails orderDetails) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement("Insert into orderDetail values (?,?,?,?)");
        pstm.setObject(1,orderDetails.getOrderId());
        pstm.setObject(2,orderDetails.getItemCode());
        pstm.setObject(3,orderDetails.getQty());
        pstm.setObject(4,orderDetails.getUnitPrice());

        return pstm.executeUpdate()>0;
    }
}
