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
public class PlayScreen3 implements Screen {

    private Stage stage;
    private Game game;
    private Player player;
    private Skin skin;
    private Skin crispy;
    private BitmapFont font, digital;
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
    private Label streak;
    private Table equation;
    private Label onlyOneRoot;
    private int attemptsCount;
    private Button.ButtonStyle buttonStyle;
    private TextButton.TextButtonStyle answerStyle;
    private TextButton.TextButtonStyle nextStyle;
    private TextButton.TextButtonStyle equationStyle;
    private Label.LabelStyle style;
    private Boolean isCorrect;
    private PlayDialog dialog;
    private boolean isAnswer1Set = false;
    private boolean isAnswer2Set = false;
    private String x1 = "";
    private String x2 = "";
    private Sound click;
    private Sound correct;
    private Sound wrong;
    private Music countdown;
    private boolean isPlaying = false;



    public PlayScreen3(Game game, Player player) {
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


        close = new Button(buttonStyle);

        close.setSize(50,50);

        stage.addActor(close);

        close.setPosition(50, 650);

        style = new Label.LabelStyle();
        style.font = digital;

        timer = 40;

        timerLabel = new Label("40", style);
        timerLabel.setColor(Color.GREEN);
        timerLabel.setPosition(1000, 600);

        attemptsCount = 0;
        attempts = new Label("attempts: " + attemptsCount, skin);
        attempts.setFontScale((float) 0.5);
        attempts.setPosition(200, 630);

        points = new Label("Points: " + Integer.toString(player.getPoints(3)), skin);
        points.setFontScale((float) 0.5);
        points.setPosition(400, 630);

        timeBonus = new Label("placeholder text", skin);
        timeBonus.setFontScale((float) 0.5);
        timeBonus.setPosition(600, 630);
        timeBonus.setVisible(false);

        streak = new Label("streak: " + player.getCurrentStreak(), skin);
        streak.setFontScale((float) 0.5);
        streak.setPosition(600, 630);

        instruction = new Label("Find all roots", skin);
        instruction.setPosition(450, 550);


        nextLabel = new Label("press for next question", skin);
        nextLabel.setFontScale((float)0.5);
        nextLabel.setVisible(false);

        onlyOneRoot = new Label("Only one root", skin);


        dialog = new PlayDialog("", skin);
        stage.addActor(attempts);
        stage.addActor(points);
        stage.addActor(timeBonus);
        stage.addActor(next);
        stage.addActor(instruction);
        stage.addActor(streak);

        final Quadratic quadratic = new Quadratic();


        equation = quadratic.getTable();

        final TextButton answer1 = new TextButton("x = ", equationStyle);
        final TextButton answer2 = new TextButton("x = ", equationStyle);


        Table table = new Table();

        feedbackCorrect = new Label("correct", skin);
        feedbackCorrect.setVisible(false);
        feedbackCorrect.setColor(Color.GREEN);
        feedbackWrong = new Label("wrong, try again", skin);
        feedbackWrong.setVisible(false);
        feedbackWrong.setColor(Color.RED);
        feedbackCorrect.setPosition(400, 490);
        feedbackWrong.setPosition(400, 490);
        nextLabel.setPosition(625, 490);
        nextLabel.setWidth(200);
        next.setPosition(950, 480);

        table.add(equation).colspan(2).center().padBottom(20);
        table.row();
        table.add(answer1);
        if (!quadratic.getOnlyOneRoot()) {
            table.add(answer2).padLeft(40);
        }
        else {
            table.add(onlyOneRoot).padLeft(40);
        }
        table.setPosition(600, 400);

        Table answersTable = new Table(skin);
        answersTable.setPosition(600, 175);

        final DragAndDrop dragAndDrop = new DragAndDrop();

        dragAndDrop.addTarget(new Target(answer1) {
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


        dragAndDrop.addTarget(new Target(answer2) {
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
        for (String answer : quadratic.generateAnswers()) {
            final TextButton label = new TextButton(answer, answerStyle);


            label.setSize(100, 100);
            label.setColor(Color.BLUE);
            answersTable.add(label).pad(15);
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

                    TextButton draggedLabel = new TextButton(label.getText().toString(), answerStyle);
                    draggedLabel.setColor(Color.BLUE);

                    payload.setDragActor(draggedLabel);

                    return payload;
                }

                @Override
                public void dragStop(InputEvent event, float xaxis, float yaxis, int pointer, Payload payload, Target target) {

                    if (target != null) {
                        if (quadratic.getOnlyOneRoot()) {
                            answer1.setText("x = " + label.getText());
                            if (Float.valueOf(label.getText().toString()) == (quadratic.getAnswer1())) {
                                correct.play();
                                countdown.stop();
                                feedbackWrong.setVisible(false);
                                feedbackCorrect.setVisible(true);
                                dragAndDrop.clear();
                                isCorrect = true;
                                next.setVisible(true);
                                nextLabel.setVisible(true);
                                incCorrectCount();
                            } else {
                                wrong.play();
                                feedbackWrong.setVisible(true);
                                incWrongCount();
                                answer1.setText("x = ");

                            }

                        } else {
                            if (target.getActor().equals(answer1)) {
                                answer1.setText("x = " + label.getText());
                                if (isAnswer2Set()) {
                                    if (Float.valueOf(label.getText().toString()) == (quadratic.getAnswer1()) &&
                                            x2.equals("answer2")) {
                                        //correct
                                        correct.play();
                                        countdown.stop();
                                        feedbackWrong.setVisible(false);
                                        feedbackCorrect.setVisible(true);
                                        dragAndDrop.clear();
                                        isCorrect = true;
                                        next.setVisible(true);
                                        nextLabel.setVisible(true);
                                        incCorrectCount();
                                    }
                                    else if (Float.valueOf(label.getText().toString()) == (quadratic.getAnswer2()) &&
                                            x2.equals("answer1")) {
                                        //correct
                                        correct.play();
                                        countdown.stop();
                                        feedbackWrong.setVisible(false);
                                        feedbackCorrect.setVisible(true);
                                        dragAndDrop.clear();
                                        isCorrect = true;
                                        next.setVisible(true);
                                        nextLabel.setVisible(true);
                                        incCorrectCount();
                                    }
                                    else {
                                        //wrong
                                        wrong.play();
                                        feedbackWrong.setVisible(true);
                                        incWrongCount();
                                        answer1.setText("x = ");
                                        answer1.setText("x = ");
                                        x1 = "";
                                        x2 = "";
                                        setIsAnswer1Set(false);
                                        setIsAnswer2Set(false);
                                    }

                                }
                                else {
                                    setIsAnswer1Set(true);
                                    if (Float.valueOf(label.getText().toString()) == (quadratic.getAnswer1())) {
                                        x1 = "answer1";
                                    }
                                    else if (Float.valueOf(label.getText().toString()) == (quadratic.getAnswer2())) {
                                        x1 = "answer2";
                                    }


                                }
                            }
                            else {
                                answer2.setText("x = " + label.getText());

                                if (isAnswer1Set()) {
                                    if (Float.valueOf(label.getText().toString()) == (quadratic.getAnswer2()) &&
                                            x1.equals("answer1")) {
                                        //correct
                                        correct.play();
                                        countdown.stop();
                                        feedbackWrong.setVisible(false);
                                        feedbackCorrect.setVisible(true);
                                        dragAndDrop.clear();
                                        isCorrect = true;
                                        next.setVisible(true);
                                        nextLabel.setVisible(true);
                                        incCorrectCount();
                                    }
                                    else if (Float.valueOf(label.getText().toString()) == (quadratic.getAnswer1()) &&
                                            x1.equals("answer2")) {
                                        //correct
                                        correct.play();
                                        countdown.stop();
                                        feedbackWrong.setVisible(false);
                                        feedbackCorrect.setVisible(true);
                                        dragAndDrop.clear();
                                        isCorrect = true;
                                        next.setVisible(true);
                                        nextLabel.setVisible(true);
                                        incCorrectCount();
                                    }
                                    else {
                                        //wrong
                                        wrong.play();
                                        feedbackWrong.setVisible(true);
                                        incWrongCount();
                                        answer1.setText("x = ");
                                        answer2.setText("x = ");
                                        x1 = "";
                                        x2 = "";
                                        setIsAnswer1Set(false);
                                        setIsAnswer2Set(false);

                                    }

                                }
                                else {
                                    setIsAnswer2Set(true);
                                    if (Float.valueOf(label.getText().toString()) == (quadratic.getAnswer2())) {
                                        x2 = "answer2";
                                    }
                                    else if (Float.valueOf(label.getText().toString()) == (quadratic.getAnswer1())) {
                                        x2 = "answer1";
                                    }


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

                game.setScreen(new PlayScreen3(game, player));

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
                game.setScreen(new PlayScreen3(game, player));
            }
        }
        if (timer == 10 && !isPlaying) {
            isPlaying = true;
            countdown.play();
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
        correct.dispose();
        wrong.dispose();
        countdown.dispose();
        player.resetCurrentStreak();

    }


    public void incCorrectCount() {
        player.incAttempts();
        player.incCorrectCount();
        player.incCurrentStreak();
        player.incPoints(3, timer);
        points.setText("Points: " + Integer.toString(player.getPoints(3)));
        timeBonus.setText("Time Bonus!!! " + "+ " + timer);
        streak.setText("streak: " + player.getCurrentStreak());
        timeBonus.setVisible(true);
        if (player.getPoints(3) >= 300 && !player.getSection(3)) {
            player.completeSection(3);

        }
    }

    public void incWrongCount() {
        player.incAttempts();
        player.resetCurrentStreak();
        player.incWrongCount();
        player.decPoints(3);
        streak.setText("streak: " + player.getCurrentStreak());
        points.setText("Points: " + Integer.toString(player.getPoints(3)));
    }



    public boolean isAnswer1Set() {
        return isAnswer1Set;
    }


    public boolean isAnswer2Set() {
        return isAnswer2Set;
    }

    public void setIsAnswer1Set(boolean isSet) {
        isAnswer1Set = isSet;
    }

    public void setIsAnswer2Set(boolean isSet) {
        isAnswer2Set = isSet;
    }



}

