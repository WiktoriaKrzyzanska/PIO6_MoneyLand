package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class MoneyLandGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;

	Music music;
	
	@Override
	public void create () {
		batch = new SpriteBatch();

	}

	@Override
	public void render () {
		ScreenUtils.clear(1, 0, 0, 1);
		batch.begin();
		batch.end();

	}
	
	@Override
	public void dispose () {
		batch.dispose();

	}
}
