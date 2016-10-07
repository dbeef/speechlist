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

	Button[] testCategories_local;

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
		client = new RESTClient();
		client.start();
		testCategories_local = new Button[4];
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
		if (logoCameOnScreen == true) {
			timerLoading += delta;
			if (timerLoading > 0.01) {
				timerLoading = 0;

				if (client.FAILED() == false) {
					if (initial.changeStringAlpha(
							Variables.INITIAL_TEXT_ARGUING_WITH_SERVER, 0) == 0
							&& downloadableTestsManager != null
							&& downloadableTestsManager
									.RETRIEVED_DOWNLOADABLES() == true)
						initial.changeStringAlpha(
								Variables.INITIAL_TEXT_LOADING_ASSETS, 0.02f);

					if (initial.changeStringAlpha(
							Variables.INITIAL_TEXT_ARGUING_WITH_SERVER, 0) > 0
							&& client.getUNIQUE_IDS_RETRIEVED())
						initial.changeStringAlpha(
								Variables.INITIAL_TEXT_ARGUING_WITH_SERVER,
								-0.05f);
					else {
						initial.changeStringAlpha(
								Variables.INITIAL_TEXT_ARGUING_WITH_SERVER,
								0.02f);
					}

				} else {
					initial.changeStringAlpha(
							Variables.INITIAL_TEXT_LOADING_ASSETS, -0.05f);
					initial.changeStringAlpha(
							Variables.INITIAL_TEXT_ARGUING_WITH_SERVER, -0.05f);

					if (initial.changeStringAlpha(
							Variables.INITIAL_TEXT_LOADING_ASSETS, 0) == 0
							&& initial.changeStringAlpha(
									Variables.INITIAL_TEXT_ARGUING_WITH_SERVER,
									0) == 0)
						initial.changeStringAlpha(
								Variables.INITIAL_TEXT_FAILED_TO_CONNECT_TO_SERVER,
								0.0065f);
				}
			}

		}
	}

	void updateAssetsLoaderLogics() {
		if (logoCameOnScreen == true) {
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
		} else {
			for (Screen s : solvingScreens)
				s.stopRendering();
			for (int a = 0; a < tests_local.length; a++)
				tests_local[a].startRendering();

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
			inputInterpreter.setTestCategories(testCategories_local);
			inputInterpreter.setTestScreens(tests_local, testsBackButton);
			inputInterpreter.setResultsManager(resultsManager);
			initiatedInput = true;
		}

		if (readyToGoMenu == true
				&& (initial.changeStringAlpha(
						Variables.INITIAL_TEXT_LOADING_ASSETS, 0) == 1 || initial
						.changeStringAlpha(
								Variables.INITIAL_TEXT_FAILED_TO_CONNECT_TO_SERVER,
								0) == 1) && initialCameraMovementsDone == false) {
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
		TestButtonsDispenser testButtonsDispenser = new TestButtonsDispenser(
				assetsManager, testsButtons, tests_local, menuDownloads, client);
		testButtonsDispenser.addTestButtons(tests);
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
	}

	void manageMenuDownloadsElements() throws InterruptedException {

		if (logoCameOnScreen == true && client != null
				&& client.getUNIQUE_IDS_RETRIEVED() == true
				&& downloadableTestsManager == null && testsManager != null) {

			System.out
					.println("Sending signal to download manager to start downloading test names");

			downloadableTestsManager = new DownloadManager();
			downloadableTestsManager.run(client.getUniqueIdContainer(),
					testsManager.getTests());
		}

		if (logoCameOnScreen == true && downloadableTestsManager != null
				&& downloadableTestsManager.RETRIEVED_DOWNLOADABLES()
				&& addedDownloadables == false) {

			System.out.println("Adding downloadable tests buttons");

			TestButtonsDispenser testButtonsDispenser = new TestButtonsDispenser(
					assetsManager, testsButtons, tests_local, menuDownloads,
					client);
			testButtonsDispenser
					.addDownloadableTestsButtons(downloadableTestsManager
							.getNames());

			if (downloadableTestsButtons == null) {
			
				menuDownloads.removeAllStrings();
				
				menuDownloads.add(assetsManager.desert, new Vector2(1480, 200));
			
				menuDownloads
						.add("Good job, cowboy!", new Vector2(1545, 170),
								new Vector2(4, 1), new Vector3(1, 1, 1));
				menuDownloads.add("You've got all the tests", new Vector2(1510, 135),
						new Vector2(4, 1), new Vector3(1, 1, 1));
				menuDownloads.add("that are on server.", new Vector2(1548, 100),
						new Vector2(4, 1), new Vector3(1, 1, 1));
			
			}
			addedDownloadables = true;
		}

		if (assetsManager != null && assetsManager.loaded == true) {
			if (client != null && client.getUNIQUE_IDS_RETRIEVED() == true) {
				menuDownloads.removeTextureWithPosition(new Vector2(1560, 200));
			
				if(addedDownloadables == false)
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