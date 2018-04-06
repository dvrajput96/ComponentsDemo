package com.example.pc.componentsdemo.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.pc.componentsdemo.R;
import com.google.android.gms.internal.im;

public class SplashActivity extends AppCompatActivity {

    private ImageView imageView;
    private SharedPreferences sharedPreferences;

    private int logincheck = 0;

    /**
     * Duration of wait
     **/
    private final int SPLASH_DISPLAY_LENGTH = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

         /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                sharedPreferences = getSharedPreferences(Login.mypreference, Context.MODE_PRIVATE);
                logincheck = sharedPreferences.getInt(Login.LOGIN, 0);
                if (logincheck == 0) {
                    Intent Intent = new Intent(getApplicationContext(), Login.class);
                    SplashActivity.this.startActivity(Intent);
                    SplashActivity.this.finish();
                } else if (logincheck == 1) {
                    Intent Intent = new Intent(getApplicationContext(), AdminLoginActivity.class);
                    SplashActivity.this.startActivity(Intent);
                    SplashActivity.this.finish();
                } else {
                    Intent Intent = new Intent(getApplicationContext(), Login.class);
                    SplashActivity.this.startActivity(Intent);
                    SplashActivity.this.finish();
                }

            }
        }, SPLASH_DISPLAY_LENGTH);


    }
}
