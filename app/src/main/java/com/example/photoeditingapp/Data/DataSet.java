package com.example.photoeditingapp.Data;

import android.content.Context;
import android.graphics.Bitmap;

public class DataSet {
    Context context;
    Bitmap imageBitMap;

    public DataSet(Context context) {
        this.context = context;
    }

    public Bitmap getImageBitMap() {
        return imageBitMap;
    }

    public void setImageBitMap(Bitmap imageBitMap) {
        this.imageBitMap = imageBitMap;
    }
}
