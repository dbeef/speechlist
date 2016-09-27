package com.dbeef.speechlist.files;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.dbeef.speechlist.models.Test;
import com.dbeef.speechlist.utils.Variables;

public class TestsManager {

	String[] defaultTests;
	Array<Test> tests = new Array<Test>();

	public TestsManager() {
		loadExternalStorageTests();
		loadInternalStorageTests();
		loadHardcodedTests();
	}

	public Array<Test> getTests() {
		return tests;
	}

	void loadExternalStorageTests() {
		int tests_loaded_counter = 0;

		if (Variables.DEBUG_MODE == true)
			System.out.println("Started loading external storage tests.");

		if (Variables.DEBUG_MODE == true)
			System.out.println("External storage path: "
					+ Gdx.files.getExternalStoragePath() + "\n"
					+ "Loading .json files.");

		FileHandle[] files = Gdx.files.external("").list();

		for (FileHandle externalFile : files) {
			if (externalFile.name().contains(".json")) {

				if (Variables.DEBUG_MODE == true)
					System.out.println("Loading: " + externalFile.name());

				FileHandle file = Gdx.files.external(externalFile.name());
				String test = file.readString();
				Json json = new Json();
				Test model = json.fromJson(Test.class, test);
				tests.add(model);
				tests_loaded_counter++;

				if (Variables.DEBUG_MODE == true) {
					System.out.println("Model name:" + model.getName());
					System.out.println("Model id:" + model.getId());

					System.out.println(model.getSentences());

					System.out.println();
				}
			}
		}

		if (Variables.DEBUG_MODE == true)
			System.out
					.println("Total number of tests loaded from external storage:"
							+ tests_loaded_counter);

	}

	void loadInternalStorageTests() {

		if (Variables.DEBUG_MODE == true)
			System.out.println("Loading internal app storage tests.");

		int tests_loaded_counter = 0;

		FileHandle dirHandle;
		if (Gdx.app.getType() == ApplicationType.Android) {
			dirHandle = Gdx.files.internal("default_tests");
		} else {
			// ApplicationType.Desktop
			
			dirHandle = Gdx.files.internal("./bin/default_tests");
		
			//On OS X '/bin/' part causes problems with loading tests
			
			if (dirHandle.exists() == false)
				dirHandle = Gdx.files.internal("./default_tests");
		
		}
		for (FileHandle entry : dirHandle.list()) {
			System.out.println(entry.name());
		}

		for (FileHandle entry : dirHandle.list()) {
			if (entry.name().contains(".json")) {

				if (Variables.DEBUG_MODE == true)
					System.out.println("Loading: " + entry.name());

				FileHandle file = Gdx.files.internal(dirHandle.path() + "/"
						+ entry.name());

				String test = file.readString();
				Json json = new Json();
				Test model = json.fromJson(Test.class, test);
				tests.add(model);
				tests_loaded_counter++;

				if (Variables.DEBUG_MODE == true) {
					System.out.println("Model name:" + model.getName());
					System.out.println("Model id:" + model.getId());
					System.out.println("Test category: " + model.getCategory());

					System.out.println(model.getSentences());

					System.out.println();
				}
			}
		}

		if (Variables.DEBUG_MODE == true) {
			System.out.println("Done loading internal app storage tests.");
			System.out.println("Loaded: " + tests_loaded_counter);
		}
	}

	void loadHardcodedTests() {

		int tests_loaded_counter = 0;

		if (Variables.DEBUG_MODE == true)
			System.out.println("Loading hardcoded tests.");

		defaultTests = new String[0];

		// defaultTests[0] =
		// "{\"id\": 2,\"uniqueId\": 1, \"name\": \"Fruits test\",  \"vocabulary\": [    \"Germany\",    \"Spain\",    \"Asia\",    \"continent\",    \"Moscow\",    \"France\"  ], \"sentences\": [   \"German people live in\",  \"France's south neighbour is\",    \"The biggest continent is\",    \"Australia is both country and\",   \"Russia's capital is\",    \"Paris is the capital of\"  ]}";
		// defaultTests[1] =
		// "{\"id\": 3,\"uniqueId\": 2, \"name\": \"Cities test\",  \"vocabulary\": [    \"Germany\",    \"Spain\",    \"Asia\",    \"continent\",    \"Moscow\",    \"France\"  ], \"sentences\": [   \"German people live in\",  \"France's south neighbour is\",    \"The biggest continent is\",    \"Australia is both country and\",   \"Russia's capital is\",    \"Paris is the capital of\"  ]}";
		// defaultTests[2] =
		// "{\"id\": 1,\"uniqueId\": 3, \"name\": \"Cars test\",  \"vocabulary\": [    \"Germany\",    \"Spain\",    \"Asia\",    \"continent\",    \"Moscow\",    \"France\"  ], \"sentences\": [   \"German people live in\",  \"France's south neighbour is\",    \"The biggest continent is\",    \"Australia is both country and\",   \"Russia's capital is\",    \"Paris is the capital of\"  ]}";
		// defaultTests[3] =
		// "{\"id\": 5,\"uniqueId\": 5, \"name\": \"Flowers test\",  \"vocabulary\": [    \"Germany\",    \"Spain\",    \"Asia\",    \"continent\",    \"Moscow\",    \"France\"  ], \"sentences\": [   \"German people live in\",  \"France's south neighbour is\",    \"The biggest continent is\",    \"Australia is both country and\",   \"Russia's capital is\",    \"Paris is the capital of\"  ]}";

		for (int a = 0; a < defaultTests.length; a++) {
			Json json = new Json();
			Test model = json.fromJson(Test.class, defaultTests[a]);
			tests.add(model);
			tests_loaded_counter++;
		}

		if (Variables.DEBUG_MODE == true) {
			System.out.println("Done loading hardcoded tests.");
			System.out.println("Loaded: " + tests_loaded_counter);
		}
	}

	public Test getTest(String testName) {

		for (int a = 0; a < tests.size; a++) {
			if (tests.get(a).getName() == testName)
				return tests.get(a);
		}
		return new Test();
	}

	void saveDefaultDictionary() {
	}
}