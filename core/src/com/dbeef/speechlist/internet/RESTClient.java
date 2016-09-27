package com.dbeef.speechlist.internet;

import com.badlogic.gdx.utils.Json;
import com.dbeef.speechlist.models.Test;
import com.dbeef.speechlist.models.UniqueIdContainer;
import com.dbeef.speechlist.utils.Variables;

public class RESTClient extends Thread {

	boolean FAILED;

	boolean UNIQUE_IDS_RETRIEVED;
	boolean TEST_RETRIEVED;
	boolean TEST_NAME_RETRIEVED;

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

			request.sendRequest(Variables.WEBSERVICE_ADRESS + "tests/uniqueids");

			while (request.isCURRENTLY_RETRIEVING() == true) {
				// wait
				System.out.println("a");
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
					+ Integer.toString(uniqueId));

			while (request.isCURRENTLY_RETRIEVING() == true) {
				// wait
			}
			if (request.isFAILED() == false)
				test = new Json().fromJson(Test.class,
						request.getRETRIEVED_CONTENT());

			if (Variables.DEBUG_MODE == true) {
				System.out.println("Output from Server - Test: \n");
				System.out.println(test.getName());
			}
			TEST_RETRIEVED = true;
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
					+ Integer.toString(uniqueId) + "/name");

			while (request.isCURRENTLY_RETRIEVING() == true) {
				// wait
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
}