package com.anigeek.mastersword;

import java.util.Random;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;


/**
 * This is BaseActivity. This activity is necessary for every other
 * activity to run properly. However, BaseActivity itself is never called,
 * and is not even included in the manifest as one of the activities.
 */
public class BaseActivity extends Activity //An Activity named BaseActivity
{
	//class variables that will be used throughout the program
	
	static Random generator = new Random();// Random number generator
	static boolean blarg = true;// boolean to make swing swoosh work.
	static boolean blarg2 = true;// used for fixing OnClick
	static boolean blarg3 = true;// used for fixing OnClick also
	static boolean lefty = false;// used to make left-handed swings
	static boolean voice = false;// used to make vocal swings
	static boolean vibe =  true;
	static boolean info = true;// used to make the info dialog
	static boolean started = true;// used to fix swinging on close
	static Intent intent = null;// makes Intent work
	static MediaPlayer mp = null;// pre-sets the Media Player.
	static MediaPlayer charge = null;// pre-sets the Media Player.
	static RotateAnimation anim = null;// pre-sets the Rotate Animation
	static ShakeActivity mShaker = null;// pre-sets the Shake Activity
	static ImageView zelda = null;// pre-sets the Image View
	static String sensText = null;// text for changing sensitivity
	static int sensitivity = 0;// default non-set sensitivity. Changed in startup()
	static int[] attack = null;// the array of attack sounds. Defined in each subclass
	static Context context = null;// the context for setting Intents
	static String back = null;// helps return from the preference screen properly
	static int voiceNum = 0;// number of the attack array that is voice sounds
	static int update = 0;// lets me add update dialogs.
	static int BG = -1;
	static LinearLayout lay = null;
	
	public void onCreate(Bundle savedInstanceState)// called when app first starts
	{
		super.onCreate(savedInstanceState);// gets the saved info from the last use of the app
		startup();// startup method. Initializes stuff.
	}
	
	public void onStart()// when the program is started. See the Android Lifecycle diagram for assistance.
	{
		started = true;// variable necessary for onShake()
		super.onStart();// crashes without.
	}
	
	public void onResume()// when the program is reopened.
	{
		started = true;// variable necessary for onShake()
		super.onResume();// crashes without
	}
	
	public void startup()// called on the first creation of the program. Sets some variables necessary for normal operation
    {
    	final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());// gets saved preferences like sensitivity
        try// just in case
        {
	        lefty = preferences.getBoolean("lefty", lefty);// left-handed mode?
	        voice = preferences.getBoolean("voice", voice);// voice-only mode?
	        vibe  = preferences.getBoolean("vibe" , vibe);
	        info = preferences.getBoolean("info", info);// first time user?
	        update = preferences.getInt("update", update);// just updated?
	        sensText = preferences.getString("sensText", sensText);// sensitivity?
	        BG = preferences.getInt("BG", BG);
        }
        catch(Exception e){}// throw, basically. If an exception is thrown, there's nothing we can do about it at this point.
        if (info)// if this is your first time using the app
    	{
    		showDialog(1);// show the how-to dialog
    		showDialog(0);
    		Editor editor = preferences.edit();// open the preference editor
    		editor.putBoolean("info", false);// change info to false
    		editor.commit();// commit it to memory
    	}
        if (update != 210)// 26 is the version code for v2.05
        {
        	showDialog(2);// show desperate plea to email me
        	Editor editor = preferences.edit();// open the preference editor
        	editor.putInt("update", 210);// change update to 26
        	editor.commit();// commit it to memory
        }
        try// if it hasn't been set yet, bad things happen
        {
        	sensText = preferences.getString("sensitivity", sensText);// get the String sensitivity
        	sensitivity = Integer.parseInt(sensText);// parse as an int and set it
        }
        catch(Exception e)
        {
        	sensitivity = 500;// if null, set it to default 5000
        }
        finally
        {
        	if (sensitivity > 5000)
        		sensitivity = 5000;// to make sure people don't set it to 9999999 and complain that it's broken. Always account for operator error.
        }
        onStart();// basically sets started to true
        
