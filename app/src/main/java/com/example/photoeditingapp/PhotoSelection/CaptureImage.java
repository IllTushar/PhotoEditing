package com.example.photoeditingapp.PhotoSelection;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.SparseArray;
import android.view.View;
import android.widget.TextView;

import com.example.photoeditingapp.Assets.Toolbars;
import com.example.photoeditingapp.Assets.Utils;
import com.example.photoeditingapp.R;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.IOException;

public class CaptureImage extends AppCompatActivity {
    FloatingActionButton camera;
    TextView textView;
    Toolbar toolbar;
    Toolbars toolbars;
    Utils utils;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    Bitmap bitmap;

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
    }

    private void UiFunction() {
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CropImage.activity().setGuidelines(CropImageView.Guidelines.ON).start(CaptureImage.this);
                dispatchTakePictureIntent();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult activityResult = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = activityResult.getUri();
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), resultUri);
                    getTextFromImage(bitmap);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
        }
    }

    private void getTextFromImage(Bitmap bitmap) {
        // Create a TextRecognizer instance
        TextRecognizer recognizer = new TextRecognizer.Builder(CaptureImage.this).build();

        // Check if the TextRecognizer is operational
        if (!recognizer.isOperational()) {
            // Handle the case where TextRecognizer is not operational
            utils.toast(CaptureImage.this, "Error: TextRecognizer is not operational");
        } else {
            // Create a Frame from the Bitmap
            Frame frame = new Frame.Builder().setBitmap(bitmap).build();

            // Use the TextRecognizer to detect text in the Frame
            SparseArray<TextBlock> textBlocks = recognizer.detect(frame);

            // Check if any text blocks were detected
            if (textBlocks.size() == 0) {
                // Handle the case where no text was detected
                utils.toast(CaptureImage.this, "No text detected");
            } else {
                // Iterate through the detected text blocks
                StringBuilder text = new StringBuilder();
                for (int i = 0; i < textBlocks.size(); i++) {
                    TextBlock textBlock = textBlocks.valueAt(i);
                    // Append the text from each text block to the StringBuilder
                    text.append(textBlock.getValue());
                    text.append("\n"); // Add a newline character between text blocks
                }
                // Display or process the extracted text
                // For example, you can display it in a TextView or perform further processing
                // For demonstration purposes, we'll just display it as a toast message
               textView.setText(text.toString());
            }
        }
    }

}