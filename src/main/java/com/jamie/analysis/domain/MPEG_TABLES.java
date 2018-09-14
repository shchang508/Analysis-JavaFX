package com.jamie.analysis.domain;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "MPEG-TABLES")
@XmlType(propOrder = { "tunedMultiplex", "pmts", "nit", "eit" })
public class MPEG_TABLES implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@XmlElement(name = "TUNED-MULTIPLEX")
	private TUNED_MULTIPLEX tunedMultiplex;

	@XmlElement(name = "PMTs")
	private PMTs pmts;
	
	@XmlElement(name = "NIT")
	private NIT nit;

	@XmlElement(name = "EIT")
	private EIT eit;
	

	public TUNED_MULTIPLEX getTunedMultiplex() {
		return tunedMultiplex;
	}

	public void setTunedMultiplex(TUNED_MULTIPLEX tunedMultiplex) {
		this.tunedMultiplex = tunedMultiplex;
	}

	public PMTs getPmts() {
		return pmts;
	}

	public void setPmts(PMTs pmts) {
		this.pmts = pmts;
	}
	
	public NIT getNit() {
		return nit;
	}

	public void setNit(NIT nit) {
		this.nit = nit;
	}

	public EIT getEit() {
		return eit;
	}

	public void setEit(EIT eit) {
		this.eit = eit;
	}

}
