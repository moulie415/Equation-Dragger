package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Payload;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Source;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Target;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.io.IOException;


/**
 * Created by henrymoule on 31/12/2016.
 */
public class PlayScreen implements Screen {

    private Stage stage;
    private Game game;
    private Player player;
    private Skin skin;
    private Skin crispy;
    private BitmapFont font, digital;
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
    private Label instruction;
    private Label points;
    private Label timeBonus;
    private Label streak;
    private int attemptsCount;
    private Button.ButtonStyle buttonStyle;
    private TextButton.TextButtonStyle answerStyle;
    private TextButton.TextButtonStyle nextStyle;
    private TextButton.TextButtonStyle equationStyle;
    private Label.LabelStyle style;
    private Boolean isCorrect;
    private PlayDialog dialog;
    private Sound click;
    private Sound correct;
    private Sound wrong;
    private Music countdown;
    private boolean isPlaying = false;

    public PlayScreen(Game game, Player player) {
        this.game = game;
        this.player = player;

        click = Gdx.audio.newSound(Gdx.files.internal("sounds/button-click.wav"));
        correct = Gdx.audio.newSound(Gdx.files.internal("sounds/correct.wav"));
        wrong = Gdx.audio.newSound(Gdx.files.internal("sounds/wrong.wav"));
        countdown = Gdx.audio.newMusic(Gdx.files.internal("sounds/countdown.wav"));
        VIRTUAL_WIDTH = 1280;
        VIRTUAL_HEIGHT = 720;

        isCorrect = false;
        //camera = new OrthographicCamera(VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
        viewport = new StretchViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT); //notice cam param here!
        stage = new Stage(viewport);


        skin = new Skin(Gdx.files.internal("uiskin.json"));
        crispy = new Skin(Gdx.files.internal("clean-crispy/skin/clean-crispy-ui.json"));
        font = new BitmapFont(Gdx.files.internal("font.fnt"), false);
        digital = new BitmapFont(Gdx.files.internal("digital.fnt"), false);

        buttonStyle = new Button.ButtonStyle();
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
        next.setVisible(false);
        next.setPosition(750,500);



        close = new Button(buttonStyle);

        close.setSize(50,50);

        stage.addActor(close);

        close.setPosition(50, 650);


        style = new Label.LabelStyle();
        style.font = digital;

        timer = 20;

        timerLabel = new Label("20", style);
        timerLabel.setColor(Color.GREEN);
        timerLabel.setPosition(1100, 600);

        attemptsCount = 0;
        attempts = new Label("attempts: " + attemptsCount, skin);
        attempts.setFontScale((float) 0.5);
        attempts.setPosition(200, 630);

        streak = new Label("streak: " + player.getCurrentStreak(), skin);
        streak.setFontScale((float) 0.5);
        streak.setPosition(600, 630);


        points = new Label("Points: " + Integer.toString(player.getPoints(1)), skin);
        points.setFontScale((float) 0.5);
        points.setPosition(400, 630);

        timeBonus = new Label("placeholder text", skin);
        timeBonus.setFontScale((float) 0.5);
        timeBonus.setPosition(750, 630);
        timeBonus.setVisible(false);

        instruction = new Label("solve for x", skin);
        instruction.setPosition(450, 550);


        nextLabel = new Label("press for next question", skin);
        nextLabel.setPosition(400,500);
        nextLabel.setFontScale((float)0.5);
        nextLabel.setVisible(false);


        dialog = new PlayDialog("", skin);
        stage.addActor(attempts);
        stage.addActor(points);
        stage.addActor(timeBonus);
        stage.addActor(nextLabel);
        stage.addActor(next);
        stage.addActor(instruction);
        stage.addActor(streak);

        final Equation equation1 = new Equation();

        final TextButton equation = new TextButton(equation1.equationString(), equationStyle);


        Table table = new Table();

        feedback = new Label("", skin);

        table.add(feedback).padBottom(10);
        table.row();
        table.add(equation);
        table.setPosition(600, 450);

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
            label.setColor(Color.BLUE);
            answersTable.add(label).padRight(50);


            dragAndDrop.addSource(new DragAndDrop.Source(label) {
                final Payload payload = new Payload();

                @Override
                public Payload dragStart(InputEvent event, float x, float y, int pointer) {
                    label.setVisible(false);
                    payload.setObject(label);

                    TextButton draggedLabel = new TextButton(label.getText().toString(), answerStyle);
                    draggedLabel.setColor(Color.BLUE);

                    payload.setDragActor(draggedLabel);

                    return payload;
                }

                @Override
                public void dragStop(InputEvent event, float x, float y, int pointer, Payload payload, Target target) {

                    if (target != null) {
                        attemptsCount +=1;
                        attempts.setText("attempts: " + attemptsCount);
                        if (label.getText().toString().equals(equation1.solveEquation())) {
                            correct.play();
                            countdown.stop();
                            feedback.setColor(Color.GREEN);
                            feedback.setText("correct!");
                            dragAndDrop.clear();
                            isCorrect = true;
                            next.setVisible(true);
                            nextLabel.setVisible(true);
                            incCorrectCount();

                        } else {
                            wrong.play();
                            feedback.setColor(Color.RED);
                            feedback.setText("wrong, try again");
                            incWrongCount();
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
                try {
                    player.savePlayer(player);
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
                click.play();
                countdown.stop();
                player.resetCurrentStreak();
                game.setScreen(new MainMenu(game, player));
                return true;
            }
        });

        next.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                click.play();

                game.setScreen(new PlayScreen(game, player));

                return true;
            }
        });
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor((float)0.4,(float)0.6,1,1);
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
            else if (timer == 0) {
                incWrongCount();
                wrong.play();
                game.setScreen(new PlayScreen(game, player));
            }
            if (timer == 10 && !isPlaying) {
                isPlaying = true;
                countdown.play();
            }
        }
    }

    @Override
    public void dispose() {
        try {
            player.savePlayer(player);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        stage.dispose();
        click.dispose();
        wrong.dispose();
        countdown.dispose();
        correct.dispose();
        player.resetCurrentStreak();

    }


    public void incCorrectCount() {
        player.incAttempts();
        player.incCurrentStreak();
        player.incCorrectCount();
        player.incPoints(1, timer);
        points.setText("Points: " + Integer.toString(player.getPoints(1)));
        streak.setText("streak: " + player.getCurrentStreak());
        timeBonus.setText("Time Bonus!!! " + "+ " + timer);
        timeBonus.setVisible(true);
        if (player.getPoints(1) >= 200 && !player.getSection(1)) {
            player.completeSection(1);
            dialog.show(stage);

        }
    }

    public void incWrongCount() {
        player.incAttempts();
        player.resetCurrentStreak();
        player.incWrongCount();
        player.decPoints(1);
        points.setText("Points: " + Integer.toString(player.getPoints(1)));
        streak.setText("streak: " + player.getCurrentStreak());
    }

}
