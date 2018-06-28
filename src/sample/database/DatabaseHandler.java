package sample.database;

import sample.controller.AddItemController;
import sample.model.Task;
import sample.model.User;

import java.sql.*;

public class DatabaseHandler extends Configs {
    Connection dbConnection;

    public Connection getDbConnection() throws ClassNotFoundException, SQLException {
        String connectionString = "jdbc:mysql://"+dbHost+":"
                +dbPort+"/"+dbName;

        Class.forName("com.mysql.jdbc.Driver");

        dbConnection = DriverManager.getConnection(connectionString,dbUser,dbPass);

        return dbConnection;

        }

        // Insere registro no banco de dados
        public void signUpUser(User user) {
            String insert = "INSERT INTO "+Const.USERS_TABLE + "("
                    +Const.USERS_FIRSTNAME+","
                    +Const.USERS_LASTTNAME+","
                    +Const.USERS_USERNAME+","
                    +Const.USERS_PASSWORD+","
                    +Const.USERS_LOCATION+","
                    +Const.USERS_GENDER+") VALUES (?,?,?,?,?,?)";

            try {
                PreparedStatement preparedStatement = getDbConnection().prepareStatement(insert);

                preparedStatement.setString(1, user.getFirstName());
                preparedStatement.setString(2, user.getLastName());
                preparedStatement.setString(3, user.getUsername());
                preparedStatement.setString(4, user.getPassword());
                preparedStatement.setString(5, user.getLocation());
                preparedStatement.setString(6, user.getGender());

                preparedStatement.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        }

        public ResultSet getTasksByUser(int userId) {
            ResultSet resultTasks = null;

            String query = "SELECT * FROM "+Const.TASKS_TABLE +
                    " WHERE " + Const.USERS_ID + "=?";

            try {
                PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);
                preparedStatement.setInt(1, userId);

                resultTasks = preparedStatement.executeQuery();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            return resultTasks;
        }

        public ResultSet getUser(User user) {
            ResultSet resultSet = null;
            if (!user.getUsername().equals("") || !user.getPassword().equals("")) {
                String query = "SELECT * FROM "+Const.USERS_TABLE +
                        " WHERE " + Const.USERS_USERNAME + "=?" + " AND " +
                        Const.USERS_PASSWORD + "=?";

                try {
                    PreparedStatement preparedStatement =
                            getDbConnection().prepareStatement(query);
                    preparedStatement.setString(1, user.getUsername());
                    preparedStatement.setString(2, user.getPassword());

                    resultSet = preparedStatement.executeQuery();

                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

            } else {
                System.out.println("Please enter your credentials");
            }

            return resultSet;
        }

        public int getAllTasks(int userId) throws SQLException, ClassNotFoundException {

            String query = "SELECT COUNT(*) FROM "+Const.TASKS_TABLE +
                    " WHERE " + Const.USERS_ID + "=?";

            PreparedStatement preparedStatement;
            preparedStatement = getDbConnection().prepareStatement(query);
            preparedStatement.setInt(1, userId);

                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    return resultSet.getInt(1);
                }

            return resultSet.getInt(1);
        }

        public void insertTask(Task task) {

            String insert = "INSERT INTO "+Const.TASKS_TABLE + "("
                    +Const.USERS_ID+","
                    +Const.TASK_DATE+","
                    +Const.TASK_DESCRIPTION+","
                    +Const.TASK_TASK+")"
                    +"VALUES (?,?,?,?)";

            try {
                PreparedStatement preparedStatement = getDbConnection().prepareStatement(insert);

                preparedStatement.setInt(1, task.getUserId());
                preparedStatement.setTimestamp(2,  task.getDatecreated());
                preparedStatement.setString(3, task.getDescription());
                preparedStatement.setString(4, task.getTask());

                preparedStatement.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }


        }


        // Read

        // Update

        // Delete

    }
