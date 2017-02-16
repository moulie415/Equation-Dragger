package com.mygdx.game;

import java.util.ArrayList;
import java.util.Collection;
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
        multiplier = randInt(2, 10);
        LHSNumber = randInt(1, 20);
        RHSNumber = randInt(1, 20);
        int randomOperator = randInt(1,2);

        if (randomOperator == 1) {
            operator = "+";
        }
        else {
            operator = "-";
        }
    }

    public String equationString() {
       return Math.round(multiplier) + "x " + operator + " " + Math.round(LHSNumber) + " = " + Math.round(RHSNumber);
    }

    public String solveEquation() {
        float answer;
        if (operator.equals("+")) {
            answer = (RHSNumber - LHSNumber) / multiplier;
                return isWhole(answer);
        }
        else {
            answer = (RHSNumber + LHSNumber) / multiplier;
            return isWhole(answer);
        }
    }

    public String isWhole(float answer) {
        if (answer % 1 == 0) {
            return Integer.toString(Math.round(answer));
        }
        else {
            if (operator.equals("+")) {
                return Integer.toString(Math.round(RHSNumber - LHSNumber)) + "/" + Integer.toString(Math.round(multiplier));
            }
            else {
                return Integer.toString(Math.round(RHSNumber + LHSNumber)) + "/" + Integer.toString(Math.round(multiplier));
            }
        }
    }

    public ArrayList<String> generateAnswers() {
        ArrayList<String> answers= new ArrayList<String>();
        int num = randInt(1, 10);
        //add actual answer to begin with
        answers.add(solveEquation());
        answers.add(Integer.toString(num));
        answers.add(Integer.toString(num*-1));
        if (num != multiplier) {
            answers.add(Integer.toString(num) + "/" + Integer.toString(Math.round(multiplier)));
        }

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
