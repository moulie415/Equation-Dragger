
package com.mygdx.game;



/*
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
		Quadratic test = new Quadratic();
		if (Gdx.files.local("player.dat").exists()) {
			try {
				player = Player.readPlayer();
			}
			catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println("Player Exits, Reading File");
		}
		else {
			player = new Player();
			try {
				Player.savePlayer(player);
			}
			catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println("Player Does Not Exist, Creating Player and Saving Player");
		}
		game.setScreen(new MainMenu(game, player));
	}

	@Override
	public void render () {
		super.render();


	}

	@Override
	public void dispose () {
		try {
			player.savePlayer(player);
		}
		catch (IOException e) {
			e.printStackTrace();
		}

	}
}