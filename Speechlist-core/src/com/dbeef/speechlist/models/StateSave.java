package com.dbeef.speechlist.models;

import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

@Entity
@XmlRootElement(name = "test")
public class StateSave {

	Array<Vector2> testResults;

	public Array<Vector2> getTestResults() {
		return testResults;
	}

	@XmlElement
	public void setTestResults(Array<Vector2> testResults) {
		this.testResults = testResults;
	}

}
