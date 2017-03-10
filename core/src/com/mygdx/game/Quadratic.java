package com.mygdx.game;

import java.util.ArrayList;
import java.util.Collections;

import static com.mygdx.game.Equation.randInt;

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

    public String toString() {
        String part1;
        String part2;
        String part3 = Integer.toString((int)Math.round(coefficientC));

        if (coefficientA == 1) {
            part1 = "x\u00B2 ";

        }
        else {
            part1 = Integer.toString((int)Math.round(coefficientA)) + "x\u00B2 ";
        }

        if (coefficientB == 1) {
            part2 = " x ";
        }
        else {
            part2 = " " + Integer.toString((int)Math.round(coefficientB)) + "x ";
        }

        return part1 + operator1 + part2 + operator2 + " " + part3 + " = 0";
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
