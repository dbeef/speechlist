package com.dbeef.speechlist.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.dbeef.speechlist.Starter;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
	config.width = 360;
	config.height = 600;
	config.title = "Speechlist";
		new LwjglApplication(new Starter(), config);
	}
}
