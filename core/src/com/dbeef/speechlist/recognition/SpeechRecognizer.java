package com.dbeef.speechlist.recognition;

import java.io.IOException;
import java.util.logging.Logger;

import com.badlogic.gdx.Gdx;
import com.dbeef.speechlist.utils.Variables;

import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;
import edu.cmu.sphinx.api.SpeechResult;

public class SpeechRecognizer extends Thread {

	String lastRecognizedWord = "No recognized words.";
	Variables variables = new Variables();
	
	public void run() {

		if(variables.getDebugMode() == true)
		System.out.println("SpeechRecognizer thread is in running state.");

		Logger cmRootLogger = Logger.getLogger("default.config");
		cmRootLogger.setLevel(java.util.logging.Level.OFF);
		String conFile = System.getProperty("java.util.logging.config.file");
		if (conFile == null) {
			System.setProperty("java.util.logging.config.file",
					"ignoreAllSphinx4LoggingOutput");
		}

		String PATH = Gdx.files.getExternalStoragePath().toString();
		if(new Variables().getDebugMode() == true)
		System.out.println(PATH);
		Configuration configuration = new Configuration();
		configuration
				.setAcousticModelPath("file:" + PATH + "/Speechlist/en-us");
		configuration.setDictionaryPath("file:" + PATH
				+ "/Speechlist/dictionaries/" + "9401.dic");
		configuration.setLanguageModelPath("file:" + PATH
				+ "/Speechlist/languageModels/" + "9401.lm");

		LiveSpeechRecognizer recognizer = null;
		try {
			recognizer = new LiveSpeechRecognizer(configuration);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		if(variables.getDebugMode() == true)
			System.out.println("Done loading acoustic model, dictionary and language model.");

		while (true) {
			recognizer.startRecognition(true);
			SpeechResult result = recognizer.getResult();
			recognizer.stopRecognition();

			
			System.out.println(result.getHypothesis());
			lastRecognizedWord = result.getHypothesis();
			// for (WordResult r : result.getWords()) {
			// System.out.println(r);
			// }

		}
	}

	public String getLastRecognizedWord() {
		return lastRecognizedWord;
	}

}
