package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.io.*;
import java.math.BigDecimal;

/**
 * Created by henrymoule on 27/02/2017.
 */
public class Player implements Serializable {

    private static final long serialVersionUID = 1;
    private int attempts;
    private int correctCount;
    private int wrongCount;
    private boolean section1;
    private boolean section2;
    private boolean section3;

    public Player() {
        attempts = 0;
        correctCount = 0;
        wrongCount = 0;
        section1 = false;
        section2 = false;
        section3 = false;
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
        float ratio;
        if (wrong > 0) {
            ratio = correct / wrong;
        }
        else {
            ratio = correct;
        }
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



    public static void savePlayer(Player player) throws IOException {
        FileHandle file = Gdx.files.local("player.dat");
        OutputStream out = null;
        try {
            file.writeBytes(serialize(player), false);
        }
        catch (Exception ex){
            System.out.println(ex.toString());
        }
        finally {
            if (out != null) try{out.close();} catch (Exception ex) {}
        }
        System.out.println("Saving Player");
    }

    public static Player readPlayer() throws IOException, ClassNotFoundException {
        Player player = null;

        FileHandle file = Gdx.files.local("player.dat");
        player = (Player) deserialize(file.readBytes());

        return player;
    }

    private static byte[] serialize(Object obj) throws IOException{
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        ObjectOutputStream o = new ObjectOutputStream(b);
        o.writeObject(obj);
        return b.toByteArray();
    }

    public static Object deserialize(byte[] bytes) throws IOException, ClassNotFoundException {
        ByteArrayInputStream b = new ByteArrayInputStream(bytes);
        ObjectInputStream o = new ObjectInputStream(b);
        return o.readObject();
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

    public void completeSection(int i) {
        switch(i) {
            case 1: section1 = true;
                break;
            case 2: section2 = true;
                break;
            case 3: section3 = true;
                break;
            default:
                System.out.println("invalid section number");
        }
    }

    public boolean getSection(int i) {
        boolean section;
        switch(i) {
            case 1: section = section1;
            break;
            case 2: section = section2;
            break;
            case 3: section = section3;
            break;
            default:
                System.out.println("invalid section number");
                section = false;
        }
        return section;
    }

}
