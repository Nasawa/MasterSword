package com.anigeek.mastersword;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.ads.AdRequest;
import com.google.ads.AdView;

public class BombActivity extends BaseActivity 
{
	static ImageView bomblit = null;
	static ImageView boom = null;
	static int alpha = 255;
	private final static Handler handler = new Handler();
//	static boolean kaboom = false;
	static MediaPlayer x = null;
	
	public void onCreate(Bundle savedInstanceState)// Basically main
	{
        super.onCreate(savedInstanceState);// gets the saved state
        setContentView(R.layout.bomb);// gets the .xml used to make the layout
        zelda = (ImageView)findViewById(R.id.bomb);
        bomblit = (ImageView)findViewById(R.id.bomblit);
        boom = (ImageView)findViewById(R.id.boom);
        back = "BOMB";
        startup();
        
        AdView adView = (AdView)this.findViewById(R.id.adds);     
        adView.loadAd(new AdRequest());
        
        ShakeActivity.FORCE_THRESHOLD = 999999;
        
        try
        {
       		final int[] light =
       		{
      			R.raw.bomblit
        	};
       		
//       		final int[] run =
//       		{
//       			R.raw.boom	
//       		};
	        voiceNum = 1;

			// Normal click. Nothing special.
	        zelda.setOnClickListener(new OnClickListener()
	        {
				public void onClick(View v) 
				{
					playSound(light);
					handler.removeCallbacks(flash);
					alpha = 255;
					boom.setAlpha(alpha);
					boom.setVisibility(ImageView.GONE);
					zelda.setVisibility(ImageView.GONE);
					bomblit.setVisibility(ImageView.VISIBLE);
					mp.setOnCompletionListener(new OnCompletionListener()
					{
						public void onCompletion(MediaPlayer mp) 
						{
							try
							{
								bomblit.setVisibility(ImageView.GONE);
								zelda.setVisibility(ImageView.VISIBLE);
								boom.setVisibility(ImageView.VISIBLE);
								x = MediaPlayer.create(getApplicationContext(), R.raw.boom);
								x.start();
								handler.post(flash);
							}
							catch(Exception e)
							{
								Toast.makeText(context, "Something broke... sorry about that.", Toast.LENGTH_SHORT).show();
							}
						}
					});
	        	}
	        });
	        
	        
	        
//	        x.setOnCompletionListener(new OnCompletionListener()
//			{
//				public void onCompletion(MediaPlayer mp)
//				{
//					handler.post(flash);
//				}
//			});
	       }
        catch(Exception e){}// lawl @ throw
//        finally
//        {
//        	boom.setVisibility(ImageView.GONE);
//			zelda.setVisibility(ImageView.VISIBLE);
//        }
	}
	
	private final static Runnable flash = new Runnable() 
	{
	    public void run() 
	    {
	        flash();
	    }
	};
	
	public void shake(){}
	
	public static void flash()
	{
		if (alpha != 0)
    	{
	    	alpha = alpha - 5;
	    	boom.setAlpha(alpha);
	    	handler.post(flash);
    	}
		else
		{
			alpha = 255;
//			zelda.setVisibility(ImageView.VISIBLE);
			boom.setVisibility(ImageView.GONE);
		}
	}
}
