package com.dbeef.speechlist.utils;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;

public class AssetsManager {

	public Texture home;
	public Texture pencil;
	public Texture cloud;
	
	public AssetsManager(){
		home = new Texture("icons/home.png");
		pencil = new Texture("icons/pencil.png");
		cloud = new Texture("icons/cloud.png");
	
		home.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		pencil.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		cloud.setFilter(TextureFilter.Linear, TextureFilter.Linear);
	}
}