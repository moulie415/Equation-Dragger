
package com.mygdx.game;



/*
TODO add music/sounds
TODO elements to implement, points,badges, stats and leaderboards (if possible)
 */


import com.badlogic.gdx.Game;

public class MyGdxGame extends Game {

	private Game game;
	private Player player;


	@Override
	public void create ()  {
		game = this;
		player = new Player();

		//game.setScreen(new MainMenu(game, player));
		player.setInstructional(false);
		if (player.getSplash()) {
			game.setScreen(new MainMenu(game,player));
		}
		else {
			game.setScreen(new SplashScreen(game, player));
		}
	}

	@Override
	public void render () {
		super.render();


	}

	@Override
	public void dispose () {

	}
}