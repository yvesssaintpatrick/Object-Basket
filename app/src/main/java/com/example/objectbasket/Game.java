package com.example.objectbasket;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowMetrics;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import androidx.appcompat.app.AppCompatActivity;
import android.media.MediaPlayer;
import android.widget.TextView;
import android.view.Gravity;
import android.util.DisplayMetrics;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


public class Game extends AppCompatActivity {
    private ImageView playerView;
    private Random objectTypeSelect = new Random();
    private SharedPreferences themePreferences;
    private SharedPreferences basketPreferences;
    private SharedPreferences objectPreferences;
    private boolean isMusicOn = true;
    private boolean paused = false;
    MediaPlayer mediaPlayer;

    int[] tracks = {R.raw.bgm1, R.raw.bgm2,R.raw.bgm3,R.raw.bgm4};
    int currentTrackIndex = 0;
    int playerScore = 0;
    private Bucket player;
    int width, height;//screen width/height

    float density;//pixel density

    boolean gameLost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        this.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        //////////////////////////////////////////////////////////Static Object Initialization
        //media player initiation
        initializeMedia();

        //Most of the cosmetic initialization
        setBackgroundAndBucket();

        //Initializing screen-dependent vars
        WindowMetrics display = getWindowManager().getCurrentWindowMetrics();
        width = display.getBounds().width();
        height = display.getBounds().height();

        density = this.getResources().getDisplayMetrics().density;
        //////////////////////////////////////////////////////////Static Object Initialization
        GameObject[] objectSet = objectSetup();

        //Initialization of pause button
        pausingInitialization(objectSet);

        //Game status initialization in case it doesn't reset on tab in
        gameLost = false;

