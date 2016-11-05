package com.dbeef.speechlist.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.dbeef.speechlist.camera.Camera;
import com.dbeef.speechlist.files.AssetsManager;
import com.dbeef.speechlist.files.ResultsManager;
import com.dbeef.speechlist.files.TestsManager;
import com.dbeef.speechlist.gui.Bracket;
import com.dbeef.speechlist.gui.BracketsInput;
import com.dbeef.speechlist.gui.Button;
import com.dbeef.speechlist.gui.TestButton;
import com.dbeef.speechlist.internet.RESTClient;
import com.dbeef.speechlist.logics.TestButtonsDispenser;
import com.dbeef.speechlist.models.Test;
import com.dbeef.speechlist.screen.Screen;
import com.dbeef.speechlist.screen.StringWithDescription;
import com.dbeef.speechlist.text.BriefingVocabularyFormatter;
import com.dbeef.speechlist.text.DefaultGUIStringsSetter;
import com.dbeef.speechlist.text.FormattedTestSentencesSetter;
import com.dbeef.speechlist.utils.Variables;

public class InputGestures implements GestureListener {

	GlyphLayout layout = new GlyphLayout();
	RESTClient client = new RESTClient();

	ResultsManager resultsManager;

	Screen gui;
	Button testsBackButton;
	Screen[] tests_local;
	Button[] testCategories_local;
	BracketsInput bracketsInput;
	Array<TestButton> vocabularyButtons;
	AssetsManager assetsManager;

	int currentSolvingScreen;
	int solvingScreensCounter;
	
	BriefingVocabularyFormatter vocabularyFormatter;

	boolean wasPannedBefore;
	boolean assetsLoaded;
	boolean initialCameraMovements;
	double initialPanX;
	double initialPanY;

	TestsManager testsManager;

	Button home;
	Button tests;
	Button downloads;
	Button accept;
	Button decline;
	Button left;
	Button right;
	Array<TestButton> testsButtons;
	Array<Bracket> brackets;
	Array<BitmapFont> fonts;
	Texture mainBackground;
	Screen menuBrief;
	Array<Screen> solvingScreens;
	Camera camera;
	Camera guiCamera;

	Vector3 xyzTap = new Vector3();
	double tapX;
	double tapY;

	double panX = 0;
	double panY = 0;
	double touchDownX = 0;
	double touchDownY = 0;
	double zoomDelta;

	public String message = "No data yet";
	public boolean touched;
	public boolean panned;
	public boolean touchedDown;
	boolean justStoppedPanning;

	public boolean zoomDeltaChanged;

	public double getZoomDelta() {
		if (zoomDeltaChanged == true) {
			zoomDeltaChanged = false;
			return zoomDelta / 100;
		} else
			return 0;
	}

	public double getPanX() {
		return panX;
	}

	public double getPanY() {
		return panY;
	}

	public boolean getPanned() {
		return panned;
	}

	public double getTouchDownX() {
		return touchDownX;
	}

	public double getTouchDownY() {
		return touchDownY;
	}

	public boolean getTouchDown() {
		if (touchedDown == true) {
			touchedDown = false;
			return true;
		} else
			return false;
	}

	@Override
	public boolean touchDown(float x, float y, int pointer, int button) {
		// TODO Auto-generated method stub
		touchedDown = true;
		touchDownX = x;
		touchDownY = y;

		if (Variables.DEBUG_INPUT == true)
			System.out.println("touchDown" + x + " " + y);
		return false;
	}

	@Override
	public boolean tap(float x, float y, int count, int button) {
		// TODO Auto-generated method stub
		if (assetsLoaded == true) {

			if (Variables.DEBUG_INPUT == true)
				System.out.println("tap");
			touched = true;

			Vector3 vec = new Vector3();
			vec.x = x;
			vec.y = y;

			guiCamera.unproject(vec);

			if (home.checkCollision((int) vec.x, (int) vec.y) == true) {
				home.select();
				tests.deselect();
				downloads.deselect();
				camera.move(Variables.HOME_SCREEN_POSITION);
			}
			if (tests.checkCollision((int) vec.x, (int) vec.y) == true) {
				home.deselect();
				tests.select();
				downloads.deselect();
				camera.move(Variables.TESTS_SCREEN_POSITION);
			}
			if (downloads.checkCollision((int) vec.x, (int) vec.y) == true) {
				home.deselect();
				tests.deselect();
				downloads.select();
				camera.move(Variables.DOWNLOADS_SCREEN_POSITION);
			}

			manageVocabularyButtonsCollisions((int) vec.x, (int) vec.y);
			manageBriefButtonsCollisions((int) vec.x, (int) vec.y);

			Vector3 vecNonGui = new Vector3();
			vecNonGui.x = x;
			vecNonGui.y = y;
			camera.unproject(vecNonGui);

			manageTestsButtonsCollisions((int) vecNonGui.x, (int) vecNonGui.y);
			managebracketsCollisions((int) vecNonGui.x, (int) vecNonGui.y);
			manageCategoriesButtonsCollisions((int) vecNonGui.x,
					(int) vecNonGui.y);

			tapX = x;
			tapY = y;
			return false;
		}
		return false;
	}

