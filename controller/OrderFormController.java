package controller;

import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.*;

import javax.management.StringValueExp;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

import static controller.OrderController.getLastOrderId;
public class OrderFormController implements Initializable {
    public TextField txtOrderId;
    public TextField txtOrderDate;
    public ComboBox cmbCustomerId;
    public TextField txtCustomerName;
    public ComboBox cmbItemCode;
    public TextField txtDescription;
    public TextField txtUnitPrice;
    public TextField txtQtyOnHand;
    public TextField txtQty;
    public TableView tblOrderDetails;
    public TableColumn colCode;
    public TableColumn colQty;
    public TableColumn colDescription;
    public TableColumn colUnitPrice;
    public TableColumn colTotal;
    public TextField txtTotal;
    public Button btnPlaceOrder;
    public Button btnRemove;
    public Button btnAdd;
    public Label lblOid;
    public ObservableList<CartTm> cartList=FXCollections.observableArrayList();
    public void btnPlaceOrderOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
       String orderId=txtOrderId.getText();
       String date=txtOrderDate.getText();
       String customerId= (String) cmbCustomerId.getSelectionModel().getSelectedItem();
       ArrayList<OrderDetails> orderDetailList=new ArrayList<>();

        for (CartTm cartTm: cartList) {
            String itemCode=cartTm.getItemCode();
            int qty=cartTm.getQty();
            double unitPrice=cartTm.getUnitPrice();
            OrderDetails orderDetail =new OrderDetails(orderId,itemCode,qty,unitPrice);
            orderDetailList.add(orderDetail);
        }
        Order order=new Order(orderId,date,customerId,orderDetailList);
        boolean isAdded=OrderController.placeOrder(order);

        if(isAdded){
           new Alert(Alert.AlertType.INFORMATION,"Added Success").show();
           clearItems();
           loadOrderId();
        }
    }
    public void clearItems(){
        txtCustomerName.setText("");
        txtDescription.setText("");
        txtUnitPrice.setText("");
        txtQtyOnHand.setText("");
        txtQty.setText("");
        tblOrderDetails.getSelectionModel().clearSelection();
    }

    private void loadDate(){
        txtOrderDate.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
    }
    private void loadOrderId() throws SQLException, ClassNotFoundException {
        String lastOrderId = getLastOrderId();
        if (lastOrderId!=null){
            lastOrderId=lastOrderId.split("[A-Z]")[1];
            lastOrderId=String.format("D%03d",(Integer.parseInt(lastOrderId)+1));
            txtOrderId.setText(lastOrderId);
        }else {
            txtOrderId.setText("D001");
        }
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        loadDate();

        try {
            loadOrderId();
            loadAllCustomerIds();
            loadAllItemId();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    private void loadAllCustomerIds() throws SQLException, ClassNotFoundException {
        ArrayList<String> custId = new CustomerController().getAllCustomersId();
        cmbCustomerId.getItems().addAll(custId);
    }
    private void loadAllItemId() throws SQLException, ClassNotFoundException {
        ArrayList<String> itemId= new ItemController().getAllItemId();
        cmbItemCode.getItems().addAll(itemId);
    }
    public void cmbCustomerOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String custId = cmbCustomerId.getSelectionModel().getSelectedItem().toString();
        txtCustomerName.setText(CustomerController.searchCustomerById(custId).getName());
    }
    public void cmbItemOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String itemCode = cmbItemCode.getSelectionModel().getSelectedItem().toString();
        Item item=ItemController.searchItemByCode(itemCode);
        txtDescription.setText(item.getDescription());
        txtQtyOnHand.setText(item.getQtyOnHand()+"");
        txtUnitPrice.setText(item.getUnitPrice()+"");
    }
   private int isAlreadyExists(CartTm cartTm){
        for (int i=0; i< cartList.size(); i++){
            String tempCode = String.valueOf(cartList.get(i).getItemCode());
            if(tempCode.equals(cartTm.getItemCode())){
                return i;
            }
        }
        return -1;
    }
    public void btnAddOnAction(ActionEvent actionEvent) {
        try{
            String itemCode = (String) cmbItemCode.getSelectionModel().getSelectedItem();
            String description = txtDescription.getText();
            int qty = Integer.parseInt(txtQty.getText());
            double unitPrice = Double.parseDouble(txtUnitPrice.getText());
            double total = unitPrice*qty;

            CartTm cartTm = new CartTm(itemCode, description, qty, unitPrice, total);
            int row = isAlreadyExists(cartTm);
            if (row==-1){
                cartList.add(cartTm);
                tblOrderDetails.setItems(cartList);
            }else {
                CartTm cart =cartList.get(row);
                CartTm tempCartTm = new CartTm(cartTm.getItemCode(),cartTm.getDescription(),cartTm.getQty()+qty,cartTm.getUnitPrice(),total+cartTm.getTotal());
                cartList.remove(row);
                cartList.add(tempCartTm);
            }
            tblOrderDetails.setItems(cartList);
            calculateTotal();
        }catch (NumberFormatException ex){
            new Alert(Alert.AlertType.ERROR,"Added Failed !").show();
        }
    }
    public void calculateTotal(){
        double total= 0;
        for (CartTm cartTm: cartList) {
            total+= cartTm.getTotal();
        }
        txtTotal.setText(String.valueOf(total));
    }
    public void btnRemoveOnAction(ActionEvent actionEvent) {
        try{
            int row = tblOrderDetails.getSelectionModel().getSelectedIndex();
            cartList.remove(row);
        }catch (ArrayIndexOutOfBoundsException ex){
            new Alert(Alert.AlertType.ERROR,"Remove Failed").show();
        }
    }
}
