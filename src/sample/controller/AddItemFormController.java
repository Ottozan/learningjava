package sample.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.database.DatabaseHandler;
import sample.model.Task;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.ResourceBundle;

public class AddItemFormController {

    private int userId;

    private DatabaseHandler databaseHandler;

//    @FXML
//    private ResourceBundle resources;
//
//    @FXML
//    private URL location;

    @FXML
    private Label successLabel;

    @FXML
    private Label sucessoLabel;

    @FXML
    private Button todoButton;

    @FXML
    private JFXTextField taskField;

    @FXML
    private JFXTextField descriptionField;

    @FXML
    private JFXButton saveTaskButton;
    @FXML
    void initialize() {

        databaseHandler = new DatabaseHandler();
        Task task = new Task();

        saveTaskButton.setOnAction(event -> {
            System.out.println("Salvar tarefa...");

            Calendar calendar = Calendar.getInstance();

            java.sql.Timestamp timestamp =
                    new java.sql.Timestamp(calendar.getTimeInMillis());

            String taskText = taskField.getText().trim();
            String taskDescription = descriptionField.getText().trim();

            if(!taskText.equals("") || !taskDescription.equals("")) {

                System.out.println("User id: " + AddItemController.userId);
                task.setUserId(AddItemController.userId);
                task.setDatecreated(timestamp);
                task.setDescription(taskDescription);
                task.setTask(taskText);
                databaseHandler.insertTask(task);

                successLabel.setVisible(true);
                todoButton.setVisible(true);
                int taskNumber = 0;
                try {
                    taskNumber = databaseHandler.getAllTasks(AddItemController.userId);
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                todoButton.setText("Tarefa: ("+ taskNumber +")");

                taskField.setText("");
                descriptionField.setText("");

                todoButton.setOnAction(event1 -> {
                    // send users to the list screen
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource(
                            "/sample/view/list.fxml"));
                    try {
                        loader.load();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Parent root = loader.getRoot();
                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.showAndWait();

                });
//                System.out.println("Tarefa adicionada com sucesso");

            } else {

                System.out.println("Nada adicionado");

            }

        });

        }

    public int getUserId() {
        return this.userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

}
