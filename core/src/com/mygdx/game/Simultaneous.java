package com.mygdx.game;


import static com.mygdx.game.Equation.randInt;

/**
 * Created by henrymoule on 03/03/2017.
 */
public class Simultaneous {
    private float xMultplier1st;
    private float xMultplier2nd;
    private float yMultplier1st;
    private float yMultplier2nd;
    private float RHSNumber;

    public Simultaneous() {
        xMultplier1st = randInt(2, 10);
        xMultplier2nd = randInt(2, 10);


    }
}
