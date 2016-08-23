package com.dbeef.speechlist.text;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.dbeef.speechlist.gui.Button;
import com.dbeef.speechlist.screen.Screen;
import com.dbeef.speechlist.utils.Variables;

public class GeneratedTestStringsSetter {

	Variables variables;
	Array<Screen> solvingScreens;

	public Array<Screen> getSolvingScreens() {
		return solvingScreens;
	}

	public GeneratedTestStringsSetter(String sentences,
			Array<BitmapFont> fonts, Texture mainBackground, Texture wordSet,
			Texture wordNotSet) {

		variables = new Variables();
		solvingScreens = new Array<Screen>();

		SentencesFormatter sentencesFormatter = new SentencesFormatter(
				sentences);
		Screen solvingScreen = new Screen(fonts);
		Array<Vector2> buttonPositions = sentencesFormatter
				.getVocabularyPositions();

		int solvingScreensCounter = 1;
		int screenWidth = variables.getScreenWidth();
		int solvingScreenPosition = variables.getSolvingScreenPosition();
		int solvingScreenVocabularyPositionY = variables
				.getSolvingScreenVocabularyPositionY();
		int solvingScreenVocabularyPositionX = variables
				.getSolvingScreenVocabularyPositionX();
		int maxLinesPerTestScreen = variables.getMaxLinesPerTestScreen();
		int solvingScreenVocabularySpanY = variables
				.getSolvingScreenVocabularySpanY();

		for (int b = 0; b + (11 * (solvingScreensCounter - 1)) < sentencesFormatter
				.getFormatted().size; b++) {

			if (b + (maxLinesPerTestScreen * (solvingScreensCounter - 1)) == maxLinesPerTestScreen - 1) {

				solvingScreen
						.add(sentencesFormatter
								.getFormatted()
								.get(b
										+ (maxLinesPerTestScreen * (solvingScreensCounter - 1))),
								new Vector2(solvingScreenVocabularyPositionX
										+ (solvingScreensCounter - 1)
										* screenWidth,
										solvingScreenVocabularyPositionY - b
												* solvingScreenVocabularySpanY),
								new Vector2(4, 1), new Vector3(1, 1, 1));

				solvingScreen.add(mainBackground, new Vector2(
						solvingScreenPosition + (solvingScreensCounter - 1)
								* screenWidth, 0));

				System.out.println("max x value "
						+ (variables.getSolvingScreenVocabularyPositionX()
						+ variables.getScreenWidth()
						* solvingScreensCounter));
				System.out.println("min x value "
						+ (variables.getSolvingScreenVocabularyPositionX()
						+ variables.getScreenWidth()
						* (solvingScreensCounter - 1)));
				System.out.println("positions number " + buttonPositions.size);
				for (Vector2 vec : buttonPositions) {
					System.out.println("x value " + vec.x);
					if (vec.x > variables.getSolvingScreenVocabularyPositionX()
							+ variables.getScreenWidth()
							* (solvingScreensCounter - 1)
							&& vec.x < (variables.getSolvingScreenVocabularyPositionX()
									+ variables.getScreenWidth()
									* solvingScreensCounter)) {

						System.out.println("Adding to screen: " + solvingScreensCounter + " " + vec.x);
						Button wordFillButton = new Button(vec.x, vec.y,
								wordNotSet);
						solvingScreen.add(wordFillButton);
						vec.x = 0;
					}
				}
				solvingScreens.add(solvingScreen);
				solvingScreen = new Screen(fonts);
				solvingScreensCounter++;
				b = -1;
			} else if (b < maxLinesPerTestScreen
					&& b
							+ (maxLinesPerTestScreen * (solvingScreensCounter - 1))
							+ 1 != sentencesFormatter.getFormatted().size) {

				solvingScreen
						.add(sentencesFormatter
								.getFormatted()
								.get(solvingScreensCounter
										- 1
										+ b
										+ ((maxLinesPerTestScreen - 1) * (solvingScreensCounter - 1))),
								new Vector2(solvingScreenVocabularyPositionX
										+ (solvingScreensCounter - 1)
										* screenWidth,
										solvingScreenVocabularyPositionY - b
												* solvingScreenVocabularySpanY),
								new Vector2(4, 1), new Vector3(1, 1, 1));
			} else {
				solvingScreen
						.add(sentencesFormatter
								.getFormatted()
								.get(b
										+ (maxLinesPerTestScreen * (solvingScreensCounter - 1))),
								new Vector2(solvingScreenVocabularyPositionX
										+ (solvingScreensCounter - 1)
										* screenWidth,
										solvingScreenVocabularyPositionY - b
												* solvingScreenVocabularySpanY),
								new Vector2(4, 1), new Vector3(1, 1, 1));

				solvingScreen.add(mainBackground, new Vector2(
						solvingScreenPosition + (solvingScreensCounter - 1)
								* screenWidth, 0));

				System.out.println("max x value "
						+ (variables.getSolvingScreenVocabularyPositionX()
						+ variables.getScreenWidth()
						* solvingScreensCounter));
				System.out.println("min x value "
						+ (variables.getSolvingScreenVocabularyPositionX()
						+ variables.getScreenWidth()
						* (solvingScreensCounter - 1)));

				System.out.println("positions number " + buttonPositions.size);
				for (Vector2 vec : buttonPositions) {
					
					System.out.println("x value " + vec.x);
				
					if (vec.x > variables.getSolvingScreenVocabularyPositionX()
							+ variables.getScreenWidth()
							* (solvingScreensCounter - 1)
							&& vec.x < (variables.getSolvingScreenVocabularyPositionX()
									+ variables.getScreenWidth()
									* solvingScreensCounter)) {
						System.out.println("Adding to screen: " + solvingScreensCounter + " " + vec.x);
						Button wordFillButton = new Button(vec.x, vec.y,
								wordNotSet);
						solvingScreen.add(wordFillButton);
						vec.x = 0;
					}
				}
				solvingScreens.add(solvingScreen);
				solvingScreen = new Screen(fonts);
				solvingScreensCounter++;
				b = -1;
			}
		}

		if (variables.getDebugMode() == true)
			System.out.println("Number of solving screens: "
					+ solvingScreens.size);
	}
}