package com.jamie.analysis;

import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.io.IOException;

import org.apache.log4j.Logger;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MainApp extends Application {

	private static final Logger logger = Logger.getLogger(MainApp.class);

	private Stage primaryStage;
	private AnchorPane root;

	private double xOffset = 0;
	private double yOffset = 0;

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
		try {
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(MainApp.class.getResource("view/Overview.fxml"));
			root = (AnchorPane) fxmlLoader.load();	
			
			//Make Anchorpane draggable
			Scene scene = new Scene(root);
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

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}