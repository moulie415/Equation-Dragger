package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.StretchViewport;


/**
 * Created by henrymoule on 15/03/2017.
 */
public class Leaderboards implements Screen {
    private Game game;
    private Player player;
    private TextButton.TextButtonStyle style;
    private TextButton points;
    private TextButton ratio;
    private TextButton streak;
    private Button close;
    private Button.ButtonStyle buttonStyle;
    private Sound click;
    private int VIRTUAL_WIDTH, VIRTUAL_HEIGHT;
    private Skin crispy;
    private StretchViewport viewport;
    private Stage stage;
    private BitmapFont font;



    public Leaderboards(Game game, Player player) {
        this.game = game;
        this.player = player;

        click = Gdx.audio.newSound(Gdx.files.internal("sounds/button-click.wav"));

        VIRTUAL_WIDTH = 1280;
        VIRTUAL_HEIGHT = 720;

        //camera = new OrthographicCamera(VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
        viewport = new StretchViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT); //notice cam param here!
        stage = new Stage(viewport);

        crispy = new Skin(Gdx.files.internal("clean-crispy/skin/clean-crispy-ui.json"));
        font = new BitmapFont(Gdx.files.internal("font.fnt"), false);
        font.setColor(Color.BLACK);
        style = new TextButton.TextButtonStyle();
        style.up = crispy.getDrawable("button");
        style.over = crispy.getDrawable("button-over");
        style.down = crispy.getDrawable("button-pressed");
        style.font = font;

        points = new TextButton("Total points", style);
        points.setPosition(200, 500);
        stage.addActor(points);
        ratio = new TextButton("Best ratio", style);
        ratio.setPosition(200, 400);
        stage.addActor(ratio);
        streak = new TextButton("Best streak", style);
        streak.setPosition(200, 300);
        stage.addActor(streak);

        buttonStyle = new Button.ButtonStyle();
        buttonStyle.up = crispy.getDrawable("button-close");
        buttonStyle.over = crispy.getDrawable("button-close-over");
        buttonStyle.down = crispy.getDrawable("button-close-pressed");
        close = new Button(buttonStyle);

        close.setSize(50,50);

        stage.addActor(close);

        close.setPosition(50, 650);

    }
    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);


        close.addListener(new InputListener(){

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                click.play();
                game.setScreen(new Stats(game, player));
                return true;
            }

        });
        points.addListener(new InputListener(){

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                click.play();
                if (MyGdxGame.googleServices.isSignedIn()) {
                    System.out.println("Already signed in");
                    MyGdxGame.googleServices.submitScore(player.getTotalPoints(), "points");
                    MyGdxGame.googleServices.showScores("points");

                }
                else {
                    MyGdxGame.googleServices.signIn();
                    MyGdxGame.googleServices.submitScore(player.getTotalPoints(), "points");
                }
                return true;
            }

        });
        ratio.addListener(new InputListener(){

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                click.play();
                if (MyGdxGame.googleServices.isSignedIn()) {
                    System.out.println("Already signed in");
                    MyGdxGame.googleServices.submitScore((long)player.getRatio(), "ratio");
                    MyGdxGame.googleServices.showScores("ratio");

                }
                else {
                    MyGdxGame.googleServices.signIn();
                    MyGdxGame.googleServices.submitScore((long)player.getRatio(), "ratio");
                }
                return true;
            }

        });
        streak.addListener(new InputListener(){

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                click.play();
                if (MyGdxGame.googleServices.isSignedIn()) {
                    System.out.println("Already signed in");
                    MyGdxGame.googleServices.submitScore(player.getBestStreak(), "streak");
                    MyGdxGame.googleServices.showScores("streak");

                }
                else {
                    MyGdxGame.googleServices.signIn();
                    MyGdxGame.googleServices.submitScore(player.getBestStreak(), "streak");
                }
                return true;
            }

        });

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1,1,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();

    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width,height,false);

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
        click.dispose();

    }
}
