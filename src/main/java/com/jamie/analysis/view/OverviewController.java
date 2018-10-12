package com.jamie.analysis.view;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.jamie.analysis.MainApp;
import com.jamie.analysis.domain.MPEG_TABLES;
import com.jamie.analysis.service.StreamConvertion;
import com.jamie.analysis.service.TestService;
import com.jamie.analysis.util.CwFileUtils;
import com.jamie.analysis.util.FileReader;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.victorlaerte.asynctask.AsyncTask;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputControl;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

public class OverviewController implements Initializable, ControlledScreen {

	private static final Logger logger = Logger.getLogger(OverviewController.class);

	@FXML
	private JFXButton closeBtn;

	@FXML
	private ImageView image;

	@FXML
	private JFXButton directoryBtn;

	@FXML
	private JFXButton startBtn;

	@FXML
	private JFXTextField directoryField = new JFXTextField();

//	@FXML
//	private JFXTextField resultField = new JFXTextField();

	public JFXTextField getDirectoryField() {
		return directoryField;
	}

	public void setDirectoryField(JFXTextField directoryField) {
		this.directoryField = directoryField;
	}

	@FXML
	Stage stage;

	@FXML
	Stage stage1;

	@FXML
	AnchorPane loadingLayout;

	@FXML
	AnchorPane resultLayout;

	String field1;

	private MainApp mainApp;
	private String reportName;

	DirectoryChooser chooser = new DirectoryChooser();

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}

	// Initializes the controller class
	ScreensController myController;

	public void initialize(URL url, ResourceBundle rb) {
		// TODO
	}

	public void setScreenParent(ScreensController screenParent) {
		myController = screenParent;
	}

	// Create a close button on the top-right corner so users can close the application
	@FXML
	void closeBtnHandle(ActionEvent event) {
		System.exit(0);
	}

	@FXML
	void backBtnHandle(ActionEvent event) {
//		stage1.hide();
		System.exit(0);
	}


	private class MyAsyncTask extends AsyncTask<String, Integer, Boolean> {
		@Override
		public void onPreExecute() {
			System.out.println("Background Thread will start");
			myController.setScreen(MainApp.screen2ID);
		}

		@Override
		public Boolean doInBackground(String... params) {
			System.out.println("Background Thread is running");

			try {

				/************************************* Start ************************************/

				String streamPath = directoryField.getText();
				logger.info("Path : " + streamPath);

//				if (StringUtils.isBlank(streamPath)) {
//					streamPath = "D:\\";
//					Alert alertPath = new Alert(Alert.AlertType.INFORMATION, "Please input a valid path!", ButtonType.OK);
//					alertPath.setHeaderText(null);
//					alertPath.setTitle("Result");
//					alertPath.setResizable(false);
//					alertPath.showAndWait();
//				}

				/************************************* Loading ************************************/
				StreamConvertion convert = new StreamConvertion(streamPath + "\\");

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

				service.genMpegExcel(workbook, mpegList, fileList, streamPath);

				reportName = "report_" + new Date().getTime();
				String destination = "D:\\Stream Analysis";

				CwFileUtils.createExcelFile(workbook, reportName, destination);
				logger.info("--------------------END--------------------");

			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return true;
		}

		@Override
		public void onPostExecute(Boolean success) {
			System.out.println("Background Thread has stopped");

			if (success) {
				System.out.println("Done with success");
			} else {
				System.out.println("Done with error");
			}
			myController.setScreen(MainApp.screen1ID);

			// Result pop-up window
			if (reportName != null) {
				Alert alertResult = new Alert(Alert.AlertType.INFORMATION, reportName + ".xlsx has been generted to D:\\Stream Analysis successfully!", ButtonType.OK);
				alertResult.setHeaderText(null);
				alertResult.setTitle("Result");
				alertResult.setResizable(false);
				alertResult.showAndWait();
			} else {
				// report does not generate successfully
				Alert alertResultFail = new Alert(Alert.AlertType.ERROR, "Report generation is unsuccessful...", ButtonType.OK);
				alertResultFail.setHeaderText(null);
				alertResultFail.setTitle("Error");
				alertResultFail.setResizable(false);
				alertResultFail.showAndWait();
			}
		}

		@Override
		public void progressCallback(Integer... params) {
//			System.out.println("Progress " + params[0]);
			System.out.println("Background Thread callback");
		}
	}

	// A button to select a directory from users' computer
	@FXML
	public void directoryBtnHandle(ActionEvent event) {
		// Alert message to remind users to launch the TSReader
		Alert alert = new Alert(Alert.AlertType.INFORMATION, "Please check you launch the TSReader first.", ButtonType.OK);
		alert.setHeaderText("Heads up!");
		alert.setTitle("Information");
		alert.setResizable(false);
		alert.showAndWait();

		// Folder chooser
		chooser.setTitle("Stream Analysis");
		File selectedDirectory = chooser.showDialog(new Stage());
		field1 = selectedDirectory.getAbsolutePath();

//		System.out.println("Text: " + field1);
//		directoryField.setEditable(false);
		logger.info("Path : " + field1);
		directoryField.setText(field1);

	}

	// A button to start the main function of this app
	@FXML
	public void startBtnHandle(ActionEvent event) {
		try {
			MyAsyncTask myAsyncTask = new MyAsyncTask();
			myAsyncTask.setDaemon(false);
			myAsyncTask.execute();

//			/************************************* Start ************************************/
//			
//			String streamPath = field1;
//
//			if (StringUtils.isBlank(streamPath)) {
//				streamPath = "D:\\";
//			}
//
//			/************************************* Loading ************************************/
//			StreamConvertion convert = new StreamConvertion(streamPath + "\\");
//
//			// String path = "D:\\Stream\\";
//			String path = "C:\\xml\\";
//
//			FileReader fr = new FileReader();
//			ArrayList<String> nameList = fr.readFile(path);
//
//			TestService service = new TestService();
//			MPEG_TABLES table = null;
//			XSSFWorkbook workbook = new XSSFWorkbook();
//
//			ArrayList<MPEG_TABLES> mpegList = new ArrayList<MPEG_TABLES>();
//			ArrayList<String> fileList = new ArrayList<String>();
//
//			for (String fName : nameList) {
//				table = (MPEG_TABLES) service.genTable(path + fName, MPEG_TABLES.class);
//				mpegList.add(table);
//				fileList.add(fName);
//			}
//
//			service.genMpegExcel(workbook, mpegList, fileList);
//
//			String reportName = "report_" + new Date().getTime();
//			String destination = "D:\\Stream Analysis";
//
//			CwFileUtils.createExcelFile(workbook, reportName, destination);

			// Go back to main screen after generating the report
			// myController.setScreen(MainApp.screen1ID);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
