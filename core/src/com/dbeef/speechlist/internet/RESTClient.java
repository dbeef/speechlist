package com.dbeef.speechlist.internet;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.HttpMethods;
import com.badlogic.gdx.utils.Json;
import com.dbeef.speechlist.models.Test;
import com.dbeef.speechlist.models.TestNamesContainer;
import com.dbeef.speechlist.models.UniqueIdContainer;
import com.dbeef.speechlist.utils.Variables;

public class RESTClient extends Thread {

	double timer;

	boolean FAILED;

	boolean UNIQUE_IDS_RETRIEVED;
	boolean TEST_RETRIEVED;
	boolean TEST_NAME_RETRIEVED;
	boolean TESTS_NAMES_CONTAINER_RETRIEVED;

	TestNamesContainer testNamesContainer;
	UniqueIdContainer uniqueIdContainer;
	Test test;
	String testName;

	public UniqueIdContainer getUniqueIdContainer() {
		return uniqueIdContainer;
	}

	public boolean getUNIQUE_IDS_RETRIEVED() {
		return UNIQUE_IDS_RETRIEVED;
	}

	public boolean getTEST_RETRIEVED() {
		return TEST_RETRIEVED;
	}

	public boolean getTEST_NAME_RETRIEVED() {
		return TEST_NAME_RETRIEVED;
	}

	public Test getTest() {
		return test;
	}

	public String getTestName() {
		return testName;
	}

	public void run() {
		getUniqueIds();
	}

	public void run(UniqueIdContainer uniqueIdContainer) {
		getTestsNamesContainer(uniqueIdContainer);
	}

	public void run(String task, int uniqueId) {
		if (task.equals(Variables.TASK_RETRIEVE_TEST))
			getTest(uniqueId);
		if (task.equals(Variables.TASK_RETRIEVE_TEST_NAME))
			getTestName(uniqueId);
	}

	void getUniqueIds() {
		System.out.println("Started getting unique ids");
		try {

			HTTPRequest request = new HTTPRequest();

			request.sendRequest(
					Variables.WEBSERVICE_ADRESS + "tests/uniqueids",
					Variables.HEADER_APPLICATION_JSON, HttpMethods.GET, null);

			while (request.isCURRENTLY_RETRIEVING() == true) {
				// wait
				timer += Gdx.graphics.getDeltaTime();
				if (timer > 1) {
					System.out.println("Currently retrieving uniqueIds");
					timer = 0;
				}
			}
			System.out.println("Unique ids retrieved " + UNIQUE_IDS_RETRIEVED);

			if (request.isFAILED() == false)
				uniqueIdContainer = new Json()
						.fromJson(UniqueIdContainer.class,
								request.getRETRIEVED_CONTENT());

			if (Variables.DEBUG_MODE == true) {
				System.out.println("Output from Server - Unique IDs: \n");
				for (int a = 0; a < uniqueIdContainer.getUniqueIds().length; a++) {
					System.out.println(uniqueIdContainer.getUniqueIds()[a]);
				}
			}
			UNIQUE_IDS_RETRIEVED = true;

		} catch (Exception e) {
			FAILED = true;
			UNIQUE_IDS_RETRIEVED = false;
			if (Variables.DEBUG_MODE == true) {
				System.out.println("Failed to retrieve unique ids.");
				e.printStackTrace();
			}
		}
	}

	public boolean FAILED() {
		return FAILED;
	}

