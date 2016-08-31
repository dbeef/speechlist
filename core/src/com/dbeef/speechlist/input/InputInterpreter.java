package com.dbeef.speechlist.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.dbeef.speechlist.camera.Camera;
import com.dbeef.speechlist.files.AssetsManager;
import com.dbeef.speechlist.files.ResultsManager;
import com.dbeef.speechlist.files.TestsManager;
import com.dbeef.speechlist.gui.Button;
import com.dbeef.speechlist.gui.SolutionInput;
import com.dbeef.speechlist.gui.SolutionInputButton;
import com.dbeef.speechlist.gui.TestButton;
import com.dbeef.speechlist.screen.Screen;
import com.dbeef.speechlist.text.DefaultStringsSetter;
import com.dbeef.speechlist.text.GeneratedTestStringsSetter;
import com.dbeef.speechlist.text.VocabularyFormatter;
import com.dbeef.speechlist.utils.Variables;

public class InputInterpreter implements GestureListener {

	ResultsManager resultsManager;

	Button testsBackButton;
	Screen[] tests_local;
	Screen[] tests_server;
	Button[] testCategories_local;
	Button[] testCategories_server;
	SolutionInput solutionInput;
	Array<TestButton> vocabularyButtons;
	AssetsManager assetsManager;

	int currentSolvingScreen;

