package com.dbeef.speechlist.camera;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.dbeef.speechlist.utils.Variables;

public class Camera extends OrthographicCamera {

	Variables variables = new Variables();
	
	static final float accumulateMax = 25;
	static final float accumulateDecreaseSpeed = 5f;
	static final float accumulateIncreaseSpeed = 6.5f;

	Vector2 destination;
	float accumulatedPlus = 1f;
	float accumulatedMinus = -1f;
	float timer = 0;

	public Camera(float viewportWidth, float viewportHeight) {
		this.viewportWidth = viewportWidth;
		this.viewportHeight = viewportHeight;
		this.near = 0;
		update();
		destination = new Vector2();
	}

	public void move(float dest) {
		destination.x = dest;
	}

	public void updateTimers(float delta) {
		timer += delta;
		if (timer > 0.01f) {
			timer = 0;

			if (this.position.x < destination.x) {

				this.position.x += accumulatedPlus;

				if (Math.abs(this.position.x - destination.x) <= 100) {
					accumulatedPlus -= delta * accumulateDecreaseSpeed
							* accumulatedPlus;
				} else
					accumulatedPlus += delta * accumulateIncreaseSpeed
							* accumulatedPlus;

				if (this.position.x > destination.x)
					this.position.x = destination.x;
			}

			if (this.position.x > destination.x) {
				this.position.x += accumulatedMinus;

				if (Math.abs(this.position.x - destination.x) <= 100) {
					accumulatedMinus += delta * accumulateDecreaseSpeed
							* Math.abs(accumulatedMinus);
				} else
					accumulatedMinus -= delta * accumulateIncreaseSpeed
							* Math.abs(accumulatedMinus);

				if (this.position.x < destination.x)
					this.position.x = destination.x;
			}

			if (this.position.x == destination.x) {
				accumulatedPlus = accumulateIncreaseSpeed;
				accumulatedMinus = -accumulateIncreaseSpeed;
			}

			if (accumulatedPlus > accumulateMax)
				accumulatedPlus = accumulateMax;
			if (accumulatedMinus < -accumulateMax)
				accumulatedMinus = -accumulateMax;

		}
	}

	public void changePosition(float x) {
		this.position.x = x;
		this.update();
	}

	public boolean isCameraChangingPosition() {

		if (this.position.x == variables.getHomeScreenPosition()
				|| this.position.x == variables.getTestsScreenPosition()
				|| this.position.x == variables.getDownloadsScreenPosition()
				|| this.position.x == variables.getBriefScreenPosition())
			return false;
		else
			return true;
	}

}