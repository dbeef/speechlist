package com.dbeef.speechlist.logics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.dbeef.speechlist.camera.Camera;
import com.dbeef.speechlist.files.AssetsManager;
import com.dbeef.speechlist.files.ResultsManager;
import com.dbeef.speechlist.files.TestsManager;
import com.dbeef.speechlist.gui.Button;
import com.dbeef.speechlist.gui.SolutionInput;
import com.dbeef.speechlist.gui.TestButton;
import com.dbeef.speechlist.input.InputGestures;
import com.dbeef.speechlist.internet.DownloadManager;
import com.dbeef.speechlist.internet.RESTClient;
import com.dbeef.speechlist.models.Test;
import com.dbeef.speechlist.screen.Screen;
import com.dbeef.speechlist.text.DefaultStringsSetter;
import com.dbeef.speechlist.utils.TimeSpentObserver;
import com.dbeef.speechlist.utils.Variables;

public class ActionManager {

	ResultsManager resultsManager;
	TimeSpentObserver timeSpentObserver;
	DownloadManager downloadableTestsManager;
	RESTClient client;
	InputGestures inputInterpreter;
	TestsManager testsManager;
	Button home;
	Button tests;
	Button downloads;
	Button accept;
	Button decline;
	Button left;
	Button right;

	Screen[] tests_local;
	Screen[] tests_server;

	Button[] testCategories_local;
	Button[] testCategories_server;

	AssetsManager assetsManager;
	Camera camera;
	Camera guiCamera;

	Screen initial;
	Screen menuHome;
	Screen menuTests;
	Screen menuDownloads;
	Screen menuBrief;
	Screen gui;

	Button testsBackButton;

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
	boolean testScreensCreated = false;

	public ActionManager(Camera camera, Camera guiCamera, Screen initial,
			Screen gui, Screen menuHome, Screen menuTests,
			Screen menuDownloads, Screen menuBrief,
			Array<Screen> solvingScreens, Array<BitmapFont> fonts,
			Texture mainBackground, SolutionInput solutionInput,
			Screen[] tests_local, Screen[] tests_server) {
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
		this.tests_local = tests_local;
		this.tests_server = tests_server;
		client = new RESTClient();
		client.start();
		testCategories_local = new Button[4];
		testCategories_server = new Button[4];
		resultsManager = new ResultsManager();
		timeSpentObserver = new TimeSpentObserver(resultsManager.getTimeSpent());
	}

	public void updateLogics(float delta) throws InterruptedException {

		if (startedLoadingAssets == true && assetsManager.loaded == false)
			delta = 0;

		if (Gdx.input.isKeyPressed(Keys.BACK)) {
			if (solutionInput != null && solutionInput.getVisibility() == true) {
				solutionInput.hide();
			}
		}

		optimizeRendering();
		updateInitialScreenLogics(delta);
		updateAssetsLoaderLogics();
		addAssetsToScreens();
		createTestScreens();
		updateCamerasLogics();
		updateButtonsGravity(delta);
		manageMenuDownloadsElements();
		updateMenuHomeCounters(delta);

	}