	@Override
	public boolean longPress(float x, float y) {
		// TODO Auto-generated method stub
		if (Variables.DEBUG_INPUT == true)
			System.out.println("longPress");
		return false;
	}

	@Override
	public boolean fling(float velocityX, float velocityY, int button) {
		if (Math.abs(velocityX) > Math.abs(velocityY) && assetsLoaded == true) {
			if (velocityX > 0) {

				if (camera.position.x > Variables.SOLVING_SCREEN_POSITION
						&& bracketsInput.getVisibility() == false) {
					left.blink();

					if (currentSolvingScreen > 1)
						camera.move(Variables.SPHINX_SCREEN_POSITION
								+ (currentSolvingScreen - 1)
								* Variables.SCREEN_WIDTH
								- Variables.SCREEN_WIDTH);

					if (solvingScreens.size - 1 > 1 && currentSolvingScreen > 1) {
						currentSolvingScreen--;
						gui.setStringWithDescription(
								Integer.toString(currentSolvingScreen) + "/"
										+ Integer.toString(solvingScreensCounter - 1),
								Variables.CURRENT_SOLVING_SCREEN_NUMBER);
					}
				}

				if (tests.getSelection() == true) {
					home.select();
					tests.deselect();
					downloads.deselect();
					camera.move(Variables.HOME_SCREEN_POSITION);
				}
				if (downloads.getSelection() == true) {
					home.deselect();
					tests.select();
					downloads.deselect();
					camera.move(Variables.TESTS_SCREEN_POSITION);
				}

			} else if (velocityX < 0) {

				if (camera.position.x > Variables.SOLVING_SCREEN_POSITION
						&& bracketsInput.getVisibility() == false) {
					right.blink();
					if (currentSolvingScreen + 1 <= solvingScreens.size) {
						currentSolvingScreen++;
						gui.setStringWithDescription(
								Integer.toString(currentSolvingScreen) + "/"
										+ Integer.toString(solvingScreensCounter - 1),
								Variables.CURRENT_SOLVING_SCREEN_NUMBER);

					}
					camera.move(Variables.SPHINX_SCREEN_POSITION
							+ (currentSolvingScreen - 1)
							* Variables.SCREEN_WIDTH);

				}

				if (tests.getSelection() == true) {
					home.deselect();
					tests.deselect();
					downloads.select();
					camera.move(Variables.DOWNLOADS_SCREEN_POSITION);
				}
				if (home.getSelection() == true) {
					home.deselect();
					tests.select();
					downloads.deselect();
					camera.move(Variables.TESTS_SCREEN_POSITION);
				}

			} else {
				// Do nothing.
			}
		} else {

			// Ignore the input, because we don't care about up/down swipes.
		}
		return true;
	}

	@Override
	public boolean pan(float x, float y, float deltaX, float deltaY) {
		// TODO Auto-generated method stub
		panned = true;
		panX = x;
		panY = y;

		if (Variables.DEBUG_INPUT == true)
			System.out.println("pan");

		if ((camera.isCameraChangingPosition() == false && initialCameraMovements == true)
				|| bracketsInput.getVisibility() == true)
			manageSheetSliding();

		return false;
	}

	@Override
	public boolean panStop(float x, float y, int pointer, int button) {
		// TODO Auto-generated method stub

		if (Variables.DEBUG_INPUT == true)
			System.out.println("panStop");
		panned = false;
		justStoppedPanning = true;

		if ((camera.isCameraChangingPosition() == false && initialCameraMovements == true)
				|| bracketsInput.getVisibility() == true)
			manageSheetSliding();

		return false;
	}

