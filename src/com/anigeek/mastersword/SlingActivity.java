package com.anigeek.mastersword;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.ImageView;

import com.google.ads.AdRequest;
import com.google.ads.AdView;

public class SlingActivity extends BaseActivity 
{
	static ImageView shot = null;
	static boolean drawn = false;
	static boolean fire = false;
	static int[] shoot1;
	static int[] hit1;
	public void onCreate(Bundle savedInstanceState)// Basically main
	{
        super.onCreate(savedInstanceState);// gets the saved state
        setContentView(R.layout.slingshot);// gets the .xml used to make the layout
        zelda = (ImageView)findViewById(R.id.slingshot);
        shot = (ImageView)findViewById(R.id.slingshotdrawn);
        back = "SLING";
        startup();
        
        AdView adView = (AdView)this.findViewById(R.id.adds);     
        adView.loadAd(new AdRequest());
        
        ShakeActivity.FORCE_THRESHOLD = 999999;
        
        drawn = false;
        fire = false;
        
        try
        {
       		final int[] shoot =
       		{
      			R.raw.slingshoot
        	};
	        voiceNum = 1;
	        
	        final int[] hit =
	        {
	        	R.raw.slinghit	
	        };
	        
	        final int[] pull =
	        {
	        	R.raw.slingpull
	        };
	        shoot1 = shoot;
	        hit1 = hit;

			zelda.setOnLongClickListener(new OnLongClickListener()
			{
				public boolean onLongClick(View v)
				{
					if (!drawn)
					{
						playSound(pull);
						drawn = true;
						zelda.setVisibility(ImageView.GONE);
						shot.setVisibility(ImageView.VISIBLE);
						fire = true;
					}
					return false;
				}
			});
	        zelda.setOnClickListener(new OnClickListener()
	        {
				public void onClick(View v) 
				{
					if (!drawn)
						playSound(pull);
					drawn = true;
					zelda.setVisibility(ImageView.GONE);
					shot.setVisibility(ImageView.VISIBLE);
						
					if (fire)
						fireArrow();
	        	}
	        });
	        shot.setOnClickListener(new OnClickListener()
	        {
	        	public void onClick(View v)
	        	{
	        		playSound(shoot);
					drawn = false;
					zelda.setVisibility(ImageView.VISIBLE);
					shot.setVisibility(ImageView.GONE);
					mp.setOnCompletionListener(new OnCompletionListener()
					{

						@Override
						public void onCompletion(MediaPlayer mp) 
						{
							playSound(hit);// plays the sounds
						}
						
					});
	        	}
	        });
	       }
        catch(Exception e){}// lawl @ throw
	}
	
	public void shake(){}
	
	public void fireArrow()
	{
		playSound(shoot1);
		drawn = false;
		fire = false;
		zelda.setVisibility(ImageView.VISIBLE);
		shot.setVisibility(ImageView.GONE);
		mp.setOnCompletionListener(new OnCompletionListener()
		{

			@Override
			public void onCompletion(MediaPlayer mp) 
			{
				playSound(hit1);// plays the sounds
			}
			
		});
	}
}
