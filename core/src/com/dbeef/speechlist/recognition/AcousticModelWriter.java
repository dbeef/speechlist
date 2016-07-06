package com.dbeef.speechlist.recognition;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class AcousticModelWriter {

	public void write() {

		
		
		FileHandle acousticModelOrigin = Gdx.files
				.internal("acousticModels/en-us/feat.params");
		FileHandle acousticModelDestination = Gdx.files
				.external("/Speechlist/en-us/feat.params");
		if (acousticModelDestination.exists() == false) {
			acousticModelOrigin.copyTo(acousticModelDestination);
		}
		acousticModelOrigin = Gdx.files.internal("acousticModels/en-us/mdef");
		acousticModelDestination = Gdx.files.external("/Speechlist/en-us/mdef");
		if (acousticModelDestination.exists() == false) {
			acousticModelOrigin.copyTo(acousticModelDestination);
		}
		acousticModelOrigin = Gdx.files.internal("acousticModels/en-us/means");
		acousticModelDestination = Gdx.files
				.external("/Speechlist/en-us/means");
		if (acousticModelDestination.exists() == false) {
			acousticModelOrigin.copyTo(acousticModelDestination);
		}
		acousticModelOrigin = Gdx.files
				.internal("acousticModels/en-us/mixture_weights");
		acousticModelDestination = Gdx.files
				.external("/Speechlist/en-us/mixture_weights");
		if (acousticModelDestination.exists() == false) {
			acousticModelOrigin.copyTo(acousticModelDestination);
		}
		acousticModelOrigin = Gdx.files
				.internal("acousticModels/en-us/noisedict");
		acousticModelDestination = Gdx.files
				.external("/Speechlist/en-us/noisedict");
		if (acousticModelDestination.exists() == false) {
			acousticModelOrigin.copyTo(acousticModelDestination);
		}
		acousticModelOrigin = Gdx.files.internal("acousticModels/en-us/README");
		acousticModelDestination = Gdx.files
				.external("/Speechlist/en-us/README");
		if (acousticModelDestination.exists() == false) {
			acousticModelOrigin.copyTo(acousticModelDestination);
		}

		acousticModelOrigin = Gdx.files
				.internal("acousticModels/en-us/sendump");
		acousticModelDestination = Gdx.files
				.external("/Speechlist/en-us/sendump");
		if (acousticModelDestination.exists() == false) {
			acousticModelOrigin.copyTo(acousticModelDestination);
		}

		acousticModelOrigin = Gdx.files
				.internal("acousticModels/en-us/transition_matrices");
		acousticModelDestination = Gdx.files
				.external("/Speechlist/en-us/transition_matrices");
		if (acousticModelDestination.exists() == false) {
			acousticModelOrigin.copyTo(acousticModelDestination);
		}

		acousticModelOrigin = Gdx.files
				.internal("acousticModels/en-us/variances");
		acousticModelDestination = Gdx.files
				.external("/Speechlist/en-us/variances");
		if (acousticModelDestination.exists() == false) {
			acousticModelOrigin.copyTo(acousticModelDestination);
		}
		
		
		acousticModelOrigin = Gdx.files
				.internal("dictionaries/9401.dic");
		acousticModelDestination = Gdx.files
				.external("/Speechlist/dictionaries/9401.dic");
		if (acousticModelDestination.exists() == false) {
			acousticModelOrigin.copyTo(acousticModelDestination);
		}
		

		acousticModelOrigin = Gdx.files
				.internal("languageModel/9401.lm");
		acousticModelDestination = Gdx.files
				.external("/Speechlist/languageModels/9401.lm");
		if (acousticModelDestination.exists() == false) {
			acousticModelOrigin.copyTo(acousticModelDestination);
		}
		
		
		
		
		
		

	}
}
