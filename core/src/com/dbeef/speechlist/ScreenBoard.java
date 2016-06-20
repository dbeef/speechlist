package com.dbeef.speechlist;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.dbeef.speechlist.camera.Camera;
import com.dbeef.speechlist.logics.ActionManager;

public class ScreenBoard implements Screen {
	
	SpriteBatch batch;
	Viewport viewport;
	Viewport guiViewport;
	Camera camera;
	Camera guiCamera;
	ActionManager actionManager;
	final Starter game;

	public ScreenBoard(final Starter gam) {

		this.game = gam;

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

		actionManager = new ActionManager(game.inputInterpreter, camera, guiCamera, game.initial,
				game.gui, game.menuHome, game.menuTests, game.menuDownloads,
				game.assetsManager, game.home, game.tests, game.downloads);

	}

	@Override
	public void render(float delta) {

		actionManager.updateLogics(delta);

		camera.updateTimers(delta);
		camera.update();
		guiCamera.updateTimers(delta);
		guiCamera.update();

		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		game.initial.render(batch, delta);
		game.menuHome.render(batch, delta);
		game.menuTests.render(batch, delta);
		game.menuDownloads.render(batch, delta);
		batch.end();

		batch.setProjectionMatrix(guiCamera.combined);
		batch.begin();
		game.gui.render(batch, delta);
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
}