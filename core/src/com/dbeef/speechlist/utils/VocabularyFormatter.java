package com.dbeef.speechlist.utils;

import com.badlogic.gdx.utils.Array;

public class VocabularyFormatter {
	static final int maxCharactersPerLine = 17;

	Array<String> vocabulary = new Array<String>();

	public String[] formatVocabulary(String[] vocabulary, int length) {

		this.vocabulary.addAll(vocabulary, 0, length);
		
		String[] output = new String[length];

		for (int a = 0; a < length; a++) {
			output[a] = getBiggestWord() + "   " + getBiggestWord();
		}

		return output;

	}

	String getBiggestWord() {
		String s = "";
		for (int a = 0; a < vocabulary.size; a++) {
			if (vocabulary.get(a).length() > s.length())
				s = vocabulary.get(a);
		}
		vocabulary.removeValue(s, true);
		return s;
	}
}