package com.dbeef.speechlist.gui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class GlareButton extends Button {

	private String name;
	public BitmapFont font;

	public GlareButton(int x, int y, Texture texture, BitmapFont font,
			String name) {
		super(x, y, texture);
		this.font = font;
		this.name = name;

		font.setColor(1, 1, 1, 1);
		this.deselect();
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
}