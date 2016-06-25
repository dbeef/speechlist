package com.dbeef.speechlist.gui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class TestButton extends Button {

	static final double gravity = 1.10f;
	double gravityFrontier;
	double gravityTimer = 0;
	double originPositionX;
	double originPositionY;
	private String name;
	public BitmapFont font;

	public TestButton(int x, int y, Texture texture, String name) {
		super(x, y, texture);
		this.name = name;

		this.deselect();

		originPositionX = x;
		originPositionY = y;
		gravityFrontier = y;
	}

	public void render(Batch batch, float delta) {
		if (selected == true) {

			if (alpha < 0.1f)
				alpha += delta;
			if (alpha > 0.1)
				alpha = 0.1f;
		}
		if (selected == false) {
			if (alpha > 0f)
				alpha -= delta;
			if (alpha < 0f)
				alpha = 0f;
		}

		image.setAlpha(alpha);
		image.draw(batch);

		font.draw(batch, name, (float) this.image.getX() + 15,
				(float) this.image.getY() + 55);
	}

	public void reverseSelection() {
		this.selected = !this.selected;
	}

	public void move(double deltaX, double deltaY) {
		image.setPosition((float) originPositionX + (float) deltaX,
				(float) originPositionY + (float) deltaY);

		gravityTimer = 1;

		if (image.getY() > gravityFrontier)
			image.setPosition(image.getX(), (float) gravityFrontier);

	}

	public void savePositionAsOriginPosition() {
		originPositionX = image.getX();
		originPositionY = image.getY();
	}

	public boolean checkCollision(int x, int y) {

		this.bounds.setPosition(image.getX(), image.getY());

		mouse.setPosition(x, y);

		if (mouse.overlaps(bounds))
			return true;
		else
			return false;
	}

	public void applyGravity(double delta) {
		gravityTimer += delta;
		gravityTimer *= gravity;

		double temp = gravityTimer;
		move(0, gravityTimer);
		gravityTimer = temp;

		if (originPositionY >= gravityFrontier) {
			originPositionY = gravityFrontier;
			gravityTimer = 0;
		}
		savePositionAsOriginPosition();
	}

	public void loadFont(BitmapFont font) {
		this.font = font;
		font.setColor(1, 1, 1, 1);
	}
}