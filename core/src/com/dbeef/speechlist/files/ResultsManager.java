package com.dbeef.speechlist.files;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.dbeef.speechlist.models.StateSave;

public class ResultsManager {

	Json json;
	StateSave stateSave;

	String testResultsJson;
	Array<Vector2> testResults;

	float timeSpent;
	int accuracy;
	int testsSolved;

	boolean refreshed = true;

	public ResultsManager() {
		json = new Json();
		testResults = new Array<Vector2>();
		stateSave = new StateSave();
	}

	public float getResultOfTestWithUniqueID(int uniqueID) {

		System.out.println("unigue id: " + uniqueID);
		for (Vector2 result : testResults) {

			System.out.println("results " + result.x + " " + result.y);

			if (result.x == uniqueID)
				return result.y;
		}

		return -1;
	}

	public void addTestResult(Vector2 result) {

		boolean firstTimeSolved = true;
		for (Vector2 testResult : testResults) {
			if (testResult.x == result.x) {
				testResult.y = result.y;
				firstTimeSolved = false;
				break;
			}
		}
		
		if (firstTimeSolved == true)
			testResults.add(result);

		testsSolved++;

		accuracy = 0;
		for (Vector2 testResult : testResults) {
			accuracy += testResult.y;
		}
		accuracy = (int) accuracy / testResults.size;

		refreshed = true;
	}

	public int getTestsSolved() {
		return testsSolved;
	}

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

	public float getTimeSpent() {
		return timeSpent;
	}

	public void saveData() {

		stateSave.setTestResults(testResults);
		testResultsJson = json.toJson(stateSave, StateSave.class);

		Preferences preferences = Gdx.app
				.getPreferences("Application prefferences");
		preferences.putInteger("accuracy", accuracy);
		preferences.putInteger("testsSolved", testsSolved);
		preferences.putString("testResults", testResultsJson);
		preferences.flush();
	}

	public void saveTimeSpent(float timeSpent) {
		Preferences preferences = Gdx.app
				.getPreferences("Application prefferences");
		preferences.putFloat("timeSpent", timeSpent);
		preferences.flush();
	}

	public void loadData() {
		Preferences preferences = Gdx.app
				.getPreferences("Application prefferences");
		accuracy = preferences.getInteger("accuracy", 0);
		testsSolved = preferences.getInteger("testsSolved", 0);
		timeSpent = preferences.getFloat("timeSpent", 0);
		testResultsJson = preferences.getString("testResults", "empty");

		if (!testResultsJson.equals("empty"))
			stateSave = json.fromJson(StateSave.class, testResultsJson);

		if (stateSave.getTestResults() != null)
			testResults = stateSave.getTestResults();

	}

	public boolean getRefreshed() {
		return refreshed;
	}
}