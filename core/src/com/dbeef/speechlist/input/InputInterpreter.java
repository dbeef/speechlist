package com.dbeef.speechlist.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class InputInterpreter implements GestureListener {

	Vector3 xyzTap = new Vector3();
	double tapX;
	double tapY;
	double flingVelocityX = 0;
	double flingVelocityY = 0;
	double panX = 0;
	double panY = 0;
	double touchDownX = 0;
	double touchDownY = 0;
	double zoomDelta;

	public String message = "No data yet";
	public boolean touched;
	public boolean flinged;
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

	public double getFlingDeltaX() {
		if (flinged == true) {
			flinged = false;
			return flingVelocityX;
		} else
			return 0;
	}

	public double getFlingDeltaY() {
		if (flinged == true) {
			flinged = false;
			return flingVelocityY;
		} else
			return 0;
	}

	public double gettapX() {
		touched = false;
		return tapX;
	}

	public double gettapY() {
		touched = false;
		return tapY;
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
		System.out.println("touchDown");
		return false;
	}

	@Override
	public boolean tap(float x, float y, int count, int button) {
		// TODO Auto-generated method stub
		System.out.println("tap");
		touched = true;
		tapX = x;
		tapY = y;
		return false;
	}

	@Override
	public boolean longPress(float x, float y) {
		// TODO Auto-generated method stub
		System.out.println("longPress");
		return false;
	}

	@Override
	public boolean fling(float velocityX, float velocityY, int button) {
		// TODO Auto-generated method stub
		flinged = true;
		flingVelocityX = velocityX;
		flingVelocityY = velocityY;
		System.out.println("fling");
		return false;
	}

	@Override
	public boolean pan(float x, float y, float deltaX, float deltaY) {
		// TODO Auto-generated method stub
		panned = true;
		panX = x;
		panY = y;
		System.out.println("pan");

		return false;
	}

	@Override
	public boolean panStop(float x, float y, int pointer, int button) {
		// TODO Auto-generated method stub
		System.out.println("panStop");
		panned = false;
		justStoppedPanning = true;
		return false;
	}

	@Override
	public boolean zoom(float initialDistance, float distance) {
		System.out.println("zoom");
		System.out.println("initialDistance: " + initialDistance + "distance: "
				+ distance);
		message = "initialDistance: " + initialDistance + "distance: "
				+ distance;

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