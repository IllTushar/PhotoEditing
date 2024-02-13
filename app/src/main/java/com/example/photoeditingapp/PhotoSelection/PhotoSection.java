package com.example.photoeditingapp.PhotoSelection;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.photoeditingapp.Assets.Toolbars;
import com.example.photoeditingapp.Assets.Utils;
import com.example.photoeditingapp.R;
import com.sanojpunchihewa.glowbutton.GlowButton;

public class PhotoSection extends AppCompatActivity {
    Utils utils;
    Toolbar toolbar;
    Toolbars toolbars;
    GlowButton photo, gallery, filter , textRecognition;
    ImageView filterImage;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_IMAGE_PICK = 2;


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
        filterImage = findViewById(R.id.imageView);
        photo = findViewById(R.id.image);
        gallery = findViewById(R.id.gallery);
        filter = findViewById(R.id.filter);
        toolbars = new Toolbars();
        textRecognition=findViewById(R.id.imageReconiztaion);
        setSupportActionBar(toolbar);
        toolbars.setToolbarTitle(PhotoSection.this,"Generative Ai", toolbar);
    }

    public void uiFunction() {
        Glide.with(PhotoSection.this).load(R.drawable.baseline_image_24).into(filterImage);
        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                utils.cameraAndMediaPermissions(PhotoSection.this);
                //dispatchTakePictureIntent();
            }
        });
        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                utils.cameraAndMediaPermissions(PhotoSection.this);
                openGallery();
            }
        });

        textRecognition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             utils.Intent(PhotoSection.this,CaptureImage.class);
            }
        });
    }



    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_IMAGE_PICK);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {

            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            filterImage.setImageBitmap(imageBitmap);
            filter.setVisibility(View.VISIBLE);

        } else if (requestCode == REQUEST_IMAGE_PICK && resultCode == RESULT_OK) {

            Uri selectedImageUri = data.getData();
            filterImage.setImageURI(selectedImageUri);
            filter.setVisibility(View.VISIBLE);

        } else {
            utils.toast(PhotoSection.this, "Action canceled");
        }
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