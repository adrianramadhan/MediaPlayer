package com.example.mediaplayer;
//lagu

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;



public class MainActivity2 extends AppCompatActivity {

    MediaPlayer mediaPlayer;
    TextView tvNama;
    ImageView imageView;
    //array songs
    int[] songs = {R.raw.somebodypleasure, R.raw.itsonlyme};
    //array images
    int[] images = {R.drawable.somebodypleasure, R.drawable.kalebj};
    String[] songNames = {"Somebody Pleasute", "It's Only Me"};
    int currentSongIndex = 0;
    Button btPlay, btPause, btStop, btNext, btPrevious, btDashboard;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main2), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        mediaPlayer=null;
        btPlay=findViewById(R.id.bt_play);
        btPause=findViewById(R.id.bt_pause);
        btStop=findViewById(R.id.bt_stop);
        btNext=findViewById(R.id.btNext);
        btPrevious=findViewById(R.id.btPrevious);
        btDashboard=findViewById(R.id.btdashboard);
        tvNama=findViewById(R.id.tvNama);
        imageView = findViewById(R.id.imageView);

        btPlay.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {playMusic();}
        });
        btPause.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {pauseMusic();}
        });
        btStop.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {stopMusic();}
        });
        btNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {nextMusic();}
        });
        btPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {previousMusic();}
        });
        btDashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity2.this, DashboardActivity.class);
                startActivity(intent);
                finish();
            }
        });

        updateImageView();
        playMusic();
        updateSongName();
    }
    @Override
    protected void onStop(){
        super.onStop();
        stopMusic();
    }

    void playMusic() {
        if (mediaPlayer != null) {
            mediaPlayer.reset();
            mediaPlayer.release();
        }
        mediaPlayer = MediaPlayer.create(this, songs[currentSongIndex]);
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) { nextMusic(); }
        });
        mediaPlayer.start();
        tvNama.setText("Playing song " + (currentSongIndex + 1));
    }

    void pauseMusic(){
        if(mediaPlayer!=null){
            mediaPlayer.pause();
        }
    }

    void stopMusic(){
        if(mediaPlayer!=null){
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;

        }
    }

    void nextMusic(){
        stopMusic();
        currentSongIndex = (currentSongIndex + 1) % songs.length; // Berpindah ke lagu berikutnya, kembali ke awal jika sudah di akhir daftar
        playMusic();
        updateImageView();
        updateSongName();
    }

    void previousMusic(){
        stopMusic();
        currentSongIndex = (currentSongIndex - 1 + songs.length) % songs.length; // Berpindah ke lagu sebelumnya, ke akhir daftar jika sudah di awal
        playMusic();
        updateImageView();
        updateSongName();
    }

    void updateImageView() {
        imageView.setImageResource(images[currentSongIndex]);
    }
    void updateSongName() {
        tvNama.setText(songNames[currentSongIndex]);
    }


}
