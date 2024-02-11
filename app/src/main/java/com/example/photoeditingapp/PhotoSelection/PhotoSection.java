package com.example.photoeditingapp.PhotoSelection;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;

import com.example.photoeditingapp.Assets.Toolbars;
import com.example.photoeditingapp.Assets.Utils;
import com.example.photoeditingapp.R;
import com.sanojpunchihewa.glowbutton.GlowButton;

public class PhotoSection extends AppCompatActivity {
    Utils utils;
    Toolbar toolbar;
    Toolbars toolbars;
    GlowButton photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_section);
        getIds();
        utils.statusBar(PhotoSection.this);
        uiFunction();
    }

    private void getIds() {
        utils = new Utils(PhotoSection.this);
        toolbar = findViewById(R.id.toolbar);
        photo = findViewById(R.id.image);
        toolbars = new Toolbars();
        setSupportActionBar(toolbar);
        toolbars.setToolbarTitle("Home Page", toolbar);
    }

    public void uiFunction() {
        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                utils.cameraAndMediaPermissions(PhotoSection.this);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == utils.CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                    grantResults.length > 1 && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                // Permissions granted, proceed with your logic
            } else {
                // Permissions denied, handle accordingly
            }
        }
    }
}