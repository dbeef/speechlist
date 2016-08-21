package com.dbeef.speechlist.files;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.dbeef.speechlist.utils.Variables;

public class AssetsManager extends Thread{

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

	public boolean loaded;
	
public void run(){
	
	if (new Variables().getDebugMode() == true)
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

	loaded = true;

	if (new Variables().getDebugMode() == true)
		System.out.println("Done loading assets.");
}
	
}