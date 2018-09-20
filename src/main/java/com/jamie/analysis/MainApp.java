package com.jamie.analysis;

import java.awt.HeadlessException;

import org.apache.log4j.Logger;

import com.jamie.analysis.view.ScreensController;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MainApp extends Application {

	private static final Logger logger = Logger.getLogger(MainApp.class);

	private Stage primaryStage;
//	private AnchorPane root;

	private double xOffset = 0;
	private double yOffset = 0;

	public static String screen1ID = "main";
	public static String screen1File = "Overview.fxml";
	public static String screen2ID = "loading";
	public static String screen2File = "Loading.fxml";

	@Override
	public void start(final Stage primaryStage) throws InterruptedException {
		try {

			this.primaryStage = primaryStage;
			this.primaryStage.setTitle("Stream Analysis");
			this.primaryStage.initStyle(StageStyle.UNDECORATED);
			initRootLayout();

		} catch (HeadlessException e) {
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void initRootLayout() {
		ScreensController mainContainer = new ScreensController();
		mainContainer.loadScreen(MainApp.screen1ID, MainApp.screen1File);
		mainContainer.loadScreen(MainApp.screen2ID, MainApp.screen2File);

		mainContainer.setScreen(MainApp.screen1ID);

		Group group = new Group();
		group.getChildren().addAll(mainContainer);

		// Make Anchorpane draggable
		Scene scene = new Scene(group);
		scene.setOnMousePressed(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				xOffset = event.getSceneX();
				yOffset = event.getSceneY();
			}
		});

		scene.setOnMouseDragged(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				primaryStage.setX(event.getScreenX() - xOffset);
				primaryStage.setY(event.getScreenY() - yOffset);
			}
		});

		// Show the scene containing the root layout.
		primaryStage.setScene(scene);
		primaryStage.show();
		primaryStage.setResizable(false);
		primaryStage.getIcons().add(new Image(MainApp.class.getResourceAsStream("view/output.png")));
	}

	public static void main(String[] args) {
		launch(args);
	}
}