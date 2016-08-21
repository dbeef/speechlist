package com.dbeef.speechlist.models;

import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement(name = "uniqueIdContainer")
public class UniqueIdContainer {

	private int uniqueIds[];

	@XmlElement
	public void setUniqueIds(int uniqueIds[]) {
		this.uniqueIds = uniqueIds;
	}

	public int[] getUniqueIds() {
		return uniqueIds;
	}

}
