package com.dbeef.speechlist.screen;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class StringWithDescription {

	String string;
	String description;
	
	Vector2 currentFontIDAndAlpha;
	Vector3 color;
	Vector2 position;

	public Vector2 getPosition(){
		return position;
	}
	public Vector3 getColor(){
		return color;
	}
	public Vector2 getCurrentFontIDAndAlpha(){
		return currentFontIDAndAlpha;
	}
	
	public StringWithDescription(String string, String description,
			Vector2 position, Vector3 color, Vector2 currentFontIDAndAlpha) {
		this.string = string;
		this.description = description;
		this.position = position;
		this.color = color;
		this.currentFontIDAndAlpha = currentFontIDAndAlpha;
	}

	public String getString() {
		return string;
	}

	public String getDescription() {
		return description;
	}

	public void setString(String string) {
		this.string = string;
	}
}
