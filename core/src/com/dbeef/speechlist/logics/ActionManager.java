package com.dbeef.speechlist.logics;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;
import com.badlogic.gdx.graphics.g2d.freetype.FreeType.GlyphSlot;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.dbeef.speechlist.camera.Camera;
import com.dbeef.speechlist.files.AssetsManager;
import com.dbeef.speechlist.files.TestsManager;
import com.dbeef.speechlist.gui.Button;
import com.dbeef.speechlist.gui.SolutionInput;
import com.dbeef.speechlist.gui.TestButton;
import com.dbeef.speechlist.input.InputInterpreter;
import com.dbeef.speechlist.internet.DownloadableTestsManager;
import com.dbeef.speechlist.internet.RESTClient;
import com.dbeef.speechlist.models.Test;
import com.dbeef.speechlist.screen.Screen;
import com.dbeef.speechlist.text.DefaultStringsSetter;
import com.dbeef.speechlist.utils.Variables;

public class ActionManager {

	DownloadableTestsManager downloadableTestsManager;
	RESTClient client;
	Variables variables = new Variables();
	InputInterpreter inputInterpreter;
	TestsManager testsManager;
	Button home;
	Button tests;
	Button downloads;
	Button accept;
	Button decline;
	Button left;
	Button right;
	AssetsManager assetsManager;
	Camera camera;
	Camera guiCamera;
	Screen initial;
	Screen menuHome;
	Screen menuTests;
	Screen menuDownloads;
	Screen menuBrief;
	Screen gui;
	Array<TestButton> testsButtons;
	Array<TestButton> downloadableTestsButtons;
	Array<Screen> solvingScreens;
	Array<BitmapFont> fonts;
	Texture mainBackground;
	SolutionInput solutionInput;
	float timerLoading = 0;

	boolean addedDownloadables = false;
	boolean wasPannedBefore = false;
	boolean initialCameraMovementsDone = false;
	boolean logoCameOnScreen = false;
	boolean loadingTextAdded = false;
	boolean assetsLoaded = false;
	boolean readyToGoMenu = false;
	boolean startedLoadingAssets = false;
	boolean initiatedInput = false;

	public ActionManager(Camera camera, Camera guiCamera, Screen initial,
			Screen gui, Screen menuHome, Screen menuTests,
			Screen menuDownloads, Screen menuBrief,
			Array<Screen> solvingScreens, Array<BitmapFont> fonts,
			Texture mainBackground, SolutionInput solutionInput) {
		this.camera = camera;
		this.guiCamera = guiCamera;
		this.initial = initial;
		this.gui = gui;
		this.menuHome = menuHome;
		this.menuTests = menuTests;
		this.menuDownloads = menuDownloads;
		this.menuBrief = menuBrief;
		this.solvingScreens = solvingScreens;
		this.fonts = fonts;
		this.mainBackground = mainBackground;
		this.solutionInput = solutionInput;
		client = new RESTClient();
		client.start();
	}

