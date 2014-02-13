package com.anigeek.mastersword;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.preference.PreferenceActivity;

public class ZeldaPreferenceActivity extends PreferenceActivity// Hey, a preference activity
{
	public void onCreate(Bundle savedInstanceState)// saved stuff
	{
		super.onCreate(savedInstanceState);// when created
		addPreferencesFromResource(R.xml.preferences);// get preferences
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);// but only in portrait
	}
	
	public void onPause()// when paused
	{
		super.onPause();// call this
		Context preference = getBaseContext();// get the context for this
		Intent intent = BaseActivity.returnPref(preference);// get the intent from returnPref
		startActivity(intent);// start the intent
	}
}