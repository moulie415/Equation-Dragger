package com.mygdx.game;


import java.util.ArrayList;
import java.util.Collections;

import static com.mygdx.game.Equation.randInt;

/**
 * Created by henrymoule on 03/03/2017.
 */
public class Simultaneous {
    private float xMultiplier1st;
    private float xMultiplier2nd;
    private float yMultiplier1st;
    private float RHSNumber;
    private float x;
    private float y;
    private String operator;

    public Simultaneous() {

        do {
            xMultiplier1st = randInt(2, 10);
            yMultiplier1st = randInt(2, 10);
            xMultiplier2nd = randInt(2, 10);
            RHSNumber = randInt(1, 10);
            int randomOperator = randInt(1, 2);

            if (randomOperator == 1) {
                operator = "+";
            } else {
                operator = "-";
            }
            assignXandY();
        } while (x % 1 != 0 || y % 1 != 0);

    }
    public void assignXandY() {
        if (operator.equals("+")) {
            x = RHSNumber/(xMultiplier1st + (yMultiplier1st * xMultiplier2nd));
        }
        else {
            x = RHSNumber/(xMultiplier1st -(yMultiplier1st * xMultiplier2nd));
        }
        y = xMultiplier2nd *x;
    }

    public String firstToString() {
        return Math.round(xMultiplier1st) + "x " +  operator + " " + Math.round(yMultiplier1st) + "y = " + Math.round(RHSNumber);
    }

    public String secondToString() {
        return "y = " + Math.round(xMultiplier2nd) + "x";
    }

    public ArrayList<String> generateAnswers() {
        ArrayList<String> answers= new ArrayList<String>();
        for (int i = 0; i < 10; i++) {
            int num;
            do {
                num = randInt(1, 10);
                if (i % 2 != 0) {
                    num = num*-1;
                }
            } while (num == getX() || num == getY() || answers.contains(Integer.toString(num)) );
            answers.add(Integer.toString(num));
        }
        answers.add(Integer.toString(Math.round(getX())));
        answers.add(Integer.toString(Math.round(getY())));

        Collections.shuffle(answers);
        return answers;

    }
    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

}
