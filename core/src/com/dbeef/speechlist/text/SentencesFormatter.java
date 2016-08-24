package com.dbeef.speechlist.text;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.dbeef.speechlist.utils.Variables;

public class SentencesFormatter {

	Array<String> formatted;
	Array<Vector2> vocabularyPositions;

	Variables variables;

	public SentencesFormatter(String sentences) {

		formatted = new Array<String>();
		vocabularyPositions = new Array<Vector2>();
		variables = new Variables();

		int charPosition = 0;
		int linesCounter = 0;

		final int maxLinesPerTestScreen = variables.getMaxLinesPerTestScreen();
		final int maxCharPerTestLine = variables.getMaxCharPerTestLine();
		final int characterWidth = variables.getCharacterWidth();
		final int solvingScreenVocabularySpanY = variables
				.getSolvingScreenVocabularySpanY();

		while (charPosition != sentences.length()) {

			System.out.println("loop");

			linesCounter++;

			charPosition = maxCharPerTestLine;

			if (linesCounter % maxLinesPerTestScreen == 0
					&& sentences.length() > 0) {
				System.out.println("1");

				if (charPosition < sentences.length()) {
					charPosition = 0;
					while (sentences.charAt(charPosition) != '.' && charPosition <= maxCharPerTestLine) {
						charPosition++;
					}
					formatted.add(new String(sentences.substring(0,
							charPosition + 1)));

					if (formatted.get(linesCounter - 1).startsWith(" "))
						formatted.set(
								linesCounter - 1,
								formatted.get(linesCounter - 1).substring(
										1,
										formatted.get(linesCounter - 1)
												.length()));

					sentences = sentences.substring(charPosition + 1,
							sentences.length());

					charPosition = maxCharPerTestLine;
				}
			}

			if (charPosition < sentences.length()
					&& isLetter(sentences.charAt(charPosition)) == false
					&& isSpecialCharacter(sentences.charAt(charPosition)) == false) {
				formatted.add(new String(sentences.substring(0, charPosition)));
				System.out.println("Sentence added:"
						+ new String(sentences.substring(0, charPosition)));
				System.out.println("sentences begfore:" + sentences);
				sentences = sentences.substring(charPosition);
				System.out.println("sentences after:" + sentences);
				System.out.println("2");
			} else if (charPosition < sentences.length()
					&& isLetter(sentences.charAt(charPosition)) == false
					&& isSpecialCharacter(sentences.charAt(charPosition)) == true) {
				while (isSpecialCharacter(sentences.charAt(charPosition)) == true) {
					charPosition--;
				}
				formatted.add(new String(sentences.substring(0, charPosition)));
				sentences = sentences.substring(charPosition);
			} else if (charPosition < sentences.length()
					&& isLetter(sentences.charAt(charPosition)) == true) {
				formatted.add(new String(sentences.substring(0,
						charPosition - 1) + "-"));
				System.out.println("counter: " + linesCounter + "Before: "
						+ sentences);
				sentences = sentences.substring(charPosition - 1);
				System.out.println("After: " + sentences);
			} else {
				charPosition = sentences.length();
				if (sentences != "") {
					formatted.add(new String(sentences.substring(0,
							charPosition)));
					sentences = sentences.substring(charPosition);
				}
			}

			if (sentences.length() <= maxCharPerTestLine) {
				linesCounter++;
				charPosition = sentences.length();
				System.out.println("breaking! Sentences: " + sentences);
				if (sentences != "" && sentences != " ")
					formatted.add(sentences);
			}

			if (formatted.size > 1) {

				if (formatted.get(linesCounter - 2).endsWith("-")
						&& isLetter(formatted.get(linesCounter - 1).charAt(0)) == false) {
					formatted.set(
							linesCounter - 2,
							formatted.get(linesCounter - 2)
									.substring(
											0,
											formatted.get(linesCounter - 2)
													.length() - 1));
				}

				if (formatted.get(linesCounter - 1).startsWith(".")
						&& isLetter(formatted.get(linesCounter - 2).charAt(
								formatted.get(linesCounter - 2).length() - 1))) {
					formatted.set(linesCounter - 2,
							formatted.get(linesCounter - 2) + ".");
					formatted.set(
							linesCounter - 1,
							formatted.get(linesCounter - 1).substring(1,
									formatted.get(linesCounter - 1).length()));
				}

				if (formatted.get(linesCounter - 2).startsWith(", ")) {
					System.out.println("warning:"
							+ formatted.get(linesCounter - 2));
					formatted.set(
							linesCounter - 2,
							formatted.get(linesCounter - 2).substring(2,
									formatted.get(linesCounter - 2).length()));
				}

			}

			if (formatted.get(linesCounter - 1).endsWith("- ")
					|| formatted.get(linesCounter - 1).endsWith(" -")) {
				formatted.set(
						linesCounter - 1,
						formatted.get(linesCounter - 1).substring(0,
								formatted.get(linesCounter - 1).length() - 2));
			}
			if (formatted.get(linesCounter - 1).endsWith(".-")) {
				formatted.set(
						linesCounter - 1,
						formatted.get(linesCounter - 1).substring(0,
								formatted.get(linesCounter - 1).length() - 1));
			}

			if (formatted.get(linesCounter - 1).startsWith(" ")
					|| formatted.get(linesCounter - 1).startsWith("-")
					|| formatted.get(linesCounter - 1).startsWith(",")) {
				formatted
						.set(linesCounter - 1,
								formatted.get(linesCounter - 1).substring(
										1,
										formatted.get(linesCounter - 1)
												.length()));
			}

		}

		for (int a = 0; a < formatted.size; a++) {

			int b = a;
			int screen = 1;

			while (b > maxLinesPerTestScreen - 1) {
				b -= (maxLinesPerTestScreen);
				screen++;
			}

			String s = formatted.get(a);
			if (s.contains("<<||>>")) {

				float x = variables.getSolvingScreenVocabularyPositionX()
						+ (screen - 1) * variables.getScreenWidth();
				float y = variables.getSolvingScreenVocabularyPositionY() - b
						* solvingScreenVocabularySpanY - 45;

				int iterator = 0;

				while (iterator != s.indexOf("<<||>>")) {
					if (s.charAt(iterator) == 'i' || s.charAt(iterator) == '.')
						x += characterWidth * 0.1f;
					else if (s.charAt(iterator) == 'r')
						x += characterWidth * 0.25f;

					else if (s.charAt(iterator) == ' '
							|| s.charAt(iterator) == 't'
							|| s.charAt(iterator) == 'l')
						x += characterWidth * 0.465f;
					else
						x += characterWidth * 1.05f;

					iterator++;
				}

				vocabularyPositions.add(new Vector2(x, y));
			}
		}
	}

	public Array<String> getFormatted() {

		for (int a = 0; a < formatted.size; a++) {
			if (formatted.get(a).contains("<<||>>"))
				formatted
						.set(a, formatted.get(a).replace("<<||>>", "        "));
		}
		return formatted;
	}

	public Array<Vector2> getVocabularyPositions() {
		return vocabularyPositions;
	}

	boolean isSpecialCharacter(char letter) {
		if (letter == '|' || letter == '>' || letter == '<')
			return true;
		else
			return false;
	}

	boolean isLetter(char letter) {
		if (letter == '.' || letter == ',' || letter == ':' || letter == ';'
				|| letter == ' ' || letter == '\t' || letter == '\r'
				|| letter == '\n' || Character.isWhitespace(letter)
				|| letter == '|' || letter == '>' || letter == '<'
				|| letter == '(' || letter == ')' || letter == '-'
				|| letter == (char) (39))
			return false;
		else
			return true;
	}
}