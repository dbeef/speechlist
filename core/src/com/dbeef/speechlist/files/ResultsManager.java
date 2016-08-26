package com.dbeef.speechlist.files;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class ResultsManager {

	float timeSpent;
	int accuracy;
	int testsSolved;

	public void setAccuracy(int accuracy) {
		this.accuracy = accuracy;
	}

	public void setTestsSolved(int testsSolved) {
		this.testsSolved = testsSolved;
	}

	public void setTimeSpend(float timeSpent) {
		this.timeSpent = timeSpent;
	}

	public int getAccuracy() {
		return accuracy;
	}

	public int getTestsSolved() {
		return accuracy;
	}

	public float getTimeSpent() {
		return timeSpent;
	}

	public void saveData() {
		Preferences preferences = Gdx.app
				.getPreferences("Application prefferences");
		preferences.putInteger("accuracy", accuracy);
		preferences.putInteger("testsSolved", testsSolved);
		preferences.putFloat("timeSpent", timeSpent);
		preferences.flush();
	}

	public void loadData() {
		Preferences preferences = Gdx.app
				.getPreferences("Application prefferences");
		accuracy = preferences.getInteger("accuracy", 0);
		testsSolved = preferences.getInteger("testsSolved", 0);
		timeSpent = preferences.getFloat("timeSpent", 0);
	}
}