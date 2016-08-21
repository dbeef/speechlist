package com.dbeef.speechlist.internet;

import java.util.ArrayList;

import com.badlogic.gdx.utils.Array;
import com.dbeef.speechlist.models.Test;
import com.dbeef.speechlist.models.UniqueIdContainer;
import com.dbeef.speechlist.utils.Variables;

public class DownloadableTestsManager extends Thread {

	boolean RETRIEVED_DOWNLOADABLES;

	Variables variables;
	ArrayList<RESTClient> clients;
	ArrayList<String> names;

	public DownloadableTestsManager() {
		variables = new Variables();
	}

	public boolean RETRIEVED_DOWNLOADABLES() {
		return RETRIEVED_DOWNLOADABLES;
	}

	public ArrayList<String> getNames() {
		return names;
	}

	public void run(UniqueIdContainer uniqueIdContainer, Array<Test> localTests)
			throws InterruptedException {

		if (variables.getDebugMode() == true)
			System.out.println("Started retrieving downloadable tests' names.");

		clients = new ArrayList<RESTClient>();
		names = new ArrayList<String>();

		for (int a = 0; a < uniqueIdContainer.getUniqueIds().length; a++) {
			for (int b = 0; b < localTests.size; b++) {
				if (uniqueIdContainer.getUniqueIds()[a] == localTests.get(b)
						.getUniqueId())
					uniqueIdContainer.getUniqueIds()[a] = -1;
			}
		}

		for (int a = 0; a < uniqueIdContainer.getUniqueIds().length; a++) {
			if (uniqueIdContainer.getUniqueIds()[a] != -1) {
				RESTClient client = new RESTClient();
				client.run(new Variables().RetrieveTestName(), a);
				clients.add(client);
			}
		}

		this.setPriority(MIN_PRIORITY);

		while (allClientsRetrievedTestName() == false) {
			// System.out.println("waiting");
		}

		this.setPriority(NORM_PRIORITY);

		if (variables.getDebugMode() == true)
			System.out.println("Test names retrieved:");
		for (int a = 0; a < clients.size(); a++) {
			names.add(clients.get(a).getTestName());
			if (variables.getDebugMode() == true)
				System.out
						.println("Retrieved: " + clients.get(a).getTestName());
		}

		RETRIEVED_DOWNLOADABLES = true;
	}

	boolean allClientsRetrievedTestName() {
		for (int a = 0; a < clients.size(); a++) {
			if (clients.get(a).TEST_NAME_RETRIEVED == false)
				return false;
		}
		return true;
	}
}