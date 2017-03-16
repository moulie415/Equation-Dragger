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
    private int section1Points;
    private int section2Points;
    private int section3Points;
    private int currentStreak;
    private int bestStreak;
    private boolean rookie;
    private boolean intermediate;
    private boolean expert;
    private boolean grinder;
    private boolean perfectionist;


    public Player() {
        attempts = 0;
        correctCount = 0;
        wrongCount = 0;
        section1Points = 0;
        section2Points = 0;
        section3Points = 0;
        section1 = false;
        section2 = false;
        section3 = false;
        rookie = false;
        intermediate = false;
        expert = false;
        grinder = false;
        perfectionist = false;
        section1Points = 0;
        section2Points = 0;
        section3Points = 0;
        currentStreak = 0;
        bestStreak = 0;
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

    public void incPoints(int section,int timeBonus) {
            switch (section) {
                case 1:
                    section1Points += 10;
                    section1Points += timeBonus;
                    break;
                case 2:
                    section2Points += 30;
                    section2Points += timeBonus;
                    break;
                case 3:
                    section3Points += 40;
                    section3Points += timeBonus;
                    break;
                default:
                    System.out.println("Invalid section number");

            }
            if (getTotalPoints() >= 100 && !rookie) {
                rookie = true;
                MyGdxGame.googleServices.unlockAchievement("rookie");
            }
            if (getTotalPoints() >= 2000 && !grinder) {
                grinder = true;
                MyGdxGame.googleServices.unlockAchievement("grinder");
            }
    }

    public void decPoints(int section) {
        switch(section) {
            case 1:
            if ((getPoints(1)-5) < 0) {
                section1Points = 0;
            }
            else {
                section1Points -=5;
            }
                break;
            case 2:
                if ((getPoints(2)-5) < 0) {
                    section2Points = 0;
                }
                else {
                    section2Points -=5;
                }
                break;
            case 3:
                if ((getPoints(3)-5) < 0) {
                    section3Points = 0;
                }
                else {
                    section3Points -=5;
                }
                break;
            default:
                System.out.println("Invalid section number");
        }


    }

    public int getPoints(int section) {
        int points;
        switch(section) {
            case 1: points = section1Points;
                break;
            case 2: points = section2Points;
                break;
            case 3: points = section3Points;
                break;
            default:
                points = 0;
                System.out.println("Invalid section number");
        }
        return points;
    }

    public int getTotalPoints() {
        return section1Points + section2Points + section3Points;
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
                if (!intermediate) {
                    intermediate = true;
                    MyGdxGame.googleServices.unlockAchievement("intermediate");
                }
                break;
            case 3: section3 = true;
                if (!expert) {
                    expert = true;
                    MyGdxGame.googleServices.unlockAchievement("expert");
                }
                if (!perfectionist && wrongCount == 0) {
                    perfectionist = true;
                    MyGdxGame.googleServices.unlockAchievement("perfectionist");
                }
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

    public int getCurrentStreak() {
        return currentStreak;
    }
    public int getBestStreak() {
        return bestStreak;
    }

    public void resetCurrentStreak(){
        currentStreak = 0;
    }

    public void incCurrentStreak() {
        currentStreak++;
        setBestStreak();
    }

    public void setBestStreak() {
        if (currentStreak > bestStreak) {
            bestStreak = currentStreak;
        }
    }

}
