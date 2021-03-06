package com.dbeef.speechlist.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.dbeef.speechlist.camera.Camera;
import com.dbeef.speechlist.gui.Button;
import com.dbeef.speechlist.gui.TestButton;
import com.dbeef.speechlist.screen.Screen;
import com.dbeef.speechlist.tests.TestsManager;
import com.dbeef.speechlist.text.DefaultStringsManager;
import com.dbeef.speechlist.text.VocabularyFormatter;
import com.dbeef.speechlist.utils.Variables;

public class InputInterpreter implements GestureListener {

	Variables variables = new Variables();
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
	Array<TestButton> testsButtons;

	Screen menuBrief;
	Screen menuSphinx;

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

		if(variables.getDebugMode() == true)
			System.out.println("touchDown");
		return false;
	}

	@Override
	public boolean tap(float x, float y, int count, int button) {
		// TODO Auto-generated method stub
		

		if(variables.getDebugMode() == true)
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
			camera.move(variables.getHomeScreenPosition());
		}
		if (tests.checkCollision((int) vec.x, (int) vec.y) == true) {
			home.deselect();
			tests.select();
			downloads.deselect();
			camera.move(variables.getTestsScreenPosition());
		}
		if (downloads.checkCollision((int) vec.x, (int) vec.y) == true) {
			home.deselect();
			tests.deselect();
			downloads.select();
			camera.move(variables.getDownloadsScreenPosition());
		}

		Vector3 vecNonGui = new Vector3();
		vecNonGui.x = x;
		vecNonGui.y = y;
		camera.unproject(vecNonGui);

		manageTestsButtonsCollisions((int) vecNonGui.x, (int) vecNonGui.y);
		manageBriefButtonsCollisions((int) vecNonGui.x, (int) vecNonGui.y);

		tapX = x;
		tapY = y;
		return false;
	}

	@Override
	public boolean longPress(float x, float y) {
		// TODO Auto-generated method stub
		if(variables.getDebugMode() == true)
			System.out.println("longPress");
		return false;
	}

	@Override
	public boolean fling(float velocityX, float velocityY, int button) {
		if (Math.abs(velocityX) > Math.abs(velocityY) && assetsLoaded == true) {
			if (velocityX > 0) {

				if (tests.getSelection() == true) {
					home.select();
					tests.deselect();
					downloads.deselect();
					camera.move(variables.getHomeScreenPosition());
				}
				if (downloads.getSelection() == true) {
					home.deselect();
					tests.select();
					downloads.deselect();
					camera.move(variables.getTestsScreenPosition());
				}

			} else if (velocityX < 0) {
				if (tests.getSelection() == true) {
					home.deselect();
					tests.deselect();
					downloads.select();
					camera.move(variables.getDownloadsScreenPosition());
				}
				if (home.getSelection() == true) {
					home.deselect();
					tests.select();
					downloads.deselect();
					camera.move(variables.getTestsScreenPosition());
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

		if(variables.getDebugMode() == true)		
			System.out.println("pan");

		if (camera.isCameraChangingPosition() == false
				&& initialCameraMovements == true)
			manageSheetSliding();

		return false;
	}

	@Override
	public boolean panStop(float x, float y, int pointer, int button) {
		// TODO Auto-generated method stub

		if(variables.getDebugMode() == true)
		System.out.println("panStop");
		panned = false;
		justStoppedPanning = true;

		if (camera.isCameraChangingPosition() == false
				&& initialCameraMovements == true)
			manageSheetSliding();

		return false;
	}

	@Override
	public boolean zoom(float initialDistance, float distance) {

		if(variables.getDebugMode() == true)
		{	System.out.println("zoom");
	
		System.out.println("initialDistance: " + initialDistance + "distance: "
				+ distance);
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

		if(variables.getDebugMode() == true)
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

	public void loadGesturesReceivers(Camera camera, Camera guiCamera,
			Button home, Button tests, Button downloads, Button accept,
			Button decline, Array<TestButton> testsButtons, Screen menuBrief,
			Screen menuSphinx, TestsManager testsManager) {
		this.camera = camera;
		this.guiCamera = guiCamera;
		this.home = home;
		this.tests = tests;
		this.downloads = downloads;
		this.accept = accept;
		this.decline = decline;
		this.testsButtons = testsButtons;
		this.testsManager = testsManager;
		this.menuSphinx = menuSphinx;
		this.menuBrief = menuBrief;
		assetsLoaded = true;
	}

	void manageBriefButtonsCollisions(float x, float y) {

		if (decline.checkCollision((int) x, (int) y) == true) {
			camera.move(variables.getTestsScreenPosition());
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
			camera.move(variables.getSphinxScreenPosition());
		}

	}

	void manageTestsButtonsCollisions(float x, float y) {

		for (int a = 0; a < testsButtons.size; a++) {
			if (testsButtons.get(a).getSelection() == true) {
				if (testsButtons.get(a).checkCollisionTick((int) x, (int) y) == true) {
					testsButtons.get(a).highlight();
					tests.deselect();
					camera.move(variables.getBriefScreenPosition());
					addMenuBriefStrings(a);
					addMenuSphinxStrings(a);
				}
			}

		}
		manageTestButtonsHighlighting(x, y);
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

	void addMenuBriefStrings(int a) {
		menuBrief.removeAllStrings();
		menuBrief = new DefaultStringsManager().setMenuBriefStrings(menuBrief);
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

	void manageSheetSliding() {
		if (wasPannedBefore == true && this.getPanned() == false) {
			for (int a = 0; a < testsButtons.size; a++)
				testsButtons.get(a).savePositionAsOriginPosition();
		}

		if (tests.getSelection() == true) {
			if (this.getTouchDown() == true) {
				initialPanX = this.getTouchDownX();
				initialPanY = this.getTouchDownY();
			}
			if (this.getPanned() == true) {
				panX = this.getPanX();
				panY = this.getPanY();
				for (int a = 0; a < testsButtons.size; a++)
					testsButtons.get(a).move(0, initialPanY - panY);
			}

		}
		wasPannedBefore = this.getPanned();
	}

	public void setInitialCameraMovementsDone() {
		initialCameraMovements = true;
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