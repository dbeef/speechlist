package com.dbeef.speechlist;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.dbeef.speechlist.screen.Screen;

public class Starter extends Game {

	public Screen gui;
	public Screen initial;
	public Screen menuHome;
	public Screen menuTests;
	public Screen menuDownloads;
	public Screen menuBrief;
	public Screen menuSphinx;
	public BitmapFont ralewayBlack42;
	public BitmapFont ralewayThinItalic12;
	public BitmapFont ralewayThinItalic16;
	public BitmapFont ralewayThinItalic32;
	public BitmapFont ralewayRegular32;
	public BitmapFont ralewayMedium38;
	public Texture mainBackground;
	public Texture logo;

	@Override
	public void create() {

		loadBasicTextures();
		createFonts();
		createScreens();

		this.setScreen(new ScreenBoard(this));
	}

	@Override
	public void render() {
		super.render();
	}

	public void createFonts() {

		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(
				Gdx.files.internal("fonts/Raleway-Black.ttf"));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = 42;
		parameter.minFilter = TextureFilter.Linear;
		parameter.magFilter = TextureFilter.Linear;

		ralewayBlack42 = generator.generateFont(parameter);

		ralewayBlack42.setColor(Color.BLACK);

		generator = new FreeTypeFontGenerator(
				Gdx.files.internal("fonts/Raleway-ThinItalic.ttf"));
		parameter = new FreeTypeFontParameter();

		parameter.size = 16;
		parameter.minFilter = TextureFilter.Linear;
		parameter.magFilter = TextureFilter.Linear;

		ralewayThinItalic16 = generator.generateFont(parameter);

		ralewayThinItalic16.setColor(Color.BLACK);

		parameter.size = 32;

		ralewayThinItalic32 = generator.generateFont(parameter);

		ralewayThinItalic32.setColor(Color.BLACK);

		parameter.size = 12;

		ralewayThinItalic12 = generator.generateFont(parameter);

		ralewayThinItalic12.setColor(Color.BLACK);

		generator = new FreeTypeFontGenerator(
				Gdx.files.internal("fonts/Raleway-Regular.ttf"));
		parameter = new FreeTypeFontParameter();

		parameter.size = 32;
		parameter.minFilter = TextureFilter.Linear;
		parameter.magFilter = TextureFilter.Linear;

		ralewayRegular32 = generator.generateFont(parameter);

		ralewayRegular32.setColor(Color.BLACK);

		generator = new FreeTypeFontGenerator(
				Gdx.files.internal("fonts/Raleway-Medium.ttf"));
		parameter = new FreeTypeFontParameter();

		parameter.size = 38;
		parameter.minFilter = TextureFilter.Linear;
		parameter.magFilter = TextureFilter.Linear;

		ralewayMedium38 = generator.generateFont(parameter);

		ralewayMedium38.setColor(Color.BLACK);

		generator.dispose();

	}

	void loadBasicTextures() {
		mainBackground = new Texture("backgrounds/mainBackground.png");
		mainBackground.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		logo = new Texture("icons/speechlistlogo.png");
		logo.setFilter(TextureFilter.Linear, TextureFilter.Linear);
	}

	void createScreens() {
		initial = new Screen(ralewayBlack42, ralewayThinItalic16,
				ralewayThinItalic32, ralewayThinItalic12, ralewayRegular32,
				ralewayMedium38);
		initial.add("Speechlist", new Vector2(130, 450), new Vector2(1, 1),
				new Vector3(1, 1, 1));
		initial.add(mainBackground, new Vector2(0, 0));
		initial.add(mainBackground, new Vector2(-480, 0));
		initial.add(logo, new Vector2(210, 480));

		gui = new Screen(ralewayBlack42, ralewayThinItalic16,
				ralewayThinItalic32, ralewayThinItalic12, ralewayRegular32,
				ralewayMedium38);

		menuHome = new Screen(ralewayBlack42, ralewayThinItalic16,
				ralewayThinItalic32, ralewayThinItalic12, ralewayRegular32,
				ralewayMedium38);

		menuHome.add(mainBackground, new Vector2(480, 0));

		menuTests = new Screen(ralewayBlack42, ralewayThinItalic16,
				ralewayThinItalic32, ralewayThinItalic12, ralewayRegular32,
				ralewayMedium38);

		menuTests.add(mainBackground, new Vector2(960, 0));

		menuDownloads = new Screen(ralewayBlack42, ralewayThinItalic16,
				ralewayThinItalic32, ralewayThinItalic12, ralewayRegular32,
				ralewayMedium38);

		menuDownloads.add(mainBackground, new Vector2(1440, 0));

		menuBrief = new Screen(ralewayBlack42, ralewayThinItalic16,
				ralewayThinItalic32, ralewayThinItalic12, ralewayRegular32,
				ralewayMedium38);
		menuBrief.add(mainBackground, new Vector2(1920, 0));

		menuSphinx = new Screen(ralewayBlack42, ralewayThinItalic16,
				ralewayThinItalic32, ralewayThinItalic12, ralewayRegular32,
				ralewayMedium38);
		menuSphinx.add(mainBackground, new Vector2(2400, 0));

	}
}