package sample.Animations;

import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.util.Duration;

public class Shaker {

    TranslateTransition translateTransition;

    public Shaker(Node node) {
        translateTransition =
                new TranslateTransition(Duration.millis(80), node);

        translateTransition.setFromX(0);
        translateTransition.setByX(10);
        translateTransition.setCycleCount(3);
        translateTransition.setAutoReverse(true);
    }

    public void shake() {
        translateTransition.playFromStart();
    }
}
