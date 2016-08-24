package com.dbeef.speechlist.gui;

import com.badlogic.gdx.graphics.Texture;

public class SolutionInputButton extends Button {

	boolean clicked;
	
	public SolutionInputButton(int x, int y, Texture texture) {
		super(x, y, texture);
	}
	public SolutionInputButton(float x, float y, Texture texture) {
		super(x, y, texture);
	}

	public boolean checkCollision(int x, int y) {

		this.bounds.setPosition(image.getX(), image.getY());

		mouse.setPosition(x, y);

		if (mouse.overlaps(bounds)) {
			clicked = true;
			return true;
		} else
			return false;
	}

	public boolean getClicked() {
		return clicked;
	}

	public void setClicked(boolean clicked){
		this.clicked = clicked;
	}
}
