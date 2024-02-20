package com.example.photoeditingapp.PhotoSelection;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.photoeditingapp.Assets.Toolbars;
import com.example.photoeditingapp.Assets.Utils;
import com.example.photoeditingapp.Data.DataSet;
import com.example.photoeditingapp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextDetector;

import java.util.*;

public class CaptureImage extends AppCompatActivity {
    FloatingActionButton camera, imageDetection;
    TextView textView;
    Toolbar toolbar;
    Toolbars toolbars;
    Utils utils;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    Bitmap imageBitmap;
    ImageView img;
    DataSet imageBitMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture_image);
        getIds();
        toolbars.setToolbarTitle(CaptureImage.this, "Capture Image", toolbar);
        UiFunction();
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    private void getIds() {
        camera = findViewById(R.id.cameraCapture);
        toolbar = findViewById(R.id.toolbars);
        setSupportActionBar(toolbar);
        toolbars = new Toolbars();
        utils = new Utils(CaptureImage.this);
        textView = findViewById(R.id.text2);
        img = findViewById(R.id.imageView);
        imageDetection = findViewById(R.id.imageDetection);
        imageBitMap = new DataSet(CaptureImage.this);
    }

    private void UiFunction() {
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakePictureIntent();
            }
        });
        imageDetection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                detectTxt();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            // on below line we are getting
            // data from our bundles. .
            Bundle extras = data.getExtras();
            imageBitmap = (Bitmap) extras.get("data");
            imageBitMap.setImageBitMap(imageBitmap);
            // below line is to set the
            // image bitmap to our image.
            img.setImageBitmap(imageBitmap);
        }
    }

    private void detectTxt() {
        utils.ProgressDialogShow("Processing...");
        // Check if bitmap is available
        Bitmap bitmap = imageBitMap.getImageBitMap();
        if (bitmap == null) {
            utils.toast(CaptureImage.this, "bitmap null");
            Log.e("detectTxt", "Bitmap is null");
            return;
        }

        // Create FirebaseVisionImage object
        FirebaseVisionImage image = FirebaseVisionImage.fromBitmap(bitmap);

        // Create FirebaseVisionTextDetector object
        FirebaseVisionTextDetector detector = FirebaseVision.getInstance().getVisionTextDetector();

        // Add onSuccessListener and onFailureListener
        detector.detectInImage(image)
                .addOnSuccessListener(new OnSuccessListener<FirebaseVisionText>() {
                    @Override
                    public void onSuccess(FirebaseVisionText firebaseVisionText) {
                        // Call method to process text after extraction
                        processTxt(firebaseVisionText);
                        utils.ProgressDialogDismiss();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        utils.toast(CaptureImage.this, "Failed to detect text from image.");
                    }
                });
    }


    private void processTxt(FirebaseVisionText text) {
        // below line is to create a list of vision blocks which
        // we will get from our firebase vision text.
        List<FirebaseVisionText.Block> blocks = text.getBlocks();

        // checking if the size of the
        // block is not equal to zero.
        if (blocks.size() == 0) {
            // if the size of blocks is zero then we are displaying
            // a toast message as no text detected.
            utils.toast(CaptureImage.this, "No Text");

            return;
        }
        // extracting data from each block using a for loop.
        for (FirebaseVisionText.Block block : text.getBlocks()) {
            // below line is to get text
            // from each block.
            String txt = block.getText();

            // below line is to set our
            // string to our text view.
            textView.setText(txt);
        }
    }
}