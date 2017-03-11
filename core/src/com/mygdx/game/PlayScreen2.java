package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
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
public class PlayScreen2 implements Screen {

    private Stage stage;
    private Game game;
    private Player player;
    private Skin skin;
    private Skin crispy;
    private BitmapFont font;
    private float timeCount;
    private int timer;
    private Label timerLabel;
    private Label feedbackWrong;
    private Label feedbackCorrect;
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
    private Label equation1;
    private Label equation2;
    private int attemptsCount;
    private Button.ButtonStyle buttonStyle;
    private TextButton.TextButtonStyle answerStyle;
    private TextButton.TextButtonStyle nextStyle;
    private TextButton.TextButtonStyle equationStyle;
    private Boolean isCorrect;
    private boolean isXCorrect = false;
    private boolean isYCorrect = false;
    private PlayDialog dialog;
    private boolean isXSet = false;
    private boolean isYSet = false;
    private Sound click;
    private Sound correct;
    private Sound wrong;
    private Sound countdown;
    private Sound smokeWeed;
    private boolean isPlaying = false;


    public PlayScreen2(Game game, Player player) {
        this.game = game;
        this.player = player;
        click = Gdx.audio.newSound(Gdx.files.internal("sounds/HITMARKER.mp3"));
        correct = Gdx.audio.newSound(Gdx.files.internal("sounds/intervention.mp3"));
        wrong = Gdx.audio.newSound(Gdx.files.internal("sounds/2SED4AIRHORN_short.mp3"));
        countdown = Gdx.audio.newSound(Gdx.files.internal("sounds/tactical_nuke.mp3"));
        smokeWeed = Gdx.audio.newSound(Gdx.files.internal("sounds/smoke_weed.mp3"));

        VIRTUAL_WIDTH = 1280;
        VIRTUAL_HEIGHT = 720;

        isCorrect = false;
        //camera = new OrthographicCamera(VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
        viewport = new StretchViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT); //notice cam param here!
        stage = new Stage(viewport);


        skin = new Skin(Gdx.files.internal("uiskin.json"));
        crispy = new Skin(Gdx.files.internal("clean-crispy/skin/clean-crispy-ui.json"));
        font = new BitmapFont(Gdx.files.internal("font.fnt"), false);

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



        close = new Button(buttonStyle);

        close.setSize(50,50);

        stage.addActor(close);

        close.setPosition(50, 650);


        timer = 40;

        timerLabel = new Label("40", skin);
        timerLabel.setColor(Color.BLUE);
        timerLabel.setPosition(1000, 600);

        attemptsCount = 0;
        attempts = new Label("attempts: " + attemptsCount, skin);
        attempts.setFontScale((float) 0.5);
        attempts.setPosition(200, 630);
        attempts.setColor(Color.BLACK);

        points = new Label("Points: " + Integer.toString(player.getPoints(2)), skin);
        points.setFontScale((float) 0.5);
        points.setPosition(400, 630);
        points.setColor(Color.BLACK);

        timeBonus = new Label("placeholder text", skin);
        timeBonus.setFontScale((float) 0.5);
        timeBonus.setPosition(600, 630);
        timeBonus.setColor(Color.BLACK);
        timeBonus.setVisible(false);

        instruction = new Label("solve for x and y", skin);
        instruction.setPosition(400, 550);
        instruction.setColor(Color.BLACK);


        nextLabel = new Label("press for next question", skin);
        nextLabel.setFontScale((float)0.5);
        nextLabel.setVisible(false);
        nextLabel.setColor(Color.BLACK);


        dialog = new PlayDialog("", skin);
        stage.addActor(attempts);
        stage.addActor(points);
        stage.addActor(timeBonus);
        stage.addActor(next);
        stage.addActor(instruction);

        final Simultaneous simul = new Simultaneous();
        System.out.println(simul.getX());
        System.out.println(simul.getY());


