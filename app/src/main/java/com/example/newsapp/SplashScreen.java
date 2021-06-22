package com.example.newsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;


public class SplashScreen extends AppCompatActivity {

    private static int SPLASH_TIMEOUT=4000;

    TextView s,b_text;
    //Animations
    Animation bottomAnim,bounceAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

     //   getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.splash_sceen);

        bottomAnim= AnimationUtils.loadAnimation(this,R.anim.button_animation);

        bounceAnim=AnimationUtils.loadAnimation(this,R.anim.bounce_animation);


        s=findViewById(R.id.s);
        b_text=findViewById(R.id.b_text);

        b_text.setAnimation(bottomAnim);
        myBounceInterpolator interpolator = new myBounceInterpolator(0.5, 20);
        bounceAnim.setInterpolator(interpolator);
          s.setAnimation(bounceAnim);

        //Splash Screen

        //time delay 5 sec
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            Intent intent=new Intent(SplashScreen.this, SecondActivity.class);
            startActivity(intent);
            finish();
        },SPLASH_TIMEOUT);

    }
}