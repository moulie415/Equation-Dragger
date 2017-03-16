package com.mygdx.game;

/**
 * Created by hen10 on 14/03/2017.
 */
public interface IGoogleServices
{
    public void signIn();
    public void signOut();
    public void rateGame();
    public void submitScore(long score, String leaderboard);
    public void showScores(String leaderboard);
    public boolean isSignedIn();
    public void unlockAchievement(String achievement);
    public void showAchievements();

}
