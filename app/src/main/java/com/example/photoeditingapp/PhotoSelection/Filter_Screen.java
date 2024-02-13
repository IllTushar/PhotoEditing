package com.example.photoeditingapp.PhotoSelection;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.graphics.Typeface;
import android.os.Bundle;

import com.example.photoeditingapp.R;

import ja.burhanrashid52.photoeditor.PhotoEditor;
import ja.burhanrashid52.photoeditor.PhotoEditorView;
import ja.burhanrashid52.photoeditor.PhotoFilter;

public class Filter_Screen extends AppCompatActivity {
    PhotoEditorView mPhotoEditorView;
    PhotoEditor mPhotoEditor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_screen);
        getIDs();

    }

    private void getIDs() {
        mPhotoEditorView = findViewById(R.id.photoEditorView);
        mPhotoEditorView.getSource().setImageResource(R.drawable.baseline_image_24);

        //loading font from asset
        Typeface mEmojiTypeFace = Typeface.createFromAsset(getAssets(), "emojione-android.ttf");

        mPhotoEditor = new PhotoEditor.Builder(this, mPhotoEditorView)
                .setPinchTextScalable(true)
                .setClipSourceImage(true)
                .setDefaultEmojiTypeface(mEmojiTypeFace)
                .build();
        mPhotoEditor.setFilterEffect(PhotoFilter.BRIGHTNESS);
    }
}