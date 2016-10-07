package com.dbeef.speechlist;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.HttpMethods;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.dbeef.speechlist.internet.HTTPRequest;
import com.dbeef.speechlist.models.Test;
import com.dbeef.speechlist.screen.Screen;
import com.dbeef.speechlist.text.DefaultStringsSetter;

public class Starter extends Game {

	Screen gui;
	Screen initial;
	Screen menuHome;
	Screen menuTests;
	Screen menuDownloads;
	Screen menuBrief;
	Array<Screen> solvingScreens;
	Array<BitmapFont> fonts;
	BitmapFont ralewayBlack42;
	BitmapFont ralewayThinItalic12;
	BitmapFont ralewayThinItalic16;
	BitmapFont ralewayThinItalic32;
	BitmapFont ralewayRegular32;
	BitmapFont ralewayMedium38;
	Texture mainBackground;
	Texture logo;

	@Override
	public void create() {

		loadBasicTextures();
		createFonts();
		createScreens();
		Gdx.input.setCatchBackKey(true);
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

		fonts = new Array<BitmapFont>();

		fonts.add(ralewayThinItalic12);
		fonts.add(ralewayThinItalic16);
		fonts.add(ralewayThinItalic32);
		fonts.add(ralewayRegular32);
		fonts.add(ralewayMedium38);
		fonts.add(ralewayBlack42);

	}

	void loadBasicTextures() {
		mainBackground = new Texture("backgrounds/mainBackground.png");
		mainBackground.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		logo = new Texture("icons/speechlistlogo.png");
		logo.setFilter(TextureFilter.Linear, TextureFilter.Linear);
	}

	void createScreens() {
		initial = new Screen(fonts);

		initial.add("Speechlist", new Vector2(130, 450), new Vector2(1, 1),
				new Vector3(1, 1, 1));
		initial.add(mainBackground, new Vector2(0, 0));
		initial.add(mainBackground, new Vector2(-480, 0));
		initial.add(logo, new Vector2(175, 480));
		initial = new DefaultStringsSetter().setInitialStrings(initial);
		
		gui = new Screen(fonts);

		menuHome = new Screen(fonts);
		menuHome.add(mainBackground, new Vector2(480, 0));

		menuTests = new Screen(fonts);
		menuTests.add(mainBackground, new Vector2(960, 0));

		menuDownloads = new Screen(fonts);
		menuDownloads.add(mainBackground, new Vector2(1440, 0));

		menuBrief = new Screen(fonts);
		menuBrief.add(mainBackground, new Vector2(1920, 0));

		solvingScreens = new Array<Screen>();
	}

	public Screen getGuiScreen() {
		return gui;
	}

	public Screen getInitialScreen() {
		return initial;
	}

	public Screen getMenuHome() {
		return menuHome;
	}

	public Screen getMenuDownloads() {
		return menuDownloads;
	}

	public Screen getMenuTests() {
		return menuTests;
	}

	public Screen getMenuBrief() {
		return menuBrief;
	}

	public Array<Screen> getSolvingScreens() {
		return solvingScreens;
	}

	public Array<BitmapFont> getFonts() {
		return fonts;
	}

	public Texture getMainBackground() {
		return mainBackground;
	}
}