package com.dbeef.speechlist.models;

import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement(name = "testNamesContainer")
public class TestNamesContainer {

	private String names[];

	@XmlElement
	public void setNames(String names[]) {
		this.names = names;
	}

	public String[] getNames() {
		return names;
	}

}