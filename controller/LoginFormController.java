package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.PreparedStatement;

public class LoginFormController {
    public TextField txtUserName;
    public PasswordField txtPassword;
    public Button btnLogin;
    private String userName="Chamudi";
    private String password="1234";
    private Parent parent;
    private  Stage stage;
    private Scene scene;


    public void btnLoginOnAction(ActionEvent actionEvent) throws IOException {
        if (txtUserName.getText().equals(userName) & txtPassword.getText().equals(password)){
            parent=FXMLLoader.load(getClass().getResource("/view/DashBoard-Form.fxml"));
            stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
            scene=new Scene(parent);
            stage.setScene(scene);
            stage.show();
        }else {
            new Alert(Alert.AlertType.ERROR,"Invalid Username or Password!").show();
        }
        txtUserName.setText("");
        txtPassword.setText("");
    }

    public void txtPasswordOnAction(ActionEvent actionEvent) throws IOException {
        btnLoginOnAction(actionEvent);
    }
}

