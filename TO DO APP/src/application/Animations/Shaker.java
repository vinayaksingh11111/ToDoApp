package application.Animations;

import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.util.Duration;

public class Shaker {
	private TranslateTransition transition;

	public Shaker(Node node) {
		transition = new TranslateTransition(Duration.millis(50), node);
		transition.setFromX(0f);
		transition.setToX(10f);
		transition.setCycleCount(2);
		transition.setAutoReverse(true);
	}

	public void Shake() {
		transition.playFromStart();
	}
}
