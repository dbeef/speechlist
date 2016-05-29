package com.dbeef.speechlist;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.dbeef.speechlist.gui.Button;
import com.dbeef.speechlist.screen.Screen;
import com.dbeef.speechlist.utils.AssetsManager;

public class Starter extends Game {

	public AssetsManager assetsManager;
	public Screen gui;
	public Screen initial;
	public Screen menuHome;
	public Screen menuTests;
	public Screen menuDownloads;
	public BitmapFont ralewayBlack42;
	public BitmapFont ralewayThinItalic16;

	public Button home;
	public Button tests;
	public Button downloads;

	@Override
	public void create() {


		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(
				Gdx.files.internal("fonts/Raleway-Black.ttf"));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = 42;
		parameter.minFilter = TextureFilter.Linear;
		parameter.magFilter = TextureFilter.Linear;

		ralewayBlack42 = generator.generateFont(parameter); // font size 12
															// pixels
		ralewayBlack42.setColor(Color.BLACK);

		generator = new FreeTypeFontGenerator(
				Gdx.files.internal("fonts/Raleway-Thinitalic.ttf"));
		parameter = new FreeTypeFontParameter();
	
		parameter.size = 16;
		parameter.minFilter = TextureFilter.Linear;
		parameter.magFilter = TextureFilter.Linear;

		ralewayThinItalic16 = generator.generateFont(parameter); // font size 12															// pixels
		ralewayThinItalic16.setColor(Color.BLACK);

		generator.dispose(); // don't forget to dispose to avoid memory leaks!

		initial = new Screen(ralewayBlack42, ralewayThinItalic16);
		initial.add("Speechlist", new Vector2(130, 450), new Vector2(1,1),new Vector3(0,0,0));
	
		gui = new Screen(ralewayBlack42, ralewayThinItalic16);
		
		menuHome = new Screen(ralewayBlack42, ralewayThinItalic16);
		
		this.setScreen(new ScreenBoard(this));
	}

	@Override
	public void render() {
		super.render(); // important!
	}
}