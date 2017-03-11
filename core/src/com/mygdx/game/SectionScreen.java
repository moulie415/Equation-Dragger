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
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.scenes.scene2d.ui.*;


/**
 * Created by henrymoule on 06/03/2017.
 */
public class SectionScreen implements Screen {
    private Stage stage;
    private Player player;
    private Game game;
    private Skin crispy;
    private BitmapFont font;
    private BitmapFont font2;
    private Viewport viewport;
    private int VIRTUAL_WIDTH;
    private int VIRTUAL_HEIGHT;
    private Button close;
    private TextButton section1;
    private TextButton section2;
    private TextButton section3;
    private TextButton.TextButtonStyle sectionStyle;
    private Button.ButtonStyle buttonStyle;
    private Label.LabelStyle labelStyle;
    private Skin skin;
    private Sound click;
    private Sound airporn;
    private Label pointsNeeded2;
    private Label pointsNeeded3;


    public SectionScreen(Game game, Player player) {
        this.game = game;
        this.player = player;
        click = Gdx.audio.newSound(Gdx.files.internal("sounds/HITMARKER.mp3"));
        airporn = Gdx.audio.newSound(Gdx.files.internal("sounds/AIRPORN.mp3"));

        VIRTUAL_WIDTH = 1280;
        VIRTUAL_HEIGHT = 720;

        //camera = new OrthographicCamera(VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
        viewport = new StretchViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT); //notice cam param here!
        stage = new Stage(viewport);

        crispy = new Skin(Gdx.files.internal("clean-crispy/skin/clean-crispy-ui.json"));
        font = new BitmapFont(Gdx.files.internal("font.fnt"), false);
        font.setColor(Color.BLACK);

        font2 = new BitmapFont(Gdx.files.internal("small.fnt"), false);
        font2.setColor(Color.BLACK);

        skin = new Skin(Gdx.files.internal("uiskin.json"));

        buttonStyle = new Button.ButtonStyle();
        buttonStyle.up = crispy.getDrawable("button-close");
        buttonStyle.over = crispy.getDrawable("button-close-over");
        buttonStyle.down = crispy.getDrawable("button-close-pressed");


        sectionStyle = new TextButton.TextButtonStyle();
        sectionStyle.up = crispy.getDrawable("button");
        sectionStyle.over = crispy.getDrawable("button-over");
        sectionStyle.down = crispy.getDrawable("button-pressed");
        sectionStyle.font = font;

        close = new Button(buttonStyle);

        close.setSize(50,50);

        stage.addActor(close);

        close.setPosition(50, 650);

        section1 = new TextButton("Section 1: Simple Equations", sectionStyle);
        section2 = new TextButton("Section 2: Simultaneous Equations", sectionStyle);
        section3 = new TextButton("Section 3: Quadratic Equations", sectionStyle);

        labelStyle = new Label.LabelStyle();
        labelStyle.font = font2;
        labelStyle.fontColor = Color.BLACK;
        pointsNeeded2 = new Label( Integer.toString(player.getPoints(1)) + "/200 points", labelStyle);
        pointsNeeded2.setPosition(section2.getWidth() + 50, 425);
        pointsNeeded2.setVisible(false);
        pointsNeeded3 = new Label( Integer.toString(player.getPoints(2)) + "/300 points", labelStyle);
        pointsNeeded3.setPosition( section3.getWidth() + 50, 325);
        pointsNeeded3.setVisible(false);

        section1.setPosition(50, 500);
        section2.setPosition(50, 400);
        if (!player.getSection(1)) {
            section2.setColor(Color.GRAY);
            pointsNeeded2.setVisible(true);
            //section2.setWidth(1200);
            //String points = Integer.toString(player.getPoints(1)) + "/200 points";
            //section2.setText("Section 2: Simultaneous Equations " + points);
        }
        section3.setPosition(50, 300);
        if (!player.getSection(2)) {
            section3.setColor(Color.GRAY);
            pointsNeeded3.setVisible(true);
           // section3.setWidth(1200);
            //String points = Integer.toString(player.getPoints(2)) + "/300 points";
            //section3.setText("Section 3: Quadratic Equations " + points);
        }

        stage.addActor(section1);
        stage.addActor(section2);
        stage.addActor(section3);
        stage.addActor(pointsNeeded2);
        stage.addActor(pointsNeeded3);


    }


    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);


        final SectionDialog dialog = new SectionDialog("", skin);
        close.addListener(new InputListener(){

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("closed");
                click.play();
                game.setScreen(new MainMenu(game, player));
                return true;
            }

        });
        section1.addListener(new InputListener(){

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                airporn.play();
                game.setScreen(new PlayScreen(game, player));
                return true;
            }

        });
        section2.addListener(new InputListener(){

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                airporn.play();
                if (player.getSection(1)) {
                    game.setScreen(new PlayScreen2(game, player));
                }
                else {
                    dialog.show(stage);

                }
                return true;
            }

        });
        section3.addListener(new InputListener(){

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                airporn.play();
                if (player.getSection(2)) {
                    game.setScreen(new PlayScreen3(game, player));
                }
                else {
                    dialog.show(stage);

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
        airporn.dispose();

    }

}
