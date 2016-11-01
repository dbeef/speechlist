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
	BitmapFont ralewayMedium38WithBorder;
	boolean isVisible = true;
	boolean render = true;
	float screenAlpha = 1;

	public Array<TestButton> getTestsButtons() {
		return testButtons;
	}

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
		this.ralewayMedium38WithBorder = fonts.get(6);
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
		button.loadFontWithBorder(ralewayMedium38WithBorder);
		testButtons.add(button);
	}

	public void render(Batch batch, float delta) {
		if (render == true) {
			updateTimers(delta);
			if (screenAlpha > 0) {
				renderTextures(batch);
				renderStrings(batch);
				renderButtons(batch, delta);
				renderSprites(batch);
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
		int size = textures.size;
		for (int a = 0; a < size; a++) {
			Vector2 texturePosition = texturesPositions.get(a);
			if (vec.x == texturePosition.x && vec.y == texturePosition.y) {
				textures.removeIndex(a);
				texturesPositions.removeIndex(a);
			}
		}
	}

	public void renderTextures(Batch batch) {
		int size = textures.size;
		for (int a = 0; a < size; a++) {
			Texture texture = textures.get(a);
			Vector2 texturePosition = texturesPositions.get(a);
			batch.setColor(1, 1, 1, screenAlpha);
			batch.draw(texture, texturePosition.x, texturePosition.y);
			batch.setColor(1, 1, 1, 1);
		}
	}

	public void renderSprites(Batch batch) {
		int size = sprites.size;
		for (int a = 0; a < size; a++) {
			Sprite sprite = sprites.get(a);
			sprite.draw(batch);
		}
	}

	public void renderStrings(Batch batch) {
		int size = strings.size;
		for (int a = 0; a < size; a++) {

			boolean shouldBeRendered = (stringsColors.get(a).z
					- (1 - screenAlpha) >= 0);

			if (shouldBeRendered) {

				Vector2 currentFontIDAndAlpha = stringsFontAndAlpha.get(a);
				String currentString = strings.get(a);
				BitmapFont currentFont;
				Vector3 currentStringColor = stringsColors.get(a);
				Vector2 currentStringPosition = stringsPositions.get(a);

				switch ((int) currentFontIDAndAlpha.x) {
				case 1: {
					currentFont = ralewayBlack42;
					break;
				}
				case 2: {
					currentFont = ralewayThinItalic16;
					break;
				}
				case 3: {
					currentFont = ralewayThinItalic32;
					break;
				}
				case 4: {
					currentFont = ralewayRegular32;
					break;
				}
				case 5: {
					currentFont = ralewayMedium38;
					break;
				}
				case 6: {
					currentFont = ralewayThinItalic12;
					break;
				}
				default: {
					currentFont = ralewayThinItalic32;
				}
				}
				currentFont.setColor(currentStringColor.x,
						currentStringColor.y, currentStringColor.z
								- (1 - screenAlpha), currentFontIDAndAlpha.y);
				currentFont.draw(batch, currentString, currentStringPosition.x,
						currentStringPosition.y);
			}
		}
	}

	public void renderButtons(Batch batch, float delta) {
		int buttonsSize = buttons.size;
		for (int a = 0; a < buttonsSize; a++) {
			Button button = buttons.get(a);
			if (screenAlpha > 0 && screenAlpha < 1) {
				if (screenAlpha <= button.getAlphaMinimum())
					button.setAlpha(screenAlpha);
				else
					button.setAlpha(button.getAlphaMinimum());
			}
			if (screenAlpha > 0)
				button.render(batch, delta);
		}
		int testButtonsSize = testButtons.size;
		for (int a = 0; a < testButtonsSize; a++) {
			TestButton testButton = testButtons.get(a);
			if (screenAlpha > 0) {
				testButton.setAlphaMinimum(testButton.getAlphaMinimum()
						- (1 - screenAlpha));
				if (testButton.getDisabled() == false)
					testButton.setFontAlpha(screenAlpha);
				testButton.render(batch, delta);

			}
		}
	}

	public float changeStringAlpha(String search, float accumulator) {

		if (strings.indexOf(search, true) != -1) {

			Vector2 fontIDAndAlpha = stringsFontAndAlpha.get(strings.indexOf(
					search, true));

			fontIDAndAlpha.y += accumulator;

			if (fontIDAndAlpha.y > 1)
				fontIDAndAlpha.y = 1;

			if (fontIDAndAlpha.y < 0)
				fontIDAndAlpha.y = 0;

			return fontIDAndAlpha.y;
		} else
			return 0;
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

		int size = strings.size;
		for (int a = 0; a < size; a++) {
			if (stringsPositions.get(a).x == position.x
					&& stringsPositions.get(a).y == position.y) {
				strings.removeIndex(a);
				stringsPositions.removeIndex(a);
				stringsFontAndAlpha.removeIndex(a);
				stringsColors.removeIndex(a);
			}
		}
	}

	public void removeStringContaining(String containing) {
		int size = strings.size;
		for (int a = 0; a < size; a++) {
			String s = strings.get(a);
			if (s.contains(containing))
				strings.removeValue(s, true);
		}
	}

	public void startRendering() {
		render = true;
	}

	public void stopRendering() {
		render = false;
	}

	public boolean allButtonsDeselected() {
		int size = buttons.size;
		for (int a = 0; a < size; a++) {
			Button b = buttons.get(a);
			if (b.getSelection() == true)
				return false;
		}
		return true;
	}

	public boolean getVisibility() {
		return isVisible;
	}

	public void removeAllTestButtons() {
		testButtons.clear();
	}

	public void changeAlphaOfSpriteWithPosition(Vector2 position,
			float accumulator) {
		int size = sprites.size;
		for (int a = 0; a < size; a++) {
			Sprite sprite = sprites.get(a);
			if (position.x == sprite.getX() && position.y == sprite.getY()) {

				if (sprite.getColor().a + accumulator < 1
						&& sprite.getColor().a + accumulator > 0)
					sprite.setAlpha(sprite.getColor().a + accumulator);

				if ((sprite.getColor().a + accumulator > 1))
					sprite.setAlpha(1);

				if ((sprite.getColor().a + accumulator < 0))
					sprite.setAlpha(0);
			}
		}
	}
}