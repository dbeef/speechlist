package com.dbeef.speechlist.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;

public class TestsManager {

	String[] defaultTests;
	Array<TestModel> tests = new Array<TestModel>();

	public TestsManager() {
		loadExternalTests();
		loadDefaultTests();
	}

	public Array<TestModel> getTests() {
		return tests;
	}

	void loadExternalTests() {

		System.out.println("External storage path: "
				+ Gdx.files.getExternalStoragePath() + "\n");

		FileHandle[] files = Gdx.files.external("").list();

		for (FileHandle externalFile : files) {
			if (externalFile.name().contains(".json")) {

				System.out.println("Loading: " + externalFile.name());

				FileHandle file = Gdx.files.external(externalFile.name());
				String test = file.readString();
				Json json = new Json();
				TestModel model = json.fromJson(TestModel.class, test);
				tests.add(model);
				System.out.println("Model name:" + model.name);
				System.out.println("Model length:" + model.length);
				System.out.println("Model id:" + model.id);

				for (int a = 0; a < model.length; a++) {
					System.out.println(model.sentences[a]);
				}

				System.out.println();

			}
		}
		System.out.println("Total number of tests:" + tests.size);

	}

	void loadDefaultTests() {

		defaultTests = new String[4];

		defaultTests[0] = "{  \"sentences\": [    \"Today I found a dog.\",   \"It's brown and lazy.\",    \"It quickly jumps over\",    \"a black fox.\"  ], \"name\": \"Animals test\",  \"id\": 1,  \"length\": 4}";
		defaultTests[1] = "{  \"sentences\": [    \"Today I found a dog.\",   \"It's brown and lazy.\",    \"It quickly jumps over\",    \"a black fox.\"  ], \"name\": \"Animals test\",  \"id\": 1,  \"length\": 4}";
		defaultTests[2] = "{  \"sentences\": [    \"Today I found a dog.\",   \"It's brown and lazy.\",    \"It quickly jumps over\",    \"a black fox.\"  ], \"name\": \"Animals test\",  \"id\": 1,  \"length\": 4}";
		defaultTests[3] = "{  \"sentences\": [    \"Today I found a dog.\",   \"It's brown and lazy.\",    \"It quickly jumps over\",    \"a black fox.\"  ], \"name\": \"Animals test\",  \"id\": 1,  \"length\": 4}";

		for (int a = 0; a < 3; a++) {
			Json json = new Json();
			TestModel model = json.fromJson(TestModel.class, defaultTests[a]);
			tests.add(model);
		}

	}
}