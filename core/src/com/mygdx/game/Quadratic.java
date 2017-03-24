package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import java.util.ArrayList;
import java.util.Collections;

import static com.mygdx.game.Equation.randInt;
import static com.mygdx.game.TextRenderer.renderString;

/**
 * Created by henrymoule on 09/03/2017.
 */
public class Quadratic {
    private double coefficientA;
    private double coefficientB;
    private double coefficientC;
    private double answer1;
    private double answer2;
    private String operator1;
    private String operator2;
    private boolean onlyOneRoot = false;


    public Quadratic() {

        do {
            coefficientA = randInt(1, 10);
            coefficientB = randInt(1, 10);
            coefficientC = randInt(1, 10);

            int randomOperator1 = randInt(1, 2);
            int randomOperator2 = randInt(1, 2);

            if (randomOperator1 == 1) {
                operator1 = "+";
            } else {
                operator1 = "-";
            }
            if (randomOperator2 == 1) {
                operator2 = "+";
            } else {
                operator2 = "-";
            }
            solve();
        } while (answer1 % 1 != 0 || answer2 % 1 != 0);

        if (answer1 == answer2) {
            onlyOneRoot = true;
        }

    }
    public void solve() {
        double b = coefficientB;

        if (operator1.equals("-")) {
            b *= -1;

        }
        double c = coefficientC;
        if (operator2.equals("-")) {
            c *= -1;

        }
        double d = b*b - 4 * coefficientA * c;
        answer1 = (- b + Math.sqrt(d))/(2*coefficientA);
        answer2 = (- b - Math.sqrt(d))/(2*coefficientA);

    }

    public Table getTable() {
        Skin skin = new Skin(Gdx.files.internal("uiskin.json"));
        String part1;
        String part2;
        String part3 = Integer.toString((int)Math.round(coefficientC));

        if (coefficientA == 1) {
            part1 = "x^2^ ";

        }
        else {
            part1 = Integer.toString((int)Math.round(coefficientA)) + "x^2^ ";
        }

        if (coefficientB == 1) {
            part2 = " x ";
        }
        else {
            part2 = " " + Integer.toString((int)Math.round(coefficientB)) + "x ";
        }

        return renderString(skin, part1 + operator1 + part2 + operator2 + " " + part3 + " = 0", Color.WHITE);
    }


    public ArrayList<String> generateAnswers() {
        ArrayList<String> answers= new ArrayList<String>();
        int start;
        if (onlyOneRoot) {
            start = 0;
        }
        else {
            start = 1;
        }
        for (int i = start; i < 11; i++) {
            int num;
            do {
                num = randInt(1, 10);
                if (i % 2 != 0) {
                    num = num*-1;
                }
            } while (num == answer1 || num == answer2 || answers.contains(Integer.toString(num)) );
            answers.add(Integer.toString(num));
        }
        answers.add(Integer.toString((int)Math.round(answer1)));
        if (!onlyOneRoot) {
            answers.add(Integer.toString((int) Math.round(answer2)));
        }

        Collections.shuffle(answers);
        return answers;

    }
    public boolean getOnlyOneRoot() {
        return onlyOneRoot;
    }

    public double getAnswer1() {
        return  answer1;
    }
    public double getAnswer2() {
        return  answer2;
    }
}
