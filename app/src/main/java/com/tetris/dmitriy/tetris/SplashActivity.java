package com.tetris.dmitriy.tetris;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;

public class SplashActivity extends Activity {

    private static final int DELAY = 1 * 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent startMainActivityIntent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(startMainActivityIntent);
            }
        }, DELAY);
    }
}
