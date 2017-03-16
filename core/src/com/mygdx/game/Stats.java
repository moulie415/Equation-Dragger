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
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;


/**
 * Created by henrymoule on 01/03/2017.
 */
public class Stats implements Screen {
    private Player player;
    private Game game;
    private Label stats;
    private Label attempts;
    private Label correct;
    private Label wrong;
    private Label ratio;
    private Label totalPoints;
    private Label bestStreak;
    private Skin crispy;
    private Button close;
    private Label.LabelStyle style, style2;
    private Button.ButtonStyle buttonStyle;
    private TextButton leaderboards;
    private TextButton.TextButtonStyle leaderBoardStyle;
    private TextButton achievements;
    private BitmapFont font;
    private BitmapFont font2;
    private Stage stage;
    private Viewport viewport;
    private int VIRTUAL_WIDTH;
    private int VIRTUAL_HEIGHT;
    private Sound click;

    public Stats(Game game, Player player) {
        this.game = game;
        this.player = player;

        VIRTUAL_WIDTH = 1280;
        VIRTUAL_HEIGHT = 720;
        //camera = new OrthographicCamera(VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
        viewport = new StretchViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT); //notice cam param here! (camera taken out)
        stage = new Stage(viewport);

        crispy = new Skin(Gdx.files.internal("clean-crispy/skin/clean-crispy-ui.json"));
        click = Gdx.audio.newSound(Gdx.files.internal("sounds/button-click.wav"));
        font = new BitmapFont(Gdx.files.internal("font.fnt"), false);
        font2 = new BitmapFont(Gdx.files.internal("small.fnt"), false);

        style = new Label.LabelStyle(font2, Color.BLACK);
        style2 = new Label.LabelStyle(font, Color.BLACK);

        leaderBoardStyle = new TextButton.TextButtonStyle();
        leaderBoardStyle.up = crispy.getDrawable("button");
        leaderBoardStyle.over = crispy.getDrawable("button-over");
        leaderBoardStyle.down = crispy.getDrawable("button-pressed");
        leaderBoardStyle.font = font;

        leaderboards = new TextButton("Leaderboards", leaderBoardStyle);
        leaderboards.setPosition(100, 500);
        stage.addActor(leaderboards);
        stats = new Label("YOUR STATS:", style2);
        stats.setPosition(400, 625);
        stage.addActor(stats);


        achievements = new TextButton("Achievements", leaderBoardStyle);
        achievements.setPosition(700, 500);
        stage.addActor(achievements);

        attempts = new Label("Attempts: " + player.getAttempts(), style);
        attempts.setPosition(100, 400);
        stage.addActor(attempts);


        correct = new Label("Correct answers: " + player.getCorrectCount(), style);
        correct.setPosition( 700,  400);
        stage.addActor(correct);


        wrong = new Label("Wrong answers: " + player.getWrongCount(), style);
        wrong.setPosition(100, 300);
        stage.addActor(wrong);

        ratio = new Label("Ratio: " + player.getRatio(), style);
        ratio.setPosition(700, 300);
        stage.addActor(ratio);

        totalPoints = new Label("Total points: " + player.getTotalPoints(), style);
        totalPoints.setPosition(100, 200);
        stage.addActor(totalPoints);

        bestStreak = new Label("Best streak: " + player.getBestStreak(), style);
        bestStreak.setPosition(700, 200);
        stage.addActor(bestStreak);



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
                game.setScreen(new MainMenu(game, player));
                return true;
            }
        });
        leaderboards.addListener(new InputListener(){

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                click.play();
                game.setScreen(new Leaderboards(game, player));

                return true;
            }
        });
         achievements.addListener(new InputListener(){

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                click.play();
                if (MyGdxGame.googleServices.isSignedIn()) {
                    MyGdxGame.googleServices.showAchievements();
                }
                else {
                    MyGdxGame.googleServices.signIn();
                    MyGdxGame.googleServices.showAchievements();

                }

                return true;
            }
        });
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(1,1,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, false);
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
