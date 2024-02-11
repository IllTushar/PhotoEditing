package com.example.photoeditingapp.Assets;

import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.example.photoeditingapp.R;

public class Toolbars {
    public void setToolbarTitle(String message, final Toolbar toolbar){
        TextView title =  toolbar.findViewById(R.id.toolbarTitle);
        title.setText(message);
    }
}
