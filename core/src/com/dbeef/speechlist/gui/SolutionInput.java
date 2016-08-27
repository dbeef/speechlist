package com.dbeef.speechlist.gui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Array;

public class SolutionInput {

	int currentSolutionIndex;
	int x;
	boolean isVisible;
	float backgroundAlpha;
	Sprite background;
	Array<TestButton> vocabularyButtons;
	Array<String> correctAnswers;
	Array<String> answers;
	Texture buttonTexture;
	Texture tick;
	BitmapFont font;
	int uniqueID;

	public void setCurrentTestUniqueID(int uniqueID) {
		this.uniqueID = uniqueID;
	}
	public int getCurrentTestUniqueID(){
		return uniqueID;
	}

	public SolutionInput(Texture texture, int x) {
		background = new Sprite(texture);
		background.setPosition(x, 0);
		backgroundAlpha = 0;
		vocabularyButtons = new Array<TestButton>();
		correctAnswers = new Array<String>();
		answers = new Array<String>();

		this.x = x;
	}

	public void addAnswer(String answer) {
		answers.set(currentSolutionIndex, answer);
	}

	public Array<TestButton> getVocabularyButtons() {
		return vocabularyButtons;
	}

	public void setButtonTexture(Texture buttonTexture) {
		this.buttonTexture = buttonTexture;
	}

	public void setButtonFont(BitmapFont font) {
		this.font = font;
	}

	public void setButtonTick(Texture tick) {
		this.tick = tick;
	}

	public void createButtons(String[] vocabulary) {
		answers = new Array<String>();
		correctAnswers = new Array<String>();

		vocabularyButtons.clear();

		Array<String> vocabularyArray = new Array<String>();
		for (int a = 0; a < vocabulary.length; a++) {
			vocabularyArray.add(vocabulary[a]);
			correctAnswers.add(vocabulary[a]);
			answers.add("");
		}

		vocabularyArray.shuffle();

		for (int a = 0; a < vocabularyArray.size; a++) {
			TestButton vocabularyButton = new TestButton(x, 700 - a * 80,
					buttonTexture, vocabularyArray.get(a));
			vocabularyButton.loadFont(font);
			vocabularyButton.loadTick(tick);
			vocabularyButtons.add(vocabularyButton);
		}
	}

	public void render(Batch batch, float delta) {

		if (backgroundAlpha != 0) {
			background.setAlpha(backgroundAlpha);
			background.draw(batch);

			for (int a = 0; a < vocabularyButtons.size; a++) {
				vocabularyButtons.get(a).setFontAlpha(backgroundAlpha);
				vocabularyButtons.get(a).render(batch, delta);
			}
		}
		if (isVisible == true) {
			if (backgroundAlpha < 1f)
				backgroundAlpha += 3f * delta;
			if (backgroundAlpha > 1f)
				backgroundAlpha = 1f;
		} else {
			if (backgroundAlpha > 0f)
				backgroundAlpha -= 3f * delta;
			if (backgroundAlpha < 0f)
				backgroundAlpha = 0f;
		}

		if (backgroundAlpha == 0)
			hide();
	}

	public boolean getVisibility() {
		return isVisible;
	}

	public void show(int currentSolutionIndex) {
		isVisible = true;
		this.currentSolutionIndex = currentSolutionIndex;
	}

	public void hide() {
		isVisible = false;
	}

	public float getSummary() {
		int correct = 0;
		for (int a = 0; a < correctAnswers.size; a++) {
			System.out.println(correctAnswers.get(a) + " vs your answer "
					+ answers.get(a));
			if (answers.get(a).equals(correctAnswers.get(a)))
				correct++;
		}

		if (correct == 0)
			return 0;
		else
			return ((float) correct / correctAnswers.size);
	}

	public boolean isAlphaZero() {
		if (backgroundAlpha == 0)
			return true;
		else
			return false;
	}
}