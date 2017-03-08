package com.mygdx.game;

import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/**
 * Created by hen10 on 07/03/2017.
 */


public class PlayDialog extends Dialog {

    public PlayDialog(String title, Skin skin) {
        super(title, skin);
    }

    public PlayDialog(String title, Skin skin, String windowStyleName) {
        super(title, skin, windowStyleName);
    }

    public PlayDialog(String title, WindowStyle windowStyle) {
        super(title, windowStyle);

    }

    {
        text("You have earnt enough\npoints to play the next section\ncontinue practicing or exit\n to play new section");

        button("OK");
    }

    @Override
    protected void result(Object object) {
    }



}