	public void updateLogics(float delta) throws InterruptedException {

		if (startedLoadingAssets == true && assetsManager.loaded == false)
			delta = 0;

		optimizeRendering();
		updateInitialScreenLogics(delta);
		updateAssetsLoaderLogics();
		addAssetsToScreens();
		updateCamerasLogics();
		updateButtonsGravity(delta);
		manageMenuDownloadsElements();
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
			timerLoading += delta;
			if (timerLoading > 0.01) {
				timerLoading = 0;

				if (initial.changeStringAlpha("loading...", 0) < 1)
					initial.changeStringAlpha("loading...", 0.005f);
			}
		}
	}

	void updateAssetsLoaderLogics() {
		if (loadingTextAdded == true) {
			if (startedLoadingAssets == false) {
				assetsManager = new AssetsManager();
				testsManager = new TestsManager();
				startedLoadingAssets = true;
				assetsManager.run();
			}
		}
		if (startedLoadingAssets == true && assetsManager.loaded == true)
			assetsLoaded = true;

	}

	void addAssetsToScreens() {
		if (assetsLoaded == true && readyToGoMenu == false) {
			solutionInput.setButtonTick(assetsManager.tick);
			solutionInput.setButtonTexture(assetsManager.glareButtonVignette);
			solutionInput.setButtonFont(fonts.get(3));
			initiateGuiButtons();
			initiateTestsButtons();
			addMenuHomeStaticElements();
			addGuiStaticElements();
			addMenuTestsStaticElements();
			addMenuDownloadsStaticElements();
			addMenuBriefStaticElements();
			readyToGoMenu = true;
		}
	}

	void updateCamerasLogics() {

		if (camera.position.x > (variables.getInitialScreenPosition() + variables
				.getHomeScreenPosition()) / 2 && initiatedInput == false) {
			inputInterpreter = new InputInterpreter();
			inputInterpreter.loadGesturesReceivers(camera, guiCamera, home,
					tests, downloads, accept, decline, left, right,
					testsButtons, menuBrief, solvingScreens, testsManager);
			inputInterpreter.setInitialCameraMovementsDone();
			inputInterpreter.loadFonts(fonts);
			inputInterpreter.loadMainBackground(mainBackground);
			inputInterpreter.setAssetsManager(assetsManager);
			inputInterpreter.setSolutionInput(solutionInput);
			initiatedInput = true;
		}

		if (readyToGoMenu == true
				&& (initial.changeStringAlpha("loading...", 0) == 1)
				&& initialCameraMovementsDone == false) {
			camera.move(variables.getHomeScreenPosition());
			guiCamera.move(variables.getHomeScreenPosition());
			initialCameraMovementsDone = true;
		}

		if (camera.position.x > variables.getDownloadsScreenPosition()) {
			if (camera.position.x < variables.getBriefScreenPosition()) {
				guiCamera.changePosition(variables.getGuiCameraPosition()
						+ (camera.position.x - variables
								.getDownloadsScreenPosition()));
				guiCamera.resetAccumulated();
			} else {
				guiCamera.changePosition(variables.getGuiCameraPosition()
						+ (variables.getBriefScreenPosition() - variables
								.getDownloadsScreenPosition()));
				guiCamera.resetAccumulated();

			}
		}
	}

	void updateButtonsGravity(double delta) {
		if (inputInterpreter != null)
			if (wasPannedBefore == false && inputInterpreter.panned == false
					&& assetsLoaded)
				for (int a = 0; a < testsButtons.size; a++)
					testsButtons.get(a).applyGravity(delta);
	}

	void initiateTestsButtons() {
		testsButtons = new Array<TestButton>();
		Array<Test> tests = testsManager.getTests();

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
				for (Screen screen : solvingScreens)
					screen.startRendering();
				// menuSphinx.startRendering();
				if (camera.position.x > variables.getDownloadsScreenPosition() + 100)
					menuTests.stopRendering();

			} else {
				if (camera.position.x < variables.getBriefScreenPosition() - 490)
					menuBrief.stopRendering();
				if (camera.position.x < variables.getSphinxScreenPosition() - 490) {
					solvingScreens.clear();
				}
				menuHome.startRendering();
				menuTests.startRendering();
			}
			if (tests.getSelection() == true)
				initial.stopRendering();
		}
	}

	void addMenuHomeStaticElements() {
		menuHome.add(assetsManager.clock, new Vector2(550, 530));
		menuHome.add(assetsManager.chart, new Vector2(690, 530));
		menuHome.add(assetsManager.checked, new Vector2(835, 530));
		menuHome = new DefaultStringsSetter().setMenuHomeStrings(menuHome);
	}

	void addGuiStaticElements() {
		gui.add(home);
		gui.add(tests);
		gui.add(downloads);
		gui.add(assetsManager.logoLittle, new Vector2(705, 680));
		gui.add(assetsManager.logoLittle, new Vector2(1180, 750));
	}

	void initiateGuiButtons() {
		home = new Button(520, 713, assetsManager.home);
		tests = new Button(670, 713, assetsManager.pencil);
		downloads = new Button(810, 713, assetsManager.cloud);
		home.select();
	}

	void addMenuTestsStaticElements() {
		menuTests = new DefaultStringsSetter().setMenuTestsStrings(menuTests);
	}

	void addMenuDownloadsStaticElements() {
		menuDownloads.add(assetsManager.sadPhone, new Vector2(1560, 200));
		menuDownloads = new DefaultStringsSetter()
				.setMenuDownloadsStrings(menuDownloads);
	}

	void manageMenuDownloadsElements() throws InterruptedException {

		if (loadingTextAdded == true && client != null
				&& client.getUNIQUE_IDS_RETRIEVED() == true
				&& downloadableTestsManager == null && testsManager != null) {

			downloadableTestsManager = new DownloadableTestsManager();
			downloadableTestsManager.run(client.getUniqueIdContainer(),
					testsManager.getTests());
		}

		if (loadingTextAdded == true && downloadableTestsManager != null
				&& downloadableTestsManager.RETRIEVED_DOWNLOADABLES()
				&& addedDownloadables == false) {

			downloadableTestsButtons = new Array<TestButton>();

			for (int a = 0; a < downloadableTestsManager.getNames().size(); a++) {
				downloadableTestsButtons.add(new TestButton(new Variables()
						.getDownloadsScreenPosition() - 240, 500 - 80 * a,
						assetsManager.glareButtonVignette,
						downloadableTestsManager.getNames().get(a)));
				downloadableTestsButtons.get(a).loadTick(assetsManager.checked);
			}

			for (int a = 0; a < downloadableTestsButtons.size; a++) {
				menuDownloads.add(downloadableTestsButtons.get(a));
			}

			testsButtons.addAll(downloadableTestsButtons);

			addedDownloadables = true;
		}

		if (assetsManager != null && assetsManager.loaded == true) {
			if (client != null && client.getUNIQUE_IDS_RETRIEVED() == true) {
				menuDownloads.removeTextureWithPosition(new Vector2(1560, 200));
				menuDownloads = new DefaultStringsSetter()
						.deleteMenuDownloadsStrings(menuDownloads);
			}
			if (client != null && client.getUNIQUE_IDS_RETRIEVED() == false
					&& menuDownloads.textureArrayEmpty() == true) {
				menuDownloads.add(assetsManager.sadPhone,
						new Vector2(1560, 200));
				menuDownloads = new DefaultStringsSetter()
						.setMenuDownloadsStrings(menuDownloads);
			}
			if (client != null && client.getUNIQUE_IDS_RETRIEVED() == false
					&& client.FAILED() == false) {
				// Still retrieving, add image with "Pulling form server..."
			}
		}

	}

	void addMenuBriefStaticElements() {
		accept = new Button(1115, 25, assetsManager.checked);
		decline = new Button(1215, 25, assetsManager.cross);
		left = new Button(970, 25, assetsManager.left);
		right = new Button(1360, 25, assetsManager.right);
		decline.setMultiplier(4);
		accept.setMultiplier(4);
		left.setMultiplier(4);
		right.setMultiplier(4);
		gui.add(accept);
		gui.add(decline);
		gui.add(left);
		gui.add(right);
	}
}