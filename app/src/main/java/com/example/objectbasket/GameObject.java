package com.example.objectbasket;


import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.TimeAnimator;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import java.util.Random; //https://www.educative.io/answers/how-to-generate-random-numbers-in-java
import java.util.Timer;
import java.util.TimerTask;

public class GameObject {
    protected int objectSize, width;
    protected int objectSpeed;
    protected Drawable objectSprite;
    protected long objectXVal;
    protected int objectYVal;
    protected ImageView objectView;
    ObjectAnimator object;
    protected boolean isCollected = false;

    public GameObject(int objectSize, int objectSpeed, ImageView objectView, int width, float density) {
        Random xValSet = new Random();
        this.objectSize = objectSize;
        this.objectSpeed = objectSpeed;
        this.objectView = objectView;
        this.objectXVal = xValSet.nextInt(width)-width/2;
        this.objectSprite = this.objectView.getDrawable();
        this.objectView.setX(objectXVal);
        this.objectYVal = 0;
        this.width = width;
    }

     public void animateDown(int time, int fallDistance) {
        //https://www.youtube.com/watch?v=_P_Z5wIxGOc
         Animator.AnimatorListener animationListener = new Animator.AnimatorListener(){

             @Override
             public void onAnimationStart(@NonNull Animator animation) {

             }

             @Override
             public void onAnimationEnd(@NonNull Animator animation) {

             }

             @Override
             public void onAnimationCancel(@NonNull Animator animation) {

             }

             @Override
             public void onAnimationRepeat(@NonNull Animator animation) { //Resetting the object to its original state
                 isCollected = false;
                 objectView.setVisibility(objectView.VISIBLE);
             }
         };//animationListener

         object = ObjectAnimator.ofFloat(objectView,
                 "y", 0, fallDistance*2);
         object.setDuration(200000/objectSpeed); //Using a reasonable objectSpeed this is going to
         //return 1-5 seconds to go down the screen and the same time delay before updating
         object.setStartDelay(time);
         object.setInterpolator(new LinearInterpolator());
         object.setRepeatCount(Animation.INFINITE);
         object.start();
         object.addListener(animationListener);

    }

    public void setObjectViewSize(int size) { //https://stackoverflow.com/questions/10159372/android-view-layout-width-how-to-change-programmatically
        ViewGroup.LayoutParams layoutParams = objectView.getLayoutParams();
        layoutParams.width = size;
        layoutParams.height = size;
        objectView.setLayoutParams(layoutParams);
    }

    public int getSize() { return this.objectSize; }
}
