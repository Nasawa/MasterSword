package com.anigeek.mastersword;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.google.ads.AdRequest;
import com.google.ads.AdView;

public class HookshotActivity extends BaseActivity 
{
	static ImageView extend = null;
	public void onCreate(Bundle savedInstanceState)// Basically main
	{
        super.onCreate(savedInstanceState);// gets the saved state
        setContentView(R.layout.hookshot);// gets the .xml used to make the layout
        zelda = (ImageView)findViewById(R.id.hookshot);
        extend = (ImageView)findViewById(R.id.extended);
        back = "HOOK";
        startup();
        
        AdView adView = (AdView)this.findViewById(R.id.adds);     
        adView.loadAd(new AdRequest());
        
        ShakeActivity.FORCE_THRESHOLD = 999999;
        
        try
        {
       		final int[] init =
       		{
      			R.raw.hookshot0,
       			R.raw.hookshot1,
       			R.raw.hookshot2
        	};
        	attack = init;
	        voiceNum = 3;
	        
	        final int[] launch =
	        {
	        	R.raw.hookshotfire
	        };

			// Normal click. Nothing special.
	        zelda.setOnClickListener(new OnClickListener()
	        {
				public void onClick(View v) 
				{
					voiceNum = 1;
					playSound(launch);
					zelda.setVisibility(ImageView.GONE);
					extend.setVisibility(ImageView.VISIBLE);
					mp.setOnCompletionListener(new OnCompletionListener()
					{

						@Override
						public void onCompletion(MediaPlayer mp) 
						{
							zelda.setVisibility(ImageView.VISIBLE);
							extend.setVisibility(ImageView.GONE);
							voiceNum = 3;
							playSound(attack);// plays the sounds
						}
						
					});
	        	}
	        });
	       }
        catch(Exception e){}// lawl @ throw
	}
	
	public void shake(){}
}
