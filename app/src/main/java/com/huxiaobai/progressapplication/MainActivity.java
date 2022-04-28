package com.huxiaobai.progressapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.IntentFilter;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;

import com.huxiaobai.receive.AppWightReceive;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListenMusicView listenMusicView = findViewById(R.id.lmv_loading);
        listenMusicView.setCurrentProgress(0);
        listenMusicView.setMaxProgress(10);
        listenMusicView.start();
        MusicSwingView mMsv = findViewById(R.id.msv_music);
        mMsv.start();
        MusicSwingCompat swingCompat = findViewById(R.id.msc_swing);
        swingCompat.start();
        swingCompat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* AppWightReceive receive = new AppWightReceive();
                IntentFilter intentFilter = new IntentFilter();
                intentFilter.setPriority(1000);
                intentFilter.addAction("android.appwidget.action.APPWIDGET_UPDATE");
                registerReceiver(receive,intentFilter);*/
            }
        });
    }

    @Override
    public void finish() {
        moveTaskToBack(false);
        //  MusicSwingCompat compat = new MusicSwingCompat()
        //   super.finish();
    }
}