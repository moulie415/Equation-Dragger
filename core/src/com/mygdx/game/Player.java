package com.mygdx.game;

import java.math.BigDecimal;

/**
 * Created by henrymoule on 27/02/2017.
 */
public class Player {

    private static final long serialVersionUID = 1;
    private int attempts;
    private int correctCount;
    private int wrongCount;

    public Player() {
        attempts = 0;
        correctCount = 0;
        wrongCount = 0;
    }

    public int getAttempts() {
        return attempts;
    }

    public int getCorrectCount() {
        return correctCount;
    }

    public int getWrongCount() {
        return wrongCount;
    }

    public float getRatio() {
        float correct = correctCount;
        float wrong = wrongCount;
        float ratio = correct/wrong;
        System.out.println(ratio);
        if (ratio > 0) {
            return round(ratio, 2);
        }
        else {
            return 0;
        }
    }

    public void incAttempts() {
        attempts +=1;
    }


    public void incCorrectCount() {
        correctCount +=1;
    }

    public void incWrongCount() {
        wrongCount +=1;
    }


    /**
     * Round to certain number of decimals
     *
     * @param d
     * @param decimalPlace
     * @return
     */
    public static float round(float d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd.floatValue();
    }

}
