package sample.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.Animations.Shaker;
import sample.database.DatabaseHandler;
import sample.model.User;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginController {

    private int userId;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private JFXTextField loginUsername;

    @FXML
    private JFXPasswordField loginPassword;

    @FXML
    private JFXButton loginButton;

    @FXML
    private JFXButton loginSignUpButton;
    private DatabaseHandler databaseHandler;

    @FXML
    void initialize() {

        databaseHandler = new DatabaseHandler();


        loginButton.setOnAction(event -> {

            String loginText = loginUsername.getText().trim();
            String loginPwd = loginPassword.getText().trim();

            User user = new User();
            user.setUsername(loginText);
            user.setPassword(loginPwd);

            ResultSet userRow = databaseHandler.getUser(user);

            int counter = 0;

            try {
                while (userRow.next()) {
                    counter++;
                    String name = userRow.getString("firstname");
                    userId = userRow.getInt("userid");
                    System.out.println("User id attrib = " + userId);
                    System.out.println("Bem-vindo antonio "+name);
                }
                if (counter == 1) {
                    showAddItemScreen();
//                    System.out.println("AddItemScreen");
                } else {
                    Shaker usernameShaker = new Shaker(loginUsername);
                    Shaker passwordShaker = new Shaker(loginPassword);
                    usernameShaker.shake();
                    passwordShaker.shake();

                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

        });

        loginSignUpButton.setOnAction(event -> {
            // take users to signup screen
            loginSignUpButton.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/sample/view/signup.fxml"));

            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.showAndWait();

            AddItemController addItemController = loader.getController();
            addItemController.setUserId(userId);


        });

   }


    private void showAddItemScreen(){
        // take users to addItem screen
        loginSignUpButton.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/sample/view/addItem.fxml"));

        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));

        AddItemController addItemController = loader.getController();
        addItemController.setUserId(userId);

        stage.showAndWait();

    }


}
