package com.dbeef.speechlist.camera;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;

public class Camera extends OrthographicCamera {

	static final float accumulateMax = 40;
	static final float accumulateDecreaseSpeed = 13f;
	static final float accumulateIncreaseSpeed = 5.3f;

	Vector2 destination;
	float accumulatePlus = 1f;
	float accumulateMinus = -1f;
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

				this.position.x += accumulatePlus;

				if (Math.abs(this.position.x - destination.x) <= 100) {
					accumulatePlus -= delta * accumulateDecreaseSpeed
							* accumulatePlus;
				} else
					accumulatePlus += delta * accumulateIncreaseSpeed
							* accumulatePlus;

				if (this.position.x > destination.x)
					this.position.x = destination.x;
			}

			if (this.position.x > destination.x) {
				this.position.x += accumulateMinus;

				if (Math.abs(this.position.x - destination.x) <= 100) {
					accumulateMinus += delta * accumulateDecreaseSpeed
							* Math.abs(accumulateMinus);
				} else
					accumulateMinus -= delta * accumulateIncreaseSpeed
							* Math.abs(accumulateMinus);

				if (this.position.x < destination.x)
					this.position.x = destination.x;
			}

			if (this.position.x == destination.x) {
				accumulatePlus = accumulateIncreaseSpeed;
				accumulateMinus = -accumulateIncreaseSpeed;
			}

			if (accumulatePlus > accumulateMax)
				accumulatePlus = accumulateMax;
			if (accumulateMinus < -accumulateMax)
				accumulateMinus = -accumulateMax;

		}
	}
	public void changePosition(float x){
		this.position.x = x;
		this.update();
	}
}