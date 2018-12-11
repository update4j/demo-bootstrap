package org.update4j.demo.bootstrap;

import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

import org.update4j.Configuration;
import org.update4j.service.Delegate;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class JavaFxDelegate extends Application implements Delegate {

	@Override
	public long version() {
		return 0;
	}

	@Override
	public void main(List<String> args) throws Throwable {
		launch();
	}

	// for testing purposes only
	public static void main(String[] args) {
		launch();
	}

	public static List<Image> images;
	public static Image inverted;

	@Override
	public void init() {
		System.setProperty("suppress.warning", "true");

		List<String> sizes = List.of("tiny", "small", "medium", "large", "xlarge");
		images = sizes.stream()
						.map(s -> ("/icons/update4j-icon-" + s + ".png"))
						.map(s -> getClass().getResource(s).toExternalForm())
						.map(Image::new)
						.collect(Collectors.toList());
		inverted = new Image("/icons/update4j-icon-invert.png");
	}

	private static Stage primary;

	public static Stage getPrimaryStage() {
		return primary;
	}

	private static ViewStack stack;

	public static ViewStack getViewStack() {
		return stack;
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primary = primaryStage;
		primaryStage.setMinWidth(650);
		primaryStage.setMinHeight(500);

		URL configUrl = new URL("http://docs.update4j.org/demo/business/config.xml");
		Configuration config = null;
		try (Reader in = new InputStreamReader(configUrl.openStream(), StandardCharsets.UTF_8)) {
			config = Configuration.read(in);
		}

		StartupView startup = new StartupView(config);

		stack = new ViewStack(startup);
		Scene scene = new Scene(stack);
		scene.getStylesheets().add(getClass().getResource("root.css").toExternalForm());

		primaryStage.getIcons().addAll(images);
		primaryStage.setScene(scene);

		primaryStage.setTitle("Update4j Demo Launcher");
		primaryStage.show();
	}

}
