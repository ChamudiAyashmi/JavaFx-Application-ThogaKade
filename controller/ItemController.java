package controller;

import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Customer;
import model.Item;

import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class ItemController implements Initializable {
    public TextField txtCode;
    public TextField txtDescription;
    public TextField txtUnitPrice;
    public TextField txtQtyOnHand;
    public TableView tblItem;
    public TableColumn colCode;
    public TableColumn colDescription;
    public TableColumn colQtyOnHand;
    public TableColumn colUnitPrice;
    public void txtCodeOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        btnSearchOnAction(actionEvent);

    }
    public void btnAddOnAction(ActionEvent actionEvent) {
        try{
            Item item = new Item(txtCode.getText(),txtDescription.getText(), Integer.parseInt(txtQtyOnHand.getText()), Double.parseDouble(txtUnitPrice.getText()));

            try {
                Optional<ButtonType> buttonType = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to add this Item?", ButtonType.YES, ButtonType.NO).showAndWait();

                if (buttonType.get() == ButtonType.YES) {
                    Connection connection = DBConnection.getInstance().getConnection();
                    PreparedStatement pstm = connection.prepareStatement("INSERT INTO item VALUES (?,?,?,?)");
                    pstm.setObject(1, item.getCode());
                    pstm.setObject(2, item.getDescription());
                    pstm.setObject(3, item.getUnitPrice());
                    pstm.setObject(4, item.getQtyOnHand());

                    if (pstm.executeUpdate() > 0) {
                        new Alert(Alert.AlertType.INFORMATION, "Item Added !").show();
                        viewTable();
                        clear();
                    } else {
                        new Alert(Alert.AlertType.ERROR, "Something went wrong !").show();
                    }
                }
            } catch (ClassNotFoundException | SQLException exception) {
                exception.printStackTrace();
                new Alert(Alert.AlertType.ERROR, exception.getMessage()).show();
            }
        }catch (NumberFormatException ex){
            new Alert(Alert.AlertType.ERROR, "Added Failed !").show();
        }

    }
    public void btnSearchOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String SQL="Select * From item where code= '"+txtCode.getText()+"'";
        Connection connection = DBConnection.getInstance().getConnection();
        Statement statement=connection.createStatement();
        ResultSet rst=statement.executeQuery(SQL);
        if(rst.next()){
            Item item= new Item(txtCode.getText(),rst.getString("description"),rst.getInt("unitPrice"),rst.getDouble("qtyOnHand"));
            txtDescription.setText(item.getDescription());
            txtUnitPrice.setText(item.getUnitPrice()+"");
            txtQtyOnHand.setText(item.getQtyOnHand()+"");
        }else {
            new Alert(Alert.AlertType.ERROR, "Item not found !").show();
        }
    }
    public void btnUpdateOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String SQL="Update Item set description=?,unitPrice=?,qtyOnHand=? where code=?";
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(SQL);

        preparedStatement.setObject(1,txtDescription.getText());
        preparedStatement.setObject(2,txtUnitPrice.getText());
        preparedStatement.setObject(3,txtQtyOnHand.getText());
        preparedStatement.setObject(4,txtCode.getText());

        if (preparedStatement.executeUpdate()>0){
            new Alert(Alert.AlertType.INFORMATION,"Update Success").show();
            viewTable();
            clear();
        }else {
            new Alert(Alert.AlertType.ERROR,"Update Failed").show();
        }
    }
    public void btnDeleteOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String SQL="Delete from item where code='"+txtCode.getText()+"'";
        Connection connection = DBConnection.getInstance().getConnection();
        Statement statement = connection.createStatement();
        int i = statement.executeUpdate(SQL);
        if (i>=0){
            new Alert(Alert.AlertType.INFORMATION,"Deleted Success").show();
            viewTable();
            clear();
        }
    }
    public void viewTable(){
        colCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colQtyOnHand.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));

        String SQL = "Select * from item";
        ObservableList<Item> list = FXCollections.observableArrayList();
        Connection connection = null;
        try {
            connection = DBConnection.getInstance().getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet=statement.executeQuery(SQL);
            while (resultSet.next()){
                Item item=new Item(resultSet.getString(1),resultSet.getString(2),resultSet.getInt(3),resultSet.getDouble(4));
                list.add(item);
            }
            tblItem.setItems(list);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        viewTable();
        tblItem.getSelectionModel().selectedItemProperty().addListener((observable,
                                                                            oldValue,
                                                                            newValue) -> {
            if(newValue!=null){
                setTableValuesToTxt((Item) newValue);
            }

        });
    }
    public void clear(){
        txtCode.setText("");
        txtDescription.setText("");
        txtUnitPrice.setText("");
        txtQtyOnHand.setText("");
    }

    private void setTableValuesToTxt(Item newValue) {
        txtCode.setText(newValue.getCode());
        txtDescription.setText(newValue.getDescription());
        txtUnitPrice.setText(String.valueOf((newValue.getUnitPrice())));
        txtQtyOnHand.setText(String.valueOf((newValue.getQtyOnHand())));
    }

    public void btnCancelOnAction(ActionEvent actionEvent) {
        clear();
    }
    public ArrayList<String> getAllItemId() throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement("SELECT code from Item");
        ResultSet rst = pstm.executeQuery();
        ArrayList<String> codeSet = new ArrayList<>();
        while (rst.next()){
            codeSet.add(rst.getString(1));
        }
        return codeSet;
    }
    public static Item searchItemByCode(String itemCode) throws SQLException, ClassNotFoundException {
        Connection connection=DBConnection.getInstance().getConnection();
        Statement stm=connection.createStatement();
        String SQL="Select * From Item where code='"+itemCode+"'";
        ResultSet rst=stm.executeQuery(SQL);
        return rst.next() ? new Item(itemCode,rst.getString("description"),rst.getInt("qtyOnHand"),rst.getDouble("unitPrice")):null;
    }
}
