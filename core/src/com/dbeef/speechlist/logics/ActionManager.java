package com.dbeef.speechlist.logics;

import java.io.IOException;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.dbeef.speechlist.camera.Camera;
import com.dbeef.speechlist.gui.Button;
import com.dbeef.speechlist.gui.TestButton;
import com.dbeef.speechlist.input.InputInterpreter;
import com.dbeef.speechlist.recognition.AcousticModelWriter;
import com.dbeef.speechlist.recognition.SpeechRecognizer;
import com.dbeef.speechlist.screen.Screen;
import com.dbeef.speechlist.utils.AssetsManager;
import com.dbeef.speechlist.utils.TestModel;
import com.dbeef.speechlist.utils.TestsManager;
import com.dbeef.speechlist.utils.VocabularyFormatter;

public class ActionManager {

	SpeechRecognizer speechRecognizer = new SpeechRecognizer();
	
	static final int guiCameraPosition = 720;
	static final int initialScreenPosition = 240;
	static final int homeScreenPosition = 720;
	static final int testsScreenPosition = 1200;
	static final int downloadsScreenPosition = 1680;
	static final int briefScreenPosition = 2160;
	static final int sphinxScreenPosition = 2640;

	int tapX;
	int tapY;
	double panX;
	double panY;
	double initialPanX;
	double initialPanY;

	InputInterpreter inputInterpreter;
	TestsManager testsManager;
	float timer = 0;

	Button home;
	Button tests;
	Button downloads;
	Button accept;
	Button decline;

	boolean wasPannedBefore = false;
	boolean initialCameraMovementsDone = false;
	boolean logoCameOnScreen = false;
	boolean loadingTextAdded = false;
	boolean assetsLoaded = false;
	boolean readyToGoMenu = false;

	AssetsManager assetsManager;

	Camera camera;
	Camera guiCamera;

	Screen initial;
	Screen menuHome;
	Screen menuTests;
	Screen menuDownloads;
	Screen menuBrief;
	Screen menuSphinx;
	Screen gui;

	Array<TestButton> testsButtons;

	public ActionManager(Camera camera, Camera guiCamera, Screen initial,
			Screen gui, Screen menuHome, Screen menuTests,
			Screen menuDownloads, Screen menuBrief, Screen menuSphinx) {
		this.camera = camera;
		this.guiCamera = guiCamera;
		this.initial = initial;
		this.gui = gui;
		this.menuHome = menuHome;
		this.menuTests = menuTests;
		this.menuDownloads = menuDownloads;
		this.menuBrief = menuBrief;
		this.menuSphinx = menuSphinx;
		inputInterpreter = new InputInterpreter();
	}

