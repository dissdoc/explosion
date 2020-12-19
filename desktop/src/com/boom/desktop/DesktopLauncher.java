package com.boom.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.boom.BoomGame;
import com.boom.Config;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = (int) Config.WORLD_WIDTH;
		config.height = (int) Config.WORLD_HEIGHT;
		new LwjglApplication(new BoomGame(), config);
	}
}
