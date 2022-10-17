package com.badlogic.gdx.vr.test;

import android.os.Bundle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.badlogic.gdx.vr.GoogleVRApplication;

public class AndroidLauncher extends GoogleVRApplication {

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new GdxVrTest(), config);
	}

	@Override
	public void onCardboardTrigger () {
		Gdx.app.log("AndroidLauncher", "onCardboardTrigger");
	}

}
