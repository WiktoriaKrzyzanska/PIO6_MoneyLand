package com.mygdx.game;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

public class DesktopLauncher {

	static final int width = 1900;
	static final int height = 1000;
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setTitle("MoneyLand");
		config.setWindowedMode(width, height);
		new Lwjgl3Application(new MoneyLandGame(), config);
	}
}