        equation1 = new Label(simul.firstToString(), skin);
        equation2 = new Label(simul.secondToString(), skin);
        equation1.setColor(Color.BLACK);
        equation2.setColor(Color.BLACK);

        final TextButton x = new TextButton("x = ", equationStyle);
        final TextButton y = new TextButton("y = ", equationStyle);



        Table table = new Table();


        feedbackCorrect = new Label("correct", skin);
        feedbackCorrect.setVisible(false);
        feedbackCorrect.setColor(Color.GREEN);
        feedbackWrong = new Label("wrong, try again", skin);
        feedbackWrong.setVisible(false);
        feedbackWrong.setColor(Color.RED);
        feedbackCorrect.setPosition(450, 480);
        feedbackWrong.setPosition(450, 480);
        nextLabel.setPosition(675, 480);
        nextLabel.setWidth(200);
        next.setPosition(1000, 470);



        table.add(equation1).padBottom(20);
        table.add(equation2).padBottom(20).padLeft(100);
        table.row();
        table.add(x);
        table.add(y).padLeft(40);
        table.setPosition(600, 400);

        Table answersTable = new Table(skin);
        answersTable.setPosition(600, 150);

        final DragAndDrop dragAndDrop = new DragAndDrop();

        dragAndDrop.addTarget(new Target(x) {
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


        dragAndDrop.addTarget(new Target(y) {
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


        int count = 0;
        for (String answer : simul.generateAnswers()) {
            final TextButton label = new TextButton(answer, answerStyle);

            //label.setStyle();

            label.setSize(100, 100);
            label.setColor(Color.BLACK);
            answersTable.add(label).pad(20);
            if (count == 5) {
                answersTable.row();

            }

            count++;


            dragAndDrop.addSource(new DragAndDrop.Source(label) {
                final Payload payload = new Payload();

                @Override
                public Payload dragStart(InputEvent event, float x, float y, int pointer) {
                    label.setVisible(false);
                    payload.setObject(label);
                    //System.out.println(label.getText());
                    //if (label.getText().toString().equals(x.solveEquation())) {

                    TextButton draggedLabel = new TextButton(label.getText().toString(), answerStyle);
                    draggedLabel.setColor(Color.BLACK);

                    payload.setDragActor(draggedLabel);

                    return payload;
                }

                @Override
                public void dragStop(InputEvent event, float xaxis, float yaxis, int pointer, Payload payload, Target target) {

                    if (target != null) {
                        if (target.getActor().equals(x)) {
                            x.setText("x = " + label.getText());
                            setIsXSet();
                            if (Float.valueOf(label.getText().toString()) == (simul.getX())) {
                                setXCorrect(true);
                                if (getIsYSet()) {
                                    attemptsCount +=1;
                                    incAttempts();
                                    attempts.setText("attempts: " + attemptsCount);
                                    if (isYCorrect()) {
                                        correct.play();
                                        System.out.println("correct");
                                        feedbackWrong.setVisible(false);
                                        feedbackCorrect.setVisible(true);
                                        dragAndDrop.clear();
                                        isCorrect = true;
                                        next.setVisible(true);
                                        nextLabel.setVisible(true);
                                        incCorrectCount();

                                    }
                                    else {
                                        wrong.play();
                                        System.out.println("false");
                                        feedbackWrong.setVisible(true);
                                        incWrongCount();
                                        x.setText("x = ");
                                        y.setText("y = ");
                                        resetXandY();


                                    }
                                }
                            }
                            else {
                                setXCorrect(false);
                                if (getIsYSet()) {
                                    wrong.play();
                                    System.out.println("false");
                                    feedbackWrong.setVisible(true);
                                    incWrongCount();
                                    x.setText("x = ");
                                    y.setText("y = ");
                                    resetXandY();

                                }
                            }

                            System.out.println("x");

                        } else {
                            System.out.println("y");
                            y.setText("y = " + label.getText());
                            setIsYSet();
                            if (Float.valueOf(label.getText().toString()) == (simul.getY())) {
                                setYCorrect(true);
                                if (getIsXSet()) {
                                    attemptsCount +=1;
                                    incAttempts();
                                    attempts.setText("attempts: " + attemptsCount);
                                    if (isXCorrect()) {
                                        correct.play();
                                        System.out.println("correct");
                                        feedbackWrong.setVisible(false);
                                        feedbackCorrect.setVisible(true);
                                        dragAndDrop.clear();
                                        isCorrect = true;
                                        next.setVisible(true);
                                        nextLabel.setVisible(true);
                                        incCorrectCount();
                                        countdown.dispose();

                                    }
                                    else {
                                        wrong.play();
                                        System.out.println("false");
                                        feedbackWrong.setVisible(true);
                                        incWrongCount();
                                        x.setText("x = ");
                                        y.setText("y = ");
                                        resetXandY();


                                    }
                                }
                            }
                            else {
                                setYCorrect(false);
                                if (getIsXSet()) {
                                    wrong.play();
                                    System.out.println("false");
                                    feedbackWrong.setVisible(true);
                                    incWrongCount();
                                    x.setText("x = ");
                                    y.setText("y = ");
                                    resetXandY();

                                }
                            }
                        }

                    }
                    label.setVisible(true);
                }
            });
        }

        stage.addActor(timerLabel);

        stage.addActor(table);

        stage.addActor(answersTable);
        stage.addActor(feedbackCorrect);
        stage.addActor(feedbackWrong);
        stage.addActor(nextLabel);
        stage.addActor(next);

    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        close.addListener(new InputListener(){

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                click.play();
                countdown.dispose();
                System.out.println("closed");
                game.setScreen(new MainMenu(game, player));
                return true;
            }
        });

        next.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                smokeWeed.play();
                System.out.println("clicked");

                game.setScreen(new PlayScreen2(game, player));

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
                timerLabel.setText(Integer.toString(timer));
                timeCount = 0;

            }
            else if (timer == 0) {
                incWrongCount();
                wrong.play();
                game.setScreen(new PlayScreen2(game, player));
            }
            if (timer == 6 && !isPlaying) {
                isPlaying = true;
                countdown.play();
            }
        }
    }

