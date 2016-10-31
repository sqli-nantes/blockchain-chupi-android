package com.example.joel.automotive;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

/**
 * Created by alb on 21/10/16
 * Splashscreen pour demarrer l'appli Choupette
 */
public class SplashScreen extends Activity {

    /** Délais attente **/
    private final int SPLASH_DISPLAY_LENGTH = 500;

    /** Appel à la premiere création d'activité. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.splashscreen);

        /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/
        boolean b = new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                Intent mainIntent = new Intent(SplashScreen.this, MainActivity.class);
                SplashScreen.this.startActivity(mainIntent);
                SplashScreen.this.finish();
                Log.d("test ecran","c'est moi");
            }

        }, SPLASH_DISPLAY_LENGTH);
    }
}
