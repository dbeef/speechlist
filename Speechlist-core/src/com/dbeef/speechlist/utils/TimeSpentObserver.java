package com.dbeef.speechlist.utils;

import com.dbeef.speechlist.files.ResultsManager;

public class TimeSpentObserver {

	ResultsManager resultsManager;

	static final float delay_save_spent_Time = 60;
	float delay_refresh_spent_time_string;

	float timer_refresh_spent_time_string;
	float timer_save_spent_time;

	float seconds;
	float minutes;
	float hours;
	float days;

	boolean refreshed;

	String time_spent;

	public TimeSpentObserver(float seconds_spent_already) {
		resultsManager = new ResultsManager();
		seconds = seconds_spent_already;
		timer_refresh_spent_time_string = 1;
		delay_refresh_spent_time_string = 1;
		time_spent = "";
	}

	public void updateTimers(float delta) {
		seconds += delta;
		timer_refresh_spent_time_string += delta;
		timer_save_spent_time += delta;

		if (timer_save_spent_time > delay_save_spent_Time) {
			timer_save_spent_time = 0;
			resultsManager.saveTimeSpent(seconds);
		}

		if (timer_refresh_spent_time_string > delay_refresh_spent_time_string) {

			minutes = seconds / 60;
			hours = minutes / 60;
			days = hours / 24;

			timer_refresh_spent_time_string = 0;
			refreshed = true;
		}
	}

	public String getTimeSpent() {

		if (seconds < 60) {
			time_spent = Double.toString(seconds);
			time_spent = time_spent.substring(0, time_spent.indexOf(".")) + "s";
		}
		if (minutes > 1) {
			time_spent = Double.toString(minutes);
			time_spent = time_spent.substring(0, time_spent.indexOf(".")) + "m";
			delay_refresh_spent_time_string = 60;
		}
		if (hours > 1) {
			time_spent = Double.toString(hours);
			time_spent = time_spent.substring(0, time_spent.indexOf(".")) + "h";
			delay_refresh_spent_time_string = 3600;
		}
		if (days > 1) {
			time_spent = Double.toString(days);
			time_spent = time_spent.substring(0, time_spent.indexOf(".")) + "d";
			delay_refresh_spent_time_string = 86400;
		}
		return time_spent;
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