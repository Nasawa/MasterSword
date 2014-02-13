package com.anigeek.mastersword;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import com.google.ads.AdRequest;
import com.google.ads.AdView;

public class DStickActivity extends BaseActivity 
{
	public void onCreate(Bundle savedInstanceState)// Basically main
	{
        super.onCreate(savedInstanceState);// gets the saved state
        setContentView(R.layout.dstick);// gets the .xml used to make the layout
        zelda = (ImageView)findViewById(R.id.dStick);
        back = "DSA";
        startup();
        
        AdView adView = (AdView)this.findViewById(R.id.adds);     
        adView.loadAd(new AdRequest());
        
        try
        {
	        // I had to MANUALLY assign this array. How mean.
	        final int[] init =
	        {
	        	R.raw.kidvoice0,
	        	R.raw.kidvoice1,
	        	R.raw.kidvoice2,
	        	R.raw.kidvoice3,
	        	R.raw.adultvoice0,
	        	R.raw.adultvoice1,
	        	R.raw.adultvoice2,
	        	R.raw.adultvoice3,
	        	R.raw.adultvoice4,
	        	R.raw.adultvoice5,
	        	R.raw.swing0,
	        	R.raw.swing1,
	        	R.raw.clash0,
	        	R.raw.clash1,
	        	R.raw.clash2,
	        	R.raw.clash3,
	        	R.raw.clash4
	        };
	        
	        attack = init;
	        voiceNum = 10;
	        
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
}
