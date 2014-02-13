package com.anigeek.mastersword;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.google.ads.AdRequest;
import com.google.ads.AdView;

public class DNutActivity extends BaseActivity 
{
	private final static Handler handler = new Handler();
	private static int alpha = 255;
	private static ImageView white = null;
	
	public void onCreate(Bundle savedInstanceState)// Basically main
	{
        super.onCreate(savedInstanceState);// gets the saved state
        setContentView(R.layout.dnut);// gets the .xml used to make the layout
        zelda = (ImageView)findViewById(R.id.dnut);
        white = (ImageView)findViewById(R.id.white);
        white.setVisibility(ImageView.GONE);
        back = "DNA";
        startup();
        
        AdView adView = (AdView)this.findViewById(R.id.adds);     
        adView.loadAd(new AdRequest());
        
        ShakeActivity.FORCE_THRESHOLD = 999999;
        
        try
        {
	        // I had to MANUALLY assign this array. How mean.
        	
        	final int[] init =
        	{
        			R.raw.nut0,
        			R.raw.nut1
        	};
        	attack = init;
	        voiceNum = 2;

			// Normal click. Nothing special.
	        zelda.setOnClickListener(new OnClickListener()
	        {
				public void onClick(View v) 
				{
					playSound(attack);// plays the sound
//					zelda.setVisibility(ImageView.GONE);
					white.setVisibility(ImageView.VISIBLE);
					alpha = 255;
					handler.post(flash);
	        	}
	        });
	       }
        catch(Exception e){}// lawl @ throw
	}
	
	private final static Runnable flash = new Runnable() 
	{
	    public void run() 
	    {
	        flash();
	    }
	};
	
	public static void flash()
	{
		if (alpha != 0)
    	{
	    	alpha = alpha - 15;
	    	white.setAlpha(alpha);
	    	handler.post(flash);
    	}
		else
		{
			alpha = 255;
			zelda.setVisibility(ImageView.VISIBLE);
			white.setVisibility(ImageView.GONE);
		}
	}
}
