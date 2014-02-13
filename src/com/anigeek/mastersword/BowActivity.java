package com.anigeek.mastersword;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.ImageView;

import com.google.ads.AdRequest;
import com.google.ads.AdView;

public class BowActivity extends BaseActivity// BowActivity, sublclass of BaseActivity
{
	static ImageView arrow = null;// picture of the drawn bow
	static boolean drawn = false;// boolean for the bow to see if its drawn
	static boolean fire = false;// boolean to make onLongClick work
	static int[] shoot1;// array of shooting sounds
	static int[] hit1;// array of hitting sounds
	public void onCreate(Bundle savedInstanceState)// Basically main
	{
        super.onCreate(savedInstanceState);// gets the saved state
        setContentView(R.layout.bow);// gets the .xml used to make the layout
        zelda = (ImageView)findViewById(R.id.bow);// sets the bow image as zelda
        arrow = (ImageView)findViewById(R.id.bowarrow);// sets the drawn bow image as arrow
        back = "BOW";// sets the return point for preference
        startup();// sets startup variables
        
        AdView adView = (AdView)this.findViewById(R.id.adds);// finds the AdView
        adView.loadAd(new AdRequest());// loads an ad
        
        ShakeActivity.FORCE_THRESHOLD = 999999;// I SHALL NOT SHAKE
        
        drawn = false;// make sure it isn't drawn
        fire = false;// make sure we aren't firing
        try
        {
       		final int[] shoot =// why an array? To fit it into playSound(), of course!
       		{
      			R.raw.arrowshoot// a pew sound for the arrow flying from the bow
        	};
	        voiceNum = 1;// no voice for this; set it to the smallest array value (1)
	        
	        final int[] hit =// same reason; hit sound.
	        {
	        	R.raw.arrowhit// the sound for the arrow hitting the target
	        };
	        
	        final int[] pull =// sound 3
	        {
	        	R.raw.bowpull// the sound of the bow pulling back
	        };
	        
	        shoot1 = shoot;// static-ifies the array
	        hit1 = hit;// same as above

			zelda.setOnLongClickListener(new OnLongClickListener()
			{
				public boolean onLongClick(View v)
				{
					if (!drawn)// if it hasn't been drawn already
					{
						playSound(pull);// pull back
						drawn = true;// set drawn to true
						zelda.setVisibility(ImageView.GONE);// to fake animation, hide the arrowless bow
						arrow.setVisibility(ImageView.VISIBLE);// show the arrowed bow
						fire = true;// set fire to true; trust me, it's necessary.
					}
					return false;// magic. Do not touch.
				}
			});
			
	        zelda.setOnClickListener(new OnClickListener()
	        {
				public void onClick(View v) 
				{
					if (!drawn)// if it in't drawn
						playSound(pull);// play the draw sound
					drawn = true;// and set drawn to true
					zelda.setVisibility(ImageView.GONE);// fake animation
					arrow.setVisibility(ImageView.VISIBLE);// fake animation
						
					if (fire)// if you're firing
						fireArrow();// fire
	        	}
	        });
	        
	        arrow.setOnClickListener(new OnClickListener()// note arrow, not zelda. If zelda is GONE, you can't click it. Have to click arrow instead.
	        {
	        	public void onClick(View v)
	        	{
	        		playSound(shoot);// shoot the arrow
					drawn = false;// make it not drawn
					zelda.setVisibility(ImageView.VISIBLE);// fake animation
					arrow.setVisibility(ImageView.GONE);// fake animation
					mp.setOnCompletionListener(new OnCompletionListener()// when shoot is done
					{

						@Override
						public void onCompletion(MediaPlayer mp) 
						{
							playSound(hit1);// play hit
						}
						
					});
	        	}
	        });
	        
	        Button fire = (Button)findViewById(R.id.fire);
	        fire.setOnClickListener(new OnClickListener()
	        {
	        	public void onClick(View v)
	        	{
	        		arrow.setImageResource(R.drawable.firearrow);
	        		
	        		final int[] hit =// same reason; hit sound.
	    	        {
	    	        	R.raw.hitfire// the sound for the arrow hitting the target
	    	        };
	        		
	        		hit1 = hit;
	        		
	        	}
	        });
	        
	        Button ice = (Button)findViewById(R.id.ice);
	        ice.setOnClickListener(new OnClickListener()
	        {
	        	public void onClick(View v)
	        	{
	        		arrow.setImageResource(R.drawable.icearrow);
	        		
	        		final int[] hit =// same reason; hit sound.
	    	        {
	    	        	R.raw.hitice// the sound for the arrow hitting the target
	    	        };
	        		
	        		hit1 = hit;
	        		
	        	}
	        });
	        
	        Button light = (Button)findViewById(R.id.light);
	        light.setOnClickListener(new OnClickListener()
	        {
	        	public void onClick(View v)
	        	{
	        		arrow.setImageResource(R.drawable.lightarrow);
	        		
	        		final int[] hit =// same reason; hit sound.
	    	        {
	    	        	R.raw.hitlight// the sound for the arrow hitting the target
	    	        };
	        		
	        		hit1 = hit;
	        		
	        	}
	        });
	        
	        Button normal = (Button)findViewById(R.id.normal);
	        normal.setOnClickListener(new OnClickListener()
	        {
	        	public void onClick(View v)
	        	{
	        		arrow.setImageResource(R.drawable.bowarrow);
	        		
	        		final int[] hit =// same reason; hit sound.
	    	        {
	    	        	R.raw.arrowhit// the sound for the arrow hitting the target
	    	        };
	        		
	        		hit1 = hit;
	        		
	        	}
	        });
	        
	       }
        catch(Exception e){}// lawl @ throw
	}
	
	public void fireArrow()// fire the arrow
	{
		playSound(shoot1);// shoot the arrow
		drawn = false;// make it not drawn
		fire = false;// unfire
		zelda.setVisibility(ImageView.VISIBLE);// fake animation
		arrow.setVisibility(ImageView.GONE);// fake animation
		mp.setOnCompletionListener(new OnCompletionListener()// when done shooting
		{

			@Override
			public void onCompletion(MediaPlayer mp) 
			{
				playSound(hit1);// play hit
			}
			
		});
	}
}