	@Override
	public boolean zoom(float initialDistance, float distance) {

		if (Variables.DEBUG_INPUT == true) {
			System.out.println("zoom");
			System.out.println("initialDistance: " + initialDistance
					+ "distance: " + distance);
			message = "initialDistance: " + initialDistance + "distance: "
					+ distance;
		}
		if (initialDistance > distance)
			zoomDelta = initialDistance / distance;
		else if (initialDistance < distance)
			zoomDelta = distance / initialDistance;
		if (distance > initialDistance) {
			zoomDelta *= (-1);
		}
		zoomDeltaChanged = true;

		return false;
	}

	@Override
	public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2,
			Vector2 pointer1, Vector2 pointer2) {
		// TODO Auto-generated method stub

		if (Variables.DEBUG_INPUT == true)
			System.out.println("pinch");
		return false;
	}

	public InputGestures() {
		Gdx.input.setInputProcessor(new GestureDetector(this));
		Gdx.input.setCatchBackKey(true);
	}

	public Vector3 getLastTouchPosition() {
		touched = false;
		xyzTap.x = (float) tapX;
		xyzTap.y = (float) tapY;
		return xyzTap;
	}

	public boolean justStoppedPanning() {
		if (justStoppedPanning == true) {
			justStoppedPanning = false;
			return true;
		} else
			return false;

	}

	public void loadFonts(Array<BitmapFont> fonts) {
		this.fonts = fonts;
	}

	public void loadMainBackground(Texture mainBackground) {
		this.mainBackground = mainBackground;
	}

	public void loadGesturesReceivers(Screen gui, Camera camera,
			Camera guiCamera, Button home, Button tests, Button downloads,
			Button accept, Button decline, Button left, Button right,
			Array<TestButton> testsButtons, Screen menuBrief,
			Array<Screen> solvingScreens, TestsManager testsManager) {
		this.camera = camera;
		this.guiCamera = guiCamera;
		this.home = home;
		this.tests = tests;
		this.downloads = downloads;
		this.accept = accept;
		this.decline = decline;
		this.right = right;
		this.left = left;
		this.testsButtons = testsButtons;
		this.testsManager = testsManager;
		this.solvingScreens = solvingScreens;
		this.menuBrief = menuBrief;
		this.gui = gui;
		assetsLoaded = true;
	}

	void manageBriefButtonsCollisions(float x, float y) {

		System.out.println(currentSolvingScreen);

		if (home.getSelection() == false && tests.getSelection() == false
				&& downloads.getSelection() == false) {
			if (decline.checkCollision((int) x, (int) y) == true
					&& bracketsInput.getVisibility() == false
					&& bracketsInput.isAlphaZero() == true) {
				camera.move(Variables.TESTS_SCREEN_POSITION);
				decline.blink();
				tests.select();
				for (int a = 0; a < testsButtons.size; a++) {
					TestButton t = testsButtons.get(a);
					if (t.isHighlighted() == true) {
						t.lowlight();
					}
				}
			}

			if (accept.checkCollision((int) x, (int) y) == true) {
				if (camera.position.x < Variables.SOLVING_SCREEN_POSITION) {
					accept.blink();
					currentSolvingScreen = 1;
					camera.move(Variables.SPHINX_SCREEN_POSITION);

					for (int a = 0; a < brackets.size; a++) {
						Bracket b = brackets.get(a);
						b.setClicked(false);
						b.setTexture(assetsManager.wordNotSet);
					}
				} else if (bracketsInput.getVisibility() == false
						&& bracketsInput.isAlphaZero() == true) {

					menuBrief.removeStringContaining("%");
					menuBrief.removeStringContaining("n/a");

					int counter = 0;
					for (int a = 0; a < brackets.size; a++)
						if (brackets.get(a).getClicked() == true)
							counter++;

					if (counter == brackets.size) {
						String percentage = Float.toString(bracketsInput
								.getSummary() * 100);
						percentage = percentage.substring(0,
								percentage.indexOf("."));
						int position = 0;
						if (percentage.length() == 3)
							position = 2120;
						if (percentage.length() == 2)
							position = 2130;
						if (percentage.length() == 1)
							position = 2135;

						camera.move(Variables.BRIEF_SCREEN_POSITION);

						menuBrief.add(percentage + "%", new Vector2(position,
								180), new Vector2(1, 1), new Vector3(1, 1, 1));

						resultsManager.addTestResult(new Vector2(bracketsInput
								.getCurrentTestUniqueID(), bracketsInput
								.getSummary() * 100));

						resultsManager.saveData();
					}
				}

			}
			if (left.checkCollision((int) x, (int) y) == true) {
				if (camera.position.x > Variables.SOLVING_SCREEN_POSITION
						&& bracketsInput.getVisibility() == false
						&& bracketsInput.isAlphaZero() == true) {
					left.blink();

					if (currentSolvingScreen > 1)
						camera.move(Variables.SPHINX_SCREEN_POSITION
								+ (currentSolvingScreen - 1)
								* Variables.SCREEN_WIDTH
								- Variables.SCREEN_WIDTH);
				}
				if (solvingScreens.size > 1 && currentSolvingScreen > 1)
					currentSolvingScreen--;

			}
			if (right.checkCollision((int) x, (int) y) == true) {
				if (camera.position.x > Variables.SOLVING_SCREEN_POSITION
						&& bracketsInput.getVisibility() == false
						&& bracketsInput.isAlphaZero() == true) {

					right.blink();
					if (currentSolvingScreen + 1 <= solvingScreens.size)
						currentSolvingScreen++;
					camera.move(Variables.SPHINX_SCREEN_POSITION
							+ (currentSolvingScreen - 1)
							* Variables.SCREEN_WIDTH);

				}
			}
		}
	}

	void manageTestsButtonsCollisions(float x, float y) {

		for (int a = 0; a < testsButtons.size; a++) {
			TestButton testButton = testsButtons.get(a);
			if (testButton.getSelection() == true) {
				if (testButton.checkCollisionTick((int) x, (int) y) == true) {
					if (!testButton.getCategory().equals(
							Variables.CATEGORY_DOWNLOADABLE)) {
						testButton.highlight();
						tests.deselect();
						camera.move(Variables.BRIEF_SCREEN_POSITION);
						addMenuBriefStrings(a);
						addMenuSphinxStrings(a);
					} else {

						client.run(Variables.TASK_RETRIEVE_TEST, testsButtons
								.get(a).getUniqueId());

						if (client.FAILED() == false) {

							testButton.setStopShowing(true);

							TestButtonsDispenser testButtonsDispenser = new TestButtonsDispenser(
									assetsManager, testsButtons, tests_local,
									null, client);

							Array<Test> tests = new Array<Test>();
							tests.add(client.getTest());
							tests.addAll(testsManager.getTests());

							testButtonsDispenser.addTestButtons(tests);
							testsManager.getTests().add(client.getTest());

							testsManager.saveTestToExternalStorage(new Json()
									.toJson(client.getTest(), Test.class),
									client.getTest().getName());
						} else {
							for (int b = 0; b < testsButtons.size; b++)
								testsButtons.get(b).setStopShowing(true);
						}
					}
				}
			}

		}
		manageTestButtonsHighlighting(x, y);
	}

	void manageTestButtonsHighlighting(float x, float y) {

		boolean anyCollisions = false;
		boolean anyCategorySelected = false;

		String currentCategory = "";

		for (int a = 0; a < tests_local.length; a++) {
			if (tests_local[a].getVisibility() == true) {
				anyCategorySelected = true;
				if (a == 0)
					currentCategory = Variables.CATEGORY_VOCABULARY;
				if (a == 1)
					currentCategory = Variables.CATEGORY_IDIOMS;
				if (a == 2)
					currentCategory = Variables.CATEGORY_TENSES;
				if (a == 3)
					currentCategory = Variables.CATEGORY_VARIOUS;
			}
		}

		if (downloads.getSelection() == true) {
			anyCategorySelected = true;
			currentCategory = Variables.CATEGORY_DOWNLOADABLE;
		}

		if (anyCategorySelected == true) {
			for (int a = 0; a < testsButtons.size; a++) {
				TestButton testButton = testsButtons.get(a);
				if (testButton.checkCollision((int) x, (int) y) == true
						&& testButton.getCategory().equals(currentCategory)) {

					anyCollisions = true;

					for (int b = 0; b < testsButtons.size; b++)
						if (a != b)
							testsButtons.get(b).deselect();

					if (testButton.isHighlighted() == false)
						testButton.reverseSelection();
				}
			}
			if (anyCollisions == false) {
				for (int a = 0; a < testsButtons.size; a++)
					testsButtons.get(a).deselect();
			}
		}
	}

	void addMenuBriefStrings(int a) {
		menuBrief.removeAllStrings();
		menuBrief = new DefaultGUIStringsSetter()
				.setMenuBriefStrings(menuBrief);

		if (vocabularyFormatter == null)
			vocabularyFormatter = new BriefingVocabularyFormatter();

		String[] formatted = vocabularyFormatter.formatVocabulary(testsManager
				.getTest(testsButtons.get(a).getName()).getVocabulary(),
				testsManager.getTest(testsButtons.get(a).getName())
						.getVocabulary().length);

		for (int c = 0; c < formatted.length; c++) {

			layout.setText(fonts.get(5), formatted[c]);

			int spanX = (480 - (int) layout.width) / 2;
			int spanY = formatted.length * 5;

			menuBrief.add(formatted[c], new Vector2(1920 + spanX, (500 - spanY)
					- c * 60), new Vector2(1, 1), new Vector3(1, 1, 1));

		}

		layout.setText(fonts.get(5), testsButtons.get(a).getName());

		float span = (Variables.SCREEN_WIDTH - layout.width) / 2;

		menuBrief.add(testsButtons.get(a).getName(), new Vector2(1920 + span,
				640), new Vector2(1, 1), new Vector3(1, 1, 1));

		float lastResult = resultsManager
				.getResultOfTestWithUniqueID(testsManager.getTest(
						testsButtons.get(a).getName()).getUniqueId());

		String lastResultString = Float.toString(lastResult);
		lastResultString = lastResultString.substring(0,
				lastResultString.indexOf("."))
				+ "%";

		if (lastResult == -1)
			menuBrief.add("n/a", new Vector2(2115, 180), new Vector2(1, 1),
					new Vector3(1, 1, 1));
		else
			menuBrief.add(lastResultString,
					new Vector2(2150 - lastResultString.length() * 10, 180),
					new Vector2(1, 1), new Vector3(1, 1, 1));

	}

	public void setAssetsManager(AssetsManager assetsManager) {
		this.assetsManager = assetsManager;
	}

	void addMenuSphinxStrings(int clickedButtonIndex) {

		String sentences = testsManager.getTest(
				testsButtons.get(clickedButtonIndex).getName()).getSentences();

		FormattedTestSentencesSetter generatedTestStringsSetter = new FormattedTestSentencesSetter(
				sentences, fonts, mainBackground, assetsManager.wordSet,
				assetsManager.wordNotSet);

		solvingScreens.clear();
		solvingScreens.addAll(generatedTestStringsSetter.getSolvingScreens());
		brackets = generatedTestStringsSetter.getSolvingButtons();

		bracketsInput
				.createButtons(testsManager.getTest(
						testsButtons.get(clickedButtonIndex).getName())
						.getVocabulary());
		bracketsInput.setCurrentTestUniqueID(testsManager.getTest(
				testsButtons.get(clickedButtonIndex).getName()).getUniqueId());
		
		solvingScreensCounter = generatedTestStringsSetter.getSolvingScreensCounter();
		
		StringWithDescription s = new StringWithDescription("1/" + Integer.toString(solvingScreensCounter-1),
				Variables.CURRENT_SOLVING_SCREEN_NUMBER, new Vector2(1187, 65),
				new Vector3(1, 1, 1), new Vector2(2, 1));

		gui.removeAllStrings();
		gui.add(s);

		vocabularyButtons = bracketsInput.getVocabularyButtons();
	}

	void manageSheetSliding() {
		if (wasPannedBefore == true && this.getPanned() == false) {
			for (int a = 0; a < testsButtons.size; a++)
				testsButtons.get(a).savePositionAsOriginPosition();
			if (bracketsInput != null)
				for (int a = 0; a < bracketsInput.getVocabularyButtons().size; a++)
					bracketsInput.getVocabularyButtons().get(a)
							.savePositionAsOriginPosition();
		}

		if (tests.getSelection() == true
				|| downloads.getSelection() == true
				|| (bracketsInput != null && bracketsInput.getVisibility() == true)) {
			if (this.getTouchDown() == true) {
				initialPanX = this.getTouchDownX();
				initialPanY = this.getTouchDownY();
			}
			if (this.getPanned() == true) {
				panX = this.getPanX();
				panY = this.getPanY();
				for (int a = 0; a < testsButtons.size; a++) {
					testsButtons.get(a).move(0, 0.4f * (initialPanY - panY));
				}

				if (bracketsInput != null
						&& bracketsInput.getVisibility() == true) {
					for (int a = 0; a < bracketsInput.getVocabularyButtons().size; a++) {
						bracketsInput.getVocabularyButtons().get(a)
								.move(0, 0.5f * (initialPanY - panY));
					}
				}
			}
		}
		wasPannedBefore = this.getPanned();
	}

	public void setInitialCameraMovementsDone() {
		initialCameraMovements = true;
	}

	void managebracketsCollisions(int x, int y) {
		if (brackets != null && bracketsInput.getVisibility() == false
				&& bracketsInput.isAlphaZero() == true) {
			for (int a = 0; a < brackets.size; a++)
				if (brackets.get(a).checkCollision(x, y) == true) {
					brackets.get(a).setTexture(assetsManager.wordSet);
					brackets.get(a).blink();
					bracketsInput.show(a);
				}
		}
	}

	void manageCategoriesButtonsCollisions(int x, int y) {
		boolean stop = false;
		for (int a = 0; a < tests_local.length; a++)
			if (tests_local[a].getVisibility() == true)
				stop = true;
		for (int a = 0; a < testCategories_local.length; a++)
			if (testCategories_local[a].checkCollision(x, y) == true
					&& stop == false) {
				testCategories_local[a].blink();

				for (int b = 0; b < tests_local.length; b++)
					tests_local[b].hide();

				tests_local[a].show();
			}
		for (int a = 0; a < testCategories_local.length; a++)
			if (testCategories_local[a].checkCollision(x, y) == true) {
				testCategories_local[a].blink();
			}

		if (testsBackButton.checkCollision(x, y) == true && stop == true) {
			testsBackButton.blink();
			for (int b = 0; b < tests_local.length; b++)
				tests_local[b].hide();
		}

	}

	void manageVocabularyButtonsCollisions(int x, int y) {
		if (vocabularyButtons != null && bracketsInput.getVisibility() == true) {
			for (int a = 0; a < vocabularyButtons.size; a++) {
				if (vocabularyButtons.get(a).checkCollision(x, y) == true) {
					vocabularyButtons.get(a).blink();
					bracketsInput.addAnswer(vocabularyButtons.get(a).getName());
					bracketsInput.hide();
				}
			}
		}
	}

	public void setbracketsInput(BracketsInput bracketsInput) {
		this.bracketsInput = bracketsInput;
	}

	public void setTestCategories(Button[] testCategories_local) {
		this.testCategories_local = testCategories_local;
	}

	public void setResultsManager(ResultsManager resultsManager) {
		this.resultsManager = resultsManager;
	}

	public void setTestScreens(Screen[] tests_local, Button testsBackButton) {
		this.tests_local = tests_local;
		this.testsBackButton = testsBackButton;
	}

	@Override
	public void pinchStop() {
		// TODO Auto-generated method stub

	}
}
/*
 * touchDown: A user touches the screen.
 * 
 * longPress: A user touches the screen for some time.
 * 
 * tap: A user touches the screen and lifts the finger again. The finger must
 * not move outside a specified square area around the initial touch position
 * for a tap to be registered. Multiple consecutive taps will be detected if the
 * user performs taps within a specified time interval.
 * 
 * pan: A user drags a finger across the screen. The detector will report the
 * current touch coordinates as well as the delta between the current and
 * previous touch positions. Useful to implement camera panning in 2D.
 * 
 * panStop: Called when no longer panning.
 * 
 * fling: A user dragged the finger across the screen, then lifted it. Useful to
 * implement swipe gestures.
 * 
 * zoom: A user places two fingers on the screen and moves them together/apart.
 * The detector will report both the initial and current distance between
 * fingers in pixels. Useful to implement camera zooming.
 * 
 * pinch: Similar to zoom. The detector will report the initial and current
 * finger positions instead of the distance. Useful to implement camera zooming
 * and more sophisticated gestures such as rotation.
 * 
 * From: https://github.com/libgdx/libgdx/wiki/Gesture-detection
 */