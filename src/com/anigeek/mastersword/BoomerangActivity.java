package com.anigeek.mastersword;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.ads.AdRequest;
import com.google.ads.AdView;

public class BoomerangActivity extends BaseActivity 
{
	static int[] launcher;
	public void onCreate(Bundle savedInstanceState)// Basically main
	{
		try
		{
	        super.onCreate(savedInstanceState);// gets the saved state
	        setContentView(R.layout.boomerang);// gets the .xml used to make the layout
	        zelda = (ImageView)findViewById(R.id.boomerang);
	        back = "BOOM";
	        startup();
	        
	        AdView adView = (AdView)this.findViewById(R.id.adds);     
	        adView.loadAd(new AdRequest());
	        
	        try
	        {
		        // I had to MANUALLY assign this array. How mean.
	        	final int[] init =
	       		{
	      			R.raw.boomerangcatch
	        	};
	        	attack = init;
		        voiceNum = 1;
		        
		        final int[] launch =
		        {
		        	R.raw.boomerangfly
		        };
		        launcher = launch;
		        
		        mShaker = new ShakeActivity(this);
		        mShaker.setOnShakeListener(new ShakeActivity.OnShakeListener () 
		        {
		          public void onShake()
		          {
						if(lefty)
						{
	    					anim = new RotateAnimation(0f,259f,250,250);// swing to the right
						}
						else
						{
							anim = new RotateAnimation(0f,-259f, 250, 250);// swing to the left
						}
			      		shake();
		          }
		        });
	
				// Normal click. Nothing special.
		        zelda.setOnClickListener(new OnClickListener()
		        {
					public void onClick(View v) 
					{
						if(lefty)
						{
	    					anim = new RotateAnimation(0f,259f,250,250);// swing to the right
						}
						else
						{
							anim = new RotateAnimation(0f,-259f, 250, 250);// swing to the left
						}
			      		shake();
		        	}
		        });
		       }
	        catch(Exception e){}// lawl @ throw
		}
		catch (Exception e)
		{
			Toast.makeText(context, "Something broke... sorry about that.", Toast.LENGTH_SHORT).show();
		}
	}
	
	public void shake()
    {
		try
		{
			if (started)
			{
		    	anim.setInterpolator(new AccelerateInterpolator());
		  		anim.setDuration(150);
		  		anim.setRepeatCount(Animation.INFINITE);
		  		try
				{
		    		mp.release();// breaks otherwise
		    		charge.release();// same as above
					charge.stop();// break the noise!
				}
				catch (Exception e){}// only calls an exception the first time. No biggie; throw it!
				anim.reset();// Just in case
				playSound(launcher);
				zelda.startAnimation(anim);// starts the swing
				mp.setOnCompletionListener(new OnCompletionListener()
				{
	
					@Override
					public void onCompletion(MediaPlayer mp) 
					{
						anim.reset();
						anim.cancel();
						playSound(attack);// plays the sounds
					}
					
				});
			}
		}
		catch(Exception e)
		{
			Toast.makeText(context, "Something broke... sorry about that.", Toast.LENGTH_SHORT).show();
		}
    }
}
