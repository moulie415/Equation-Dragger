package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.io.*;

/**
 * Created by henrymoule on 27/02/2017.
 */
public class Player implements Serializable {

    private static final long serialVersionUID = 1;
    private int attempts;
    private int correctCount;
    private int wrongCount;
    private int ratio;

    public Player() {
        attempts = 0;
        correctCount = 0;
        wrongCount = 0;
        ratio = 0;
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

    public int getRatio() {
        return ratio;
    }

    public void incAttempts() {
        attempts +=1;
    }


    public void incCorrectCount() {
        correctCount +=1;
        ratio = correctCount/wrongCount;
    }

    public void incWrongCount() {
        wrongCount +=1;
        ratio = correctCount/wrongCount;
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
}
