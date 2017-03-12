package com.mygdx.game.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import com.mygdx.game.MyGdxGame;

public class HtmlLauncher extends GwtApplication {

        @Override
        public GwtApplicationConfiguration getConfig () {
                GwtApplicationConfiguration cfg = new GwtApplicationConfiguration(1280, 720);
                cfg.preferFlash = false;
                return cfg;
        }

        @Override
        public ApplicationListener createApplicationListener () {
                return new MyGdxGame();
        }
}