package com.example.service;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    MediaPlayer player;
    Button btnPlay, btnStop, btnPlayService, btnStopService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Media");

        anhXa();

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player = MediaPlayer.create(MainActivity.this, Settings.System.DEFAULT_RINGTONE_URI);
                player.setLooping(true);
                player.start();
            }
        });
        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.stop();
            }
        });
        btnPlayService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startService(new Intent(MainActivity.this, MyService.class));
            }
        });
        btnStopService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopService(new Intent(MainActivity.this, MyService.class));
            }
        });
    }

    private void anhXa() {
        btnPlay = (Button) findViewById(R.id.btnPlay);
        btnPlayService = (Button) findViewById(R.id.btnPlayService);
        btnStop = (Button) findViewById(R.id.btnStop);
        btnStopService = (Button) findViewById(R.id.btnStopService);
    }
}
