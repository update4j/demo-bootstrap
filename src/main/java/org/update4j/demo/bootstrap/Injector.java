package org.update4j.demo.bootstrap;

import org.update4j.inject.InjectSource;
import org.update4j.inject.Injectable;

import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Injector implements Injectable {


	@InjectSource
	public Stage primaryStage;

	@InjectSource
	public Image inverted;
	
	@InjectSource
	public CheckBox singleInstanceCheckbox;
	
	@InjectSource
	public TextField singleInstanceMessage;
	
	@InjectSource
	public CheckBox newWindowCheckbox;
}
