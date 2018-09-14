package com.jamie.analysis.service;

import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import java.net.PortUnreachableException;
import java.io.IOException;

import de.mud.telnet.TelnetWrapper;

public class TSReader extends TelnetWrapper{
	
	private static final Logger logger = Logger.getLogger(TSReader.class);
	
	public TSReader(){
		
		/*TSReader telnet connection */
		try {
			connect("127.0.0.1", 1399);
		} catch(java.io.IOException e) {
			e.printStackTrace();
			
			logger.info("Fail to connect TSReader! Please check whether TSReader is working.");
		}
	}		
	
}