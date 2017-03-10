package com.mygdx.game;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class TextRenderer {

    private static final float PADDING = 50; // Vertical padding for subscript and superscript blocks

    public static Table renderString(Skin skin, String string, Color color) {
        Table table = new Table(skin);
        StringBuilder input = new StringBuilder(string);
        StringBuilder currentBlock = new StringBuilder();
        RenderMode currentMode = RenderMode.NORMAL;
        while (input.length() >= 1) {
            char currentChar = input.charAt(0);
            input.deleteCharAt(0);
            boolean endOfBlock = false;
            RenderMode newMode = null;
            switch(currentMode) {
                case NORMAL:
                    endOfBlock = true;
                    if (currentChar == '_') {
                        newMode = RenderMode.SUBSCRIPT;
                    } else if (currentChar == '^') {
                        newMode = RenderMode.SUPERSCRIPT;
                    } else {
                        currentBlock.append(currentChar);
                        endOfBlock = false;
                    }
                    break;
                case SUBSCRIPT:
                    if (currentChar == '_') {
                        newMode = RenderMode.NORMAL;
                        endOfBlock = true;
                    } else {
                        currentBlock.append(currentChar);
                    }
                    break;
                case SUPERSCRIPT:
                    if (currentChar == '^') {
                        newMode = RenderMode.NORMAL;
                        endOfBlock = true;
                    } else {
                        currentBlock.append(currentChar);
                    }
                    break;
            }
            if ((endOfBlock || input.length() == 0) && currentBlock.length() > 0) {
                switch (currentMode) {
                    case NORMAL:
                        String text = currentBlock.toString();
                        table.add(text, "default-font", color);
                        break;
                    case SUBSCRIPT:
                        table.add(currentBlock.toString(), "default-font", color).padTop(PADDING);
                        break;
                    case SUPERSCRIPT:
                        table.add(currentBlock.toString(), "small-font", color).padBottom(PADDING);
                        break;
                }
                currentBlock = new StringBuilder();
            }
            if (newMode != null) {
                currentMode = newMode;
            }
        }
        return table;
    }

    public enum RenderMode {
        NORMAL, SUBSCRIPT, SUPERSCRIPT;
    }

}
