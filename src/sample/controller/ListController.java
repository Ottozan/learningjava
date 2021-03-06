package sample.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import sample.database.DatabaseHandler;
import sample.model.Task;

import javax.xml.crypto.Data;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;

public class ListController {


    @FXML
    private JFXTextField listTaskField;

    @FXML
    private JFXTextField listDescriptionField;

    @FXML
    private JFXButton listSaveButton;

    @FXML
    private JFXListView<Task> listTasks;

    private ObservableList<Task> tasks;

    private DatabaseHandler databaseHandler;

    @FXML
    void initialize() throws SQLException {

        tasks = FXCollections.observableArrayList();

        databaseHandler = new DatabaseHandler();
        ResultSet resultSet = databaseHandler.getTasksByUser(AddItemController.userId);

        while (resultSet.next()) {
            Task task = new Task();
            task.setTaskId(resultSet.getInt("taskid"));
            task.setTask(resultSet.getString("task"));
            task.setDatecreated(resultSet.getTimestamp("datecreated"));
            task.setDescription(resultSet.getString("description"));
            tasks.addAll(task);
        }


        listTasks.setItems(tasks);
        listTasks.setCellFactory(CellController -> new CellController());

        listSaveButton.setOnAction(event -> {
            addNewTask();
        });

    }

    public void addNewTask() {
        if (!listTaskField.getText().equals("") ||
                !listDescriptionField.getText().equals("")) {
            Task myNewTask = new Task();

            Calendar calendar = Calendar.getInstance();
            java.sql.Timestamp timestamp =
                    new java.sql.Timestamp(calendar.getTimeInMillis());
            myNewTask.setUserId(AddItemController.userId);
            myNewTask.setTask(listTaskField.getText().trim());
            myNewTask.setDescription(listDescriptionField.getText().trim());
            myNewTask.setDatecreated(timestamp);

            databaseHandler.insertTask(myNewTask);

            listTaskField.setText("");
            listDescriptionField.setText("");

//            tasks.add(myNewTask); // resolve a atualização, mas não a deleção
            try {
                initialize();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


}
