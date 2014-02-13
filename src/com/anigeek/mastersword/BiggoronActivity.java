package com.anigeek.mastersword;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import com.google.ads.AdRequest;
import com.google.ads.AdView;

public class BiggoronActivity extends BaseActivity //implements AdListener
{	
	private final static Handler handler = new Handler();
    public void onCreate(Bundle savedInstanceState)// Basically main
	{
        super.onCreate(savedInstanceState);// gets the saved state
        setContentView(R.layout.biggoron);// gets the .xml used to make the layout
        zelda = (ImageView)findViewById(R.id.biggoron);
        back = "BIG";
        startup();
       
        blarg = false;
        
        AdView adView = (AdView)this.findViewById(R.id.ad);     
        adView.loadAd(new AdRequest());
        
        try
        {
	        // I had to MANUALLY assign this array. How mean.
        	final int[] init =
	        {
	        	R.raw.adultvoice0,
	        	R.raw.adultvoice1,
	        	R.raw.adultvoice2,
	        	R.raw.adultvoice3,
	        	R.raw.adultvoice4,
	        	R.raw.adultvoice5,
	        	R.raw.swing0,
	        	R.raw.swing1,
	        	R.raw.sword0,
	        	R.raw.sword1,
	        	R.raw.sword2,
	        	R.raw.clash0,
	        	R.raw.clash1,
	        	R.raw.clash2,
	        	R.raw.clash3,
	        	R.raw.clash4,
	        	R.raw.metalclash0,
	        	R.raw.metalclash1,
	        	R.raw.metalclash2,
	        	R.raw.metalclash3,
	        	R.raw.metalclash4,
	        	R.raw.metalclash5,
	        	R.raw.metalclash6,
	        	R.raw.metalclash7
	        };
	        voiceNum = 6;
	        
	        attack = init;
	        
	        mShaker = new ShakeActivity(this);
	        mShaker.setOnShakeListener(new ShakeActivity.OnShakeListener () 
	        {
	          public void onShake()
	          {
					if(lefty)
					{
    					anim = new RotateAnimation(0f,60f,250,500);// swing to the right
					}
					else
					{
						anim = new RotateAnimation(0f,-60f, 250, 500);// swing to the left
					}
		      		shake();
	          }
	        });

			// On Long Click
	        zelda.setOnLongClickListener(new OnLongClickListener()
			{
				// A BOOLEAN?!
				// Don't turn this to true. Evar.
				public boolean onLongClick(View v)// onLongClick
				{
					// In a later update, I plan on having a looping charge sound. It's really hard to do.
					
					checkLOR();// check direction
					// Makes the MediaPlayer charge. Basically is used to make the charge for LongPress
			        charge = MediaPlayer.create(context, R.raw.swordcharge);// creates a charge sound.
			        charge.start();// makes cool sounds
			        
			        charge.setOnCompletionListener(new OnCompletionListener()
			        {
						public void onCompletion(MediaPlayer arg0) 
						{
							charge = MediaPlayer.create(context, R.raw.hold);
							charge.setLooping(true);
							charge.start();
						}
			        });
			        
			        
			        blarg = true;
			        blarg2 = false;
			        if (vibe)
			        {
			        	Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
			        	vibe.vibrate(500);
			        }
					return false;  // magic. Do not touch.
					// onLongClick calls onClick after it's done. This is important.
				}
			});
			// Normal click. Nothing special.
	        zelda.setOnClickListener(new OnClickListener()
	        {
				public void onClick(View v) 
				{
					checkLOR();// check direction
					try
					{
			    		mp.release();// breaks otherwise
					}
					catch (Exception e){}// only calls an exception the first time. No biggie; throw it!
					try
					{
						charge.release();// same as above
						charge.stop();// break the noise!
					}
					catch(Exception e){}
					anim.reset();// Just in case
					if (blarg3)
					{
						if (blarg2)// fixes onClick
						{
							playSound(attack);// plays the sounds
							zelda.startAnimation(anim);// starts the swing
						}
						else
						{
							blarg = false;
	//						if (blarg3)
	//						{
								zelda.startAnimation(anim);
								handler.post(swoosh);
	//						}
	//						blarg3 = false;
						}
						blarg2 = true;
					}
					blarg3 = true;
	        	}
	        });
	       }
        catch(Exception e){}// lawl @ throw
}    
    
    public void playSound(int[] attack)// magical musics
	{
		try
		{
			int i = 0;
			if (voice)
         		i = generator.nextInt(voiceNum);// makes him only use vocals
			else
				i = generator.nextInt(attack.length);// makes random numbers from 0 to 21
			
			if (!blarg)// if NO swooshy noises
			{
				mp = MediaPlayer.create(this, attack[i]);// play normal sounds
				if (mp.isPlaying())// if playing
					mp.stop();// shut up
			}
//			else// if YES swooshy noises
//			{
//				blarg = false;
//				handler.post(swoosh);
//			}
			mp.start();// starts the music
			zelda.clearFocus();
			if (vibe)
			{
				Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
	        	vibe.vibrate(200);
			}
		}
    catch(Exception e){}// lawl @ throw 
	}
    
	public void shake()// if you shake
    {
		if (started)// if not locked
		{
	    	anim.setInterpolator(new AccelerateInterpolator());// set interpolator
	  		anim.setDuration(200);// set duration to 200 milliseconds
	  		anim.setRepeatCount(1);// repeats once
	  		anim.setRepeatMode(Animation.REVERSE);// repeats that once in reverese
	  		try
			{
	    		mp.release();// breaks otherwise
			}
			catch (Exception e){}// only calls an exception the first time. No biggie; throw it!
			try
			{
				charge.release();// same as above
				charge.stop();// break the noise!
			}
			catch (Exception e){}
			anim.reset();// Just in case
			if (!blarg)
				playSound(attack);
			else
			{
				blarg3 = false;
				handler.post(swoosh);
			}
			zelda.startAnimation(anim);// starts the swing
		}
    }
	
	private final Runnable swoosh = new Runnable() 
	{
	    public void run() 
	    {
	    	blarg = false;// No moar swoosh
	    	try
	    	{
	    		if (mp.isPlaying())// if playing
					mp.stop();// shut up
	    	}
	    	catch (Exception e){}
			mp = MediaPlayer.create(getApplicationContext(), R.raw.chargedswing);// SWOOSH!
//			if (charge.isPlaying())
//				charge.stop();
			mp.start();
//			blarg3 = true;
			if (vibe)
			{
				Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
	        	vibe.vibrate(200);
			}
			blarg2 = true;
			handler.removeCallbacks(swoosh);
	    }
	};
}