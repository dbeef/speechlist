package com.dbeef.speechlist.gui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class TestButton extends Button {

	// A button with its own characteristic parameters, like name, category,
	// font and uniqueId

	int uniqueId;

	static final double gravity = 1.10f;
	double gravityTimer = 0;
	double originPositionX;
	double originPositionY;
	boolean highlightButton;
	float fontAlpha = 1;

	int maxMovingY = -1;
	int minMovingY = -1;

	int maxDrawingY = -1;
	int minDrawingY = -1;

	boolean disabled;
	boolean withBounds;
	boolean stopShowing;

	String name;
	String category;
	BitmapFont font;
	BitmapFont fontWithBorder;
	Button tick;

	public TestButton(int x, int y, Texture texture, String name) {
		super(x, y, texture);
		this.name = name;
		this.deselect();
		originPositionX = x;
		originPositionY = y;
	}

	public void render(Batch batch, float delta) {

		updateTimers(delta);

		tick.setPosition(this.image.getX() + 400, this.image.getY() + 10);

		image.setAlpha(alpha);
		image.draw(batch);

		if (withBounds == false) {
			font.setColor(font.getColor().r, font.getColor().g,
					font.getColor().b, fontAlpha);
			font.draw(batch, name, (float) this.image.getX() + 15,
					(float) this.image.getY() + 55);
		} else {
			fontWithBorder.setColor(fontWithBorder.getColor().r,
					fontWithBorder.getColor().g, fontWithBorder.getColor().b,
					fontAlpha);
			fontWithBorder.draw(batch, name, (float) this.image.getX() + 15,
					(float) this.image.getY() + 55);
		}
		tick.render(batch, delta);
	}

	public void reverseSelection() {
		this.selected = !this.selected;
	}

	public void move(double deltaX, double deltaY) {
		image.setPosition((float) originPositionX + (float) deltaX,
				(float) originPositionY + (float) deltaY);

		// originPositionX = image.getX();
		// originPositionY = image.getY();
		x = image.getX();
		y = image.getY();
		gravityTimer = 1;
	}

	public void savePositionAsOriginPosition() {
		originPositionX = image.getX();
		originPositionY = image.getY();
	}

	public boolean checkCollision(int x, int y) {
		if (disabled == false) {
			this.bounds.setPosition(image.getX(), image.getY());

			mouse.setPosition(x, y);

			if (mouse.overlaps(bounds))
				return true;
			else
				return false;
		} else
			return false;
	}

	public boolean checkCollisionTick(int x, int y) {
		return tick.checkCollision(x, y);
	}

	public void applyGravity(double delta) {
		// gravityTimer += delta;
		// gravityTimer *= gravity;
		// double temp = gravityTimer;
		// move(0, gravityTimer);
		// gravityTimer = temp;
		// savePositionAsOriginPosition();
	}

	public void loadFont(BitmapFont font) {
		this.font = font;
		font.setColor(1, 1, 1, 1);
	}

	public void loadFontWithBorder(BitmapFont fontWithBorder) {
		this.fontWithBorder = fontWithBorder;
		font.setColor(1, 1, 1, 1);
	}

	public void loadTick(Texture tick) {
		this.tick = new Button((int) this.image.getX(),
				(int) this.image.getY(), tick);
		this.tick.setAlphaMinimum(0);
		this.tick.setMultiplier(5);
	}

	void updateTimers(float delta) {

		if (maxMovingY != -1 && minMovingY != -1) {
			if (y > maxMovingY) {
				y = (int) maxMovingY - 1;
				image.setPosition(image.getX(), maxMovingY - 1);
				savePositionAsOriginPosition();
			}
			if (y < minMovingY) {
				y = (int) minMovingY + 1;
				image.setPosition(image.getX(), minMovingY + 1);
				savePositionAsOriginPosition();
			}
		}

		if (maxDrawingY != -1 && minDrawingY != -1) {

			if (y > maxDrawingY) {

				disabled = true;
				this.deselect();
				tick.deselect();

				alpha -= delta * 8;
				fontAlpha -= delta * 8;

				if (alpha < 0)
					alpha = 0;
				if (fontAlpha < 0)
					fontAlpha = 0;

			}

			if (y > maxDrawingY + 150) {
				alpha = 0;
				fontAlpha = 0;
			}
			if (y < minDrawingY) {
				disabled = true;
				this.deselect();
				tick.deselect();
				alpha -= delta * 6;
				fontAlpha -= delta * 6;

				if (alpha < 0f)
					alpha = 0f;
				if (fontAlpha < 0)
					fontAlpha = 0;
			}
			if (y > minDrawingY && y < maxDrawingY) {

				if (disabled == true)
					alpha += delta;

				if (stopShowing == false)
					fontAlpha += delta * 3;

				if (alpha > 0.1f) {
					alpha = 0.1f;
				}
				if (fontAlpha > 1) {
					fontAlpha = 1;
					disabled = false;
				}
			}
		}

		if (selected == true && disabled == false) {
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

		if (stopShowing == true) {
			disabled = true;
			if (alpha > 0f)
				alpha -= delta * 5f;
			if (alpha < 0f)
				alpha = 0f;
			if (fontAlpha > 0f)
				fontAlpha -= delta * 5f;
			if (fontAlpha < 0f)
				fontAlpha = 0f;
		}

	}

	public String getName() {
		return name;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public void highlight() {
		highlightButton = true;
	}

	public void lowlight() {
		highlightButton = false;
	}

	public boolean isHighlighted() {
		return highlightButton;
	}

	public void setFontAlpha(float fontAlpha) {
		this.fontAlpha = fontAlpha;
	}

	public float getFontAlpha() {
		return fontAlpha;
	}

	public void setPosition(float x, float y) {
		this.x = x;
		this.y = y;
		originPositionX = x;
		originPositionY = y;
		image.setPosition(x, y);
		savePositionAsOriginPosition();
	}

	public void setMaxDrawingY(int maxDrawingY) {
		this.maxDrawingY = maxDrawingY;
	}

	public void setMinDrawingY(int minDrawingY) {
		this.minDrawingY = minDrawingY;
	}

	public void setMaxMovingY(int maxMovingY) {
		this.maxMovingY = maxMovingY;
	}

	public void setMovingMinY(int minMovingY) {
		this.minMovingY = minMovingY;
	}

	public boolean getDisabled() {
		return disabled;
	}

	public int getUniqueId() {
		return uniqueId;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	public void setUniqueId(int uniqueId) {
		this.uniqueId = uniqueId;
	}

	public void setWithBounds(boolean withBounds) {
		this.withBounds = withBounds;
	}

	public void setStopShowing(boolean stopShowing) {
		this.stopShowing = stopShowing;
	}

	public boolean getStopShowing() {
		return stopShowing;
	}
}