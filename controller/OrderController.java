package controller;

import db.DBConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class OrderController {
    public static String getLastOrderId() throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT id FROM Orders ORDER BY id DESC LIMIT 1");
        return resultSet.next() ? resultSet.getString("id") : null;

    }
}
