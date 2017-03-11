package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.scenes.scene2d.ui.*;

import static com.mygdx.game.TextRenderer.renderString;


/**
 * Created by henrymoule on 06/03/2017.
 */
public class About implements Screen {
    private Stage stage;
    private Game game;
    private Player player;
    private Skin crispy;
    private Viewport viewport;
    private int VIRTUAL_WIDTH;
    private int VIRTUAL_HEIGHT;
    private Label developer;
    private Label builtUsing;
    private Label email;
    private Label emailLink;
    private Label underline;
    private Label.LabelStyle style;
    private Label.LabelStyle style2;
    private BitmapFont font;
    private Button close;
    private Texture image;
    private Button.ButtonStyle buttonStyle;
    private Sound click;
    private Music spooky;

    public About(Game game, Player player) {
        this.game = game;
        this.player = player;
        click = Gdx.audio.newSound(Gdx.files.internal("sounds/HITMARKER.mp3"));
        spooky = Gdx.audio.newMusic(Gdx.files.internal("sounds/SPOOKY.mp3"));

        VIRTUAL_WIDTH = 1280;
        VIRTUAL_HEIGHT = 720;

        //camera = new OrthographicCamera(VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
        viewport = new StretchViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT); //notice cam param here!
        stage = new Stage(viewport);

        crispy = new Skin(Gdx.files.internal("clean-crispy/skin/clean-crispy-ui.json"));

        font = new BitmapFont(Gdx.files.internal("font.fnt"), false);
        style = new Label.LabelStyle(font, Color.BLACK);
        style2 = new Label.LabelStyle(font, Color.BLUE);


        buttonStyle = new Button.ButtonStyle();
        buttonStyle.up = crispy.getDrawable("button-close");
        buttonStyle.over = crispy.getDrawable("button-close-over");
        buttonStyle.down = crispy.getDrawable("button-close-pressed");

        developer = new Label("Developed by Henry Moule", style);
        developer.setPosition(200, 500);

        email = new Label("Email: ", style);
        email.setPosition(200, 400);
        emailLink = new Label("henry.moule@gmail.com", style2);
        emailLink.setPosition(400, 400);
        underline = new Label("_______________________", style2);
        underline.setPosition(400,400);

        builtUsing = new Label("Built using", style);
        builtUsing.setPosition(200, 300);




        close = new Button(buttonStyle);

        close.setSize(50,50);


        stage.addActor(underline);
        stage.addActor(close);
        stage.addActor(developer);
        stage.addActor(email);
        stage.addActor(emailLink);
        stage.addActor(builtUsing);

        close.setPosition(50, 650);

        image = new Texture(Gdx.files.internal("images/libgdx_logo.bmp"));

    }


    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        spooky.play();


        close.addListener(new InputListener(){

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("closed");
                click.play();
                spooky.dispose();
                game.setScreen(new MainMenu(game, player));
                return true;
            }

        });

        emailLink.addListener(new InputListener(){
            @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.net.openURI("mailto:henry.moule@gmail.com");
                return true;
            }
        });


    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1,1,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Gdx.graphics.getDeltaTime());
        stage.getBatch().begin();
        stage.getBatch().draw(image, 450, 230);
        stage.getBatch().end();
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
        spooky.dispose();

    }

}

