package com.dbeef.speechlist.recognition;

import java.io.IOException;
import java.util.logging.Logger;

import com.badlogic.gdx.Gdx;
import com.dbeef.speechlist.utils.Variables;

public class SpeechRecognizer extends Thread {

	String lastRecognizedWord = "No recognized words.";
	Variables variables = new Variables();
	
	public void run() {
	}

	public String getLastRecognizedWord() {
		return lastRecognizedWord;
	}

}
