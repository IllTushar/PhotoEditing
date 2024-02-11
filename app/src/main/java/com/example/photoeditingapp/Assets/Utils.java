package com.example.photoeditingapp.Assets;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;
import android.Manifest;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.photoeditingapp.R;

public class Utils {
    Context context;
    ProgressDialog pd;
    public static final int CAMERA_PERMISSION_REQUEST_CODE = 100;
    public static final int STORAGE_PERMISSION_REQUEST_CODE = 101;

    public Utils(Context context) {
        this.context = context;
        pd = new ProgressDialog(context);
    }

    public void toast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public void ProgressDialogShow(String message) {
        pd.show();
        pd.setMessage(message);
    }

    public void ProgressDialogDismiss() {
        pd.dismiss();
    }

    public void statusBar(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(context, R.color.black)); // Replace your_color with your desired color resource
        }
    }

    public void Intent(Context context, Class<?> targetClass) {
        context.startActivity(new Intent(context, targetClass));
    }

    public void cameraAndMediaPermissions(Activity activity){
        // Check if permissions are already granted
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted, request it
            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE},
                    CAMERA_PERMISSION_REQUEST_CODE);
        } else {
            // Permission already granted, proceed with your logic
        }
    }
}
