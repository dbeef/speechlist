package com.dbeef.speechlist.text;

import com.badlogic.gdx.utils.Array;

public class SentencesFormatter {

	static final int maxCharPerLine = 32;
	Array<String> formatted;

	public SentencesFormatter(String sentences) {
		formatted = new Array<String>();

		int position = 0;
		int counter = 0;

		while (position != sentences.length()) {

			counter++;

			position = maxCharPerLine;

			if (position < sentences.length()
					&& isLetter(sentences.charAt(position)) == false) {
				formatted.add(new String(sentences.substring(0, position)));
				sentences = sentences.substring(position);
			} else if (isLetter(sentences.charAt(position)) == true) {
				formatted.add(new String(sentences.substring(0, position - 1)
						+ "-"));
				sentences = sentences.substring(position - 1);
			} else {
				formatted.add(new String(sentences.substring(0, position)));
				sentences = sentences.substring(position);
			}

			if (sentences.length() < maxCharPerLine) {
				formatted.add(new String(sentences));
				break;
			}

			if (formatted.size > 1) {
				if (formatted.get(counter - 2).endsWith("-")
						&& isLetter(formatted.get(counter - 1).charAt(0)) == false) {
					formatted.set(
							counter - 2,
							formatted.get(counter - 2).substring(0,
									formatted.get(counter - 2).length() - 1));
				}
				if (formatted.get(counter - 1).startsWith(".")
						&& isLetter(sentences.charAt(formatted.get(counter - 2)
								.length()))) {
					formatted
							.set(counter - 2, formatted.get(counter - 2) + ".");
					formatted.set(counter - 1, formatted.get(counter - 1)
							.substring(1, formatted.get(counter - 1).length()));
				}
			}

			if (formatted.get(counter - 1).endsWith("- ")
					|| formatted.get(counter - 1).endsWith(" -")) {
				formatted.set(counter - 1, formatted.get(counter - 1)
						.substring(0, formatted.get(counter - 1).length() - 2));
			}
			if (formatted.get(counter - 1).endsWith(".-")) {
				formatted.set(counter - 1, formatted.get(counter - 1)
						.substring(0, formatted.get(counter - 1).length() - 1));
			}

			if (formatted.get(counter - 1).startsWith(" ")
					|| formatted.get(counter - 1).startsWith("-")) {
				formatted.set(counter - 1, formatted.get(counter - 1)
						.substring(1, formatted.get(counter - 1).length()));
			}
			
		}
	}

	public Array<String> getFormatted() {
		return formatted;
	}

	boolean isLetter(char letter) {
		if (letter == '.' || letter == ',' || letter == ' ' || letter == ':'
				|| letter == ';' || letter == ' ' || letter == '\t'
				|| letter == '\r' || letter == '\n'
				|| Character.isWhitespace(letter))
			return false;
		else
			return true;
	}
}