package com.dbeef.speechlist.files;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.dbeef.speechlist.utils.Variables;

public class AssetsManager extends Thread {

	public Texture left;
	public Texture right;
	public Texture home;
	public Texture pencil;
	public Texture cloud;
	public Texture logoLittle;
	public Texture clock;
	public Texture chart;
	public Texture checked;
	public Texture glareButtonVignette;
	public Texture tick;
	public Texture sadPhone;
	public Texture cross;
	public Texture wordSet;
	public Texture wordNotSet;
	public Texture various;
	public Texture tenses;
	public Texture idioms;
	public Texture vocabulary;
	public Texture antenna;
	public Texture mainBackground_middle;

	public boolean loaded;

	public void run() {

		if (Variables.DEBUG_MODE == true)
			System.out.println("Started loading assets.");

		glareButtonVignette = new Texture("buttons/glareButtonVignette.png");
		home = new Texture("icons/home.png");
		tick = new Texture("icons/tick.png");
		pencil = new Texture("icons/pencil.png");
		cloud = new Texture("icons/cloud.png");
		logoLittle = new Texture("icons/speechlistlogolittle.png");
		clock = new Texture("icons/clock.png");
		chart = new Texture("icons/chart.png");
		checked = new Texture("icons/checked.png");
		sadPhone = new Texture("icons/sadphone.png");
		cross = new Texture("icons/cross.png");
		left = new Texture("icons/left.png");
		right = new Texture("icons/right.png");
		wordSet = new Texture("icons/wordSet.png");
		wordNotSet = new Texture("icons/wordNotSet.png");
		various = new Texture("icons/various.png");
		idioms = new Texture("icons/idioms.png");
		tenses = new Texture("icons/tenses.png");
		antenna = new Texture("icons/antenna.png");
		vocabulary = new Texture("icons/vocabulary.png");
		mainBackground_middle = new Texture("backgrounds/mainBackground_middle.png");

		glareButtonVignette.setFilter(TextureFilter.Linear,
				TextureFilter.Linear);
		home.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		tick.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		pencil.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		cloud.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		logoLittle.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		clock.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		chart.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		checked.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		sadPhone.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		cross.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		left.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		right.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		wordSet.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		wordNotSet.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		various.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		idioms.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		tenses.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		vocabulary.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		mainBackground_middle.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);

		loaded = true;

		if (Variables.DEBUG_MODE == true)
			System.out.println("Done loading assets.");
	}

}