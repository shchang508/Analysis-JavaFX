package com.jamie.analysis;

import java.awt.HeadlessException;
import java.io.IOException;

import org.apache.log4j.Logger;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MainApp extends Application {
	
	private static final Logger logger = Logger.getLogger(MainApp.class);

	private Stage primaryStage;
	private AnchorPane rootLayout;
	
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
			
			Parent root = FXMLLoader.load(getClass().getResource("view/Overview.fxml"));

			// Show the scene containing the root layout.
			Scene scene = new Scene(root);
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