package com.tetris.dmitriy.tetris;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.ActivityMain_NewGameBtn).setOnClickListener(onNewGameBtnClick);
        findViewById(R.id.ActivityMain_SettingsBtn).setOnClickListener(onSettingsBtnClick);
        findViewById(R.id.ActivityMain_RecordsBtn).setOnClickListener(onRecordsBtnClick);
        findViewById(R.id.ActivityMain_ExitBtn).setOnClickListener(onExitBtnClick);
    }

    private View.OnClickListener onNewGameBtnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent startGameIntent = new Intent(MainActivity.this, GameActivity.class);
            startActivity(startGameIntent);
        }
    };

    private View.OnClickListener onSettingsBtnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

        }
    };

    private View.OnClickListener onRecordsBtnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

        }
    };

    private View.OnClickListener onExitBtnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            finish();
        }
    };

    @Override
    protected void onDestroy() {
        android.os.Process.killProcess(android.os.Process.myPid());
        super.onDestroy();
    }
}
