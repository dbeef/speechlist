package com.dbeef.speechlist.input;




import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;



public class InputInterpreter  implements GestureListener{

	Vector3 xyzTap = new Vector3();
	double xTap;
	double yTap;
	double zoomDelta;
	boolean deltaChanged;

	public String message = "No data yet";
	public boolean touched;

	public double getZoomDelta(){
		if(deltaChanged == true)
	{
			deltaChanged = false;
			return zoomDelta/100;
	}
		else
			return 0;


	}
	public double getXTap(){
		touched = false;
		return xTap;
	}
	public double getYTap(){
		touched = false;
		return yTap;
	}
	
	@Override
	public boolean touchDown(float x, float y, int pointer, int button) {
		// TODO Auto-generated method stub
		System.out.println("touchDown");
		return false;
	}

	@Override
	public boolean tap(float x, float y, int count, int button) {
		// TODO Auto-generated method stub
		System.out.println("tap");
		touched = true;
		xTap = x;
		yTap = y;
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
		System.out.println("fling");
		return false;
	}

	@Override
	public boolean pan(float x, float y, float deltaX, float deltaY) {
		// TODO Auto-generated method stub
		System.out.println("pan");
		return false;
	}

	@Override
	public boolean panStop(float x, float y, int pointer, int button) {
		// TODO Auto-generated method stub
		System.out.println("panStop");
		return false;
	}

	@Override
	public boolean zoom(float initialDistance, float distance) {
		System.out.println("zoom");
		System.out.println("initialDistance: " + initialDistance + "distance: " + distance);
		message = "initialDistance: " + initialDistance + "distance: " + distance;
	
		if(initialDistance > distance)
		zoomDelta = initialDistance/distance;
		else if(initialDistance < distance)
		zoomDelta = distance/initialDistance;
		if(distance > initialDistance){zoomDelta *= (-1);
		}
		deltaChanged = true;


		return false;
	}

	@Override
	public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2,
			Vector2 pointer1, Vector2 pointer2) {
		// TODO Auto-generated method stub
		System.out.println("pinch");
		return false;
	}
	
public InputInterpreter(){
	Gdx.input.setInputProcessor(new GestureDetector(this));	
}
public Vector3 getLastTouchPosition(){
xyzTap.x = (float) xTap;
xyzTap.y = (float) yTap;
	return xyzTap;	
}
}
/*
touchDown: A user touches the screen.

longPress: A user touches the screen for some time.

tap: A user touches the screen and lifts the finger again.
The finger must not move outside a specified square area around the initial touch position for a 
tap to be registered. Multiple consecutive taps will be detected if the user performs taps within a
specified time interval.

pan: A user drags a finger across the screen. The detector will report the current touch coordinates as
 well as the delta between the current and previous touch positions. Useful to implement camera panning in 2D.

panStop: Called when no longer panning.

fling: A user dragged the finger across the screen, then lifted it. Useful to implement swipe gestures.

zoom: A user places two fingers on the screen and moves them together/apart. The detector will report
 both the initial and current distance between fingers in pixels. Useful to implement camera zooming.

pinch: Similar to zoom. The detector will report the initial and current finger positions instead of the
distance. Useful to implement camera zooming and more sophisticated gestures such as rotation.

From: https://github.com/libgdx/libgdx/wiki/Gesture-detection
 */