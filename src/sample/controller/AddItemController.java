package sample.controller;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import sample.Animations.Shaker;

import java.io.IOException;


public class AddItemController {

//    private int userId;
    public static int userId;

    @FXML
    private AnchorPane rootAnchorPane;


    @FXML
    private Label noTaskLabel;

    @FXML
    private ImageView addButton;

    @FXML
    void initialize() {

        addButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            Shaker buttonSchaker = new Shaker(addButton);
            buttonSchaker.shake();

            FadeTransition fadeTransition = new FadeTransition(Duration.millis(2000), addButton);
            FadeTransition labelTransition = new FadeTransition(Duration.millis(2000), noTaskLabel);

            System.out.println("Added clicked");
            System.out.println(userId);
            addButton.relocate(0, 20);
            noTaskLabel.relocate(0, 85);

            addButton.setOpacity(0);
            noTaskLabel.setOpacity(0);

            fadeTransition.setFromValue(1.0);
            fadeTransition.setToValue(0);
            fadeTransition.setCycleCount(1);
            fadeTransition.setAutoReverse(false);
            fadeTransition.play();

            labelTransition.setFromValue(1.0);
            labelTransition.setToValue(0.0);
            labelTransition.setCycleCount(1);
            labelTransition.setAutoReverse(false);
            labelTransition.play();


            AnchorPane formPane = null;
            try {
                formPane = FXMLLoader.load(getClass().getResource(
                        "/sample/view/addItemForm.fxml"));

                AddItemController.userId = getUserId();

//                AddItemFormController addItemFormController = new AddItemFormController();
//                addItemFormController.setUserId(getUserId());

                // perfumaria
                FadeTransition rootTransition = new FadeTransition(Duration.millis(2000), formPane);
                rootTransition.setFromValue(0);
                rootTransition.setToValue(1);
                rootTransition.setCycleCount(1);
                rootTransition.setAutoReverse(false);
                rootTransition.play();

                rootAnchorPane.getChildren().setAll(formPane);
            } catch (IOException e) {
                e.printStackTrace();
            }
//            rootAnchorForm.getChildren().setAll(formPane);


        });

    }

    public void setUserId(int userId) {
        this.userId = userId;
        System.out.println("User ID is "+this.userId);
    }

    public int getUserId() {
        return this.userId;
    }


}
