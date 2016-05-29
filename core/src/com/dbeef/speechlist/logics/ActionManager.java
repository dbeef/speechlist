package com.dbeef.speechlist.logics;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.dbeef.speechlist.camera.Camera;
import com.dbeef.speechlist.gui.Button;
import com.dbeef.speechlist.screen.Screen;
import com.dbeef.speechlist.utils.AssetsManager;

public class ActionManager {

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

	public ActionManager(Camera camera, Camera guiCamera, Screen initial, Screen gui, Screen menuHome,
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
		
	}

	public void updateLogics(float delta) {
		if (Math.abs(camera.position.x - 240) < 10) {
			logoCameOnScreen = true;
		}
		if (logoCameOnScreen == true && loadingTextAdded == false) {
			initial.add("loading...", new Vector2(205, 350), new Vector2(2, 0),
					new Vector3(0, 0, 0));
			loadingTextAdded = true;
		}
		if (loadingTextAdded == true) {

			if (assetsLoaded == false) {
				assetsManager = new AssetsManager();
				assetsLoaded = true;
			}
			timer += delta;
			if (timer > 0.01) {
				timer = 0;

				if (initial.changeStringAlpha("loading...", 0) < 1)
					initial.changeStringAlpha("loading...", 0.005f);

			}

		}
		if (assetsLoaded == true && readyToGoMenu == false) {
			home = new Button(460, 700, assetsManager.home);
			tests = new Button(600, 700, assetsManager.pencil);
			downloads = new Button(740, 700, assetsManager.cloud);
			
			readyToGoMenu = true;
		
			gui.add(home);
			gui.add(tests);
			gui.add(downloads);


		}
		if (readyToGoMenu == true
				&& (initial.changeStringAlpha("loading...", 0) == 1)){
			camera.moveRight(720);
			guiCamera.moveRight(720);
		}

	}
	
}