	void updateInitialScreenLogics(float delta) {
		if (Math.abs(camera.position.x - 240) < 1) {
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
				resultsManager.loadData();
				timeSpentObserver = new TimeSpentObserver(
						resultsManager.getTimeSpent());
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

		if (camera.position.x > Variables.TESTS_SCREEN_POSITION + 100) {
			menuHome.stopRendering();
		} else
			menuHome.startRendering();

		if (camera.position.x < Variables.TESTS_SCREEN_POSITION - 100) {
			menuDownloads.stopRendering();
		} else
			menuDownloads.startRendering();

		if (camera.position.x > Variables.BRIEF_SCREEN_POSITION + 50) {
			menuDownloads.stopRendering();
		} else
			menuDownloads.startRendering();

		if (camera.position.x > Variables.BRIEF_SCREEN_POSITION - 50) {
			for (Screen s : solvingScreens)
				s.startRendering();
			for (int a = 0; a < tests_local.length; a++)
				tests_local[a].stopRendering();
			for (int a = 0; a < tests_local.length; a++)
				tests_server[a].stopRendering();
		} else {
			for (Screen s : solvingScreens)
				s.stopRendering();
			for (int a = 0; a < tests_local.length; a++)
				tests_local[a].startRendering();
			for (int a = 0; a < tests_local.length; a++)
				tests_server[a].startRendering();

			menuDownloads.startRendering();
		}

		if (camera.position.x > (Variables.INITIAL_SCREEN_POSITION + Variables.HOME_SCREEN_POSITION) / 2
				&& initiatedInput == false) {
			inputInterpreter = new InputGestures();
			inputInterpreter.loadGesturesReceivers(camera, guiCamera, home,
					tests, downloads, accept, decline, left, right,
					testsButtons, menuBrief, solvingScreens, testsManager);
			inputInterpreter.setInitialCameraMovementsDone();
			inputInterpreter.loadFonts(fonts);
			inputInterpreter.loadMainBackground(mainBackground);
			inputInterpreter.setAssetsManager(assetsManager);
			inputInterpreter.setSolutionInput(solutionInput);
			inputInterpreter.setTestCategories(testCategories_local,
					testCategories_server);
			inputInterpreter.setTestScreens(tests_local, tests_server,
					testsBackButton);
			inputInterpreter.setResultsManager(resultsManager);
			initiatedInput = true;
		}

		if (readyToGoMenu == true
				&& (initial.changeStringAlpha("loading...", 0) == 1)
				&& initialCameraMovementsDone == false) {
			camera.move(Variables.HOME_SCREEN_POSITION);
			guiCamera.move(Variables.HOME_SCREEN_POSITION);
			initialCameraMovementsDone = true;
		}

		if (camera.position.x > Variables.DOWNLOADS_SCREEN_POSITION) {
			if (camera.position.x < Variables.BRIEF_SCREEN_POSITION) {
				guiCamera
						.changePosition(Variables.GUI_CAMERA_POSITION
								+ (camera.position.x - Variables.DOWNLOADS_SCREEN_POSITION));
				guiCamera.resetAccumulated();
			} else {
				guiCamera
						.changePosition(Variables.GUI_CAMERA_POSITION
								+ (Variables.BRIEF_SCREEN_POSITION - Variables.DOWNLOADS_SCREEN_POSITION));
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
			testsButtons.get(a).setCategory(tests.get(a).getCategory());
		}

		for (int a = 0; a < testsButtons.size; a++) {
			if (testsButtons.get(a).getCategory()
					.equals(Variables.CATEGORY_VOCABULARY)) {
				testsButtons.get(a).setPosition(960,
						500 - 80 * tests_local[0].getTestsButtons().size);
				tests_local[0].add(testsButtons.get(a));
			} else if (testsButtons.get(a).getCategory()
					.equals(Variables.CATEGORY_IDIOMS)) {
				testsButtons.get(a).setPosition(960,
						500 - 80 * tests_local[1].getTestsButtons().size);
				tests_local[1].add(testsButtons.get(a));
			} else if (testsButtons.get(a).getCategory()
					.equals(Variables.CATEGORY_TENSES)) {
				testsButtons.get(a).setPosition(960,
						500 - 80 * tests_local[2].getTestsButtons().size);
				tests_local[2].add(testsButtons.get(a));
			} else if (testsButtons.get(a).getCategory()
					.equals(Variables.CATEGORY_VARIOUS)) {
				testsButtons.get(a).setPosition(960,
						500 - 80 * tests_local[3].getTestsButtons().size);
				tests_local[3].add(testsButtons.get(a));
			}
		}

		for (int b = 0; b < tests_local.length; b++) {
			int buttons_with_Y_below_120 = 0;
			Array<TestButton> testButtons = tests_local[b].getTestsButtons();

			for (int a = 0; a < testButtons.size; a++) {
				if (testButtons.get(a).getY() < 120)
					buttons_with_Y_below_120++;

				testButtons.get(a).setMaxDrawingY(540);
				testButtons.get(a).setMinDrawingY(85);
			}
			for (int a = 0; a < testButtons.size; a++) {
				testButtons.get(a).setMaxMovingY(
						(int) testButtons.get(a).getY()
								+ (buttons_with_Y_below_120 - 1) * 80);
				testButtons.get(a).setMovingMinY(
						(int) testButtons.get(a).getY());
			}
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
				if (camera.position.x > Variables.DOWNLOADS_SCREEN_POSITION + 100)
					menuTests.stopRendering();

			} else {
				if (camera.position.x < Variables.BRIEF_SCREEN_POSITION - 490)
					menuBrief.stopRendering();
				if (camera.position.x < Variables.SPHINX_SCREEN_POSITION - 490) {
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

		testCategories_local[0] = new Button(
				Variables.TESTS_SCREEN_POSITION - 205, 390,
				assetsManager.vocabulary);
		testCategories_local[1] = new Button(Variables.TESTS_SCREEN_POSITION
				+ assetsManager.idioms.getWidth() - 175, 390,
				assetsManager.idioms);
		testCategories_local[2] = new Button(
				Variables.TESTS_SCREEN_POSITION - 205, 115,
				assetsManager.tenses);
		testCategories_local[3] = new Button(Variables.TESTS_SCREEN_POSITION
				+ assetsManager.idioms.getWidth() - 175, 115,
				assetsManager.various);

		menuTests.add(testCategories_local[0]);
		menuTests.add(testCategories_local[1]);
		menuTests.add(testCategories_local[2]);
		menuTests.add(testCategories_local[3]);
	}

	void addMenuDownloadsStaticElements() {
		menuDownloads.add(assetsManager.sadPhone, new Vector2(1560, 200));
		menuDownloads = new DefaultStringsSetter()
				.setMenuDownloadsStrings(menuDownloads);
		/*
		 * testCategories_server = new Button[4]; testCategories_server[0] = new
		 * Button( Variables.DOWNLOADS_SCREEN_POSITION- 230, 350,
		 * assetsManager.vocabulary); testCategories_server[1] = new Button(
		 * Variables.DOWNLOADS_SCREEN_POSITION+ assetsManager.idioms.getWidth()
		 * - 220, 350, assetsManager.idioms); testCategories_server[2] = new
		 * Button( Variables.DOWNLOADS_SCREEN_POSITION- 220, 50,
		 * assetsManager.tenses); testCategories_server[3] = new Button(
		 * Variables.DOWNLOADS_SCREEN_POSITION+ assetsManager.idioms.getWidth()
		 * - 220, 50, assetsManager.various);
		 * 
		 * menuDownloads.add(testCategories_server[0]);
		 * menuDownloads.add(testCategories_server[1]);
		 * menuDownloads.add(testCategories_server[2]);
		 * menuDownloads.add(testCategories_server[3]);
		 */

	}

	void manageMenuDownloadsElements() throws InterruptedException {
		
		if (loadingTextAdded == true && client != null
				&& client.getUNIQUE_IDS_RETRIEVED() == true
				&& downloadableTestsManager == null && testsManager != null) {

			System.out
					.println("Sending signal to download manager to start downloading test names");

			downloadableTestsManager = new DownloadManager();
			downloadableTestsManager.run(client.getUniqueIdContainer(),
					testsManager.getTests());
		}

		if (loadingTextAdded == true && downloadableTestsManager != null
				&& downloadableTestsManager.RETRIEVED_DOWNLOADABLES()
				&& addedDownloadables == false) {

			System.out.println("Adding downloadable tests buttons");

			downloadableTestsButtons = new Array<TestButton>();

			for (int a = 0; a < downloadableTestsManager.getNames().size(); a++) {
				downloadableTestsButtons.add(new TestButton(
						Variables.DOWNLOADS_SCREEN_POSITION - 240,
						500 - 80 * a, assetsManager.glareButtonVignette,
						downloadableTestsManager.getNames().get(a)));
				downloadableTestsButtons.get(a).loadTick(assetsManager.checked);
			}

			for (int a = 0; a < downloadableTestsButtons.size; a++) {
				menuDownloads.add(downloadableTestsButtons.get(a));
				// Need to make something like getNameWithCategory or getName +
				// getCategory
				// Or if not, downloads will have just a simple menu without
				// categories
			}
			testsButtons.addAll(downloadableTestsButtons);
			addedDownloadables = true;
		}

		if (assetsManager != null && assetsManager.loaded == true) {
			if (client != null && client.getUNIQUE_IDS_RETRIEVED() == true) {
				System.out.println("Deleting default downloads screen strings");
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
		accept = new Button(1115, 20, assetsManager.checked);
		decline = new Button(1215, 20, assetsManager.cross);
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

	void createTestScreens() {
		if (testScreensCreated == false && assetsLoaded == true) {
			testsBackButton = new Button(Variables.TESTS_SCREEN_POSITION - 25,
					30, assetsManager.cross);

			for (int a = 0; a < tests_local.length; a++) {
				tests_local[a].add(assetsManager.mainBackground_middle,
						new Vector2(Variables.TESTS_SCREEN_POSITION - 240, 0));
				tests_local[a].hide();
				tests_local[a].add(testsBackButton);
			}
			for (int a = 0; a < tests_server.length; a++) {
				tests_server[a].add(assetsManager.mainBackground_middle,
						new Vector2(Variables.DOWNLOADS_SCREEN_POSITION - 240,
								0));
				tests_server[a].hide();
			}
			testScreensCreated = true;
		}
	}

	void updateMenuHomeCounters(float delta) {

		if (assetsLoaded == true && home.getSelection() == true
				&& timeSpentObserver.getRefreshed() == true) {
			menuHome.removeAllStrings();
			menuHome = new DefaultStringsSetter().setMenuHomeStrings(menuHome);
			if (timeSpentObserver.getTimeSpent().length() == 3)
				menuHome.add(timeSpentObserver.getTimeSpent(), new Vector2(550,
						485), new Vector2(1, 1), new Vector3(1, 1, 1));
			if (timeSpentObserver.getTimeSpent().length() == 2)
				menuHome.add(timeSpentObserver.getTimeSpent(), new Vector2(560,
						485), new Vector2(1, 1), new Vector3(1, 1, 1));
		}

		if (assetsLoaded == true && resultsManager.getRefreshed() == true) {
			String timeSpent = timeSpentObserver.getTimeSpent();
			menuHome.removeAllStrings();
			menuHome = new DefaultStringsSetter().setMenuHomeStrings(menuHome);
			if (timeSpentObserver.getTimeSpent().length() == 3)
				menuHome.add(timeSpent, new Vector2(550, 485),
						new Vector2(1, 1), new Vector3(1, 1, 1));
			if (timeSpentObserver.getTimeSpent().length() == 2)
				menuHome.add(timeSpent, new Vector2(555, 485),
						new Vector2(1, 1), new Vector3(1, 1, 1));

			String testsSolved = Integer.toString(resultsManager
					.getTestsSolved());
			menuHome.add(testsSolved, new Vector2(
					865 - testsSolved.length() * 10, 485), new Vector2(1, 1),
					new Vector3(1, 1, 1));
			String accuracy = Integer.toString(resultsManager.getAccuracy())
					+ "%";
			menuHome.add(accuracy, new Vector2(720 - accuracy.length() * 10,
					485), new Vector2(1, 1), new Vector3(1, 1, 1));
		}

		timeSpentObserver.updateTimers(delta);
	}

}