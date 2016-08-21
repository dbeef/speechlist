package com.dbeef.speechlist.internet;

import javax.ws.rs.core.MediaType;

import com.dbeef.speechlist.models.Test;
import com.dbeef.speechlist.models.UniqueIdContainer;
import com.dbeef.speechlist.utils.Variables;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class RESTClient extends Thread {

	boolean FAILED;
	boolean UNIQUE_IDS_RETRIEVED;
	boolean TEST_RETRIEVED;
	boolean TEST_NAME_RETRIEVED;

	Variables variables;
	UniqueIdContainer uniqueIdContainer;
	Test test;
	String testName;

	public RESTClient() {
		variables = new Variables();
	}

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
		if (task.equals(new Variables().retrieveTest()))
			getTest(uniqueId);
		if (task.equals(new Variables().RetrieveTestName()))
			getTestName(uniqueId);
	}

	void getUniqueIds() {
		try {
			Client client = Client.create();

			WebResource webResource = client
					.resource("http://annihilator:8080/UserManagement/rest/TestService/tests/uniqueids");

			ClientResponse response = webResource.accept("application/json")
					.get(ClientResponse.class);

			if (response.getStatus() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ response.getStatus());
			}
			uniqueIdContainer = response.getEntity(UniqueIdContainer.class);

			if (variables.getDebugMode() == true) {
				System.out.println("Output from Server - Unique IDs: \n");
				for (int a = 0; a < uniqueIdContainer.getUniqueIds().length; a++) {
					System.out.println(uniqueIdContainer.getUniqueIds()[a]);
				}
			}
			UNIQUE_IDS_RETRIEVED = true;
		} catch (Exception e) {
			FAILED = true;
			UNIQUE_IDS_RETRIEVED = false;
			if (variables.getDebugMode() == true) {
				System.out.println("Failed to retrieve unique ids.");
				e.printStackTrace();
			}
		}
	}

	public boolean FAILED() {
		return FAILED;
	}

	public void getTest(int uniqueId) {
		try {
			Client client = Client.create();

			WebResource webResource = client
					.resource("http://annihilator:8080/UserManagement/rest/TestService/tests/"
							+ Integer.toString(uniqueId));

			ClientResponse response = webResource.accept("application/json")
					.get(ClientResponse.class);

			if (response.getStatus() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ response.getStatus());
			}
			test = response.getEntity(Test.class);
			if (variables.getDebugMode() == true) {
				System.out.println("Output from Server - Test: \n");
				System.out.println(test.getName());
			}
			TEST_RETRIEVED = true;
		} catch (Exception e) {
			FAILED = true;
			TEST_RETRIEVED = false;
			if (variables.getDebugMode() == true) {
				System.out.println("Failed to retrive test.");
				e.printStackTrace();
			}
		}
	}

	public void getTestName(int uniqueId) {
		try {
			Client client = Client.create();

			WebResource webResource = client
					.resource("http://annihilator:8080/UserManagement/rest/TestService/tests/"
							+ Integer.toString(uniqueId) + "/name");

			ClientResponse response = webResource.accept(MediaType.TEXT_PLAIN)
					.get(ClientResponse.class);

			if (response.getStatus() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ response.getStatus());
			}
			testName = response.getEntity(String.class);
			if (variables.getDebugMode() == true) {
				System.out.println("Output from Server - Test name: \n");
				System.out.println(testName);
			}
			TEST_NAME_RETRIEVED = true;
		} catch (Exception e) {
			TEST_NAME_RETRIEVED = false;
			FAILED = true;
			if (variables.getDebugMode() == true) {
				System.out.println("Failed to retrieve test name.");
				e.printStackTrace();
			}
		}
	}

}