package com.dbeef.speechlist.tests;

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

		defaultTests[0] = "{\"sentences\": [\"German people live in\",\"France's south neighbour is\",\"The biggest continent is\",\"Australia is both country and\",\"Russia's capital is\",\"Paris is the capital of\"],\"vocabulary\": [\"Germany\",\"Spain\",\"Asia\",\"continent\",\"Moscow\",\"France\"],\"name\": \"Countries test\",\"id\": 4,\"length\": 6}";
		defaultTests[1] = "{\"sentences\": [\"German people live in\",\"France's south neighbour is\",\"The biggest continent is\",\"Australia is both country and\",\"Russia's capital is\",\"Paris is the capital of\"],\"vocabulary\": [\"Germany\",\"Spain\",\"Asia\",\"continent\",\"Moscow\",\"France\"],\"name\": \"Countries test\",\"id\": 4,\"length\": 6}";
		defaultTests[2] = "{\"sentences\": [\"German people live in\",\"France's south neighbour is\",\"The biggest continent is\",\"Australia is both country and\",\"Russia's capital is\",\"Paris is the capital of\"],\"vocabulary\": [\"Germany\",\"Spain\",\"Asia\",\"continent\",\"Moscow\",\"France\"],\"name\": \"Countries test\",\"id\": 4,\"length\": 6}";
		defaultTests[3] = "{\"sentences\": [\"German people live in\",\"France's south neighbour is\",\"The biggest continent is\",\"Australia is both country and\",\"Russia's capital is\",\"Paris is the capital of\"],\"vocabulary\": [\"Germany\",\"Spain\",\"Asia\",\"continent\",\"Moscow\",\"France\"],\"name\": \"Countries test\",\"id\": 4,\"length\": 6}";
		
		for (int a = 0; a < 3; a++) {
			Json json = new Json();
			TestModel model = json.fromJson(TestModel.class, defaultTests[a]);
			tests.add(model);
		}

	}

	public TestModel getTest(String testName) {

		for (int a = 0; a < tests.size; a++) {
			if (tests.get(a).getName() == testName)
				return tests.get(a);
		}
		return new TestModel();
	}
	void saveDefaultDictionary(){	
	}
}