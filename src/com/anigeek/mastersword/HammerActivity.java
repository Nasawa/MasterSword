package com.anigeek.mastersword;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import com.google.ads.AdRequest;
import com.google.ads.AdView;

public class HammerActivity extends BaseActivity 
{
	public void onCreate(Bundle savedInstanceState)// Basically main
	{
        super.onCreate(savedInstanceState);// gets the saved state
        setContentView(R.layout.hammer);// gets the .xml used to make the layout
        zelda = (ImageView)findViewById(R.id.hammer);
        back = "HA";
        startup();
        
        AdView adView = (AdView)this.findViewById(R.id.adds);     
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
	        	R.raw.metalclash0,
	        	R.raw.metalclash1,
	        	R.raw.metalclash2,
	        	R.raw.metalclash3,
	        	R.raw.metalclash4,
	        	R.raw.metalclash5,
	        	R.raw.metalclash6,
	        	R.raw.metalclash7,
	        	R.raw.hammer0,
	        	R.raw.hammer1
	        };
	        
	        attack = init;
	        voiceNum = 6;
	        
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

			// Normal click. Nothing special.
	        zelda.setOnClickListener(new OnClickListener()
	        {
				public void onClick(View v) 
				{
					checkLOR();
					anim.reset();// Just in case
					playSound(attack);// plays the sounds
					zelda.startAnimation(anim);// starts the swing
	        	}
	        });
	       }
        catch(Exception e){}// lawl @ throw
}
	
	public void checkLOR()// check if it should swing left or right
    {
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
}
