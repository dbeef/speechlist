package com.dbeef.speechlist.utils;

public class Variables {

	static final int characterWidth = 19;
	static final int maxCharPerTestLine = 30;
	static final int maxLinesPerTestScreen = 11;
	static final int guiCameraBriefPosition = 1175;
	static final int guiCameraPosition = 720;
	static final int initialScreenPosition = 240;
	static final int homeScreenPosition = 720;
	static final int testsScreenPosition = 1200;
	static final int downloadsScreenPosition = 1680;
	static final int briefScreenPosition = 2160;
	static final int sphinxScreenPosition = 2640;
	static final int solvingScreenPosition = 2400;
	static final int solvingScreenVocabularyPositionX = solvingScreenPosition + 10;
	static final int solvingScreenVocabularyPositionY = 735;
	static final int solvingScreenVocabularySpanY = 60;
	static final int screenWidth = 480;

	static final String retrieveTest = "retrieveTest";
	static final String retrieveTestName = "retrieveTestName";

	static final boolean debugMode = true;
	static final boolean debugInput = false;

	public int getCharacterWidth() {
		return characterWidth;
	}

	public int getSolvingScreenVocabularySpanY() {
		return solvingScreenVocabularySpanY;
	}

	public int getMaxCharPerTestLine() {
		return maxCharPerTestLine;
	}

	public int getMaxLinesPerTestScreen() {
		return maxLinesPerTestScreen;
	}

	public int getSolvingScreenPosition() {
		return solvingScreenPosition;
	}

	public int getSolvingScreenVocabularyPositionX() {
		return solvingScreenVocabularyPositionX;
	}

	public int getSolvingScreenVocabularyPositionY() {
		return solvingScreenVocabularyPositionY;
	}

	public int getScreenWidth() {
		return screenWidth;
	}

	public int getGuiCameraBriefPosition() {
		return guiCameraBriefPosition;
	}

	public int getGuiCameraPosition() {
		return guiCameraPosition;
	}

	public String RetrieveTestName() {
		return retrieveTestName;
	}

	public String retrieveTest() {
		return retrieveTest;
	}

	public int getInitialScreenPosition() {
		return initialScreenPosition;
	}

	public int getHomeScreenPosition() {
		return homeScreenPosition;
	}

	public int getTestsScreenPosition() {
		return testsScreenPosition;
	}

	public int getDownloadsScreenPosition() {
		return downloadsScreenPosition;
	}

	public int getBriefScreenPosition() {
		return briefScreenPosition;
	}

	public int getSphinxScreenPosition() {
		return sphinxScreenPosition;
	}

	public boolean getDebugMode() {
		return debugMode;
	}

	public boolean getDebugInput() {
		return debugInput;
	}
}
