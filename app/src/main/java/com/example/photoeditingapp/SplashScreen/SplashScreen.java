package com.example.photoeditingapp.SplashScreen;

import static java.lang.Thread.sleep;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.photoeditingapp.Assets.Utils;
import com.example.photoeditingapp.PhotoSelection.PhotoSection;
import com.example.photoeditingapp.R;

public class SplashScreen extends AppCompatActivity {
    private ImageView homeScreenImage;
    Utils utils;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getIDs();
        Glide.with(SplashScreen.this).load(R.drawable.photo_image).into(homeScreenImage);
        utils.statusBar(SplashScreen.this);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    sleep(5000);
                    utils.Intent(SplashScreen.this, PhotoSection.class);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            }
        });
        thread.start();
    }

    private void getIDs() {
        homeScreenImage = findViewById(R.id.splashScreenImage);
        utils = new Utils(SplashScreen.this);
    }

}