package com.example.objectbasket;

import androidx.appcompat.app.AppCompatActivity;
import android.media.MediaPlayer;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    Button start, cosmetics;
    MediaPlayer mediaPlayer;
    int[] tracks = {R.raw.music_1, R.raw.music_2, R.raw.music_3, R.raw.music_4,
            R.raw.music_5, R.raw.music_6, R.raw.music_7};
    int currentTrackIndex = 0;
    boolean isPaused = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        start = (Button) findViewById(R.id.startButton);//Start button
        cosmetics = (Button) findViewById(R.id.cosmeticsButton);//cosmetics button

        initializeMediaPlayer();

        start.setOnClickListener(new View.OnClickListener() {//listener for setting button
            @Override
            public void onClick(View v) {
                openGame(v);
            }
        });//start
        cosmetics.setOnClickListener(new View.OnClickListener() {//listener for setting button
            @Override
            public void onClick(View v) {
                openCosmetics(v);
            }
        });//cosmetics
        //Onclick Listeners/////////////////////////////////////////////////////
    }

    public void openGame(View view){
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
            isPaused = true;
        }
        Intent intent = new Intent(this, Game.class);
        startActivity(intent);
    }//openGame
    public void openCosmetics(View view){
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
        Intent intent = new Intent(this, Cosmetics.class);
        startActivity(intent);
    }//openCosmetics

    //Activity Switching//////////////////////////

    private void initializeMediaPlayer() {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(this, tracks[currentTrackIndex]);
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    playNextTrack();
                }
            });
            mediaPlayer.start();
        }
    }
    private void playNextTrack() {

        currentTrackIndex++;

        if (currentTrackIndex < tracks.length) {

            mediaPlayer.release();

            mediaPlayer = MediaPlayer.create(this, tracks[currentTrackIndex]);

            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    playNextTrack();
                }
            });

            mediaPlayer.start();
        } else {

            currentTrackIndex = 0;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        initializeMediaPlayer();
    }
    @Override
    protected void onPause() {
        super.onPause();
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            isPaused = true;
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

}