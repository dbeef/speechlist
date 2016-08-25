package com.dbeef.speechlist.screen;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.dbeef.speechlist.gui.Button;
import com.dbeef.speechlist.gui.TestButton;

public class Screen {

	Array<Texture> textures;
	Array<Vector2> texturesPositions;
	Array<String> strings;
	Array<Vector2> stringsPositions;
	Array<Vector2> stringsFontAndAlpha;
	Array<Vector3> stringsColors;
	Array<Sprite> sprites;
	Array<Button> buttons;
	Array<TestButton> testButtons;
	BitmapFont ralewayBlack42;
	BitmapFont ralewayThinItalic12;
	BitmapFont ralewayThinItalic16;
	BitmapFont ralewayThinItalic32;
	BitmapFont ralewayRegular32;
	BitmapFont ralewayMedium38;
	boolean isVisible = true;
	boolean render = true;
	float screenAlpha = 1;

	public Screen(Array<BitmapFont> fonts) {
		textures = new Array<Texture>();
		texturesPositions = new Array<Vector2>();
		strings = new Array<String>();
		stringsPositions = new Array<Vector2>();
		sprites = new Array<Sprite>();
		buttons = new Array<Button>();
		testButtons = new Array<TestButton>();
		stringsColors = new Array<Vector3>();
		stringsFontAndAlpha = new Array<Vector2>();

		this.ralewayThinItalic12 = fonts.get(0);
		this.ralewayThinItalic16 = fonts.get(1);
		this.ralewayThinItalic32 = fonts.get(2);
		this.ralewayRegular32 = fonts.get(3);
		this.ralewayMedium38 = fonts.get(4);
		this.ralewayBlack42 = fonts.get(5);
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

	public void add(TestButton button) {
		button.loadFont(ralewayMedium38);
		testButtons.add(button);
	}

	public void render(Batch batch, float delta) {
		if (render == true) {
			updateTimers(delta);
			if (screenAlpha > 0) {
				renderTextures(batch);
				renderStrings(batch);
				renderButtons(batch, delta);
			}
		}
	}

	public void hide() {
		isVisible = false;
	}

	public void show() {
		isVisible = true;
	}

	public void removeAllTextures() {
		textures.clear();
		texturesPositions.clear();
	}

	public boolean textureArrayEmpty() {
		if (textures.size == 0)
			return true;
		else
			return false;
	}

	public void removeTextureWithPosition(Vector2 vec) {
		for (int a = 0; a < textures.size; a++) {
			if (vec.x == texturesPositions.get(a).x
					&& vec.y == texturesPositions.get(a).y) {
				textures.removeIndex(a);
				texturesPositions.removeIndex(a);
			}
		}
	}

	public void renderTextures(Batch batch) {
		for (int a = 0; a < textures.size; a++) {
			batch.setColor(1, 1, 1, screenAlpha);
			batch.draw(textures.get(a), texturesPositions.get(a).x,
					texturesPositions.get(a).y);
			batch.setColor(1, 1, 1, 1);
		}
	}

	public void renderSprites(Batch batch) {
		for (int a = 0; a < sprites.size; a++) {
			sprites.get(a).setAlpha(screenAlpha);
			sprites.get(a).draw(batch);
		}
	}

	public void renderStrings(Batch batch) {
		for (int a = 0; a < strings.size; a++) {

			switch ((int) stringsFontAndAlpha.get(a).x) {
			case 1: {
				if (stringsColors.get(a).z - (1 - screenAlpha) > 0)
					ralewayBlack42.setColor(stringsColors.get(a).x,
							stringsColors.get(a).y, stringsColors.get(a).z
									- (1 - screenAlpha),
							stringsFontAndAlpha.get(a).y);

				ralewayBlack42.draw(batch, strings.get(a),
						stringsPositions.get(a).x, stringsPositions.get(a).y);
				break;
			}

			case 2: {
				if (stringsColors.get(a).z - (1 - screenAlpha) > 0)
					ralewayThinItalic16.setColor(stringsColors.get(a).x,
							stringsColors.get(a).y, stringsColors.get(a).z
									- (1 - screenAlpha),
							stringsFontAndAlpha.get(a).y);

				ralewayThinItalic16.draw(batch, strings.get(a),
						stringsPositions.get(a).x, stringsPositions.get(a).y);
				break;
			}
			case 3: {
				if (stringsColors.get(a).z - (1 - screenAlpha) > 0)
					ralewayThinItalic32.setColor(stringsColors.get(a).x,
							stringsColors.get(a).y, stringsColors.get(a).z
									- (1 - screenAlpha),
							stringsFontAndAlpha.get(a).y);

				ralewayThinItalic32.draw(batch, strings.get(a),
						stringsPositions.get(a).x, stringsPositions.get(a).y);
				break;
			}
			case 4: {
				if (stringsColors.get(a).z - (1 - screenAlpha) > 0)
					ralewayRegular32.setColor(stringsColors.get(a).x,
							stringsColors.get(a).y, stringsColors.get(a).z
									- (1 - screenAlpha),
							stringsFontAndAlpha.get(a).y);

				ralewayRegular32.draw(batch, strings.get(a),
						stringsPositions.get(a).x, stringsPositions.get(a).y);
				break;
			}
			case 5: {
				if (stringsColors.get(a).z - (1 - screenAlpha) > 0)
					ralewayMedium38.setColor(stringsColors.get(a).x,
							stringsColors.get(a).y, stringsColors.get(a).z
									- (1 - screenAlpha),
							stringsFontAndAlpha.get(a).y);

				ralewayMedium38.draw(batch, strings.get(a),
						stringsPositions.get(a).x, stringsPositions.get(a).y);
				break;
			}
			case 6: {
				if (stringsColors.get(a).z - (1 - screenAlpha) > 0)
					ralewayThinItalic12.setColor(stringsColors.get(a).x,
							stringsColors.get(a).y, stringsColors.get(a).z
									- (1 - screenAlpha),
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
			if (screenAlpha > 0 && screenAlpha < 1) {
				if (screenAlpha <= buttons.get(a).getAlphaMinimum())
					buttons.get(a).setAlpha(screenAlpha);
				else
					buttons.get(a).setAlpha(buttons.get(a).getAlphaMinimum());
			}
			if (screenAlpha > 0)
				buttons.get(a).render(batch, delta);

		}
		for (int a = 0; a < testButtons.size; a++) {
			if (screenAlpha > 0) {

				testButtons.get(a).setAlphaMinimum(
						testButtons.get(a).getAlphaMinimum()
								- (1 - screenAlpha));
				testButtons.get(a).setFontAlpha(screenAlpha);
				testButtons.get(a).render(batch, delta);

			}
		}
	}

	public float changeStringAlpha(String search, float accumulator) {
		stringsFontAndAlpha.get(strings.indexOf(search, true)).y += accumulator;
		if (stringsFontAndAlpha.get(strings.indexOf(search, true)).y > 1)
			stringsFontAndAlpha.get(strings.indexOf(search, true)).y = 1;

		return stringsFontAndAlpha.get(strings.indexOf(search, true)).y;
	}

	public void updateTimers(float delta) {

		if (isVisible == true) {
			if (screenAlpha < 1f)
				screenAlpha += 3f * delta;
			if (screenAlpha > 1f)
				screenAlpha = 1f;
		} else {
			if (screenAlpha > 0f)
				screenAlpha -= 3f * delta;
			if (screenAlpha < 0f)
				screenAlpha = 0f;
		}

		if (screenAlpha == 0)
			hide();

	}

	public void dispose() {

	}

	public void removeAllStrings() {
		strings.clear();
		stringsPositions.clear();
		stringsFontAndAlpha.clear();
		stringsColors.clear();
	}

	public void removeStringsWithPosition(Vector2 position) {

		for (int a = 0; a < strings.size; a++) {
			if (stringsPositions.get(a).x == position.x
					&& stringsPositions.get(a).y == position.y) {
				strings.removeIndex(a);
				stringsPositions.removeIndex(a);
				stringsFontAndAlpha.removeIndex(a);
				stringsColors.removeIndex(a);
			}
		}
	}

	public void startRendering() {
		render = true;
	}

	public void stopRendering() {
		render = false;
	}

	public boolean allButtonsDeselected() {
		for (Button b : buttons) {
			if (b.getSelection() == true)
				return false;
		}
		return true;
	}

	public boolean getVisibility() {
		return isVisible;
	}
}
