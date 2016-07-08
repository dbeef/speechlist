package com.dbeef.speechlist.text;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.dbeef.speechlist.screen.Screen;

public class DefaultStringsManager {

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
		menuHome.add("Copyright 2016 Daniel Zalega", new Vector2(650, 290),
				new Vector2(6, 1), new Vector3(1, 1, 1));
		menuHome.add("This software uses Sphinx4, which is under:",
				new Vector2(615, 255), new Vector2(6, 1), new Vector3(1, 1, 1));
		menuHome.add("Copyright 1999-2015 Carnegie Mellon University.  ",
				new Vector2(600, 220), new Vector2(6, 1), new Vector3(1, 1, 1));
		menuHome.add("Portions Copyright 2002-2008 Sun Microsystems, Inc.  ",
				new Vector2(585, 185), new Vector2(6, 1), new Vector3(1, 1, 1));
		menuHome.add(
				"Portions Copyright 2002-2008 Mitsubishi Electric Research Laboratories.",
				new Vector2(535, 150), new Vector2(6, 1), new Vector3(1, 1, 1));
		menuHome.add("Portions Copyright 2013-2015 Alpha Cephei, Inc.",
				new Vector2(590, 115), new Vector2(6, 1), new Vector3(1, 1, 1));
		menuHome.add("All Rights Reserved.", new Vector2(680, 80), new Vector2(
				6, 1), new Vector3(1, 1, 1));
		menuHome.add("Version 0.1", new Vector2(700, 45), new Vector2(6, 1),
				new Vector3(1, 1, 1));
		return menuHome;
	}

	public Screen setMenuBriefStrings(Screen menuBrief) {
		menuBrief.add("Briefing", new Vector2(2110, 780), new Vector2(3, 1),
				new Vector3(1, 1, 1));
		menuBrief.add("Vocabulary", new Vector2(2080, 630), new Vector2(3, 1),
				new Vector3(1, 1, 1));
		menuBrief.add("Last result", new Vector2(2085, 350), new Vector2(3, 1),
				new Vector3(1, 1, 1));
		return menuBrief;
	}

	public Screen setMenuDownloadsStrings(Screen menuDownloads) {
		menuDownloads.add("Download", new Vector2(1610, 660),
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

	public Screen setMenuTestsStrings(Screen menuTests) {
		menuTests.add("Your tests", new Vector2(1133, 660), new Vector2(3, 1),
				new Vector3(1, 1, 1));

		return menuTests;
	}

}
