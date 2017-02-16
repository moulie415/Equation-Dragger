package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
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
    private float timeCount;
    private int timer;
    private Label timerLabel;
    private Viewport viewport;
    private Camera camera;
    private int VIRTUAL_WIDTH;
    private int VIRTUAL_HEIGHT;



    public PlayScreen(Game game) {
        this.game = game;

        VIRTUAL_WIDTH = 1280;
        VIRTUAL_HEIGHT = 720;
        //camera = new OrthographicCamera(VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
        viewport = new StretchViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT); //notice cam param here!
        stage = new Stage(viewport);

        skin = new Skin(Gdx.files.internal("uiskin.json"));

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("digital-7.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 64;

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



        final Equation equation1 = new Equation();
        Equation equation2 = new Equation();
        System.out.println(equation1.equationString());
        System.out.println(equation1.solveEquation());

        final Label equation = new Label(equation1.equationString(), skin);


        //equation.setSize(100,100);
        System.out.println(equation.getFontScaleX());
        System.out.println(equation.getFontScaleY());

        equation.setColor(Color.BLACK);

        Table table = new Table();

        //table.add(timerLabel);
        table.row();
        table.add(equation);
       table.setPosition(equation.getWidth()*2  , Gdx.graphics.getHeight()/2 );

        Table answersTable = new Table(skin);
        answersTable.setPosition(equation.getWidth()*2  , Gdx.graphics.getHeight()/2 - equation.getHeight());

        DragAndDrop dragAndDrop = new DragAndDrop();

        dragAndDrop.addTarget(new Target(equation) {
            @Override
            public boolean drag(Source source, Payload payload, float x, float y, int pointer) {


                getActor().setColor(Color.BLUE);

                return true;
            }


            public void reset (DragAndDrop.Source source, DragAndDrop.Payload payload) {
                getActor().setColor(Color.BLACK);
            }

            @Override
            public void drop(Source source, Payload payload, float x, float y, int pointer) {

            }
        });


        for (String answer : equation1.generateAnswers()) {
            final Label label = new Label(answer, skin);
            //label.setStyle();



            label.setColor(Color.BLACK);
            answersTable.add(label);
            answersTable.add(" ");


            dragAndDrop.addSource(new DragAndDrop.Source(label) {
                final Payload payload = new Payload();

                @Override
                public Payload dragStart(InputEvent event, float x, float y, int pointer) {
                    label.setVisible(false);
                    payload.setObject(label);
                    //System.out.println(label.getText());
                    //if (label.getText().toString().equals(equation1.solveEquation())) {

                    Label draggedLabel = new Label(label.getText(), skin);
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
                        if (label.getText().toString().equals(equation1.solveEquation())) {
                            System.out.println("correct");
                        } else {
                            System.out.println("false");
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
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,1,0,1);
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
        timeCount += dt;
        if (timeCount >= 1 && timer > 0 ) {
            timer--;
            timerLabel.setText(String.format("%3d", timer));
            timeCount = 0;

        }
    }

    @Override
    public void dispose() {
        stage.dispose();

    }
}
