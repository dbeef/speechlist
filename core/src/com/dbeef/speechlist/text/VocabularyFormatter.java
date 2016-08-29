package com.dbeef.speechlist.text;

import com.badlogic.gdx.utils.Array;

public class VocabularyFormatter {
	static final int maxCharactersPerLine = 14;

	Array<String> vocabulary = new Array<String>();

	public String[] formatVocabulary(String[] vocabulary, int length) {

		this.vocabulary.addAll(vocabulary, 0, length);

		String[] output = new String[length];
		for (int a = 0; a < output.length; a++)
			output[a] = "";

		for (int a = 0; a < length; a++) {

			output[a] += getBiggestWord() + " " + getSmallestWord();
			output[a] = output[a].trim();
		}

		int charactersInOutput = 0;
		for (int a = 0; a < output.length; a++)
			charactersInOutput += output[a].length();

		if (charactersInOutput > 90){
			String[] s = new String[3];
			s[0] = "Over " + output.length;
			s[1] = "lines of";
			s[2] = "vocabulary";
			return s;
		}
		else
			return output;

	}

	String getSmallestWord() {
		boolean changed = false;
		String s = "___________________________________________";
		for (int a = 0; a < vocabulary.size; a++) {
			if (vocabulary.get(a).length() < s.length()) {
				s = vocabulary.get(a);
				changed = true;
			}
		}
		if (changed == true) {
			vocabulary.removeValue(s, true);
			return s;
		} else
			return " ";
	}

	String getBiggestWord() {
		String s = "";
		for (int a = 0; a < vocabulary.size; a++) {
			if (vocabulary.get(a).length() > s.length())
				s = vocabulary.get(a);
		}
		if (vocabulary.size != 0) {
			vocabulary.removeValue(s, true);
			return s;
		} else
			return " ";
	}
}