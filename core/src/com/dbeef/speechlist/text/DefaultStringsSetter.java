package com.dbeef.speechlist.text;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.dbeef.speechlist.screen.Screen;

public class DefaultStringsSetter {

	public Screen setMenuHomeStrings(Screen menuHome) {
		menuHome.add("Summary", new Vector2(655, 660), new Vector2(3, 1),
				new Vector3(1, 1, 1));
		menuHome.add("Time spent:", new Vector2(545, 510), new Vector2(2, 1),
				new Vector3(1, 1, 1));
		menuHome.add("Accuracy:", new Vector2(690, 510), new Vector2(2, 1),
				new Vector3(1, 1, 1));
		menuHome.add("Tests solved:", new Vector2(828, 510), new Vector2(2, 1),
				new Vector3(1, 1, 1));
		menuHome.add("Speechlist", new Vector2(610, 370), new Vector2(1, 1),
				new Vector3(1, 1, 1));
		menuHome.add("Copyright 2016 Daniel Zalega", new Vector2(650, 310),
				new Vector2(6, 1), new Vector3(1, 1, 1));
		menuHome.add("Version 0.1", new Vector2(695, 285), new Vector2(6, 1),
				new Vector3(1, 1, 1));
		menuHome.add("This app is 100% free.", new Vector2(650, 245),
				new Vector2(2, 1), new Vector3(1, 1, 1));
		menuHome.add(
				"If you like it, please support me on patreon: <danielzalega>",
				new Vector2(520, 210), new Vector2(2, 1), new Vector3(1, 1, 1));
		menuHome.add("to help maintaining servers.", new Vector2(630, 175),
				new Vector2(2, 1), new Vector3(1, 1, 1));
		menuHome.add("You can also observe me on Facebook", new Vector2(580,
				140), new Vector2(2, 1), new Vector3(1, 1, 1));
		menuHome.add("to be up-to-date with my projects.",
				new Vector2(605, 105), new Vector2(2, 1), new Vector3(1, 1, 1));
		menuHome.add("Enjoy, Daniel.", new Vector2(675, 70), new Vector2(2, 1),
				new Vector3(1, 1, 1));

		return menuHome;
	}

	public Screen setMenuBriefStrings(Screen menuBrief) {
		menuBrief.add("Briefing", new Vector2(2110, 700), new Vector2(3, 1),
				new Vector3(1, 1, 1));
		menuBrief.add("Vocabulary", new Vector2(2080, 570), new Vector2(3, 1),
				new Vector3(1, 1, 1));
		menuBrief.add("Last result", new Vector2(2085, 310), new Vector2(3, 1),
				new Vector3(1, 1, 1));
		return menuBrief;
	}

	public Screen setMenuDownloadsStrings(Screen menuDownloads) {
		menuDownloads.add("Downloads", new Vector2(1610, 660),
				new Vector2(3, 1), new Vector3(1, 1, 1));

		menuDownloads.add("We're sorry", new Vector2(1575, 550), new Vector2(5,
				1), new Vector3(1, 1, 1));
		menuDownloads
				.add("This service is unavailable", new Vector2(1485, 170),
						new Vector2(4, 1), new Vector3(1, 1, 1));
		menuDownloads.add("for now.", new Vector2(1625, 135),
				new Vector2(4, 1), new Vector3(1, 1, 1));

		return menuDownloads;
	}

	public Screen deleteMenuDownloadsStrings(Screen menuDownloads) {

		menuDownloads.removeStringsWithPosition(new Vector2(1575, 550));
		menuDownloads.removeStringsWithPosition(new Vector2(1485, 170));
		menuDownloads.removeStringsWithPosition(new Vector2(1625, 135));

		return menuDownloads;
	}

	public Screen setMenuTestsStrings(Screen menuTests) {
		menuTests.add("Your tests", new Vector2(1133, 660), new Vector2(3, 1),
				new Vector3(1, 1, 1));
		return menuTests;
	}

}
