package com.dbeef.speechlist.utils;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;

public class AssetsManager {

	public Texture home;
	public Texture pencil;
	public Texture cloud;
	public Texture homeBackground;
	public Texture guiFrame;
	public Texture logoLittle;

	public AssetsManager() {
		home = new Texture("icons/home.png");
		pencil = new Texture("icons/pencil.png");
		cloud = new Texture("icons/cloud.png");
		homeBackground = new Texture("backgrounds/homebackground.png");
		guiFrame = new Texture("backgrounds/guiFrame.png");
		logoLittle = new Texture("icons/speechlistlogolittle.png");

		home.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		pencil.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		cloud.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		homeBackground.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		guiFrame.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		logoLittle.setFilter(TextureFilter.Linear, TextureFilter.Linear);

	}
}