        setBG(BG);
        
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);// makes the app not go into landscape. Bad things happen.
        
        started = true;// maybe redundant, but I can't remember if it was necessary. Leaving it in for now.
        context = getBaseContext();// sets the context of the current Activity so that we can navigate using Intents
    }
	
	public void shake()// method called when the accelerometer detects movement
    {
		if (started)// if the phone isn't locked
		{
	    	anim.setInterpolator(new AccelerateInterpolator());// makes a more realistic sword movement
	  		anim.setDuration(200);// 200 milliseconds
	  		anim.setRepeatCount(1);// repeats one time
	  		anim.setRepeatMode(Animation.REVERSE);// repeats the one time in reverse
	  		try// if this isn't here, and the media players haven't been called, breaks the app
			{
	    		mp.release();// breaks otherwise
	    		charge.release();// same as above
				charge.stop();// break the noise!
			}
			catch (Exception e){}// only calls an exception the first time. No biggie; throw it!
			anim.reset();// Just in case
			playSound(attack);// play the sound from the Activity
			zelda.startAnimation(anim);// starts the swing
		}
    }
	
	protected android.app.Dialog onCreateDialog(int id)// dialog method
	    {
	   	 switch (id)// the number of the dialog
	   	 {
	   	 	case 0:// the about. I really need to change this to be one-time only, I think
	   	 	{
	   	 		AlertDialog.Builder builder = new Builder(this);// builds an alert
	   	 		return builder
	   	 			.setTitle("About")// sets title as about
	   	 			.setMessage("All images and sounds copyright Nintendo and Shigeru Miyamoto.\n\n" +
	   	 					"Questions and Comments: email me at developer@anigeek.com")// sets the message?
	   	 			.create();// creates it
	   	 	}
	   	 	case 1:// the 'I just downloaded this' dialog
	   	 	{
	   	 		AlertDialog.Builder builder1 = new Builder(this);// yep
	   	 		return builder1
	   	 		.setTitle("How To Use")
	   	 		.setMessage("Hello, and thank you for using Master Sword!\n\n" +
	   	 				"If you touch the sword, it'll swing. Cool right?\n\n" +
	   	 				"If you swing the sword, it'll also swing. Turn it to lefty mode for realistic Link-ness if you wish.\n\n" +
	   	 				"Here's the cool part: If you hold the sword, it'll charge. If you swing or release, it'll make the magic spin!\n\n" +
	   	 				"For questions or comments, please go to:\n" +
	   	 				"http://www.mastersword.anigeek.com\n" +
	   	 				"Or contact me at\n" +
	   	 				"developer@anigeek.com")
	   	 		.create();
	   	 	}
	   	 	case 2:// update dialog
	   	 	{
	   	 	AlertDialog.Builder builder = new Builder(this);// builds an alert
   	 		return builder
   	 		.setTitle("2.1! Finally!")
	 		.setMessage("This means that 3.0 is on the way, too. I'm working on a game right now, " +
					"something like Harvest Moon, so I'll be a bit busy, but hopefully not too busy." +
					"\n\nSorry for the long delay. My artist has had some crazy stuff going on, but now " +
					"the new art is here and all is well. I personally think the boomerang and slingshot are just amazing." +
					"No real functionality updates with this, but there will be a bit soon. On my list are some spin-attack updates and some minor corrections." +
					"\n\nThe artist who did all this is ShardPrime - shard_prime@hotmail.com. Please direct any art questions or requests to him." +
					"\n\nAs always, feel free to rate, comment, and email me at developer@anigeek.com!" +
					"\n-Kyen")// sets the message?
			.create();
	   	 	}
	   	 }
	   	 
	   	 return null;// in case a dialog isn't called. Neat trick.
	    }
			
	public void checkLOR()// check if it should swing left or right
	    {
	    	int i = generator.nextInt(2);// random number, left or right
			// Makes the rotate animation. Moves it to 60 degrees
	        // then back to 0. Does this at X = 250, Y = 550.
			if (i == 0)
	        	anim = new RotateAnimation(0f,60f, 250, 500);
			else
	        	anim = new RotateAnimation(0f,-60f, 250, 500);
	        // Basically, this makes it accelerate back to zero instead of
	        // move in a linear fashion.
			anim.setInterpolator(new AccelerateInterpolator());
			// Sets the duration of the movement for 200 milliseconds
			anim.setDuration(200);
			// Makes the animation repeat once
			anim.setRepeatCount(1);
			// Makes the animation go there, and back again (A Hobbit's Tale by Bilbo Baggins)
			anim.setRepeatMode(Animation.REVERSE);
			
			// if you're wondering why the redundancy, checkLOR() isn't called for shake().
			// This is more for onClick().
	    }
	    
	public void playSound(int[] attack)// magical musics
		{
			try
			{
				mp.release();// clear the cache. Like closing a file.
			}
			catch(Exception e)
			{}
			try
			{
				int i = 0;// wouldn't have it any other way. No really.
				if (voice)// if voice only
	         		i = generator.nextInt(voiceNum);// gets a number from the array that is a vocal sound. Not always applicable (in which case voiceNum is set to the normal array size)
				else// if NOT voice only
					i = generator.nextInt(attack.length);// gets a number from the array, voice or otherwise
				
				mp = MediaPlayer.create(this, attack[i]);// play normal sounds
				if (mp.isPlaying())// if playing
					mp.stop();// shut up
				mp.start();// starts the music
				zelda.clearFocus();// I can't remember what this was for. Probably important.
				if (vibe)
				{
					Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
					vibe.vibrate(200);
				}
			}
	    catch(Exception e){}// lawl @ throw 
		}
		
	public boolean onCreateOptionsMenu(Menu menu)// called when the phone's 'menu' button is pressed
	    {
	    	MenuInflater menuInflater = getMenuInflater();// makes a menuInflater
	    	menuInflater.inflate(R.menu.zelda_menu, menu);// inflates the menu with the zelda_menu .xml
	    	return super.onCreateOptionsMenu(menu);// returns... something.
	    }

	public boolean onOptionsItemSelected(MenuItem item)// when an item is selected
	    {
		try
		{
	    	switch (item.getItemId())// get the item selected
	    	{
		    	
		    	case R.id.settings://if 'Settings'
		    	{
		    		// Takes you to the preferences screen
		    		intent = new Intent(context, ZeldaPreferenceActivity.class);
		    		intent.putExtra("x", back);// this might not be necessary. Too scared to remove.
		    		startActivity(intent);// starts the Intent, in this case the preferences.
		    		break;
		    	}
		    	
		    	// the following gets called if you choose 'weapon'.
		    	// Android makes it so I don't have to show 'weapon'.
		    	// For brevity, I will on only comment 'master'.
		    	
		    	case R.id.sundown:
		    	{
		    		BG = R.drawable.zelda7;
//		    		lay.setBackgroundResource(BG);
		    		setBG(BG);
		    		break;
		    	}
		    	case R.id.MSBG:
		    	{
		    		BG = R.drawable.zelda16;
		    		setBG(BG);
		    		break;
		    	}
		    	case R.id.GL:
		    	{
		    		BG = R.drawable.zelda0;
		    		setBG(BG);
		    		break;
		    	}
		    	case R.id.RL:
		    	{
		    		BG = R.drawable.zelda1;
		    		setBG(BG);
		    		break;
		    	}
		    	case R.id.PL:
		    	{
		    		BG = R.drawable.zelda2;
		    		setBG(BG);
		    		break;
		    	}
		    	case R.id.BL:
		    	{
		    		BG = R.drawable.zelda3;
		    		setBG(BG);
		    		break;
		    	}
		    	case R.id.moon:
		    	{
		    		BG = R.drawable.zelda24;
		    		setBG(BG);
		    		break;
		    	}
		    	case R.id.black:
		    	{
		    		BG = 0;
		    		setBG(BG);
		    		break;
		    	}
		    	
		    	case R.id.master:// if Master Sword
		    	{
		    		intent = new Intent(context, MSActivity.class);// calls Master Sword with context as the calling class
		    		startActivity(intent);// starts the activity
		    		break;
		    	}
		    	case R.id.kokiri:
		    	{
		    		intent = new Intent(context, KokiriActivity.class);
		    		startActivity(intent);
		    		break;
		    	}
		    	case R.id.biggoron:
		    	{
		    		intent = new Intent(context, BiggoronActivity.class);
		    		startActivity(intent);
		    		break;
		    	}
		    	case R.id.dekustick:
		    	{
		    		intent = new Intent(context, DStickActivity.class);
		    		startActivity(intent);
		    		break;
		    	}
		    	case R.id.dekunut:
		    	{
		    		intent = new Intent(context, DNutActivity.class);
		    		startActivity(intent);
		    		break;
		    	}
		    	case R.id.bomb:
		    	{
		    		intent = new Intent(context, BombActivity.class);
		    		startActivity(intent);
		    		break;
		    	}
		    	case R.id.slingshot:
		    	{
		    		intent = new Intent(context, SlingActivity.class);
		    		startActivity(intent);
		    		break;
		    	}
		    	case R.id.boomerang:
		    	{
		    		intent = new Intent(context, BoomerangActivity.class);
		    		startActivity(intent);
		    		break;
		    	}
		    	case R.id.megaton:
		    	{
		    		intent = new Intent(context, HammerActivity.class);
		    		startActivity(intent);
		    		break;
		    	}
		    	case R.id.hookshot:
		    	{
		    		intent = new Intent(context, HookshotActivity.class);
		    		startActivity(intent);
		    		break;
		    	}
		    	case R.id.bow:
		    	{
		    		intent = new Intent(context, BowActivity.class);
		    		startActivity(intent);
		    		break;
		    	}
	    	}
		}
		catch (Exception e)
		{
			Toast.makeText(context, "Something broke... Sorry about that.", Toast.LENGTH_SHORT).show();
		}
	    	return super.onOptionsItemSelected(item);// yep.
	    }
		
	protected void onSaveInstanceState(Bundle outState)// when it gets closed
		{		
			outState.putBoolean("lefty", lefty);// puts lefty  in memory
			outState.putInt("BG", BG);
			outState.putBoolean("voice", voice);// puts voice  in memory
			outState.putBoolean("info", info);//   puts info   in memory
			outState.putBoolean("vibe", vibe);
			outState.putInt("update", update);//   puts update in memory
			outState.putString("sensText", sensText);// puts sensText in memory
			super.onSaveInstanceState(outState);// saves the state
		}
		
	public void onPause()// when it's paused
		{
			started = false;// don't let it shake!
			super.onPause();
				try
				{
					mp.release();// clear mp
					charge.release();// clear charge
				}
				catch(Exception e){}
				final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
				if (BG != -1)
		        {
		        	Editor editor = preferences.edit();
		        	editor.putInt("BG", BG);
		        	editor.commit();
		        }
		}
		
	public boolean onKeyDown(int keyCode, KeyEvent event)// without this, the app goes through each screen ever called.
	{    
		if (keyCode == KeyEvent.KEYCODE_BACK)// if user presses back button
		{        
			moveTaskToBack(true);// sends Master Sword to the background
			return true;// returns... true
			}   
		return super.onKeyDown(keyCode, event);// yeah.
	}

	public static Intent returnPref(Context preference)// called in PreferenceActivity to get the returning class
	{
		if      (back.equals("DSA"))// if 'Deku Stick Activity'
			intent = new Intent(preference, DStickActivity.class);// go to 'Deku Stick Activity'
		else if (back.equals("DNA"))
			intent = new Intent(preference, DNutActivity.class);
		else if (back.equals("BOMB"))
			intent = new Intent(preference, BombActivity.class);
		else if (back.equals("SLING"))
			intent = new Intent(preference, SlingActivity.class);
		else if (back.equals("BOOM"))
			intent = new Intent(preference, BoomerangActivity.class);
		else if (back.equals("HA"))
			intent = new Intent(preference, HammerActivity.class);
		else if (back.equals("HOOK"))
			intent = new Intent(preference, HookshotActivity.class);
		else if (back.equals("BOW"))
			intent = new Intent(preference, BowActivity.class);
		else if (back.equals("KA"))
			intent = new Intent(preference, KokiriActivity.class);
		else if (back.equals("MSA"))
			intent = new Intent(preference, MSActivity.class);
		else if (back.equals("BIG"))
			intent = new Intent(preference, BiggoronActivity.class);
		else
			intent = new Intent(preference, MSActivity.class);

		
		return intent;// return the newly made intent
	}
	
	public void setBG(int BG)
	{
		lay = (LinearLayout)findViewById(R.id.main);
		if (BG != -1 && lay != null)
			lay.setBackgroundResource(BG);
//		else
//			Toast.makeText(this, "The background break'd...", Toast.LENGTH_SHORT).show();
	}
}
// EOF!