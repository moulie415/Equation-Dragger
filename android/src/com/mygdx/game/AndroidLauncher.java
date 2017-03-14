package com.mygdx.game;

import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.mygdx.game.MyGdxGame;

public class AndroidLauncher extends AndroidApplication {
	private static final String TAG = "AndroidLauncher";
	private AdView adView;
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		RelativeLayout layout = new RelativeLayout(this);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		View gameView = initializeForView(new MyGdxGame(), config);
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
}
