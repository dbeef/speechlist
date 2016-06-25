package com.dbeef.speechlist.logics;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.dbeef.speechlist.camera.Camera;
import com.dbeef.speechlist.gui.Button;
import com.dbeef.speechlist.gui.TestButton;
import com.dbeef.speechlist.input.InputInterpreter;
import com.dbeef.speechlist.screen.Screen;
import com.dbeef.speechlist.utils.AssetsManager;

public class ActionManager {

	static final int homeScreenPosition = 720;
	static final int testsScreenPosition = 1200;
	static final int downloadsScreenPosition = 1680;

	int tapX;
	int tapY;
	double panX;
	double panY;
	double initialPanX;
	double initialPanY;

	InputInterpreter inputInterpreter;

	float timer = 0;

	Button home;
	Button tests;
	Button downloads;

	boolean wasPannedBefore = false;
	boolean initialCameraMovements = false;
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
	Screen gui;

	Array<TestButton> sheetButtons;

	public ActionManager(InputInterpreter inputInterpreter, Camera camera,
			Camera guiCamera, Screen initial, Screen gui, Screen menuHome,
			Screen menuTests, Screen menuDownloads,
			AssetsManager assetsManager, Button home, Button tests,
			Button downloads, Array<TestButton> sheetButtons) {
		this.camera = camera;
		this.guiCamera = guiCamera;
		this.initial = initial;
		this.assetsManager = assetsManager;
		this.home = home;
		this.tests = tests;
		this.downloads = downloads;
		this.gui = gui;
		this.menuHome = menuHome;
		this.menuTests = menuTests;
		this.menuDownloads = menuDownloads;
		this.inputInterpreter = inputInterpreter;
		this.sheetButtons = sheetButtons;
	}

	public void updateLogics(float delta) {
		updateInitialScreenLogics(delta);
		updateAssetsLoaderLogics();
		addAssetsToScreens();
		updateCamerasLogics();
		if (isCameraChangingPosition() == false
				&& initialCameraMovements == true) {
			updateButtonsLogics();
			updateFlingCamera();
			manageSheetSliding();
		}
		if (wasPannedBefore == false && inputInterpreter.panned == false
				&& assetsLoaded) {
			updateButtonsGravity(delta);
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
				assetsLoaded = true;
			}

		}
	}

	void addAssetsToScreens() {

		if (assetsLoaded == true && readyToGoMenu == false) {
			home = new Button(520, 713, assetsManager.home);
			tests = new Button(670, 713, assetsManager.pencil);
			downloads = new Button(810, 713, assetsManager.cloud);

			initiateSheetButtons();

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

			readyToGoMenu = true;

		}

	}

	void updateCamerasLogics() {
		if (readyToGoMenu == true
				&& (initial.changeStringAlpha("loading...", 0) == 1)
				&& initialCameraMovements == false) {
			camera.move(homeScreenPosition);
			guiCamera.move(homeScreenPosition);
			initialCameraMovements = true;
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
			manageSheetButtonsCollisions((int) vecNonGui.x, (int) vecNonGui.y);
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
				|| camera.position.x == downloadsScreenPosition)
			return false;
		else
			return true;
	}

	void manageSheetButtonsCollisions(float x, float y) {
		boolean anyCollisions = false;
		for (int a = 0; a < sheetButtons.size; a++) {
			if (sheetButtons.get(a).checkCollision((int) x, (int) y) == true) {
				anyCollisions = true;
				for (int b = 0; b < sheetButtons.size; b++)
					if (a != b)
						sheetButtons.get(b).deselect();
				sheetButtons.get(a).reverseSelection();
			}
		}
		if (anyCollisions == false) {
			for (int a = 0; a < sheetButtons.size; a++)
				sheetButtons.get(a).deselect();
		}
	}

	void manageSheetSliding() {

		if (wasPannedBefore == true && inputInterpreter.getPanned() == false) {
			for (int a = 0; a < sheetButtons.size; a++)
				sheetButtons.get(a).savePositionAsOriginPosition();
		}

		if (tests.getSelection() == true) {

			if (inputInterpreter.getTouchDown() == true) {
				initialPanX = inputInterpreter.getTouchDownX();
				initialPanY = inputInterpreter.getTouchDownY();
			}
			if (inputInterpreter.getPanned() == true) {
				panX = inputInterpreter.getPanX();
				panY = inputInterpreter.getPanY();

				for (int a = 0; a < sheetButtons.size; a++)
					sheetButtons.get(a).move(0, initialPanY - panY);
			}

		}
		wasPannedBefore = inputInterpreter.getPanned();
	}

	void updateButtonsGravity(double delta) {
		for (int a = 0; a < sheetButtons.size; a++)
			sheetButtons.get(a).applyGravity(delta);
	}

	void initiateSheetButtons() {

		sheetButtons = new Array<TestButton>();
		sheetButtons.add(new TestButton(960, 500,
				assetsManager.glareButtonVignette, "Sample test"));
		sheetButtons.add(new TestButton(960, 420,
				assetsManager.glareButtonVignette, "Sample test"));
		sheetButtons.add(new TestButton(960, 340,
				assetsManager.glareButtonVignette, "Sample test"));

		for (int a = 0; a < sheetButtons.size; a++) {
			menuTests.add(sheetButtons.get(a));
		}
	}
}