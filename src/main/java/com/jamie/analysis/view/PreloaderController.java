package com.jamie.analysis.view;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jamie.analysis.MainApp;

import javafx.application.Platform;
import javafx.application.Preloader;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class PreloaderController implements Initializable {
	
	@FXML
	private AnchorPane layout;
	
	public void initialize(URL url, ResourceBundle rb) {
		new Sleeper().start();
	}

	class Sleeper extends Thread {
		@Override
		public void run() {
			try {
				Thread.sleep(5000);

				Platform.runLater(new Runnable() {
					public void run() {
						Parent root = null;
						try {
							root = FXMLLoader.load(getClass().getResource("view/Overview.fxml"));
//							FXMLLoader loader = new FXMLLoader();
//							loader.setLocation(getClass().getResource("Overview.fxml"));
//							AnchorPane rootLayout;
//							rootLayout = (AnchorPane) loader.load();

							// Show the scene containing the root layout.
							Scene scene = new Scene(root);
							Stage primaryStage = new Stage();
							primaryStage.setScene(scene);
							primaryStage.show();
							primaryStage.setResizable(false);

							layout.getScene().getWindow().hide();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				});

			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
