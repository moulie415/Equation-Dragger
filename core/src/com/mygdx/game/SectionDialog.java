package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/**
 * Created by hen10 on 07/03/2017.
 */


public class SectionDialog extends Dialog {
    private Sound click;

    public SectionDialog(String title, Skin skin) {
        super(title, skin);
        click = Gdx.audio.newSound(Gdx.files.internal("sounds/button-click.wav"));
    }

    public SectionDialog(String title, Skin skin, String windowStyleName) {
        super(title, skin, windowStyleName);
    }

    public SectionDialog(String title, WindowStyle windowStyle) {
        super(title, windowStyle);

    }

    {
        text("Please complete previous section first");

        button("OK");
    }

    @Override
    protected void result(Object object) {
        click.play();
    }



}
