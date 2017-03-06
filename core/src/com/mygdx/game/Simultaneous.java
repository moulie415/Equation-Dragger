package com.mygdx.game;


import static com.mygdx.game.Equation.randInt;

/**
 * Created by henrymoule on 03/03/2017.
 */
public class Simultaneous {
    private float xMultplier1st;
    private float xMultplier2nd;
    private float yMultplier1st;
    private float RHSNumber;
    private float x;
    private float y;
    private String operator;

    public Simultaneous() {

        do {
            xMultplier1st = randInt(2, 10);
            yMultplier1st = randInt(2, 10);
            xMultplier2nd = randInt(2, 10);
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
            x = RHSNumber/(xMultplier1st + (yMultplier1st*xMultplier2nd));
        }
        else {
            x = RHSNumber/(xMultplier1st-(yMultplier1st*xMultplier2nd));
        }
        y = xMultplier2nd*x;
    }

    public String firstToString() {
        return Math.round(xMultplier1st) + "x " +  operator + " " + Math.round(yMultplier1st) + "y = " + Math.round(RHSNumber);
    }

    public String secondToString() {
        return "y = " + Math.round(xMultplier2nd) + "x";
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

}
