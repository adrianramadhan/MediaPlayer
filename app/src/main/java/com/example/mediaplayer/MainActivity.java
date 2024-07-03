package com.example.mediaplayer;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.MediaController;
import android.widget.VideoView;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    VideoView videoView;
    MediaController mediaController;
    Button btPlay, btStop, btPrevious, btNext, btDashboard;
    TextView tvJudul;

    Uri[] videoUris;
    int currentVideoIndex = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        videoView=findViewById(R.id.vv_vidio);
        btPlay=findViewById(R.id.bt_play);
        btStop=findViewById(R.id.bt_stop);
        btNext = findViewById(R.id.bt_next);
        btPrevious = findViewById(R.id.bt_previous);
        btDashboard= findViewById(R.id.btdashboard);
        tvJudul=findViewById(R.id.tv_judul);

        mediaController= new MediaController(this);
        videoUris = new Uri[] {
                Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.meme),
                Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.meme2),
                Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.meme3)
        };


        btPlay.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                startVideo();
                btStop.setVisibility(View.VISIBLE);
                btPlay.setVisibility(View.GONE);
            }
        });

        btStop.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                stopVideo();
            }
        });
        btNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextVideo();
            }
        });
        btPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                previousVideo();
            }
        });
        btDashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DashboardActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
    void stopVideo(){
        videoView.stopPlayback();
        btStop.setVisibility(View.GONE);
        btPlay.setVisibility(View.VISIBLE);
    }

    void startVideo(){
        videoView.setVideoURI(videoUris[currentVideoIndex]);
        videoView.setMediaController(mediaController);
        mediaController.setAnchorView(videoView);
        videoView.start();
        tvJudul.setText("Video " + (currentVideoIndex + 1));
    }
    void nextVideo() {
        if (currentVideoIndex < videoUris.length - 1) {
            currentVideoIndex++;
        } else {
            currentVideoIndex = 0; // loop back to the first video
        }
        startVideo();
    }
    void previousVideo() {
        if (currentVideoIndex > 0) {
            currentVideoIndex--;
        } else {
            currentVideoIndex = videoUris.length - 1; // loop back to the last video
        }
        startVideo();
    }




}