package com.dbeef.speechlist.utils;

import com.dbeef.speechlist.files.ResultsManager;

public class TimeSpentObserver {

	ResultsManager resultsManager;

	static final float saveSpentTimeDelay = 60;

	float timer;
	float saveSpentTimeTimer;

	float refreshDelay;

	double seconds;
	double minutes;
	double hours;
	double days;

	boolean refreshed;

	String timeSpent;

	public TimeSpentObserver(double seconds) {
		resultsManager = new ResultsManager();
		this.seconds = seconds;
		timer = 1;
		refreshDelay = 1;
		timeSpent = "";
	}

	public void updateTimers(float delta) {
		seconds += delta;
		timer += delta;
		saveSpentTimeTimer += delta;

		if (saveSpentTimeTimer > saveSpentTimeDelay) {
			saveSpentTimeTimer = 0;
			System.out.println("saving spent time, second:" + seconds);
			resultsManager.saveTimeSpent((float) seconds);
		}

		if (timer > refreshDelay) {
			timer = 0;
			refreshed = true;

			minutes = seconds / 60;
			hours = minutes / 60;
			days = hours / 24;
		}
	}

	public String getTimeSpent() {

		if (seconds < 60) {
			timeSpent = Double.toString(seconds);
			timeSpent = timeSpent.substring(0, timeSpent.indexOf(".")) + "s";
		}
		if (minutes > 1) {
			timeSpent = Double.toString(minutes);
			timeSpent = timeSpent.substring(0, timeSpent.indexOf(".")) + "m";
			refreshDelay = 60;
		}
		if (hours > 1) {
			timeSpent = Double.toString(hours);
			timeSpent = timeSpent.substring(0, timeSpent.indexOf(".")) + "h";
			refreshDelay = 3600;
		}
		if (days > 1) {
			timeSpent = Double.toString(days);
			timeSpent = timeSpent.substring(0, timeSpent.indexOf(".")) + "d";
			refreshDelay = 86400;
		}

		return timeSpent;
	}

	public boolean getRefreshed() {
		if (refreshed == true) {
			refreshed = false;
			return true;
		} else
			return false;
	}

	public void setRefreshed() {
		refreshed = true;
	}
}