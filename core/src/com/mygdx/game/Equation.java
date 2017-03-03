package com.mygdx.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Created by henrymoule on 04/01/2017.
 */

public class Equation {
    private float multiplier;
    private float LHSNumber;
    private float RHSNumber;
    private String operator;

    public Equation() {

        //do while to make sure x is a whole number
        do {
            multiplier = randInt(2, 10);
            LHSNumber = randInt(1, 20);
            RHSNumber = randInt(1, 20);
            int randomOperator = randInt(1, 2);

            if (randomOperator == 1) {
                operator = "+";
            } else {
                operator = "-";
            }
        } while (answer() % 1 != 0);

    }

    public String equationString() {
       return Math.round(multiplier) + "x " + operator + " " + Math.round(LHSNumber) + " = " + Math.round(RHSNumber);
    }

    public String solveEquation() {
        return Integer.toString(Math.round(answer()));

    }

    public float answer() {
        float answer;
        if (operator.equals("+")) {
            answer = (RHSNumber - LHSNumber) / multiplier;

        } else {
            answer = (RHSNumber + LHSNumber) / multiplier;

        }
        return  answer;
    }

    public ArrayList<String> generateAnswers() {
        ArrayList<String> answers= new ArrayList<String>();
        for (int i = 0; i < 7; i++) {
            int num;
            do {
                num = randInt(1, 10);
                if (i % 2 != 0) {
                    num = num*-1;
                }
            } while (num == answer() || answers.contains(Integer.toString(num)) );
            answers.add(Integer.toString(num));
        }
        answers.add(solveEquation());

        Collections.shuffle(answers);
        return answers;

    }

    /**
     *
     * @param min minimum number you want to return
     * @param max maximum number you want to return
     * @return returns random number in between min and max
     */
    public int randInt(int min, int max) {

        Random rand =  new Random();

        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }

}
