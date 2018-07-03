package sample.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import sample.database.DatabaseHandler;
import sample.model.User;

import java.net.URL;
import java.util.ResourceBundle;

public class SignupController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private CheckBox signUpChBoxFeminino;

    @FXML
    private JFXTextField signUpNome;

    @FXML
    private JFXTextField signUpSobrenome;

    @FXML
    private JFXTextField signUpUsuario;

    @FXML
    private JFXPasswordField signUpSenha;

    @FXML
    private JFXTextField signUpLocal;

    @FXML
    private JFXButton signUpButtonSignUp;

    @FXML
    void initialize() {

        signUpButtonSignUp.setOnAction(event -> {

            createUser();

        });

    }

    public void createUser() {

        DatabaseHandler databaseHandler = new DatabaseHandler();

        String nome = signUpNome.getText();
        String sobrenome = signUpSobrenome.getText();
        String usuario = signUpUsuario.getText();
        String senha = signUpSenha.getText();
        String local = signUpLocal.getText();

        String sexo = "";
        if (signUpChBoxFeminino.isSelected()) {
            sexo = "Feminino";
        } else sexo = "Masculino";

        User user = new User(nome, sobrenome, usuario, senha, local, sexo);


        databaseHandler.signUpUser(user);

    }

}
