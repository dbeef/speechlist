package com.dbeef.speechlist.utils;

public class Variables {

	static final int guiCameraPosition = 720;
	static final int initialScreenPosition = 240;
	static final int homeScreenPosition = 720;
	static final int testsScreenPosition = 1200;
	static final int downloadsScreenPosition = 1680;
	static final int briefScreenPosition = 2160;
	static final int sphinxScreenPosition = 2640;

	static final String retrieveTest = "retrieveTest";
	static final String retrieveTestName = "retrieveTestName";

	static final boolean debugMode = true;
	static final boolean debugInput = false;

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
