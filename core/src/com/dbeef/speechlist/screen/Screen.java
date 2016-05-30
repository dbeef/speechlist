package com.dbeef.speechlist.screen;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.dbeef.speechlist.gui.Button;

public class Screen {

	Array<Texture> textures;
	Array<Vector2> texturesPositions;

	Array<String> strings;
	Array<Vector2> stringsPositions;
	Array<Vector2> stringsFontAndAlpha;
	Array<Vector3> stringsColors;

	Array<Sprite> sprites;

	Array<Button> buttons;

	BitmapFont ralewayBlack42;
	BitmapFont ralewayThinItalic12;
	BitmapFont ralewayThinItalic16;
	BitmapFont ralewayThinItalic32;
	BitmapFont ralewayRegular32;
	BitmapFont ralewayMedium38;
	
	public Screen(BitmapFont ralewayBlack42, BitmapFont ralewayThinItalic16,
			BitmapFont ralewayThinItalic32, BitmapFont ralewayThinItalic12, BitmapFont ralewayRegular32, BitmapFont ralewayMedium38) {
		textures = new Array<Texture>();
		texturesPositions = new Array<Vector2>();
		strings = new Array<String>();
		stringsPositions = new Array<Vector2>();
		sprites = new Array<Sprite>();
		buttons = new Array<Button>();
		stringsColors = new Array<Vector3>();
		stringsFontAndAlpha = new Array<Vector2>();
		this.ralewayBlack42 = ralewayBlack42;
		this.ralewayThinItalic12 = ralewayThinItalic12;
		this.ralewayThinItalic16 = ralewayThinItalic16;
		this.ralewayThinItalic32 = ralewayThinItalic32;
		this.ralewayRegular32 = ralewayRegular32;
		this.ralewayMedium38 = ralewayMedium38;
		
	}

	public void add(Sprite sprite) {
		sprites.add(sprite);
	}

	public void add(Texture texture, Vector2 position) {
		textures.add(texture);
		texturesPositions.add(position);
	}

	public void add(String s, Vector2 position, Vector2 fontAndAlpha,
			Vector3 color) {
		strings.add(s);
		stringsPositions.add(position);
		stringsFontAndAlpha.add(fontAndAlpha);
		stringsColors.add(color);
	}

	public void add(Button button) {
		buttons.add(button);
	}

	public void render(Batch batch, float delta) {
		updateTimers(delta);
		renderTextures(batch);
		renderStrings(batch);
		renderButtons(batch, delta);
	}

	public void renderTextures(Batch batch) {
		for (int a = 0; a < textures.size; a++) {
			batch.draw(textures.get(a), texturesPositions.get(a).x,
					texturesPositions.get(a).y);
		}
	}

	public void renderSprites(Batch batch) {
		for (int a = 0; a < sprites.size; a++) {
			sprites.get(a).draw(batch);
		}
	}

	public void renderStrings(Batch batch) {
		for (int a = 0; a < strings.size; a++) {

			switch ((int) stringsFontAndAlpha.get(a).x) {
			case 1: {
				ralewayBlack42.setColor(stringsColors.get(a).x,
						stringsColors.get(a).y, stringsColors.get(a).z,
						stringsFontAndAlpha.get(a).y);

				ralewayBlack42.draw(batch, strings.get(a),
						stringsPositions.get(a).x, stringsPositions.get(a).y);
				break;
			}

			case 2: {
				ralewayThinItalic16.setColor(stringsColors.get(a).x,
						stringsColors.get(a).y, stringsColors.get(a).z,
						stringsFontAndAlpha.get(a).y);

				ralewayThinItalic16.draw(batch, strings.get(a),
						stringsPositions.get(a).x, stringsPositions.get(a).y);
				break;
			}
			case 3: {
				ralewayThinItalic32.setColor(stringsColors.get(a).x,
						stringsColors.get(a).y, stringsColors.get(a).z,
						stringsFontAndAlpha.get(a).y);

				ralewayThinItalic32.draw(batch, strings.get(a),
						stringsPositions.get(a).x, stringsPositions.get(a).y);
				break;
			}
			case 4: {
				ralewayRegular32.setColor(stringsColors.get(a).x,
						stringsColors.get(a).y, stringsColors.get(a).z,
						stringsFontAndAlpha.get(a).y);

				ralewayRegular32.draw(batch, strings.get(a),
						stringsPositions.get(a).x, stringsPositions.get(a).y);
				break;
			}
			case 5: {
				ralewayMedium38.setColor(stringsColors.get(a).x,
						stringsColors.get(a).y, stringsColors.get(a).z,
						stringsFontAndAlpha.get(a).y);

				ralewayMedium38.draw(batch, strings.get(a),
						stringsPositions.get(a).x, stringsPositions.get(a).y);
				break;
			}
			case 6: {
				ralewayThinItalic12.setColor(stringsColors.get(a).x,
						stringsColors.get(a).y, stringsColors.get(a).z,
						stringsFontAndAlpha.get(a).y);

				ralewayThinItalic12.draw(batch, strings.get(a),
						stringsPositions.get(a).x, stringsPositions.get(a).y);
				break;
			}
			default: {

			}
			}
		}
	}

	public void renderButtons(Batch batch, float delta) {
		for (int a = 0; a < buttons.size; a++) {
			buttons.get(a).render(batch, delta);
		}
	}

	public float changeStringAlpha(String search, float accumulator) {
		stringsFontAndAlpha.get(strings.indexOf(search, true)).y += accumulator;
		if (stringsFontAndAlpha.get(strings.indexOf(search, true)).y > 1)
			stringsFontAndAlpha.get(strings.indexOf(search, true)).y = 1;

		return stringsFontAndAlpha.get(strings.indexOf(search, true)).y;
	}

	public void updateTimers(float delta) {
	}

	public void dispose() {

	}
}