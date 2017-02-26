package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Payload;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Source;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Target;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;


/**
 * Created by henrymoule on 31/12/2016.
 */
public class PlayScreen implements Screen {

    private Stage stage;
    private Game game;
    private Skin skin;
    private Skin crispy;
    private BitmapFont font;
    private float timeCount;
    private int timer;
    private Label timerLabel;
    private Label feedback;
    private Viewport viewport;
    private int VIRTUAL_WIDTH;
    private int VIRTUAL_HEIGHT;
    private Button close;
    private Button next;
    private Label nextLabel;
    private Label attempts;
    private int attempsCount;
    private TextButton.TextButtonStyle buttonStyle;
    private TextButton.TextButtonStyle answerStyle;
    private TextButton.TextButtonStyle nextStyle;
    private TextButton.TextButtonStyle equationStyle;
    private Boolean isCorrect;

    public PlayScreen(Game game) {
        this.game = game;

        VIRTUAL_WIDTH = 1280;
        VIRTUAL_HEIGHT = 720;

        isCorrect = false;
        //camera = new OrthographicCamera(VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
        viewport = new StretchViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT); //notice cam param here!
        stage = new Stage(viewport);


        skin = new Skin(Gdx.files.internal("uiskin.json"));
        crispy = new Skin(Gdx.files.internal("clean-crispy/skin/clean-crispy-ui.json"));
        font = new BitmapFont(Gdx.files.internal("font.fnt"), false);

        buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.up = crispy.getDrawable("button-close");
        buttonStyle.over = crispy.getDrawable("button-close-over");
        buttonStyle.down = crispy.getDrawable("button-close-pressed");

        answerStyle = new TextButton.TextButtonStyle();
        answerStyle.up = crispy.getDrawable("button");
        answerStyle.over = crispy.getDrawable("button-over");
        answerStyle.down = crispy.getDrawable("button-pressed");
        answerStyle.font = font;

        equationStyle = new TextButton.TextButtonStyle();
        equationStyle.up = crispy.getDrawable("button");
        equationStyle.over = crispy.getDrawable("button-over");
        equationStyle.down = crispy.getDrawable("button-pressed");
        equationStyle.font = font;

        nextStyle = new TextButton.TextButtonStyle();
        nextStyle.up = crispy.getDrawable("button-arcade");
        nextStyle.down = crispy.getDrawable("button-arcade-pressed");
        next = new Button(nextStyle);
        next.setPosition(650, 500);
        next.setVisible(false);
        next.setPosition(750,550);



        close = new Button(buttonStyle);

        close.setSize(25,25);

        stage.addActor(close);

        close.setPosition(50, 650);


        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("digital-7.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 128;

        BitmapFont font12 = generator.generateFont(parameter); // font size 64 pixels
        generator.dispose(); // don't forget to dispose to avoid memory leaks!


        Skin test = new Skin();

        test.addRegions(new TextureAtlas(Gdx.files.internal("uiskin.atlas")));
        test.add("default-font", font12);

        test.load(Gdx.files.internal("digital.json"));

        timer = 20;

        timerLabel = new Label("20", test);
        timerLabel.setColor(Color.BLUE);
        timerLabel.setPosition(1000, 600);

        attempsCount = 0;
        attempts = new Label("attempts: " + attempsCount, skin);
        attempts.setFontScale((float) 0.5);
        attempts.setPosition(200, 600);
        attempts.setColor(Color.BLACK);

        nextLabel = new Label("press for next question", skin);
        nextLabel.setPosition(400,550);
        nextLabel.setFontScale((float)0.5);
        nextLabel.setVisible(false);
        nextLabel.setColor(Color.BLACK);

        stage.addActor(attempts);
        stage.addActor(nextLabel);
        stage.addActor(next);

        final Equation equation1 = new Equation();

        //final Label equation = new Label(equation1.equationString(), skin);
        final TextButton equation = new TextButton(equation1.equationString(), equationStyle);


        //Color myOrange = new Color(0xff6600ff);
       // equation.setColor(new Color(0xffffffff));

        Table table = new Table();

        feedback = new Label("", skin);

        table.add(feedback).padBottom(10);
        table.row();
        table.add(equation);
        table.setPosition(600, 500);

        Table answersTable = new Table(skin);
        //answersTable.setPosition(equation.getWidth()*2  , Gdx.graphics.getHeight()/2 - equation.getHeight());
        answersTable.setPosition(650, 300);

        final DragAndDrop dragAndDrop = new DragAndDrop();

        dragAndDrop.addTarget(new Target(equation) {
            @Override
            public boolean drag(Source source, Payload payload, float x, float y, int pointer) {

                getActor().setColor(Color.BLUE);

                return true;
            }


            public void reset (DragAndDrop.Source source, DragAndDrop.Payload payload) {
                getActor().setColor(Color.WHITE);
            }

            @Override
            public void drop(Source source, Payload payload, float x, float y, int pointer) {

            }
        });

        for (String answer : equation1.generateAnswers()) {
            final TextButton label = new TextButton(answer, answerStyle);

            //label.setStyle();

            label.setSize(100, 100);
            label.setColor(Color.BLACK);
            answersTable.add(label).padRight(50);


            dragAndDrop.addSource(new DragAndDrop.Source(label) {
                final Payload payload = new Payload();

                @Override
                public Payload dragStart(InputEvent event, float x, float y, int pointer) {
                    label.setVisible(false);
                    payload.setObject(label);
                    //System.out.println(label.getText());
                    //if (label.getText().toString().equals(equation1.solveEquation())) {

                    TextButton draggedLabel = new TextButton(label.getText().toString(), answerStyle);
                    draggedLabel.setColor(Color.BLACK);

                    payload.setDragActor(draggedLabel);

                    return payload;
                }

                @Override
                public void dragStop(InputEvent event, float x, float y, int pointer, Payload payload, Target target) {
                    System.out.println(target);
                    System.out.println(label.getText());

                    System.out.println();
                    if (target != null) {
                        attempsCount +=1;
                        attempts.setText("attempts: " + attempsCount);
                        if (label.getText().toString().equals(equation1.solveEquation())) {
                            System.out.println("correct");
                            feedback.setColor(Color.GREEN);
                            feedback.setText("correct!");
                            dragAndDrop.clear();
                            isCorrect = true;
                            next.setVisible(true);
                            nextLabel.setVisible(true);
                        } else {
                            System.out.println("false");
                            feedback.setColor(Color.RED);
                            feedback.setText("wrong, try again");
                        }
                    }
                    label.setVisible(true);
                }
            });
        }

        stage.addActor(timerLabel);

        stage.addActor(table);

        stage.addActor(answersTable);

    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        close.addListener(new InputListener(){

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("closed");
                game.setScreen(new MainMenu(game));
                return true;
            }
        });

        next.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("clicked");

                game.setScreen(new PlayScreen(game));

                return true;
            }
        });
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1,1,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Gdx.graphics.getDeltaTime());
        update(Gdx.graphics.getDeltaTime());
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

    public void update(float dt) {
        if (!isCorrect) {
            timeCount += dt;
            if (timeCount >= 1 && timer > 0) {
                timer--;
                timerLabel.setText(String.format("%3d", timer));
                timeCount = 0;

            }
        }
    }

    @Override
    public void dispose() {
        stage.dispose();

    }
}
