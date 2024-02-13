package com.example.photoeditingapp.Assets;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.photoeditingapp.R;

public class Toolbars {
    public void setToolbarTitle(Context context,String message, final Toolbar toolbar){
        TextView title =  toolbar.findViewById(R.id.toolbarTitle);
        title.setText(message);
        ImageView ai = toolbar.findViewById(R.id.toolbarIcon);
        Glide.with(context).load(R.drawable.toolbaricon).into(ai);
    }
}