	public void getTest(int uniqueId) {
		System.out.println("Started getting particular test");
		try {

			HTTPRequest request = new HTTPRequest();

			request.sendRequest(Variables.WEBSERVICE_ADRESS + "tests/"
					+ Integer.toString(uniqueId),
					Variables.HEADER_APPLICATION_JSON, HttpMethods.GET, null);

			while (request.isCURRENTLY_RETRIEVING() == true) {
				// wait
				timer += Gdx.graphics.getDeltaTime();
				if (timer > 1) {
					System.out.println("Currently retrieving particular test");
					timer = 0;
				}
			}
			
			
			if (request.isFAILED() == false)
				test = new Json().fromJson(Test.class,
						request.getRETRIEVED_CONTENT());
			if(test.getName() != null){
			if (Variables.DEBUG_MODE == true) {
				System.out.println("Output from Server - Test: \n");
				System.out.println(test.getName());
				System.out.println(request.getRETRIEVED_CONTENT());
			}
			TEST_RETRIEVED = true;
			}
			else
			{
				TEST_RETRIEVED = false;	
			}
		} catch (Exception e) {
			FAILED = true;
			TEST_RETRIEVED = false;
			if (Variables.DEBUG_MODE == true) {
				System.out.println("Failed to retrive test.");
				e.printStackTrace();
			}
		}
		
	}

	public void getTestName(int uniqueId) {
		System.out.println("Started getting particular test name");
		try {
			HTTPRequest request = new HTTPRequest();

			request.sendRequest(Variables.WEBSERVICE_ADRESS + "tests/"
					+ Integer.toString(uniqueId) + "/name",
					Variables.HEADER_TEXT_PLAIN, HttpMethods.GET, null);

			while (request.isCURRENTLY_RETRIEVING() == true) {
				// wait
				timer += Gdx.graphics.getDeltaTime();
				if (timer > 1f) {
					System.out
							.println("Currently retrieving particular test name");
					System.out.println(request.getURL());
					timer = 0;
				}

			}

			if (request.isFAILED() == false)
				testName = request.getRETRIEVED_CONTENT();

			if (Variables.DEBUG_MODE == true) {
				System.out.println("Output from Server - Test name: \n");
				System.out.println(testName);
			}
			TEST_NAME_RETRIEVED = true;
		} catch (Exception e) {
			TEST_NAME_RETRIEVED = false;
			FAILED = true;
			if (Variables.DEBUG_MODE == true) {
				System.out.println("Failed to retrieve test name.");
				e.printStackTrace();
			}
		}
	}

	void getTestsNamesContainer(UniqueIdContainer uniqueIdContainer) {
		System.out.println("Started getting getTestsNamesContainer");
		try {

			HTTPRequest request = new HTTPRequest();

			String uniqueIdContainerInString = new Json().toJson(
					uniqueIdContainer, UniqueIdContainer.class);

			request.sendRequest(Variables.WEBSERVICE_ADRESS
					+ "tests/testNamesContainer",
					Variables.HEADER_APPLICATION_JSON, HttpMethods.POST,
					uniqueIdContainerInString);

			System.out.println(uniqueIdContainerInString);

			while (request.isCURRENTLY_RETRIEVING() == true) {
				// wait
				timer += Gdx.graphics.getDeltaTime();
				if (timer > 1) {
					System.out
							.println("Currently retrieving getTestsNamesContainer");
					timer = 0;
				}
			}

			if (request.isFAILED() == false)
				testNamesContainer = new Json().fromJson(
						TestNamesContainer.class,
						request.getRETRIEVED_CONTENT());

			if (Variables.DEBUG_MODE == true) {
				System.out.println("Output from Server - Unique IDs: \n");
				// for (int a = 0; a < uniqueIdContainer.getUniqueIds().length;
				// a++) {
				// System.out.println(uniqueIdContainer.getUniqueIds()[a]);
				// }
				System.out.println("Our unique ids:");
				for (int a = 0; a < uniqueIdContainer.getUniqueIds().length; a++)
					System.out.println(uniqueIdContainer.getUniqueIds()[a]);
			}
			TESTS_NAMES_CONTAINER_RETRIEVED = true;

		} catch (Exception e) {
			FAILED = true;
			TESTS_NAMES_CONTAINER_RETRIEVED = false;
			if (Variables.DEBUG_MODE == true) {
				System.out.println("Failed to retrieve unique ids.");
				e.printStackTrace();
			}
		}
	}
}