	VocabularyFormatter vocabularyFormatter;

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
	Array<SolutionInputButton> solvingButtons;
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
			manageSolvingButtonsCollisions((int) vecNonGui.x, (int) vecNonGui.y);
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
						&& solutionInput.getVisibility() == false) {
					left.blink();

					if (currentSolvingScreen > 1)
						camera.move(Variables.SPHINX_SCREEN_POSITION
								+ (currentSolvingScreen - 1)
								* Variables.SCREEN_WIDTH
								- Variables.SCREEN_WIDTH);

					if (solvingScreens.size - 1 > 1 && currentSolvingScreen > 1)
						currentSolvingScreen--;

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
						&& solutionInput.getVisibility() == false) {
					right.blink();
					if (currentSolvingScreen + 1 <= solvingScreens.size)
						currentSolvingScreen++;
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

		if ((camera.isCameraChangingPosition() == false
				&& initialCameraMovements == true) || solutionInput.getVisibility() == true)
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

		if ((camera.isCameraChangingPosition() == false
				&& initialCameraMovements == true)|| solutionInput.getVisibility() == true)
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

	public InputInterpreter() {
		Gdx.input.setInputProcessor(new GestureDetector(this));
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

	public void loadGesturesReceivers(Camera camera, Camera guiCamera,
			Button home, Button tests, Button downloads, Button accept,
			Button decline, Button left, Button right,
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
		assetsLoaded = true;
	}

	void manageBriefButtonsCollisions(float x, float y) {

		System.out.println(currentSolvingScreen);

		if (home.getSelection() == false && tests.getSelection() == false
				&& downloads.getSelection() == false) {
			if (decline.checkCollision((int) x, (int) y) == true
					&& solutionInput.getVisibility() == false && solutionInput.isAlphaZero() == true) {
				camera.move(Variables.TESTS_SCREEN_POSITION);
				decline.blink();
				tests.select();
				for (int a = 0; a < testsButtons.size; a++) {
					if (testsButtons.get(a).isHighlighted() == true) {
						testsButtons.get(a).lowlight();
					}
				}
			}

			if (accept.checkCollision((int) x, (int) y) == true) {
				if (camera.position.x < Variables.SOLVING_SCREEN_POSITION) {
					accept.blink();
					currentSolvingScreen = 1;
					camera.move(Variables.SPHINX_SCREEN_POSITION);

					for (SolutionInputButton solutionInputButton : solvingButtons) {
						solutionInputButton.setClicked(false);
						solutionInputButton
								.setTexture(assetsManager.wordNotSet);
					}
				} else if (solutionInput.getVisibility() == false) {

					menuBrief.removeStringContaining("%");
					menuBrief.removeStringContaining("n/a");

					int counter = 0;
					for (SolutionInputButton solutionInputButton : solvingButtons)
						if (solutionInputButton.getClicked() == true)
							counter++;
					if (counter == solvingButtons.size) {
						String percentage = Float.toString(solutionInput
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

						resultsManager.addTestResult(new Vector2(solutionInput
								.getCurrentTestUniqueID(), solutionInput
								.getSummary() * 100));

						resultsManager.saveData();
					}
				}

			}
			if (left.checkCollision((int) x, (int) y) == true) {
				if (camera.position.x > Variables.SOLVING_SCREEN_POSITION
						&& solutionInput.getVisibility() == false) {
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
						&& solutionInput.getVisibility() == false) {

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
			if (testsButtons.get(a).getSelection() == true) {
				if (testsButtons.get(a).checkCollisionTick((int) x, (int) y) == true) {
					testsButtons.get(a).highlight();
					tests.deselect();
					camera.move(Variables.BRIEF_SCREEN_POSITION);
					addMenuBriefStrings(a);
					addMenuSphinxStrings(a);
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

		if (anyCategorySelected == true) {
			for (int a = 0; a < testsButtons.size; a++) {
				if (testsButtons.get(a).checkCollision((int) x, (int) y) == true
						&& testsButtons.get(a).getCategory()
								.equals(currentCategory)) {
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
	}

	void addMenuBriefStrings(int a) {
		menuBrief.removeAllStrings();
		menuBrief = new DefaultStringsSetter().setMenuBriefStrings(menuBrief);

		if (vocabularyFormatter == null)
			vocabularyFormatter = new VocabularyFormatter();

		String[] formatted = vocabularyFormatter.formatVocabulary(testsManager
				.getTest(testsButtons.get(a).getName()).getVocabulary(),
				testsManager.getTest(testsButtons.get(a).getName())
						.getVocabulary().length);

		for (int c = 0; c < formatted.length; c++) {

			int spanX = (480 - (int) fonts.get(5).getBounds(formatted[c]).width) / 2;
			int spanY = formatted.length * 5;

			menuBrief.add(formatted[c], new Vector2(1920 + spanX, (500 - spanY)
					- c * 60), new Vector2(1, 1), new Vector3(1, 1, 1));

		}

		int span = testsButtons.get(a).getName().length() * 11;

		menuBrief.add(testsButtons.get(a).getName(), new Vector2(2165 - span,
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

		GeneratedTestStringsSetter generatedTestStringsSetter = new GeneratedTestStringsSetter(
				sentences, fonts, mainBackground, assetsManager.wordSet,
				assetsManager.wordNotSet);

		solvingScreens.clear();
		solvingScreens.addAll(generatedTestStringsSetter.getSolvingScreens());
		solvingButtons = generatedTestStringsSetter.getSolvingButtons();

		solutionInput
				.createButtons(testsManager.getTest(
						testsButtons.get(clickedButtonIndex).getName())
						.getVocabulary());
		solutionInput.setCurrentTestUniqueID(testsManager.getTest(
				testsButtons.get(clickedButtonIndex).getName()).getUniqueId());

		System.out.println("sending id to solution"
				+ testsManager.getTest(
						testsButtons.get(clickedButtonIndex).getName())
						.getUniqueId());

		vocabularyButtons = solutionInput.getVocabularyButtons();
	}

	void manageSheetSliding() {
		if (wasPannedBefore == true && this.getPanned() == false) {
			for (int a = 0; a < testsButtons.size; a++)
				testsButtons.get(a).savePositionAsOriginPosition();
			if(solutionInput != null )
				for(int a =0;a<solutionInput.getVocabularyButtons().size;a++)
				solutionInput.getVocabularyButtons().get(a).savePositionAsOriginPosition();
		}

		if (tests.getSelection() == true || downloads.getSelection() == true || (solutionInput != null && solutionInput.getVisibility() == true)) {
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
				
				System.out.println("soltionInput != null" + solutionInput != null);
				System.out.println("solutionInput.getVisibility()" + solutionInput.getVisibility());
				
				if (solutionInput != null
						&& solutionInput.getVisibility() == true) {
					System.out.println("True, now for");
					for (int a = 0; a < solutionInput.getVocabularyButtons().size; a++) {
				System.out.println("Moving");
				
						solutionInput.getVocabularyButtons().get(a)
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

	void manageSolvingButtonsCollisions(int x, int y) {
		if (solvingButtons != null && solutionInput.getVisibility() == false
				&& solutionInput.isAlphaZero() == true) {
			for (int a = 0; a < solvingButtons.size; a++)
				if (solvingButtons.get(a).checkCollision(x, y) == true) {
					solvingButtons.get(a).setTexture(assetsManager.wordSet);
					solvingButtons.get(a).blink();
					System.out.println("showing");
					solutionInput.show(a);
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
		for (int a = 0; a < testCategories_server.length; a++)
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
		if (vocabularyButtons != null && solutionInput.getVisibility() == true) {
			for (int a = 0; a < vocabularyButtons.size; a++) {
				if (vocabularyButtons.get(a).checkCollision(x, y) == true) {
					vocabularyButtons.get(a).blink();
					solutionInput.addAnswer(vocabularyButtons.get(a).getName());
					solutionInput.hide();
				}
			}
		}
	}

	public void setSolutionInput(SolutionInput solutionInput) {
		this.solutionInput = solutionInput;
	}

	public void setTestCategories(Button[] testCategories_local,
			Button[] testCategories_server) {
		this.testCategories_local = testCategories_local;
		this.testCategories_server = testCategories_server;
	}

	public void setResultsManager(ResultsManager resultsManager) {
		this.resultsManager = resultsManager;
	}

	public void setTestScreens(Screen[] tests_local, Screen[] tests_server,
			Button testsBackButton) {
		this.tests_local = tests_local;
		this.tests_server = tests_server;
		this.testsBackButton = testsBackButton;
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