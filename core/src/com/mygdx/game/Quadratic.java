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

        System.out.println(toString());
        System.out.println(answer1);
        System.out.println(answer2);

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
        answer1 = (-1*(b)+Math.sqrt((Math.pow(b, 2)-4*coefficientA*c)))/2*coefficientA;
        answer2 = (-1*(b)-Math.sqrt((Math.pow(b, 2)-4*coefficientA*c)))/2*coefficientA;

    }

    public String toString() {
        String part1;
        String part2;

        if (coefficientA == 1) {
            part1 = "x²";

        }
        else {
            part1 = Double.toString(coefficientA) + "x² ";
        }

        if (coefficientB == 1) {
            part2 = " x ";
        }
        else {
            part2 = " " + Double.toString(coefficientB) + "x ";
        }

        return part1 + operator1 + part2 + operator2 + " " + coefficientC + " = 0";
    }


/*    public ArrayList<String> generateAnswers() {
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

    }*/
}
