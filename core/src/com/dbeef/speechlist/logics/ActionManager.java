package com.dbeef.speechlist.logics;

import java.io.IOException;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.dbeef.speechlist.camera.Camera;
import com.dbeef.speechlist.files.AssetsManager;
import com.dbeef.speechlist.gui.Button;
import com.dbeef.speechlist.gui.TestButton;
import com.dbeef.speechlist.input.InputInterpreter;
import com.dbeef.speechlist.recognition.DefaultFilesWriter;
import com.dbeef.speechlist.recognition.SpeechRecognizer;
import com.dbeef.speechlist.screen.Screen;
import com.dbeef.speechlist.tests.TestModel;
import com.dbeef.speechlist.tests.TestsManager;
import com.dbeef.speechlist.text.DefaultStringsManager;
import com.dbeef.speechlist.utils.Variables;

public class ActionManager {

	Variables variables = new Variables();
	SpeechRecognizer speechRecognizer;
	InputInterpreter inputInterpreter;
	TestsManager testsManager;
	Button home;
	Button tests;
	Button downloads;
	Button accept;
	Button decline;
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

	float timer = 0;

	boolean wasPannedBefore = false;
	boolean initialCameraMovementsDone = false;
	boolean logoCameOnScreen = false;
	boolean loadingTextAdded = false;
	boolean readyToGoMenu = false;
	boolean assetsLoaded = false;
	boolean startedLoadingAssets = false;

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
		speechRecognizer = new SpeechRecognizer();
	}

	public void updateLogics(float delta) {

		if(assetsManager != null && assetsManager.getAssetsLoaded() == true)
			assetsLoaded = true;
	
		optimizeRendering();
		updateInitialScreenLogics(delta);
		updateAssetsLoaderLogics();
		addAssetsToScreens();
		updateCamerasLogics();
		updateButtonsGravity(delta);

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
			if (startedLoadingAssets == false) {
				assetsManager = new AssetsManager();
				assetsManager.start();
				testsManager = new TestsManager();
				DefaultFilesWriter defaultFilesWriter = new DefaultFilesWriter();
				defaultFilesWriter.run();
				startedLoadingAssets = true;
			}
		}
	}

	void addAssetsToScreens() {
		if (assetsLoaded == true && readyToGoMenu == false) {
			initiateGuiButtons();
			initiateTestsButtons();
			addMenuHomeStaticElements();
			addGuiStaticElements();
			addMenuTestsStaticElements();
			addMenuDownloadsStaticElements();
			addMenuBriefStaticElements();
			inputInterpreter.loadGesturesReceivers(camera, guiCamera, home,
					tests, downloads, accept, decline, testsButtons, menuBrief,
					menuSphinx, testsManager);
			readyToGoMenu = true;
		}

	}

	void updateCamerasLogics() {
		if (readyToGoMenu == true
				&& (initial.changeStringAlpha("loading...", 0) == 1)
				&& initialCameraMovementsDone == false) {
			camera.move(variables.getHomeScreenPosition());
			guiCamera.move(variables.getHomeScreenPosition());
			initialCameraMovementsDone = true;
			inputInterpreter.setInitialCameraMovementsDone();

		}
		if (camera.position.x > variables.getDownloadsScreenPosition()) {
			guiCamera.changePosition(variables.getGuiCameraPosition()
					+ (camera.position.x - variables
							.getDownloadsScreenPosition()));
		}

	}

	void updateButtonsGravity(double delta) {
		if (wasPannedBefore == false && inputInterpreter.panned == false
				&& assetsLoaded)
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

		if (assetsLoaded == true && camera.isCameraChangingPosition() == true) {
			if (gui.allButtonsDeselected() == true) {
				menuHome.stopRendering();
				menuBrief.startRendering();
				menuSphinx.startRendering();
				if (camera.position.x > variables.getDownloadsScreenPosition() + 100)
					menuTests.stopRendering();

			} else {
				if (camera.position.x < variables.getBriefScreenPosition() - 490)
					menuBrief.stopRendering();
				if (camera.position.x < variables.getSphinxScreenPosition() - 490)
					menuSphinx.stopRendering();

				menuHome.startRendering();
				menuTests.startRendering();
			}
			if (tests.getSelection() == true)
				initial.stopRendering();
		}
	}

	void recognizeSpeech() throws IOException {
		if (camera.position.x == variables.getSphinxScreenPosition()) {
			// if (speechRecognizer.isAlive() == false)
			// speechRecognizer.start();
			// if (speechRecognizer.isAlive() == true) {
			// menuSphinx.removeAllStrings();
			// menuSphinx.add(speechRecognizer.getLastRecognizedWord(),
			// new Vector2(2500, 100), new Vector2(4, 1), new Vector3(
			// 1, 1, 1));
			// }
		}
	}

	void addMenuHomeStaticElements() {
		menuHome.add(assetsManager.clock, new Vector2(550, 530));
		menuHome.add(assetsManager.chart, new Vector2(690, 530));
		menuHome.add(assetsManager.checked, new Vector2(835, 530));
		menuHome = new DefaultStringsManager().setMenuHomeStrings(menuHome);
	}

	void addGuiStaticElements() {
		gui.add(home);
		gui.add(tests);
		gui.add(downloads);
		gui.add(assetsManager.logoLittle, new Vector2(705, 680));
	}

	void initiateGuiButtons() {
		home = new Button(520, 713, assetsManager.home);
		tests = new Button(670, 713, assetsManager.pencil);
		downloads = new Button(810, 713, assetsManager.cloud);
		home.select();
	}

	void addMenuTestsStaticElements() {
		menuTests = new DefaultStringsManager().setMenuTestsStrings(menuTests);
	}

	void addMenuDownloadsStaticElements() {
		menuDownloads.add(assetsManager.sadPhone, new Vector2(1560, 200));
		menuDownloads = new DefaultStringsManager()
				.setMenuDownloadsStrings(menuDownloads);
	}

	void addMenuBriefStaticElements() {
		accept = new Button(2080, 100, assetsManager.checked);
		decline = new Button(2180, 100, assetsManager.cross);
		decline.setMultiplier(4);
		accept.setMultiplier(4);
		menuBrief.add(accept);
		menuBrief.add(decline);
	}
}