package com.dbeef.speechlist.text;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.dbeef.speechlist.screen.Screen;
import com.dbeef.speechlist.utils.Variables;

public class DefaultStringsSetter {

	Variables variables = new Variables();

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
		menuHome.add("Copyright 2016 Daniel Zalega", new Vector2(650, 395),
				new Vector2(6, 1), new Vector3(1, 1, 1));
		menuHome.add("Version 0.4", new Vector2(695, 415), new Vector2(6, 1),
				new Vector3(1, 1, 1));
		menuHome.add("This app is 100% free.", new Vector2(650, 305),
				new Vector2(2, 1), new Vector3(1, 1, 1));
		menuHome.add(
				"If you like it, please support me on patreon: <danielzalega>",
				new Vector2(520, 270), new Vector2(2, 1), new Vector3(1, 1, 1));
		menuHome.add("to help maintaining servers.", new Vector2(630, 235),
				new Vector2(2, 1), new Vector3(1, 1, 1));
		menuHome.add("You can also observe me on Facebook", new Vector2(580,
				200), new Vector2(2, 1), new Vector3(1, 1, 1));
		menuHome.add("to be up-to-date with my projects.",
				new Vector2(605, 165), new Vector2(2, 1), new Vector3(1, 1, 1));
		menuHome.add("Enjoy, Daniel.", new Vector2(675, 130), new Vector2(2, 1),
				new Vector3(1, 1, 1));
		
		menuHome.add("designed by from Flaticon,", new Vector2(630, 70), new Vector2(2, 1),
				new Vector3(1, 1, 1));
		menuHome.add("using 'Raleway' font by Matt McInerney", new Vector2(580, 35), new Vector2(2, 1),
				new Vector3(1, 1, 1));
		
		
		//835 for 3 digits
		//825 for 4 digits
		//815 for 2 digits
		//855 for 1 digit
		
		menuHome.add("1", new Vector2(855,
				485), new Vector2(1, 1), new Vector3(1, 1, 1));
		

		return menuHome;
	}

	public Screen setMenuBriefStrings(Screen menuBrief) {
		menuBrief.add("Briefing", new Vector2(2105, 700), new Vector2(3, 1),
				new Vector3(1, 1, 1));
		menuBrief.add("Vocabulary", new Vector2(2075, 570), new Vector2(3, 1),
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
		menuTests.add("Vocabulary",
				new Vector2(variables.getTestsScreenPosition() - 190, 360),
				new Vector2(3, 1), new Vector3(1, 1, 1));
		menuTests.add("Tenses", new Vector2(
				variables.getTestsScreenPosition() - 160, 90),
				new Vector2(3, 1), new Vector3(1, 1, 1));
		menuTests.add("Various", new Vector2(
				variables.getTestsScreenPosition() + 55, 90),
				new Vector2(3, 1), new Vector3(1, 1, 1));
		menuTests.add("Idioms", new Vector2(
				variables.getTestsScreenPosition() + 65, 360),
				new Vector2(3, 1), new Vector3(1, 1, 1));
		return menuTests;
	}

}
