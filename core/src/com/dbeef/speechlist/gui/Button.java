package com.dbeef.speechlist.gui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

public class Button {

	boolean showing;
	boolean selected = false;
	boolean blinking = false;
	float blinkTimer = 0;
	float alphaMinimum = 0.5f;
	float multiplier = 1;
	float alpha = 0;
	Sprite image;
	Rectangle mouse;
	Rectangle bounds;
	float x;
	float y;

	public void setTexture(Texture texture) {
		image = new Sprite(texture);
		image.setPosition(x, y);

		mouse = new Rectangle();
		mouse.setSize(1, 1);

		bounds = new Rectangle();
		bounds.setSize(image.getWidth(), image.getHeight());
		bounds.setPosition(x, y);

		showing = true;
	}

	public Button(int x, int y, Texture texture) {

		this.x = x;
		this.y = y;

		image = new Sprite(texture);
		image.setPosition(x, y);

		mouse = new Rectangle();
		mouse.setSize(1, 1);

		bounds = new Rectangle();
		bounds.setSize(image.getWidth(), image.getHeight());
		bounds.setPosition(x, y);

		showing = true;
	}

	public Button(float x, float y, Texture texture) {

		this.x = x;
		this.y = y;

		image = new Sprite(texture);
		image.setPosition(x, y);

		mouse = new Rectangle();
		mouse.setSize(1, 1);

		bounds = new Rectangle();
		bounds.setSize(image.getWidth(), image.getHeight());
		bounds.setPosition(x, y);

		showing = true;
	}

	public void render(Batch batch, float delta) {
		updateTimers(delta);
		image.setAlpha(alpha);
		image.draw(batch);
	}

	public boolean checkCollision(int x, int y) {
		if (showing == true) {
			mouse.setPosition(x, y);

			if (mouse.overlaps(bounds))
				return true;
			else
				return false;
		} else
			return false;
	}

	public void setShowing(boolean showing) {
		this.showing = showing;
	}

	public void select() {
		selected = true;
	}

	public void deselect() {
		selected = false;
	}

	public boolean getSelection() {
		return selected;
	}

	public void dispose() {
	}

	void updateTimers(float delta) {

		if (showing == true) {
			if (blinking == true) {
				if (blinkTimer < 0.5f) {
					select();
					blinkTimer += delta;
					if (blinkTimer > 0.5f) {
						blinkTimer = 0;
						deselect();
						blinking = false;
					}
				}
			}

			if (selected == true) {

				if (alpha < 1)
					alpha += delta * multiplier;
				if (alpha > 1)
					alpha = 1;
			}
			if (selected == false) {
				if (alpha > alphaMinimum)
					alpha -= delta * multiplier;
				if (alpha < alphaMinimum)
					alpha = alphaMinimum;
			}
		} else {
			if (alpha > 0)
				alpha -= delta;
			if (alpha < 0)
				alpha = 0;
		}
	}

	public void setPosition(float x, float y) {
		image.setPosition(x, y);
		bounds.setPosition(x, y);
	}

	public void setAlphaMinimum(float alphaMinimum) {
		this.alphaMinimum = alphaMinimum;
	}

	public void setMultiplier(float multiplier) {
		this.multiplier = multiplier;
	}

	public void blink() {
		blinking = true;
	}

	public float getAlphaMinimum() {
		return alphaMinimum;
	}
	public void setAlpha(float alpha){
		this.alpha = alpha;
	}
	public float getAlpha(){
		return alpha;
	}
}