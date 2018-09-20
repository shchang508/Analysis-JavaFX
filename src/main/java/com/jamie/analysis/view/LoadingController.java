package com.jamie.analysis.view;

import java.net.URL;
import java.util.ResourceBundle;

import com.jamie.analysis.MainApp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class LoadingController implements Initializable, ControlledScreen {

	ScreensController myController;

	public void setScreenParent(ScreensController screenPage) {
		// TODO Auto-generated method stub
		this.myController = screenPage;
	}

	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}

	@FXML
	private void goToScreen1(ActionEvent event) {
		myController.setScreen(MainApp.screen1ID);
	}

}
