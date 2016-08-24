package com.dbeef.speechlist;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.dbeef.speechlist.camera.Camera;
import com.dbeef.speechlist.gui.SolutionInput;
import com.dbeef.speechlist.logics.ActionManager;
import com.dbeef.speechlist.screen.Screen;

public class ScreenBoard implements com.badlogic.gdx.Screen {

	SolutionInput solutionInput;

	Screen gui;
	Screen initial;
	Screen menuHome;
	Screen menuTests;
	Screen menuDownloads;
	Screen menuBrief;
	Array<Screen> solvingScreens;

	SpriteBatch batch;
	Viewport viewport;
	Viewport guiViewport;
	Camera camera;
	Camera guiCamera;
	ActionManager actionManager;
	final Starter game;

	float span = 0.5f;
	boolean start;

	public ScreenBoard(final Starter gam) {

		this.game = gam;

		gui = game.getGuiScreen();
		initial = game.getInitialScreen();
		menuHome = game.getMenuHome();
		menuTests = game.getMenuTests();
		menuDownloads = game.getMenuDownloads();
		menuBrief = game.getMenuBrief();
		solvingScreens = game.getSolvingScreens();

		camera = new Camera(480, 800);
		camera.position.x = -240;
		camera.position.y = 400;
		camera.move(240);

		guiCamera = new Camera(480, 800);
		guiCamera.position.x = -240;
		guiCamera.position.y = 400;
		guiCamera.move(240);

		batch = new SpriteBatch();

		viewport = new FillViewport(800, 480, camera);
		guiViewport = new FillViewport(800, 480, guiCamera);

		solutionInput = new SolutionInput(game.getMainBackground(), 959);
		
		actionManager = new ActionManager(camera, guiCamera, initial, gui,
				menuHome, menuTests, menuDownloads, menuBrief, solvingScreens,
				game.getFonts(), game.getMainBackground(),solutionInput);
	}

	@Override
	public void render(float delta) {

		delta = manageLowFpsOnLaunch(delta);

		try {
			actionManager.updateLogics(delta);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		camera.updateTimers(delta);
		camera.update();
		guiCamera.updateTimers(delta);
		guiCamera.update();

		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

		batch.setProjectionMatrix(camera.combined);
		batch.begin();

		drawScreens(delta);
		
		batch.end();

		batch.setProjectionMatrix(guiCamera.combined);
		batch.begin();

		drawGui(delta);
		drawSolutionInput(delta);
		
		batch.end();

	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	float manageLowFpsOnLaunch(float delta) {

		if (delta < 0.1f)
			start = true;

		if (start == true)
			span -= delta;

		if (Gdx.graphics.getRawDeltaTime() > 0.05f
				&& Gdx.graphics.getDeltaTime() > 0.05f)
			delta = 0;

		if (span >= 0)
			return 0;

		return delta;
	}

	void drawScreens(float delta) {
		initial.render(batch, delta);
		menuHome.render(batch, delta);
		menuTests.render(batch, delta);
		menuDownloads.render(batch, delta);
		menuBrief.render(batch, delta);
		for (Screen screen : solvingScreens)
			screen.render(batch, delta);
	}
	
	void drawSolutionInput(float delta){
		solutionInput.render(batch, delta);
	}

	void drawGui(float delta) {
		gui.render(batch, delta);
	}

}