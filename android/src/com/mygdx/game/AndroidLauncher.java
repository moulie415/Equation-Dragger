package com.mygdx.game;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.games.Games;
import com.google.example.games.basegameutils.GameHelper;

public class AndroidLauncher extends AndroidApplication implements IGoogleServices {
	private static final String TAG = "AndroidLauncher";
	private AdView adView;
	private final static int REQUEST_CODE_UNUSED = 9002;

	private GameHelper _gameHelper;
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//Create the GameHelper
		_gameHelper = new GameHelper(this, GameHelper.CLIENT_GAMES);
		_gameHelper.enableDebugLog(false);
		GameHelper.GameHelperListener gameHelperListener = new GameHelper.GameHelperListener()
		{
			@Override
			public void onSignInSucceeded()
			{
			}

			@Override
			public void onSignInFailed()
			{
			}
		};

		_gameHelper.setup(gameHelperListener);

		RelativeLayout layout = new RelativeLayout(this);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		View gameView = initializeForView(new MyGdxGame(this), config);
		layout.addView(gameView);
		adView = new AdView(this);

		adView.setAdListener(new AdListener() {
			@Override
			public void onAdLoaded() {
				super.onAdLoaded();
				int visibility = adView.getVisibility();
				adView.setVisibility(AdView.GONE);
				adView.setVisibility(visibility);
				Log.i(TAG, "Ad Loaded....");
			}
		});
		adView.setAdSize(AdSize.SMART_BANNER);
		adView.setAdUnitId("ca-app-pub-7885763333661292/4193990968");

		AdRequest.Builder builder = new AdRequest.Builder();
		RelativeLayout.LayoutParams adParams = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT
		);
		adParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		layout.addView(adView, adParams);
		adView.loadAd(builder.build());

		setContentView(layout);
		//initialize(new MyGdxGame(), config);
	}

	@Override
	public void signIn() {
		try {
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					_gameHelper.beginUserInitiatedSignIn();
				}
			});
		}
		catch (Exception e) {
			Gdx.app.log("MainActivity", "Log in failed: " + e.getMessage() + ".");
		}

	}

	@Override
	public void signOut() {

		try {
			runOnUiThread(new Runnable() {
				//@Override
				public void run() {
					_gameHelper.signOut();
				}
			});
		}
		catch (Exception e) {
			Gdx.app.log("MainActivity", "Log out failed: " + e.getMessage() + ".");
		}
	}

	@Override
	public void rateGame() {
// Replace the end of the URL with the package of your game
		String str ="https://play.google.com/store/apps/details?id=com.mygdx.equationDragger";
		startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(str)));

	}

	@Override
	public void submitScore(long score, String leaderboard) {
		if (isSignedIn())
		{
			if (leaderboard.equals("points")) {
				Games.Leaderboards.submitScore(_gameHelper.getApiClient(), getString(R.string.points_id), score);
				startActivityForResult(Games.Leaderboards.getLeaderboardIntent(_gameHelper.getApiClient(), getString(R.string.points_id)), REQUEST_CODE_UNUSED);
			}
			else if (leaderboard.equals("ratio")) {
				Games.Leaderboards.submitScore(_gameHelper.getApiClient(), getString(R.string.ratio_id), score);
				startActivityForResult(Games.Leaderboards.getLeaderboardIntent(_gameHelper.getApiClient(), getString(R.string.ratio_id)), REQUEST_CODE_UNUSED);

			}
			else {
				System.out.println("invalid leaderboard string");
			}
		}
		else
		{
// Maybe sign in here then redirect to submitting score?
		}
	}

	@Override
	public void showScores(String leaderboard) {
		if (isSignedIn())
			if (leaderboard.equals("points")) {
				startActivityForResult(Games.Leaderboards.getLeaderboardIntent(_gameHelper.getApiClient(), getString(R.string.points_id)), REQUEST_CODE_UNUSED);
			}
			else if (leaderboard.equals("ratio")) {
				startActivityForResult(Games.Leaderboards.getLeaderboardIntent(_gameHelper.getApiClient(), getString(R.string.ratio_id)), REQUEST_CODE_UNUSED);

			}
			else {
				System.out.println("invalid leaderboard string");

			}
		else

		{
// Maybe sign in here then redirect to showing scores?
		}
	}

	@Override
	public boolean isSignedIn() {
		return _gameHelper.isSignedIn();
	}
	@Override
	protected void onStart()
	{
		super.onStart();
		_gameHelper.onStart(this);
	}

	@Override
	protected void onStop()
	{
		super.onStop();
		_gameHelper.onStop();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);
		_gameHelper.onActivityResult(requestCode, resultCode, data);
	}
}
