package org.update4j.demo.bootstrap;

import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class ViewStack extends StackPane {

	public ViewStack(Node initial) {
		this();
		getChildren().setAll(initial);
	}

	private Rectangle clip;

	public ViewStack() {
		clip = new Rectangle(0, 0);
		clip.widthProperty().bind(widthProperty());
		clip.heightProperty().bind(heightProperty());
	}

	public void push(Node node) {
		TranslateTransition animation = new TranslateTransition(Duration.millis(100), node);
		node.setTranslateX(getWidth());

		animation.setToX(0);
		animation.setInterpolator(Interpolator.EASE_IN);

		if (getChildren().contains(node)) {
			getChildren().remove(node);
		}

		getChildren().add(node);

		animation.playFromStart();
	}

	public void back() {
		if (getChildren().size() <= 1)
			return;
		
		Node node = getChildren().get(getChildren().size() - 1);

		TranslateTransition animation = new TranslateTransition(Duration.millis(100), node);

		animation.setToX(getWidth());
		animation.setInterpolator(Interpolator.EASE_IN);
		animation.setOnFinished(evt -> {
			getChildren().remove(node);
			getChildren().add(0, node);
		});

		animation.playFromStart();
	}

}
