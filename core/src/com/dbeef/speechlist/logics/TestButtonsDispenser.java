package com.dbeef.speechlist.logics;

import java.util.ArrayList;

import com.badlogic.gdx.utils.Array;
import com.dbeef.speechlist.files.AssetsManager;
import com.dbeef.speechlist.gui.TestButton;
import com.dbeef.speechlist.internet.RESTClient;
import com.dbeef.speechlist.models.Test;
import com.dbeef.speechlist.screen.Screen;
import com.dbeef.speechlist.utils.Variables;

public class TestButtonsDispenser {

	Array<TestButton> testsButtons;
	AssetsManager assetsManager;
	Screen[] tests_local;
	Screen downloads;
	RESTClient client;

	public TestButtonsDispenser(AssetsManager assetsManager,
			Array<TestButton> testsButtons, Screen[] tests_local,
			Screen downloads, RESTClient client) {
		this.testsButtons = testsButtons;
		this.assetsManager = assetsManager;
		this.tests_local = tests_local;
		this.client = client;
		this.downloads = downloads;
	}

	public void addDownloadableTestsButtons(ArrayList<String> names) {

		Array<TestButton> downloadableTestButtons = new Array<TestButton>();

		for (int a = 0; a < names.size(); a++) {
			downloadableTestButtons.add(new TestButton(
					Variables.DOWNLOADS_SCREEN_POSITION - 240, 500 - 80 * a,
					assetsManager.glareButtonVignette, names.get(a)));
			downloadableTestButtons.get(a).loadTick(assetsManager.download);
			downloadableTestButtons.get(a).setCategory(
					Variables.CATEGORY_DOWNLOADABLE);
			downloadableTestButtons.get(a).setUniqueId(
					client.getUniqueIdContainer().getUniqueIds()[a]);
		}

		int buttons_with_Y_below_120 = 0;

		for (int a = 0; a < downloadableTestButtons.size; a++) {
			if (downloadableTestButtons.get(a).getY() < 120)
				buttons_with_Y_below_120++;
			downloadableTestButtons.get(a).setMaxDrawingY(540);
			downloadableTestButtons.get(a).setMinDrawingY(85);
		}
		for (int a = 0; a < downloadableTestButtons.size; a++) {
			downloadableTestButtons.get(a).setMaxMovingY(
					(int) downloadableTestButtons.get(a).getY()
							+ (buttons_with_Y_below_120 - 1) * 80);
			downloadableTestButtons.get(a).setMovingMinY(
					(int) downloadableTestButtons.get(a).getY());
		}

		testsButtons.addAll(downloadableTestButtons);

		for (int a = 0; a < downloadableTestButtons.size; a++) {
			downloads.add(downloadableTestButtons.get(a));
		}
	}

	public void addTestButtons(Array<Test> tests) {

		for(int a= 0;a<tests_local.length;a++){
			tests_local[a].removeAllTestButtons();
		}
		
		Array<TestButton> currentTestButtons = new Array<TestButton>();

		for (int a = 0; a < tests.size; a++) {
			currentTestButtons.add(new TestButton(960, 500 - 80 * a,
					assetsManager.glareButtonVignette, tests.get(a).getName()));
			currentTestButtons.get(a).loadTick(assetsManager.checked);
			currentTestButtons.get(a).setCategory(tests.get(a).getCategory());
		}

		for (int a = 0; a < currentTestButtons.size; a++) {
			if (currentTestButtons.get(a).getCategory()
					.equals(Variables.CATEGORY_VOCABULARY)) {
				currentTestButtons.get(a).setPosition(960,
						500 - 80 * tests_local[0].getTestsButtons().size);
				tests_local[0].add(currentTestButtons.get(a));
			} else if (currentTestButtons.get(a).getCategory()
					.equals(Variables.CATEGORY_IDIOMS)) {
				currentTestButtons.get(a).setPosition(960,
						500 - 80 * tests_local[1].getTestsButtons().size);
				tests_local[1].add(currentTestButtons.get(a));
			} else if (currentTestButtons.get(a).getCategory()
					.equals(Variables.CATEGORY_TENSES)) {
				currentTestButtons.get(a).setPosition(960,
						500 - 80 * tests_local[2].getTestsButtons().size);
				tests_local[2].add(currentTestButtons.get(a));
			} else if (currentTestButtons.get(a).getCategory()
					.equals(Variables.CATEGORY_VARIOUS)) {
				currentTestButtons.get(a).setPosition(960,
						500 - 80 * tests_local[3].getTestsButtons().size);
				tests_local[3].add(currentTestButtons.get(a));
			}
		}

		for (int b = 0; b < tests_local.length; b++) {
			int buttons_with_Y_below_120 = 0;
			Array<TestButton> particularCategoryTestButtons = tests_local[b]
					.getTestsButtons();

			for (int a = 0; a < particularCategoryTestButtons.size; a++) {
				if (particularCategoryTestButtons.get(a).getY() < 120)
					buttons_with_Y_below_120++;

				particularCategoryTestButtons.get(a).setMaxDrawingY(540);
				particularCategoryTestButtons.get(a).setMinDrawingY(85);
			}
			for (int a = 0; a < particularCategoryTestButtons.size; a++) {
				particularCategoryTestButtons.get(a).setMaxMovingY(
						(int) particularCategoryTestButtons.get(a).getY()
								+ (buttons_with_Y_below_120 - 1) * 80);
				particularCategoryTestButtons.get(a).setMovingMinY(
						(int) particularCategoryTestButtons.get(a).getY());
			}
		}
		testsButtons.addAll(currentTestButtons);
	}
}