
package com.mygdx.game;



/*
TODO game ideas: work out algebra equation: drag and drop with timer (time bomb that ticks down)
TODO add music/sounds
TODO elements to implement, points,badges, stats and leaderboards (if possible)
TODO implement simultaneous and quadratic equation sections
 */


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

import java.io.IOException;

public class MyGdxGame extends Game {

	private Game game;
	private Player player;


	@Override
	public void create ()  {
		game = this;
		player = new Player();
		game.setScreen(new MainMenu(game, player));
	}

	@Override
	public void render () {
		super.render();


	}

	@Override
	public void dispose () {

	}
}