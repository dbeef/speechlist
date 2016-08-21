package com.dbeef.speechlist.models;

import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@Entity
@XmlRootElement(name = "test")
public class Test {

	String name;
	String sentences;
	String[] vocabulary;
	int id;
	int uniqueId;
	
	public String getName() {
		return name;
	}

	public String getSentences() {
		return sentences;
	}

	public String[] getVocabulary() {
		return vocabulary;
	}

	public int getId() {
		return id;
	}
	@XmlElement
	public void setId(int id) {
		this.id = id;
	}

	public int getUniqueId() {
		return uniqueId;
	}
	@XmlElement
	public void setUniqueId(int uniqueId) {
		this.uniqueId = uniqueId;
	}
	@XmlElement
	public void setName(String name) {
		this.name = name;
	}
	@XmlElement
	public void setSentences(String sentences) {
		this.sentences = sentences;
	}
	@XmlElement
	public void setVocabulary(String[] vocabulary) {
		this.vocabulary = vocabulary;
	}

}