	public void updateLogics(float delta) {
		if (isCameraChangingPosition() == true)
			optimizeRendering();
		updateInitialScreenLogics(delta);
		updateAssetsLoaderLogics();
		addAssetsToScreens();
		updateCamerasLogics();
		if (isCameraChangingPosition() == false
				&& initialCameraMovementsDone == true) {
			updateButtonsLogics();
			updateFlingCamera();
			manageSheetSliding();
		}
		if (wasPannedBefore == false && inputInterpreter.panned == false
				&& assetsLoaded) {
			updateButtonsGravity(delta);
		}
		try {
			recognizeSpeech();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	void updateInitialScreenLogics(float delta) {
		if (Math.abs(camera.position.x - 240) < 10) {
			logoCameOnScreen = true;
		}
		if (logoCameOnScreen == true && loadingTextAdded == false) {
			initial.add("loading...", new Vector2(215, 350), new Vector2(2, 0),
					new Vector3(1, 1, 1));
			loadingTextAdded = true;
		}
		if (loadingTextAdded == true) {
			timer += delta;
			if (timer > 0.01) {
				timer = 0;

				if (initial.changeStringAlpha("loading...", 0) < 1)
					initial.changeStringAlpha("loading...", 0.005f);
			}
		}
	}

	void updateAssetsLoaderLogics() {

		if (loadingTextAdded == true) {

			if (assetsLoaded == false) {
				assetsManager = new AssetsManager();
				testsManager = new TestsManager();
				AcousticModelWriter acousticModelWriter = new AcousticModelWriter();
				acousticModelWriter.write();
				assetsLoaded = true;
			}

		}
	}

	void addAssetsToScreens() {

		if (assetsLoaded == true && readyToGoMenu == false) {
			home = new Button(520, 713, assetsManager.home);
			tests = new Button(670, 713, assetsManager.pencil);
			downloads = new Button(810, 713, assetsManager.cloud);

			initiateTestsButtons();

			home.select();

			menuHome.add(assetsManager.clock, new Vector2(550, 530));
			menuHome.add(assetsManager.chart, new Vector2(690, 530));
			menuHome.add(assetsManager.checked, new Vector2(835, 530));

			menuHome.add("Summary", new Vector2(655, 660), new Vector2(3, 1),
					new Vector3(1, 1, 1));

			menuHome.add("Time spent:", new Vector2(545, 510),
					new Vector2(2, 1), new Vector3(1, 1, 1));
			menuHome.add("Accuracy:", new Vector2(690, 510), new Vector2(2, 1),
					new Vector3(1, 1, 1));
			menuHome.add("Tests solved:", new Vector2(828, 510), new Vector2(2,
					1), new Vector3(1, 1, 1));

			menuHome.add("Speechlist", new Vector2(610, 370),
					new Vector2(1, 1), new Vector3(1, 1, 1));

			menuHome.add("Copyright 2016 Daniel Zalega", new Vector2(650, 290),
					new Vector2(6, 1), new Vector3(1, 1, 1));
			menuHome.add("This software uses Sphinx4, which is under:",
					new Vector2(615, 255), new Vector2(6, 1), new Vector3(1, 1,
							1));
			menuHome.add("Copyright 1999-2015 Carnegie Mellon University.  ",
					new Vector2(600, 220), new Vector2(6, 1), new Vector3(1, 1,
							1));
			menuHome.add(
					"Portions Copyright 2002-2008 Sun Microsystems, Inc.  ",
					new Vector2(585, 185), new Vector2(6, 1), new Vector3(1, 1,
							1));
			menuHome.add(
					"Portions Copyright 2002-2008 Mitsubishi Electric Research Laboratories.",
					new Vector2(535, 150), new Vector2(6, 1), new Vector3(1, 1,
							1));
			menuHome.add("Portions Copyright 2013-2015 Alpha Cephei, Inc.",
					new Vector2(590, 115), new Vector2(6, 1), new Vector3(1, 1,
							1));
			menuHome.add("All Rights Reserved.", new Vector2(680, 80),
					new Vector2(6, 1), new Vector3(1, 1, 1));
			menuHome.add("Version 0.1", new Vector2(700, 45),
					new Vector2(6, 1), new Vector3(1, 1, 1));
			gui.add(home);
			gui.add(tests);
			gui.add(downloads);
			gui.add(assetsManager.logoLittle, new Vector2(705, 680));

			menuTests.add("Your tests", new Vector2(1133, 660), new Vector2(3,
					1), new Vector3(1, 1, 1));

			menuDownloads.add("Download", new Vector2(1610, 660), new Vector2(
					3, 1), new Vector3(1, 1, 1));

			menuDownloads.add(assetsManager.sadPhone, new Vector2(1560, 200));
			menuDownloads.add("We're sorry", new Vector2(1575, 550),
					new Vector2(5, 1), new Vector3(1, 1, 1));
			menuDownloads.add("This service is unavailable", new Vector2(1485,
					170), new Vector2(4, 1), new Vector3(1, 1, 1));
			menuDownloads.add("for now.", new Vector2(1625, 135), new Vector2(
					4, 1), new Vector3(1, 1, 1));

			accept = new Button(2080, 100, assetsManager.checked);
			decline = new Button(2180, 100, assetsManager.cross);

			decline.setMultiplier(4);
			accept.setMultiplier(4);

			menuBrief.add(accept);
			menuBrief.add(decline);

			readyToGoMenu = true;

		}

	}

	void updateCamerasLogics() {
		if (readyToGoMenu == true
				&& (initial.changeStringAlpha("loading...", 0) == 1)
				&& initialCameraMovementsDone == false) {
			camera.move(homeScreenPosition);
			guiCamera.move(homeScreenPosition);
			initialCameraMovementsDone = true;
		}
		if (camera.position.x > downloadsScreenPosition) {
			guiCamera.changePosition(guiCameraPosition
					+ (camera.position.x - downloadsScreenPosition));
		}

	}

	void updateButtonsLogics() {
		if (inputInterpreter.touched == true) {

			Vector3 vec = inputInterpreter.getLastTouchPosition();

			guiCamera.unproject(vec);

			if (home.checkCollision((int) vec.x, (int) vec.y) == true) {
				home.select();
				tests.deselect();
				downloads.deselect();
				camera.move(homeScreenPosition);
			}
			if (tests.checkCollision((int) vec.x, (int) vec.y) == true) {
				home.deselect();
				tests.select();
				downloads.deselect();
				camera.move(testsScreenPosition);
			}
			if (downloads.checkCollision((int) vec.x, (int) vec.y) == true) {
				home.deselect();
				tests.deselect();
				downloads.select();
				camera.move(downloadsScreenPosition);
			}

			Vector3 vecNonGui = inputInterpreter.getLastTouchPosition();

			camera.unproject(vecNonGui);
			manageTestsButtonsCollisions((int) vecNonGui.x, (int) vecNonGui.y);
			manageBriefButtonsCollisions((int) vecNonGui.x, (int) vecNonGui.y);
		}
	}

	void updateFlingCamera() {
		double flingedDeltaX = inputInterpreter.getFlingDeltaX();

		if (flingedDeltaX > 300) {

			if (tests.getSelection() == true) {
				home.select();
				tests.deselect();
				downloads.deselect();
				camera.move(homeScreenPosition);
			}
			if (downloads.getSelection() == true) {
				home.deselect();
				tests.select();
				downloads.deselect();
				camera.move(testsScreenPosition);
			}
		}
		if (flingedDeltaX < -300) {
			if (tests.getSelection() == true) {
				home.deselect();
				tests.deselect();
				downloads.select();
				camera.move(downloadsScreenPosition);
			}
			if (home.getSelection() == true) {
				home.deselect();
				tests.select();
				downloads.deselect();
				camera.move(testsScreenPosition);
			}

		}
	}

	boolean isCameraChangingPosition() {
		if (camera.position.x == homeScreenPosition
				|| camera.position.x == testsScreenPosition
				|| camera.position.x == downloadsScreenPosition
				|| camera.position.x == briefScreenPosition)
			return false;
		else
			return true;
	}

	void manageBriefButtonsCollisions(float x, float y) {

		if (decline.checkCollision((int) x, (int) y) == true) {
			camera.move(testsScreenPosition);
			guiCamera.move(guiCameraPosition);
			decline.blink();
			tests.select();
			for (int a = 0; a < testsButtons.size; a++) {
				if (testsButtons.get(a).isHighlighted() == true) {
					testsButtons.get(a).lowlight();
				}
			}
		}

		if (accept.checkCollision((int) x, (int) y) == true) {
			accept.blink();
			camera.move(sphinxScreenPosition);
		}

	}

	void manageTestsButtonsCollisions(float x, float y) {

		for (int a = 0; a < testsButtons.size; a++) {
			if (testsButtons.get(a).getSelection() == true) {
				if (testsButtons.get(a).checkCollisionTick((int) x, (int) y) == true) {
					testsButtons.get(a).highlight();
					tests.deselect();
					camera.move(briefScreenPosition);
					addMenuBriefStrings(a);
					addMenuSphinxStrings(a);
				}
			}

		}
		manageTestButtonsHighlighting(x, y);
	}

	void manageSheetSliding() {

		if (wasPannedBefore == true && inputInterpreter.getPanned() == false) {
			for (int a = 0; a < testsButtons.size; a++)
				testsButtons.get(a).savePositionAsOriginPosition();
		}

		if (tests.getSelection() == true) {

			if (inputInterpreter.getTouchDown() == true) {
				initialPanX = inputInterpreter.getTouchDownX();
				initialPanY = inputInterpreter.getTouchDownY();
			}
			if (inputInterpreter.getPanned() == true) {
				panX = inputInterpreter.getPanX();
				panY = inputInterpreter.getPanY();

				for (int a = 0; a < testsButtons.size; a++)
					testsButtons.get(a).move(0, initialPanY - panY);
			}

		}
		wasPannedBefore = inputInterpreter.getPanned();
	}

	void updateButtonsGravity(double delta) {
		for (int a = 0; a < testsButtons.size; a++)
			testsButtons.get(a).applyGravity(delta);
	}

	void initiateTestsButtons() {

		testsButtons = new Array<TestButton>();

		Array<TestModel> tests = testsManager.getTests();

		for (int a = 0; a < tests.size; a++) {
			testsButtons.add(new TestButton(960, 500 - 80 * a,
					assetsManager.glareButtonVignette, tests.get(a).getName()));
			testsButtons.get(a).loadTick(assetsManager.checked);
		}
		for (int a = 0; a < testsButtons.size; a++) {
			menuTests.add(testsButtons.get(a));
		}
	}

	void optimizeRendering() {
		if (assetsLoaded == true) {
			if (home.getSelection() == false && tests.getSelection() == false
					&& downloads.getSelection() == false) {
				menuHome.stopRendering();
				menuBrief.startRendering();
				menuSphinx.startRendering();
				if (camera.position.x > downloadsScreenPosition + 100)
					menuTests.stopRendering();

			} else {
				if (camera.position.x < briefScreenPosition - 490)
					menuBrief.stopRendering();
				if (camera.position.x < sphinxScreenPosition - 490)
					menuSphinx.stopRendering();

				menuHome.startRendering();
				menuTests.startRendering();
			}
			if (tests.getSelection() == true)
				initial.stopRendering();

		}
	}

	void addMenuBriefStrings(int a) {

		menuBrief.removeAllStrings();
		menuBrief.add("Briefing", new Vector2(2110, 780), new Vector2(3, 1),
				new Vector3(1, 1, 1));
		menuBrief.add("Vocabulary", new Vector2(2080, 630), new Vector2(3, 1),
				new Vector3(1, 1, 1));
		menuBrief.add("Last result", new Vector2(2085, 350), new Vector2(3, 1),
				new Vector3(1, 1, 1));

		VocabularyFormatter vocabuleryFormatter = new VocabularyFormatter();

		String[] formatted = vocabuleryFormatter
				.formatVocabulary(
						testsManager.getTest(testsButtons.get(a).getName())
								.getVocabulary(),
						testsManager.getTest(testsButtons.get(a).getName())
								.getLength());

		for (int c = 0; c < testsManager.getTest(testsButtons.get(a).getName())
				.getLength(); c++) {

			int span = (17 - formatted[c].length()) * 11;

			menuBrief.add(formatted[c], new Vector2(1975 + span, 550 - c * 60),
					new Vector2(1, 1), new Vector3(1, 1, 1));

		}

		int span = testsButtons.get(a).getName().length() * 11;

		menuBrief.add(testsButtons.get(a).getName(), new Vector2(2165 - span,
				710), new Vector2(1, 1), new Vector3(1, 1, 1));

	}

	void addMenuSphinxStrings(int a) {

		menuSphinx.removeAllStrings();

		String[] sentences = testsManager
				.getTest(testsButtons.get(a).getName()).getSentences();

		for (int c = 0; c < testsManager.getTest(testsButtons.get(a).getName())
				.getLength(); c++) {

			int span = (24 - sentences[c].length()) * 11;

			menuSphinx.add(sentences[c],
					new Vector2(2465 + span, 860 - c * 120), new Vector2(4, 1),
					new Vector3(1, 1, 1));

		}

	}

	void manageTestButtonsHighlighting(float x, float y) {
		boolean anyCollisions = false;
		for (int a = 0; a < testsButtons.size; a++) {
			if (testsButtons.get(a).checkCollision((int) x, (int) y) == true) {
				anyCollisions = true;
				for (int b = 0; b < testsButtons.size; b++)
					if (a != b)
						testsButtons.get(b).deselect();

				if (testsButtons.get(a).isHighlighted() == false)
					testsButtons.get(a).reverseSelection();
			}
		}
		if (anyCollisions == false) {
			for (int a = 0; a < testsButtons.size; a++)
				testsButtons.get(a).deselect();
		}

	}

	void recognizeSpeech() throws IOException {
		if (camera.position.x == sphinxScreenPosition) {
		if(speechRecognizer.isAlive() == false)
		speechRecognizer.start();
		if(speechRecognizer.isAlive() ==true){
			menuSphinx.removeAllStrings();
			menuSphinx.add(speechRecognizer.getLastRecognizedWord(),
					new Vector2(2500, 100), new Vector2(4, 1),
					new Vector3(1, 1, 1));
		}
		}
	}
}