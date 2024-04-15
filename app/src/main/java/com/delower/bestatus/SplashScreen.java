package com.delower.bestatus;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
//import com.example.statusapplication.R;

public class SplashScreen extends AppCompatActivity {

    LottieAnimationView animation_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash_screen);
        animation_view = findViewById(R.id.animation_view);


        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                animation_view.setAnimation(R.raw.animation);
                finish();
                Intent intent = new Intent(SplashScreen.this,MainActivity.class);
                startActivity(intent);
            }
        },4000);

    }
}