        //Starting the objects' mechanics
        objectFallAndCollide(objectSet);
    }

    float x,dx;

    @Override
    public boolean onTouchEvent(MotionEvent event) {//In charge of moving basket

        if (event.getAction() == MotionEvent.ACTION_DOWN && (paused == false)){
            x = event.getX();
            player.setHitx(playerView.getMeasuredWidth()/2);
            player.setHity(playerView.getMeasuredHeight());
        }

        if(event.getAction() == MotionEvent.ACTION_MOVE && paused == false) {
            if (gameLost == false) {
                dx = event.getX() - (x);
                playerView.setX(playerView.getX() + dx);
                player.setPosx(playerView.getX());
                x = event.getX();
                if (playerView.getX() <= 0) {
                    playerView.setX(0);
                    player.setPosx(0);
                } else if (playerView.getX() + 100 * density >= width) {
                    playerView.setX(width - 100 * density);
                    player.setPosx(width - 100 * density);
                }

            }
        }
        return super.onTouchEvent(event);
    }

    public void initializeMedia() { //Setting up field variables and music
        mediaPlayer = MediaPlayer.create(this, tracks[currentTrackIndex]);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();

        themePreferences = getSharedPreferences("theme_prefs", MODE_PRIVATE);
        basketPreferences = getSharedPreferences("basket_prefs", MODE_PRIVATE);
        objectPreferences = getSharedPreferences("object_prefs", MODE_PRIVATE);
    }

    public void setBackgroundAndBucket() {
        // Retrieve selected theme and set background
        int selectedTheme = themePreferences.getInt("selected_theme", R.drawable.default_theme);
        ImageView background = findViewById(R.id.background);
        background.setImageResource(selectedTheme);

        // Retrieve selected basket and set the image
        int selectedBasket = basketPreferences.getInt("selected_basket", R.drawable.default_basket);
        playerView = findViewById(R.id.bucket);
        player = new Bucket(playerView.getX(), playerView.getY());
        playerView.setImageResource(selectedBasket);
    }

    public void pausingInitialization(GameObject[] objectSet) {
        ImageView pauseButton = findViewById(R.id.pause);
        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(gameLost == false) {
                    showPauseMenu(objectSet);
                }
            }
        });
    }

    public GameObject[] objectSetup () {
        int[] speedOptions = {20, 30, 50};
        int[] sizeOptions = {50, 40, 75};


        int selectedObjectCategory = objectPreferences.getInt("selected_object_category", 0);

        ImageView object1View = findViewById(R.id.object1);
        ImageView object2View = findViewById(R.id.object2);
        ImageView object3View = findViewById(R.id.object3);
        ImageView object4View = findViewById(R.id.object4);
        ImageView object5View = findViewById(R.id.object5);
        ImageView object6View = findViewById(R.id.object6);
        ImageView object7View = findViewById(R.id.object7);
        ImageView object8View = findViewById(R.id.object8);
        ImageView object9View = findViewById(R.id.object9);
        ImageView object10View = findViewById(R.id.object10);
        ImageView[] objectViews = {object1View, object2View, object3View, object4View, object5View,
                object6View, object7View, object8View, object9View, object10View};

        //ImageView[] objectViews = {object1View};
        GameObject[] objectSet = new GameObject[10];
        for (int i = 0; i<10; i++) {
            int objectType = objectTypeSelect.nextInt(300) % 3;
            objectSet[i] = new GameObject(sizeOptions[objectType], speedOptions[objectType],
                    objectViews[i], width, density);


        }
        switch (selectedObjectCategory) {
            case 1:
                object1View.setImageResource(R.drawable.sport1);
                object2View.setImageResource(R.drawable.sport2);
                object3View.setImageResource(R.drawable.sport3);
                object4View.setImageResource(R.drawable.sport4);
                object5View.setImageResource(R.drawable.sport5);
                object6View.setImageResource(R.drawable.sport6);
                object7View.setImageResource(R.drawable.sport7);
                object8View.setImageResource(R.drawable.sport8);
                object9View.setImageResource(R.drawable.sport9);
                object10View.setImageResource(R.drawable.sport10);
                break;
            case 2:
                object1View.setImageResource(R.drawable.item1);
                object2View.setImageResource(R.drawable.item2);
                object3View.setImageResource(R.drawable.item3);
                object4View.setImageResource(R.drawable.item4);
                object5View.setImageResource(R.drawable.item5);
                object6View.setImageResource(R.drawable.item6);
                object7View.setImageResource(R.drawable.item7);
                object8View.setImageResource(R.drawable.item8);
                object9View.setImageResource(R.drawable.item9);
                object10View.setImageResource(R.drawable.item10);
                break;
            case 3:
                object1View.setImageResource(R.drawable.fruit1);
                object2View.setImageResource(R.drawable.fruit2);
                object3View.setImageResource(R.drawable.fruit3);
                object4View.setImageResource(R.drawable.fruit4);
                object5View.setImageResource(R.drawable.fruit5);
                object6View.setImageResource(R.drawable.fruit6);
                object7View.setImageResource(R.drawable.fruit7);
                object8View.setImageResource(R.drawable.fruit8);
                object9View.setImageResource(R.drawable.fruit9);
                object10View.setImageResource(R.drawable.fruit10);
                break;
            default:
                object1View.setImageResource(R.drawable.fruit9);
                object2View.setImageResource(R.drawable.item7);
                object3View.setImageResource(R.drawable.sport8);
                object4View.setImageResource(R.drawable.fruit4);
                object5View.setImageResource(R.drawable.sport5);
                object6View.setImageResource(R.drawable.item4);
                object7View.setImageResource(R.drawable.item9);
                object8View.setImageResource(R.drawable.fruit10);
                object9View.setImageResource(R.drawable.sport2);
                object10View.setImageResource(R.drawable.sport6);
                break;
        }
        return objectSet;
    }

    public void objectFallAndCollide(GameObject[] objectSet) {
        int timer = 0;
        for (GameObject currentObject : objectSet) {
            currentObject.setObjectViewSize((int) (currentObject.getSize() * density));
            currentObject.animateDown(timer, height-(int)(45*density));
            timer += 1000;
            Timer yTracker = new Timer();
            int finalTimer = timer;
            TimerTask yTask = new TimerTask() {
                @Override
                public void run() {// Detects collisions
                    collision(currentObject, player);
                    checkForLoss(currentObject, objectSet);
                    if (gameLost == true) {
                        yTracker.cancel();
                    }
                }//Timer run()
            };
            yTracker.schedule(yTask, timer, 30);
        }
    }

    public void collision(GameObject currentObject, Bucket player){
        if(currentObject.objectView.getY() >= height - (player.getHity()+100)
                && currentObject.objectView.getY() <= height //y bounds
                && ((currentObject.objectView.getX() <= player.getPosx()
                + player.hitx + (currentObject.getSize() * density / 2))
                && (currentObject.objectView.getX() >= player.getPosx()
                - player.hitx - (currentObject.getSize() * density / 2))) //x bounds
                && (currentObject.isCollected == false) //Not already collected this cycle
                && (gameLost == false)){ //Safeguard for when the game lost
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    currentObject.isCollected = true;
                    currentObject.objectView.setVisibility(View.INVISIBLE);
                    addScore();
                }
            });//runOnUiThread
        }//If statement
    }

    public void checkForLoss(GameObject currentObject, GameObject[] objectSet) {
        if ((currentObject.isCollected == false ) //Object not collected in time
                && (currentObject.objectView.getY() > height)
                && (currentObject.objectView.getY() < height*1.5)) { //Avoid false positives from onRepeat
            gameLost = true;
            onLoss(objectSet);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    showGameOver();
                }
            });//runOnUiThread

        }
    }

    public void addScore(){
        playerScore += 10;
        TextView textView = (TextView) findViewById(R.id.score);
        textView.setText("Score: " + playerScore);
    }//AddScore()

    private void showPauseMenu(GameObject[] objectsPause) {
        View popupView = getLayoutInflater().inflate(R.layout.pause, null);
        int width = (int) (270*density);
        int height = (int) (285*density);
        boolean focusable = false;
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);
        Button resumeButton = popupView.findViewById(R.id.resumeButton);
        paused = true;
        for (GameObject currentObject : objectsPause) {//Stops all objects
            currentObject.object.pause();
        }
        resumeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                paused = false;
                for (GameObject currentObject : objectsPause) {//Resumes all objects
                    currentObject.object.resume();
                }
            }
        });

        ImageView musicButton = popupView.findViewById(R.id.music_off);
        musicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleMusicState();
                toggleMusicButtonImage(musicButton);
            }
        });

        Button mainMenu = popupView.findViewById(R.id.menuButton);
        mainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                    mediaPlayer.release();
                    mediaPlayer = null;
                }
                Intent intent = new Intent(Game.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        View parentLayout = findViewById(android.R.id.content);
int popupWidth = popupWindow.getWidth();
        int popupHeight = popupWindow.getHeight();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int screenWidth = displayMetrics.widthPixels;
        int screenHeight = displayMetrics.heightPixels;


        int centerX = (screenWidth - popupWidth) / 2;
        int centerY = (screenHeight - popupHeight) / 2;


        popupWindow.showAtLocation(parentLayout, Gravity.NO_GRAVITY, centerX, centerY);
    }

    private void showGameOver() {
        View popupView = getLayoutInflater().inflate(R.layout.game_over, null);
        int width = (int) (270*density);
        int height = (int) (285*density);
        boolean focusable = false;
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);
        paused = true;
        TextView textView = popupView.findViewById(R.id.endScore);
        textView.setText("Score: " + playerScore);
        Button mainMenu = popupView.findViewById(R.id.menuButtonEnd);
        mainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                    mediaPlayer.release();
                    mediaPlayer = null;
                }
                Intent intent = new Intent(Game.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        View parentLayout = findViewById(android.R.id.content);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int screenWidth = displayMetrics.widthPixels;
        int screenHeight = displayMetrics.heightPixels;
        int popupWidth = popupWindow.getWidth();
        int popupHeight = popupWindow.getHeight();

        int centerX = (screenWidth - popupWidth) / 2;
        int centerY = (screenHeight - popupHeight) / 2;


        popupWindow.showAtLocation(parentLayout, Gravity.NO_GRAVITY, centerX, centerY);
    }

    private void toggleMusicState() {
        if (isMusicOn) {
            if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
            }
            isMusicOn = false;
        } else {
            if (mediaPlayer != null) {
                mediaPlayer.start();
            }
            isMusicOn = true;
        }
    }


    private void toggleMusicButtonImage(ImageView musicButton) {
        if (isMusicOn) {
            musicButton.setImageResource(R.drawable.music_off);
        } else {
            musicButton.setImageResource(R.drawable.music_on);
        }
    }

    protected void onLoss(GameObject[] objects) {
        for (GameObject object: objects) {
            object.object.pause();
        }
    }


}
