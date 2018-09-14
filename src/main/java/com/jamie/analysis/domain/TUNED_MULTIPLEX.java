package com.jamie.analysis.domain;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "TUNED-MULTIPLEX")
@XmlType(propOrder = { "tunerString" })
public class TUNED_MULTIPLEX {

	@XmlElement(name = "TUNER-STRING")
	private String tunerString;

	public String getTunerString() {
		return tunerString;
	}

	public void setTunerString(String tunerString) {
		this.tunerString = tunerString;
	}
	
	
}
