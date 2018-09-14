package com.jamie.analysis;

import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.Image;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.jamie.analysis.domain.MPEG_TABLES;
import com.jamie.analysis.service.StreamConvertion;
import com.jamie.analysis.service.TestService;
import com.jamie.analysis.util.CwFileUtils;
import com.jamie.analysis.util.FileReader;

public class Main extends JFrame {

	private static final Logger logger = Logger.getLogger(Main.class);

	public ImageIcon getImage(String imagePath) {
		URL url = getClass().getResource(imagePath);
		ImageIcon image = new ImageIcon(url);
		return image;
	}


	public static void main(String[] args) throws Exception {
		try {
			/************************************* Start ************************************/
			// Change application icon from Java coffee cup to a customize image
			Image iconImage = ImageIO.read(Main.class.getResource("/output.png"));

			Main m = new Main();
			ImageIcon icon = m.getImage("/outputDialog.png");

			
			JFileChooser chooser = new JFileChooser();
			chooser.setCurrentDirectory(new java.io.File("."));
			chooser.setDialogTitle("Stream Analysis");
			chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			chooser.setAcceptAllFileFilterUsed(false);

			if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
//				logger.info("getCurrentDirectory(): " + chooser.getCurrentDirectory());
				logger.info("getSelectedFile() : " + chooser.getSelectedFile());

			} else {
				logger.info("No Selection");
			}

//			String streamPath = (String) JOptionPane.showInputDialog(null, "Please input stream path: ", "Input", JOptionPane.QUESTION_MESSAGE, icon, null, null);

			String streamPath = chooser.getSelectedFile().getPath();

			if (StringUtils.isBlank(streamPath)) {
				streamPath = "D:\\";
			}

			/************************************* Loading ************************************/
			JFrame loadingFrame = new JFrame("Stream Analysis");
			ImageIcon loadingIcon = m.getImage("/ajax-loader.gif");
			JLabel loadingLabel = new JLabel("Loading... ", loadingIcon, JLabel.CENTER);
			loadingFrame.add(loadingLabel);
			loadingLabel.setFont(new Font("Calibri", Font.BOLD, 18));
			loadingFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			loadingFrame.setSize(300, 200);
			loadingFrame.setLocationRelativeTo(null); // set JFrame to the centre of the screen
			loadingFrame.setVisible(true);
			loadingFrame.setResizable(false);
			loadingFrame.setIconImage(iconImage);

			StreamConvertion convert = new StreamConvertion(streamPath + "\\");

//			String path = "D:\\Stream\\";
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

			loadingFrame.dispose(); // Window will close after generate xml file

			service.genMpegExcel(workbook, mpegList, fileList);

			String reportName = "report_" + new Date().getTime();
			String destination = "D:\\Stream Analysis";

			CwFileUtils.createExcelFile(workbook, reportName, destination);

			/************************************* Result ************************************/
			JFrame resultFrame = new JFrame("Stream Analysis");
			JLabel resultLabel = new JLabel("Excel file is generated in D:\\Stream Analysis", null, JLabel.CENTER);
			resultFrame.add(resultLabel);
			resultLabel.setFont(new Font("Calibri", Font.BOLD, 18));
			resultFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			resultFrame.setSize(400, 200);
			resultFrame.setLocationRelativeTo(null); // set JFrame to the centre of the screen
			resultFrame.setVisible(true);
			resultFrame.setResizable(false);
			resultFrame.setIconImage(iconImage);

//			JOptionPane.showMessageDialog(null, "Excel file is generated in D:\\Stream Analysis", "Stream Analysis", JOptionPane.INFORMATION_MESSAGE, icon);

		} catch (HeadlessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.info("Cannot find the file specified)");
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
