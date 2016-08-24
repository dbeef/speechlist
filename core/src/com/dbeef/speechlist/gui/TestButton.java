package com.dbeef.speechlist.gui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class TestButton extends Button {

	static final double gravity = 1.10f;
	double maximumYPosition;
	double gravityTimer = 0;
	double originPositionX;
	double originPositionY;
	private String name;
	public BitmapFont font;
	Button tick;
	boolean highlightButton = false;
	float fontAlpha = 1;
	
	public TestButton(int x, int y, Texture texture, String name) {
		super(x, y, texture);
		this.name = name;
		this.deselect();
		originPositionX = x;
		originPositionY = y;
		maximumYPosition = y;
	}

	public void render(Batch batch, float delta) {

		updateTimers(delta);

		tick.setPosition(this.image.getX() + 400, this.image.getY() + 10);

		image.setAlpha(alpha);
		image.draw(batch);

		font.setColor(font.getColor().r,font.getColor().g,font.getColor().b, fontAlpha);
		font.draw(batch, name, (float) this.image.getX() + 15,
				(float) this.image.getY() + 55);

		tick.render(batch, delta);
	}

	public void reverseSelection() {
		this.selected = !this.selected;
	}

	public void move(double deltaX, double deltaY) {
		image.setPosition((float) originPositionX + (float) deltaX,
				(float) originPositionY + (float) deltaY);

		gravityTimer = 1;

		if (image.getY() > maximumYPosition)
			image.setPosition(image.getX(), (float) maximumYPosition);

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

	public boolean checkCollisionTick(int x, int y) {
		return tick.checkCollision(x, y);
	}

	public void applyGravity(double delta) {
		gravityTimer += delta;
		gravityTimer *= gravity;

		double temp = gravityTimer;
		move(0, gravityTimer);
		gravityTimer = temp;

		if (originPositionY >= maximumYPosition) {
			originPositionY = maximumYPosition;
			gravityTimer = 0;
		}
		savePositionAsOriginPosition();
	}

	public void loadFont(BitmapFont font) {
		this.font = font;
		font.setColor(1, 1, 1, 1);
	}

	public void loadTick(Texture tick) {
		this.tick = new Button((int) this.image.getX(),
				(int) this.image.getY(), tick);
		this.tick.setAlphaMinimum(0);
		this.tick.setMultiplier(5);
	}

	void updateTimers(float delta) {

		if (selected == true) {
			tick.select();

			if (highlightButton == false) {
				if (alpha < 0.1f)
					alpha += delta;
				if (alpha > 0.1)
					alpha = 0.1f;
			}
			if (highlightButton == true) {
				if (alpha < 0.4f)
					alpha += delta;
				if (alpha > 0.4f) {
					alpha = 0.4f;
				}
			}

		}
		if (selected == false) {
			tick.deselect();
			if (alpha > 0f)
				alpha -= delta;
			if (alpha < 0f)
				alpha = 0f;
		}

	}

	public String getName() {
		return name;
	}

	public void highlight() {
		highlightButton = true;
	}
	public void lowlight(){
		highlightButton = false;
	}

	public boolean isHighlighted() {
		return highlightButton;
	}
	public void setFontAlpha(float fontAlpha){
		this.fontAlpha = fontAlpha;
	}
}