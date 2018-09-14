package com.jamie.analysis.domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "NIT-ENTRY")
@XmlType(propOrder = { "networkName" })
public class NIT_ENTRY {
	
	@XmlElement(name = "NETWORK-NAME")
	private String networkName;

	public String getNetworkName() {
		return networkName;
	}

	public void setNetworkName(String networkName) {
		this.networkName = networkName;
	}
	

}
