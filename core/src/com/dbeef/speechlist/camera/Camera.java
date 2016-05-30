package com.dbeef.speechlist.camera;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;

public class Camera extends OrthographicCamera {

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

	public void moveRight(float dest) {
		destination.x = dest;
	}

	public void updateTimers(float delta) {
		
		timer += delta;
		if (timer > 0.01f) {
			timer = 0;

			if (this.position.x < destination.x) {
				this.position.x += accumulatePlus;

				if(Math.abs(this.position.x - destination.x) <= 100){
					accumulatePlus -= delta * 13f * accumulatePlus; 
				}
				else
					accumulatePlus += delta * 5 * accumulatePlus;
				
				if (this.position.x > destination.x)
					this.position.x = destination.x;
			}
			if (this.position.x > destination.x) {
				this.position.x += accumulateMinus;
				
				if(Math.abs(this.position.x - destination.x) <= 100){
					accumulateMinus += delta * 13f * Math.abs(accumulateMinus);
					}
				else
					accumulateMinus -= delta * 5 * Math.abs(accumulateMinus);
				
				
				if (this.position.x < destination.x)
					this.position.x = destination.x;
			}

			if (this.position.x == destination.x) {
				accumulatePlus = 5;
				accumulateMinus = -5;
			}

		}
	}
}