    @Override
    public void dispose() {
        stage.dispose();
        click.dispose();
        correct.dispose();
        wrong.dispose();
        countdown.dispose();
        smokeWeed.dispose();
    }

    public void incAttempts() {
        player.incAttempts();
    }

    public void incCorrectCount() {
        player.incCorrectCount();
        player.incPoints(2, timer);
        points.setText("Points: " + Integer.toString(player.getPoints(2)));
        timeBonus.setText("Time Bonus!!! " + "+ " + timer);
        timeBonus.setVisible(true);
        if (player.getPoints(2) >= 300 && !player.getSection(2)) {
            player.completeSection(2);
            dialog.show(stage);

        }
    }

    public void incWrongCount() {
        player.incWrongCount();
        player.decPoints(2);
        points.setText("Points: " + Integer.toString(player.getPoints(2)));
    }

    public void setIsXSet() {
        isXSet = true;
    }
    public void setIsYSet() {
        isYSet = true;
    }

    public boolean getIsXSet() {
        return isXSet;
    }

    public boolean getIsYSet() {
        return isYSet;
    }

    public boolean isXCorrect() {
        return isXCorrect;
    }

    public void setXCorrect(boolean XCorrect) {
        isXCorrect = XCorrect;
    }

    public boolean isYCorrect() {
        return isYCorrect;
    }

    public void setYCorrect(boolean YCorrect) {
        isYCorrect = YCorrect;
    }

    public void resetXandY(){
        isXSet = false;
        isYSet = false;
        isXCorrect = false;
        isYCorrect = false;

    }
}
