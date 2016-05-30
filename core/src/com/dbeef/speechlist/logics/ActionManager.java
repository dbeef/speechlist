package com.dbeef.speechlist.logics;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.dbeef.speechlist.camera.Camera;
import com.dbeef.speechlist.gui.Button;
import com.dbeef.speechlist.input.InputInterpreter;
import com.dbeef.speechlist.screen.Screen;
import com.dbeef.speechlist.utils.AssetsManager;

public class ActionManager {

	static final int menuScreenPosition = 720;
	static final int testsScreenPosition = 1200;

	int tapX;
	int tapY;

	InputInterpreter inputInterpreter;

	float timer = 0;

	Button home;
	Button tests;
	Button downloads;

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

	public ActionManager(InputInterpreter inputInterpreter, Camera camera,
			Camera guiCamera, Screen initial, Screen gui, Screen menuHome,
			Screen menuTests, Screen menuDownloads,
			AssetsManager assetsManager, Button home, Button tests,
			Button downloads) {
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
	}

	public void updateLogics(float delta) {
		updateInitialScreenLogics(delta);
		updateAssetsLoaderLogics();
		addAssetsToScreens();
		updateCamerasLogics();
		updateButtonsLogics();
	}

	void updateInitialScreenLogics(float delta) {
		if (Math.abs(camera.position.x - 240) < 10) {
			logoCameOnScreen = true;
		}
		if (logoCameOnScreen == true && loadingTextAdded == false) {
			initial.add("loading...", new Vector2(215, 350), new Vector2(2, 0),
					new Vector3(0, 0, 0));
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
			home = new Button(550, 728, assetsManager.home);
			tests = new Button(700, 728, assetsManager.pencil);
			downloads = new Button(840, 728, assetsManager.cloud);

			home.select();

			gui.add(home);
			gui.add(tests);
			gui.add(downloads);

			gui.add(assetsManager.homeBackground, new Vector2(480, 0));
			gui.add(assetsManager.guiFrame, new Vector2(480, 720));
			gui.add(assetsManager.logoLittle, new Vector2(700, 680));

			menuHome.add(assetsManager.clock, new Vector2(550, 530));
			menuHome.add(assetsManager.chart, new Vector2(690, 530));
			menuHome.add(assetsManager.checked, new Vector2(835, 530));

			menuHome.add("Summary", new Vector2(655, 660), new Vector2(3, 1),
					new Vector3(0, 0, 0));

			menuHome.add("Time spent:", new Vector2(545, 510),
					new Vector2(2, 1), new Vector3(0, 0, 0));
			menuHome.add("Accuracy:", new Vector2(690, 510), new Vector2(2, 1),
					new Vector3(0, 0, 0));
			menuHome.add("Tests solved:", new Vector2(820, 510), new Vector2(2,
					1), new Vector3(0, 0, 0));

			menuHome.add("Speechlist", new Vector2(615, 370),
					new Vector2(1, 1), new Vector3(0, 0, 0));

			menuHome.add("Copyright 2016 Daniel Zalega", new Vector2(650, 290),
					new Vector2(6, 1), new Vector3(0, 0, 0));
			menuHome.add("This software uses Sphinx4, which is under:",
					new Vector2(615, 255), new Vector2(6, 1), new Vector3(0, 0,
							0));
			menuHome.add("Copyright 1999-2015 Carnegie Mellon University.  ",
					new Vector2(600, 220), new Vector2(6, 1), new Vector3(0, 0,
							0));
			menuHome.add(
					"Portions Copyright 2002-2008 Sun Microsystems, Inc.  ",
					new Vector2(585, 185), new Vector2(6, 1), new Vector3(0, 0,
							0));
			menuHome.add(
					"Portions Copyright 2002-2008 Mitsubishi Electric Research Laboratories.",
					new Vector2(535, 150), new Vector2(6, 1), new Vector3(0, 0,
							0));
			menuHome.add("Portions Copyright 2013-2015 Alpha Cephei, Inc.",
					new Vector2(590, 115), new Vector2(6, 1), new Vector3(0, 0,
							0));
			menuHome.add("All Rights Reserved.", new Vector2(680, 80),
					new Vector2(6, 1), new Vector3(0, 0, 0));
			menuHome.add("Version 0.1", new Vector2(700, 45),
					new Vector2(6, 1), new Vector3(0, 0, 0));
			readyToGoMenu = true;

		}

	}

	void updateCamerasLogics() {

		if (readyToGoMenu == true
				&& (initial.changeStringAlpha("loading...", 0) == 1)) {
			camera.moveRight(menuScreenPosition);
			guiCamera.moveRight(menuScreenPosition);
		}

	}

	void updateButtonsLogics() {
		if (inputInterpreter.touched == true) {

			Vector3 vec = inputInterpreter.getLastTouchPosition();

			guiCamera.unproject(vec);

			System.out.println(home.checkCollision((int) vec.x, (int) vec.y));
		}

	}
}