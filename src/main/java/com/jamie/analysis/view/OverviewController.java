package com.jamie.analysis.view;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.jamie.analysis.MainApp;
import com.jamie.analysis.domain.MPEG_TABLES;
import com.jamie.analysis.service.StreamConvertion;
import com.jamie.analysis.service.TestService;
import com.jamie.analysis.util.CwFileUtils;
import com.jamie.analysis.util.FileReader;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class OverviewController {

	@FXML
	private JFXButton closeBtn;
	
	@FXML
	private JFXButton closeBtn2;

	@FXML
	private JFXButton directoryBtn;

	@FXML
	private JFXButton startBtn;

	@FXML
	private JFXTextField directoryField = new JFXTextField();

	@FXML
	Stage stage;

	public Stage getStage() {
		return stage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}
	
	String field1;

	private MainApp mainApp;

	DirectoryChooser chooser = new DirectoryChooser();

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}

	// Create a close button on the top-right corner so users can close the application
	@FXML
	void closeBtnHandle(ActionEvent event) {
		System.exit(0);
	}
	
	// A button to select a directory from users' computer
	@FXML
	public void directoryBtnHandle(ActionEvent event) {
		// Alert message to remind users to launch the TSReader
		Alert alert = new Alert(Alert.AlertType.INFORMATION, "Please check you launch the TSReader first.", ButtonType.OK);
		alert.setHeaderText("Heads up!");
		alert.setResizable(false);
		alert.showAndWait();

		// File chooser
		chooser.setTitle("Stream Analysis");
		File selectedDirectory = chooser.showDialog(new Stage());
		field1 = selectedDirectory.getAbsolutePath();
		System.out.println("Text: " + field1);
//		directoryField.setEditable(false);
		directoryField.setText(field1);
	}

	// A button to start the main function of this app
	@FXML
	public void startBtnHandle(ActionEvent event) {
		try {

			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(OverviewController.class.getResource("Loading.fxml"));
			AnchorPane layout = (AnchorPane) fxmlLoader.load();

			Scene scene = new Scene(layout);
			stage = new Stage(StageStyle.UNDECORATED);
			stage.setTitle("Stream Analysis");
			stage.setScene(scene);
			stage.show();

			/************************************* Start ************************************/
			String streamPath = field1;

			if (StringUtils.isBlank(streamPath)) {
				streamPath = "D:\\";
			}

			/************************************* Loading ************************************/
			StreamConvertion convert = new StreamConvertion(streamPath + "\\");

			// String path = "D:\\Stream\\";
			String path = "C:\\xml\\";

			FileReader fr = new FileReader();
			ArrayList<String> nameList = fr.readFile(path);

			TestService service = new TestService();
			MPEG_TABLES table = null;
			XSSFWorkbook workbook = new XSSFWorkbook();

			ArrayList<MPEG_TABLES> mpegList = new ArrayList<MPEG_TABLES>();
			ArrayList<String> fileList = new ArrayList<String>();

			for (String fName : nameList) {
				table = (MPEG_TABLES) service.genTable(path + fName, MPEG_TABLES.class);
				mpegList.add(table);
				fileList.add(fName);
			}

			stage.close(); // Window will close after generate xml file

			service.genMpegExcel(workbook, mpegList, fileList);

			String reportName = "report_" + new Date().getTime();
			String destination = "D:\\Stream Analysis";

			CwFileUtils.createExcelFile(workbook, reportName, destination);

		} catch (IOException e) {
			e.printStackTrace();
//	        Logger logger = Logger.getLogger(getClass().getName());
//	        logger.log(Level.SEVERE, "Failed to create new Window.", e);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
