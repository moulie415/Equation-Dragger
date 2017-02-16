
package com.mygdx.game;



/*
TODO game ideas: work out algebra equation: drag and drop with timer (time bomb that ticks down)
TODO add music
TODO elements to implement, points,badges, stats and leaderboards (if possible)
 */


import com.badlogic.gdx.Game;

public class MyGdxGame extends Game {

	private Game game;


	@Override
	public void create () {
		game = this;
		game.setScreen(new MainMenu(game));
	}

	@Override
	public void render () {
		super.render();


	}

	@Override
	public void dispose () {
	}
}