package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class DashBoardController implements Initializable {
    public AnchorPane root;
    public ImageView btnExitOnAction;
    private Parent parent;
    private Stage stage;
    private Scene scene;

    public void btnCustomerOnAction(ActionEvent actionEvent) throws IOException {
       URL resource = this.getClass().getResource("/view/Customer-Form.fxml");

        assert resource != null;

        Parent load = (Parent) FXMLLoader.load(resource);
        this.root.getChildren().clear();
        this.root.getChildren().add(load);
    }
    public void btnItemOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = this.getClass().getResource("/view/Item-Form.fxml");

        assert resource != null;

        Parent load = (Parent) FXMLLoader.load(resource);
        this.root.getChildren().clear();
        this.root.getChildren().add(load);
    }

    public void btnExitOnAction(ActionEvent actionEvent) throws IOException {
        parent=FXMLLoader.load(getClass().getResource("/view/Login-Form.fxml"));
        stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        scene=new Scene(parent);
        stage.setScene(scene);
        stage.show();
    }

    public void btnHomeOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = this.getClass().getResource("/view/HomePage.fxml");

        assert resource != null;

        Parent load = (Parent) FXMLLoader.load(resource);
        this.root.getChildren().clear();
        this.root.getChildren().add(load);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        URL resource = this.getClass().getResource("/view/HomePage.fxml");

        assert resource != null;

        Parent load = null;
        try {
            load = (Parent) FXMLLoader.load(resource);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.root.getChildren().clear();
        this.root.getChildren().add(load);
    }

    public void btnOrderOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = this.getClass().getResource("/view/order-Form.fxml");

        assert resource != null;

        Parent load = (Parent) FXMLLoader.load(resource);
        this.root.getChildren().clear();
        this.root.getChildren().add(load);

    }
    public void btnpPlaceOrderOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = this.getClass().getResource("/view/placeOrder-Form.fxml");

        assert resource != null;

        Parent load = (Parent) FXMLLoader.load(resource);
        this.root.getChildren().clear();
        this.root.getChildren().add(load);
    }
}
