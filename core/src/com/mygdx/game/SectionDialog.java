package com.mygdx.game;

import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/**
 * Created by hen10 on 07/03/2017.
 */


public class SectionDialog extends Dialog {

    public SectionDialog(String title, Skin skin) {
        super(title, skin);
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
    }



}
