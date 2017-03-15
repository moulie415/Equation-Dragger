package com.mygdx.game;

/**
 * Created by hen10 on 14/03/2017.
 */
public interface IGoogleServices
{
    public void signIn();
    public void signOut();
    public void rateGame();
    public void submitScore(long score);
    public void showScores();
    public boolean isSignedIn();
}
